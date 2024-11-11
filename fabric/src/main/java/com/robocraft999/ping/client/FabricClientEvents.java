package com.robocraft999.ping.client;

import com.robocraft999.ping.PingKeyBinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.KeyMapping;

public class FabricClientEvents implements ClientModInitializer {
    public static KeyMapping PING_KEY;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (PING_KEY.consumeClick()) {
                ClientPingHandler.handleClick();
            }
            ClientPingHandler.handleTick();
        });

        PING_KEY = KeyBindingHelper.registerKeyBinding(PingKeyBinds.createKey());

        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {
            ClientPingHandler.handleRender(worldRenderContext.matrixStack(), worldRenderContext.tickCounter());
            return true;
        });
    }
}
