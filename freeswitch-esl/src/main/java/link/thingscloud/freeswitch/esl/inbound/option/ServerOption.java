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

package link.thingscloud.freeswitch.esl.inbound.option;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>ServerOption class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */

@Data
@Accessors(fluent = true)
public class ServerOption {
    private final String host;
    private final int port;
    private int timeoutSeconds;
    private String password;

    private ConnectState state = ConnectState.INIT;

    private int connectTimes = 0;

    /**
     * <p>addr.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String addr() {
        return host + ":" + port;
    }

    /**
     * <p>addConnectTimes.</p>
     */
    public void addConnectTimes() {
        connectTimes++;
    }
}
