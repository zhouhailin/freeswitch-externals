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

package link.thingscloud.freeswitch.esl.transport.event;

import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import link.thingscloud.freeswitch.esl.transport.util.HeaderParser;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FreeSWITCH Event Socket <strong>events</strong> are decoded into this data object.
 * <p>
 * An ESL event is modelled as a collection of text lines. An event always has several eventHeader
 * lines, and optionally may have some eventBody lines.  In addition the messageHeaders of the
 * original containing {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} which carried the event are also available.
 * <p>
 * The eventHeader lines are parsed and cached in a map keyed by the eventHeader name string. An event
 * is always expected to have an "Event-Name" eventHeader. Commonly used eventHeader names are coded
 * in {@link link.thingscloud.freeswitch.esl.transport.event.EslEventHeaderNames}
 * <p>
 * Any eventBody lines are cached in a list.
 * <p>
 * The messageHeader lines from the original message are cached in a map keyed by {@link link.thingscloud.freeswitch.esl.transport.message.EslHeaders.Name}.
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 * @see EslEventHeaderNames
 */
@Slf4j
public class EslEvent {

    private final Map<EslHeaders.Name, String> messageHeaders;
    private final Map<String, String> eventHeaders;
    private final List<String> eventBody;
    private final boolean decodeEventHeaders = true;

    /**
     * <p>Constructor for EslEvent.</p>
     *
     * @param rawMessage a {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} object.
     */
    public EslEvent(EslMessage rawMessage) {
        this(rawMessage, false);
    }

    /**
     * <p>Constructor for EslEvent.</p>
     *
     * @param rawMessage        a {@link link.thingscloud.freeswitch.esl.transport.message.EslMessage} object.
     * @param parseCommandReply a boolean.
     */
    public EslEvent(EslMessage rawMessage, boolean parseCommandReply) {
        messageHeaders = rawMessage.getHeaders();
        eventHeaders = new HashMap<>(rawMessage.getBodyLines().size());
        eventBody = new ArrayList<>();
        // plain or xml body
        if (rawMessage.getContentType().equals(EslHeaders.Value.TEXT_EVENT_PLAIN)) {
            parsePlainBody(rawMessage.getBodyLines());
        } else if (rawMessage.getContentType().equals(EslHeaders.Value.TEXT_EVENT_XML)) {
            throw new IllegalStateException("XML events are not yet supported");
        } else if (rawMessage.getContentType().equals(EslHeaders.Value.COMMAND_REPLY) && parseCommandReply) {
            parsePlainBody(rawMessage.getBodyLines());
        } else {
            throw new IllegalStateException("Unexpected EVENT content-type: " +
                    rawMessage.getContentType());
        }
    }

    /**
     * The message headers of the original ESL message from which this event was decoded.
     * The message headers are stored in a map keyed by {@link link.thingscloud.freeswitch.esl.transport.message.EslHeaders.Name}. The string mapped value
     * is the parsed content of the header line (ie, it does not include the header name).
     *
     * @return map of header values
     */
    public Map<EslHeaders.Name, String> getMessageHeaders() {
        return messageHeaders;
    }

    /**
     * The event headers of this event. The headers are parsed and stored in a map keyed by the string
     * name of the header, and the string mapped value is the parsed content of the event header line
     * (ie, it does not include the header name).
     *
     * @return map of event header values
     */
    public Map<String, String> getEventHeaders() {
        return eventHeaders;
    }

    /**
     * Any event body lines that were present in the event.
     *
     * @return list of decoded event body lines, may be an empty list.
     */
    public List<String> getEventBodyLines() {
        return eventBody;
    }

    /**
     * Convenience method.
     *
     * @return the string value of the event header "Event-Name"
     */
    public String getEventName() {
        return getEventHeaders().get(EslEventHeaderNames.EVENT_NAME);
    }

    /**
     * Convenience method.
     *
     * @return long value of the event header "Event-Date-Timestamp"
     */
    public long getEventDateTimestamp() {
        return Long.valueOf(getEventHeaders().get(EslEventHeaderNames.EVENT_DATE_TIMESTAMP));
    }

    /**
     * Convenience method.
     *
     * @return long value of the event header "Event-Date-Local"
     */
    public String getEventDateLocal() {
        return getEventHeaders().get(EslEventHeaderNames.EVENT_DATE_LOCAL);
    }

    /**
     * Convenience method.
     *
     * @return long value of the event header "Event-Date-GMT"
     */
    public String getEventDateGmt() {
        return getEventHeaders().get(EslEventHeaderNames.EVENT_DATE_GMT);
    }

    /**
     * Convenience method.
     *
     * @return true if the eventBody list is not empty.
     */
    public boolean hasEventBody() {
        return !eventBody.isEmpty();
    }

    private void parsePlainBody(final List<String> rawBodyLines) {
        boolean isEventBody = false;
        for (String rawLine : rawBodyLines) {
            if (!isEventBody) {
                // split the line
                String[] headerParts = HeaderParser.splitHeader(rawLine);
                if (decodeEventHeaders) {
                    try {
                        String decodedValue = URLDecoder.decode(headerParts[1], "UTF-8");
                        log.trace("decoded from: [{}]", headerParts[1]);
                        log.trace("decoded   to: [{}]", decodedValue);
                        eventHeaders.put(headerParts[0], decodedValue);
                    } catch (UnsupportedEncodingException e) {
                        log.warn("Could not URL decode [{}]", headerParts[1]);
                        eventHeaders.put(headerParts[0], headerParts[1]);
                    }
                } else {
                    eventHeaders.put(headerParts[0], headerParts[1]);
                }
                if (headerParts[0].equals(EslEventHeaderNames.CONTENT_LENGTH)) {
                    // the remaining lines will be considered body lines
                    isEventBody = true;
                }
            } else {
                // ignore blank line (always is one following the content-length
                if (rawLine.length() > 0) {
                    eventBody.add(rawLine);
                }
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EslEvent: name=[");
        sb.append(getEventName());
        sb.append("] headers=");
        sb.append(messageHeaders.size());
        sb.append(", eventHeaders=");
        sb.append(eventHeaders.size());
        sb.append(", eventBody=");
        sb.append(eventBody.size());
        sb.append(" lines.");
        return sb.toString();
    }
}
