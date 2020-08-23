package link.thingscloud.freeswitch.esl.outbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.transport.message.EslFrameDecoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OutboundChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final InboundClientOption option;
    private final IClientHandlerFactory clientHandlerFactory;
    private ExecutorService callbackExecutor = Executors.newSingleThreadExecutor();

    public OutboundChannelInitializer(InboundClientOption option, IClientHandlerFactory clientHandlerFactory) {
        this.option = option; this.clientHandlerFactory = clientHandlerFactory;
    }

    public OutboundChannelInitializer setCallbackExecutor(ExecutorService callbackExecutor) {
        this.callbackExecutor = callbackExecutor;
        return this;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Add the text line codec combination first
        pipeline.addLast("encoder", new StringEncoder());
        // Note that outbound mode requires the decoder to treat many 'headers' as body lines
        pipeline.addLast("decoder", new EslFrameDecoder(8092, true));
        if (option.readerIdleTimeSeconds() > 0 && option.readTimeoutSeconds() > 0
                && option.readerIdleTimeSeconds() < option.readTimeoutSeconds()) {
            pipeline.addLast("idleState", new IdleStateHandler(option.readerIdleTimeSeconds(), 0, 0));
            pipeline.addLast("readTimeout", new ReadTimeoutHandler(option.readTimeoutSeconds()));
        }

        // now the outbound client logic
        pipeline.addLast("clientHandler",
                new OutboundClientHandler(
                        clientHandlerFactory.createClientHandler(),
                        callbackExecutor));
    }
}
