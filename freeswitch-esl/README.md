# FreeSWITCH ESL

[![Jdk Version](https://img.shields.io/badge/JDK-17-green.svg)](https://img.shields.io/badge/JDK-17-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/link.thingscloud/freeswitch-esl)](https://mvnrepository.com/artifact/link.thingscloud/freeswitch-esl)

    FreeSWITCH Event Socket 客户端，基于 Netty 4.1.x

## InboundClient

    inboundClient : 单例对象
    
    可以动态添加服务端或删除服务端，InboundClient感知，进行连接或者断开连接

## 目标

    目标：支持单个InboundClient实例连接10000台以上的FreeSWITCH

## 性能

    暂未计划

## 创建 InboundClient

    // 创建 InboundClient 配置信息对象
    InboundClientOption option = new InboundClientOption();
    
    // 设置 默认 ESL 密码, ServerOption 未设置密码，采用此默认值
    option.defaultPassword("ClueCon")
            .addServerOption(new ServerOption("127.0.0.1", 8021));
    // 设置 ESL 订阅事件
    option.addEvents("all");

    // 设置 ESL 事件监听器
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
    
    // 创建 InboundClient 实列对象
    InboundClient inboundClient = InboundClient.newInstance(option);
    
    // 启动客户端
    inboundClient.start();
    
    // 关闭客户端 - 注：关闭后不支持再次 start, 一般伴随JVM关闭而关闭
    inboundClient.shutdown();

## 获取 InboundClient

    // 获取 InboundClient 实例
    InboundClient inboundClient = InboundClient.getInstance();

## InboundClient FreeSWITCH API

    InboundClient.getBootstrap()
    SpringBoot容器 : @Autowired InboundClientBootstrap bootstrap
