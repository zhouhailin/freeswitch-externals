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

package link.thingscloud.freeswitch.esl.spring.boot.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>InboundClientProperties class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "link.thingscloud.freeswitch.esl.inbound")
public class InboundClientProperties {

    private int sndBufSize = 65535;
    private int rcvBufSize = 65535;
    private int workerGroupThread = Runtime.getRuntime().availableProcessors() * 2;
    private int publicExecutorThread = Runtime.getRuntime().availableProcessors() * 2;
    private int privateExecutorThread = Runtime.getRuntime().availableProcessors() * 2;
    private int callbackExecutorThread = Runtime.getRuntime().availableProcessors() * 2;
    private int defaultTimeoutSeconds = 5;
    private int readTimeoutSeconds = 30;
    private int readerIdleTimeSeconds = 25;
    private String defaultPassword = "ClueCon";
    private boolean disablePublicExecutor = false;
    private boolean performance = false;
    private long performanceCostTime = 200;
    private boolean eventPerformance = false;
    private long eventPerformanceCostTime = 200;
    private List<String> events = new ArrayList<>();
    private List<ServerProperties> servers = new ArrayList<>();

}
