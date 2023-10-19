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

package link.thingscloud.freeswitch.esl.transport.util;

/**
 * <p>ByteBuilder class.</p>
 *
 * @author zhouhailin
 * @version 1.0.0
 */
public class ByteBuilder {

    private byte[] b = new byte[128];
    private int index = 0;

    private ByteBuilder() {
    }

    /**
     * <p>newBuilder.</p>
     *
     * @return a {@link link.thingscloud.freeswitch.esl.transport.util.ByteBuilder} object.
     */
    public static ByteBuilder newBuilder() {
        return new ByteBuilder();
    }

    /**
     * <p>append.</p>
     *
     * @param byte0 a byte.
     * @return a {@link link.thingscloud.freeswitch.esl.transport.util.ByteBuilder} object.
     */
    public ByteBuilder append(byte byte0) {
        if (this.b.length <= (index + 1)) {
            byte[] newB = new byte[this.b.length * 2];
            System.arraycopy(this.b, 0, newB, 0, this.b.length);
            this.b = newB;
        }
        this.b[index] = byte0;
        index++;
        return this;
    }

    /**
     * <p>length.</p>
     *
     * @return a int.
     */
    public int length() {
        return index;
    }

    /**
     * <p>build.</p>
     *
     * @return an array of {@link byte} objects.
     */
    public byte[] build() {
        return b;
    }

    /**
     * <p>string.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String string() {
        return new String(b, 0, index);
    }
}
