package com.robocraft999.ping;


import com.robocraft999.ping.client.ClientPingHandler;
import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.platform.Services;
import com.robocraft999.ping.platform.config.NeoConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@Mod(Constants.MOD_ID)
public class Ping {

    public Ping(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
        eventBus.addListener(this::registerPayloadHandler);

        var container = ModLoadingContext.get().getActiveContainer();
        container.registerConfig(ModConfig.Type.CLIENT, NeoConfig.CLIENT_CONFIG_SPEC);
        container.registerConfig(ModConfig.Type.SERVER, NeoConfig.SERVER_CONFIG_SPEC);
    }

    private void registerPayloadHandler(RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                PingRequest.TYPE,
                PingRequest.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (request, o) -> {
                            o.enqueueWork(() -> ClientPingHandler.handle(request));
                        },
                        (request, o) -> {
                            o.enqueueWork(() -> {
                                var server = ServerLifecycleHooks.getCurrentServer();
                                var level = server.getLevel(request.levelResourceKey());
                                Services.NETWORK.sendToPlayersInRadius(request, level, Services.CONFIG.getExtendedReach());
                            });
                        }
                )
        );
    }
}
