/*
 * Copyright 2010 david varnes.
 *
 * Licensed under the Apache License, version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package link.thingscloud.freeswitch.esl.outbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;

/**
 * Entry point to run a socket client that a running FreeSWITCH Event Socket Library module can
 * make outbound connections to.
 * <p/>
 * This class provides for what the FreeSWITCH documentation refers to as 'Outbound' connections
 * from the Event Socket module. That is, with reference to the module running on the FreeSWITCH
 * server, this client accepts an outbound connection from the server module.
 * <p/>
 * See <a href="http://wiki.freeswitch.org/wiki/Mod_event_socket">http://wiki.freeswitch.org/wiki/Mod_event_socket</a>
 */
public class SocketClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final OutboundChannelInitializer channelInitializer;
	private final EventLoopGroup bossGroup;
	private final EventLoopGroup workerGroup;
	private final IClientHandlerFactory factory;
	private final SocketAddress bindAddress;

	private Channel serverChannel;

	public SocketClient(SocketAddress bindAddress,
						IClientHandlerFactory factory,
						ExecutorService executor) {
		this.bindAddress = bindAddress;
		this.factory = factory;
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();
		channelInitializer = new OutboundChannelInitializer(factory, executor);
	}

	public void start() {
		final ServerBootstrap bootstrap = new ServerBootstrap()
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(channelInitializer)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		serverChannel = bootstrap.bind(bindAddress).syncUninterruptibly().channel();
		log.info("SocketClient waiting for connections on [{}] ...", bindAddress);
	}

	public void shutdown() {
		if (null != serverChannel) {
			serverChannel.close().awaitUninterruptibly();
		}
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		log.info("SocketClient stopped");
	}

}
