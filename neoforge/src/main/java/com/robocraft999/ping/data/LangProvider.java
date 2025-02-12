package com.robocraft999.ping.data;

import com.robocraft999.ping.Constants;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Translations.KEY_PING_KEY, "Ping");
        add(Translations.KEY_HIDE_ALL, "Toggle hiding pings");
        add(Translations.KEY_HIDE, "Hide currently visible");
        add(Translations.KEY_KEYBIND_CATEGORY, "Ping");

        add(Translations.KEY_ACONFIG_TITLE, "Ping");
        add(Translations.KEY_ACONFIG_CATEGORY_CLIENT, "Client");
        add(Translations.KEY_ACONFIG_CATEGORY_SERVER, "Server");

        add(Translations.KEY_CONFIG_NAME_MAX_TICKS, "Max Ticks");
        add(Translations.KEY_ACONFIG_OPTION_MAXTICKS, "Max Ticks");

        add(Translations.KEY_CONFIG_NAME_USE_CUSTOM_COLOR, "Use custom color");
        add(Translations.KEY_ACONFIG_OPTION_USE_CUSTOM_COLOR, "Use custom color");

        add(Translations.KEY_CONFIG_NAME_CUSTOM_COLOR, "Custom color");
        add(Translations.KEY_ACONFIG_OPTION_CUSTOM_COLOR, "Custom color");

        add(Translations.KEY_CONFIG_NAME_NEARFIELD, "Nearfield");
        add(Translations.KEY_ACONFIG_OPTION_NEARFIELD, "Nearfield");

        add(Translations.KEY_CONFIG_NAME_EXTENDED_REACH, "Extended Reach");
        add(Translations.KEY_ACONFIG_OPTION_EXTENDED_REACH, "Extended reach");

        add(Translations.KEY_CONFIG_NAME_CLICK_COOLDOWN, "Click cooldown");
        add(Translations.KEY_ACONFIG_OPTION_CLICK_COOLDOWN, "Click cooldown");

        add(Translations.KEY_CONFIG_NAME_SHOW_PLAYER_HEADS, "Show player heads");
        add(Translations.KEY_ACONFIG_OPTION_SHOW_PLAYER_HEADS, "Show player heads");
    }
}
