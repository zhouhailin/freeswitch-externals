package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;

import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.inbound.option.ServerOption;
import link.thingscloud.freeswitch.esl.spring.boot.starter.propeties.InboundClientProperties;
import link.thingscloud.freeswitch.esl.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Abstract AbstractInboundClientOptionHandler class.</p>
 *
 * @author zhouhailin
 * @version 1.0.0
 */
public abstract class AbstractInboundClientOptionHandler implements InboundClientOptionHandler {

    @Autowired
    protected InboundClientProperties properties;

    /**
     * <p>intercept.</p>
     *
     * @param inboundClientOption a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     */
    protected abstract void intercept(InboundClientOption inboundClientOption);

    /**
     * {@inheritDoc}
     */
    @Override
    public InboundClientOption getOption() {
        InboundClientOption option = newInboundClientOption();
        properties.getServers().forEach(server -> {
            if (StringUtils.isNotBlank(server.getHost()) && server.getPort() > 1) {
                option.addServerOption(new ServerOption(server.getHost(), server.getPort())
                        .timeoutSeconds(server.getTimeoutSeconds())
                        .password(server.getPassword()));
            }
        });
        properties.getEvents().forEach(event -> {
            if (StringUtils.isNotBlank(event)) {
                option.addEvents(event);
            }
        });
        intercept(option);
        return option;
    }

    /**
     * <p>newInboundClientOption.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption} object.
     */
    protected InboundClientOption newInboundClientOption() {
        return new InboundClientOption().sndBufSize(properties.getSndBufSize())
                .rcvBufSize(properties.getRcvBufSize())
                .workerGroupThread(properties.getWorkerGroupThread())
                .publicExecutorThread(properties.getPublicExecutorThread())
                .callbackExecutorThread(properties.getCallbackExecutorThread())
                .defaultTimeoutSeconds(properties.getDefaultTimeoutSeconds())
                .readTimeoutSeconds(properties.getReadTimeoutSeconds())
                .readerIdleTimeSeconds(properties.getReaderIdleTimeSeconds())
                .defaultPassword(properties.getDefaultPassword())
                .disablePublicExecutor(properties.isDisablePublicExecutor())
                .performance(properties.isPerformance())
                .performanceCostTime(properties.getPerformanceCostTime())
                .eventPerformance(properties.isEventPerformance())
                .eventPerformanceCostTime(properties.getEventPerformanceCostTime());
    }

}
