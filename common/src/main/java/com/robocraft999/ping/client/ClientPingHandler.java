package com.robocraft999.ping.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robocraft999.ping.Constants;
import com.robocraft999.ping.client.renderer.PingRenderer;
import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.platform.Services;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ClientPingHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final float MAX_TICKS = 40;
    private static final float extendedReach = 50;
    public static final float farDistanceSquared = 20*20;

    private static float currentTicks = MAX_TICKS;
    private static HitResult result;
    private static ConcurrentHashMap<PingRequest, Float> activePings = new ConcurrentHashMap<>();
    private static int color;

    public static void handleTick(){
        Constants.LOG.info("Pinging");
        var player = mc.player;
        if (player != null){
            double blockReach = player.blockInteractionRange() + extendedReach;
            double entityReach = player.entityInteractionRange() + extendedReach;
            result = PingRenderer.rayTrace(player, blockReach, entityReach);
            if (result instanceof BlockHitResult blockHitResult){
                Services.NETWORK.sendToServer(new PingRequest(blockHitResult.getBlockPos(), personalColor(), player.getGameProfile()));
            }
        }
    }

    private static int personalColor(){
        if (color == 0){
            Random random = new Random();
            color = new Color((int)(random.nextDouble() * 255), (int)(random.nextDouble() * 255), (int)(random.nextDouble() * 255)).getRGB();
        }
        return color;
    }

    public static void handleRender(LevelRenderer renderer, PoseStack poseStack, DeltaTracker delta){
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

        var partialTicks = delta.getGameTimeDeltaPartialTick(true);
        activePings.forEach((request, remainingTicks) -> {
            if (remainingTicks > 0){
                PingRenderer.render(request, renderer, poseStack, active, partialTicks);
                activePings.put(request, remainingTicks - delta.getRealtimeDeltaTicks());
            } else {
                activePings.remove(request);
            }
        });

        poseStack.popPose();
    }

    public static void handle(PingRequest request){
        activePings.put(request, MAX_TICKS);
    }
}
