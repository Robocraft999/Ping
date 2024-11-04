package com.robocraft999.ping.network;

import com.robocraft999.ping.platform.Services;

public class ServerPayloadHandler {
    public static void handlePingRequest(PingRequest pingRequest){
        Services.NETWORK.sendToAllPlayers(pingRequest);
    }
}
