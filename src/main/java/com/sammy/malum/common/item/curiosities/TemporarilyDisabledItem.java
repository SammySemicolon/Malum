package com.sammy.malum.common.item.curiosities;

import com.sammy.malum.registry.common.item.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public class TemporarilyDisabledItem extends Item {

    public static final String DISABLED = "malum:disabled";

    public TemporarilyDisabledItem(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof ServerPlayer player) {
            if (pStack.hasTag()) {
                CompoundTag tag = pStack.getTag();
                if (tag.contains(DISABLED)) {
                    long time = tag.getLong(DISABLED);
                    if (pLevel.getGameTime() >= time) {
                        enable(player, pSlotId);
                        return;
                    }
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
        var disabledTag = disabled.getOrCreateTag();
        var itemTag = new CompoundTag();
        disabledTag.putLong(DISABLED, player.level().getGameTime() + 300);
        inventory.getItem(slot).save(itemTag);
        disabledTag.put("item", itemTag);
        inventory.setItem(slot, disabled);
    }

    @SuppressWarnings("DataFlowIssue")
    public static void enable(ServerPlayer player, int slot) {
        var inventory = player.getInventory();
        var disabledItem = inventory.getItem(slot);
        if (disabledItem.hasTag()) {
            var tag = disabledItem.getTag();
            if (tag.contains("item")) {
                var original = ItemStack.of(tag.getCompound("item"));
                inventory.setItem(slot, original);
            }
        }
    }
}
