package link.thingscloud.freeswitch.esl.builder;

import link.thingscloud.freeswitch.esl.util.StringUtils;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
public class Command {

    public static final String UUID_ANSWER = "uuid_answer";
    public static final String UUID_BRIDGE = "uuid_bridge";
    public static final String UUID_BROADCAST = "uuid_broadcast";
    public static final String UUID_BREAK = "uuid_break";
    public static final String UUID_HOLD = "uuid_hold";
    public static final String UUID_GETVAR = "uuid_getvar";
    public static final String UUID_SETVAR = "uuid_setvar";
    public static final String UUID_SETVAR_MULTI = "uuid_setvar_multi";
    public static final String UUID_RECORD = "uuid_record";
    public static final String UUID_TRANSFER = "uuid_transfer";

    private final StringBuilder builder = new StringBuilder();

    public static Command cmd(String cmd) {
        return new Command().arg(cmd);
    }

    public Command arg(String arg) {
        if (arg == null || arg.isEmpty()) {
            return this;
        }
        builder.append(arg).append(StringUtils.BLANK);
        return this;
    }

    public Command arg(int arg) {
        builder.append(arg).append(StringUtils.BLANK);
        return this;
    }

    public Command arg(boolean arg) {
        builder.append(arg).append(StringUtils.BLANK);
        return this;
    }

    @Override
    public String toString() {
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
