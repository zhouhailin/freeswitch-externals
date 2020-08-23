package link.thingscloud.freeswitch.esl.dptools;

import link.thingscloud.freeswitch.esl.internal.IModEslApi;
import link.thingscloud.freeswitch.esl.transport.SendMsg;

public class DpTools {

	private final IModEslApi api;

	public DpTools(IModEslApi api) {
		this.api = api;
	}

	public DpTools answer() {
		api.sendMessage(new SendMsg().addCallCommand("answer"));
		return this;
	}

}
