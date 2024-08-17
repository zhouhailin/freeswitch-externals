package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>CallStats class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class CallStats {
    private Audio audio;
}
