package com.robocraft999.ping.network;

import com.mojang.authlib.GameProfile;
import com.robocraft999.ping.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record PingRequest(BlockPos pos, int color, GameProfile owner) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PingRequest> TYPE = new CustomPacketPayload.Type<>(Constants.modLoc("ping_request"));
    public static final StreamCodec<ByteBuf, PingRequest> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(BlockPos.CODEC),
            PingRequest::pos,
            ByteBufCodecs.INT,
            PingRequest::color,
            ByteBufCodecs.GAME_PROFILE,
            PingRequest::owner,
            PingRequest::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
