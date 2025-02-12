package com.robocraft999.ping.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robocraft999.ping.Constants;
import com.robocraft999.ping.client.renderer.PingRenderer;
import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.platform.Services;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ClientPingHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final ConcurrentHashMap<PingRequest, Float> activePings = new ConcurrentHashMap<>();
    private static int clickCooldown = 0;
    private static final List<PingRequest> newPings = new ArrayList<>();
    private static int color;
    private static boolean acceptingPings = true;

    public static void handleClick(){
        if (!acceptingPings)
            return;
        if (clickCooldown > 0)
            return;
        var player = mc.player;
        if (player != null){
            int extendedReach = Services.CONFIG.getExtendedReach();
            double blockReach = player.blockInteractionRange() + extendedReach;
            double entityReach = player.entityInteractionRange() + extendedReach;
            HitResult result = PingRenderer.rayTrace(player, blockReach, entityReach);
            if (result instanceof BlockHitResult blockHitResult){
                Services.NETWORK.sendToServer(new PingRequest(blockHitResult.getBlockPos(), personalColor(), player.getGameProfile(), player.level().dimension()));
            }
            clickCooldown = Services.CONFIG.getClickCooldown();
        }
    }

    public static void handleTick(){
        if (!acceptingPings)
            return;
        if (clickCooldown > 0)
            clickCooldown -= 1;

        var level = Minecraft.getInstance().level;
        newPings.forEach(request -> {
            activePings.put(request, (float) Services.CONFIG.getMaxTicks());
            if (level != null){
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                if (camera.isInitialized()){
                    var pos = request.pos();
                    var dir = pos.getCenter().subtract(camera.getPosition());
                    var soundPos = camera.getPosition().add(dir.normalize().scale(2));
                    level.playLocalSound(soundPos.x, soundPos.y, soundPos.z, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1, 1, false);
                }
            }
        });
        newPings.clear();
    }

    public static void toggleHideAll(){
        acceptingPings = !acceptingPings;
        if (!acceptingPings){
            handleHide();
        }
    }

    public static void handleHide(){
        activePings.clear();
        newPings.clear();
    }

    private static int personalColor(){
        if (color == 0){
            Random random = new Random();
            color = new Color((int)(random.nextDouble() * 255), (int)(random.nextDouble() * 255), (int)(random.nextDouble() * 255)).getRGB();
        }
        if (Services.CONFIG.useCustomColor()){
            return Services.CONFIG.getCustomColor().getFireworkColor();
        }
        return color;
    }

    public static void handleRender(PoseStack poseStack, DeltaTracker delta){
        if (!acceptingPings || mc.player == null || mc.level == null)
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
                PingRenderer.render(request, poseStack, active, partialTicks);
                activePings.put(request, remainingTicks - delta.getRealtimeDeltaTicks());
            } else {
                activePings.remove(request);
            }
        });

        poseStack.popPose();
    }

    public static void handle(PingRequest request){
        if (!acceptingPings)
            return;
        newPings.add(request);
    }
}
