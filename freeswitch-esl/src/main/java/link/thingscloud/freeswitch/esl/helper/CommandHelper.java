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

/**
 * @author zhouhailin
 * @since 1.6.5
 */
public class CommandHelper {

    private final StringBuilder builder = new StringBuilder();

    private CommandHelper(String cmd) {
        builder.append(cmd);
    }

    public static CommandHelper cmd(String cmd) {
        return new CommandHelper(cmd);
    }

    public CommandHelper arg(String arg) {
        builder.append(" ").append(arg);
        return this;
    }

    public CommandHelper arg(boolean arg) {
        builder.append(" ").append(arg);
        return this;
    }

    public CommandHelper arg(int arg) {
        builder.append(" ").append(arg);
        return this;
    }

    public CommandHelper arg(long arg) {
        builder.append(" ").append(arg);
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
