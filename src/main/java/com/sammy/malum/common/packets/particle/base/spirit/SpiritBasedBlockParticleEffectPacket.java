package com.sammy.malum.common.packets.particle.base.spirit;

import com.sammy.malum.common.packets.particle.base.BlockBasedParticleEffectPacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public abstract class SpiritBasedBlockParticleEffectPacket extends BlockBasedParticleEffectPacket {
    protected final List<String> spirits;

    public SpiritBasedBlockParticleEffectPacket(List<String> spirits, BlockPos pos) {
        super(pos);
        this.spirits = spirits;
    }

    public SpiritBasedBlockParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf);
        int strings = buf.readInt();
        List<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++) {
            spirits.add(buf.readUtf());
        }
        this.spirits = spirits;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        super.serialize(buf);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        for (String string : spirits) {
            handle(iPayloadContext, MalumSpiritType.getSpiritType(string));
        }
    }

    protected abstract void handle(IPayloadContext iPayloadContext, MalumSpiritType spiritType);

}
