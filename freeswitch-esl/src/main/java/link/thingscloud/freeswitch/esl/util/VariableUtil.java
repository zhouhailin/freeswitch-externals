package link.thingscloud.freeswitch.esl.util;

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * @author zhouhailin
 * @version 1.5.1
 */
public class VariableUtil {

    private static final String VARIABLE_PREFIX = "variable_";

    public static String get(EslEvent event, String key) {
        return event.getEventHeaders().get(key);
    }

    public static String getVar(EslEvent event, String key) {
        if (key.startsWith(VARIABLE_PREFIX)) {
            return event.getEventHeaders().get(key);
        }
        return event.getEventHeaders().get(VARIABLE_PREFIX + key);
    }

    private VariableUtil() {
    }

}
