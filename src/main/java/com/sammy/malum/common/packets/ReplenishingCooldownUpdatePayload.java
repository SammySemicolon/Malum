package com.sammy.malum.common.packets;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.core.handlers.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public class ReplenishingCooldownUpdatePayload extends OneSidedPayloadData {
    private final Item item;
    private final int enchantmentLevel;

    public ReplenishingCooldownUpdatePayload(Item item, int enchantmentLevel) {
        this.item = item;
        this.enchantmentLevel = enchantmentLevel;
    }

    public ReplenishingCooldownUpdatePayload(FriendlyByteBuf buf) {
        this.item = BuiltInRegistries.ITEM.byId(buf.readInt());
        this.enchantmentLevel = buf.readInt();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        EnchantmentEffectEventHandler.replenishStaffCooldown((AbstractStaffItem) item, iPayloadContext.player(), enchantmentLevel);
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(BuiltInRegistries.ITEM.getId(item));
        friendlyByteBuf.writeInt(this.enchantmentLevel);
    }
}