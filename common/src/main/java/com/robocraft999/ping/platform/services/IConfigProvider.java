package com.robocraft999.ping.platform.services;

import net.minecraft.world.item.DyeColor;

public interface IConfigProvider{
    int getMaxTicks();
    int getExtendedReach();
    int getNearfield();
    boolean useCustomColor();
    DyeColor getCustomColor();
    int getClickCooldown();
    boolean shouldRenderPlayerHeads();
}
