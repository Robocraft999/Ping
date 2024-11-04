package com.robocraft999.ping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class PingKeyBinds {

    public static KeyMapping createKey(){
        return new KeyMapping(
                "key.ping.ping",
                InputConstants.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_5,
                "key.categories.ping");
    }
}
