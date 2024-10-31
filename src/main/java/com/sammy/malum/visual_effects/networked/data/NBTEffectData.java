package com.sammy.malum.visual_effects.networked.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTEffectData {

    public static final String ITEM = "stack";

    public final CompoundTag compoundTag;

    public NBTEffectData(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public NBTEffectData() {
        this(new CompoundTag());
    }

    public NBTEffectData(ItemStack stack) {
        this();
//        compoundTag.put(ITEM, stack.save(new CompoundTag()));
    }

    public ItemStack getStack() {
        return ItemStack.EMPTY;
//        return compoundTag.contains(ITEM) ? ItemStack.of(compoundTag.getCompound(ITEM)) : ItemStack.EMPTY;
    }
}
