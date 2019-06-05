# FreeSWITCH ESL ALL

[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-all/badge.svg)](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-all/)

## 目标

    1、支持连接FreeSWITCH大规模集群
    2、更易于集成使用
    4、与spring boot 2.1.x深度整合，提供 starter

## 模块说明

### 1.[freeswitch-esl](freeswitch-esl/README.md)

    freeswitch esl 客户端

### 2.[freeswitch-esl-example](freeswitch-esl-example/README.md)

    基于 freeswitch-esl 客户端示例

### 3.[freeswitch-esl-spring-boot-starter](freeswitch-esl-spring-boot-starter/README.md)

    基于 Spring boot 2.1.x, freeswitch-esl 客户端

### 4.[freeswitch-esl-spring-boot-starter-example](freeswitch-esl-spring-boot-starter-example/README.md)

    基于 freeswitch-esl-spring-boot-starter 客户端示例

## TODO

    回调事件后，调用命令时 省略addr参数，构建 ThreadLocal AddrContext 上下文获取addr
    
## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) Copyright (C) Apache Software Foundation
