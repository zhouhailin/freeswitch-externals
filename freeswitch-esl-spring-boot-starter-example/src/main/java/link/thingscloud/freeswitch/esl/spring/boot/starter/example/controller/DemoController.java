/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.freeswitch.esl.spring.boot.starter.example.controller;

import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>DemoController class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    /**
     * <p>demo.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }

    /**
     * <p>addServer1.</p>
     *
     * @param host a {@link java.lang.String} object.
     * @param port a int.
     */
    @GetMapping("/addServer1")
    public void addServer1(String host, int port) {
        InboundClient.getInstance().option().addServerOption(new ServerOption(host, port));
    }

    @Autowired
    private InboundClient inboundClient;

    /**
     * <p>addServer2.</p>
     *
     * @param host a {@link java.lang.String} object.
     * @param port a int.
     */
    @GetMapping("/addServer2")
    public void addServer2(String host, int port) {
        inboundClient.option().addServerOption(new ServerOption(host, port));
    }

    /**
     * <p>removeServer1.</p>
     */
    @GetMapping("/removeServer1")
    public void removeServer1() {
        ServerOption serverOption = inboundClient.option().serverOptions().get(0);
        inboundClient.option().removeServerOption(serverOption);
    }

    /**
     * <p>serverOptions.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/serverOptions")
    public String serverOptions() {
        return inboundClient.option().serverOptions().toString();
    }

}
