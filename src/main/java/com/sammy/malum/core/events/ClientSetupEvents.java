package com.sammy.malum.core.events;

import com.sammy.malum.client.screen.tooltip.*;
import com.sammy.malum.common.data_components.*;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.function.Function;

public class ClientSetupEvents {

    public static void registerTooltipComponentManagers() {
        register(SoulwovenPouchContents.class, ClientSoulwovenPouchTooltip::new);

    }
    public static <T extends TooltipComponent> void register(Class<T> clazz, Function<? super T, ? extends ClientTooltipComponent> factory) {
        TooltipComponentCallback.EVENT.register((tooltipComponent) -> {
            return clazz.isInstance(tooltipComponent) ? (ClientTooltipComponent)factory.apply(clazz.cast(tooltipComponent)) : null;
        });
    }
}