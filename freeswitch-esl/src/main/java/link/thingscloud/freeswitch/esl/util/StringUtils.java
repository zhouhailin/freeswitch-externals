package link.thingscloud.freeswitch.esl.util;

/**
 * <p>StringUtils class.</p>
 *
 * @author zhouhailin
 * @version 1.0.0
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * <p>length.</p>
     *
     * @param cs a {@link java.lang.CharSequence} object.
     * @return a int.
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * <p>isNotBlank.</p>
     *
     * @param cs a {@link java.lang.CharSequence} object.
     * @return a boolean.
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * <p>isBlank.</p>
     *
     * @param cs a {@link java.lang.CharSequence} object.
     * @return a boolean.
     */
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>equals.</p>
     *
     * @param cs1 a {@link java.lang.CharSequence} object.
     * @param cs2 a {@link java.lang.CharSequence} object.
     * @return a boolean.
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        // Step-wise comparison
        final int length = cs1.length();
        for (int i = 0; i < length; i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean inEquals(String str, String... strs) {
        for (String s : strs) {
            if (equals(str, s)) {
                return true;
            }
        }
        return false;
    }
}
