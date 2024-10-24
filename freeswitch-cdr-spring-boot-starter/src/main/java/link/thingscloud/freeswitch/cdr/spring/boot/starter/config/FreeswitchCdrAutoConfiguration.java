package link.thingscloud.freeswitch.cdr.spring.boot.starter.config;

import link.thingscloud.freeswitch.cdr.handler.CdrHandler;
import link.thingscloud.freeswitch.cdr.spring.boot.starter.handler.SimpleCdrHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>FreeswitchCdrAutoConfiguration class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Configuration
@ComponentScan({"link.thingscloud.freeswitch.cdr"})
public class FreeswitchCdrAutoConfiguration {


    /**
     * <p>cdrHandler.</p>
     *
     * @return a {@link CdrHandler} object.
     */
    @Bean
    @ConditionalOnMissingBean(CdrHandler.class)
    public CdrHandler cdrHandler() {
        return new SimpleCdrHandler();
    }

}
