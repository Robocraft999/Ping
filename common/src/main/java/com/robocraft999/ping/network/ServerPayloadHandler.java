package com.robocraft999.ping.network;

import com.robocraft999.ping.platform.Services;

public class ServerPayloadHandler {
    public static void handlePingRequest(PingRequest pingRequest){
        var level = Services.PLATFORM.getCurrentServer().getLevel(pingRequest.levelResourceKey());
        Services.NETWORK.sendToPlayersInRadius(pingRequest, level, Services.CONFIG.getExtendedReach());
    }
}
