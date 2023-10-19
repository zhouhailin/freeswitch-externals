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

package link.thingscloud.freeswitch.esl.helper;

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;

import java.util.List;
import java.util.Map;

/**
 * <p>EslHelper class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public class EslHelper {

    /**
     * private constructor
     */
    private EslHelper() {
    }

    /**
     * <p>formatEslEvent.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatEslEvent(EslEvent event) {

        StringBuilder sb = new StringBuilder();

        sb.append(System.lineSeparator());
        sb.append("#").append(System.lineSeparator());
        sb.append("## message header : ").append(System.lineSeparator());
        Map<EslHeaders.Name, String> messageHeaders =
                event.getMessageHeaders();
        for (Map.Entry<EslHeaders.Name, String> entry : messageHeaders.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(System.lineSeparator());
        }

        sb.append("## event header : ").append(System.lineSeparator());
        Map<String, String> eventHeaders = event.getEventHeaders();
        for (Map.Entry<String, String> entry : eventHeaders.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(System.lineSeparator());

        }

        sb.append("## event body lines : ").append(System.lineSeparator());
        List<String> eventBodyLines = event.getEventBodyLines();
        for (String eventBodyLine : eventBodyLines) {
            sb.append(eventBodyLine).append(System.lineSeparator());
        }
        sb.append("#").append(System.lineSeparator());

        return sb.toString();
    }


    /**
     * <p>formatEslMessage.</p>
     *
     * @param message a {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatEslMessage(EslMessage message) {
        StringBuilder sb = new StringBuilder();

        sb.append(System.lineSeparator());
        sb.append("#").append(System.lineSeparator());
        sb.append("## message header : ").append(System.lineSeparator());

        Map<EslHeaders.Name, String> headers =
                message.getHeaders();
        for (Map.Entry<EslHeaders.Name, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(System.lineSeparator());
        }

        sb.append("## event body lines : ").append(System.lineSeparator());
        List<String> eventBodyLines = message.getBodyLines();
        for (String eventBodyLine : eventBodyLines) {
            sb.append(eventBodyLine).append(System.lineSeparator());
        }
        sb.append("#").append(System.lineSeparator());

        return sb.toString();
    }
}
