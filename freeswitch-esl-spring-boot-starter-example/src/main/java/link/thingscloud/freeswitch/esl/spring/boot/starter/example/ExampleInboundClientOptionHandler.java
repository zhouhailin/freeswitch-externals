package link.thingscloud.freeswitch.esl.spring.boot.starter.example;

import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.AbstractInboundClientOptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>ExampleInboundClientOptionHandler class.</p>
 *
 * @author zhouhailin
 * @version $Id: $Id
 */
@Slf4j
@Component
public class ExampleInboundClientOptionHandler extends AbstractInboundClientOptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void intercept(InboundClientOption inboundClientOption) {
        List<ServerOption> serverOptions = inboundClientOption.serverOptions();
        log.info("serverOptions before : {}", serverOptions);
        serverOptions.clear();
        serverOptions.add(new ServerOption("127.0.0.8", 8021));
        log.info("serverOptions after  : {}", serverOptions);
    }
}
