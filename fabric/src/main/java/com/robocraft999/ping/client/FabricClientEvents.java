package com.robocraft999.ping.client;

import com.robocraft999.ping.PingKeyBinds;
import com.robocraft999.ping.network.PingRequest;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.KeyMapping;

public class FabricClientEvents implements ClientModInitializer {
    public static KeyMapping PING_KEY;
    public static KeyMapping HIDE_KEY;
    public static KeyMapping HIDE_ALL_KEY;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (PING_KEY.consumeClick()) {
                ClientPingHandler.handleClick();
            }
            while (HIDE_ALL_KEY.consumeClick()){
                ClientPingHandler.toggleHideAll();
            }
            while (HIDE_KEY.consumeClick()){
                ClientPingHandler.handleHide();
            }
            ClientPingHandler.handleTick();
        });

        PING_KEY = KeyBindingHelper.registerKeyBinding(PingKeyBinds.createPingKey());
        HIDE_KEY = KeyBindingHelper.registerKeyBinding(PingKeyBinds.createHideKey());
        HIDE_ALL_KEY = KeyBindingHelper.registerKeyBinding(PingKeyBinds.createHideAllKey());

        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {
            ClientPingHandler.handleRender(worldRenderContext.matrixStack(), worldRenderContext.tickCounter());
            return true;
        });

        ClientPlayNetworking.registerGlobalReceiver(PingRequest.TYPE, (payload, context) -> {
            context.client().execute(() -> ClientPingHandler.handle(payload));
        });
    }
}
