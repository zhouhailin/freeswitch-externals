package link.thingscloud.freeswitch.esl.internal;

import link.thingscloud.freeswitch.esl.constant.PlaybackLeg;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

@Slf4j
public abstract class Execute implements IModEslApi {
//
//    /**
//     * Sends an info packet with a sipfrag. If the phone supports it will show
//     * message on the display.
//     *
//     * @param message to display
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_Send_Display">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_Send_Display
//     *     </a>
//     */
//    public boolean sendDisplay(String message) {
//        CommandResponse resp = execute("send_display", message);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Answers an incoming call or session.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_answer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_answer
//     *     </a>
//     */
//    public boolean answer() {
//        CommandResponse resp = execute("answer");
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Make an attended transfer.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_att_xfer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_att_xfer
//     *     </a>
//     * @param channelUrl
//     *            ex: sofia/default/${attxfer_callthis}
//     */
//    public boolean attAnswer(String channelUrl) {
//        CommandResponse resp = execute("att_xfer", channelUrl);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     *
//     * @param key
//     *            the button you want to respond to after the * button is
//     *            pressed. If you wanted to respond to *1, you would put 1 in
//     *            place of KEY. You are limited to a single digit.
//     * @param leg
//     *            which call leg(s) to listen on. Acceptable parameters are a, b
//     *            or ab.
//     * @param flags
//     *            modifies the behavior. The following flags are available: a -
//     *            Respond on A leg, b - Respond on B leg, o - Respond on
//     *            opposite leg, s - Respond on same leg, i - Execute inline, 1 -
//     *            Unbind this meta_app after it is used one time
//     * @param application
//     *            is which application you want to execute.
//     * @param params
//     *            are the arguments you want or need to provide to the
//     *            APPLICATION.
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_bind_meta_app">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_bind_meta_app
//     *     </a>
//     */
//    public boolean bindMetaApp(String key, String leg, String flags, String application, String params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(key).append(" ").append(leg);
//        sb.append(flags).append(" ").append(application);
//        sb.append("::").append(params);
//        CommandResponse resp = execute("bind_meta_app", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Cancels currently running application on the given UUID. Dialplan
//     * execution proceeds to the next application. Optionally clears all
//     * unprocessed events (queued applications) on the channel.
//     *
//     * @param all
//     *            clear all unprocessed events (queued applications) on the
//     *            channel, otherwise just the current application.
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_break">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_break
//     *     </a>
//     */
//    public boolean breakChannel(boolean all) {
//        CommandResponse resp = execute("break", all ? "all" : "");
//        return resp != null && resp.isOk();
//    }

    /**
     * Provides the ability to bridge two endpoints. Generally used to route an
     * incoming call to one or more endpoints. Multiple endpoints can be dialed
     * simultaneously or sequentially using the comma and pipe delimiters,
     * respectively.
     *
     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_bridge">
     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_bridge
     *     </a>
     */
    public boolean bridge(String endpoint) {
        CommandResponse resp = executeApp("bridge", endpoint);
        return resp != null && resp.isOk();
    }

//    /**
//     * Export a channel variable across a bridge. This application differs from
//     * export in that it works with *any* kind of bridge, not just a bridge
//     * called from the dialplan. For example, bridge_export will export its
//     * variables if the leg is uuid_transfer'd whereas export will not
//     *
//     * @param key channel variable name
//     * @param value channel variable value
//     * @param local to only export to the B leg false, otherwise true
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_bridge_export">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_bridge_export
//     *     </a>
//     */
//    public boolean bridgeExport(String key, String value, boolean local) {
//        StringBuilder sb = new StringBuilder();
//        if(!local) sb.append("nolocal:");
//        sb.append(key).append("=").append(value);
//        CommandResponse resp = execute("bridge_export", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Send a text message to a IM client.
//     *
//     * @param proto ex: sip
//     * @param from ex: 1000@127.0.0.1
//     * @param to ex: 1001@127.0.0.1
//     * @param message ex: Hello chat from freeswitch
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_chat">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_chat
//     *     </a>
//     */
//    public boolean chat(String proto, String from, String to, String message) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(proto).append("|").append(from);
//        sb.append("|").append(to).append("|").append(message);
//        CommandResponse resp = execute("chat", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * cng plc is just an app that says to perform plc on any lost packets and
//     * execute on originate. It is like execute on answer, etc. but only for
//     * outbound calls during originate.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_cng_plc">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_cng_plc
//     *     </a>
//     */
//    public boolean cngPlc() {
//        CommandResponse resp = execute("cng_plc");
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Start or join a conference
//     */
//    public boolean conference(String name) {
//        return conference(name, null, null, null);
//    }
//
//    /**
//     * Start or join a conference
//     */
//    public boolean conference(String name, String profile) {
//        return conference(name,profile,null,null);
//    }
//
//    /**
//     * Start or join a conference
//     */
//    public boolean conference(String name, String profile, String pin) {
//        return conference(name,profile,pin,null);
//    }
//
//    /**
//     * Start or join a conference
//     */
//    public boolean conference(String name, String profile, String pin, String flags) {
//        StringBuilder sb = new StringBuilder(name);
//        if(StringUtils.isNotBlank(profile))
//            sb.append("@").append(profile);
//        if(StringUtils.isNotBlank(pin))
//            sb.append("+").append(pin);
//        if(StringUtils.isNotBlank(flags))
//            sb.append("+flags{").append(flags).append("}");
//        CommandResponse resp = execute("conference", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Deflect sends a Refer to the client. The deflect application allows
//     * FreeSWITCH to be removed from the list of connection hops and tell the
//     * originator to reroute the call. When using the deflect application,
//     * FreeSWITCH first hangs up the channel and then send a REFER message and a
//     * new INVITE message to the originator. The originator, which could be a
//     * gateway or sip proxy, should read the INVITE and reroute the call
//     * accordingly.
//     *
//     * @param endpoint
//     *            SIP URI to contact (with or without "sip:")
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_deflect">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_deflect
//     *     </a>
//     */
//    public boolean deflect(String endpoint) {
//        CommandResponse resp = execute("deflect", endpoint);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Places the calling channel in delayed loopback mode. It simply returns
//     * everything sent, including voice, DTMF, etc but with the specified delay
//     * [ms]. It is generally useful for checking if RTP audio path works both
//     * ways. Normal echo app can fail when tested on speaker phone.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_delay_echo">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_delay_echo
//     *     </a>
//     */
//    public boolean delayEcho(long ms) {
//        CommandResponse resp = execute("delay_echo", ms + "");
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Implements speech recognition.
//     *
//     * @param args
//     *            <mod_name> <gram_name> <gram_path> [<addr>] grammar
//     *            <gram_name> [<path>] grammaron <gram_name> grammaroff
//     *            <gram_name> grammarsalloff nogrammar <gram_name> param <name>
//     *            <value> pause resume start_input_timers stop
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_detect_speech">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_detect_speech
//     *     </a>
//     */
//    public boolean detectSpeech(String args) {
//        CommandResponse resp = execute("detect_speech", args);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Displace file. Plays a file or stream to a channel.
//     *
//     * @param path
//     *            any sound format FreeSWITCH supports, wav, local_steam, shout
//     *            etc.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_displace_session">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_displace_session
//     *     </a>
//     */
//    public boolean displaceSession(String path) {
//        return displaceSession(path, null, 0);
//    }
//
//    /**
//     * Displace file. Plays a file or stream to a channel.
//     *
//     * @param path
//     *            any sound format FreeSWITCH supports, wav, local_steam, shout
//     *            etc.
//     * @param flags
//     *            flags to stream, null if none
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_displace_session">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_displace_session
//     *     </a>
//     */
//    public boolean displaceSession(String path, String flags) {
//        return displaceSession(path, flags, 0);
//    }
//
//    /**
//     * Displace file. Plays a file or stream to a channel.
//     *
//     * @param path
//     *            any sound format FreeSWITCH supports, wav, local_steam, shout
//     *            etc.
//     * @param flags
//     *            flags to stream, null if none
//     * @param timeLimitMillis
//     *            optional time limit, 0 for none
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_displace_session">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_displace_session
//     *     </a>
//     */
//    public boolean displaceSession(String path, String flags, long timeLimitMillis) {
//        StringBuilder sb = new StringBuilder(path);
//        if(StringUtils.isNotBlank(flags))
//            sb.append(" ").append(flags);
//        if(timeLimitMillis > 0 )
//            sb.append(" +").append(timeLimitMillis);
//
//        CommandResponse resp = execute("displace_session",sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Provides the ability to spy on a channel. It often referred to as call
//     * barge. For persistent spying on a user see Mod_spy.
//     *
//     * DTMF signals during eavesdrop, 2 to speak with the uuid, 1 to speak with
//     * the other half, 3 to engage a three way, 0 to restore eavesdrop, * to
//     * next channel.
//     *
//     * @param uuid
//     *            uuid of the call or 'all' for all
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop
//     *     </a>
//     */
//    public boolean eavesdrop(String uuid) {
//        return eavesdrop(uuid, false, null, null, null, null);
//    }
//
//    /**
//     * Provides the ability to spy on a channel. It often referred to as call
//     * barge. For persistent spying on a user see Mod_spy.
//     *
//     * DTMF signals during eavesdrop, 2 to speak with the uuid, 1 to speak with
//     * the other half, 3 to engage a three way, 0 to restore eavesdrop, * to
//     * next channel.
//     *
//     * @param uuid
//     *            uuid of the call or 'all' for all
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop
//     *     </a>
//     */
//    public boolean eavesdrop(String uuid, boolean enableDTMF) {
//        return eavesdrop(uuid, enableDTMF, null, null, null, null);
//    }
//
//    /**
//     * Provides the ability to spy on a channel. It often referred to as call
//     * barge. For persistent spying on a user see Mod_spy.
//     *
//     * DTMF signals during eavesdrop, 2 to speak with the uuid, 1 to speak with
//     * the other half, 3 to engage a three way, 0 to restore eavesdrop, * to
//     * next channel.
//     *
//     * @param uuid
//     *            uuid of the call or 'all' for all
//     * @param groupId
//     *            if specified, eavesdrop only works with channels that have an
//     *            "eavesdrop_group" channel variable set to the same name.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop
//     *     </a>
//     */
//    public boolean eavesdrop(String uuid, boolean enableDTMF, String groupId) {
//        return eavesdrop(uuid, enableDTMF, groupId, null, null, null);
//    }
//
//    /**
//     * Provides the ability to spy on a channel. It often referred to as call
//     * barge. For persistent spying on a user see Mod_spy.
//     *
//     * DTMF signals during eavesdrop, 2 to speak with the uuid, 1 to speak with
//     * the other half, 3 to engage a three way, 0 to restore eavesdrop, * to
//     * next channel.
//     *
//     * @param uuid
//     *            uuid of the call or 'all' for all
//     * @param groupId
//     *            if specified, eavesdrop only works with channels that have an
//     *            "eavesdrop_group" channel variable set to the same name.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop
//     *     </a>
//     */
//    public boolean eavesdrop(String uuid, boolean enableDTMF, String groupId, String failedWav) {
//        return eavesdrop(uuid, enableDTMF, groupId, failedWav, null, null);
//    }
//
//    /**
//     * Provides the ability to spy on a channel. It often referred to as call
//     * barge. For persistent spying on a user see Mod_spy.
//     *
//     * DTMF signals during eavesdrop, 2 to speak with the uuid, 1 to speak with
//     * the other half, 3 to engage a three way, 0 to restore eavesdrop, * to
//     * next channel.
//     *
//     * @param uuid
//     *            uuid of the call or 'all' for all
//     * @param groupId
//     *            if specified, eavesdrop only works with channels that have an
//     *            "eavesdrop_group" channel variable set to the same name.
//     * @param failedWav
//     *            ex: /sounds/failed.wav
//     * @param newChannelWav
//     *            ex: /sounds/new_chan_announce.wav
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop
//     *     </a>
//     */
//    public boolean eavesdrop(String uuid, boolean enableDTMF, String groupId,
//            String failedWav, String newChannelWav) {
//        return eavesdrop(uuid, enableDTMF, groupId, failedWav, newChannelWav, null);
//    }
//
//    /**
//     * Provides the ability to spy on a channel. It often referred to as call
//     * barge. For persistent spying on a user see Mod_spy.
//     *
//     * DTMF signals during eavesdrop, 2 to speak with the uuid, 1 to speak with
//     * the other half, 3 to engage a three way, 0 to restore eavesdrop, * to
//     * next channel.
//     *
//     * @param uuid
//     *            uuid of the call or 'all' for all
//     * @param groupId
//     *            if specified, eavesdrop only works with channels that have an
//     *            "eavesdrop_group" channel variable set to the same name.
//     * @param failedWav
//     *            ex: /sounds/failed.wav
//     * @param newChannelWav
//     *            ex: /sounds/new_chan_announce.wav
//     * @param idleWav
//     *            ex: /sounds/idle.wav
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eavesdrop
//     *     </a>
//     */
//    public boolean eavesdrop(String uuid, boolean enableDTMF, String groupId,
//            String failedWav, String newChannelWav, String idleWav) {
//
//        if (StringUtils.isNotBlank(groupId))
//            set("eavesdrop_require_group", groupId);
//        if (StringUtils.isNotBlank(failedWav))
//            set("eavesdrop_indicate_failed", failedWav);
//        if (StringUtils.isNotBlank(newChannelWav))
//            set("eavesdrop_indicate_new", newChannelWav);
//        if (StringUtils.isNotBlank(idleWav))
//            set("eavesdrop_indicate_idle", idleWav);
//
//        set("eavesdrop_enable_dtmf", String.valueOf(enableDTMF));
//
//        CommandResponse resp = execute("eavesdrop", uuid);
//        return resp != null && resp.isOk();
//    }
//
//
//    /**
//     * Places the calling channel in loopback mode. It simply returns everything
//     * sent, including voice, DTMF, etc. Consider it an "echo test". It is
//     * generally useful for checking delay in a call path.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_echo">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_echo
//     *     </a>
//     */
//    public boolean echo() {
//        CommandResponse resp = execute("echo");
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * This application is used to play a file endlessly and the playing cannot
//     * be stopped externally.
//     *
//     * @param file
//     *            to play
//     */
//    public boolean endlessPlayback(String file) {
//        CommandResponse resp = execute("endless_playback", file);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Playback a file to the channel looply for limted times.
//     * But it can not set pause time in between, like playback，playback_sleep_val，file_string..
//     *
//     * @param file
//     *            to play
//     */
//    public boolean loopPlayback(String file, int loops) {
//        String args = "+" + loops + " " + file;
//        CommandResponse resp = execute("loop_playback", args);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Eval can be used to execute an internal API or simply log some text to
//     * the console.
//     *
//     * @param string
//     *            to eval
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eval">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_eval
//     *     </a>
//     */
//    public boolean eval(String string) {
//        CommandResponse resp = execute("eval", string);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Event application can be used to fire aribtrary events.
//     *
//     * @param event
//     *            to send ex:
//     *            Event-Subclass=myevent::notify,Event-Name=CUSTOM,key1
//     *            =value1,key2=value2
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_event">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_event
//     *     </a>
//     */
//    public boolean event(String event) {
//        CommandResponse resp = execute("event", event);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * execute an extension from within another extension with this dialplan
//     * application.
//     *
//     *
//     * @param extension
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_execute_extension">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_execute_extension
//     *     </a>
//     */
//    public boolean executeExtension(String extension) {
//        return executeExtension(extension, null, null);
//    }
//    /**
//     * execute an extension from within another extension with this dialplan
//     * application.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_execute_extension">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_execute_extension
//     *     </a>
//     */
//    public boolean executeExtension(String extension, String dialplan) {
//        return executeExtension(extension, dialplan, null);
//    }
//
//    /**
//     * execute an extension from within another extension with this dialplan
//     * application.
//     *
//     * @param context
//     *            (will only be set if optionalDialplan is not null)
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_execute_extension">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_execute_extension
//     *     </a>
//     */
//    public boolean executeExtension(String extension, String dialplan,
//            String context) {
//        StringBuilder sb = new StringBuilder(extension);
//        if(StringUtils.isNotBlank(dialplan)) {
//            sb.append(" ").append(dialplan);
//            if(StringUtils.isNotBlank(context))
//                sb.append(" ").append(context);
//        }
//        CommandResponse resp = execute("execute_extension", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Exports a channel variable from the A leg to the B leg. Variables and
//     * their values will be replicated in any new channels created from the one
//     * export was called.
//     *
//     * @param key
//     *            channel variable name
//     * @param value
//     *            channel variable value
//     * @param local
//     *            to only export to the B leg false, otherwise true
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_export">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_export
//     *     </a>
//     */
//    public boolean export(String key, String value, boolean local) {
//        StringBuilder sb = new StringBuilder();
//        if(!local) sb.append("nolocal:");
//        sb.append(key).append("=").append(value);
//        CommandResponse resp = execute("export", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * When a fax is detected, the call will be routed to the ext in the context
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_fax_detect">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_fax_detect
//     *     </a>
//     */
//    public boolean faxDetect(String context, String ext) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("fax 1100 r +5000 transfer ");
//        sb.append(ext).append(" XML ").append(context);
//        CommandResponse resp = execute("tone_detect", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Flushes DTMFs received on a channel. Useful in cases where callers may
//     * have entered extra digits in one dialog and you want to flush them out
//     * before sending them into another dialog.
//     *
//     * @see #playAndGetDigits
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_flush_dtmf">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_flush_dtmf
//     *     </a>
//     */
//    public boolean flushDTMF() {
//        CommandResponse resp = execute("flush_dtmf");
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Generate TGML tones.
//     *
//     * @param tone
//     *            ex: Generate a 500ms beep at 800Hz, tone = "%(500,0,800)"
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_gentones">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_gentones
//     *     </a>
//     * @see <a href="http://wiki.freeswitch.org/wiki/TGML">
//     *     http://wiki.freeswitch.org/wiki/TGML
//     *     </a>
//     */
//    public boolean genTones(String tone) {
//        return genTones(tone, 0);
//    }
//
//    /**
//     * Generate TGML tones.
//     *
//     * @param tone
//     *            ex: Generate a 500ms beep at 800Hz, tone = "%(500,0,800)"
//     * @param loops
//     *            set to a non zero nuber, -1 for infinate loop
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_gentones">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_gentones
//     *     </a>
//     * @see <a href="http://wiki.freeswitch.org/wiki/TGML">
//     *     http://wiki.freeswitch.org/wiki/TGML
//     *     </a>
//     */
//    public boolean genTones(String tone, int loops) {
//        CommandResponse resp = execute("gentones",
//                tone + (loops != 0 ? "|" + loops : ""));
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * adds/deletes groups to/from the db(internal db or ODBC) and allows calls
//     * to these groups in conjunction with the bridge-application. Depends on
//     * mod_db and mod_dptools.
//     *
//     * @param action
//     *            (insert|delete|call )
//     * @param groupName
//     *            ex: :01@example.com
//     * @param url
//     *            ex: sofia/gateway/provider/0123456789
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_group">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_group
//     *     </a>
//     */
//    public boolean group(String action, String groupName, String url) {
//        CommandResponse resp = execute("group",
//                action + ":" + groupName + ":" + url);
//        return resp != null && resp.isOk();
//    }

    /**
     * Hangs up a channel, with an optional reason supplied.
     *
     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_hangup">
     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_hangup
     *     </a>
     */
    public boolean hangup() {
        return hangup(null);
    }

    /**
     * Hangs up a channel, with an optional reason supplied.
     *
     * @param reason
     *            if not null the hangup reason, ex: USER_BUSY
     *
     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_hangup">
     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_hangup
     *     </a>
     */
    public boolean hangup(String reason) {
        SendMsg sendMsg = new SendMsg();
        try {
            sendMsg.addCallCommand("execute");
            sendMsg.addExecuteAppName("hangup");
            if (StringUtils.isNotBlank(reason))
                sendMsg.addHangupCause(reason);
            CommandResponse resp = sendMessage(sendMsg);
            return resp != null && resp.isOk();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean fileExists(@NotBlank String file) {
        String replyText = executeApi("file_exists", file);
        return StringUtils.equalsIgnoreCase("true", replyText);
    }
//
//    public String createUUID() {
//        return StringUtils.trim(getApiReply("create_uuid"));
//    }
//
//    public boolean uuidExists(String uuid) {
//        String replyText = getApiReply("uuid_exists", uuid);
//        return StringUtils.equalsIgnoreCase("true", replyText);
//    }
//
//    public String sendfile(String url, String filePath, String fileName,
//                           Map<String,String> post, String report, String identifier) {
//        if (!StringUtils.equalsAnyIgnoreCase(report,
//                "event", "stream", "both", "none")) {
//            report = null;
//        }
//        StringBuilder sb = new StringBuilder(url);
//        sb.append(" filenameParamName=").append(fileName);
//        if(post != null && post.size() > 0) {
//            sb.append(" ");
//            for(Map.Entry<String, String> record :post.entrySet()) {
//                sb.append(record.getKey()).append("=")
//                        .append(record.getValue()).append("&");
//            }
//            sb.deleteCharAt(sb.length()-1);
//        }
//        if(StringUtils.isNotEmpty(report))
//            sb.append(" ").append(report);
//        if(StringUtils.isNotEmpty(identifier))
//            sb.append(" ").append(identifier);
//        CommandResponse resp = execute("curl_sendfile", sb.toString());
//        return resp == null ? null : resp.getReplyText();
//    }
//
//    public boolean deleteFile(String filePath) {
//        StringBuffer sb = new StringBuffer("rm -rf ").append(filePath);
//        CommandResponse resp = execute("system", sb.toString());
//        String text = resp == null ? null : resp.getReplyText();
//        return StringUtils.contains(text, filePath);
//    }
//
//    public String getBasePath() {
//        CommandResponse resp = execute("global_getvar", "base_dir");
//        return resp == null ? null : resp.getReplyText();
//    }
//
//    public boolean runLua(@NotBlank String fileName, String... args) {
//        StringBuilder sb = new StringBuilder(fileName);
//        if(args != null && args.length > 0) {
//            for(String arg : args)
//                sb.append(" ").append(arg);
//        }
//        CommandResponse resp = execute("luarun", sb.toString());
//        return resp != null && resp.isOk();
//    }

    public boolean uuidSetVar(@NotBlank String uuid,
                              @NotBlank String name,
                              @NotBlank String value) {
        String args = uuid + " " + name + "=" + value;
        String replyText = executeApi("uuid_setvar", args);
        return StringUtils.startsWith(replyText, "+OK");
    }

//    @Deprecated
//    public boolean uuidSetVarMulti(@NotBlank String uuid, @NotNull Map<String, String> values) {
//        if(values.size() < 1) return false;
//
//        StringBuilder sb = new StringBuilder(uuid).append(" ");
//        for(Map.Entry<String, String> record : values.entrySet()) {
//            String key = record.getKey(), value = record.getValue();
//            sb.append(key).append("=").append(value).append("; ");
//        }
//        String command = "uuid_setvar_multi";
//        String args = sb.substring(0, sb.length()-2);
//        CommandResponse response = execute(command, args);
//        return response != null && response.isOk();
//    }

    public boolean playBack(@NotBlank String uuid, @NotBlank String file,
                            PlaybackLeg direction) {
        return playBack(uuid, file, 0, 0, null, direction);
    }

    public boolean playBack(@NotBlank String uuid, @NotBlank String file,
                            String hangupCause, PlaybackLeg direction) {
        return playBack(uuid, file, 0, 0, hangupCause, direction);
    }

    public boolean playBack(@NotBlank String uuid, @NotBlank String file,
                            int loops, String hangupCause, PlaybackLeg direction) {
        return playBack(uuid, file, 0, loops, hangupCause, direction);
    }

    public boolean playBack(@NotBlank String uuid, @NotBlank String file,
                            int delay, int loops, String hangupCause, PlaybackLeg direction) {
        StringBuffer filePath = new StringBuffer(file);
        if (StringUtils.startsWith(file, "http://"))
            filePath.insert(0, "http_cache://");

        String command = "uuid_broadcast";
        StringBuffer buffer = new StringBuffer(uuid);
        if (delay > 0) {
            command = "sched_broadcast";
            buffer.insert(0, "+"+delay+" ");
        }
        if (loops > 0) {
            buffer.append(" loop_playback");
        } else { buffer.append(" playback"); }
        if (StringUtils.isNotBlank(hangupCause))
            buffer.append("!").append(hangupCause);
        buffer.append("::");
        if (loops > 0) {
            buffer.append("'+").append(loops).append(" ")
                    .append(filePath).append("'");
        } else { buffer.append(filePath); }
        if (direction != null) {
            switch (direction) {
                case aleg: buffer.append(" aleg"); break;
                case bleg: buffer.append(" bleg"); break;
                case both: buffer.append(" both"); break;
            }
        }
        final String args = buffer.toString().trim();
        String replyText = executeApi(command, args);
        return StringUtils.startsWith(replyText, "+OK");
    }

//    /**
//     * Dumps channel information to console.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_info">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_info
//     *     </a>
//     */
//    public boolean info() {
//        CommandResponse resp = execute("info", null);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Dumps channel information to console.
//     *
//     * @param level
//     *            if not null the level to log. Ex: notice Ex:
//     *            bridge_pre_execute_bleg_app=info
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_info">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_info
//     *     </a>
//     */
//    public boolean info(String level) {
//        CommandResponse resp = execute("info", level);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Allows one channel to bridge itself to the a or b leg of another call.
//     * The remaining leg of the original call gets hungup
//     *
//     * @param bleg
//     *            intercept the b leg of the call
//     */
//    public boolean intercept(String uuid, boolean bleg) {
//        CommandResponse resp = execute("intercept",
//                (bleg ? "-bleg " : "") + uuid);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Logs a string of text to the console
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_log">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_log
//     *     </a>
//     * @see <a href="http://wiki.freeswitch.org/wiki/Mod_logfile">
//     *     http://wiki.freeswitch.org/wiki/Mod_logfile
//     *     </a>
//     */
//    public boolean log(String message) {
//       return log(null,message);
//    }
//
//    /**
//     * Logs a string of text to the console
//     *
//     * @param level
//     *            ex: DEBUG, INFO
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_log">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_log
//     *     </a>
//     * @see <a href="http://wiki.freeswitch.org/wiki/Mod_logfile">
//     *     http://wiki.freeswitch.org/wiki/Mod_logfile
//     *     </a>
//     */
//    public boolean log(String level, String message) {
//        StringBuffer sb = new StringBuffer();
//        if (StringUtils.isNotBlank(level))
//            sb.append(level).append(" ");
//        sb.append(message);
//        CommandResponse resp = execute("log", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Creates a directory. Also creates parent directories by default(When they
//     * don't exist).
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_mkdir">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_mkdir
//     *     </a>
//     */
//    public boolean mkdir(String path) {
//        CommandResponse resp = execute("mkdir", path);
//        return resp != null && resp.isOk();
//    }

    /**
     * Places a channel "on hold" in the switch, instead of in the phone. Allows
     * for a number of different options, including: Set caller in a place where
     * the channel won't be hungup on, while waiting for someone to talk to.
     * Generic "hold" mechanism, where you transfer a caller to it. Please note
     * that to retrieve a call that has been "parked", you'll have to bridge to
     * them or transfer the call to a valid location. Also, remember that
     * parking a call does *NOT* supply music on hold or any other media. Park
     * is quite literally a way to put a call in limbo until you you
     * bridge/uuid_bridge or transfer/uuid_transfer it. For a different means of
     * using 'park', see mod_fifo.
     *
     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_park">
     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_park
     *     </a>
     */
    public boolean park() {
        CommandResponse resp = executeApp("park");
        return resp != null && resp.isOk();
    }

    public boolean valetPark(@NotBlank String uuid) {
        String args = "valet_lot " + uuid + "";
        CommandResponse resp = executeApp("valet_park", args);
        return resp != null && resp.isOk();
    }

    public boolean uuidPark(String uuid, long timeout, String hangupCause) {
        if (timeout > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append(timeout);
            if (StringUtils.isNotBlank(hangupCause))
                sb.append(":").append(hangupCause);
            if (uuidSetVar(uuid, "park_timeout", sb.toString()))
                return false;
        }
        String replyText = executeApi("uuid_park", uuid);
        return StringUtils.startsWith(replyText, "+OK");
    }

//
//    /**
//     * Speak a phrase of text using a predefined phrase macro. (For more
//     * information on TTS see mod_cepstral and OpenMRCP.) See also the speech
//     * phrase management page for more information and examples; This command
//     * relies on the configuration in the phrases section of the freeswitch.xml
//     * file and including xml files in lang/en/*.xml. Following is a sample of
//     * phrases management:
//     *
//     * @param macroName
//     *            ex: spell, timespec, saydate
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_phrase">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_phrase
//     *     </a>
//     */
//    public boolean phrase(String macroName, String data) {
//        CommandResponse resp = execute("phrase", macroName + "," + data);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Permits proper answering of multiple simultaneous calls to the same
//     * pickup group.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_pickup">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_pickup
//     *     </a>
//     */
//    public boolean pickup(String group) {
//        CommandResponse resp = execute("pickup", group);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Play while doing speech recognition. Result is stored in the
//     * detect_speech_result channel variable.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_play_and_detect_speech">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_play_and_detect_speech
//     *     </a>
//     */
//    public String playAndDetectSpeech(String uuid, String file, String engine,
//            String grammar) {
//        return playAndDetectSpeech(uuid, file, engine, null, grammar);
//    }
//
//    /**
//     * Play while doing speech recognition. Result is stored in the
//     * detect_speech_result channel variable.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_play_and_detect_speech">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_play_and_detect_speech
//     *     </a>
//     */
//    public String playAndDetectSpeech(String uuid, String file, String engine, String grammar, String params) {
//        String strParams = StringUtils.isNotBlank(params) ? params : "";
//        StringBuffer sb = new StringBuffer();
//        sb.append(file).append(" detect:").append(engine);
//        sb.append(" {").append(strParams).append("}").append(grammar);
//        CommandResponse resp = execute("play_and_detect_speech", sb.toString());
//        if (resp == null || !resp.isOk()) return null;
//
//        sb = new StringBuffer(uuid).append(" detect_speech_result");
//        EslMessage eslMessage = sendApiCommand("uuid_getvar", sb.toString());
//        if (eslMessage.getBodyLines().size() > 0)
//            return eslMessage.getBodyLines().get(0);
//        return null;
//    }
//
//    /**
//     * Play a prompt and get digits.
//     *
//     * @return collected digits or null if none
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_play_and_get_digits">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_play_and_get_digits
//     *     </a>
//     */
//    public String playAndGetDigits(String uuid, int min, int max, int tries, int timeout,
//            String terminator, String file, String invalidFile, String regexp,
//            int digitTimeout) {
//
//        String id = UUID.randomUUID().toString();
//
//        CommandResponse resp = execute("play_and_get_digits",
//                min
//                        + " " + max
//                        + " " + tries
//                        + " " + timeout
//                        + " " + terminator
//                        + " " + file
//                        + " " + invalidFile
//                        + " " + id
//                        + " " + regexp
//                        + " " + digitTimeout);
//
//        if (resp != null && resp.isOk()) {
//            EslMessage eslMessage = sendApiCommand("uuid_getvar", uuid
//                    + " " + id);
//            if (eslMessage.getBodyLines().size() > 0)
//                return eslMessage.getBodyLines().get(0);
//        }
//        return null;
//    }
//
//    /**
//     * Plays a sound file on the current channel.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_playback">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_playback
//     *     </a>
//     */
//    public boolean playback(String file) {
//        return playback(file, null);
//    }
//
//    /**
//     * Plays a sound file on the current channel.
//     *
//     * @param data
//     *            ex: var1=val1,var2=val2 adds specific vars that will be sent
//     *            in PLAYBACK_START and PLAYBACK_STOP events
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_playback">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_playback
//     *     </a>
//     */
//    public boolean playback(String file, String data) {
//        StringBuilder sb = new StringBuilder(file);
//        if(StringUtils.isNotBlank(data))
//            sb.append(" {").append(data).append("}");
//        CommandResponse resp = execute("playback",sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    @Deprecated
//    public boolean playback(@NotBlank String uuid, @NotBlank String filePath,
//                            int loop, String nextCmd, String nextArg) {
//        if(filePath.startsWith("http://"))
//            filePath = "http_cache://" + filePath;
//        if(StringUtils.isNotEmpty(nextCmd) && loop < 2) {
//            filePath = "{variable_next_command="+nextCmd+"}"+filePath;
//        } else if(StringUtils.isNotEmpty(nextCmd) && loop > 1) {
//            Map<String, String> values = new HashMap<>();
//            values.put("playback_delimiter", "!");
//            values.put("playback_sleep_val", "1000");
//            uuidSetVarMulti(uuid, values);
//            StringBuilder buffer = new StringBuilder();
//            buffer.append("{variable_next_command=").append(nextCmd);
//            buffer.append(",variable_next_args=").append(nextArg).append("}");
//            buffer.append("file_string://").append(filePath);
//            for (int i = 1; i < loop; i++)
//                buffer.append("!").append(filePath);
//            filePath = buffer.toString();
//        }
//
//        SendMsg sendMsg = new SendMsg(uuid);
//        sendMsg.addCallCommand("execute");
//        sendMsg.addExecuteAppName("playback");
//        sendMsg.addExecuteAppArg(filePath);
//        CommandResponse resp = sendMessage(sendMsg);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Manage the audio being played into a channel from a sound file
//     *
//     * @param step
//     *            <+[step]>|<-[step]>
//     *
//     * @see <a
//     *      href="http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman">http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman</a>
//     */
//    public boolean playbackSpeed(String uuid, int step) {
//        return playbackControl(uuid, "speed " + step);
//    }
//
//    /**
//     * Manage the audio being played into a channel from a sound file
//     *
//     * @param step
//     *            <+[step]>|<-[step]>
//     *
//     * @see <a
//     *      href="http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman">http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman</a>
//     */
//    public boolean playbackVolume(String uuid, int step) {
//        return playbackControl(uuid, "volume " + step);
//    }
//
//    /**
//     * Manage the audio being played into a channel from a sound file
//     *
//     * @see <a
//     *      href="http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman">http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman</a>
//     */
//    public boolean playbackPause(String uuid) {
//        return playbackControl(uuid, "pause");
//    }
//
//    /**
//     * Manage the audio being played into a channel from a sound file
//     *
//     * @see <a
//     *      href="http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman">http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman</a>
//     */
//    public boolean playbackTruncate(String uuid) {
//        return playbackControl(uuid, "truncate");
//    }
//
//    /**
//     * Manage the audio being played into a channel from a sound file
//     *
//     * @see <a
//     *      href="http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman">http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman</a>
//     */
//    public boolean playbackRestart(String uuid) {
//        return playbackControl(uuid, "restart");
//    }
//
//    /**
//     * Manage the audio being played into a channel from a sound file
//     *
//     * @param samples
//     *            <+[samples]>|<-[samples]> Samples are the literally the number
//     *            of samples in the file to jump forward or backward. In an 8kHz
//     *            file, 8000 samples would represent one second, in a 16kHz file
//     *            16000 samples would be one second, etc
//     *
//     * @see <a
//     *      href="http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman">http://wiki.freeswitch.org/wiki/Mod_commands#uuid_fileman</a>
//     */
//
//    public boolean playbackSeek(String uuid, int samples) {
//        return playbackControl(uuid, "seek " + samples);
//    }
//
//    private boolean playbackControl(String uuid, String cmd) {
//        String args = uuid + " " + cmd;
//        EslMessage msg = sendApiCommand("uuid_getvar", args);
//        return new CommandResponse("uuid_getvar", msg).isOk();
//    }

    /**
     * equivalent to a SIP status code 183 with SDP. (This is the same as cmd
     * Progress in Asterisk.) It establishes media (early media) but does not
     * answer. You can use this for example to send an in-band error message to
     * the caller before disconnecting them (pre_answer, playback, reject with a
     * cause code of xxxx).
     *
     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_pre_answer">
     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_pre_answer
     *     </a>
     */
    public boolean preAnswer() {
        CommandResponse resp = executeApp("pre_answer");
        return resp != null && resp.isOk();
    }
//
//    /**
//     * Sends an event of either type PRESENCE_IN or PRESENCE_OUT. Currently,
//     * this function is not very useful in conjunction with sofia. This does not
//     * affect the presence of hook state for use with BLF either, but sending an
//     * event that expresses the user's hook state does.
//     *
//     * @param in
//     *            true if in, false if out
//     * @param rpid
//     *            ex: dnd, unavailable
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_presence">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_presence
//     *     </a>
//     */
//    public boolean presence(String user, boolean in, String rpid, String message) {
//        CommandResponse resp = execute("presence",
//                in ? "in" : "out" + "|" + user + "|" + rpid + "|" + message);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Set caller privacy on calls.
//     *
//     * @param type
//     *            ex: no, yes, name, full, member
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_privacy">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_privacy
//     *     </a>
//     */
//    public boolean privacy(String type) {
//        CommandResponse resp = execute("privacy", type);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Send DTMF digits after a bridge is successful from the session using the
//     * method(s) configured on the endpoint in use. use the character w for a .5
//     * second delay and the character W for a 1 second delay.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_queue_dtmf">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_queue_dtmf
//     *     </a>
//     */
//    public boolean queueDTMF(String digits) {
//        return queueDTMF(digits, 0);
//    }
//
//    /**
//     * Send DTMF digits after a bridge is successful from the session using the
//     * method(s) configured on the endpoint in use. use the character w for a .5
//     * second delay and the character W for a 1 second delay.
//     *
//     * @param durationsMillis
//     *            ignored if <=0
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_queue_dtmf">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_queue_dtmf
//     *     </a>
//     */
//    public boolean queueDTMF(String digits, int durationsMillis) {
//        CommandResponse resp = execute("dtmf_digits",
//                digits + (durationsMillis > 0 ? "@" + durationsMillis : ""));
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Read DTMF (touch-tone) digits.
//     *
//     * @param min
//     *            Minimum number of digits to fetch.
//     * @param max
//     *            Maximum number of digits to fetch.
//     * @param soundFile
//     *            Sound file to play before digits are fetched.
//     * @param timeout
//     *            Number of milliseconds to wait on each digit
//     * @param terminators
//     *            Digits used to end input if less than <min> digits have been
//     *            pressed. (Typically '#')
//     *
//     * @return read string or null
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_read">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_read
//     *     </a>
//     */
//    public String read(String uuid, int min, int max, String soundFile, long timeout, String terminators) {
//        String id = UUID.randomUUID().toString();
//        StringBuffer sb = new StringBuffer();
//        sb.append(min).append(" ").append(max);
//        sb.append(" ").append(soundFile).append(" ");
//        sb.append(id).append(" ").append(timeout);
//        sb.append(" ").append(terminators);
//        CommandResponse resp = execute("read", sb.toString());
//        if (resp == null || !resp.isOk()) return null;
//
//        String args = uuid + " " + id;
//        EslMessage eslMessage = sendApiCommand("uuid_getvar", args);
//        if (eslMessage.getBodyLines().size() > 0)
//            return eslMessage.getBodyLines().get(0);
//        return null;
//    }
//
//    /**
//     * Record is used for recording messages, like in a voicemail system. This
//     * application will record a file to file
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record
//     *     </a>
//     */
//    public boolean record(String file) {
//        return record("record", null, 0, 0, 0, true, false, null, null, null, null,
//                null, null, 0);
//    }
//
//
//    /**
//     * Record is used for recording messages, like in a voicemail system. This
//     * application will record a file to file
//     *
//     * @param timeLimitSeconds
//     *            the maximum duration of the recording.
//     * @param silenceThreshold
//     *            is the energy level.
//     * @param wateResources
//     *            By default record doesn't send RTP packets. This is generally
//     *            acceptable, but for longer recordings or depending on the RTP
//     *            timer of your gateway, your channel may hangup with cause
//     *            MEDIA_TIMEOUT. Setting this variable will 'waste' bandwidth by
//     *            sending RTP even during recording. The value can be
//     *            true/false/<desired silence factor>. By default the silence
//     *            factor is 1400 if true
//     * @param silenceHits
//     *            how many positive hits on being below that thresh you can
//     *            tolerate to stop default hits are sample rate * 3 / the number
//     *            of samples per frame so the default, if missing, is 3.
//     * @param append
//     *            append or overwite if file exists
//     * @param recordTile
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordCopyright
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordSoftware
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordArtist
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordComment
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordDate
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordRate
//     *            the sample rate of the recording.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record
//     *     </a>
//     */
//    public boolean record(String file, boolean append, boolean wateResources,
//            int timeLimitSeconds, int silenceThreshold, int silenceHits,
//            String recordTile, String recordCopyright, String recordSoftware,
//            String recordArtist, String recordComment, String recordDate,
//            int recordRate) {
//        return record("record", file, timeLimitSeconds, silenceThreshold, silenceHits,
//                wateResources, append, recordTile, recordCopyright,
//                recordSoftware, recordArtist, recordComment, recordDate,
//                recordRate);
//    }
//
//    /**
//     *  Records an entire phone call or session.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record
//     *     </a>
//     */
//    public boolean recordSession(String file) {
//        return record("record_session", null, 0, 0, 0, true, false, null, null, null, null,
//                null, null, 0);
//    }
//
//
//    /**
//     *  Records an entire phone call or session.
//     *
//     * @param timeLimitSeconds
//     *            the maximum duration of the recording.
//     * @param silenceThreshold
//     *            is the energy level.
//     * @param wateResources
//     *            By default record doesn't send RTP packets. This is generally
//     *            acceptable, but for longer recordings or depending on the RTP
//     *            timer of your gateway, your channel may hangup with cause
//     *            MEDIA_TIMEOUT. Setting this variable will 'waste' bandwidth by
//     *            sending RTP even during recording. The value can be
//     *            true/false/<desired silence factor>. By default the silence
//     *            factor is 1400 if true
//     * @param silenceHits
//     *            how many positive hits on being below that thresh you can
//     *            tolerate to stop default hits are sample rate * 3 / the number
//     *            of samples per frame so the default, if missing, is 3.
//     *
//     * @param append
//     *            append or overwite if file exists
//     * @param recordTile
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordCopyright
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordSoftware
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordArtist
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordComment
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordDate
//     *            store in the file header meta data (provided the file format
//     *            supports meta headers).
//     * @param recordRate
//     *            the sample rate of the recording.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_record
//     *     </a>
//     */
//    public boolean recordSession(String file, boolean append, boolean wateResources,
//            int timeLimitSeconds, int silenceThreshold, int silenceHits,
//            String recordTile, String recordCopyright, String recordSoftware,
//            String recordArtist, String recordComment, String recordDate,
//            int recordRate) {
//        return record("record_session", file, timeLimitSeconds, silenceThreshold,
//                silenceHits, wateResources, append, recordTile, recordCopyright,
//                recordSoftware, recordArtist, recordComment, recordDate, recordRate);
//    }
//
//    private boolean record(String action, String file, int optionalTimeLimitSeconds,
//            int optionalSilenceThreshold, int optionalSilenceHits,
//            boolean wateResources, boolean append, String optionalRecordTile,
//            String optionalRecordCopyright, String optionalRecordSoftware,
//            String optionalRecordArtist, String optionalRecordComment,
//            String optionalRecordDate, int optionalRecordRate) {
//        if (StringUtils.isNotBlank(optionalRecordTile))
//            set("RECORD_TITLE", optionalRecordTile);
//        if (StringUtils.isNotBlank(optionalRecordCopyright))
//            set("RECORD_COPYRIGHT", optionalRecordCopyright);
//        if (StringUtils.isNotBlank(optionalRecordSoftware))
//            set("RECORD_SOFTWARE", optionalRecordSoftware);
//        if (StringUtils.isNotBlank(optionalRecordArtist))
//            set("RECORD_ARTIST", optionalRecordArtist);
//        if (StringUtils.isNotBlank(optionalRecordComment))
//            set("RECORD_COMMENT", optionalRecordComment);
//        if (StringUtils.isNotBlank(optionalRecordDate))
//            set("RECORD_DATE", optionalRecordDate);
//        if (optionalRecordRate > 0)
//            set("record_sample_rate", String.valueOf(optionalRecordRate));
//
//        set("RECORD_APPEND", String.valueOf(append));
//        set("record_waste_resources", String.valueOf(wateResources));
//
//        StringBuilder sb = new StringBuilder(file);
//        if (optionalTimeLimitSeconds > 0) {
//            sb.append(" ").append(optionalTimeLimitSeconds);
//            if (optionalSilenceThreshold > 0) {
//                sb.append(" ").append(optionalSilenceThreshold);
//                if (optionalSilenceHits > 0) {
//                    sb.append(" ").append(optionalSilenceHits);
//                }
//            }
//        }
//
//        CommandResponse resp = execute(action, sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Can redirect a channel to another endpoint, you must take care to not
//     * redirect incompatible channels, as that wont have the desired effect. Ie
//     * if you redirect to a SIP url it should be a SIP channel. By providing a
//     * single SIP URI FreeSWITCH will issue a 302 "Moved Temporarily":
//     *
//     * @param endpoint
//     *            ex:"sip:foo@bar.com " or "sip:foo@bar.com,sip:foo@end.com"
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_redirect">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_redirect
//     *     </a>
//     */
//    public boolean redirect(String endpoint) {
//        CommandResponse resp = execute("redirect", endpoint);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Send SIP session respond code to the SIP device.
//     *
//     * @param code
//     *            ex: "407" or "480 Try again later"
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_respond">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_respond
//     *     </a>
//     */
//    public boolean respond(String code) {
//        CommandResponse resp = execute("respond", code);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * This causes an 180 Ringing to be sent to the originator.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_ring_ready">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_ring_ready
//     *     </a>
//     */
//    public boolean ringReady() {
//        CommandResponse resp = execute("ring_ready");
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * The say application will use the pre-recorded sound files to read or say
//     * various things like dates, times, digits, etc. The say application can
//     * read digits and numbers as well as dollar amounts, date/time values and
//     * IP addresses. It can also spell out alpha-numeric text, including
//     * punctuation marks. There's a transcript of the pre-recorded files in the
//     * sources under docs/phrase/phrase_en.xml
//     *
//     * @param moduleName
//     *            Module name is usually the channel language, e.g. "en" or "es"
//     * @param sayType
//     *            Say type is one of the following NUMBER ITEMS PERSONS MESSAGES
//     *            CURRENCY TIME_MEASUREMENT CURRENT_DATE CURRENT_TIME
//     *            CURRENT_DATE_TIME TELEPHONE_NUMBER TELEPHONE_EXTENSION URL
//     *            IP_ADDRESS EMAIL_ADDRESS POSTAL_ADDRESS ACCOUNT_NUMBER
//     *            NAME_SPELLED NAME_PHONETIC SHORT_DATE_TIME
//     * @param sayMethod
//     *            Say method is one of the following N/A PRONOUNCED ITERATED
//     *            COUNTED
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_say">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_say
//     *     </a>
//     */
//    public boolean say(String moduleName, String text, String sayType,
//            String sayMethod) {
//
//        return say(moduleName, text, sayType, sayMethod, null);
//    }
//
//    /**
//     * The say application will use the pre-recorded sound files to read or say
//     * various things like dates, times, digits, etc. The say application can
//     * read digits and numbers as well as dollar amounts, date/time values and
//     * IP addresses. It can also spell out alpha-numeric text, including
//     * punctuation marks. There's a transcript of the pre-recorded files in the
//     * sources under docs/phrase/phrase_en.xml
//     *
//     * @param moduleName
//     *            Module name is usually the channel language, e.g. "en" or "es"
//     * @param sayType
//     *            Say type is one of the following NUMBER ITEMS PERSONS MESSAGES
//     *            CURRENCY TIME_MEASUREMENT CURRENT_DATE CURRENT_TIME
//     *            CURRENT_DATE_TIME TELEPHONE_NUMBER TELEPHONE_EXTENSION URL
//     *            IP_ADDRESS EMAIL_ADDRESS POSTAL_ADDRESS ACCOUNT_NUMBER
//     *            NAME_SPELLED NAME_PHONETIC SHORT_DATE_TIME
//     * @param sayMethod
//     *            Say method is one of the following N/A PRONOUNCED ITERATED
//     *            COUNTED
//     * @param gender
//     *            Say gender is one of the following (For languages with
//     *            gender-specific grammar, like French and German) FEMININE
//     *            MASCULINE NEUTER
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_say">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_say
//     *     </a>
//     */
//    public boolean say(String moduleName, String text, String sayType,
//            String sayMethod, String gender) {
//
//        StringBuilder sb = new StringBuilder(moduleName);
//        sb.append(" ").append(sayType);
//        sb.append(" ").append(sayMethod);
//        if (StringUtils.isNotBlank(gender))
//            sb.append(" ").append(gender);
//        sb.append(" ").append(text);
//
//        CommandResponse resp = execute("say", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Schedule future broadcast.
//     *
//     * @param seconds
//     *            the epoc time in the future, or the number of seconds in the
//     *            future
//     * @param interval
//     *            is the param seconds an epoc time or interval
//     * @param path
//     *            ex: /tmp/howdy.wav
//     * @param leg
//     *            can be aleg,bleg,both
//     */
//    public boolean schedBroadcast(long seconds, boolean interval, String path, String leg) {
//        StringBuilder sb = new StringBuilder();
//        if (interval) sb.append('+');
//        sb.append(seconds);
//        sb.append(" ").append(path);
//        sb.append(" ").append(leg);
//        CommandResponse resp = execute("sched_broadcast", sb.toString());
//        return resp != null && resp.isOk();
//    }

    /**
     * The sched_hangup application allows you to schedule a hangup action for a
     * call, basically to limit call duration.
     *
     * @param seconds
     *            the epoc time in the future, or the number of seconds in the
     *            future
     * @param cause
     *             ex:allotted_timeout
     */
    public boolean schedHangup(long seconds, String cause) {
        return schedHangup(seconds, true, cause);
    }

    /**
     * The sched_hangup application allows you to schedule a hangup action for a
     * call, basically to limit call duration.
     *
     * @param seconds
     *            the epoc time in the future, or the number of seconds in the
     *            future
     * @param interval
     *            is the param seconds an epoc time or interval
     */
    public boolean schedHangup(long seconds, boolean interval) {
       return schedHangup(seconds, interval, null);
    }

    /**
     * The sched_hangup application allows you to schedule a hangup action for a
     * call, basically to limit call duration.
     *
     * @param seconds
     *            the epoc time in the future, or the number of seconds in the
     *            future
     * @param interval
     *            is the param seconds an epoc time or interval
     * @param cause
     *            ex:allotted_timeout
     */
    public boolean schedHangup(long seconds, boolean interval, String cause) {
        StringBuilder sb = new StringBuilder();
        if (interval) sb.append('+');
        sb.append(seconds);
        if (StringUtils.isNotBlank(cause))
            sb.append(" ").append(cause);
        CommandResponse resp = executeApp("sched_hangup", sb.toString());
        return resp != null && resp.isOk();
    }

//    /**
//     * Schedule a transfer in the future.
//     *
//     * @param seconds
//     *            the epoc time in the future, or the number of seconds in the
//     *            future
//     * @param interval
//     *            is the param seconds an epoc time or interval
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_sched_transfer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_sched_transfer
//     *     </a>
//     */
//    public boolean schedTransfer(long seconds, boolean interval, String extension) {
//        return schedTransfer(seconds, interval, extension, null, null);
//    }
//
//    /**
//     * Schedule a transfer in the future.
//     *
//     * @param seconds
//     *            the epoc time in the future, or the number of seconds in the
//     *            future
//     * @param interval
//     *            is the param seconds an epoc time or interval
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_sched_transfer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_sched_transfer
//     *     </a>
//     */
//    public boolean schedTransfer(long seconds, boolean interval, String extension,
//            String dialPlan) {
//        return schedTransfer(seconds, interval, extension, dialPlan, null);
//    }
//
//    /**
//     * Schedule a transfer in the future.
//     *
//     * @param seconds
//     *            the epoc time in the future, or the number of seconds in the
//     *            future
//     * @param interval
//     *            is the param seconds an epoc time or interval
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_sched_transfer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_sched_transfer
//     *     </a>
//     */
//    public boolean schedTransfer(long seconds, boolean interval, String extension,
//            String dialPlan, String context) {
//        StringBuilder sb = new StringBuilder();
//        if (interval)
//            sb.append('+');
//        sb.append(seconds);
//        sb.append(" ").append(extension);
//        if (StringUtils.isNotBlank(dialPlan)) {
//            sb.append(" ").append(dialPlan);
//            if (StringUtils.isNotBlank(context)) {
//                sb.append(" ").append(context);
//            }
//        }
//        CommandResponse resp = execute("sched_transfer", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Send DTMF digits from the session using the method(s) configured on the
//     * endpoint in use. Use the character w for a .5 second delay and the
//     * character W for a 1 second delay.
//     */
//    public boolean sendDTMF(String digits) {
//        return sendDTMF(digits, 0);
//    }
//
//    /**
//     * Send DTMF digits from the session using the method(s) configured on the
//     * endpoint in use. Use the character w for a .5 second delay and the
//     * character W for a 1 second delay.
//     */
//    public boolean sendDTMF(String digits, int durationMillis) {
//        StringBuilder sb = new StringBuilder(digits);
//        if (durationMillis > 0)
//            sb.append('@').append(durationMillis);
//
//        CommandResponse resp = execute("send_dtmf", sb.toString());
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Set a channel variable for the channel calling the application.
//     *
//     * @param key
//     *            channel_variable name
//     * @param value
//     *            channel_variable value
//     */
//    public boolean set(String key, String value) {
//        CommandResponse resp = execute("set", key + "=" + value);
//        return resp != null && resp.isOk();
//    }
//
//    public boolean speak(String engine, String voice, String message) {
//        String args = engine + "|" + voice + "|" + message;
//        CommandResponse resp = execute("speak", args);
//        return resp != null && resp.isOk();
//    }
//
//    /**
//     * Immediately transfer the calling channel to a new context. If there
//     * happens to be an xml extension named <destination_number> then control is
//     * "warped" directly to that extension. Otherwise it goes through the entire
//     * context checking for a match.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_transfer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_transfer
//     *     </a>
//     */
//    public boolean transfer(String destinationNumber) {
//        return transfer(destinationNumber, null, null);
//    }
//
//    /**
//     * Immediately transfer the calling channel to a new context. If there
//     * happens to be an xml extension named <destination_number> then control is
//     * "warped" directly to that extension. Otherwise it goes through the entire
//     * context checking for a match.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_transfer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_transfer
//     *     </a>
//     */
//    public boolean transfer(String destinationNumber, String dialplan) {
//        return transfer(destinationNumber, dialplan, null);
//    }
//
//    /**
//     * Immediately transfer the calling channel to a new context. If there
//     * happens to be an xml extension named <destination_number> then control is
//     * "warped" directly to that extension. Otherwise it goes through the entire
//     * context checking for a match.
//     *
//     * @see <a href="http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_transfer">
//     *     http://wiki.freeswitch.org/wiki/Misc._Dialplan_Tools_transfer
//     *     </a>
//     */
//    public boolean transfer(String destinationNumber, String dialplan, String context) {
//        StringBuilder sb = new StringBuilder(destinationNumber);
//        if (StringUtils.isNotBlank(dialplan)) {
//            sb.append(" ").append(dialplan);
//            if (StringUtils.isNotBlank(context))
//                sb.append(" ").append(context);
//        }
//        CommandResponse resp = execute("transfer", sb.toString());
//        return resp != null && resp.isOk();
//    }

    public String executeApi(String command) {
        return executeApi(command, null);
    }

    public String executeApi(String command, String args) {
        try {
            EslMessage message = sendApiCommand(command, args);
            StringBuilder sb = new StringBuilder();
            for (String line : message.getBodyLines())
                sb.append(line);
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public CommandResponse executeApp(String app) {
        return executeApp(app, null);
    }

    public CommandResponse executeApp(String app, String args) {
        SendMsg msg = new SendMsg();
        msg.addCallCommand("execute");
        msg.addExecuteAppName(app);
        if (StringUtils.isNotBlank(args))
            msg.addExecuteAppArg(args);
        try {
            return sendMessage(msg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

//    private CommandResponse execute(String app, String args, String uuid) {
//        SendMsg msg = new SendMsg(uuid);
//        msg.addCallCommand("execute");
//        msg.addExecuteAppName(app);
//        if (StringUtils.isNotBlank(args))
//            msg.addExecuteAppArg(args);
//        try {
//            return sendMessage(msg);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return null;
//    }
}
