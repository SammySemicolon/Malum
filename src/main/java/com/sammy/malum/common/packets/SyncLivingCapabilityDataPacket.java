package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public class SyncLivingCapabilityDataPacket extends OneSidedPayloadData {
    private final int entityId;
    private final CompoundTag capability;

    public SyncLivingCapabilityDataPacket(int entityId, CompoundTag capability) {
        this.entityId = entityId;
        this.capability = capability;
    }

    public SyncLivingCapabilityDataPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.capability = buf.readNbt();
    }

    @OnlyIn(Dist.CLIENT)
    public void handle(IPayloadContext context) {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity instanceof LivingEntity livingEntity) {
            MalumLivingEntityDataCapability.getCapabilityOptional(livingEntity).ifPresent(c -> c.pullFromNBT(capability));
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeNbt(capability);
    }

}