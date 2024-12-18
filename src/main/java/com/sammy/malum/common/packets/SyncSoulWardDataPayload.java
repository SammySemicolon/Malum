package com.sammy.malum.common.packets;

import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.registry.common.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.*;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.network.LodestonePayload;

public class SyncSoulWardDataPayload implements CustomPacketPayload, LodestonePayload {
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        Entity entity = context.player().level().getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setAttached(AttachmentTypeRegistry.SOUL_WARD, data);
        }
    }

    private final int entityId;
    private final SoulWardData data;

    public static CustomPacketPayload.Type<SyncSoulWardDataPayload> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("sync_soul_ward"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, SyncSoulWardDataPayload> STREAM_CODEC = CustomPacketPayload.codec(SyncSoulWardDataPayload::write, SyncSoulWardDataPayload::new);


    public SyncSoulWardDataPayload(int entityId, SoulWardData data) {
        this.entityId = entityId;
        this.data = data;
    }

    public SyncSoulWardDataPayload(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.data = SoulWardData.STREAM_CODEC.decode(buf);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        SoulWardData.STREAM_CODEC.encode(buf, data);
    }
}