# FreeSWITCH ESL Spring Boot Starter

[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-spring-boot-starter/)

## 配置

    具体详细配置见：InboundClientProperties.java

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
              events:
                - ALL
