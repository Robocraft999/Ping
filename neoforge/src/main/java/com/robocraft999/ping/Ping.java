package com.robocraft999.ping;


import com.robocraft999.ping.client.ClientEvents;
import com.robocraft999.ping.client.ClientPingHandler;
import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.network.ServerPayloadHandler;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Constants.MOD_ID)
public class Ping {

    public Ping(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
        eventBus.addListener(this::registerKeybinds);
        eventBus.addListener(this::registerPayloadHandler);
    }

    private void registerKeybinds(RegisterKeyMappingsEvent event){
        Constants.LOG.debug("registering keybinds");
        event.register(ClientEvents.PING_KEY.get());
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
                            o.enqueueWork(() -> ServerPayloadHandler.handlePingRequest(request));
                        }
                )
        );
    }
}
