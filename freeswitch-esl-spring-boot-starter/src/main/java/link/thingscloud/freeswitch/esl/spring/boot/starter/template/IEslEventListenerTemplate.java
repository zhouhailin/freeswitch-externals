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

package link.thingscloud.freeswitch.esl.spring.boot.starter.template;

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.DefaultEslEventHandler;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.util.ArrayUtils;
import link.thingscloud.freeswitch.esl.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>IEslEventListenerTemplate class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
@Slf4j
public class IEslEventListenerTemplate implements IEslEventListener, InitializingBean {

    @Autowired
    private InboundClient inboundClient;
    @Autowired(required = false)
    private final List<EslEventHandler> eslEventHandlers = Collections.emptyList();

    private EslEventHandler defaultEventHandler = new DefaultEslEventHandler();
    private final Map<String, List<EslEventHandler>> handlerTable = new HashMap<>(16);

    /**
     * {@inheritDoc}
     */
    @Override
    public void eventReceived(String addr, EslEvent event) {
        handleEslEvent(addr, event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void backgroundJobResultReceived(String addr, EslEvent event) {
        handleEslEvent(addr, event);
    }

    private void handleEslEvent(String addr, EslEvent event) {
        String eventName = event.getEventName();
        List<EslEventHandler> handlers = handlerTable.get(eventName);
        if (!CollectionUtils.isEmpty(handlers)) {
            handlers.forEach(eventHandler -> eventHandler.handle(addr, event));
            return;
        }
        defaultEventHandler.handle(addr, event);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        log.info("IEslEventListener init ...");
        for (EslEventHandler eventHandler : eslEventHandlers) {
            // link.thingscloud.freeswitch.esl.spring.boot.starter.example.HeartbeatEslEventHandler
            // link.thingscloud.freeswitch.esl.spring.boot.starter.example.ReScheduleEslEventHandler$$EnhancerBySpringCGLIB$$1b4e8d
            EslEventName eventName = eventHandler.getClass().getAnnotation(EslEventName.class);
            if (eventName == null) {
                // FIXED : AOP
                eventName = eventHandler.getClass().getSuperclass().getAnnotation(EslEventName.class);
            }
            if (eventName == null || ArrayUtils.isEmpty(eventName.value())) {
                continue;
            }
            for (String value : eventName.value()) {
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                log.info("IEslEventListener add EventName[{}], EventHandler[{}] ...", value, eventHandler.getClass());
                if (StringUtils.equals(EslEventHandler.DEFAULT_ESL_EVENT_HANDLER, value)) {
                    defaultEventHandler = eventHandler;
                } else {
                    handlerTable.computeIfAbsent(value, k -> new ArrayList<>(4)).add(eventHandler);
                }
            }
        }
        inboundClient.option().addListener(this);
    }

}
