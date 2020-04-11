# FreeSWITCH ESL Spring Boot Starter

[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-spring-boot-starter/)

## 配置


    application.properties

    link.thingscloud.freeswitch.esl.inbound.servers[0].host=127.0.0.1
    link.thingscloud.freeswitch.esl.inbound.servers[0].port=8021
    link.thingscloud.freeswitch.esl.inbound.servers[0].timeout-seconds=5
    link.thingscloud.freeswitch.esl.inbound.servers[2].host=127.0.0.2
    link.thingscloud.freeswitch.esl.inbound.servers[2].port=8021
    link.thingscloud.freeswitch.esl.inbound.servers[2].timeout-seconds=5
    link.thingscloud.freeswitch.esl.inbound.events=CHANNEL_CREATE CHANNEL_DESTORY 
    
    
    