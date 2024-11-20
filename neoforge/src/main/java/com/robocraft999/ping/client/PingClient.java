package com.robocraft999.ping.client;

import com.robocraft999.ping.Constants;
import com.robocraft999.ping.PingKeyBinds;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class PingClient {
    public static Lazy<KeyMapping> PING_KEY = Lazy.of(PingKeyBinds::createPingKey);
    public static Lazy<KeyMapping> HIDE_KEY = Lazy.of(PingKeyBinds::createHideKey);
    public static Lazy<KeyMapping> HIDE_ALL_KEY = Lazy.of(PingKeyBinds::createHideAllKey);

    public PingClient(IEventBus modBus){
        modBus.addListener(this::registerKeybinds);

        NeoForge.EVENT_BUS.addListener(this::onClientTick);
        NeoForge.EVENT_BUS.addListener(this::renderLevel);

        var container = ModLoadingContext.get().getActiveContainer();
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void registerKeybinds(RegisterKeyMappingsEvent event){
        Constants.LOG.debug("registering keybinds");
        event.register(PING_KEY.get());
        event.register(HIDE_KEY.get());
        event.register(HIDE_ALL_KEY.get());
    }

    private void onClientTick(ClientTickEvent.Post event) {
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

    private void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            return;
        }

        ClientPingHandler.handleRender(event.getPoseStack(), event.getPartialTick());
    }
}
