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

/**
 * <p>StringUtils class.</p>
 *
 * @author zhouhailin
 * @version 1.0.0
 */
public class StringUtils {

    public static final String EMPTY = "";
    public static final String BLANK = " ";

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
