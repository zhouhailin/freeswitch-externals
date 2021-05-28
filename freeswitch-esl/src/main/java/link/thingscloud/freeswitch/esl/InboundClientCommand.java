package link.thingscloud.freeswitch.esl;

import java.util.List;
import java.util.Map;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
public interface InboundClientCommand {

    /**
     * uuid_answer &lt;uuid&gt;
     *
     * @param addr addr
     * @param uuid leg uuid
     * @return Job UUID
     */
    String answer(String addr, String uuid);

    /**
     * uuid_bridge &lt;uuid&gt; &lt;other_uuid&gt;
     *
     * @param addr      addr
     * @param uuid      leg uuid
     * @param otherUuid other leg uuid
     * @return Job UUID
     */
    String bridge(String addr, String uuid, String otherUuid);

    /**
     * uuid_broadcast  &lt;uuid&gt; &lt;path&gt; [aleg|bleg|holdb|both]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param path file path
     * @param smf  swithc media flag : aleg|bleg|holdb|both
     * @return Job UUID
     */
    String broadcast(String addr, String uuid, String path, String smf);

    /**
     * uuid_break &lt;uuid&gt; [all]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @return Job UUID
     */
    default String break0(String addr, String uuid) {
        return break0(addr, uuid, false);
    }

    /**
     * uuid_break &lt;uuid&gt; [all]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param all  false
     * @return Job UUID
     */
    String break0(String addr, String uuid, boolean all);

    /**
     * uuid_hold [off|toggle] &lt;uuid&gt; [&lt;display&gt;]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @return Job UUID
     */
    default String hold(String addr, String uuid) {
        return hold(addr, "off", uuid, false);
    }

    /**
     * uuid_hold [off|toggle] &lt;uuid&gt; [&lt;display&gt;]
     *
     * @param addr    addr
     * @param smf     off|toggle
     * @param uuid    leg uuid
     * @param display false
     * @return Job UUID
     */
    String hold(String addr, String smf, String uuid, boolean display);

    /**
     * uuid_getvar &lt;uuid&gt; &lt;var&gt;
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param var  变量名
     * @return Job UUID
     */
    List<String> getVar(String addr, String uuid, String var);

    /**
     * uuid_setvar &lt;uuid&gt; &lt;var&gt; [value]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param var  变量名
     * @param val  变量值, 为空时则删除该变量
     * @return Job UUID
     */
    String setVar(String addr, String uuid, String var, String val);

    /**
     * uuid_setvar_multi &lt;uuid&gt; &lt;var&gt;=&lt;value&gt;;&lt;var&gt;=&lt;value&gt;...
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param map  键值对集合
     * @return Job UUID
     */
    String multiSetVar(String addr, String uuid, Map<String, String> map);

    /**
     * uuid_record &lt;uuid&gt; [start|stop|mask|unmask] &lt;path&gt; [&lt;limit&gt;]
     *
     * @param addr   addr
     * @param uuid   leg uuid
     * @param action start|stop|mask|unmask
     * @param path   录音路径
     * @param limit  limit
     * @return Job UUID
     */
    String record(String addr, String uuid, String action, String path, int limit);

    /**
     * uuid_transfer &lt;uuid&gt; [-bleg|-both] &lt;dest-exten&gt; [&lt;dialplan&gt;] [&lt;context&gt;]
     *
     * @param addr addr
     * @param uuid leg uuid
     * @param dest dest extension
     * @return Job UUID
     */
    default String transfer(String addr, String uuid, String dest) {
        return transfer(addr, uuid, null, dest, null, null);
    }

    /**
     * uuid_transfer &lt;uuid&gt; [-bleg|-both] &lt;dest-exten&gt; [&lt;dialplan&gt;] [&lt;context&gt;]
     *
     * @param addr     addr
     * @param uuid     leg uuid
     * @param smf      [-bleg|-both]
     * @param dest     dest extension
     * @param dialplan XML
     * @param context  dialplan context name
     * @return Job UUID
     */
    String transfer(String addr, String uuid, String smf, String dest, String dialplan, String context);

}
