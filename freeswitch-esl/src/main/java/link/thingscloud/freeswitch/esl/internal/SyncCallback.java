package link.thingscloud.freeswitch.esl.internal;


import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class SyncCallback {
    private final CountDownLatch latch = new CountDownLatch(1);
    private EslMessage response;

    /**
     * Block waiting for the countdown latch to be released, then return the
     * associated response object.
     *
     * @return msg
     */
    public EslMessage get() {
        try {
            log.trace("awaiting latch ... ");
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.trace("returning response [{}]", response);
        return response;
    }

    /**
     * Attach this response to the callback and release the countdown latch.
     *
     * @param response res
     */
    public void handle(EslMessage response) {
        this.response = response;
        log.trace("releasing latch for response [{}]", response);
        latch.countDown();
    }
}