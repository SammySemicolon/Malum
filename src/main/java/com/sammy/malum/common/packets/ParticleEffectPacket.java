package com.sammy.malum.common.packets;

import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.network.LodestonePayload;


public class ParticleEffectPacket implements CustomPacketPayload, LodestonePayload {

    private final String id;
    private final PositionEffectData positionData;
    @Nullable
    private final ColorEffectData colorData;
    @Nullable
    private final NBTEffectData nbtData;

    public static CustomPacketPayload.Type<ParticleEffectPacket> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("particle_effect"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, ParticleEffectPacket> STREAM_CODEC = CustomPacketPayload.codec(ParticleEffectPacket::write, ParticleEffectPacket::new);


    public ParticleEffectPacket(String id, PositionEffectData positionData, @Nullable ColorEffectData colorData, @Nullable NBTEffectData nbtData) {
        this.id = id;
        this.positionData = positionData;
        this.colorData = colorData;
        this.nbtData = nbtData;
    }

    public ParticleEffectPacket(FriendlyByteBuf buf) {
        this.id = buf.readUtf();
        this.positionData = new PositionEffectData(buf);
        this.colorData = buf.readBoolean() ? new ColorEffectData(buf) : null;
        this.nbtData = buf.readBoolean() ? new NBTEffectData(buf.readNbt()) : null;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        positionData.encode(buf);
        boolean nonNullColorData = colorData != null;
        buf.writeBoolean(nonNullColorData);
        if (nonNullColorData) {
            colorData.encode(buf);
        }
        boolean nonNullCompoundTag = nbtData != null;
        buf.writeBoolean(nonNullCompoundTag);
        if (nonNullCompoundTag) {
            buf.writeNbt(nbtData.compoundTag);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
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