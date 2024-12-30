package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class AugmentBlockEntityInventory extends MalumBlockEntityInventory {

    public static AugmentBlockEntityInventory augmentInventory(LodestoneBlockEntity blockEntity, int slotCount) {
        return new AugmentBlockEntityInventory(blockEntity, slotCount, false);
    }
    public static AugmentBlockEntityInventory coreAugmentInventory(LodestoneBlockEntity blockEntity, int slotCount) {
        return new AugmentBlockEntityInventory(blockEntity, slotCount, true);
    }

    public AugmentBlockEntityInventory(LodestoneBlockEntity blockEntity, int slotCount, boolean coreAugment) {
        super(blockEntity, slotCount, 1);
        setInputPredicate(s -> s.has(DataComponentRegistry.ARTIFICE_AUGMENT) && coreAugment == s.get(DataComponentRegistry.ARTIFICE_AUGMENT).isCoreAugment());
    }

    @Override
    public SoundEvent getInsertSound(ItemStack stack) {
        return SoundRegistry.CRUCIBLE_AUGMENT_APPLY.get();
    }

    @Override
    public SoundEvent getExtractSound(ItemStack stack) {
        return SoundRegistry.CRUCIBLE_AUGMENT_REMOVE.get();
    }
}
