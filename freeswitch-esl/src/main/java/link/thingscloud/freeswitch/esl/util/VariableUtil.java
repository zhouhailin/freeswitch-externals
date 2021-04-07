package link.thingscloud.freeswitch.esl.util;

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

import java.util.Map;

/**
 * @author zhouhailin
 * @version 1.5.1
 */
public class VariableUtil {

    private static final String VARIABLE_PREFIX = "variable_";

    public static String get(EslEvent event, String key) {
        return event.getEventHeaders().get(key);
    }

    public static long getLongVar(EslEvent event, String key) {
        return Long.parseLong(getVar(event.getEventHeaders(), key));
    }

    public static int getIntVar(EslEvent event, String key) {
        return Integer.parseInt(getVar(event.getEventHeaders(), key));
    }

    public static String getVar(EslEvent event, String key) {
        return getVar(event.getEventHeaders(), key);
    }

    public static String getVar(Map<String, String> eventHeaders, String key) {
        if (key.startsWith(VARIABLE_PREFIX)) {
            return eventHeaders.get(key);
        }
        return eventHeaders.get(VARIABLE_PREFIX + key);
    }

    private VariableUtil() {
    }

}
