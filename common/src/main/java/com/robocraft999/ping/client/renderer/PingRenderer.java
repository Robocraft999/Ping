package com.robocraft999.ping.client.renderer;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.platform.Services;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.joml.Matrix4f;

public class PingRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    public static void render(PingRequest request, PoseStack poseStack, boolean active, float partialTick){
        poseStack.pushPose();

        var pos = request.pos();
        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());

        Minecraft minecraft = Minecraft.getInstance();

        int color = 0xFFFFFF;
        if (active) {
            color = request.color();
        }

        var bufferSource = mc.renderBuffers().bufferSource();
        var farDistanceSquared = Math.pow(Services.CONFIG.getNearfield(), 2);
        boolean isFar = pos.distToCenterSqr(mc.player.getX(), mc.player.getY(), mc.player.getZ()) > farDistanceSquared;

        if (!isFar){
            RenderType lineType = RenderType.LINES;
            VertexConsumer lines = bufferSource.getBuffer(lineType);
            LevelRenderer.renderLineBox(poseStack, lines, 0, 0, 0, 1, 1, 1, FastColor.ARGB32.red(color) / 255F, FastColor.ARGB32.green(color) / 255F,
                    FastColor.ARGB32.blue(color) / 255F, 1);

            if (Services.CONFIG.shouldRenderPlayerHeads()){
                poseStack.pushPose();
                ResourceLocation skinLocation = mc.getSkinManager().getInsecureSkin(request.owner()).texture();
                VertexConsumer buffer = bufferSource.getBuffer(RenderType.text(skinLocation));
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                Vec3 camPos = camera.getPosition();
                poseStack.translate(0.5, 1.5, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot() + 180)); // Rotate to face the player
                poseStack.mulPose(Axis.XP.rotationDegrees(-camera.getXRot()));  // Tilt to match player's view

                float size = 0.5f; // Size of the quad
                float halfSize = size / 2;

                // Texture coordinates for the **face only** (not full skin)
                float u0 = 8f / 64f;  // Left of the head (skin file is 64x64)
                float v0 = 8f / 64f;  // Top of the head
                float u1 = 16f / 64f; // Right of the head
                float v1 = 16f / 64f; // Bottom of the head

                int light = 0xF000F0;

                // Render a textured quad
                Matrix4f matrix = poseStack.last().pose();
                buffer.addVertex(matrix, -halfSize, -halfSize, 0).setUv(u0, v1).setColor(-1).setLight(light);
                buffer.addVertex(matrix, halfSize, -halfSize, 0).setUv(u1, v1).setColor(-1).setLight(light);
                buffer.addVertex(matrix, halfSize, halfSize, 0).setUv(u1, v0).setColor(-1).setLight(light);
                buffer.addVertex(matrix, -halfSize, halfSize, 0).setUv(u0, v0).setColor(-1).setLight(light);
                poseStack.popPose();
                bufferSource.endLastBatch();
            }
        } else {
            BeaconRenderer.renderBeaconBeam(poseStack, bufferSource, BeaconRenderer.BEAM_LOCATION, partialTick, 1.0F, mc.level.getGameTime(), 0, 255, color, 0.2F, 0.25F);
        }

        poseStack.popPose();
        minecraft.renderBuffers().bufferSource().endBatch();
    }

    public static HitResult rayTrace(Entity entity, double blockReach, double entityReach) {
        float partialTick = mc.getTimer().getGameTimeDeltaPartialTick(true);
        Vec3 eyePosition = entity.getEyePosition(partialTick);

        Vec3 traceEnd;
        Vec3 lookVector;
        if (mc.hitResult == null) {
            lookVector = entity.getViewVector(partialTick);
            traceEnd = eyePosition.add(lookVector.scale(entityReach));
        } else {
            traceEnd = mc.hitResult.getLocation().subtract(eyePosition);
            lookVector = entity.getViewVector(partialTick);
            // when it comes to a block hit, we only need to find entities that closer than the block
            if (mc.hitResult.getType() == HitResult.Type.BLOCK && traceEnd.lengthSqr() < entityReach * entityReach) {
                traceEnd = eyePosition.add(lookVector.scale(traceEnd.length() + 1e-5));
            } else {
                traceEnd = eyePosition.add(lookVector.scale(entityReach));
            }
        }

        Level world = entity.level();

        if (blockReach != entityReach) {
            traceEnd = eyePosition.add(lookVector.scale(blockReach * 1.001));
        }

        ClipContext.Fluid fluidView = ClipContext.Fluid.NONE;
        CollisionContext collisionContext = CollisionContext.of(entity);
        ClipContext context = new ClipContext(eyePosition, traceEnd, ClipContext.Block.OUTLINE, fluidView, collisionContext);

        BlockHitResult blockResult = world.clip(context);
        if (blockResult.getType() == HitResult.Type.MISS && mc.hitResult instanceof BlockHitResult hit) {
            // weird, we didn't hit a block in our way. try the vanilla result
            blockResult = hit;
        }
        if (blockResult.getType() == HitResult.Type.BLOCK) {
            BlockState state = wrapBlock(world, blockResult, collisionContext);
            if (state == Blocks.AIR.defaultBlockState()){
                blockResult = null;
            }
        } else {
            blockResult = null;
        }
        if (blockResult == null) {
            context = new ClipContext(eyePosition, traceEnd, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, collisionContext);
            blockResult = world.clip(context);
            BlockState state = wrapBlock(world, blockResult, collisionContext);
            if (state == Blocks.AIR.defaultBlockState()){
                blockResult = null;
            }
        }

        return blockResult;
    }

    public static BlockState wrapBlock(BlockGetter level, BlockHitResult hit, CollisionContext context) {
        if (hit.getType() != HitResult.Type.BLOCK) {
            return Blocks.AIR.defaultBlockState();
        }
        return level.getBlockState(hit.getBlockPos());
    }
}
