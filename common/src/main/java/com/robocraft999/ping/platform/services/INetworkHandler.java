package com.robocraft999.ping.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface INetworkHandler {
    <T extends CustomPacketPayload> void sendToServer(T payload);
    <T extends CustomPacketPayload> void sendToAllPlayers(T payload);
}
