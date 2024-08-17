package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Hold class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Hold {
    private Long on;
    private Long off;
    private String bridgedTo;
}
