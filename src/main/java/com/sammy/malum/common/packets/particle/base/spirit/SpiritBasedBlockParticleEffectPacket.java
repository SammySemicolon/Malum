package com.sammy.malum.common.packets.particle.base.spirit;

import com.sammy.malum.common.packets.particle.base.BlockBasedParticleEffectPacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

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
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        super.write(buf);
    }


    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        for (String string : spirits) {
            handle(context, MalumSpiritType.getSpiritType(string));
        }
    }

    protected abstract void handle(ClientPlayNetworking.Context context, MalumSpiritType spiritType);

}
