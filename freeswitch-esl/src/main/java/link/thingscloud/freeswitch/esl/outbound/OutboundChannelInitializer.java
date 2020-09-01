package link.thingscloud.freeswitch.esl.outbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import link.thingscloud.freeswitch.esl.transport.message.EslFrameDecoder;

import java.util.concurrent.ExecutorService;

public class OutboundChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final IClientHandlerFactory factory;
    private final ExecutorService executor;

    public OutboundChannelInitializer(IClientHandlerFactory factory,
                                      ExecutorService executor) {
        this.factory = factory; this.executor = executor;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Add the text line codec combination first
        pipeline.addLast("encoder", new StringEncoder());
        // Note that outbound mode requires the decoder to treat many 'headers' as body lines
        pipeline.addLast("decoder", new EslFrameDecoder(8092, true));

        // now the outbound client logic
        pipeline.addLast("clientHandler",
                new OutboundClientHandler(factory.createClientHandler(), executor));
    }
}
