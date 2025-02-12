package com.robocraft999.ping.platform;

import com.robocraft999.ping.platform.config.NeoConfig;
import com.robocraft999.ping.platform.services.IConfigProvider;
import net.minecraft.world.item.DyeColor;

public class NeoForgeConfigProvider implements IConfigProvider {
    @Override
    public int getMaxTicks() {
        return NeoConfig.CLIENT_CONFIG.MAX_TICKS.get();
    }

    @Override
    public int getExtendedReach() {
        return NeoConfig.SERVER_CONFIG.EXTENDED_REACH.get();
    }

    @Override
    public int getNearfield() {
        return NeoConfig.CLIENT_CONFIG.NEARFIELD.get();
    }

    @Override
    public boolean useCustomColor() {
        return NeoConfig.CLIENT_CONFIG.USE_CUSTOM_COLOR.get();
    }

    @Override
    public DyeColor getCustomColor() {
        return NeoConfig.CLIENT_CONFIG.CUSTOM_COLOR.get();
    }

    @Override
    public int getClickCooldown() {
        return NeoConfig.SERVER_CONFIG.CLICK_COOLDOWN.get();
    }

    @Override
    public boolean shouldRenderPlayerHeads() {
        return NeoConfig.CLIENT_CONFIG.SHOW_PLAYER_HEADS.get();
    }
}
