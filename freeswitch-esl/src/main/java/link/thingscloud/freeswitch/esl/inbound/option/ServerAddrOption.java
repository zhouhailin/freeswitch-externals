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


import link.thingscloud.freeswitch.esl.util.RandomUtils;
import link.thingscloud.freeswitch.esl.util.Validate;

import java.util.List;

/**
 * <p>ServerAddrOption class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public class ServerAddrOption {
    private final List<ServerOption> serverOptions;
    private static final String VALIDATE_MESSAGE_1 = "serverOptions must be not empty!";

    ServerAddrOption(List<ServerOption> serverOptions) {
        this.serverOptions = serverOptions;
    }

    /**
     * <p>first.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String first() {
        Validate.notEmpty(serverOptions, VALIDATE_MESSAGE_1);
        return serverOptions.get(0).addr();
    }

    /**
     * <p>last.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String last() {
        Validate.notEmpty(serverOptions, VALIDATE_MESSAGE_1);
        return serverOptions.get(serverOptions.size() - 1).addr();
    }

    /**
     * <p>random.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String random() {
        Validate.notEmpty(serverOptions, VALIDATE_MESSAGE_1);
        return serverOptions.get(RandomUtils.nextInt(0, serverOptions.size())).addr();
    }


}
