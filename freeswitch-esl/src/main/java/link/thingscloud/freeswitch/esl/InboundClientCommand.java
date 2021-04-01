package link.thingscloud.freeswitch.esl;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
public interface InboundClientCommand {

    /**
     * uuid_answer <uuid>
     *
     * @param addr addr
     * @param uuid leg uuid
     * @return Job UUID
     */
    String answer(String addr, String uuid);

    /**
     * uuid_bridge <uuid> <other_uuid>
     *
     * @param addr      addr
     * @param uuid      leg uuid
     * @param otherUuid other leg uuid
     * @return Job UUID
     */
    String bridge(String addr, String uuid, String otherUuid);

    /**
     * uuid_broadcast  <uuid> <path> [aleg|bleg|holdb|both]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param path file path
     * @param smf  swithc media flag : aleg|bleg|holdb|both
     * @return Job UUID
     */
    String broadcast(String addr, String uuid, String path, String smf);

    /**
     * uuid_break <uuid> [all]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param all  false
     * @return Job UUID
     */
    String break0(String addr, String uuid, boolean all);

}
