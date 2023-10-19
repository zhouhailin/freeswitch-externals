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

package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;


import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * <p>EslEventHandler interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public interface EslEventHandler {

    /**
     * Constant <code>DEFAULT_ESL_EVENT_HANDLER="DEFAULT_ESL_EVENT_HANDLER"</code>
     */
    String DEFAULT_ESL_EVENT_HANDLER = "DEFAULT_ESL_EVENT_HANDLER";

    /**
     * sub event key
     */
    String SUB_EVENT_HEADER_KEY = "Event-Subclass";

    /**
     * <p>handle.</p>
     *
     * @param addr  a {@link java.lang.String} object.
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     */
    void handle(String addr, EslEvent event);

}
