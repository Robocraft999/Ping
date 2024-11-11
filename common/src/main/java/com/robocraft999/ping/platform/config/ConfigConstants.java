package com.robocraft999.ping.platform.config;

public class ConfigConstants {

    //MAX_TICKS
    public static final String NAME_MAX_TICKS = "max_ticks";
    public static final String DESCRIPTION_MAX_TICKS = "The amount of ticks the ping should be visible.";
    public static final int RANGE_MIN_MAX_TICKS = 5;
    //TODO maybe add keybind to hide all active pings
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
    public static final String DESCRIPTION_NEARFIELD = "The distance from which the ping changes from Block outline to beacon beam (in Blocks)";
    public static final int RANGE_MIN_NEARFIELD = 0;
    public static final int RANGE_MAX_NEARFIELD = Integer.MAX_VALUE;
    public static final int DEFAULT_NEARFIELD = 20;
}
