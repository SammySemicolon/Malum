package com.sammy.malum.common.block.curiosities.weavers_workbench;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class WeaversWorkbenchItemHandler extends ItemStackHandler {

    public final WeaversWorkbenchBlockEntity entity;
    public final int outputs;
    public boolean isCrafting;

    public WeaversWorkbenchItemHandler(int size, int outputs, WeaversWorkbenchBlockEntity blockEntity) {
        super(size + outputs);
        this.entity = blockEntity;
        this.outputs = outputs;
    }

    @Override
    public void onContentsChanged(int slot) {
        if (!isCrafting) {
            this.entity.setChanged();
            if (slot < 2) {
                this.entity.tryCraft();
            }
        }
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (slot >= this.getSlots() - this.outputs) {
            return stack;
        }
        //todo: check if item allowed
        return super.insertItem(slot, stack, simulate);
    }
}