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

package link.thingscloud.freeswitch.esl.spring.boot.starter.template;

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.DefaultEslEventHandler;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>IEslEventListenerTemplate class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@Component
public class IEslEventListenerTemplate implements IEslEventListener, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Map<String, List<EslEventHandler>> handlerTable = new HashMap<>(16);
    private EslEventHandler defaultEventHandler = new DefaultEslEventHandler();

    /**
     * {@inheritDoc}
     */
    @Override
    public void eventReceived(String addr, EslEvent event) {
        handleEslEvent(addr, event);
    }

    /** {@inheritDoc} */
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


    /** {@inheritDoc} */
    @Override
    public void afterPropertiesSet() {
        log.info("IEslEventListener init ...");
        Map<String, EslEventHandler> eventHandlerMap = applicationContext.getBeansOfType(EslEventHandler.class);
        for (EslEventHandler eventHandler : eventHandlerMap.values()) {
            Class<? extends EslEventHandler> eventHandleImpl = eventHandler.getClass();
            EslEventName eventName = eventHandleImpl.getAnnotation(EslEventName.class);
            if (eventName == null) {
                continue;
            }
            String[] values = eventName.value();
            if (ArrayUtils.isNotEmpty(values)) {
                for (String value : values) {
                    if (StringUtils.isNotBlank(value)) {
                        log.info("IEslEventListener add EventName[{}], EventHandler[{}] to tables ...", value, eventHandler.getClass());
                        if (StringUtils.equals(EslEventHandler.DEFAULT_ESL_EVENT_HANDLER, value)) {
                            defaultEventHandler = eventHandler;
                        } else {
                            handlerTable.computeIfAbsent(value, k -> new ArrayList<>(4)).add(eventHandler);
                        }
                    }
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
