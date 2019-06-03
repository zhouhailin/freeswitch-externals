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

package link.thingscloud.freeswitch.esl;

import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
public class EslInboundClientExample {

    public static void main(String[] args) {
        InboundClientOption option = new InboundClientOption();

        option.defaultPassword("NewVois001")
                .addServerOption(new ServerOption("172.16.2.253", 8014));
        option.addEvents("all");

        option.addListener(new IEslEventListener() {
            @Override
            public void eventReceived(String addr, EslEvent event) {
                System.out.println(addr);
                System.out.println(event);
            }

            @Override
            public void backgroundJobResultReceived(String addr, EslEvent event) {
                System.out.println(addr);
                System.out.println(event);
            }
        });

        InboundClient inboundClient = InboundClient.newInstance(option);

        inboundClient.start();


    }

}
