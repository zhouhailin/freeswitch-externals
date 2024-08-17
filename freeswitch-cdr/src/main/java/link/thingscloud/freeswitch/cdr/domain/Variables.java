package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Variables class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Variables {
    private Map<String, String> variableTable;

    /**
     * <p>putVariable.</p>
     *
     * @param key   a {@link String} object.
     * @param value a {@link String} object.
     */
    public void putVariable(String key, String value) {
        if (variableTable == null) {
            variableTable = new HashMap<>(256);
        }
        variableTable.put(key, value);
    }
}
