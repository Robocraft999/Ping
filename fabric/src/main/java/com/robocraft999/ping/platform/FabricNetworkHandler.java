package com.robocraft999.ping.platform;

import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.platform.services.INetworkHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;

public class FabricNetworkHandler implements INetworkHandler {
    @Override
    public <T extends CustomPacketPayload> void sendToServer(T payload) {
        ClientPlayNetworking.send(payload);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToAllPlayers(T payload) {

    }

    @Override
    public void sendToPlayersInRadius(PingRequest payload, ServerLevel level, int radius) {
        for (var player : PlayerLookup.around(level, payload.pos().getCenter(), radius)) {
            ServerPlayNetworking.send(player, payload);
        }
    }
}
