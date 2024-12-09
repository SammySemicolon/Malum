package com.sammy.malum.common.packets;

import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.network.handling.*;
import team.lodestar.lodestone.systems.network.*;

public class SyncSoulWardDataPayload extends OneSidedPayloadData {
    private final int entityId;
    private final SoulWardData data;

    public SyncSoulWardDataPayload(int entityId, SoulWardData data) {
        this.entityId = entityId;
        this.data = data;
    }

    public SyncSoulWardDataPayload(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.data = SoulWardData.STREAM_CODEC.decode(buf);
    }

    public void handle(IPayloadContext context) {
        Entity entity = context.player().level().getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setData(AttachmentTypeRegistry.SOUL_WARD, data);
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        SoulWardData.STREAM_CODEC.encode(buf, data);
    }
}