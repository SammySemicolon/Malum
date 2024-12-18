package com.sammy.malum.common.packets;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.core.handlers.enchantment.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.network.LodestonePayload;

public class ReplenishingCooldownUpdatePayload implements CustomPacketPayload, LodestonePayload {
    private final Item item;
    private final int enchantmentLevel;
    public static CustomPacketPayload.Type<ReplenishingCooldownUpdatePayload> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("replenish_cooldown"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, ReplenishingCooldownUpdatePayload> STREAM_CODEC = CustomPacketPayload.codec(ReplenishingCooldownUpdatePayload::write, ReplenishingCooldownUpdatePayload::new);


    public ReplenishingCooldownUpdatePayload(Item item, int enchantmentLevel) {
        this.item = item;
        this.enchantmentLevel = enchantmentLevel;
    }

    public ReplenishingCooldownUpdatePayload(FriendlyByteBuf buf) {
        this.item = BuiltInRegistries.ITEM.byId(buf.readInt());
        this.enchantmentLevel = buf.readInt();
    }

    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(BuiltInRegistries.ITEM.getId(item));
        friendlyByteBuf.writeInt(this.enchantmentLevel);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        ReplenishingHandler.replenishStaffCooldown((AbstractStaffItem) item, context.player(), enchantmentLevel);
    }
}