package com.robocraft999.ping.client;

import com.robocraft999.ping.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID)
public class ClientEvents {

    @SubscribeEvent
    private static void onClientTick(ClientTickEvent.Post event) {
        while (PingKeybinds.PING_KEY.get().consumeClick()) {
            Constants.LOG.info("Pinging");
            ClientPingHandler.handleTick();
        }
    }

    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            return;
        }

        ClientPingHandler.handleRender(event.getLevelRenderer(), event.getPoseStack(), event.getPartialTick().getGameTimeDeltaPartialTick(true));
    }


}
