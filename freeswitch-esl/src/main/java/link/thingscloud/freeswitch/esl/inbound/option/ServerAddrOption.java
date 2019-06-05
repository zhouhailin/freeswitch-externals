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

package link.thingscloud.freeswitch.esl.inbound.option;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
public class ServerAddrOption {
    private final List<ServerOption> serverOptions;

    ServerAddrOption(List<ServerOption> serverOptions) {
        this.serverOptions = serverOptions;
    }

    public String first() {
        Validate.notEmpty(serverOptions, "serverOptions must be not empty!");
        return serverOptions.get(0).addr();
    }

    public String last() {
        Validate.notEmpty(serverOptions, "serverOptions must be not empty!");
        return serverOptions.get(serverOptions.size() - 1).addr();
    }

    public String random() {
        Validate.notEmpty(serverOptions, "serverOptions must be not empty!");
        return serverOptions.get(RandomUtils.nextInt(0, serverOptions.size())).addr();
    }


}
