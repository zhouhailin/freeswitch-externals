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

package link.thingscloud.freeswitch.esl.exception;

/**
 * <p>InboundTimeoutExcetion class.</p>
 *
 * @author : zhouhailin
 * @version 1.0.0
 */
public class InboundTimeoutExcetion extends InboundClientException {
    /**
     * <p>Constructor for InboundTimeoutExcetion.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public InboundTimeoutExcetion(String message) {
        super(message);
    }

    /**
     * <p>Constructor for InboundTimeoutExcetion.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause   a {@link java.lang.Throwable} object.
     */
    public InboundTimeoutExcetion(String message, Throwable cause) {
        super(message, cause);
    }
}
