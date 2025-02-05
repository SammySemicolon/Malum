package com.sammy.malum.events;

import com.sammy.malum.common.item.banner.*;
import com.sammy.malum.registry.common.block.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.event.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void onEntityJoin(RegisterCapabilitiesEvent event) {
        BlockEntityRegistry.registerCapabilities(event);
    }
    @SubscribeEvent
    public static void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        SoulwovenBannerBlockItem.addBannerVariantsToCreativeTab(event);
    }
}