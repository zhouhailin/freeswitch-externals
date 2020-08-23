package link.thingscloud.freeswitch.esl.outbound;

import link.thingscloud.freeswitch.esl.IEslEventListener;
import link.thingscloud.freeswitch.esl.internal.Context;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

public interface IClientHandler {

	void onConnect(Context context, EslEvent event);

	/**
	 * Signal of a server initiated event.
	 *
	 * @param eslEvent as an {@link EslEvent}
	 */
	void onEslEvent(Context context, EslEvent eslEvent);
}
