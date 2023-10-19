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

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * <p>RemotingUtil class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version 1.0.0
 */
public class RemotingUtil {

    /**
     * private constructor
     */
    private RemotingUtil() {
    }

    /**
     * <p>socketAddress2String.</p>
     *
     * @param addr a {@link java.net.SocketAddress} object.
     * @return a {@link java.lang.String} object.
     */
    public static String socketAddress2String(final SocketAddress addr) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) addr;
        return inetSocketAddress.getAddress().getHostAddress() +
                ":" +
                inetSocketAddress.getPort();
    }
}
