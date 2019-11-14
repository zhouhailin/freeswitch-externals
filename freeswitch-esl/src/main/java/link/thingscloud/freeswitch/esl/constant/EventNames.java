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

package link.thingscloud.freeswitch.esl.constant;

/**
 * <p>EventNames class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public class EventNames {
    public static final String CUSTOM = "CUSTOM";
    public static final String CLONE = "CLONE";
    public static final String CHANNEL_CREATE = "CHANNEL_CREATE";
    public static final String CHANNEL_DESTROY = "CHANNEL_DESTROY";
    public static final String CHANNEL_STATE = "CHANNEL_STATE";
    public static final String CHANNEL_CALLSTATE = "CHANNEL_CALLSTATE";
    public static final String CHANNEL_ANSWER = "CHANNEL_ANSWER";
    public static final String CHANNEL_HANGUP = "CHANNEL_HANGUP";
    public static final String CHANNEL_HANGUP_COMPLETE = "CHANNEL_HANGUP_COMPLETE";
    public static final String CHANNEL_EXECUTE = "CHANNEL_EXECUTE";
    public static final String CHANNEL_EXECUTE_COMPLETE = "CHANNEL_EXECUTE_COMPLETE";
    public static final String CHANNEL_HOLD = "CHANNEL_HOLD";
    public static final String CHANNEL_UNHOLD = "CHANNEL_UNHOLD";
    public static final String CHANNEL_BRIDGE = "CHANNEL_BRIDGE";
    public static final String CHANNEL_UNBRIDGE = "CHANNEL_UNBRIDGE";
    public static final String CHANNEL_PROGRESS = "CHANNEL_PROGRESS";
    public static final String CHANNEL_PROGRESS_MEDIA = "CHANNEL_PROGRESS_MEDIA";
    public static final String CHANNEL_OUTGOING = "CHANNEL_OUTGOING";
    public static final String CHANNEL_PARK = "CHANNEL_PARK";
    public static final String CHANNEL_UNPARK = "CHANNEL_UNPARK";
    public static final String CHANNEL_APPLICATION = "CHANNEL_APPLICATION";
    public static final String CHANNEL_ORIGINATE = "CHANNEL_ORIGINATE";
    public static final String CHANNEL_UUID = "CHANNEL_UUID";
    public static final String API = "API";
    public static final String LOG = "LOG";
    public static final String INBOUND_CHAN = "INBOUND_CHAN";
    public static final String OUTBOUND_CHAN = "OUTBOUND_CHAN";
    public static final String STARTUP = "STARTUP";
    public static final String SHUTDOWN = "SHUTDOWN";
    public static final String PUBLISH = "PUBLISH";
    public static final String UNPUBLISH = "UNPUBLISH";
    public static final String TALK = "TALK";
    public static final String NOTALK = "NOTALK";
    public static final String SESSION_CRASH = "SESSION_CRASH";
    public static final String MODULE_LOAD = "MODULE_LOAD";
    public static final String MODULE_UNLOAD = "MODULE_UNLOAD";
    public static final String DTMF = "DTMF";
    public static final String MESSAGE = "MESSAGE";
    public static final String PRESENCE_IN = "PRESENCE_IN";
    public static final String NOTIFY_IN = "NOTIFY_IN";
    public static final String PRESENCE_OUT = "PRESENCE_OUT";
    public static final String PRESENCE_PROBE = "PRESENCE_PROBE";
    public static final String MESSAGE_WAITING = "MESSAGE_WAITING";
    public static final String MESSAGE_QUERY = "MESSAGE_QUERY";
    public static final String ROSTER = "ROSTER";
    public static final String CODEC = "CODEC";
    public static final String BACKGROUND_JOB = "BACKGROUND_JOB";
    public static final String DETECTED_SPEECH = "DETECTED_SPEECH";
    public static final String DETECTED_TONE = "DETECTED_TONE";
    public static final String PRIVATE_COMMAND = "PRIVATE_COMMAND";
    public static final String HEARTBEAT = "HEARTBEAT";
    public static final String TRAP = "TRAP";
    public static final String ADD_SCHEDULE = "ADD_SCHEDULE";
    public static final String DEL_SCHEDULE = "DEL_SCHEDULE";
    public static final String EXE_SCHEDULE = "EXE_SCHEDULE";
    public static final String RE_SCHEDULE = "RE_SCHEDULE";
    public static final String RELOADXML = "RELOADXML";
    public static final String NOTIFY = "NOTIFY";
    public static final String PHONE_FEATURE = "PHONE_FEATURE";
    public static final String PHONE_FEATURE_SUBSCRIBE = "PHONE_FEATURE_SUBSCRIBE";
    public static final String SEND_MESSAGE = "SEND_MESSAGE";
    public static final String RECV_MESSAGE = "RECV_MESSAGE";
    public static final String REQUEST_PARAMS = "REQUEST_PARAMS";
    public static final String CHANNEL_DATA = "CHANNEL_DATA";
    public static final String GENERAL = "GENERAL";
    public static final String COMMAND = "COMMAND";
    public static final String SESSION_HEARTBEAT = "SESSION_HEARTBEAT";
    public static final String CLIENT_DISCONNECTED = "CLIENT_DISCONNECTED";
    public static final String SERVER_DISCONNECTED = "SERVER_DISCONNECTED";
    public static final String SEND_INFO = "SEND_INFO";
    public static final String RECV_INFO = "RECV_INFO";
    public static final String RECV_RTCP_MESSAGE = "RECV_RTCP_MESSAGE";
    public static final String CALL_SECURE = "CALL_SECURE";
    public static final String NAT = "NAT";
    public static final String RECORD_START = "RECORD_START";
    public static final String RECORD_STOP = "RECORD_STOP";
    public static final String PLAYBACK_START = "PLAYBACK_START";
    public static final String PLAYBACK_STOP = "PLAYBACK_STOP";
    public static final String CALL_UPDATE = "CALL_UPDATE";
    public static final String FAILURE = "FAILURE";
    public static final String SOCKET_DATA = "SOCKET_DATA";
    public static final String MEDIA_BUG_START = "MEDIA_BUG_START";
    public static final String MEDIA_BUG_STOP = "MEDIA_BUG_STOP";
    public static final String CONFERENCE_DATA_QUERY = "CONFERENCE_DATA_QUERY";
    public static final String CONFERENCE_DATA = "CONFERENCE_DATA";
    public static final String CALL_SETUP_REQ = "CALL_SETUP_REQ";
    public static final String CALL_SETUP_RESULT = "CALL_SETUP_RESULT";
    public static final String CALL_DETAIL = "CALL_DETAIL";
    public static final String DEVICE_STATE = "DEVICE_STATE";
    public static final String ALL = "ALL";

    private EventNames() {
    }
}
