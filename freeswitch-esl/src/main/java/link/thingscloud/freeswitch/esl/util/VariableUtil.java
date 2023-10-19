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

package link.thingscloud.freeswitch.esl.util;

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

import java.util.Map;

/**
 * @author zhouhailin
 * @version 1.5.1
 */
public class VariableUtil {

    private static final String VARIABLE_PREFIX = "variable_";

    public static String get(EslEvent event, String key) {
        return event.getEventHeaders().get(key);
    }

    public static long getLongVar(EslEvent event, String key) {
        return Long.parseLong(getVar(event.getEventHeaders(), key));
    }

    public static int getIntVar(EslEvent event, String key) {
        return Integer.parseInt(getVar(event.getEventHeaders(), key));
    }

    public static String getVar(EslEvent event, String key) {
        return getVar(event.getEventHeaders(), key);
    }

    public static String getVar(Map<String, String> eventHeaders, String key) {
        if (key.startsWith(VARIABLE_PREFIX)) {
            return eventHeaders.get(key);
        }
        return eventHeaders.get(VARIABLE_PREFIX + key);
    }

    private VariableUtil() {
    }

}
