package com.robocraft999.ping.platform.services;

import com.robocraft999.ping.network.PingRequest;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;

public interface INetworkHandler {
    <T extends CustomPacketPayload> void sendToServer(T payload);
    <T extends CustomPacketPayload> void sendToAllPlayers(T payload);
    void sendToPlayersInRadius(PingRequest payload, ServerLevel level, int radius);
}
