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

package link.thingscloud.freeswitch.esl.transport;


import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
public class CommandResponse {

    private final String command;
    private final String replyText;
    private final EslMessage response;
    private final boolean success;

    public CommandResponse(String command, EslMessage response) {
        this.command = command;
        this.response = response;
        this.replyText = response == null ? "" : response.getHeaderValue(EslHeaders.Name.REPLY_TEXT);
        this.success = replyText.startsWith("+OK");
    }

    /**
     * @return the original command sent to the server
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return true if and only if the response Reply-Text line starts with "+OK"
     */
    public boolean isOk() {
        return success;
    }

    /**
     * @return the full response Reply-Text line.
     */
    public String getReplyText() {
        return replyText;
    }

    /**
     * @return {@link EslMessage} the full response from the server
     */
    public EslMessage getResponse() {
        return response;
    }
}
