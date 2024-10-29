package link.thingscloud.freeswitch.cdr.spring.boot.starter.handler;

import com.alibaba.fastjson.JSON;
import link.thingscloud.freeswitch.cdr.domain.Cdr;
import link.thingscloud.freeswitch.cdr.handler.CdrHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>SimpleCdrHandler class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
public class SimpleCdrHandler implements CdrHandler, InitializingBean {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCdr(Cdr cdr) {
        log.info("simple handle cdr : [{}]", JSON.toJSONString(cdr, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        log.warn("implements 'CdrHandler' can replace SimpleCdrHandler ...");
    }
}
