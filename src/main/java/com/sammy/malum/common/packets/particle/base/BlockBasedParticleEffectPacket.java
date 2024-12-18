package com.sammy.malum.common.packets.particle.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import team.lodestar.lodestone.systems.network.LodestonePayload;

public abstract class BlockBasedParticleEffectPacket implements CustomPacketPayload, LodestonePayload {
    protected final BlockPos pos;

    public BlockBasedParticleEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public BlockBasedParticleEffectPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
}