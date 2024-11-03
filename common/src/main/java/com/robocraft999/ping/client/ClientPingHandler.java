package com.robocraft999.ping.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robocraft999.ping.client.renderer.PingRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ClientPingHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final float MAX_TICKS = 40;
    private static final float extendedReach = 50;
    private static final float farDistanceSquared = 20*20;

    private static float currentTicks = MAX_TICKS;
    private static HitResult result;

    public static void handleTick(){
        var player = mc.player;
        if (player != null){
            double blockReach = player.blockInteractionRange() + extendedReach;
            double entityReach = player.entityInteractionRange() + extendedReach;
            result = PingRenderer.rayTrace(player, blockReach, entityReach);
            if (result != null){
                currentTicks = MAX_TICKS;
            }
        }
    }

    public static void handleRender(LevelRenderer renderer, PoseStack poseStack, float partialTicks){
        if (mc.player == null || mc.level == null)
            return;

        poseStack.pushPose();
        Camera mainCamera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 projectedView = mainCamera.getPosition();
        poseStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        //boolean active = activeTarget == target;
        boolean active = true;

        // needed for smooth rendering
        // the boolean value controls whether it's still smooth while the game world is paused (e.g. /tick freeze)

        if (result != null){
            if (currentTicks > 0){
                PingRenderer.render(result, renderer, poseStack, active, result.distanceTo(mc.player) > farDistanceSquared, partialTicks);
            } else {
                result = null;
            }
        }

        poseStack.popPose();
        if (currentTicks > 0){
            currentTicks -= partialTicks;
        }
    }
}
