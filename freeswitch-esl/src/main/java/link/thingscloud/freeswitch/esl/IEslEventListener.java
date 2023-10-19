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

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * <p>IEslEventListener interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public interface IEslEventListener {

    /**
     * Signal of a server initiated event.
     *
     * @param addr  addr
     * @param event as an {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent}
     */
    void eventReceived(String addr, EslEvent event);

    /**
     * Signal of an event containing the result of a client requested background job.  The Job-UUID will
     * be available as an event header of that name.
     *
     * @param addr  addr
     * @param event as an {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent}
     */
    void backgroundJobResultReceived(String addr, EslEvent event);

}
