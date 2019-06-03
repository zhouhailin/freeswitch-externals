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

package link.thingscloud.freeswitch.esl.spring.boot.starter.example;

import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.helper.EslHelper;
import link.thingscloud.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
@Slf4j
@EslEventName("RE_SCHEDULE")
@Component
public class ReScheduleEslEventHandler implements EslEventHandler {

    @Autowired
    private InboundClient inboundClient;

    @Override
    public void handle(String addr, EslEvent event) {
        log.info("ReScheduleEslEventHandler handle addr[{}] EslEvent[{}].", addr, event);
        System.out.println(inboundClient);
        EslMessage eslMessage = inboundClient.sendSyncApiCommand(addr, "version", null);
        System.out.println(EslHelper.formatEslMessage(eslMessage));
    }
}
