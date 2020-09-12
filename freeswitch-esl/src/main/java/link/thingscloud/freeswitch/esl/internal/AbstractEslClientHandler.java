package link.thingscloud.freeswitch.esl.internal;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.event.EslEventHeaderNames;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractEslClientHandler extends SimpleChannelInboundHandler<EslMessage> {

    public static final String MESSAGE_TERMINATOR = "\n\n";
    public static final String LINE_TERMINATOR = "\n";

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    // used to preserve association between adding future to queue and sending message on channel
    private final ReentrantLock syncLock = new ReentrantLock();
    private final Queue<SyncCallback> syncCallbacks = new ConcurrentLinkedQueue<>();

    private final Map<String, CompletableFuture<EslEvent>> backgroundJobs = new ConcurrentHashMap<>();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        for (final CompletableFuture<EslEvent> backgroundJob : backgroundJobs.values()) {
            backgroundJob.completeExceptionally(e.getCause());
        }

        ctx.close();

        ctx.fireExceptionCaught(e);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EslMessage message) throws Exception {
        final String contentType = message.getContentType();
        if (contentType.equals(EslHeaders.Value.TEXT_EVENT_PLAIN) ||
                contentType.equals(EslHeaders.Value.TEXT_EVENT_XML)) {
            //  transform into an event
            final EslEvent eslEvent = new EslEvent(message);
            if (eslEvent.getEventName().equals("BACKGROUND_JOB")) {
                final String backgroundUuid = eslEvent.getEventHeaders().get(EslEventHeaderNames.JOB_UUID);
                final CompletableFuture<EslEvent> future = backgroundJobs.remove(backgroundUuid);
                if (null != future) future.complete(eslEvent);
            } else {
                handleEslEvent(ctx, eslEvent);
            }
        } else {
            handleEslMessage(ctx, message);
        }
    }

    protected void handleEslMessage(ChannelHandlerContext ctx, EslMessage message) {
        log.info("Received message: [{}]", message);
        final String contentType = message.getContentType();

        switch (contentType) {
            case EslHeaders.Value.API_RESPONSE:
                log.debug("Api response received [{}]", message);
                Objects.requireNonNull(syncCallbacks.poll()).handle(message);
                break;

            case EslHeaders.Value.COMMAND_REPLY:
                log.debug("Command reply received [{}]", message);
                Objects.requireNonNull(syncCallbacks.poll()).handle(message);
                break;

            case EslHeaders.Value.AUTH_REQUEST:
                log.debug("Auth request received [{}]", message);
                handleAuthRequest(ctx);
                break;

            case EslHeaders.Value.TEXT_DISCONNECT_NOTICE:
                log.debug("Disconnect notice received [{}]", message);
                handleDisconnectionNotice();
                break;

            default:
                log.warn("Unexpected message content type [{}]", contentType);
                break;
        }
    }

    /**
     * Synthesise a synchronous command/response by creating a callback object which is placed in
     * queue and blocks waiting for another IO thread to process an incoming {@link EslMessage} and
     * attach it to the callback.
     *
     * @param channel
     * @param command single string to send
     * @return the {@link EslMessage} attached to this command's callback
     */
    public EslMessage sendApiSingleLineCommand(Channel channel, final String command) {
        if (!channel.isActive())
            throw new ChannelException();
        SyncCallback callback = new SyncCallback();
        try {
            syncLock.lock();
            syncCallbacks.add(callback);
            channel.writeAndFlush(command + MESSAGE_TERMINATOR);
        } finally {
            syncLock.unlock();
        }

        //  Block until the response is available
        return callback.get();
    }

    /**
     * Sends a FreeSWITCH API command to the channel and blocks, waiting for an immediate response from the
     * server.
     * <p/>
     * The outcome of the command from the server is returned in an {@link EslMessage} object.
     *
     * @param channel
     * @param command API command to send
     * @param arg     command arguments
     * @return an {@link EslMessage} containing command results
     */
    public EslMessage sendSyncApiCommand(
            @NotBlank(message = "command may not be null or empty") Channel channel,
            @NotBlank(message = "arg may not be null or empty") String command, String arg) {
        return sendApiSingleLineCommand(channel, "api " + command + ' ' + arg);
    }

    /**
     * Synthesise a synchronous command/response by creating a callback object which is placed in
     * queue and blocks waiting for another IO thread to process an incoming {@link EslMessage} and
     * attach it to the callback.
     *
     * @param channel
     * @return the {@link EslMessage} attached to this command's callback
     */
    public EslMessage sendApiMultiLineCommand(Channel channel, final List<String> commandLines) {
        //  Build command with double line terminator at the end
        final StringBuilder sb = new StringBuilder();
        for (final String line : commandLines) {
            sb.append(line);
            sb.append(LINE_TERMINATOR);
        }
        sb.append(LINE_TERMINATOR);
        if (!channel.isActive())
            throw new ChannelException();
        SyncCallback callback = new SyncCallback();
        try {
            syncLock.lock();
            syncCallbacks.add(callback);
            channel.write(sb.toString());
            channel.flush();
        } finally {
            syncLock.unlock();
        }

        //  Block until the response is available
        return callback.get();
    }

    /**
     * Returns the Job UUID of that the response event will have.
     *
     * @param channel
     * @param command
     * @return Job-UUID as a string
     */
    public CompletableFuture<EslEvent> sendBackgroundApiCommand(Channel channel, final String command) {
        EslMessage result = sendApiSingleLineCommand(channel, command);
        if (!result.hasHeader(EslHeaders.Name.JOB_UUID))
            throw new IllegalStateException("Missing Job-UUID header in bgapi response");
        final String jobId = result.getHeaderValue(EslHeaders.Name.JOB_UUID);
        final CompletableFuture<EslEvent> resultFuture = new CompletableFuture<>();
        backgroundJobs.put(jobId, resultFuture); return resultFuture;
    }

    protected abstract void handleEslEvent(ChannelHandlerContext ctx, EslEvent event);

    protected abstract void handleAuthRequest(ChannelHandlerContext ctx);

    protected abstract void handleDisconnectionNotice();
}
