package link.thingscloud.freeswitch.esl.inbound;

import link.thingscloud.freeswitch.esl.builder.Command;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;

import java.util.List;
import java.util.Map;

import static link.thingscloud.freeswitch.esl.builder.Command.*;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
abstract class AbstractInboundClientCommand extends AbstractInboundClient {

    private static final String EMPTY = "";

    private final boolean debugEnabled = log.isDebugEnabled();

    AbstractInboundClientCommand(InboundClientOption option) {
        super(option);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_answer <uuid>
     */
    @Override
    public String answer(String addr, String uuid) {
        String command = Command.cmd(UUID_ANSWER).arg(uuid).toString();
        if (debugEnabled) {
            log.debug("answer addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_bridge <uuid> <other_uuid>
     */
    @Override
    public String bridge(String addr, String uuid, String otherUuid) {
        String command = Command.cmd(UUID_BRIDGE).arg(uuid).arg(otherUuid).toString();
        if (debugEnabled) {
            log.debug("bridge addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_broadcast  <uuid> <path> [aleg|bleg|holdb|both]
     */
    @Override
    public String broadcast(String addr, String uuid, String path, String smf) {
        String command = Command.cmd(UUID_BROADCAST).arg(uuid).arg(path).arg(smf).toString();
        if (debugEnabled) {
            log.debug("broadcast addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_break <uuid> [all]
     */
    @Override
    public String break0(String addr, String uuid, boolean all) {
        String command = Command.cmd(UUID_BREAK).arg(uuid).arg(all ? "all" : null).toString();
        if (debugEnabled) {
            log.debug("break0 addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_hold [off|toggle] <uuid> [<display>]
     */
    @Override
    public String hold(String addr, String smf, String uuid, boolean display) {
        String command = Command.cmd(UUID_HOLD).arg(smf).arg(uuid).arg(display ? "all" : EMPTY).toString();
        if (debugEnabled) {
            log.debug("hold addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_getvar <uuid> <var>
     */
    @Override
    public List<String> getVar(String addr, String uuid, String var) {
        String command = Command.cmd(UUID_GETVAR).arg(uuid).arg(var).toString();
        if (debugEnabled) {
            log.debug("getVar addr : {}, command : {}", addr, command);
        }
        return sendSyncApiCommand(addr, command, EMPTY).getBodyLines();
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_setvar <uuid> <var> [value]
     */
    @Override
    public String setVar(String addr, String uuid, String var, String val) {
        String command = Command.cmd(UUID_SETVAR).arg(uuid).arg(var).arg(val).toString();
        if (debugEnabled) {
            log.debug("setVar addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_setvar_multi <uuid> <var>=<value>;<var>=<value>...
     */
    @Override
    public String multiSetVar(String addr, String uuid, Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return EMPTY;
        }
        StringBuilder command = new StringBuilder("uuid_setvar_multi " + uuid + " ");
        map.forEach((key, value) -> command.append(key).append("=").append(value).append(";"));
        command.deleteCharAt(command.length() - 1);
        if (debugEnabled) {
            log.debug("multiSetVar addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command.toString(), EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_record <uuid> [start|stop|mask|unmask] <path> [<limit>]
     */
    @Override
    public String record(String addr, String uuid, String action, String path, int limit) {
        String command = Command.cmd(UUID_RECORD).arg(uuid).arg(action).arg(path).arg(limit < 1 ? null : String.valueOf(limit)).toString();
        if (debugEnabled) {
            log.debug("record addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * uuid_transfer <uuid> [-bleg|-both] <dest-exten> [<dialplan>] [<context>]
     */
    @Override
    public String transfer(String addr, String uuid, String smf, String dest, String dialplan, String context) {
        String command = Command.cmd(UUID_TRANSFER).arg(uuid).arg(smf).arg(dest).arg(dialplan).arg(context).toString();
        if (debugEnabled) {
            log.debug("transfer addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }
}
