package com.sammy.malum.common.item.curiosities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;


public class TemporarilyDisabledItem extends Item {

    public TemporarilyDisabledItem(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof ServerPlayer player) {
            var disabled = pStack.get(DataComponentRegistry.DISABLED);
            if (disabled != null) {
                long time = disabled.time();
                if (pLevel.getGameTime() >= time) {
                    enable(player, pSlotId);
                    return;
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public static void disable(ServerPlayer player, int slot) {
        disable(player, slot, ItemRegistry.SOUL_OF_A_SCYTHE.get());
    }
    public static void disable(ServerPlayer player, int slot, Item disabledItemType) {
        var inventory = player.getInventory();
        var disabled = disabledItemType.getDefaultInstance();
        disabled.set(DataComponentRegistry.DISABLED, new Disabled(inventory.getItem(slot), player.level().getGameTime() + 300));
        inventory.setItem(slot, disabled);
    }

    public static void enable(ServerPlayer player, int slot) {
        var inventory = player.getInventory();
        var disabledItem = inventory.getItem(slot);
        var disabled = disabledItem.get(DataComponentRegistry.DISABLED);
        if (disabled != null) {
            var original = disabled.item();
            if (!original.isEmpty()) {
                inventory.setItem(slot, original);
            }
        }
    }
    public record Disabled(ItemStack item, long time) {
        public static Codec<TemporarilyDisabledItem.Disabled> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ItemStack.OPTIONAL_CODEC.fieldOf("item").forGetter(TemporarilyDisabledItem.Disabled::item),
                Codec.LONG.fieldOf("time").forGetter(TemporarilyDisabledItem.Disabled::time)
        ).apply(instance, TemporarilyDisabledItem.Disabled::new));

        public static StreamCodec<RegistryFriendlyByteBuf, TemporarilyDisabledItem.Disabled> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC,
                p -> p.item,
                ByteBufCodecs.VAR_LONG,
                p -> p.time,
                TemporarilyDisabledItem.Disabled::new
        );
    }
}
