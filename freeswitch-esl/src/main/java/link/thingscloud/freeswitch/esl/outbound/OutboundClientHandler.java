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

import io.netty.channel.ChannelHandlerContext;
import link.thingscloud.freeswitch.esl.internal.AbstractEslClientHandler;
import link.thingscloud.freeswitch.esl.internal.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;

import java.util.concurrent.ExecutorService;

class OutboundClientHandler extends AbstractEslClientHandler {

	private final IClientHandler clientHandler;
	private final ExecutorService callbackExecutor;

	public OutboundClientHandler(IClientHandler clientHandler, ExecutorService callbackExecutor) {
		this.clientHandler = clientHandler;
		this.callbackExecutor = callbackExecutor;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);

		// Have received a connection from FreeSWITCH server, send connect response
		log.debug("Received new connection from server, sending connect message");

		callbackExecutor.execute(()->{
			try {
				EslMessage response = sendApiSingleLineCommand(ctx.channel(), "connect");
				clientHandler.onConnect(new Context(ctx.channel(), OutboundClientHandler.this),
						new EslEvent(response, true));
			} catch (Exception e) {
				ctx.channel().close();
				handleDisconnectionNotice();
			}
		});
//		sendApiSingleLineCommand(ctx.channel(), "connect")
//				.thenAccept(response -> clientHandler.onConnect(
//						new Context(ctx.channel(), OutboundClientHandler.this),
//						new EslEvent(response, true)))
//				.exceptionally(throwable -> {
//					ctx.channel().close();
//					handleDisconnectionNotice();
//					return null;
//				});
	}

	@Override
	protected void handleEslEvent(final ChannelHandlerContext ctx, final EslEvent event) {
		callbackExecutor.execute(() -> clientHandler.onEslEvent(
				new Context(ctx.channel(), OutboundClientHandler.this), event));
	}

	@Override
	protected void handleAuthRequest(ChannelHandlerContext ctx) {
		// This should not happen in outbound mode
		log.warn("Auth request received in outbound mode, ignoring");
	}

	@Override
	protected void handleDisconnectionNotice() {
		log.debug("Received disconnection notice");
	}
}
