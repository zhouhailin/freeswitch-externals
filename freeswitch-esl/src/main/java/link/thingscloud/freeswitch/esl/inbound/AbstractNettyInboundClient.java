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

package link.thingscloud.freeswitch.esl.inbound;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import link.thingscloud.freeswitch.esl.InboundClientService;
import link.thingscloud.freeswitch.esl.inbound.handler.InboundChannelHandler;
import link.thingscloud.freeswitch.esl.inbound.listener.ChannelEventListener;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.transport.message.EslFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
abstract class AbstractNettyInboundClient implements ChannelEventListener, InboundClientService {

    final Bootstrap bootstrap;
    final EventLoopGroup workerGroup;
    final ExecutorService publicExecutor;
    final ExecutorService privateExecutor;

    final InboundClientOption option;

    final Logger log = LoggerFactory.getLogger(getClass());

    AbstractNettyInboundClient(InboundClientOption option) {
        this.option = option;

        bootstrap = new Bootstrap();

        publicExecutor = new ScheduledThreadPoolExecutor(option.publicExecutorThread(),
                new DefaultThreadFactory("publicExecutor", true));
        privateExecutor = new ScheduledThreadPoolExecutor(option.privateExecutorThread(),
                new DefaultThreadFactory("privateExecutor", true));

        workerGroup = new NioEventLoopGroup(option.workerGroupThread());
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.SO_SNDBUF, option.sndBufSize())
                .option(ChannelOption.SO_RCVBUF, option.rcvBufSize())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast("decoder", new EslFrameDecoder(8192));
                        if (option.readerIdleTimeSeconds() > 0 && option.readTimeoutSeconds() > 0
                                && option.readerIdleTimeSeconds() < option.readTimeoutSeconds()) {
                            pipeline.addLast("idleState", new IdleStateHandler(option.readerIdleTimeSeconds(), 0, 0));
                            pipeline.addLast("readTimeout", new ReadTimeoutHandler(option.readTimeoutSeconds()));
                        }
                        // now the inbound client logic
                        pipeline.addLast("clientHandler", new InboundChannelHandler(AbstractNettyInboundClient.this, publicExecutor, privateExecutor, option.disablePublicExecutor()));
                    }
                });
    }

}
