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

package link.thingscloud.freeswitch.esl.inbound.option;

import link.thingscloud.freeswitch.esl.inbound.listener.EventListener;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ServerOption class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */

@Data
@Accessors(fluent = true)
public class ServerOption {
    private final String host;
    private final int port;
    private String password;
    private int timeoutSeconds;
    private int connectTimes = 0;
    private EventListener eventListener;
    private ConnectState state = ConnectState.INIT;

    @Setter(AccessLevel.NONE)
    private List<String> events = new ArrayList<>();



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

    public ServerOption setEvents(List<String> events) {
        if (events == null || events.isEmpty())
            return this;
        this.events = events;
        if (eventListener != null)
            eventListener.addEvents(events);
        return this;
    }

    public ServerOption addEvents(String... addEvents) {
        if (addEvents == null) {
            return this;
        }

        List<String> list = new ArrayList<>();
        for (String addEvent : addEvents) {
            if (!events().contains(addEvent)) {
                list.add(addEvent);
            }
        }
        if (!list.isEmpty()) {
            events.addAll(list);
            if (eventListener != null) {
                eventListener.addEvents(list);
            }
        }
        return this;
    }

    public ServerOption cancelEvents() {
        if (!events.isEmpty()) {
            if (eventListener != null) {
                eventListener.cancelEvents();
            }
            events.clear();
        }
        return this;
    }
}
