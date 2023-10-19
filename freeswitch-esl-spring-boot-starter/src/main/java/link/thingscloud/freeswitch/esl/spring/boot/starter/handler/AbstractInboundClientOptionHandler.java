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

package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;

import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.spring.boot.starter.properties.InboundClientProperties;
import link.thingscloud.freeswitch.esl.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Abstract AbstractInboundClientOptionHandler class.</p>
 *
 * @author zhouhailin
 * @version 1.0.0
 */
public abstract class AbstractInboundClientOptionHandler implements InboundClientOptionHandler {

    @Autowired
    protected InboundClientProperties properties;

    /**
     * <p>intercept.</p>
     *
     * @param inboundClientOption a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     */
    protected abstract void intercept(InboundClientOption inboundClientOption);

    /**
     * {@inheritDoc}
     */
    @Override
    public InboundClientOption getOption() {
        InboundClientOption option = newInboundClientOption();
        properties.getServers().forEach(server -> {
            if (StringUtils.isNotBlank(server.getHost()) && server.getPort() > 1) {
                option.addServerOption(new ServerOption(server.getHost(), server.getPort())
                        .timeoutSeconds(server.getTimeoutSeconds())
                        .password(server.getPassword()));
            }
        });
        properties.getEvents().forEach(event -> {
            if (StringUtils.isNotBlank(event)) {
                option.addEvents(event);
            }
        });
        intercept(option);
        return option;
    }

    /**
     * <p>newInboundClientOption.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     */
    protected InboundClientOption newInboundClientOption() {
        return new InboundClientOption().sndBufSize(properties.getSndBufSize())
                .rcvBufSize(properties.getRcvBufSize())
                .workerGroupThread(properties.getWorkerGroupThread())
                .publicExecutorThread(properties.getPublicExecutorThread())
                .privateExecutorThread(properties.getPrivateExecutorThread())
                .callbackExecutorThread(properties.getCallbackExecutorThread())
                .defaultTimeoutSeconds(properties.getDefaultTimeoutSeconds())
                .readTimeoutSeconds(properties.getReadTimeoutSeconds())
                .readerIdleTimeSeconds(properties.getReaderIdleTimeSeconds())
                .defaultPassword(properties.getDefaultPassword())
                .disablePublicExecutor(properties.isDisablePublicExecutor())
                .performance(properties.isPerformance())
                .performanceCostTime(properties.getPerformanceCostTime())
                .eventPerformance(properties.isEventPerformance())
                .eventPerformanceCostTime(properties.getEventPerformanceCostTime());
    }

}
