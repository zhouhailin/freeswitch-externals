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

package link.thingscloud.freeswitch.esl.spring.boot.starter.config;

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.ServerConnectionListener;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.spring.boot.starter.propeties.InboundClientProperties;
import link.thingscloud.freeswitch.esl.spring.boot.starter.template.IEslEventListenerTemplate;
import link.thingscloud.freeswitch.esl.spring.boot.starter.template.ServerConnectionListenerTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>FreeswitchEslAutoConfiguration class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({InboundClientProperties.class})
@ConditionalOnClass(InboundClient.class)
public class FreeswitchEslAutoConfiguration {

    @Autowired
    private InboundClientProperties properties;

    /**
     * <p>listener.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.IEslEventListener} object.
     */
    @Bean
    @ConditionalOnMissingBean(IEslEventListener.class)
    public IEslEventListener listener() {
        return new IEslEventListenerTemplate();
    }

    /**
     * <p>serverConnectionListener.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.ServerConnectionListener} object.
     */
    @Bean
    @ConditionalOnMissingBean(ServerConnectionListener.class)
    public ServerConnectionListener serverConnectionListener() {
        return new ServerConnectionListenerTemplate();
    }

    /**
     * <p>inboundClient.</p>
     *
     * @param listener                 a {@link link.thingscloud.freeswitch.esl.IEslEventListener} object.
     * @param serverConnectionListener a {@link link.thingscloud.freeswitch.esl.ServerConnectionListener} object.
     * @return a {@link link.thingscloud.freeswitch.esl.InboundClient} object.
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean(InboundClient.class)
    public InboundClient inboundClient(@Autowired IEslEventListener listener, @Autowired ServerConnectionListener serverConnectionListener) {
        InboundClientOption option = new InboundClientOption();

        option.sndBufSize(properties.getSndBufSize())
                .rcvBufSize(properties.getRcvBufSize())
                .workerGroupThread(properties.getWorkerGroupThread())
                .publicExecutorThread(properties.getPublicExecutorThread())
                .callbackExecutorThread(properties.getCallbackExecutorThread())
                .defaultTimeoutSeconds(properties.getDefaultTimeoutSeconds())
                .readTimeoutSeconds(properties.getReadTimeoutSeconds())
                .readerIdleTimeSeconds(properties.getReaderIdleTimeSeconds())
                .defaultPassword(properties.getDefaultPassword())
                .performance(properties.isPerformance())
                .performanceCostTime(properties.getPerformanceCostTime());

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

        option.addListener(listener);

        option.serverConnectionListener(serverConnectionListener);

        log.info("inboundClient properties : [{}]", properties);
        log.info("inboundClient option : [{}]", option);

        return InboundClient.newInstance(option);
    }

}
