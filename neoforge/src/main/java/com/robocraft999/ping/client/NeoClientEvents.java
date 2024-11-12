package com.robocraft999.ping.client;

import com.robocraft999.ping.Constants;
import com.robocraft999.ping.PingKeyBinds;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.util.Lazy;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
public class NeoClientEvents {

    public static Lazy<KeyMapping> PING_KEY = Lazy.of(PingKeyBinds::createPingKey);
    public static Lazy<KeyMapping> HIDE_KEY = Lazy.of(PingKeyBinds::createHideKey);
    public static Lazy<KeyMapping> HIDE_ALL_KEY = Lazy.of(PingKeyBinds::createHideAllKey);

    @SubscribeEvent
    private static void onClientTick(ClientTickEvent.Post event) {
        while (PING_KEY.get().consumeClick()) {
            ClientPingHandler.handleClick();
        }
        while (HIDE_ALL_KEY.get().consumeClick()){
            ClientPingHandler.toggleHideAll();
        }
        while (HIDE_KEY.get().consumeClick()){
            ClientPingHandler.handleHide();
        }
        ClientPingHandler.handleTick();
    }

    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            return;
        }

        ClientPingHandler.handleRender(event.getPoseStack(), event.getPartialTick());
    }
}
