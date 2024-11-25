package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;

import java.util.function.*;

public class AugmentBlockEntityInventory extends MalumBlockEntityInventory {
    public AugmentBlockEntityInventory(int slotCount, int allowedItemSize) {
        this(slotCount, allowedItemSize, t -> t.getItem() instanceof AbstractAugmentItem augmentItem && !(augmentItem instanceof AbstractCoreAugmentItem));
    }
    public AugmentBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate) {
        super(slotCount, allowedItemSize, inputPredicate);
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
