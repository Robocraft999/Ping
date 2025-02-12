package com.robocraft999.ping;

import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.platform.Services;
import com.robocraft999.ping.platform.config.FabricConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class Ping implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        PayloadTypeRegistry.playS2C().register(PingRequest.TYPE, PingRequest.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PingRequest.TYPE, PingRequest.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PingRequest.TYPE, (payload, context) -> {
            var server = context.player().server;
            var level = server.getLevel(payload.levelResourceKey());
            Services.NETWORK.sendToPlayersInRadius(payload, level, Services.CONFIG.getExtendedReach());
        });

        AutoConfig.register(FabricConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
    }
}
