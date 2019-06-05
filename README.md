# FreeSWITCH ESL

## 目标

    1、支持连接FreeSWITCH大规模集群
    2、更易于集成使用
    4、与spring boot 2.1.x深度整合，提供 starter

## 模块说明

### 1.freeswitch-esl

    freeswitch esl 客户端

### 2.freeswitch-esl-example

    基于 freeswitch-esl 客户端示例

### 3.freeswitch-esl-spring-boot-starter

    基于 Spring boot 2.1.x, freeswitch-esl 客户端

### 4.freeswitch-esl-spring-boot-starter-example

    基于 freeswitch-esl-spring-boot-starter 客户端示例

## TODO

    回调事件后，调用命令时 省略addr参数，构建 ThreadLocal AddrContext 上下文获取addr