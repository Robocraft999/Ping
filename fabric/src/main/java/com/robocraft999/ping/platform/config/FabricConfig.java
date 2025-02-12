package com.robocraft999.ping.platform.config;

import com.robocraft999.ping.Constants;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.item.DyeColor;

@Config(name = Constants.MOD_ID)
public class FabricConfig extends PartitioningSerializer.GlobalData{
    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public Client client = new Client();

    @ConfigEntry.Category("server")
    @ConfigEntry.Gui.TransitiveObject
    public Server server = new Server();

    @Config(name = "client")
    public static class Client implements ConfigData {
        @ConfigEntry.BoundedDiscrete(min = ConfigConstants.RANGE_MIN_MAX_TICKS, max = ConfigConstants.RANGE_MAX_MAX_TICKS)
        @Comment(ConfigConstants.DESCRIPTION_MAX_TICKS)
        public int maxTicks = ConfigConstants.DEFAULT_MAX_TICKS;

        @ConfigEntry.BoundedDiscrete(min = ConfigConstants.RANGE_MIN_NEARFIELD, max = ConfigConstants.RANGE_MAX_NEARFIELD)
        @Comment(ConfigConstants.DESCRIPTION_NEARFIELD)
        public int nearField = ConfigConstants.DEFAULT_NEARFIELD;

        @Comment(ConfigConstants.DESCRIPTION_USE_CUSTOM_COLOR)
        public boolean useCustomColor = false;

        @ConfigEntry.ColorPicker
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        @Comment(ConfigConstants.DESCRIPTION_CUSTOM_COLOR)
        public DyeColor customColor = DyeColor.WHITE;

        @Comment(ConfigConstants.DESCRIPTION_SHOW_PLAYER_HEADS)
        public boolean showPlayerHeads = true;
    }

    @Config(name = "server")
    public static class Server implements ConfigData {
        @ConfigEntry.BoundedDiscrete(min = ConfigConstants.RANGE_MIN_EXTENDED_REACH, max = ConfigConstants.RANGE_MAX_EXTENDED_REACH)
        @Comment(ConfigConstants.DESCRIPTION_EXTENDED_REACH)
        public int extendedReach = ConfigConstants.DEFAULT_EXTENDED_REACH;

        @ConfigEntry.BoundedDiscrete(min = ConfigConstants.RANGE_MIN_CLICK_COOLDOWN, max = ConfigConstants.RANGE_MAX_CLICK_COOLDOWN)
        @Comment(ConfigConstants.DESCRIPTION_CLICK_COOLDOWN)
        public int clickCooldown = ConfigConstants.DEFAULT_CLICK_COOLDOWN;
    }
}
