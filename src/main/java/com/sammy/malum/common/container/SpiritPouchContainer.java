package com.sammy.malum.common.container;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.*;

public abstract class SpiritPouchContainer extends AbstractContainerMenu {
    protected SpiritPouchContainer(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }
//    private final Container inventory;
//
//    public SpiritPouchContainer(int windowId, Inventory playerInv, ItemStack backpack) {
//        this(ContainerRegistry.SPIRIT_POUCH.get(), windowId, playerInv, SpiritPouchItem.getInventory(backpack));
//    }
//
//    public SpiritPouchContainer(MenuType<? extends SpiritPouchContainer> containerType, int windowId, Inventory playerInv, Container inventory) {
//        super(containerType, windowId);
//        this.inventory = inventory;
//        inventory.startOpen(playerInv.player);
//        for (int i = 0; i < inventory.getContainerSize() / 9f; ++i) {
//            for (int j = 0; j < 9; ++j) {
//                int index = i * 9 + j;
//                addSlot(new Slot(inventory, index, 8 + j * 18, 18 + i * 18) {
//                    @Override
//                    public boolean mayPlace(ItemStack stack) {
//                        return stack.getItem() instanceof SpiritShardItem;
//                    }
//                });
//            }
//        }
//        int offset = offset();
//        for (int l = 0; l < 3; ++l) {
//            for (int j1 = 0; j1 < 9; ++j1) {
//                this.addSlot(new Slot(playerInv, j1 + (l + 1) * 9, 8 + j1 * 18, offset + 84 + l * 18) {
//                    @Override
//                    public boolean mayPickup(Player pPlayer) {
//                        return !(getItem().getItem() instanceof SpiritPouchItem);
//                    }
//
//                    @Override
//                    public boolean mayPlace(ItemStack pStack) {
//                        return !(getItem().getItem() instanceof SpiritPouchItem);
//                    }
//                });
//            }
//        }
//
//        for (int i1 = 0; i1 < 9; ++i1) {
//            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, offset + 142) {
//                @Override
//                public boolean mayPickup(Player pPlayer) {
//                    return !(getItem().getItem() instanceof SpiritPouchItem);
//                }
//
//                @Override
//                public boolean mayPlace(ItemStack pStack) {
//                    return !(getItem().getItem() instanceof SpiritPouchItem);
//                }
//            });
//        }
//    }
//
//    @Override
//    public void removed(Player playerIn) {
//        playerIn.level().playSound(null, playerIn.blockPosition(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1, 1);
//        super.removed(playerIn);
//        this.inventory.stopOpen(playerIn);
//    }
//
//    public int offset() {
//        return 0;
//    }
//
//    @Override
//    public boolean stillValid(Player playerIn) {
//        return this.inventory.stillValid(playerIn);
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack quickMoveStack(Player playerIn, int index) {
//        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.slots.get(index);
//        if (slot.hasItem()) {
//            ItemStack itemstack1 = slot.getItem();
//            itemstack = itemstack1.copy();
//            if (index < this.inventory.getContainerSize()) {
//                if (!this.moveItemStackTo(itemstack1, this.inventory.getContainerSize(), this.slots.size(), true)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getContainerSize(), false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty()) {
//                slot.set(ItemStack.EMPTY);
//            } else {
//                slot.setChanged();
//            }
//        }
//
//        return itemstack;
//    }
//
//    public void update(ItemInventory newData) {
//        for (int i = 0; i < newData.getContainerSize(); i++) {
//            inventory.setItem(i, newData.getItem(i));
//        }
//    }
}
