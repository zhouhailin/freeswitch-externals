/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
