# FreeSWITCH ESL ALL

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/23fb13f7487f4ccd985f09c96341dfab)](https://app.codacy.com/gh/zhouhailin/freeswitch-esl-all?utm_source=github.com&utm_medium=referral&utm_content=zhouhailin/freeswitch-esl-all&utm_campaign=Badge_Grade_Settings)
[![Jdk Version](https://img.shields.io/badge/JDK-8-green.svg)](https://img.shields.io/badge/JDK-8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/link.thingscloud/freeswitch-esl-all)](https://mvnrepository.com/artifact/link.thingscloud/freeswitch-esl-all)
[![Gitter](https://badges.gitter.im/freeswitch-esl-all/community.svg)](https://gitter.im/freeswitch-esl-all/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## Summary

    Support JDK 1.8 / 17+

    Netty used 4.1.x, 5.x is not supported

## Features

    1. Support to connect FreeSWITCH Cluster
    2. Easy to integrate with FreeSWITCH ESL
    3. Support spring boot starter
    4. Support to dynamic add or remove remote server

## Maven

    <dependency>
        <groupId>link.thingscloud</groupId>
        <artifactId>freeswitch-esl</artifactId>
        <version>${freeswitch-esl.version}</version>
    </dependency>

## Quick Start

### freeswitch-esl-spring-boot-starter

[Maven Central](https://mvnrepository.com/artifact/link.thingscloud/freeswitch-esl-spring-boot-starter)

```xml

<dependency>
    <groupId>link.thingscloud</groupId>
    <artifactId>freeswitch-esl-spring-boot-starter</artifactId>
    <version>${freeswitch-esl.version}</version>
</dependency>
```

application.properties

```properties
link.thingscloud.freeswitch.esl.inbound.servers[0].host=127.0.0.1
link.thingscloud.freeswitch.esl.inbound.servers[0].port=8021
link.thingscloud.freeswitch.esl.inbound.servers[0].timeout-seconds=5
link.thingscloud.freeswitch.esl.inbound.servers[0].password=ClueCon
link.thingscloud.freeswitch.esl.inbound.servers[2].host=127.0.0.2
link.thingscloud.freeswitch.esl.inbound.servers[2].port=8021
link.thingscloud.freeswitch.esl.inbound.servers[2].timeout-seconds=5
link.thingscloud.freeswitch.esl.inbound.events=CHANNEL_CREATE CHANNEL_DESTORY 
# performance monitor - event driven - business logic processing time
link.thingscloud.freeswitch.esl.inbound.performance=true 
link.thingscloud.freeswitch.esl.inbound.performanceCostTime=200 
# event performance monitor - event driven - event generate time and event receive time
link.thingscloud.freeswitch.esl.inbound.eventPerformance=false 
link.thingscloud.freeswitch.esl.inbound.eventPerformanceCostTime=200
```

application.yml

```yaml
link:
  thingscloud:
    freeswitch:
      esl:
        inbound:
          defaultPassword: ClueCon
          performance: false
          performanceCostTime: 200
          #read-timeout-seconds: 0
          servers:
            - host: 127.0.0.1
              port: 8021
              timeoutSeconds: 5
            - host: 127.0.0.2
            - host: 127.0.0.3
          events:
            - CHANNEL_CREATE
            - CHANNEL_DESTORY
```

```java

@Slf4j
@Component
public class ExampleInboundClient {
    @Autowired
    private InboundClient inboundClient;

    @Autowired
    private InboundClientBootstrap inboundClientBootstrap;

    @PostConstruct
    public void startup() {
        System.out.println(inboundClientBootstrap);
    }

}
```

```java

@Slf4j
@Component
public class InboundClientOptionHandlerExample extends AbstractInboundClientOptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void intercept(InboundClientOption inboundClientOption) {
        List<ServerOption> serverOptions = inboundClientOption.serverOptions();
        log.info("serverOptions before : {}", serverOptions);
        serverOptions.clear();
        serverOptions.add(new ServerOption("127.0.0.8", 8021));
        log.info("serverOptions after  : {}", serverOptions);
    }
}
```

```java

@Slf4j
@Component
@EslEventName(EventNames.HEARTBEAT)
public class HeartbeatEslEventHandler implements EslEventHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(String addr, EslEvent event) {
        log.info("HeartbeatEslEventHandler handle addr[{}] EslEvent[{}].", addr, event);
    }
}
```

```java

@Slf4j
@Service
public class ServerConnectionListenerImpl implements ServerConnectionListener {
    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpened(ServerOption serverOption) {
        log.info("onOpened serverOption : {}", serverOption);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClosed(ServerOption serverOption) {
        log.info("onClosed serverOption : {}", serverOption);
    }
}
```

### freeswitch-esl

[Maven Central](https://mvnrepository.com/artifact/link.thingscloud/freeswitch-esl)

```xml

<dependency>
    <groupId>link.thingscloud</groupId>
    <artifactId>freeswitch-esl</artifactId>
    <version>${freeswitch-esl.version}</version>
</dependency>
```

```
InboundClientOption option = new InboundClientOption();

// set default esl password, if ServerOption not set password, use this default value
option.defaultPassword("ClueCon")
    .addServerOption(new ServerOption("127.0.0.1", 8021));

// set esl subscribe events
option.addEvents("all");

// ESL event listener
option.addListener(new IEslEventListener() {
    @Override
    public void eventReceived(String addr, EslEvent event) {
        System.out.println(addr);
        System.out.println(event);
    }

    @Override
    public void backgroundJobResultReceived(String addr, EslEvent event) {
        System.out.println(addr);
        System.out.println(event);
    }
});

option.serverConnectionListener(new ServerConnectionListener() {
    @Override
    public void onOpened(ServerOption serverOption) {
        System.out.println("---onOpened--");
    }

    @Override
    public void onClosed(ServerOption serverOption) {
        System.out.println("---onClosed--");
    }
});

InboundClient inboundClient = InboundClient.newInstance(option);

// start client
inboundClient.start();

// shutdown client, after shutdown, can't start again, shutdown with JVM
inboundClient.shutdown();
```

```
// Get InboundClient Instance
InboundClient inboundClient = InboundClient.getInstance();

// Get InboundClient FreeSWITCH API
InboundClient.getBootstrap()
```

```
// add server option
inboundClient.option().addServerOption(new ServerOption(host, port));
// InboundClient.getInstance().option().addServerOption(new ServerOption(host, port));

// remove server option
ServerOption serverOption = inboundClient.option().serverOptions().get(0);
inboundClient.option().removeServerOption(serverOption);
// InboundClient.getInstance().option().removeServerOption(serverOption);

```

## Star history

[![Star History Chart](https://api.star-history.com/svg?repos=zhouhailin/freeswitch-externals&type=Date)](https://star-history.com/#zhouhailin/freeswitch-externals&Date)

## Notice

    1. Send api is recommended to use asynchronous operation, especially the originate command
    2. There is a message timeout mechanism at present to deal with the false death of the connection caused by the direct shutdown of the server. Set the value of readTimeoutSeconds parameter to 0 to turn off this feature
    3. In order to improve performance, when processing EslEvent, use Netty's worker thread. If the processing logic involves IO or time-consuming operation, the processing logic must be put in a new thread to process, and the disablePublicExecutor parameter is set

## WeChat User Groups - Please note the source

![WeChat](https://gitee.com/zhouhailin/images/raw/master/a0eb627b9b6bc1c6da8f19d3292ebce.jpg)

## LICENSE

[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) Copyright (C) Apache Software Foundation
