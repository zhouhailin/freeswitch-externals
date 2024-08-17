package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>OriginateeCallerProfile class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class OriginateeCallerProfile {
    private String username;
    private String dialplan;
    private String callerIdName;
    private String callerIdNumber;
    private String calleeIdName;
    private String calleeIdNumber;
    private String ani;
    private String aniii;
    private String networkAddr;
    private String rdnis;
    private String destinationNumber;
    private String uuid;
    private String source;
    private String context;
    private String chanName;
}
