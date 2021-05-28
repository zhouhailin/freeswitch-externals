package link.thingscloud.freeswitch.esl.util;

import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

/**
 * <p>EslEventUtil class.</p>
 *
 * @author zhouhailin
 * @version 1.5.0
 */
public class EslEventUtil {

    /**
     * Constant <code>CORE_UUID="Core-UUID"</code>
     */
    public static final String CORE_UUID = "Core-UUID";
    /**
     * Constant <code>UNIQUE_ID="Unique-ID"</code>
     */
    public static final String UNIQUE_ID = "Unique-ID";

    /**
     * Constant <code>EVENT_NAME="Event-Name"</code>
     */
    public static final String EVENT_NAME = "Event-Name";
    /**
     * Constant <code>EVENT_SEQUENCE="Event-Sequence"</code>
     */
    public static final String EVENT_SEQUENCE = "Event-Sequence";
    /**
     * Constant <code>EVENT_DATE_LOCAL="Event-Date-Local"</code>
     */
    public static final String EVENT_DATE_LOCAL = "Event-Date-Local";
    /**
     * Constant <code>EVENT_DATE_GMT="Event-Date-GMT"</code>
     */
    public static final String EVENT_DATE_GMT = "Event-Date-GMT";
    /**
     * Constant <code>EVENT_DATE_TIMESTAMP="Event-Date-Timestamp"</code>
     */
    public static final String EVENT_DATE_TIMESTAMP = "Event-Date-Timestamp";

    /**
     * Constant <code>EVENT_CALLING_FILE="Event-Calling-File"</code>
     */
    public static final String EVENT_CALLING_FILE = "Event-Calling-File";
    /**
     * Constant <code>EVENT_CALLING_FUNCTION="Event-Calling-Function"</code>
     */
    public static final String EVENT_CALLING_FUNCTION = "Event-Calling-Function";
    /**
     * Constant <code>EVENT_CALLING_LINE_NUMBER="Event-Calling-Line-Number"</code>
     */
    public static final String EVENT_CALLING_LINE_NUMBER = "Event-Calling-Line-Number";

    /**
     * Constant <code>FREESWITCH_IPV4="FreeSWITCH-IPv4"</code>
     */
    public static final String FREESWITCH_IPV4 = "FreeSWITCH-IPv4";
    /**
     * Constant <code>FREESWITCH_IPV6="FreeSWITCH-IPv6"</code>
     */
    public static final String FREESWITCH_IPV6 = "FreeSWITCH-IPv6";
    /**
     * Constant <code>FREESWITCH_HOSTNAME="FreeSWITCH-Hostname"</code>
     */
    public static final String FREESWITCH_HOSTNAME = "FreeSWITCH-Hostname";
    /**
     * Constant <code>FREESWITCH_SWITCHNAME="FreeSWITCH-Switchname"</code>
     */
    public static final String FREESWITCH_SWITCHNAME = "FreeSWITCH-Switchname";

    /**
     * Constant <code>CALLER_UNIQUE_ID="Caller-Unique-ID"</code>
     */
    public static final String CALLER_UNIQUE_ID = "Caller-Unique-ID";
    /**
     * Constant <code>CALLER_NETWORK_ADDR="Caller-Network-Addr"</code>
     */
    public static final String CALLER_NETWORK_ADDR = "Caller-Network-Addr";

    /**
     * Constant <code>CALLER_CONTEXT="Caller-Context"</code>
     */
    public static final String CALLER_CONTEXT = "Caller-Context";
    /**
     * Constant <code>CALLER_DIALPLAN="Caller-Dialplan"</code>
     */
    public static final String CALLER_DIALPLAN = "Caller-Dialplan";
    /**
     * Constant <code>CALLER_DIRECTION="Caller-Direction"</code>
     */
    public static final String CALLER_DIRECTION = "Caller-Direction";
    /**
     * Constant <code>CALLER_LOGICAL_DIRECTION="Caller-Logical-Direction"</code>
     */
    public static final String CALLER_LOGICAL_DIRECTION = "Caller-Logical-Direction";
    /**
     * Constant <code>CALLER_PROFILE_INDEX="Caller-Profile-Index"</code>
     */
    public static final String CALLER_PROFILE_INDEX = "Caller-Profile-Index";

    /**
     * Constant <code>CALLER_ANI="Caller-ANI"</code>
     */
    public static final String CALLER_ANI = "Caller-ANI";
    /**
     * Constant <code>CALLER_USERNAME="Caller-Username"</code>
     */
    public static final String CALLER_USERNAME = "Caller-Username";
    /**
     * Constant <code>CALLER_DESTINATION_NUMBER="Caller-Destination-Number"</code>
     */
    public static final String CALLER_DESTINATION_NUMBER = "Caller-Destination-Number";
    /**
     * Constant <code>CALLER_CALLER_ID_NAME="Caller-Caller-ID-Name"</code>
     */
    public static final String CALLER_CALLER_ID_NAME = "Caller-Caller-ID-Name";
    /**
     * Constant <code>CALLER_CALLER_ID_NUMBER="Caller-Caller-ID-Number"</code>
     */
    public static final String CALLER_CALLER_ID_NUMBER = "Caller-Caller-ID-Number";

    /**
     * Constant <code>CALLER_ORIG_CALLER_ID_NAME="Caller-Orig-Caller-ID-Name"</code>
     */
    public static final String CALLER_ORIG_CALLER_ID_NAME = "Caller-Orig-Caller-ID-Name";
    /**
     * Constant <code>CALLER_ORIG_CALLER_ID_NUMBER="Caller-Orig-Caller-ID-Number"</code>
     */
    public static final String CALLER_ORIG_CALLER_ID_NUMBER = "Caller-Orig-Caller-ID-Number";

    /**
     * Constant <code>CALLER_PROFILE_CREATED_TIME="Caller-Profile-Created-Time"</code>
     */
    public static final String CALLER_PROFILE_CREATED_TIME = "Caller-Profile-Created-Time";

    /**
     * Constant <code>CALLER_CHANNEL_CREATED_TIME="Caller-Channel-Created-Time"</code>
     */
    public static final String CALLER_CHANNEL_CREATED_TIME = "Caller-Channel-Created-Time";
    /**
     * Constant <code>CALLER_CHANNEL_PROGRESS_TIME="Caller-Channel-Progress-Time"</code>
     */
    public static final String CALLER_CHANNEL_PROGRESS_TIME = "Caller-Channel-Progress-Time";
    /**
     * Constant <code>CALLER_CHANNEL_PROGRESS_MEDIA_TIME="Caller-Channel-Progress-Media-Time"</code>
     */
    public static final String CALLER_CHANNEL_PROGRESS_MEDIA_TIME = "Caller-Channel-Progress-Media-Time";
    /**
     * Constant <code>CALLER_CHANNEL_ANSWERED_TIME="Caller-Channel-Answered-Time"</code>
     */
    public static final String CALLER_CHANNEL_ANSWERED_TIME = "Caller-Channel-Answered-Time";
    /**
     * Constant <code>CALLER_CHANNEL_HANGUP_TIME="Caller-Channel-Hangup-Time"</code>
     */
    public static final String CALLER_CHANNEL_HANGUP_TIME = "Caller-Channel-Hangup-Time";
    /**
     * Constant <code>CALLER_CHANNEL_BRIDGED_TIME="Caller-Channel-Bridged-Time"</code>
     */
    public static final String CALLER_CHANNEL_BRIDGED_TIME = "Caller-Channel-Bridged-Time";

    /**
     * Constant <code>CALLER_CHANNEL_NAME="Caller-Channel-Name"</code>
     */
    public static final String CALLER_CHANNEL_NAME = "Caller-Channel-Name";
    /**
     * Constant <code>CALLER_CHANNEL_HOLD_ACCUM="Caller-Channel-Hold-Accum"</code>
     */
    public static final String CALLER_CHANNEL_HOLD_ACCUM = "Caller-Channel-Hold-Accum";
    /**
     * Constant <code>CALLER_CHANNEL_LAST_HOLD="Caller-Channel-Last-Hold"</code>
     */
    public static final String CALLER_CHANNEL_LAST_HOLD = "Caller-Channel-Last-Hold";
    /**
     * Constant <code>CALLER_CHANNEL_TRANSFER_TIME="Caller-Channel-Transfer-Time"</code>
     */
    public static final String CALLER_CHANNEL_TRANSFER_TIME = "Caller-Channel-Transfer-Time";
    /**
     * Constant <code>CALLER_CHANNEL_RESURRECT_TIME="Caller-Channel-Resurrect-Time"</code>
     */
    public static final String CALLER_CHANNEL_RESURRECT_TIME = "Caller-Channel-Resurrect-Time";


    private EslEventUtil() {
    }

    /**
     * <p>getCoreUuid.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCoreUuid(EslEvent event) {
        return event.getEventHeaders().get(CORE_UUID);
    }

    /**
     * <p>getUniqueId.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getUniqueId(EslEvent event) {
        return event.getEventHeaders().get(UNIQUE_ID);
    }

    /**
     * <p>getCallerUniqueId.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerUniqueId(EslEvent event) {
        return event.getEventHeaders().get(CALLER_UNIQUE_ID);
    }

    /**
     * <p>getEventName.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventName(EslEvent event) {
        return event.getEventHeaders().get(EVENT_NAME);
    }

    /**
     * <p>getEventSequence.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventSequence(EslEvent event) {
        return event.getEventHeaders().get(EVENT_SEQUENCE);
    }

    /**
     * <p>getEventDateLocal.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventDateLocal(EslEvent event) {
        return event.getEventHeaders().get(EVENT_DATE_LOCAL);
    }

    /**
     * <p>getEventDateGmt.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventDateGmt(EslEvent event) {
        return event.getEventHeaders().get(EVENT_DATE_GMT);
    }

    /**
     * <p>getEventCallingFile.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventCallingFile(EslEvent event) {
        return event.getEventHeaders().get(EVENT_CALLING_FILE);
    }

    /**
     * <p>getEventCallingFunction.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventCallingFunction(EslEvent event) {
        return event.getEventHeaders().get(EVENT_CALLING_FUNCTION);
    }

    /**
     * <p>getEventCallingLineNumber.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventCallingLineNumber(EslEvent event) {
        return event.getEventHeaders().get(EVENT_CALLING_LINE_NUMBER);
    }

    /**
     * <p>getFreeswitchIpv4.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getFreeswitchIpv4(EslEvent event) {
        return event.getEventHeaders().get(FREESWITCH_IPV4);
    }

    /**
     * <p>getFreeswitchIpv6.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getFreeswitchIpv6(EslEvent event) {
        return event.getEventHeaders().get(FREESWITCH_IPV6);
    }

    /**
     * <p>getFreeswitchHostname.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getFreeswitchHostname(EslEvent event) {
        return event.getEventHeaders().get(FREESWITCH_HOSTNAME);
    }

    /**
     * <p>getFreeswitchSwitchname.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getFreeswitchSwitchname(EslEvent event) {
        return event.getEventHeaders().get(FREESWITCH_SWITCHNAME);
    }

    /**
     * <p>getEventDateTimestamp.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getEventDateTimestamp(EslEvent event) {
        return event.getEventHeaders().get(EVENT_DATE_TIMESTAMP);
    }

    /**
     * <p>getCallerProfileCreatedTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerProfileCreatedTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_PROFILE_CREATED_TIME);
    }

    /**
     * <p>getCallerChannelCreatedTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelCreatedTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_CREATED_TIME);
    }

    /**
     * <p>getCallerChannelProgressTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelProgressTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_PROGRESS_TIME);
    }

    /**
     * <p>getCallerChannelProgressMediaTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelProgressMediaTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_PROGRESS_MEDIA_TIME);
    }

    /**
     * <p>getCallerChannelAnsweredTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelAnsweredTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_ANSWERED_TIME);
    }

    /**
     * <p>getCallerChannelHangupTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelHangupTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_HANGUP_TIME);
    }

    /**
     * <p>getCallerChannelBridgedTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelBridgedTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_BRIDGED_TIME);
    }

    /**
     * <p>getCallerNetworkAddr.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerNetworkAddr(EslEvent event) {
        return event.getEventHeaders().get(CALLER_NETWORK_ADDR);
    }

    /**
     * <p>getCallerContext.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerContext(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CONTEXT);
    }

    /**
     * <p>getCallerDialplan.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerDialplan(EslEvent event) {
        return event.getEventHeaders().get(CALLER_DIALPLAN);
    }

    /**
     * <p>getCallerDirection.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerDirection(EslEvent event) {
        return event.getEventHeaders().get(CALLER_DIRECTION);
    }

    /**
     * <p>getCallerLogicalDirection.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerLogicalDirection(EslEvent event) {
        return event.getEventHeaders().get(CALLER_LOGICAL_DIRECTION);
    }

    /**
     * <p>getCallerProfileIndex.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerProfileIndex(EslEvent event) {
        return event.getEventHeaders().get(CALLER_PROFILE_INDEX);
    }

    /**
     * <p>getCallerChannelName.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelName(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_NAME);
    }

    /**
     * <p>getCallerChannelHoldAccum.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelHoldAccum(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_HOLD_ACCUM);
    }

    /**
     * <p>getCallerChannelLastHold.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelLastHold(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_LAST_HOLD);
    }

    /**
     * <p>getCallerChannelTransferTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelTransferTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_TRANSFER_TIME);
    }

    /**
     * <p>getCallerChannelResurrectTime.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerChannelResurrectTime(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CHANNEL_RESURRECT_TIME);
    }

    /**
     * <p>getCallerAni.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerAni(EslEvent event) {
        return event.getEventHeaders().get(CALLER_ANI);
    }

    /**
     * <p>getCallerUsername.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerUsername(EslEvent event) {
        return event.getEventHeaders().get(CALLER_USERNAME);
    }

    /**
     * <p>getCallerDestinationNumber.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerDestinationNumber(EslEvent event) {
        return event.getEventHeaders().get(CALLER_DESTINATION_NUMBER);
    }

    /**
     * <p>getCallerCallerIdName.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerCallerIdName(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CALLER_ID_NAME);
    }

    /**
     * <p>getCallerCallerIdNumber.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerCallerIdNumber(EslEvent event) {
        return event.getEventHeaders().get(CALLER_CALLER_ID_NUMBER);
    }

    /**
     * <p>getCallerOrigCallerIdName.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerOrigCallerIdName(EslEvent event) {
        return event.getEventHeaders().get(CALLER_ORIG_CALLER_ID_NAME);
    }

    /**
     * <p>getCallerOrigCallerIdNumber.</p>
     *
     * @param event a {@link link.thingscloud.freeswitch.esl.transport.event.EslEvent} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCallerOrigCallerIdNumber(EslEvent event) {
        return event.getEventHeaders().get(CALLER_ORIG_CALLER_ID_NUMBER);
    }
}
