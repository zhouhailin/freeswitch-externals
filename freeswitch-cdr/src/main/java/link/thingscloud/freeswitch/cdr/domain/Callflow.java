package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Callflow class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Callflow {
    private String dialplan;
    private String uniqueId;
    private String cloneOf;
    private String profileIndex;
    private Extension extension;
    private CallerProfile callerProfile;
    private Times times;
}
