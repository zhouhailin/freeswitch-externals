package link.thingscloud.freeswitch.esl.util;

import java.lang.reflect.Array;

/**
 * @author zhouhailin
 */
public class ArrayUtils {

    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }
}
