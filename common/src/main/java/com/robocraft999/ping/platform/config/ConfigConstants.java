package com.robocraft999.ping.platform.config;

public class ConfigConstants {

    //MAX_TICKS
    public static final String NAME_MAX_TICKS = "max_ticks";
    public static final String DESCRIPTION_MAX_TICKS = "The amount of ticks the ping should be visible.";
    public static final int RANGE_MIN_MAX_TICKS = 5;
    public static final int RANGE_MAX_MAX_TICKS = Integer.MAX_VALUE;
    public static final int DEFAULT_MAX_TICKS = 40;

    //EXTENDED_REACH
    public static final String NAME_EXTENDED_REACH = "extended_reach";
    public static final String DESCRIPTION_EXTENDED_REACH = "The range in which you can ping which gets added to the default interaction range.";
    public static final int RANGE_MIN_EXTENDED_REACH = 0;
    public static final int RANGE_MAX_EXTENDED_REACH = 256;
    public static final int DEFAULT_EXTENDED_REACH = 50;

    //NEARFIELD
    public static final String NAME_NEARFIELD = "nearfield";
    public static final String DESCRIPTION_NEARFIELD = "The distance from which the ping changes from Block outline to beacon beam (in Blocks).";
    public static final int RANGE_MIN_NEARFIELD = 0;
    public static final int RANGE_MAX_NEARFIELD = Integer.MAX_VALUE;
    public static final int DEFAULT_NEARFIELD = 20;

    //USE_CUSTOM_COLOR
    public static final String NAME_USE_CUSTOM_COLOR = "use_custom_color";
    public static final String DESCRIPTION_USE_CUSTOM_COLOR = "Whether the custom color or a randomly generated color should be used.";

    //CUSTOM_COLOR
    public static final String NAME_CUSTOM_COLOR = "custom_color";
    public static final String DESCRIPTION_CUSTOM_COLOR = "The color that should be used if 'use_custom_color' is enabled.";

    //PING_COOLDOWN
    public static final String NAME_CLICK_COOLDOWN = "click_cooldown";
    public static final String DESCRIPTION_CLICK_COOLDOWN = "The cooldown in ticks for creating a new ping.";
    public static final int RANGE_MIN_CLICK_COOLDOWN = 0;
    public static final int RANGE_MAX_CLICK_COOLDOWN = Integer.MAX_VALUE;
    public static final int DEFAULT_CLICK_COOLDOWN = 5;

    //SHOW_PLAYER_HEADS
    public static final String NAME_SHOW_PLAYER_HEADS = "show_player_heads";
    public static final String DESCRIPTION_SHOW_PLAYER_HEADS = "If the head of the Player who pinged should be displayed above the ping.";
}
