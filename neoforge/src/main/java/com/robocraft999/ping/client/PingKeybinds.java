package com.robocraft999.ping.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class PingKeybinds {
    public static final Lazy<KeyMapping> PING_KEY = Lazy.of(() -> new KeyMapping(
            "key.ping.ping",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_5,
            "key.categories.ping"));
}
