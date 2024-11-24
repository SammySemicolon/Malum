package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.WeaversWorkbenchContainerScreen;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.*;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.sammy.malum.MalumMod.MALUM;

public class ContainerRegistry {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, MALUM);
//
//    public static final DeferredHolder<MenuType<SpiritPouchContainer>> SPIRIT_POUCH = CONTAINERS.register("spirit_pouch", () -> IForgeMenuType.create((int id, Inventory inv, FriendlyByteBuf extraData) -> new SpiritPouchContainer(id, inv, extraData.readItem())));
    public static final DeferredHolder<MenuType<?>, MenuType<WeaversWorkbenchContainer>> WEAVERS_WORKBENCH = CONTAINERS.register("weavers_workbench", () -> new MenuType<>(WeaversWorkbenchContainer::new, FeatureFlags.DEFAULT_FLAGS));
//


    @EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void bindContainerRenderers(RegisterMenuScreensEvent event) {
                event.register(ContainerRegistry.WEAVERS_WORKBENCH.get(), WeaversWorkbenchContainerScreen::new);
        }
    }
}