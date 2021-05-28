package link.thingscloud.freeswitch.esl.builder;

import link.thingscloud.freeswitch.esl.util.StringUtils;

/**
 * <p>Command class.</p>
 *
 * @author zhouhailin
 * @version $Id: $Id
 * @since 1.6.0
 */
public class Command {

    /**
     * Constant <code>UUID_ANSWER="uuid_answer"</code>
     */
    public static final String UUID_ANSWER = "uuid_answer";
    /**
     * Constant <code>UUID_BRIDGE="uuid_bridge"</code>
     */
    public static final String UUID_BRIDGE = "uuid_bridge";
    /**
     * Constant <code>UUID_BROADCAST="uuid_broadcast"</code>
     */
    public static final String UUID_BROADCAST = "uuid_broadcast";
    /**
     * Constant <code>UUID_BREAK="uuid_break"</code>
     */
    public static final String UUID_BREAK = "uuid_break";
    /**
     * Constant <code>UUID_HOLD="uuid_hold"</code>
     */
    public static final String UUID_HOLD = "uuid_hold";
    /**
     * Constant <code>UUID_GETVAR="uuid_getvar"</code>
     */
    public static final String UUID_GETVAR = "uuid_getvar";
    /**
     * Constant <code>UUID_SETVAR="uuid_setvar"</code>
     */
    public static final String UUID_SETVAR = "uuid_setvar";
    /**
     * Constant <code>UUID_SETVAR_MULTI="uuid_setvar_multi"</code>
     */
    public static final String UUID_SETVAR_MULTI = "uuid_setvar_multi";
    /**
     * Constant <code>UUID_RECORD="uuid_record"</code>
     */
    public static final String UUID_RECORD = "uuid_record";
    /**
     * Constant <code>UUID_TRANSFER="uuid_transfer"</code>
     */
    public static final String UUID_TRANSFER = "uuid_transfer";

    private final StringBuilder builder = new StringBuilder();

    /**
     * <p>cmd.</p>
     *
     * @param cmd a {@link java.lang.String} object.
     * @return a {@link link.thingscloud.freeswitch.esl.builder.Command} object.
     */
    public static Command cmd(String cmd) {
        return new Command().arg(cmd);
    }

    /**
     * <p>arg.</p>
     *
     * @param arg a {@link java.lang.String} object.
     * @return a {@link link.thingscloud.freeswitch.esl.builder.Command} object.
     */
    public Command arg(String arg) {
        if (arg == null || arg.isEmpty()) {
            return this;
        }
        builder.append(arg).append(StringUtils.BLANK);
        return this;
    }

    /**
     * <p>arg.</p>
     *
     * @param arg a int.
     * @return a {@link link.thingscloud.freeswitch.esl.builder.Command} object.
     */
    public Command arg(int arg) {
        builder.append(arg).append(StringUtils.BLANK);
        return this;
    }

    /**
     * <p>arg.</p>
     *
     * @param arg a boolean.
     * @return a {@link link.thingscloud.freeswitch.esl.builder.Command} object.
     */
    public Command arg(boolean arg) {
        builder.append(arg).append(StringUtils.BLANK);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
