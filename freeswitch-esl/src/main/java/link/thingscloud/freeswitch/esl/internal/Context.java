package link.thingscloud.freeswitch.esl.internal;

import io.netty.channel.Channel;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;

@Data
public class Context extends Execute {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final AbstractEslClientHandler handler;

    @Setter(AccessLevel.NONE)
    private final Channel channel;

    public Context(Channel channel, AbstractEslClientHandler clientHandler) {
        this.handler = clientHandler;
        this.channel = channel;
    }

    @Override
    public boolean canSend() {
        return channel != null && channel.isActive();
    }

    /**
     * Sends a mod_event_socket command to FreeSWITCH server and blocks, waiting for an immediate response from the
     * server.
     * <p/>
     * The outcome of the command from the server is returned in an {@link EslMessage} object.
     *
     * @param command a mod_event_socket command to send
     * @return an {@link EslMessage} containing command results
     */
    public EslMessage sendCommand(@NotBlank(message = "command cannot be null or empty") String command) {
        final String cmd = command.toLowerCase().trim();
        try {
            return handler.sendApiSingleLineCommand(channel, cmd);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Sends a FreeSWITCH API command to the server and blocks, waiting for an immediate response from the
     * server.
     * <p/>
     * The outcome of the command from the server is returned in an {@link EslMessage} object.
     *
     * @param command API command to send
     * @param arg     command arguments
     * @return an {@link EslMessage} containing command results
     */
    @Override
    public EslMessage sendApiCommand(
            @NotBlank(message = "command cannot be null or empty") String command, String arg) {
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append("api ").append(command);
            if (StringUtils.isNotBlank(arg)) {
                sb.append(' ').append(arg);
            }
            return handler.sendApiSingleLineCommand(channel, sb.toString());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Submit a FreeSWITCH API command to the server to be executed in background mode. A synchronous
     * response from the server provides a UUID to identify the job execution results. When the server
     * has completed the job execution it fires a BACKGROUND_JOB Event with the execution results.<p/>
     * Note that this Client must be subscribed in the normal way to BACKGROUND_JOB Events, in order to
     * receive this event.
     *
     * @param command API command to send
     * @param arg     command arguments
     * @return String Job-UUID that the server will tag result event with.
     */
    @Override
    public CompletableFuture<EslEvent> sendBackgroundApiCommand(
            @NotBlank(message = "command cannot be null or empty") String command, String arg) {
        final StringBuilder sb = new StringBuilder();
        sb.append("bgapi ").append(command);
        if (StringUtils.isNotBlank(arg)) {
            sb.append(' ').append(arg);
        }
        return handler.sendBackgroundApiCommand(channel, sb.toString());
    }

    /**
     * Set the current event subscription for this connection to the server.  Examples of the events
     * argument are:
     * <pre>
     *   ALL
     *   CHANNEL_CREATE CHANNEL_DESTROY HEARTBEAT
     *   CUSTOM conference::maintenance
     *   CHANNEL_CREATE CHANNEL_DESTROY CUSTOM conference::maintenance sofia::register sofia::expire
     * </pre>
     * Subsequent calls to this method replaces any previous subscriptions that were set.
     * </p>
     * Note: current implementation can only process 'plain' events.
     *
     * @param format can be { plain | xml }
     * @param events { all | space separated list of events }
     * @return a {@link CommandResponse} with the server's response.
     */
    @Override
    public CommandResponse setEventSubscriptions(@NotNull EventFormat format, String events) {
        // temporary hack
        if (!format.equals(EventFormat.PLAIN))
            throw new IllegalStateException("Only 'plain' event format is supported at present");
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append("event ").append(format.toString());
            if (StringUtils.isNotBlank(events)) {
                sb.append(' ').append(events);
            }
            EslMessage response = handler.sendApiSingleLineCommand(channel, sb.toString());
            return new CommandResponse(sb.toString(), response);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Cancel any existing event subscription.
     *
     * @return a {@link CommandResponse} with the server's response.
     */
    @Override
    public CommandResponse cancelEventSubscriptions() {
        try {
            EslMessage response = handler.sendApiSingleLineCommand(channel, "noevents");
            return new CommandResponse("noevents", response);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Add an event filter to the current set of event filters on this connection. Any of the event headers
     * can be used as a filter.
     * </p>
     * Note that event filters follow 'filter-in' semantics. That is, when a filter is applied
     * only the filtered values will be received. Multiple filters can be added to the current
     * connection.
     * </p>
     * Example filters:
     * <pre>
     *    eventHeader        valueToFilter
     *    ----------------------------------
     *    Event-Name         CHANNEL_EXECUTE
     *    Channel-State      CS_NEW
     * </pre>
     *
     * @param eventHeader   to filter on
     * @param valueToFilter the value to match
     * @return a {@link CommandResponse} with the server's response.
     */
    @Override
    public CommandResponse addEventFilter(
            @NotBlank(message = "eventHeader cannot be null or empty") String eventHeader, String valueToFilter) {
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append("filter ").append(eventHeader);
            if (StringUtils.isNotBlank(valueToFilter)) {
                sb.append(' ').append(valueToFilter);
            }
            EslMessage response = handler.sendApiSingleLineCommand(channel, sb.toString());
            return new CommandResponse(sb.toString(), response);

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Delete an event filter from the current set of event filters on this connection.  See
     *
     * @param eventHeader   to remove
     * @param valueToFilter to remove
     * @return a {@link CommandResponse} with the server's response.
     */
    @Override
    public CommandResponse deleteEventFilter(
            @NotBlank(message = "eventHeader cannot be null or empty") String eventHeader, String valueToFilter) {
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append("filter delete ").append(eventHeader);
            if (StringUtils.isNotBlank(valueToFilter)) {
                sb.append(' ').append(valueToFilter);
            }

            EslMessage response = handler.sendApiSingleLineCommand(channel, sb.toString());
            return new CommandResponse(sb.toString(), response);

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Send a {@link SendMsg} command to FreeSWITCH.  This client requires that the {@link SendMsg}
     * has a call UUID parameter.
     *
     * @param sendMsg a {@link SendMsg} with call UUID
     * @return a {@link CommandResponse} with the server's response.
     */
    @Override
    public CommandResponse sendMessage(@NotNull(message = "sendMsg cannot be null") SendMsg sendMsg) {
        try {
            EslMessage response = handler.sendApiMultiLineCommand(channel, sendMsg.getMsgLines());
            return new CommandResponse(sendMsg.toString(), response);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

    }

    /**
     * Enable log output.
     *
     * @param level using the same values as in console.conf
     * @return a {@link CommandResponse} with the server's response.
     */
    @Override
    public CommandResponse setLoggingLevel(@NotNull LoggingLevel level) {
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append("log ").append(level.toString());

            EslMessage response = handler.sendApiSingleLineCommand(channel, sb.toString());
            return new CommandResponse(sb.toString(), response);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Disable any logging previously enabled with setLogLevel().
     *
     * @return a {@link CommandResponse} with the server's response.
     */
    @Override
    public CommandResponse cancelLogging() {

        try {
            EslMessage response = handler.sendApiSingleLineCommand(channel, "nolog");
            return new CommandResponse("nolog", response);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void closeChannel() {
        try {
            if(channel != null && channel.isOpen())
                channel.close();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
