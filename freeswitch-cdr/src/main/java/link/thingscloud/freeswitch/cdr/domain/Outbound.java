package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Outbound class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Outbound {
    private String rawBytes;
    private String mediaBytes;
    private String packetCount;
    private String mediaPacketCount;
    private String skipPacketCount;
    private String dtmfPacketCount;
    private String cngPacketCount;
    private String rtcpPacketCount;
    private String rtcpOctetCount;
}
