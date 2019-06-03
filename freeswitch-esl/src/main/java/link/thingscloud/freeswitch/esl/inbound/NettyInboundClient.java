/*
 * Copyright 2019 ThingsCloud Link.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.freeswitch.esl.inbound;

import link.thingscloud.freeswitch.esl.inbound.handler.InboundChannelHandler;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;

import java.util.function.Consumer;


/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
public class NettyInboundClient extends AbstractInboundClient {

    public NettyInboundClient(InboundClientOption option) {
        super(option);
    }

    @Override
    public EslMessage sendSyncApiCommand(String addr, String command, String arg) {
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
        return handler.sendSyncSingleLineCommand(sb.toString());
    }

    @Override
    public void sendSyncApiCommand(String addr, String command, String arg, Consumer<EslMessage> consumer) {
        publicExecutor.execute(() -> {
            EslMessage msg = sendSyncApiCommand(addr, command, arg);
            if (consumer != null) {
                consumer.accept(msg);
            }
        });
    }

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

    @Override
    public void sendAsyncApiCommand(String addr, String command, String arg, Consumer<String> consumer) {
        publicExecutor.execute(() -> {
            String msg = sendAsyncApiCommand(addr, command, arg);
            if (consumer != null) {
                consumer.accept(msg);
            }
        });

    }

    @Override
    public CommandResponse setEventSubscriptions(String addr, String format, String events) {
        if (!format.equals("plain")) {
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

    @Override
    public CommandResponse cancelEventSubscriptions(String addr) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncSingleLineCommand("noevents");
        return new CommandResponse("noevents", response);
    }

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

    @Override
    public CommandResponse sendMessage(String addr, SendMsg sendMsg) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncMultiLineCommand(sendMsg.getMsgLines());
        return new CommandResponse(sendMsg.toString(), response);
    }

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

    @Override
    public CommandResponse cancelLogging(String addr) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncSingleLineCommand("nolog");
        return new CommandResponse("nolog", response);
    }

    @Override
    public CommandResponse close(String addr) {
        InboundChannelHandler handler = getAuthedHandler(addr);
        EslMessage response = handler.sendSyncSingleLineCommand("exit");
        return new CommandResponse("exit", response);
    }
}
