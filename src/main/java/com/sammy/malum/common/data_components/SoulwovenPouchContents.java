package com.sammy.malum.common.data_components;

import com.google.common.collect.*;
import com.mojang.serialization.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import org.apache.commons.lang3.math.*;

import java.util.*;
import java.util.stream.*;

public final class SoulwovenPouchContents implements TooltipComponent {

    public static final SoulwovenPouchContents EMPTY = new SoulwovenPouchContents(List.of());
    public static final Codec<SoulwovenPouchContents> CODEC = ItemStack.CODEC.listOf().xmap(SoulwovenPouchContents::new,
            contents -> contents.items);
    public static final StreamCodec<RegistryFriendlyByteBuf, SoulwovenPouchContents> STREAM_CODEC = ItemStack.STREAM_CODEC
        .apply(ByteBufCodecs.list())
        .map(SoulwovenPouchContents::new, contents -> contents.items);

    private static final Fraction BUNDLE_IN_BUNDLE_WEIGHT = Fraction.getFraction(1, 16);
    final List<ItemStack> items;
    final Fraction weight;

    SoulwovenPouchContents(List<ItemStack> items, Fraction weight) {
        this.items = items;
        this.weight = weight;
    }

    public SoulwovenPouchContents(List<ItemStack> items) {
        this(items, computeContentWeight(items));
    }

    private static Fraction computeContentWeight(List<ItemStack> content) {
        var fraction = Fraction.ZERO;

        for (ItemStack itemstack : content) {
            fraction = fraction.add(getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)));
        }

        return fraction;
    }

    static Fraction getWeight(ItemStack stack) {
        var contents = stack.get(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS.get());
        if (contents != null) {
            return BUNDLE_IN_BUNDLE_WEIGHT.add(contents.weight());
        } else {
            int weightMultiplier = stack.is(ItemTagRegistry.SOULHUNTERS_TREASURE) ? 8 : 1;
            return Fraction.getFraction(1, stack.getMaxStackSize()*weightMultiplier);
        }
    }

    public ItemStack getItemUnsafe(int index) {
        return this.items.get(index);
    }

    public Stream<ItemStack> itemCopyStream() {
        return this.items.stream().map(ItemStack::copy);
    }

    public Iterable<ItemStack> items() {
        return this.items;
    }

    public Iterable<ItemStack> itemsCopy() {
        return Lists.transform(this.items, ItemStack::copy);
    }

    public int size() {
        return this.items.size();
    }

    public Fraction weight() {
        return this.weight;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof SoulwovenPouchContents otherContents && this.weight.equals(otherContents.weight) && ItemStack.listMatches(this.items, otherContents.items);
        }
    }

    @Override
    public int hashCode() {
        return ItemStack.hashStackList(this.items);
    }

    @Override
    public String toString() {
        return "BundleContents" + this.items;
    }
    public static class Mutable {
        private final List<ItemStack> items;
        private Fraction weight;

        public Mutable(SoulwovenPouchContents contents) {
            this.items = new ArrayList<>(contents.items);
            this.weight = contents.weight;
        }

        public SoulwovenPouchContents.Mutable clearItems() {
            this.items.clear();
            this.weight = Fraction.ZERO;
            return this;
        }

        private int findStackIndex(ItemStack stack) {
            if (stack.isStackable()) {
                for (int i = 0; i < this.items.size(); i++) {
                    final ItemStack compare = this.items.get(i);
                    if (compare.getCount() >= compare.getMaxStackSize()) {
                        continue;
                    }
                    if (ItemStack.isSameItemSameComponents(compare, stack)) {
                        return i;
                    }
                }
            }
            return -1;
        }

        private int getMaxAmountToAdd(ItemStack stack) {
            Fraction fraction = Fraction.ONE.subtract(this.weight);
            return Math.max(fraction.divideBy(SoulwovenPouchContents.getWeight(stack)).intValue(), 0);
        }

        public int tryInsert(ItemStack stack) {
            if (!stack.isEmpty() && stack.getItem().canFitInsideContainerItems()) {
                int i = Math.min(stack.getCount(), this.getMaxAmountToAdd(stack));
                if (i == 0) {
                    return 0;
                } else {
                    weight = weight.add(SoulwovenPouchContents.getWeight(stack).multiplyBy(Fraction.getFraction(i, 1)));
                    int j = findStackIndex(stack);
                    if (j != -1) {
                        var itemstack = items.remove(j);
                        int transferSize = Math.min(i, stack.getMaxStackSize()-itemstack.getCount());
                        items.addFirst(itemstack.copyWithCount(itemstack.getCount() + transferSize));
                        stack.shrink(transferSize);
                        if (!stack.isEmpty()) //Split remainder into separate stack
                        {
                            int remainder = stack.getCount();
                            weight = weight.add(SoulwovenPouchContents.getWeight(stack).multiplyBy(Fraction.getFraction(remainder, 1)));
                            items.addFirst(itemstack.copyWithCount(remainder));
                            stack.shrink(remainder);
                        }
                    } else {
                        items.addFirst(stack.split(i));
                    }

                    return i;
                }
            } else {
                return 0;
            }
        }

        public int tryTransfer(Slot slot, Player player) {
            ItemStack itemstack = slot.getItem();
            int i = this.getMaxAmountToAdd(itemstack);
            return this.tryInsert(slot.safeTake(itemstack.getCount(), i, player));
        }

        public ItemStack removeOne() {
            if (this.items.isEmpty()) {
                return null;
            } else {
                ItemStack itemstack = this.items.removeFirst().copy();
                this.weight = this.weight.subtract(SoulwovenPouchContents.getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)));
                return itemstack;
            }
        }

        public Fraction weight() {
            return this.weight;
        }

        public SoulwovenPouchContents toImmutable() {
            return new SoulwovenPouchContents(List.copyOf(this.items), this.weight);
        }
    }
}