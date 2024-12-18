package com.sammy.malum.core.events;

import com.sammy.malum.*;
import com.sammy.malum.client.renderer.item.SpiritJarItemRenderer;
import com.sammy.malum.client.screen.tooltip.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.core.handlers.client.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class ClientSetupEvents {



    public static void registerTooltipComponentManagers() {
        event.register(SoulwovenPouchContents.class, ClientSoulwovenPouchTooltip::new);
    }

    public static void registerOverlays() {
        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, MalumMod.malumPath("soul_ward"),
                SoulWardRenderHandler::renderSoulWard);

        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, MalumMod.malumPath("hidden_blade_cooldown"),
                HiddenBladeRenderHandler::renderHiddenBladeCooldown);

        event.registerAboveAll(MalumMod.malumPath("touch_of_darkness"),
                TouchOfDarknessRenderHandler::renderDarknessVignette);
    }

}