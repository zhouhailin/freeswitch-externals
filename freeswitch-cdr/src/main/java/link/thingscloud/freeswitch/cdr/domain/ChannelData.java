package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>ChannelData class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class ChannelData {
    private String state;
    private String direction;
    private String stateNumber;
    private String flags;
    private String caps;
}
