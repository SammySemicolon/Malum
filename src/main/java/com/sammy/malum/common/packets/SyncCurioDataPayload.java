package com.sammy.malum.common.packets;

import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.registry.common.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.*;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.network.*;

public class SyncCurioDataPayload implements CustomPacketPayload, LodestonePayload {
    private final int entityId;
    private final CurioData data;

    public static CustomPacketPayload.Type<SyncCurioDataPayload> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("sync_curio"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, SyncCurioDataPayload> STREAM_CODEC = CustomPacketPayload.codec(SyncCurioDataPayload::write, SyncCurioDataPayload::new);


    public SyncCurioDataPayload(int entityId, CurioData data) {
        this.entityId = entityId;
        this.data = data;
    }

    public SyncCurioDataPayload(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.data = CurioData.STREAM_CODEC.decode(buf);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        CurioData.STREAM_CODEC.encode(buf, data);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        Entity entity = context.player().level().getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setAttached(AttachmentTypeRegistry.CURIO_DATA, data);
        }
    }
}