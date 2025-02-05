package com.sammy.malum.events;

import com.sammy.malum.common.item.banner.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        SoulwovenBannerBlockItem.addBannerVariantsToCreativeTab(event);
    }
}