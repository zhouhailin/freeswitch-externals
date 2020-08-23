/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.freeswitch.esl.transport.message;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Basic FreeSWITCH Event Socket messages from the server are decoded into this data object.
 * <p>
 * An ESL message is modelled as text lines.  A message always has one or more header lines, and
 * optionally may have some body lines.
 * <p>
 * Header lines are parsed and cached in a map keyed by the {@link link.thingscloud.freeswitch.esl.transport.message.EslHeaders.Name} enum.  A message
 * is always expected to have a "Content-Type" header
 * <p>
 * Any Body lines are cached in a list.
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 * @see EslHeaders.Name
 */
@Slf4j
public class EslMessage {

    //private final Map<EslHeaders.Name, String> headers = new EnumMap<>(EslHeaders.Name.class);
    private final Map<EslHeaders.Name, String> headers = new HashMap<>();
    private final List<String> body = new ArrayList<>();

    private Integer contentLength = null;

    /**
     * All the received message headers in a map keyed by {@link link.thingscloud.freeswitch.esl.transport.message.EslHeaders.Name}. The string mapped value
     * is the parsed content of the header line (ie, it does not include the header name).
     *
     * @return map of header values
     */
    public Map<EslHeaders.Name, String> getHeaders() {
        return headers;
    }

    /**
     * Convenience method
     *
     * @param headerName as a {@link link.thingscloud.freeswitch.esl.transport.message.EslHeaders.Name}
     * @return true if an only if there is a header entry with the supplied header name
     */
    public boolean hasHeader(EslHeaders.Name headerName) {
        return headers.containsKey(headerName);
    }

    /**
     * Convenience method
     *
     * @param headerName as a {@link link.thingscloud.freeswitch.esl.transport.message.EslHeaders.Name}
     * @return same as getHeaders().get( headerName )
     */
    public String getHeaderValue(EslHeaders.Name headerName) {
        return headers.get(headerName);
    }

    /**
     * Convenience method
     *
     * @return true if and only if a header exists with name "Content-Length"
     */
    public boolean hasContentLength() {
        return headers.containsKey(EslHeaders.Name.CONTENT_LENGTH);
    }

    /**
     * Convenience method
     *
     * @return integer value of header with name "Content-Length"
     */
    public Integer getContentLength() {
        if (contentLength != null) {
            return contentLength;
        }
        if (hasContentLength()) {
            contentLength = Integer.valueOf(headers.get(EslHeaders.Name.CONTENT_LENGTH));
        }
        return contentLength;
    }

    /**
     * Convenience method
     *
     * @return header value of header with name "Content-Type"
     */
    public String getContentType() {
        return headers.get(EslHeaders.Name.CONTENT_TYPE);
    }

    /**
     * Any received message body lines
     *
     * @return list with a string for each line received, may be an empty list
     */
    public List<String> getBodyLines() {
        return body;
    }

    /**
     * Used by the {@link EslFrameDecoder}.
     *
     * @param name
     * @param value
     */
    void addHeader(EslHeaders.Name name, String value) {
        log.trace("adding header [{}] [{}]", name, value);
        headers.put(name, value);
    }

    /**
     * Used by the {@link EslFrameDecoder}
     *
     * @param line
     */
    void addBodyLine(String line) {
        if (line == null) {
            return;
        }
        body.add(line);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EslMessage: contentType=[");
        sb.append(getContentType());
        sb.append("] headers=");
        sb.append(headers.size());
        sb.append(", body=");
        sb.append(body.size());
        sb.append(" lines.");

        return sb.toString();
    }

}
