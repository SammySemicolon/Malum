package com.sammy.malum.common.packets.particle.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import team.lodestar.lodestone.systems.network.LodestonePayload;


public abstract class PositionBasedParticleEffectPacket implements CustomPacketPayload, LodestonePayload {
    protected final double posX;
    protected final double posY;
    protected final double posZ;

    public PositionBasedParticleEffectPacket(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public PositionBasedParticleEffectPacket(FriendlyByteBuf buf) {
        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
}
