package link.thingscloud.freeswitch.cdr.common;

import link.thingscloud.freeswitch.cdr.domain.Cdr;
import link.thingscloud.freeswitch.cdr.util.CdrDecodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * <p>CdrHelper class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
public class CdrHelper {

    private static final String SIP_H_USER_TO_USER = "sip_h_User-to-User";


    private CdrHelper() {
    }

    /**
     * 获取 AVAYA UUI 随路数据
     * <p>
     * 04FA08006424BC5CF89291C808
     * 00FA080064006F5CFF915E
     * <p>
     * &lt;sip_h_User-to-User&gt;00FA080064006F5CFF915E;encoding=hex&lt;/sip_h_User-to-User&gt;
     * &lt;sip_h_User-to-User&gt;00FA080064007C5CFF92E2%3Bencoding%3Dhex&lt;/sip_h_User-to-User&gt;
     *
     * @param cdr a {@link Cdr} object.
     * @return a {@link String} object.
     */
    public static ImmutablePair<String, String> getUserData(Cdr cdr) {
        String userToUser = cdr.getVariables().getVariableTable().get(SIP_H_USER_TO_USER);
        if (StringUtils.isBlank(userToUser)) {
            return new ImmutablePair<>(EMPTY, EMPTY);
        }

        try {
            return AvayaHelper.decode(userToUser);
        } catch (Exception e) {
            log.warn("getUserData decodeHex userToUser : [{}]", userToUser, e);
            return new ImmutablePair<>(EMPTY, EMPTY);
        }
    }


    /**
     * <p>decode.</p>
     *
     * @param str a {@link String} object.
     * @return a {@link String} object.
     */
    public static String decode(String str) {
        return CdrDecodeUtil.decode(str);
    }


}
