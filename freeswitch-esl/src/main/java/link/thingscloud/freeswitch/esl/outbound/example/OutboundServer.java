package link.thingscloud.freeswitch.esl.outbound.example;

import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;
import link.thingscloud.freeswitch.esl.internal.Context;
import link.thingscloud.freeswitch.esl.internal.IModEslApi;
import link.thingscloud.freeswitch.esl.outbound.IClientHandler;
import link.thingscloud.freeswitch.esl.outbound.SocketClient;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;

import static link.thingscloud.freeswitch.esl.internal.IModEslApi.EventFormat.PLAIN;

public class OutboundServer {
    static CompletableFuture<EslEvent> future;
    public static void main(String[] args) {
        InboundClientOption option = new InboundClientOption();
        SocketAddress address = new InetSocketAddress("localhost", 8084);
        try {
            new SocketClient(address, option, () -> new IClientHandler() {
                @Override
                public void onConnect(Context context, EslEvent eslEvent) {
                    StringBuffer subscriptions = new StringBuffer();
                    subscriptions.append("BACKGROUND_JOB").append(" ");
//                    subscriptions.append("PLAYBACK_STOP").append(" ");
//                    subscriptions.append("DETECTED_TONE").append(" ");
//                    subscriptions.append("CHANNEL_CREATE").append(" ");
//                    subscriptions.append("CHANNEL_ANSWER").append(" ");
//                    subscriptions.append("CHANNEL_UNBRIDGE").append(" ");
//                    subscriptions.append("CHANNEL_HANGUP_COMPLETE");

                    String uuid = eslEvent.getEventHeaders().get("Unique-ID");
                    CommandResponse response = context.setEventSubscriptions(PLAIN, subscriptions.toString());
                    System.out.println(response.isOk());

                    future = context.sendBackgroundApiCommand("system", "ls /");
                    future.thenAccept(result -> System.out.println("Job::::::::::::"+result.toString()));

                    response = context.addEventFilter("Unique-ID", uuid);
                    System.out.println(response.isOk());

                    response = context.addEventFilter("Other-Leg-Unique-ID", uuid);
                    System.out.println(response.isOk());

//                    System.out.println(eslEvent.toString());
                }

                @Override
                public void onEslEvent(Context ctx, EslEvent eslEvent) {
//                    System.out.println(eslEvent.toString());
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}