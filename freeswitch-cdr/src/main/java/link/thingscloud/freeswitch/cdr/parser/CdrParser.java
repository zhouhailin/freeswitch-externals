package link.thingscloud.freeswitch.cdr.parser;

import com.alibaba.fastjson.JSON;
import link.thingscloud.freeswitch.cdr.domain.*;
import link.thingscloud.freeswitch.cdr.exception.ParserException;
import link.thingscloud.freeswitch.cdr.util.CdrDecodeUtil;
import link.thingscloud.freeswitch.cdr.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>CdrParser class.</p>
 *
 * @author : zhouhailin
 * @version $Id: $Id
 */
@Slf4j
public class CdrParser {

    private static final String CORE_UUID = "core-uuid";
    private static final String SWITCHNAME = "switchname";

    private static final String CHANNEL_DATA = "channel_data";
    private static final String CALL_STATS = "call-stats";
    private static final String STATE = "state";
    private static final String DIRECTION = "direction";
    private static final String STATE_NUMBER = "state_number";
    private static final String FLAGS = "flags";
    private static final String CAPS = "caps";

    private static final String VARIABLES = "variables";

    private static final String APP_LOG = "app_log";

    private static final String HOLD_RECORD = "hold-record";

    private static final String CALLFLOW = "callflow";

    private static ThreadLocal<String> local = new ThreadLocal<>();

    private static boolean isTraceEnabled = log.isTraceEnabled();

    private CdrParser() {
    }

    /**
     * xml_cdr.conf.xml
     * <p>
     * &lt;param name=&quot;encode&quot; value=&quot;true&quot;/&gt;
     * <p>
     * cdr=&lt;?xml&nbsp;version=&quot;1.0&quot;?&gt;
     * <p>
     * uuid=a_12d714e6-3c49-463a-8965-755b8f598032&amp;cdr=&lt;?xml version=&quot;1.0&quot;?&gt;
     *
     * @param reqText req xml content
     * @return cdr
     * @throws ParserException if any.
     */
    public static Cdr decodeThenParse(String reqText) throws ParserException {
        String decodeText = CdrDecodeUtil.decode(reqText);
        String decodeXml = StringUtils.substringAfter(decodeText, "cdr=");
        return parse(decodeXml);
    }

    /**
     * <p>parse.</p>
     *
     * @param decodeXml a {@link String} object.
     * @return a {@link Cdr} object.
     * @throws ParserException if any.
     */
    public static Cdr parse(String decodeXml) throws ParserException {
        if (StringUtils.isBlank(decodeXml)) {
            throw new ParserException("cdr parse xml failed, strXml is blank.");
        }
        local.set(decodeXml);
        try {
            Document document = DocumentHelper.parseText(decodeXml);
            Element rootElement = document.getRootElement();

            Cdr cdr = new Cdr();
            assignCdrElement(cdr, rootElement);

            if (isTraceEnabled) {
                log.trace("cdr parse : [{}]", JSON.toJSONString(cdr, true));
            }
            return cdr;
        } catch (Exception e) {
            throw new ParserException("cdr parse xml failed.", e);
        } finally {
            local.remove();
        }
    }


    private static void assignCdrElement(Cdr cdr, Element rootElement) {
        // cdr 节点属性赋值
        attributes(rootElement, (name, value) -> {
            if (CORE_UUID.equals(name)) {
                cdr.setCoreUuid(value);
            } else if (SWITCHNAME.equals(name)) {
                cdr.setSwitchname(value);
            } else {
                log.warn("assignCdrElement found other attribute name : [{}], value : [{}], xml : [{}]", name, value, local.get());
            }
        });

        // cdr 节点下所有元素
        elements(rootElement, (name, element) -> {
            switch (name) {
                case CHANNEL_DATA:
                    ChannelData channelData = new ChannelData();
                    cdr.setChannelData(channelData);
                    assignChannelDataElement(channelData, element);
                    break;
                case CALL_STATS:
                    CallStats callStats = new CallStats();
                    cdr.setCallStats(callStats);
                    assignCallStatsElement(callStats, element);
                    break;
                case VARIABLES:
                    Variables variables = new Variables();
                    cdr.setVariables(variables);
                    assignVariablesElement(variables, element);
                    break;
                case APP_LOG:
                    AppLog appLog = new AppLog();
                    cdr.setAppLog(appLog);
                    assignAppLogElement(appLog, element);
                    break;
                case HOLD_RECORD:
                    HoldRecord holdRecord = new HoldRecord();
                    cdr.setHoldRecord(holdRecord);
                    assignHoldRecordElement(holdRecord, element);
                    break;
                case CALLFLOW:
                    Callflow callflow = new Callflow();
                    cdr.addCallflow(callflow);
                    assignCallflowElement(callflow, element);
                    break;
                default:
                    log.warn("assignCdrElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }


    private static void assignChannelDataElement(ChannelData channelData, Element rootElement) {
        // channel_data 节点属性赋值
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case STATE:
                    channelData.setState(value);
                    break;
                case DIRECTION:
                    channelData.setDirection(value);
                    break;
                case STATE_NUMBER:
                    channelData.setStateNumber(value);
                    break;
                case FLAGS:
                    channelData.setFlags(value);
                    break;
                case CAPS:
                    channelData.setCaps(value);
                    break;
                default:
                    log.warn("assignChannelDataElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }

    private static final String AUDIO = "audio";

    private static void assignCallStatsElement(CallStats callStats, Element rootElement) {
        // call-stats 节点属性赋值
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case AUDIO:
                    Audio audio = new Audio();
                    callStats.setAudio(audio);
                    assignAudioElement(audio, element);
                    break;
                default:
                    log.warn("assignCallStatsElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }

    private static final String INBOUND = "inbound";
    private static final String OUTBOUND = "outbound";
    private static final String ERROR_LOG = "error-log";

    private static void assignAudioElement(Audio audio, Element rootElement) {
        // call-stats - audio 节点属性赋值
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case INBOUND:
                    Inbound inbound = new Inbound();
                    audio.setInbound(inbound);
                    assignInboundElement(inbound, element);
                    break;
                case OUTBOUND:
                    Outbound outbound = new Outbound();
                    audio.setOutbound(outbound);
                    assignOutboundElement(outbound, element);
                    break;
                case ERROR_LOG:
                    ErrorLog errorLog = new ErrorLog();
                    audio.setErrorLog(errorLog);
                    assignErrorLogElement(errorLog, element);
                    break;
                default:
                    log.warn("assignAudioElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }


    private static final String RAW_BYTES = "raw_bytes";
    private static final String MEDIA_BYTES = "media_bytes";
    private static final String PACKET_COUNT = "packet_count";
    private static final String MEDIA_PACKET_COUNT = "media_packet_count";
    private static final String SKIP_PACKET_COUNT = "skip_packet_count";
    private static final String JITTER_PACKET_COUNT = "jitter_packet_count";
    private static final String DTMF_PACKET_COUNT = "dtmf_packet_count";
    private static final String CNG_PACKET_COUNT = "cng_packet_count";
    private static final String FLUSH_PACKET_COUNT = "flush_packet_count";
    private static final String LARGEST_JB_SIZE = "largest_jb_size";
    private static final String JITTER_MIN_VARIANCE = "jitter_min_variance";
    private static final String JITTER_MAX_VARIANCE = "jitter_max_variance";
    private static final String JITTER_LOSS_RATE = "jitter_loss_rate";
    private static final String JITTER_BURST_RATE = "jitter_burst_rate";
    private static final String MEAN_INTERVAL = "mean_interval";
    private static final String FLAW_TOTAL = "flaw_total";
    private static final String QUALITY_PERCENTAGE = "quality_percentage";
    private static final String MOS = "mos";

    private static void assignInboundElement(Inbound inbound, Element rootElement) {
        // call-stats - audio - inbound 节点属性赋值
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case RAW_BYTES:
                    inbound.setRawBytes(value);
                    break;
                case MEDIA_BYTES:
                    inbound.setMediaBytes(value);
                    break;
                case PACKET_COUNT:
                    inbound.setPacketCount(value);
                    break;
                case MEDIA_PACKET_COUNT:
                    inbound.setMediaPacketCount(value);
                    break;
                case SKIP_PACKET_COUNT:
                    inbound.setSkipPacketCount(value);
                    break;
                case JITTER_PACKET_COUNT:
                    inbound.setJitterPacketCount(value);
                    break;
                case DTMF_PACKET_COUNT:
                    inbound.setDtmfPacketCount(value);
                    break;
                case CNG_PACKET_COUNT:
                    inbound.setCngPacketCount(value);
                    break;
                case FLUSH_PACKET_COUNT:
                    inbound.setFlushPacketCount(value);
                    break;
                case LARGEST_JB_SIZE:
                    inbound.setLargestJbSize(value);
                    break;
                case JITTER_MIN_VARIANCE:
                    inbound.setJitterMinVariance(value);
                    break;
                case JITTER_MAX_VARIANCE:
                    inbound.setJitterMaxVariance(value);
                    break;
                case JITTER_LOSS_RATE:
                    inbound.setJitterLossRate(value);
                    break;
                case JITTER_BURST_RATE:
                    inbound.setJitterBurstRate(value);
                    break;
                case MEAN_INTERVAL:
                    inbound.setMeanInterval(value);
                    break;
                case FLAW_TOTAL:
                    inbound.setFlawTotal(value);
                    break;
                case QUALITY_PERCENTAGE:
                    inbound.setQualityPercentage(value);
                    break;
                case MOS:
                    inbound.setMos(value);
                    break;
                default:
                    log.warn("assignInboundElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }

    private static final String RTCP_PACKET_COUNT = "rtcp_packet_count";
    private static final String RTCP_OCTET_COUNT = "rtcp_octet_count";

    private static void assignOutboundElement(Outbound outbound, Element rootElement) {
        // call-stats - audio - outbound 节点属性赋值
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case RAW_BYTES:
                    outbound.setRawBytes(value);
                    break;
                case MEDIA_BYTES:
                    outbound.setMediaBytes(value);
                    break;
                case PACKET_COUNT:
                    outbound.setPacketCount(value);
                    break;
                case MEDIA_PACKET_COUNT:
                    outbound.setMediaPacketCount(value);
                    break;
                case SKIP_PACKET_COUNT:
                    outbound.setSkipPacketCount(value);
                    break;
                case DTMF_PACKET_COUNT:
                    outbound.setDtmfPacketCount(value);
                    break;
                case CNG_PACKET_COUNT:
                    outbound.setCngPacketCount(value);
                    break;
                case RTCP_PACKET_COUNT:
                    outbound.setRtcpPacketCount(value);
                    break;
                case RTCP_OCTET_COUNT:
                    outbound.setRtcpOctetCount(value);
                    break;
                default:
                    log.warn("assignOutboundElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }

    private static final String ERROR_PERIOD = "error-period";

    private static void assignErrorLogElement(ErrorLog errorLog, Element rootElement) {
        // call-stats - audio - error-log 节点属性赋值
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case ERROR_PERIOD:
                    ErrorPeriod errorPeriod = new ErrorPeriod();
                    errorLog.addErrorPeriod(errorPeriod);
                    assignErrorPeriodElement(errorPeriod, element);
                    break;
                default:
                    log.warn("assignErrorLogElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }

    private static final String START = "start";
    private static final String STOP = "stop";
    private static final String FLAWS = "flaws";
    private static final String CONSECUTIVE_FLAWS = "consecutive-flaws";
    private static final String DURATION_MSEC = "duration-msec";

    private static void assignErrorPeriodElement(ErrorPeriod errorPeriod, Element rootElement) {
        // call-stats - audio - error-log 节点属性赋值
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case START:
                    errorPeriod.setStart(value);
                    break;
                case STOP:
                    errorPeriod.setStop(value);
                    break;
                case FLAWS:
                    errorPeriod.setFlaws(value);
                    break;
                case CONSECUTIVE_FLAWS:
                    errorPeriod.setConsecutiveFlaws(value);
                    break;
                case DURATION_MSEC:
                    errorPeriod.setDurationMsec(value);
                    break;
                default:
                    log.warn("assignErrorPeriodElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });
    }

    private static void assignVariablesElement(Variables variables, Element rootElement) {
        elements(rootElement, (name, element) -> variables.putVariable(name, element.getTextTrim()));
    }

    private static final String HOLD = "hold";

    private static void assignHoldRecordElement(HoldRecord holdRecord, Element rootElement) {
        List<Hold> holds = new ArrayList<>(4);
        holdRecord.setHolds(holds);
        elements(rootElement, (name, element) -> {
            if (HOLD.equals(name)) {
                assignHoldElement(holds, element);
            } else {
                log.warn("assignHoldRecordElement found other element name : [{}], xml : [{}]", name, local.get());
            }
        });
    }

    private static final String ON = "on";
    private static final String OFF = "off";
    private static final String BRIDGED_TO = "bridged-to";

    private static void assignHoldElement(final List<Hold> holds, final Element rootElement) {
        Hold hold = new Hold();
        attributes(rootElement, (name, value) -> {
            switch (name) {
                case ON:
                    hold.setOn(NumberUtil.toLong(value));
                    break;
                case OFF:
                    hold.setOff(NumberUtil.toLong(value));
                    break;
                case BRIDGED_TO:
                    hold.setBridgedTo(value);
                    break;
                default:
                    log.warn("assignHoldElement found other attribute name : [{}], value : [{}], xml : [{}]", name, value, local.get());
                    break;
            }
        });
        holds.add(hold);
    }

    private static final String APPLICATION = "application";

    private static void assignAppLogElement(AppLog appLog, Element rootElement) {
        List<Application> applications = new ArrayList<>();
        appLog.setApplications(applications);
        elements(rootElement, (name, element) -> {
            if (APPLICATION.equals(name)) {
                assignApplicationElement(applications, element);
            } else {
                log.warn("assignAppLogElement found other element name : [{}], xml : [{}]", name, local.get());
            }
        });
    }

    private static final String APP_NAME = "app_name";
    private static final String APP_DATA = "app_data";
    private static final String APP_STAMP = "app_stamp";


    private static void assignApplicationElement(final List<Application> applications, final Element rootElement) {
        Application application = new Application();
        attributes(rootElement, (name, value) -> {
            switch (name) {
                case APP_NAME:
                    application.setAppName(value);
                    break;
                case APP_DATA:
                    application.setAppData(value);
                    break;
                case APP_STAMP:
                    application.setAppStamp(NumberUtil.toLong(value));
                    break;
                default:
                    log.warn("assignApplicationElement found other attribute name : [{}], value : [{}], xml : [{}]", name, value, local.get());
                    break;
            }
        });
        applications.add(application);
    }

    private static final String DIALPLAN = "dialplan";
    private static final String UNIQUE_ID = "unique-id";
    private static final String CLONE_OF = "clone-of";
    private static final String PROFILE_INDEX = "profile_index";
    private static final String EXTENSION = "extension";
    private static final String CALLER_PROFILE = "caller_profile";
    private static final String TIMES = "times";

    private static void assignCallflowElement(Callflow callflow, Element rootElement) {
        // 属性
        attributes(rootElement, (name, value) -> {
            switch (name) {
                case DIALPLAN:
                    callflow.setDialplan(value);
                    break;
                case UNIQUE_ID:
                    callflow.setUniqueId(value);
                    break;
                case CLONE_OF:
                    callflow.setCloneOf(value);
                    break;
                case PROFILE_INDEX:
                    callflow.setProfileIndex(value);
                    break;
                default:
                    log.warn("assignCallflowElement found other attribute name : [{}], value : [{}], xml : [{}]", name, value, local.get());
                    break;
            }
        });

        // 子元素
        elements(rootElement, (name, element) -> {
            switch (name) {
                case EXTENSION:
                    Extension extension = new Extension();
                    callflow.setExtension(extension);
                    assignExtensionElement(extension, element);
                    break;
                case CALLER_PROFILE:
                    CallerProfile callerProfile = new CallerProfile();
                    callflow.setCallerProfile(callerProfile);
                    assignCallerProfileElement(callerProfile, element);
                    break;
                case TIMES:
                    Times times = new Times();
                    callflow.setTimes(times);
                    assignTimesElement(times, element);
                    break;
                default:
                    log.warn("assignCallflowChildElement found other element name : [{}]], xml : [{}]", name, local.get());
                    break;
            }

        });
    }

    private static final String NAME = "name";
    private static final String NUMBER = "number";


    private static void assignExtensionElement(Extension extension, Element rootElement) {
        // 属性
        attributes(rootElement, (name, value) -> {
            switch (name) {
                case NAME:
                    extension.setName(value);
                    break;
                case NUMBER:
                    extension.setNumber(value);
                    break;
                default:
                    log.warn("assignExtensionElement found other attribute name : [{}], value : [{}], xml : [{}]", name, value, local.get());
                    break;
            }
        });

        List<Application> applications = new ArrayList<>();
        extension.setApplications(applications);
        elements(rootElement, (name, element) -> {
            if (APPLICATION.equals(name)) {
                assignApplicationElement(applications, element);
            } else {
                log.warn("assignExtensionElement found other element name : [{}], xml : [{}]", name, local.get());
            }
        });
    }

    private static final String USERNAME = "username";
    private static final String CALLER_ID_NAME = "caller_id_name";
    private static final String CALLER_ID_NUMBER = "caller_id_number";
    private static final String CALLEE_ID_NAME = "callee_id_name";
    private static final String CALLEE_ID_NUMBER = "callee_id_number";
    private static final String ANI = "ani";
    private static final String ANIII = "aniii";
    private static final String NETWORK_ADDR = "network_addr";
    private static final String RDNIS = "rdnis";
    private static final String DESTINATION_NUMBER = "destination_number";
    private static final String UUID = "uuid";
    private static final String SOURCE = "source";
    private static final String TRANSFER_SOURCE = "transfer_source";
    private static final String CONTEXT = "context";
    private static final String CHAN_NAME = "chan_name";
    private static final String ORIGINATOR = "originator";
    private static final String ORIGINATION = "origination";
    private static final String ORIGINATEE = "originatee";

    private static void assignCallerProfileElement(CallerProfile callerProfile, Element rootElement) {

        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case USERNAME:
                    callerProfile.setUsername(value);
                    break;
                case DIALPLAN:
                    callerProfile.setDialplan(value);
                    break;
                case CALLER_ID_NAME:
                    callerProfile.setCallerIdName(value);
                    break;
                case CALLER_ID_NUMBER:
                    callerProfile.setCallerIdNumber(value);
                    break;
                case CALLEE_ID_NAME:
                    callerProfile.setCalleeIdName(value);
                    break;
                case CALLEE_ID_NUMBER:
                    callerProfile.setCalleeIdNumber(value);
                    break;
                case ANI:
                    callerProfile.setAni(value);
                    break;
                case ANIII:
                    callerProfile.setAniii(value);
                    break;
                case NETWORK_ADDR:
                    callerProfile.setNetworkAddr(value);
                    break;
                case RDNIS:
                    callerProfile.setRdnis(value);
                    break;
                case DESTINATION_NUMBER:
                    callerProfile.setDestinationNumber(value);
                    break;
                case UUID:
                    callerProfile.setUuid(value);
                    break;
                case SOURCE:
                    callerProfile.setSource(value);
                    break;
                case TRANSFER_SOURCE:
                    callerProfile.setTransferSource(value);
                    break;
                case CONTEXT:
                    callerProfile.setContext(value);
                    break;
                case CHAN_NAME:
                    callerProfile.setChanName(value);
                    break;
                case ORIGINATOR:
                    Originator originator = new Originator();
                    callerProfile.setOriginator(originator);
                    assignOriginatorElement(originator, element);
                    break;
                case ORIGINATION:
                    Origination origination = new Origination();
                    callerProfile.setOrigination(origination);
                    assignOriginationElement(origination, element);
                    break;
                case ORIGINATEE:
                    Originatee originatee = new Originatee();
                    callerProfile.setOriginatee(originatee);
                    assignOriginateeElement(originatee, element);
                    break;
                default:
                    log.warn("assignCallerProfileElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });

    }

    private static final String ORIGINATOR_CALLER_PROFILE = "originator_caller_profile";

    private static void assignOriginatorElement(Originator originator, Element rootElement) {
        elements(rootElement, (name, element) -> {
            if (ORIGINATOR_CALLER_PROFILE.equals(name)) {
                OriginatorCallerProfile originatorCallerProfile = new OriginatorCallerProfile();
                originator.setOriginatorCallerProfile(originatorCallerProfile);
                assignOriginatorCallerProfileElement(originatorCallerProfile, element);
            } else {
                log.warn("assignOriginatorElement found other element name : [{}], xml : [{}]", name, local.get());
            }
        });
    }

    private static void assignOriginatorCallerProfileElement(OriginatorCallerProfile originatorCallerProfile, Element rootElement) {
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case USERNAME:
                    originatorCallerProfile.setUsername(value);
                    break;
                case DIALPLAN:
                    originatorCallerProfile.setDialplan(value);
                    break;
                case CALLER_ID_NAME:
                    originatorCallerProfile.setCallerIdName(value);
                    break;
                case CALLER_ID_NUMBER:
                    originatorCallerProfile.setCallerIdNumber(value);
                    break;
                case CALLEE_ID_NAME:
                    originatorCallerProfile.setCalleeIdName(value);
                    break;
                case CALLEE_ID_NUMBER:
                    originatorCallerProfile.setCalleeIdNumber(value);
                    break;
                case ANI:
                    originatorCallerProfile.setAni(value);
                    break;
                case ANIII:
                    originatorCallerProfile.setAniii(value);
                    break;
                case NETWORK_ADDR:
                    originatorCallerProfile.setNetworkAddr(value);
                    break;
                case RDNIS:
                    originatorCallerProfile.setRdnis(value);
                    break;
                case DESTINATION_NUMBER:
                    originatorCallerProfile.setDestinationNumber(value);
                    break;
                case UUID:
                    originatorCallerProfile.setUuid(value);
                    break;
                case SOURCE:
                    originatorCallerProfile.setSource(value);
                    break;
                case CONTEXT:
                    originatorCallerProfile.setContext(value);
                    break;
                case CHAN_NAME:
                    originatorCallerProfile.setChanName(value);
                    break;
                default:
                    log.warn("assignOriginationCallerProfileElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;

            }
        });
    }

    private static final String ORIGINATION_CALLER_PROFILE = "origination_caller_profile";

    private static void assignOriginationElement(Origination origination, Element rootElement) {
        elements(rootElement, (name, element) -> {
            if (ORIGINATION_CALLER_PROFILE.equals(name)) {
                OriginationCallerProfile originationCallerProfile = new OriginationCallerProfile();
                origination.setOriginationCallerProfile(originationCallerProfile);
                assignOriginationCallerProfileElement(originationCallerProfile, element);
            } else {
                log.warn("assignOriginationElement found other element name : [{}], xml : [{}]", name, local.get());
            }
        });

    }

    private static void assignOriginationCallerProfileElement(OriginationCallerProfile originationCallerProfile, Element rootElement) {
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case USERNAME:
                    originationCallerProfile.setUsername(value);
                    break;
                case DIALPLAN:
                    originationCallerProfile.setDialplan(value);
                    break;
                case CALLER_ID_NAME:
                    originationCallerProfile.setCallerIdName(value);
                    break;
                case CALLER_ID_NUMBER:
                    originationCallerProfile.setCallerIdNumber(value);
                    break;
                case CALLEE_ID_NAME:
                    originationCallerProfile.setCalleeIdName(value);
                    break;
                case CALLEE_ID_NUMBER:
                    originationCallerProfile.setCalleeIdNumber(value);
                    break;
                case ANI:
                    originationCallerProfile.setAni(value);
                    break;
                case ANIII:
                    originationCallerProfile.setAniii(value);
                    break;
                case NETWORK_ADDR:
                    originationCallerProfile.setNetworkAddr(value);
                    break;
                case RDNIS:
                    originationCallerProfile.setRdnis(value);
                    break;
                case DESTINATION_NUMBER:
                    originationCallerProfile.setDestinationNumber(value);
                    break;
                case UUID:
                    originationCallerProfile.setUuid(value);
                    break;
                case SOURCE:
                    originationCallerProfile.setSource(value);
                    break;
                case CONTEXT:
                    originationCallerProfile.setContext(value);
                    break;
                case CHAN_NAME:
                    originationCallerProfile.setChanName(value);
                    break;
                default:
                    log.warn("assignOriginationCallerProfileElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;

            }
        });
    }

    private static final String ORIGINATEE_CALLER_PROFILE = "originatee_caller_profile";

    private static void assignOriginateeElement(Originatee originatee, Element rootElement) {
        elements(rootElement, (name, element) -> {
            if (ORIGINATEE_CALLER_PROFILE.equals(name)) {
                OriginateeCallerProfile originateeCallerProfile = new OriginateeCallerProfile();
                originatee.setOriginateeCallerProfile(originateeCallerProfile);
                assignOriginateeCallerProfileElement(originateeCallerProfile, element);
            } else {
                log.warn("assignOriginationElement found other element name : [{}], xml : [{}]", name, local.get());
            }
        });

    }

    private static void assignOriginateeCallerProfileElement(OriginateeCallerProfile originateeCallerProfile, Element rootElement) {
        elements(rootElement, (name, element) -> {
            String value = element.getTextTrim();
            switch (name) {
                case USERNAME:
                    originateeCallerProfile.setUsername(value);
                    break;
                case DIALPLAN:
                    originateeCallerProfile.setDialplan(value);
                    break;
                case CALLER_ID_NAME:
                    originateeCallerProfile.setCallerIdName(value);
                    break;
                case CALLER_ID_NUMBER:
                    originateeCallerProfile.setCallerIdNumber(value);
                    break;
                case CALLEE_ID_NAME:
                    originateeCallerProfile.setCalleeIdName(value);
                    break;
                case CALLEE_ID_NUMBER:
                    originateeCallerProfile.setCalleeIdNumber(value);
                    break;
                case ANI:
                    originateeCallerProfile.setAni(value);
                    break;
                case ANIII:
                    originateeCallerProfile.setAniii(value);
                    break;
                case NETWORK_ADDR:
                    originateeCallerProfile.setNetworkAddr(value);
                    break;
                case RDNIS:
                    originateeCallerProfile.setRdnis(value);
                    break;
                case DESTINATION_NUMBER:
                    originateeCallerProfile.setDestinationNumber(value);
                    break;
                case UUID:
                    originateeCallerProfile.setUuid(value);
                    break;
                case SOURCE:
                    originateeCallerProfile.setSource(value);
                    break;
                case CONTEXT:
                    originateeCallerProfile.setContext(value);
                    break;
                case CHAN_NAME:
                    originateeCallerProfile.setChanName(value);
                    break;
                default:
                    log.warn("assignOriginateeCallerProfileElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;

            }
        });
    }

    private static final String CREATED_TIME = "created_time";
    private static final String PROFILE_CREATED_TIME = "profile_created_time";
    private static final String PROGRESS_TIME = "progress_time";
    private static final String PROGRESS_MEDIA_TIME = "progress_media_time";
    private static final String ANSWERED_TIME = "answered_time";
    private static final String BRIDGED_TIME = "bridged_time";
    private static final String LAST_HOLD_TIME = "last_hold_time";
    private static final String HOLD_ACCUM_TIME = "hold_accum_time";
    private static final String HANGUP_TIME = "hangup_time";
    private static final String RESURRECT_TIME = "resurrect_time";
    private static final String TRANSFER_TIME = "transfer_time";

    private static void assignTimesElement(Times times, Element rootElement) {
        elements(rootElement, (name, element) -> {
            String value0 = element.getTextTrim();
            Long value = NumberUtil.toLong(value0);
            switch (name) {
                case CREATED_TIME:
                    times.setCreatedTime(value);
                    break;
                case PROFILE_CREATED_TIME:
                    times.setProfileCreatedTime(value);
                    break;
                case PROGRESS_TIME:
                    times.setProgressTime(value);
                    break;
                case PROGRESS_MEDIA_TIME:
                    times.setProgressMediaTime(value);
                    break;
                case ANSWERED_TIME:
                    times.setAnsweredTime(value);
                    break;
                case BRIDGED_TIME:
                    times.setBridgedTime(value);
                    break;
                case LAST_HOLD_TIME:
                    times.setLastHoldTime(value);
                    break;
                case HOLD_ACCUM_TIME:
                    times.setHoldAccumTime(value);
                    break;
                case HANGUP_TIME:
                    times.setHangupTime(value);
                    break;
                case RESURRECT_TIME:
                    times.setResurrectTime(value);
                    break;
                case TRANSFER_TIME:
                    times.setTransferTime(value);
                    break;
                default:
                    log.warn("assignTimesElement found other element name : [{}], xml : [{}]", name, local.get());
                    break;
            }
        });

    }

    private static void attributes(Element element, BiConsumer<String, String> consumer) {
        List<Attribute> attributes = element.attributes();
        for (Attribute attribute : attributes) {
            attribute.getName();
            consumer.accept(attribute.getName(), attribute.getValue());
        }
    }

    private static void elements(Element rootElement, BiConsumer<String, Element> consumer) {
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            consumer.accept(element.getName(), element);
        }
    }
}
