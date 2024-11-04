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
public class ClientEvents {

    public static Lazy<KeyMapping> PING_KEY = Lazy.of(PingKeyBinds::createKey);

    @SubscribeEvent
    private static void onClientTick(ClientTickEvent.Post event) {
        while (PING_KEY.get().consumeClick()) {
            ClientPingHandler.handleTick();
        }
    }

    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            return;
        }

        ClientPingHandler.handleRender(event.getLevelRenderer(), event.getPoseStack(), event.getPartialTick());
    }


}
