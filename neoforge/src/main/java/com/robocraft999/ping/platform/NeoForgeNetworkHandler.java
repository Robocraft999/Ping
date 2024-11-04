package com.robocraft999.ping.platform;

import com.robocraft999.ping.platform.services.INetworkHandler;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkHandler implements INetworkHandler {
    @Override
    public <T extends CustomPacketPayload> void sendToServer(T payload) {
        PacketDistributor.sendToServer(payload);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToAllPlayers(T payload) {
        PacketDistributor.sendToAllPlayers(payload);
    }
}
