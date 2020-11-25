package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;

import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;

/**
 * <p>InboundClientOptionHandler interface.</p>
 *
 * @author zhouhailin
 * @version $Id: $Id
 */
public interface InboundClientOptionHandler {

    /**
     * <p>getOption.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     */
    InboundClientOption getOption();

}
