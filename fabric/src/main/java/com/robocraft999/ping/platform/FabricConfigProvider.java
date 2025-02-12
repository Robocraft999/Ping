package com.robocraft999.ping.platform;

import com.robocraft999.ping.platform.config.FabricConfig;
import com.robocraft999.ping.platform.services.IConfigProvider;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.world.item.DyeColor;

public class FabricConfigProvider implements IConfigProvider {
    @Override
    public int getMaxTicks() {
        return AutoConfig.getConfigHolder(FabricConfig.class).getConfig().client.maxTicks;
    }

    @Override
    public int getExtendedReach() {
        return AutoConfig.getConfigHolder(FabricConfig.class).getConfig().server.extendedReach;
    }

    @Override
    public int getNearfield() {
        return AutoConfig.getConfigHolder(FabricConfig.class).getConfig().client.nearField;
    }

    @Override
    public boolean useCustomColor() {
        return AutoConfig.getConfigHolder(FabricConfig.class).getConfig().client.useCustomColor;
    }

    @Override
    public DyeColor getCustomColor() {
        return AutoConfig.getConfigHolder(FabricConfig.class).getConfig().client.customColor;
    }

    @Override
    public int getClickCooldown() {
        return AutoConfig.getConfigHolder(FabricConfig.class).getConfig().server.clickCooldown;
    }

    @Override
    public boolean shouldRenderPlayerHeads() {
        return AutoConfig.getConfigHolder(FabricConfig.class).getConfig().client.showPlayerHeads;
    }
}
