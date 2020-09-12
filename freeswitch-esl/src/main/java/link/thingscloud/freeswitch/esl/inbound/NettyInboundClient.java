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

import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.constant.EslConstant;
import link.thingscloud.freeswitch.esl.exception.InboundTimeoutExcetion;
import link.thingscloud.freeswitch.esl.inbound.handler.InboundChannelHandler;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import org.apache.commons.lang3.StringUtils;

import java.nio.channels.ClosedChannelException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


/**
 * <p>NettyInboundClient class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public class NettyInboundClient extends AbstractInboundClient {

    /**
     * <p>Constructor for NettyInboundClient.</p>
     *
     * @param option a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     */
    public NettyInboundClient(InboundClientOption option) {
        super(option);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EslMessage sendSyncApiCommand(String addr, String command, String arg) throws Exception {
        InboundChannelHandler handler = getAuthedHandler(addr);
        StringBuilder sb = new StringBuilder();
        if (command != null && !command.isEmpty()) {
            sb.append("api ");
            sb.append(command);
        }
        if (arg != null && !arg.isEmpty()) {
            sb.append(' ');
            sb.append(arg);
        }
        log.debug("sendSyncApiCommand addr : {}, command : {}, arg : {}", addr, command, arg);
        return handler.sendSyncSingleLineCommand(sb.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EslMessage sendSyncApiCommand(String addr, String command, String arg, long timeoutSeconds) throws InboundTimeoutExcetion {
        try {
            return publicExecutor.submit(() -> sendSyncApiCommand(addr, command, arg)).get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new InboundTimeoutExcetion(String.format("sendSyncApiCommand addr : %s, command : %s, arg : %s, timeoutSeconds : %s", addr, command, arg, timeoutSeconds), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendSyncApiCommand(String addr, String command, String arg, Consumer<EslMessage> consumer) {
        Future<EslMessage> future = publicExecutor.submit(() -> sendSyncApiCommand(addr, command, arg));
        EslMessage result = null;
        try {
            result = future.get();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (consumer != null)
            consumer.accept(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendAsyncApiCommand(String addr, String command, String arg) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        StringBuilder sb = new StringBuilder();
        if (command != null && !command.isEmpty()) {
            sb.append("bgapi ");
            sb.append(command);
        }
        if (arg != null && !arg.isEmpty()) {
            sb.append(' ');
            sb.append(arg);
        }

        return handler.sendAsyncCommand(sb.toString());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAsyncApiCommand(String addr, String command, String arg, Consumer<String> consumer) {
        Future<String> future = publicExecutor.submit(() -> sendAsyncApiCommand(addr, command, arg));
        String result = null;
        try {
            result = future.get();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (consumer != null)
            consumer.accept(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse setEventSubscriptions(String addr, String format, String events) {
        if (!StringUtils.equals(format, EslConstant.PLAIN)) {
            throw new IllegalStateException("Only 'plain' event format is supported at present");
        }
        InboundChannelHandler handler = getAuthedHandler(addr);

        StringBuilder sb = new StringBuilder();
        sb.append("event ");
        sb.append(format);
        if (events != null && !events.isEmpty()) {
            sb.append(' ');
            sb.append(events);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());
        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse cancelEventSubscriptions(String addr) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncSingleLineCommand("noevents");
        return new CommandResponse("noevents", response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse addEventFilter(String addr, String eventHeader, String valueToFilter) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        StringBuilder sb = new StringBuilder();
        if (eventHeader != null && !eventHeader.isEmpty()) {
            sb.append("filter ");
            sb.append(eventHeader);
        }
        if (valueToFilter != null && !valueToFilter.isEmpty()) {
            sb.append(' ');
            sb.append(valueToFilter);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());

        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse deleteEventFilter(String addr, String eventHeader, String valueToFilter) {
        InboundChannelHandler handler = getAuthedHandler(addr);

        StringBuilder sb = new StringBuilder();
        if (eventHeader != null && !eventHeader.isEmpty()) {
            sb.append("filter delete ");
            sb.append(eventHeader);
        }
        if (valueToFilter != null && !valueToFilter.isEmpty()) {
            sb.append(' ');
            sb.append(valueToFilter);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());
        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse sendMessage(String addr, SendMsg sendMsg) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncMultiLineCommand(sendMsg.getMsgLines());
        return new CommandResponse(sendMsg.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse setLoggingLevel(String addr, String level) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        StringBuilder sb = new StringBuilder();
        if (level != null && !level.isEmpty()) {
            sb.append("log ");
            sb.append(level);
        }
        EslMessage response = handler.sendSyncSingleLineCommand(sb.toString());
        return new CommandResponse(sb.toString(), response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse cancelLogging(String addr) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncSingleLineCommand("nolog");
        return new CommandResponse("nolog", response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandResponse close(String addr) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncSingleLineCommand("exit");
        return new CommandResponse("exit", response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InboundClient closeChannel(String addr) {
        getAuthedHandler(addr).close();
        return this;
    }

    @Override
    public boolean hangup(String addr) {
        return hangup(addr, null);
    }

    @Override
    public boolean hangup(String addr, String reason) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.addCallCommand("execute");
        sendMsg.addExecuteAppName("hangup");
        if (StringUtils.isNotBlank(reason))
            sendMsg.addHangupCause(reason);
        CommandResponse resp = sendMessage(addr, sendMsg);
        return resp != null && resp.isOk();
    }

    @Override
    public boolean bridge(String addr, String endpoint) {
        CommandResponse resp = executeApp(addr, "bridge", endpoint);
        return resp != null && resp.isOk();
    }

    @Override
    public CommandResponse executeApp(String addr, String app) {
        return executeApp(addr, app, null);
    }

    @Override
    public CommandResponse executeApp(String addr, String app, String args) {
        SendMsg msg = new SendMsg();
        msg.addCallCommand("execute");
        msg.addExecuteAppName(app);
        if (StringUtils.isNotBlank(args))
            msg.addExecuteAppArg(args);
        return sendMessage(addr, msg);
    }
}
