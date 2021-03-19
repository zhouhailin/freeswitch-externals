# FreeSWITCH ESL ALL

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bc80abd17a444f0ba0d94ec807e07843)](https://app.codacy.com/manual/zhouhailin/freeswitch-esl-all?utm_source=github.com&utm_medium=referral&utm_content=zhouhailin/freeswitch-esl-all&utm_campaign=Badge_Grade_Settings)
[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-all/badge.svg)](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-all/)
[![Gitter](https://badges.gitter.im/freeswitch-esl-all/community.svg)](https://gitter.im/freeswitch-esl-all/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## 目标

    1、支持连接FreeSWITCH大规模集群
    2、更易于集成使用
    4、与spring boot 2.3.x深度整合，提供 starter
    5、可动态配置

## 模块说明

### 1.[freeswitch-esl](freeswitch-esl/README.md)

    freeswitch esl 客户端(详细信息，移步至子模块）

### 2.[freeswitch-esl-example](freeswitch-esl-example/README.md)

    基于 freeswitch-esl 客户端示例(详细信息，移步至子模块）

### 3.[freeswitch-esl-spring-boot-starter](freeswitch-esl-spring-boot-starter/README.md)

    基于 Spring boot 2.3.x, freeswitch-esl 客户端(详细信息，移步至子模块）
    
    application.properties
    
        link.thingscloud.freeswitch.esl.inbound.servers[0].host=127.0.0.1
        link.thingscloud.freeswitch.esl.inbound.servers[0].port=8021
        link.thingscloud.freeswitch.esl.inbound.servers[0].timeout-seconds=5
        link.thingscloud.freeswitch.esl.inbound.servers[0].password=ClueCon
        link.thingscloud.freeswitch.esl.inbound.servers[2].host=127.0.0.2
        link.thingscloud.freeswitch.esl.inbound.servers[2].port=8021
        link.thingscloud.freeswitch.esl.inbound.servers[2].timeout-seconds=5
        link.thingscloud.freeswitch.esl.inbound.events=CHANNEL_CREATE CHANNEL_DESTORY 
        # 开启性能监控 - 事件驱动-业务逻辑处理时间
        link.thingscloud.freeswitch.esl.inbound.performance=true 
        link.thingscloud.freeswitch.esl.inbound.performanceCostTime=200 
        # 开启事件性能监控 - fs产生事件与应用接收到事件时间差
        link.thingscloud.freeswitch.esl.inbound.eventPerformance=false 
        link.thingscloud.freeswitch.esl.inbound.eventPerformanceCostTime=200 

    
    application.yml
    
        link:
          thingscloud:
            freeswitch:
              esl:
                inbound:
                  defaultPassword: ClueCon
                  performance: false
                  performanceCostTime: 200
                  servers:
                    - host: 127.0.0.1
                      port: 8021
                      timeoutSeconds: 5
                    - host: 127.0.0.2
                    - host: 127.0.0.3
                  events:
                    - CHANNEL_CREATE
                    - CHANNEL_DESTORY

### 4.[freeswitch-esl-spring-boot-starter-example](freeswitch-esl-spring-boot-starter-example/README.md)

    基于 freeswitch-esl-spring-boot-starter 客户端示例(详细信息，移步至子模块）


## 特性说明

    获取实例 
        InboundClient.getInstance()
        SpringBoot容器 : @Autowired inboundClient
    
    可动态配置添加或删除远端地址
        添加远端地址
            a、inboundClient.option().addServerOption(new ServerOption(host, port));
            b、InboundClient.getInstance().option().addServerOption(new ServerOption(host, port));
        
        删除远端地址
            ServerOption serverOption = inboundClient.option().serverOptions().get(0);
            
            a、inboundClient.option().removeServerOption(serverOption);
            b、InboundClient.getInstance().option().removeServerOption(serverOption);
            
    服务端连接监听器 ServerConnectionListener
        inboundClient.option().serverConnectionListener(serverConnectionListenerImpl);
            void onOpened(ServerOption serverOption);
            void onClosed(ServerOption serverOption);
    
    配置动态获取或者删除  
        1、实现接口 InboundClientOptionHandler
        2、继承抽象类 AbstractInboundClientOptionHandler

## 使用须知

    1、发送api建议采用异步操作，特别是originate命令
    2、目前存在消息超时机制，应对服务端直接关机导致连接假死，设置readTimeoutSeconds参数值为0，可关闭此特性
    3、为了提供性能，处理EslEvent时，使用Netty的Worker线程，如果处理逻辑涉及IO或者耗时操作，必须要将处理逻辑放在新线程里面处理，通过disablePublicExecutor参数设置

## 志同道合(微信) - 请备注来源

![微信](https://gitee.com/zhouhailin/images/raw/master/a0eb627b9b6bc1c6da8f19d3292ebce.jpg)

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) Copyright (C) Apache Software Foundation
