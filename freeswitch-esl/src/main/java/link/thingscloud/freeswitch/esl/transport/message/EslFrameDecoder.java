/*
 * Copyright 2019 ThingsCloud Link.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.thingscloud.freeswitch.esl.transport.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import link.thingscloud.freeswitch.esl.exception.EslDecoderException;
import link.thingscloud.freeswitch.esl.transport.util.HeaderParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 */
@Slf4j
public class EslFrameDecoder extends ReplayingDecoder<EslFrameDecoder.State> {
    /**
     * Line feed character
     */
    private static final byte LF = 10;
    private final int maxHeaderSize;
    private EslMessage currentMessage;
    private boolean treatUnknownHeadersAsBody = false;

    public EslFrameDecoder(int maxHeaderSize) {
        super(State.READ_HEADER);
        if (maxHeaderSize <= 0) {
            throw new IllegalArgumentException(
                    "maxHeaderSize must be a positive integer: " +
                            maxHeaderSize);
        }
        this.maxHeaderSize = maxHeaderSize;
    }

    public EslFrameDecoder(int maxHeaderSize, boolean treatUnknownHeadersAsBody) {
        this(maxHeaderSize);
        this.treatUnknownHeadersAsBody = treatUnknownHeadersAsBody;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        log.trace("decode() : state [{}]", state());
        switch (state()) {
            case READ_HEADER:
                if (currentMessage == null) {
                    currentMessage = new EslMessage();
                }
                /*
                 *  read '\n' terminated lines until reach a single '\n'
                 */
                boolean reachedDoubleLF = false;
                while (!reachedDoubleLF) {
                    // this will read or fail
                    String headerLine = readToLineFeedOrFail(buffer, maxHeaderSize);
                    log.trace("read header line [{}]", headerLine);
                    if (!headerLine.isEmpty()) {
                        // split the header line
                        String[] headerParts = HeaderParser.splitHeader(headerLine);
                        EslHeaders.Name headerName = EslHeaders.Name.fromLiteral(headerParts[0]);
                        if (headerName == null) {
                            if (treatUnknownHeadersAsBody) {
                                // cache this 'header' as a body line <-- useful for Outbound client mode
                                currentMessage.addBodyLine(headerLine);
                            } else {
                                throw new IllegalStateException("Unhandled ESL header [" + headerParts[0] + ']');
                            }
                        }
                        currentMessage.addHeader(headerName, headerParts[1]);
                    } else {
                        reachedDoubleLF = true;
                    }
                    // do not read in this line again
                    checkpoint();
                }
                // have read all headers - check for content-length
                if (currentMessage.hasContentLength()) {
                    checkpoint(State.READ_BODY);
                    log.trace("have content-length, decoding body ..");
                    //  force the next section
                    return;
                } else {
                    // end of message
                    checkpoint(State.READ_HEADER);
                    // send message upstream
                    EslMessage decodedMessage = currentMessage;
                    currentMessage = null;

                    out.add(decodedMessage);
                    return;
                }
            case READ_BODY:
                /*
                 *   read the content-length specified
                 */
                int contentLength = currentMessage.getContentLength();
                ByteBuf bodyBytes = buffer.readBytes(contentLength);
                log.trace("read [{}] body bytes", bodyBytes.writerIndex());
                // most bodies are line based, so split on LF
                while (bodyBytes.isReadable()) {
                    String bodyLine = readLine(bodyBytes, contentLength);
                    log.trace("read body line [{}]", bodyLine);
                    currentMessage.addBodyLine(bodyLine);
                }

                // end of message
                checkpoint(State.READ_HEADER);
                // send message upstream
                EslMessage decodedMessage = currentMessage;
                currentMessage = null;

                out.add(decodedMessage);
                return;
            default:
                throw new EslDecoderException("Illegal state: [" + state() + ']');
        }
    }

    private String readToLineFeedOrFail(ByteBuf buffer, int maxLineLegth) {
        StringBuilder sb = new StringBuilder(64);
        while (true) {
            // this read might fail
            byte nextByte = buffer.readByte();
            if (nextByte == LF) {
                return sb.toString();
            } else {
                // Abort decoding if the decoded line is too large.
                if (sb.length() >= maxLineLegth) {
                    throw new TooLongFrameException(
                            "ESL header line is longer than " + maxLineLegth + " bytes.");
                }
                sb.append((char) nextByte);
            }
        }
    }

    private String readLine(ByteBuf buffer, int maxLineLength) {
        StringBuilder sb = new StringBuilder(64);
        while (buffer.isReadable()) {
            // this read should always succeed
            byte nextByte = buffer.readByte();
            if (nextByte == LF) {
                return sb.toString();
            } else {
                // Abort decoding if the decoded line is too large.
                if (sb.length() >= maxLineLength) {
                    throw new TooLongFrameException(
                            "ESL message line is longer than " + maxLineLength + " bytes.");
                }
                sb.append((char) nextByte);
            }
        }

        return sb.toString();
    }

    protected enum State {
        /**
         * 包头
         */
        READ_HEADER,
        /**
         * 包体
         */
        READ_BODY,
    }
}
