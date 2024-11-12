package com.robocraft999.ping;

import com.mojang.blaze3d.platform.InputConstants;
import com.robocraft999.ping.data.Translations;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public interface PingKeyBinds {

    static KeyMapping createPingKey() {
        return new KeyMapping(
                Translations.KEY_PING_KEY,
                InputConstants.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_5,
                Translations.KEY_KEYBIND_CATEGORY
        );
    }

    static KeyMapping createHideAllKey() {
        return new KeyMapping(
                Translations.KEY_HIDE_ALL,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                Translations.KEY_KEYBIND_CATEGORY
        );
    }

    static KeyMapping createHideKey() {
        return new KeyMapping(
                Translations.KEY_HIDE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                Translations.KEY_KEYBIND_CATEGORY
        );
    }
}
