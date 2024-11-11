package com.robocraft999.ping.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robocraft999.ping.network.PingRequest;
import com.robocraft999.ping.platform.Services;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

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
        } else {
            BeaconRenderer.renderBeaconBeam(poseStack, bufferSource, BeaconRenderer.BEAM_LOCATION, partialTick, 1.0F, mc.level.getGameTime(), 0, 255, color, 0.2F, 0.25F);
        }

        poseStack.popPose();
        minecraft.renderBuffers().bufferSource().endBatch();
    }

    public static HitResult rayTrace(Entity entity, double blockReach, double entityReach) {
        Camera camera = mc.gameRenderer.getMainCamera();
        float partialTick = mc.getTimer().getGameTimeDeltaPartialTick(true);
        Vec3 eyePosition = entity.getEyePosition(partialTick);
        boolean startFromEye = true;
        Vec3 traceStart = startFromEye ? eyePosition : camera.getPosition();
        double distance = startFromEye ? 0 : eyePosition.distanceToSqr(traceStart);
        if (distance > 1e-5) {
            distance = Math.sqrt(distance);
            blockReach += distance;
        }

        Vec3 traceEnd;
        Vec3 lookVector;
        if (mc.hitResult == null) {
            lookVector = startFromEye ? entity.getViewVector(partialTick) : new Vec3(camera.getLookVector());
            traceEnd = traceStart.add(lookVector.scale(entityReach));
        } else {
            traceEnd = mc.hitResult.getLocation().subtract(traceStart);
            lookVector = startFromEye ? entity.getViewVector(partialTick) : traceEnd.normalize();
            // when it comes to a block hit, we only need to find entities that closer than the block
            if (mc.hitResult.getType() == HitResult.Type.BLOCK && traceEnd.lengthSqr() < entityReach * entityReach) {
                traceEnd = startFromEye ? traceStart.add(lookVector.scale(traceEnd.length() + 1e-5)) : mc.hitResult.getLocation().add(
                        lookVector.scale(1e-5));
            } else {
                traceEnd = traceStart.add(lookVector.scale(entityReach));
            }
        }

        Level world = entity.level();
        AABB bound = new AABB(traceStart, traceEnd);

        if (blockReach != entityReach) {
            traceEnd = traceStart.add(lookVector.scale(blockReach * 1.001));
        }

        BlockState eyeBlock = world.getBlockState(BlockPos.containing(eyePosition));
        ClipContext.Fluid fluidView = ClipContext.Fluid.NONE;
        CollisionContext collisionContext = CollisionContext.of(entity);
        ClipContext context = new ClipContext(traceStart, traceEnd, ClipContext.Block.OUTLINE, fluidView, collisionContext);

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
            context = new ClipContext(traceStart, traceEnd, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, collisionContext);
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
