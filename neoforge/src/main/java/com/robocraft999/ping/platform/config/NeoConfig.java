package com.robocraft999.ping.platform.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NeoConfig {
    public static final NeoConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.IntValue MAX_TICKS;
    public final ModConfigSpec.IntValue EXTENDED_REACH;
    public final ModConfigSpec.IntValue NEARFIELD;

    private NeoConfig(ModConfigSpec.Builder builder){
        MAX_TICKS = builder
                .comment(ConfigConstants.DESCRIPTION_MAX_TICKS)
                .defineInRange(
                    ConfigConstants.NAME_MAX_TICKS,
                    ConfigConstants.DEFAULT_MAX_TICKS,
                    ConfigConstants.RANGE_MIN_MAX_TICKS,
                    ConfigConstants.RANGE_MAX_MAX_TICKS
                );

        EXTENDED_REACH = builder
                .comment(ConfigConstants.DESCRIPTION_EXTENDED_REACH)
                .defineInRange(
                    ConfigConstants.NAME_EXTENDED_REACH,
                    ConfigConstants.DEFAULT_EXTENDED_REACH,
                    ConfigConstants.RANGE_MIN_EXTENDED_REACH,
                    ConfigConstants.RANGE_MAX_EXTENDED_REACH
                );

        NEARFIELD = builder
                .comment(ConfigConstants.DESCRIPTION_NEARFIELD)
                .defineInRange(
                    ConfigConstants.NAME_NEARFIELD,
                    ConfigConstants.DEFAULT_NEARFIELD,
                    ConfigConstants.RANGE_MIN_NEARFIELD,
                    ConfigConstants.RANGE_MAX_NEARFIELD
                );
    }

    static {
        Pair<NeoConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(NeoConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
