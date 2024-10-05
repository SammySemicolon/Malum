package com.sammy.malum.common.block;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

import java.util.function.*;

public class MalumBlockEntityInventory extends LodestoneBlockEntityInventory {

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate) {
        super(slotCount, allowedItemSize, inputPredicate, outputPredicate);
    }

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate) {
        super(slotCount, allowedItemSize, inputPredicate);
    }

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize) {
        super(slotCount, allowedItemSize);
    }

    @Override
    public void extractItem(Player playerEntity, ItemStack stack, int slot) {
        super.extractItem(playerEntity, stack, slot);
        SoundEvent soundEvent = getExtractSound(stack);

        SoundHelper.playSound(playerEntity, soundEvent, SoundSource.BLOCKS, 0.7f, RandomHelper.randomBetween(playerEntity.getRandom(), 0.8f, 1.2f));
    }

    @Override
    public ItemStack insertItem(Player playerEntity, ItemStack stack) {
        final ItemStack result = super.insertItem(playerEntity, stack);
        if (!result.isEmpty()) {
            SoundEvent soundEvent = getInsertSound(result);
            SoundHelper.playSound(playerEntity, soundEvent, SoundSource.BLOCKS, 0.7f, RandomHelper.randomBetween(playerEntity.getRandom(), 0.8f, 1.2f));
        }
        return result;
    }

    public SoundEvent getExtractSound(ItemStack stack) {
        return stack.getItem() instanceof SpiritShardItem ? SoundRegistry.PEDESTAL_SPIRIT_PICKUP.get() : SoundRegistry.PEDESTAL_ITEM_PICKUP.get();
    }
    public SoundEvent getInsertSound(ItemStack stack) {
        return stack.getItem() instanceof SpiritShardItem ? SoundRegistry.PEDESTAL_SPIRIT_INSERT.get() : SoundRegistry.PEDESTAL_ITEM_INSERT.get();
    }
}
