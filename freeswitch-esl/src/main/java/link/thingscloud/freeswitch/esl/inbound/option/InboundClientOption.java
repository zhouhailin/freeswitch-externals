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

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.inbound.listener.EventListener;
import link.thingscloud.freeswitch.esl.inbound.listener.ServerOptionListener;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
@ToString
public class InboundClientOption {

    private int sndBufSize = 65535;
    private int rcvBufSize = 65535;

    private int workerGroupThread = 8;
    private int publicExecutorThread = 8;
    private int callbackExecutorThread = 8;

    private int defaultTimeoutSeconds = 5;
    private String defaultPassword = "ClueCon";


    private boolean performance = false;
    private long performanceCostTime = 200;

    private ServerOptionListener serverOptionListener = null;
    private List<ServerOption> serverOptions = new ArrayList<>();
    private ServerAddrOption serverAddrOption = new ServerAddrOption(serverOptions);

    private List<IEslEventListener> listeners = new ArrayList<>();

    private EventListener eventListener = null;
    private List<String> events = new ArrayList<>();

    public int sndBufSize() {
        return sndBufSize;
    }

    public InboundClientOption sndBufSize(int sndBufSize) {
        this.sndBufSize = sndBufSize;
        return this;
    }

    public int rcvBufSize() {
        return rcvBufSize;
    }

    public InboundClientOption rcvBufSize(int rcvBufSize) {
        this.rcvBufSize = rcvBufSize;
        return this;
    }

    public int workerGroupThread() {
        return workerGroupThread;
    }

    public InboundClientOption workerGroupThread(int workerGroupThread) {
        this.workerGroupThread = workerGroupThread;
        return this;
    }

    public int publicExecutorThread() {
        return publicExecutorThread;
    }

    public InboundClientOption publicExecutorThread(int publicExecutorThread) {
        this.publicExecutorThread = publicExecutorThread;
        return this;
    }

    public int callbackExecutorThread() {
        return callbackExecutorThread;
    }

    public InboundClientOption callbackExecutorThread(int callbackExecutorThread) {
        this.callbackExecutorThread = callbackExecutorThread;
        return this;
    }

    public int defaultTimeoutSeconds() {
        return defaultTimeoutSeconds;
    }

    public InboundClientOption defaultTimeoutSeconds(int defaultTimeoutSeconds) {
        this.defaultTimeoutSeconds = defaultTimeoutSeconds;
        return this;
    }

    public String defaultPassword() {
        return defaultPassword;
    }

    public InboundClientOption defaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
        return this;
    }

    public boolean performance() {
        return performance;
    }

    public InboundClientOption performance(boolean performance) {
        this.performance = performance;
        return this;
    }

    public long performanceCostTime() {
        return performanceCostTime;
    }

    public InboundClientOption performanceCostTime(long performanceCostTime) {
        this.performanceCostTime = performanceCostTime;
        return this;
    }

    public ServerOptionListener serverOptionListener() {
        return serverOptionListener;
    }

    public InboundClientOption serverOptionListener(ServerOptionListener serverOptionListener) {
        this.serverOptionListener = serverOptionListener;
        return this;
    }

    public ServerAddrOption serverAddrOption() {
        return serverAddrOption;
    }

    public List<ServerOption> serverOptions() {
        return serverOptions;
    }

    public InboundClientOption addServerOption(ServerOption serverOption) {
        for (ServerOption option : serverOptions) {
            if (StringUtils.equals(option.addr(), serverOption.addr())) {
                return this;
            }
        }
        serverOptions.add(serverOption);
        if (serverOptionListener != null) {
            serverOptionListener.onAdded(serverOption);
        }
        return this;
    }

    public InboundClientOption removeServerOption(ServerOption serverOption) {
        serverOptions.remove(serverOption);
        if (serverOptionListener != null) {
            serverOptionListener.onRemoved(serverOption);
        }
        return this;
    }

    public InboundClientOption addListener(IEslEventListener listener) {
        listeners.add(listener);
        return this;
    }

    public InboundClientOption removeListener(IEslEventListener listener) {
        listeners.remove(listener);
        return this;
    }

    public List<IEslEventListener> listeners() {
        return listeners;
    }

    public EventListener eventListener() {
        return eventListener;
    }

    public InboundClientOption eventListener(EventListener eventListener) {
        this.eventListener = eventListener;
        return this;
    }

    public List<String> events() {
        return events;
    }

    public InboundClientOption addEvents(String... addEvents) {
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

    public InboundClientOption cancelEvents() {
        if (!events.isEmpty()) {
            if (eventListener != null) {
                eventListener.cancelEvents();
            }
            events.clear();
        }
        return this;
    }


}
