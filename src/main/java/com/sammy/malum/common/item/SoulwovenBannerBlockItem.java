package com.sammy.malum.common.item;

import com.sammy.malum.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

import java.util.*;

import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

public class SoulwovenBannerBlockItem extends BlockItem {
    public SoulwovenBannerBlockItem(Properties properties) {
        super(BlockRegistry.SOULWOVEN_BANNER.get(), properties.component(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN.get(), SoulwovenBannerPatternData.DEFAULT));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        var pattern = stack.get(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN.get());
        if (pattern != null && !pattern.equals(SoulwovenBannerPatternData.DEFAULT)) {
            tooltipComponents.add(Component.translatable(pattern.translationKey()).withStyle(ChatFormatting.GRAY));
        }
    }

    public static float getBannerPattern(ItemStack stack) {
        var pattern = stack.get(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN.get());
        return SoulwovenBannerPatternData.REGISTERED_PATTERNS.indexOf(pattern) ;
    }

    public static void addBannerVariantsToCreativeTab() {
        final var defaultInstance = ItemRegistry.SOULWOVEN_BANNER.get().getDefaultInstance();

        ItemGroupEvents.modifyEntriesEvent(CreativeTabRegistry.BUILDING.getKey()).register(fabricItemGroupEntries -> {
            for (SoulwovenBannerPatternData pattern : SoulwovenBannerPatternData.REGISTERED_PATTERNS) {
                if (pattern.equals(SoulwovenBannerPatternData.DEFAULT)) {
                    continue;
                }
                final ItemStack copy = pattern.getDefaultStack();
                fabricItemGroupEntries.addAfter(defaultInstance, copy);
            }
        });
    }
}