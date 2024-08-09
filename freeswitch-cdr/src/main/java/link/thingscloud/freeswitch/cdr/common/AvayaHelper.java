package link.thingscloud.freeswitch.cdr.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.IllegalFormatFlagsException;

/**
 * <p>AvayaHelper class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
public class AvayaHelper {

    private static final String EMPTY = "";

    private AvayaHelper() {
    }

    /**
     * 解码随路数据
     *
     * @param data 随路数据原始报文, User-to-User字段内容
     * @return pair.left shared uui, pair.right ucid
     */
    public static ImmutablePair<String, String> decode(String data) {
        log.debug("decode data : {}", data);
        return new ImmutablePair<>(decodeShared(data), decodeUcid(data));
    }

    /**
     * 获取随路数据
     * opcode : C8
     *
     * @param data 随路数据原始报文, User-to-User字段内容
     * @return 随路数据
     */
    public static String decodeShared(String data) {
        log.debug("decode shared data : {}", data);
        // 04C80B4646464646464646464646
        //       4646464646464646464646
        return decodeHex(getHexData(data, "C8"));
    }

    /**
     * 获取ucid
     * opcode : C8
     *
     * @param data 随路数据原始报文, User-to-User字段内容
     * @return ucid
     */
    public static String decodeUcid(String data) {
        if (StringUtils.isBlank(data)) {
            return EMPTY;
        }
        log.debug("decode ucid data : {}", data);
        // 006403895D75D362
        String ucidHex = getHexData(data, "FA");
        // 截取十六进制
        String group1Hex = ucidHex.substring(0, 4);
        String group2Hex = ucidHex.substring(4, 8);
        String group3Hex = ucidHex.substring(8);
        // 转成十进制
        int group1 = hexToDecimal(group1Hex);
        int group2 = hexToDecimal(group2Hex);
        int group3 = hexToDecimal(group3Hex);
        return append(group1, 5) + append(group2, 5) + append(group3, 10);
    }

    private static String getHexData(String data, String token) {
        int index = data.indexOf(token);
        if (index == -1) {
            return EMPTY;
        }
        String strLen = data.substring(index + 2, index + 4);
        int len = hexToDecimal(strLen) * 2;
        if (data.length() < index + 4 + len) {
            throw new IllegalFormatFlagsException("");
        }
        return data.substring(index + 4, index + 4 + len);
    }

    private static int hexToDecimal(String hex) {
        return Integer.valueOf(hex, 16);
    }

    private static String append(int value, int maxLen) {
        StringBuilder strValue = new StringBuilder(Integer.toString(value));
        while (strValue.length() < maxLen) {
            strValue.insert(0, "0");
        }
        return strValue.toString();
    }

    private static String decodeHex(String hexStr) {
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (pos < hexStr.length()) {
            sb.append((char) hexToDecimal(hexStr.substring(pos, pos + 2)));
            pos += 2;
        }
        return sb.toString();
    }

}
