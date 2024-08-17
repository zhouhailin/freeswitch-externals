package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>ErrorPeriod class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class ErrorPeriod {
    private String start;
    private String stop;
    private String flaws;
    private String consecutiveFlaws;
    private String durationMsec;
}
