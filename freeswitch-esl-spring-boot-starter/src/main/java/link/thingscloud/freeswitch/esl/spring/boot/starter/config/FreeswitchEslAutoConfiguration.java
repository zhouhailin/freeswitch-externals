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

package link.thingscloud.freeswitch.esl.spring.boot.starter.config;

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.InboundClientBootstrap;
import link.thingscloud.freeswitch.esl.ServerConnectionListener;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.InboundClientOptionHandler;
import link.thingscloud.freeswitch.esl.spring.boot.starter.properties.InboundClientProperties;
import link.thingscloud.freeswitch.esl.spring.boot.starter.template.DefaultInboundClientOptionHandlerTemplate;
import link.thingscloud.freeswitch.esl.spring.boot.starter.template.IEslEventListenerTemplate;
import link.thingscloud.freeswitch.esl.spring.boot.starter.template.ServerConnectionListenerTemplate;
import lombok.extern.slf4j.Slf4j;
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
 * @version 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({InboundClientProperties.class})
@ConditionalOnClass(InboundClient.class)
public class FreeswitchEslAutoConfiguration {

    /**
     * <p>inboundClientPropertiesHandler.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.spring.boot.starter.handler.InboundClientOptionHandler} object.
     */
    @Bean
    @ConditionalOnMissingBean(InboundClientOptionHandler.class)
    public InboundClientOptionHandler inboundClientOptionHandler() {
        return new DefaultInboundClientOptionHandlerTemplate();
    }

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
     * @param serverConnectionListener   a {@link link.thingscloud.freeswitch.esl.ServerConnectionListener} object.
     * @param inboundClientOptionHandler a {@link link.thingscloud.freeswitch.esl.spring.boot.starter.handler.InboundClientOptionHandler} object.
     * @return a {@link link.thingscloud.freeswitch.esl.InboundClient} object.
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean(InboundClient.class)
    public InboundClient inboundClient(@Autowired ServerConnectionListener serverConnectionListener, @Autowired InboundClientOptionHandler inboundClientOptionHandler) {
        InboundClientOption option = inboundClientOptionHandler.getOption();
        option.serverConnectionListener(serverConnectionListener);
        log.info("inboundClient properties : [{}]", option);
        log.info("inboundClient option : [{}]", option);
        return InboundClient.newInstance(option);
    }

    /**
     * InboundClient Bootstrap
     *
     * @param inboundClient a {@link link.thingscloud.freeswitch.esl.InboundClient} object.
     * @return a {@link link.thingscloud.freeswitch.esl.InboundClientBootstrap} object.
     */
    @Bean
    @ConditionalOnMissingBean(InboundClientBootstrap.class)
    public InboundClientBootstrap inboundClientBootstrap(@Autowired InboundClient inboundClient) {
        return new InboundClientBootstrap(inboundClient);
    }

}
