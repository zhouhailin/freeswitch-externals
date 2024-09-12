package link.thingscloud.freeswitch.cdr.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;


/**
 * <p>CdrDecodeUtil class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
public class CdrDecodeUtil {

    private CdrDecodeUtil() {
    }

    /**
     * <p>decode.</p>
     *
     * @param strXml a {@link String} object.
     * @return a {@link String} object.
     */
    public static String decode(String strXml) {
        try {
            return URLDecoder.decode(strXml, "UTF-8");
        } catch (Exception e) {
            log.error("url decode failed, strXml : [{}]", strXml, e);
            return strXml;
        }
    }

    /**
     * <p>decodeLine.</p>
     *
     * @param strXml a {@link String} object.
     * @return a {@link String} object.
     */
    public static String decodeLine(String strXml) {
        String[] strs = StringUtils.split(strXml, StringUtils.LF);
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(decode(str)).append(StringUtils.LF);
        }
        return sb.toString();
    }
}
