package com.sammy.malum.events;

import com.sammy.malum.common.item.banner.*;
import com.sammy.malum.registry.common.DataMapRegistry;
import com.sammy.malum.registry.common.block.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

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
    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        DataMapRegistry.registerDataMapTypes(event);
    }
}