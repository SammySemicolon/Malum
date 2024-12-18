package com.sammy.malum.common.packets.particle.base.spirit;

import com.sammy.malum.common.packets.particle.base.PositionBasedParticleEffectPacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;


public abstract class SpiritBasedParticleEffectPacket extends PositionBasedParticleEffectPacket {
    protected final List<String> spirits;

    public SpiritBasedParticleEffectPacket(List<String> spirits, Vec3 pos) {
        super(pos.x, pos.y, pos.z);
        this.spirits = spirits;
    }

    public SpiritBasedParticleEffectPacket(FriendlyByteBuf buf) {
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

    }

    protected abstract void handle(ClientPlayNetworking.Context context, MalumSpiritType spiritType);


    public static <T extends SpiritBasedParticleEffectPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        int strings = buf.readInt();
        List<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++) {
            spirits.add(buf.readUtf());
        }
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return provider.getPacket(spirits, posX, posY, posZ);
    }

    public interface PacketProvider<T extends SpiritBasedParticleEffectPacket> {
        T getPacket(List<String> spirits, double posX, double posY, double posZ);
    }
}
