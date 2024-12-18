package com.sammy.malum.common.packets.particle.base.color;

import com.sammy.malum.common.packets.particle.base.PositionBasedParticleEffectPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.awt.*;

public abstract class ColorBasedParticleEffectPacket extends PositionBasedParticleEffectPacket {
    protected final Color color;

    public ColorBasedParticleEffectPacket(Color color, double posX, double posY, double posZ) {
        super(posX, posY, posZ);
        this.color = color;
    }

    public ColorBasedParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf);
        this.color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        super.write(buf);
    }
}