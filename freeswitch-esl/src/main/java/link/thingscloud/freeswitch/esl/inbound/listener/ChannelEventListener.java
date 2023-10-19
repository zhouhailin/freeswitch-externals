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

package link.thingscloud.freeswitch.esl.inbound.listener;

import link.thingscloud.freeswitch.esl.inbound.handler.InboundChannelHandler;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * <p>ChannelEventListener interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public interface ChannelEventListener {

    /**
     * <p>onChannelActive.</p>
     *
     * @param remoteAddr            a {@link java.lang.String} object.
     * @param inboundChannelHandler a {@link link.thingscloud.freeswitch.esl.inbound.handler.InboundChannelHandler} object.
     */
    void onChannelActive(String remoteAddr, InboundChannelHandler inboundChannelHandler);

    /**
     * <p>onChannelClosed.</p>
     *
     * @param remoteAddr a {@link java.lang.String} object.
     */
    void onChannelClosed(String remoteAddr);

    /**
     * <p>handleAuthRequest.</p>
     *
     * @param remoteAddr            a {@link java.lang.String} object.
     * @param inboundChannelHandler a {@link link.thingscloud.freeswitch.esl.inbound.handler.InboundChannelHandler} object.
     */
    void handleAuthRequest(String remoteAddr, InboundChannelHandler inboundChannelHandler);

    /**
     * <p>handleEslEvent.</p>
     *
     * @param remoteAddr a {@link java.lang.String} object.
     * @param event      a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     */
    void handleEslEvent(String remoteAddr, EslEvent event);

    /**
     * <p>handleDisconnectNotice.</p>
     *
     * @param remoteAddr a {@link java.lang.String} object.
     */
    void handleDisconnectNotice(String remoteAddr);

    /**
     * <p>handleRudeRejection.</p>
     *
     * @param remoteAddr a {@link java.lang.String} object.
     */
    void handleRudeRejection(String remoteAddr);
}
