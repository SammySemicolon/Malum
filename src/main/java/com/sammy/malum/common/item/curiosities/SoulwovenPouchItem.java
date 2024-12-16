package com.sammy.malum.common.item.curiosities;

import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.*;
import net.minecraft.core.component.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.apache.commons.lang3.math.*;

import java.util.*;

public class SoulwovenPouchItem extends Item {
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    public SoulwovenPouchItem(Item.Properties properties) {
        super(properties.component(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, SoulwovenPouchContents.EMPTY));
    }

    public static float getFullnessDisplay(ItemStack stack) {
        var contents = stack.getOrDefault(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, SoulwovenPouchContents.EMPTY);
        return contents.weight().floatValue();
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) {
            return false;
        } else {
            var contents = stack.get(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS);
            if (contents == null) {
                return false;
            } else {
                var itemstack = slot.getItem();
                var mutable = new SoulwovenPouchContents.Mutable(contents);
                if (itemstack.isEmpty()) {
                    this.playRemoveOneSound(player);
                    var tryRemove = mutable.removeOne();
                    if (tryRemove != null) {
                        var removed = slot.safeInsert(tryRemove);
                        mutable.tryInsert(removed);
                    }
                } else if (itemstack.getItem().canFitInsideContainerItems()) {
                    int i = mutable.tryTransfer(slot, player);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }

                stack.set(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, mutable.toImmutable());
                return true;
            }
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1) {
            return false;
        }
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            var contents = stack.get(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS);
            if (contents == null) {
                return false;
            } else {
                var mutable = new SoulwovenPouchContents.Mutable(contents);
                if (other.isEmpty()) {
                    var removed = mutable.removeOne();
                    if (removed != null) {
                        this.playRemoveOneSound(player);
                        access.set(removed);
                    }
                } else {
                    int i = mutable.tryInsert(other);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }

                stack.set(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (dropContents(itemstack, player)) {
            this.playDropContentsSound(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var contents = stack.getOrDefault(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, SoulwovenPouchContents.EMPTY);
        return contents.weight().compareTo(Fraction.ZERO) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var contents = stack.getOrDefault(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, SoulwovenPouchContents.EMPTY);
        return Math.min(1 + Mth.mulAndTruncate(contents.weight(), 12), 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    private static boolean dropContents(ItemStack stack, Player player) {
        var contents = stack.get(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS);
        if (contents != null && !contents.isEmpty()) {
            stack.set(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, SoulwovenPouchContents.EMPTY);
            if (player instanceof ServerPlayer) {
                contents.itemsCopy().forEach(item -> player.drop(item, true));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
                ? Optional.ofNullable(stack.get(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS))
                : Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var contents = stack.get(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS);
        if (contents != null) {
            int i = Mth.mulAndTruncate(contents.weight(), 512);
            tooltipComponents.add(Component.translatable("item.minecraft.bundle.fullness", i, 512).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        var contents = itemEntity.getItem().get(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS);
        if (contents != null) {
            itemEntity.getItem().set(DataComponentRegistry.SOULWOVEN_POUCH_CONTENTS, SoulwovenPouchContents.EMPTY);
            ItemUtils.onContainerDestroyed(itemEntity, contents.itemsCopy());
        }
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }
}