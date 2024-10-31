package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public class VoidRejectionPayload extends OneSidedPayloadData {
    private final int entityId;

    public VoidRejectionPayload(int entityId) {
        this.entityId = entityId;
    }

    public VoidRejectionPayload(FriendlyByteBuf byteBuf) {
        this.entityId = byteBuf.readInt();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            MalumLivingEntityDataCapability.getCapabilityOptional(livingEntity).ifPresent(c -> c.touchOfDarknessHandler.reject(livingEntity));
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }
}