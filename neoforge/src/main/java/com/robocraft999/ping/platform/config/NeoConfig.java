package com.robocraft999.ping.platform.config;

import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NeoConfig {
    public static final Client CLIENT_CONFIG;
    public static final ModConfigSpec CLIENT_CONFIG_SPEC;
    public static final Server SERVER_CONFIG;
    public static final ModConfigSpec SERVER_CONFIG_SPEC;

    public static class Client {
        public final ModConfigSpec.IntValue MAX_TICKS;
        public final ModConfigSpec.IntValue NEARFIELD;
        public final ModConfigSpec.BooleanValue USE_CUSTOM_COLOR;
        public final ModConfigSpec.EnumValue<DyeColor> CUSTOM_COLOR;
        public final ModConfigSpec.BooleanValue SHOW_PLAYER_HEADS;

        private Client(ModConfigSpec.Builder builder){
            MAX_TICKS = builder
                    .comment(ConfigConstants.DESCRIPTION_MAX_TICKS)
                    .defineInRange(
                            ConfigConstants.NAME_MAX_TICKS,
                            ConfigConstants.DEFAULT_MAX_TICKS,
                            ConfigConstants.RANGE_MIN_MAX_TICKS,
                            ConfigConstants.RANGE_MAX_MAX_TICKS
                    );
            NEARFIELD = builder
                    .comment(ConfigConstants.DESCRIPTION_NEARFIELD)
                    .defineInRange(
                            ConfigConstants.NAME_NEARFIELD,
                            ConfigConstants.DEFAULT_NEARFIELD,
                            ConfigConstants.RANGE_MIN_NEARFIELD,
                            ConfigConstants.RANGE_MAX_NEARFIELD
                    );
            USE_CUSTOM_COLOR = builder
                    .comment(ConfigConstants.DESCRIPTION_USE_CUSTOM_COLOR)
                    .define(ConfigConstants.NAME_USE_CUSTOM_COLOR, false);
            CUSTOM_COLOR = builder
                    .comment(ConfigConstants.DESCRIPTION_CUSTOM_COLOR)
                    .defineEnum(ConfigConstants.NAME_CUSTOM_COLOR, DyeColor.WHITE);
            SHOW_PLAYER_HEADS = builder
                    .comment(ConfigConstants.DESCRIPTION_SHOW_PLAYER_HEADS)
                    .define(ConfigConstants.NAME_SHOW_PLAYER_HEADS, true);
        }
    }

    public static class Server {
        public final ModConfigSpec.IntValue EXTENDED_REACH;
        public final ModConfigSpec.IntValue CLICK_COOLDOWN;

        private Server(ModConfigSpec.Builder builder) {
            EXTENDED_REACH = builder
                    .comment(ConfigConstants.DESCRIPTION_EXTENDED_REACH)
                    .defineInRange(
                            ConfigConstants.NAME_EXTENDED_REACH,
                            ConfigConstants.DEFAULT_EXTENDED_REACH,
                            ConfigConstants.RANGE_MIN_EXTENDED_REACH,
                            ConfigConstants.RANGE_MAX_EXTENDED_REACH
                    );
            CLICK_COOLDOWN = builder
                    .comment(ConfigConstants.DESCRIPTION_CLICK_COOLDOWN)
                    .defineInRange(
                            ConfigConstants.NAME_CLICK_COOLDOWN,
                            ConfigConstants.DEFAULT_CLICK_COOLDOWN,
                            ConfigConstants.RANGE_MIN_CLICK_COOLDOWN,
                            ConfigConstants.RANGE_MAX_CLICK_COOLDOWN
                    );
        }

    }


    static {
        Pair<Client, ModConfigSpec> clientPair =
                new ModConfigSpec.Builder().configure(Client::new);

        CLIENT_CONFIG = clientPair.getLeft();
        CLIENT_CONFIG_SPEC = clientPair.getRight();

        Pair<Server, ModConfigSpec> serverPair =
                new ModConfigSpec.Builder().configure(Server::new);

        SERVER_CONFIG = serverPair.getLeft();
        SERVER_CONFIG_SPEC = serverPair.getRight();
    }
}
