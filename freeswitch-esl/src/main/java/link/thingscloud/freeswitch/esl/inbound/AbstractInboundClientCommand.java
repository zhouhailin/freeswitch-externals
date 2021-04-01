package link.thingscloud.freeswitch.esl.inbound;

import link.thingscloud.freeswitch.esl.constant.EslConstant;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.util.StringUtils;

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
     * uuid_answer <uuid>
     *
     * @param addr addr
     * @param uuid leg uuid
     * @return Job UUID
     */
    @Override
    public String answer(String addr, String uuid) {
        String command = "uuid_answer " + uuid;
        if (debugEnabled) {
            log.debug("answer addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * uuid_bridge <uuid> <other_uuid>
     *
     * @param addr      addr
     * @param uuid      leg uuid
     * @param otherUuid other leg uuid
     * @return Job UUID
     */
    @Override
    public String bridge(String addr, String uuid, String otherUuid) {
        String command = "uuid_bridge " + uuid + " " + otherUuid;
        if (debugEnabled) {
            log.debug("bridge addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * uuid_broadcast  <uuid> <path> [aleg|bleg|holdb|both]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param path file path
     * @param smf  swithc media flag : aleg|bleg|holdb|both
     * @return Job UUID
     */
    @Override
    public String broadcast(String addr, String uuid, String path, String smf) {
        String command = "uuid_broadcast " + uuid + " " + path;
        if (StringUtils.inEquals(smf, EslConstant.SMF_ALEG, EslConstant.SMF_BLEG, EslConstant.SMF_HOLDB, EslConstant.SMF_BOTH)) {
            command += " " + smf;
        }
        if (debugEnabled) {
            log.debug("broadcast addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }

    /**
     * uuid_break <uuid> [all]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param all  all
     * @return Job UUID
     */
    @Override
    public String break0(String addr, String uuid, boolean all) {
        String command = "uuid_break " + uuid;
        if (all) {
            command += " all";
        }
        if (debugEnabled) {
            log.debug("break0 addr : {}, command : {}", addr, command);
        }
        return sendAsyncApiCommand(addr, command, EMPTY);
    }
}
