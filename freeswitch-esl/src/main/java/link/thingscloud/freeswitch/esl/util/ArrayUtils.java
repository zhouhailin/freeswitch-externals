package link.thingscloud.freeswitch.esl.util;

import java.lang.reflect.Array;

/**
 * <p>ArrayUtils class.</p>
 *
 * @author zhouhailin
 * @version 1.0.0
 */
public class ArrayUtils {

    /**
     * <p>isEmpty.</p>
     *
     * @param array an array of {@link java.lang.Object} objects.
     * @return a boolean.
     */
    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    /**
     * <p>getLength.</p>
     *
     * @param array a {@link java.lang.Object} object.
     * @return a int.
     */
    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }
}
