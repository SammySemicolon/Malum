package com.sammy.malum.common.packets;

import com.sammy.malum.common.geas.explosion.*;
import com.sammy.malum.common.geas.oath.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.network.handling.*;
import team.lodestar.lodestone.systems.network.*;

public class SyncMaverickStocksPayload extends OneSidedPayloadData {
    private final int entityId;
    private final int stocks;


    public SyncMaverickStocksPayload(int entityId, int stocks) {
        this.entityId = entityId;
        this.stocks = stocks;
    }
    public SyncMaverickStocksPayload(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.stocks = buf.readInt();
    }

    public void handle(IPayloadContext context) {
        Entity entity = context.player().level().getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            var data = livingEntity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO);
            var maverick = MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get();
            var geas = data.getGeasEffect(livingEntity, maverick).getValue();
            if (geas instanceof MaverickGeas maverickGeas) {
                maverickGeas.stocks = stocks;
            }
            livingEntity.setData(AttachmentTypeRegistry.LIVING_SOUL_INFO, data);
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(stocks);
    }
}
