/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.freeswitch.esl;

import link.thingscloud.freeswitch.esl.exception.InboundTimeoutExcetion;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendEvent;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;

import java.util.function.Consumer;

/**
 * <p>InboundClient interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public interface InboundClient extends InboundClientService {

    /**
     * <p>newInstance.</p>
     *
     * @param option a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     * @return a {@link link.thingscloud.freeswitch.esl.InboundClient} object.
     */
    static InboundClient newInstance(InboundClientOption option) {
        return InboundClientFactory.getInstance().newInboundClient(option);
    }

    /**
     * <p>newBootstrap.</p>
     *
     * @param option a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     * @return a {@link link.thingscloud.freeswitch.esl.InboundClientBootstrap} object.
     */
    static InboundClientBootstrap newBootstrap(InboundClientOption option) {
        return new InboundClientBootstrap(InboundClientFactory.getInstance().newInboundClient(option));
    }

    /**
     * <p>getInstance.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.InboundClient} object.
     */
    static InboundClient getInstance() {
        return InboundClientFactory.getInstance().getInboundClient();
    }

    /**
     * <p>getBootstrap.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.InboundClientBootstrap} object.
     */
    static InboundClientBootstrap getBootstrap() {
        return new InboundClientBootstrap(getInstance());
    }

    /**
     * 获取客户可配置选项
     *
     * @return this
     */
    InboundClientOption option();

    /**
     * Sends a FreeSWITCH API command to the server and blocks, waiting for an immediate response from the
     * server.
     * <p>
     * The outcome of the command from the server is retured in an {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} object.
     *
     * @param addr    Esl server address
     * @param command API command to send
     * @param arg     command arguments
     * @return an {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} containing command results
     */
    EslMessage sendSyncApiCommand(String addr, String command, String arg);

    /**
     * Sends a FreeSWITCH API command to the server and blocks, waiting for an immediate response from the
     * server.
     * <p>
     * The outcome of the command from the server is retured in an {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} object.
     *
     * @param addr           Esl server address
     * @param command        API command to send
     * @param arg            command arguments
     * @param timeoutSeconds timeout seconds arguments
     * @return an {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} containing command results
     * @throws link.thingscloud.freeswitch.esl.exception.InboundTimeoutExcetion if any.
     */
    EslMessage sendSyncApiCommand(String addr, String command, String arg, long timeoutSeconds) throws InboundTimeoutExcetion;

    /**
     * Aync callback Sends a FreeSWITCH API command to the server and blocks, waiting for an immediate response from the
     * server.
     * <p>
     * The outcome of the command from the server is retured in an {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} object.
     *
     * @param addr     Esl server address
     * @param command  API command to send
     * @param arg      command arguments
     * @param consumer a {@link java.util.function.Consumer} object.
     */
    void sendSyncApiCommand(String addr, String command, String arg, Consumer<EslMessage> consumer);

    /**
     * Submit a FreeSWITCH API command to the server to be executed in background mode. A synchronous
     * response from the server provides a UUID to identify the job execution results. When the server
     * has completed the job execution it fires a BACKGROUND_JOB Event with the execution results.
     * <p>
     * Note that this Client must be subscribed in the normal way to BACKGOUND_JOB Events, in order to
     * receive this event.
     *
     * @param addr    Esl server address
     * @param command API command to send
     * @param arg     command arguments
     * @return String Job-UUID that the server will tag result event with.
     */
    String sendAsyncApiCommand(String addr, String command, String arg);

    /**
     * Aync callback Submit a FreeSWITCH API command to the server to be executed in background mode. A synchronous
     * response from the server provides a UUID to identify the job execution results. When the server
     * has completed the job execution it fires a BACKGROUND_JOB Event with the execution results.
     * <p>
     * Note that this Client must be subscribed in the normal way to BACKGOUND_JOB Events, in order to
     * receive this event.
     *
     * @param addr     Esl server address
     * @param command  API command to send
     * @param arg      command arguments
     * @param consumer a {@link java.util.function.Consumer} object.
     */
    void sendAsyncApiCommand(String addr, String command, String arg, Consumer<String> consumer);

    /**
     * Set the current event subscription for this connection to the server.  Examples of the events
     * argument are:
     * <pre>
     *   ALL
     *   CHANNEL_CREATE CHANNEL_DESTROY HEARTBEAT
     *   CUSTOM conference::maintenance
     *   CHANNEL_CREATE CHANNEL_DESTROY CUSTOM conference::maintenance sofia::register sofia::expire
     * </pre>
     * <p>
     * Subsequent calls to this method replaces any previous subscriptions that were set.
     * </p>
     * Note: current implementation can only process 'plain' events.
     *
     * @param addr   Esl server address
     * @param format can be { plain | xml }
     * @param events { all | space separated list of events }
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse setEventSubscriptions(String addr, String format, String events);

    /**
     * Cancel any existing event subscription.
     *
     * @param addr Esl server address
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse cancelEventSubscriptions(String addr);

    /**
     * <p>
     * Add an event filter to the current set of event filters on this connection. Any of the event headers
     * can be used as a filter.
     * </p>
     * <p>
     * Note that event filters follow 'filter-in' semantics. That is, when a filter is applied
     * only the filtered values will be received. Multiple filters can be added to the current
     * connection.
     * </p>
     * Example filters:
     * <pre>
     *    eventHeader        valueToFilter
     *    ----------------------------------
     *    Event-Name         CHANNEL_EXECUTE
     *    Channel-State      CS_NEW
     * </pre>
     *
     * @param addr          Esl server address
     * @param eventHeader   to filter on
     * @param valueToFilter the value to match
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse addEventFilter(String addr, String eventHeader, String valueToFilter);

    /**
     * Delete an event filter from the current set of event filters on this connection.  See
     * {@link link.thingscloud.freeswitch.esl.InboundClient#addEventFilter}
     *
     * @param addr          Esl server address
     * @param eventHeader   to remove
     * @param valueToFilter to remove
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse deleteEventFilter(String addr, String eventHeader, String valueToFilter);

    /**
     * Send a {@link link.thingscloud.freeswitch.esl.transport.SendMsg} command to FreeSWITCH.  This client requires that the {@link link.thingscloud.freeswitch.esl.transport.SendMsg}
     * has a call UUID parameter.
     * <p>
     * https://freeswitch.org/confluence/display/FREESWITCH/mod_event_socket
     *
     * @param addr      Esl server address
     * @param sendEvent a {@link link.thingscloud.freeswitch.esl.transport.SendEvent} Event
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse sendEvent(String addr, SendEvent sendEvent);

    /**
     * Aync callback Send a {@link link.thingscloud.freeswitch.esl.transport.SendMsg} command to FreeSWITCH.  This client requires that the {@link link.thingscloud.freeswitch.esl.transport.SendMsg}
     * has a call UUID parameter.
     * <p>
     * https://freeswitch.org/confluence/display/FREESWITCH/mod_event_socket
     *
     * @param addr      Esl server address
     * @param sendEvent a {@link link.thingscloud.freeswitch.esl.transport.SendEvent} Event
     * @param consumer  a {@link java.util.function.Consumer} object.
     */
    void sendEvent(String addr, SendEvent sendEvent, Consumer<CommandResponse> consumer);

    /**
     * Send a {@link link.thingscloud.freeswitch.esl.transport.SendMsg} command to FreeSWITCH.  This client requires that the {@link link.thingscloud.freeswitch.esl.transport.SendMsg}
     * has a call UUID parameter.
     * <p>
     * https://freeswitch.org/confluence/display/FREESWITCH/mod_event_socket
     *
     * @param addr    Esl server address
     * @param sendMsg a {@link link.thingscloud.freeswitch.esl.transport.SendMsg} with call UUID
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse sendMessage(String addr, SendMsg sendMsg);

    /**
     * Aync callback Send a {@link link.thingscloud.freeswitch.esl.transport.SendMsg} command to FreeSWITCH.  This client requires that the {@link link.thingscloud.freeswitch.esl.transport.SendMsg}
     * has a call UUID parameter.
     * <p>
     * https://freeswitch.org/confluence/display/FREESWITCH/mod_event_socket
     *
     * @param addr     Esl server address
     * @param sendMsg  a {@link link.thingscloud.freeswitch.esl.transport.SendMsg} with call UUID
     * @param consumer a {@link java.util.function.Consumer} object.
     */
    void sendMessage(String addr, SendMsg sendMsg, Consumer<CommandResponse> consumer);

    /**
     * Enable log output.
     *
     * @param addr  Esl server address
     * @param level using the same values as in console.conf
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse setLoggingLevel(String addr, String level);

    /**
     * Disable any logging previously enabled with setLogLevel().
     *
     * @param addr Esl server address
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse cancelLogging(String addr);

    /**
     * Close the socket connection
     *
     * @param addr Esl server address
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    CommandResponse close(String addr);

    /**
     * Close the socket connection
     *
     * @param addr Esl server address
     * @return a {@link link.thingscloud.freeswitch.esl.transport.CommandResponse} with the server's response.
     */
    InboundClient closeChannel(String addr);

}
