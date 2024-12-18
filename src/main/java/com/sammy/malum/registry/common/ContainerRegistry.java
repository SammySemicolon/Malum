package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.WeaversWorkbenchContainerScreen;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.*;
import net.minecraft.world.inventory.MenuType;

import static com.sammy.malum.MalumMod.MALUM;

public class ContainerRegistry {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, MALUM);
//
//    public static final DeferredHolder<MenuType<SpiritPouchContainer>> SPIRIT_POUCH = CONTAINERS.register("spirit_pouch", () -> IForgeMenuType.create((int id, Inventory inv, FriendlyByteBuf extraData) -> new SpiritPouchContainer(id, inv, extraData.readItem())));
    public static final DeferredHolder<MenuType<?>, MenuType<WeaversWorkbenchContainer>> WEAVERS_WORKBENCH = CONTAINERS.register("weavers_workbench", () -> new MenuType<>(WeaversWorkbenchContainer::new, FeatureFlags.DEFAULT_FLAGS));
//


    public static class ClientOnly {

        public static void bindContainerRenderers() {
            MenuScreens.register(ContainerRegistry.WEAVERS_WORKBENCH.get(), WeaversWorkbenchContainerScreen::new);
        }
    }
}