package com.sammy.malum.common.packets;

import com.sammy.malum.common.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.network.handling.*;
import team.lodestar.lodestone.systems.network.*;

public class SyncLionsHeartDurationPayload extends OneSidedPayloadData {
    private final int entityId;
    private final int lionsHeartDuration;


    public SyncLionsHeartDurationPayload(int entityId, int lionsHeartDuration) {
        this.entityId = entityId;
        this.lionsHeartDuration = lionsHeartDuration;
    }
    public SyncLionsHeartDurationPayload(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.lionsHeartDuration = buf.readInt();
    }

    public void handle(IPayloadContext context) {
        Entity entity = context.player().level().getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            var data = livingEntity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO);
            var authority = MalumGeasEffectTypeRegistry.LIONS_HEART.get();
            var geas = data.getGeasEffect(livingEntity, authority).getValue();
            if (geas instanceof LionsHeartGeas greedGeas) {
                greedGeas.lionsHeartDuration = lionsHeartDuration;
            }
            livingEntity.setData(AttachmentTypeRegistry.LIVING_SOUL_INFO, data);
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(lionsHeartDuration);
    }
}