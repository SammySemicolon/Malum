package com.sammy.malum.common.packets;

import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.network.handling.*;
import team.lodestar.lodestone.systems.network.*;

public class SyncCurioDataPayload extends OneSidedPayloadData {
    private final int entityId;
    private final CurioData data;

    public SyncCurioDataPayload(int entityId, CurioData data) {
        this.entityId = entityId;
        this.data = data;
    }

    public SyncCurioDataPayload(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.data = CurioData.STREAM_CODEC.decode(buf);
    }

    public void handle(IPayloadContext context) {
        Entity entity = context.player().level().getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setData(AttachmentTypeRegistry.CURIO_DATA, data);
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        CurioData.STREAM_CODEC.encode(buf, data);
    }
}