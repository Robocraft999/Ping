package com.robocraft999.ping.platform;

import com.robocraft999.ping.platform.config.NeoConfig;
import com.robocraft999.ping.platform.services.IConfigProvider;

public class NeoForgeConfigProvider implements IConfigProvider {
    @Override
    public int getMaxTicks() {
        return NeoConfig.CONFIG.MAX_TICKS.get();
    }

    @Override
    public int getExtendedReach() {
        return NeoConfig.CONFIG.EXTENDED_REACH.get();
    }

    @Override
    public int getNearfield() {
        return NeoConfig.CONFIG.NEARFIELD.get();
    }
}
