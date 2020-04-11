/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.freeswitch.esl.inbound;

import io.netty.channel.ChannelFutureListener;
import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.constant.EslConstant;
import link.thingscloud.freeswitch.esl.exception.InboundClientException;
import link.thingscloud.freeswitch.esl.inbound.handler.InboundChannelHandler;
import link.thingscloud.freeswitch.esl.inbound.listener.EventListener;
import link.thingscloud.freeswitch.esl.inbound.listener.ServerOptionListener;
import link.thingscloud.freeswitch.esl.inbound.option.ConnectState;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>flow :</p>
 * |------------------------------------|
 * |                                    |
 * \|            |---» CONNECTED  ---» CLOSED  ---» SHUTDOWN
 * INIT ----» CONNECTING -----|
 * /|            |---» FAILED
 * |                     |
 * ----------------------|
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
abstract class AbstractInboundClient extends AbstractNettyInboundClient implements InboundClient {

    private final ScheduledThreadPoolExecutor scheduledPoolExecutor = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());

    private final Map<String, InboundChannelHandler> handlerTable = new HashMap<>(32);

    AbstractInboundClient(InboundClientOption option) {
        super(option);
    }

    /** {@inheritDoc} */
    @Override
    public InboundClientOption option() {
        return option;
    }


    /** {@inheritDoc} */
    @Override
    public void start() {
        log.info("inbound client will start ...");

        addServerOptionListener();

        addEventListener();

        option().serverOptions().forEach(serverOption -> {
            if (serverOption.state() == ConnectState.INIT) {
                serverOption.state(ConnectState.CONNECTING);
                doConnect(serverOption);
            }
        });
    }


    /** {@inheritDoc} */
    @Override
    public void shutdown() {
        log.info("inbound client will shutdown ...");
        option().serverOptions().forEach(serverOption -> {
            serverOption.state(ConnectState.SHUTDOWN);
            InboundChannelHandler inboundChannelHandler = handlerTable.get(serverOption.addr());
            if (inboundChannelHandler != null) {
                inboundChannelHandler.close().addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        log.info("shutdown inbound client remote server [{}:{}] success.", serverOption.host(), serverOption.port());
                    } else {
                        log.info("shutdown inbound client remote server [{}:{}] failed, cause : ", serverOption.host(), serverOption.port(), future.cause());
                    }
                });
            }
        });
        workerGroup.shutdownGracefully();
    }

    /** {@inheritDoc} */
    @Override
    public void onChannelActive(String remoteAddr, InboundChannelHandler inboundChannelHandler) {
        handlerTable.put(remoteAddr, inboundChannelHandler);
        // 连接监听
        option().serverOptions().forEach(serverOption -> {
            if (StringUtils.equals(serverOption.addr(), remoteAddr)) {
                if (option().serverConnectionListener() != null) {
                    option().serverConnectionListener().onOpened(serverOption);
                }
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void onChannelClosed(String remoteAddr) {
        handlerTable.remove(remoteAddr);
        option().serverOptions().forEach(serverOption -> {
            if (StringUtils.equals(serverOption.addr(), remoteAddr)) {
                // 连接监听
                if (option().serverConnectionListener() != null) {
                    option().serverConnectionListener().onClosed(serverOption);
                }
                if (serverOption.state() != ConnectState.SHUTDOWN) {
                    serverOption.state(ConnectState.CLOSED);
                    scheduledPoolExecutor.schedule(() -> doConnect(serverOption), getTimeoutSeconds(serverOption), TimeUnit.SECONDS);
                }
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void handleAuthRequest(String addr, InboundChannelHandler inboundChannelHandler) {
        log.info("Auth requested[{}], sending [auth {}]", addr, "*****");
        for (ServerOption serverOption : option().serverOptions()) {
            String password = serverOption.password();
            if (password == null) {
                password = option().defaultPassword();
            }
            if (StringUtils.equals(addr, serverOption.addr())) {
                EslMessage response = inboundChannelHandler.sendSyncSingleLineCommand("auth " + password);
                log.debug("Auth response [{}]", response);
                if (response.getContentType().equals(EslHeaders.Value.COMMAND_REPLY)) {
                    CommandResponse reply = new CommandResponse("auth " + password, response);
                    serverOption.state(ConnectState.AUTHED);
                    log.info("Auth response success={}, message=[{}]", reply.isOk(), reply.getReplyText());
                    if (!option().events().isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("event plain ");
                        for (String event : option().events()) {
                            sb.append(event).append(" ");
                        }
                        setEventSubscriptions(addr, "plain", sb.toString());
                    }
                } else {
                    serverOption.state(ConnectState.AUTHED_FAILED);
                    log.error("Bad auth response message [{}]", response);
                    throw new IllegalStateException("Incorrect auth response");
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void handleEslEvent(String addr, EslEvent event) {
        option().listeners().forEach(listener -> {
            long start = 0L;
            if (option().performance()) {
                start = System.currentTimeMillis();
            }
            log.debug("Event addr[{}] received [{}]", addr, event);
            /*
             *  Notify listeners in a different thread in order to:
             *    - not to block the IO threads with potentially long-running listeners
             *    - generally be defensive running other people's code
             *  Use a different worker thread pool for async job results than for event driven
             *  events to keep the latency as low as possible.
             */
            if (StringUtils.equals(event.getEventName(), EslConstant.BACKGROUND_JOB)) {
                try {
                    listener.backgroundJobResultReceived(addr, event);
                } catch (Throwable t) {
                    log.error("Error caught notifying listener of job result [{}], remote address [{}]", event, addr, t);
                }
            } else {
                try {
                    listener.eventReceived(addr, event);
                } catch (Throwable t) {
                    log.error("Error caught notifying listener of event [{}], remote address [{}]", event, addr, t);
                }
            }
            if (option().performance()) {
                long cost = System.currentTimeMillis() - start;
                if (cost >= option().performanceCostTime()) {
                    log.warn("[performance] handle esl event cost time : {}ms", cost);
                }
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void handleDisconnectNotice(String addr) {
        log.info("Disconnected[{}] ...", addr);
    }

    public InboundChannelHandler getAuthedHandler(String addr) {
        InboundChannelHandler handler = handlerTable.get(addr);
        if (handler == null) {
            throw new InboundClientException("not found inbound handler for addr : " + addr);
        }
        List<ServerOption> serverOptions = option().serverOptions();
        for (ServerOption serverOption : serverOptions) {
            if (StringUtils.equals(serverOption.addr(), addr)) {
                if (serverOption.state() != ConnectState.AUTHED) {
                    throw new InboundClientException("inbound handler is not authed for addr : " + addr);
                }
                break;
            }
        }
        return handler;
    }

    private void addServerOptionListener() {
        option().serverOptionListener(new ServerOptionListener() {
            @Override
            public void onAdded(ServerOption serverOption) {
                if (serverOption.state() == ConnectState.INIT) {
                    doConnect(serverOption);
                }
            }

            @Override
            public void onRemoved(ServerOption serverOption) {
                if (serverOption.state() == ConnectState.CONNECTED || serverOption.state() == ConnectState.AUTHED) {
                    doClose(serverOption);
                }
            }
        });
    }

    private void addEventListener() {
        log.info("add event listener ...");
        option().eventListener(new EventListener() {
            @Override
            public void addEvents(List<String> list) {
                StringBuilder sb = new StringBuilder();
                for (String event : list) {
                    sb.append(event).append(" ");
                }
                option().serverOptions().forEach(serverOption -> publicExecutor.execute(() -> setEventSubscriptions(serverOption.addr(), "plain", sb.toString())));
            }

            @Override
            public void cancelEvents() {
                option().serverOptions().forEach(serverOption -> publicExecutor.execute(() -> cancelEventSubscriptions(serverOption.addr())));

            }
        });
    }

    private void doConnect(final ServerOption serverOption) {
        log.info("doConnect remote server [{}:{}] success.", serverOption.host(), serverOption.port());
        serverOption.addConnectTimes();
        serverOption.state(ConnectState.CONNECTING);
        bootstrap.connect(serverOption.host(), serverOption.port()).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                serverOption.state(ConnectState.CONNECTED);
                log.info("connect remote server [{}:{}] success.", serverOption.host(), serverOption.port());
            } else {
                serverOption.state(ConnectState.FAILED);
                log.warn("connect remote server [{}:{}] failed, will try again, cause : ", serverOption.host(), serverOption.port(), future.cause());
                scheduledPoolExecutor.schedule(() -> doConnect(serverOption), getTimeoutSeconds(serverOption), TimeUnit.SECONDS);
            }
        });
    }

    private void doClose(ServerOption serverOption) {
        log.info("doClose remote server [{}:{}] success.", serverOption.host(), serverOption.port());
        serverOption.state(ConnectState.CLOSING);
        option().serverOptions().remove(serverOption);
        String remoteAddr = serverOption.addr();
        InboundChannelHandler inboundChannelHandler = handlerTable.get(remoteAddr);
        if (inboundChannelHandler != null) {
            inboundChannelHandler.close().addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("close remote server [{}:{}] success.", serverOption.host(), serverOption.port());
                } else {
                    log.info("close remote server [{}:{}] failed, cause : ", serverOption.host(), serverOption.port(), future.cause());
                }
            });
        }
    }

    private int getTimeoutSeconds(ServerOption serverOption) {
        return serverOption.timeoutSeconds() == 0 ? option().defaultTimeoutSeconds() : serverOption.timeoutSeconds();
    }
}
