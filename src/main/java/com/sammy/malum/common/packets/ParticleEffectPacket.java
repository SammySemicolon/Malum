package com.sammy.malum.common.packets;

import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

import javax.annotation.Nullable;

public class ParticleEffectPacket extends OneSidedPayloadData {

    private final String id;
    private final PositionEffectData positionData;
    @Nullable
    private final ColorEffectData colorData;
    @Nullable
    private final NBTEffectData nbtData;

    public ParticleEffectPacket(String id, PositionEffectData positionData, @Nullable ColorEffectData colorData, @Nullable NBTEffectData nbtData) {
        this.id = id;
        this.positionData = positionData;
        this.colorData = colorData;
        this.nbtData = nbtData;
    }

    public ParticleEffectPacket(FriendlyByteBuf buf) {
        this.id = buf.readUtf();
        this.positionData = new PositionEffectData(buf);
        this.colorData = buf.readBoolean() ? ColorEffectData.STREAM_CODEC.decode(buf) : null;
        this.nbtData = buf.readBoolean() ? new NBTEffectData(buf.readNbt()) : null;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        positionData.encode(buf);
        boolean nonNullColorData = colorData != null;
        buf.writeBoolean(nonNullColorData);
        if (nonNullColorData) {
            ColorEffectData.STREAM_CODEC.encode(buf, colorData);
        }
        boolean nonNullCompoundTag = nbtData != null;
        buf.writeBoolean(nonNullCompoundTag);
        if (nonNullCompoundTag) {
            buf.writeNbt(nbtData.compoundTag);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        Minecraft instance = Minecraft.getInstance();
        ClientLevel level = instance.level;
        ParticleEffectType particleEffectType = ParticleEffectTypeRegistry.EFFECT_TYPES.get(id);
        if (particleEffectType == null) {
            throw new RuntimeException("This shouldn't be happening.");
        }
        ParticleEffectType.ParticleEffectActor particleEffectActor = particleEffectType.get().get();
        particleEffectActor.act(level, level.random, positionData, colorData, nbtData);
    }
}