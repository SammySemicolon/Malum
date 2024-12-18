package com.sammy.malum.common.packets.particle.base.color;

import com.sammy.malum.common.packets.particle.base.BlockBasedParticleEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.awt.*;

public abstract class ColorBasedBlockParticleEffectPacket extends BlockBasedParticleEffectPacket {
    protected final Color color;

    public ColorBasedBlockParticleEffectPacket(Color color, BlockPos pos) {
        super(pos);
        this.color = color;
    }

    public ColorBasedBlockParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf);
        this.color =  new Color(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        super.write(buf);
    }
}