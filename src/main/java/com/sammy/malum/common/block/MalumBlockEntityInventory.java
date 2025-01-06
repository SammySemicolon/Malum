package com.sammy.malum.common.block;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

import java.util.function.*;

public class MalumBlockEntityInventory extends LodestoneBlockEntityInventory {

    public static MalumBlockEntityInventory singleItem(LodestoneBlockEntity blockEntity) {
        return new MalumBlockEntityInventory(blockEntity, 1, 1);
    }
    public static MalumBlockEntityInventory singleItemStack(LodestoneBlockEntity blockEntity) {
        return new MalumBlockEntityInventory(blockEntity, 1, 64);
    }
    public static MalumBlockEntityInventory singleNotSpirit(LodestoneBlockEntity blockEntity) {
        return new MalumBlockEntityInventory(blockEntity, 1, 1).setInputPredicate(p -> !p.is(ItemTagRegistry.SPIRITS));
    }
    public static MalumBlockEntityInventory singleStackNotSpirit(LodestoneBlockEntity blockEntity) {
        return new MalumBlockEntityInventory(blockEntity, 1, 64).setInputPredicate(p -> !p.is(ItemTagRegistry.SPIRITS));
    }
    public static MalumBlockEntityInventory items(LodestoneBlockEntity blockEntity, int slotCount) {
        return new MalumBlockEntityInventory(blockEntity, slotCount, 1);
    }
    public static MalumBlockEntityInventory itemStacks(LodestoneBlockEntity blockEntity, int slotCount) {
        return new MalumBlockEntityInventory(blockEntity, slotCount, 64);
    }
    public static MalumBlockEntityInventory notSpirits(LodestoneBlockEntity blockEntity, int slotCount) {
        return new MalumBlockEntityInventory(blockEntity, slotCount, 1).setInputPredicate(p -> !p.is(ItemTagRegistry.SPIRITS));
    }
    public static MalumBlockEntityInventory stacksNotSpirits(LodestoneBlockEntity blockEntity, int slotCount) {
        return new MalumBlockEntityInventory(blockEntity, slotCount, 64).setInputPredicate(p -> !p.is(ItemTagRegistry.SPIRITS));
    }

    protected MalumBlockEntityInventory(LodestoneBlockEntity blockEntity, int slotCount, int allowedItemSize) {
        super(blockEntity, slotCount, allowedItemSize);
        triggerBlockEntityUpdate();
    }

    @Override
    public MalumBlockEntityInventory setInputPredicate(Predicate<ItemStack> inputPredicate) {
        return (MalumBlockEntityInventory) super.setInputPredicate(inputPredicate);
    }


    @Override
    public void giveItemToPlayer(Player playerEntity, ItemStack stack, int slot) {
        super.giveItemToPlayer(playerEntity, stack, slot);
        SoundEvent soundEvent = getExtractSound(stack);

        SoundHelper.playSound(playerEntity, soundEvent, SoundSource.BLOCKS, 0.7f, RandomHelper.randomBetween(playerEntity.getRandom(), 0.8f, 1.2f));
    }

    @Override
    public ItemStack takeItemFromPlayer(Player playerEntity, ItemStack stack) {
        final ItemStack result = super.takeItemFromPlayer(playerEntity, stack);
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
