package link.thingscloud.freeswitch.esl.util;

import java.util.Collection;
import java.util.Objects;

/**
 * <p>Validate class.</p>
 *
 * @author zhouhailin
 * @version $Id: $Id
 */
public class Validate {

    /**
     * <p>isTrue.</p>
     *
     * @param expression a boolean.
     * @param message    a {@link java.lang.String} object.
     * @param values     a {@link java.lang.Object} object.
     */
    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    /**
     * <p>notEmpty.</p>
     *
     * @param collection a T object.
     * @param message    a {@link java.lang.String} object.
     * @param values     a {@link java.lang.Object} object.
     * @param <T>        T
     * @return a T object.
     */
    public static <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... values) {
        Objects.requireNonNull(collection, () -> String.format(message, values));
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return collection;
    }
}
