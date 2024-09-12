package link.thingscloud.freeswitch.cdr.util;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>NumberUtil class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
public class NumberUtil {

    private NumberUtil() {
    }

    /**
     * <p>toLong.</p>
     *
     * @param str a {@link String} object.
     * @return a {@link Long} object.
     */
    public static Long toLong(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            log.warn("toLong failure, str : [{}]", str, nfe);
            return null;
        }
    }

    /**
     * <p>toInteger.</p>
     *
     * @param str a {@link String} object.
     * @return a {@link Integer} object.
     */
    public static Integer toInteger(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            log.warn("toInteger failure, str : [{}]", str, nfe);
            return null;
        }
    }
}
