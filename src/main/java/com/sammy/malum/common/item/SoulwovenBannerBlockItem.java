package com.sammy.malum.common.item;

import com.sammy.malum.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.*;

import java.util.*;

import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

public class SoulwovenBannerBlockItem extends BlockItem {
    public SoulwovenBannerBlockItem(Properties properties) {
        super(BlockRegistry.SOULWOVEN_BANNER.get(), properties.component(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN, SoulwovenBannerPatternData.DEFAULT));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        var pattern = stack.get(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN);
        if (pattern != null && !pattern.equals(SoulwovenBannerPatternData.DEFAULT)) {
            tooltipComponents.add(Component.translatable(pattern.translationKey()).withStyle(ChatFormatting.GRAY));
        }
    }

    public static float getBannerPattern(ItemStack stack) {
        var pattern = stack.getOrDefault(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN, SoulwovenBannerPatternData.DEFAULT);
        return SoulwovenBannerPatternData.REGISTERED_PATTERNS.contains(pattern) ? SoulwovenBannerPatternData.REGISTERED_PATTERNS.indexOf(pattern) : 0;
    }

    public static void addBannerVariantsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        final ItemStack defaultInstance = ItemRegistry.SOULWOVEN_BANNER.get().getDefaultInstance();
        if (event.getParentEntries().contains(defaultInstance)) {
            for (SoulwovenBannerPatternData pattern : SoulwovenBannerPatternData.REGISTERED_PATTERNS) {
                if (pattern.equals(SoulwovenBannerPatternData.DEFAULT)) {
                    continue;
                }
                final ItemStack copy = defaultInstance.copy();
                copy.set(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN, pattern);
                if (event.getParentEntries().contains(copy)) {
                    continue;
                }
                event.insertAfter(defaultInstance, copy, PARENT_AND_SEARCH_TABS);
            }
        }
    }
}