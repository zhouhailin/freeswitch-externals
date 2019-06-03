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

package link.thingscloud.freeswitch.esl.helper;

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;

import java.util.List;
import java.util.Map;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
public class EslHelper {


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
