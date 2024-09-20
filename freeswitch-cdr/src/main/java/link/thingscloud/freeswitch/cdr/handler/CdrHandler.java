package link.thingscloud.freeswitch.cdr.handler;

import link.thingscloud.freeswitch.cdr.domain.Cdr;

/**
 * <p>CdrHandler interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public interface CdrHandler {

    /**
     * <p>handleCdr.</p>
     *
     * @param cdr a {@link Cdr} object.
     */
    void handleCdr(Cdr cdr);

}
