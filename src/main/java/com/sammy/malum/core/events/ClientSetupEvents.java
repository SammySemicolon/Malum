package com.sammy.malum.core.events;

import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.resources.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {


    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(SpiritJarItem.CUSTOM_RENDERER, ItemRegistry.SPIRIT_JAR.get());
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, MalumMod.malumPath("soul_ward"),
                SoulWardHandler.ClientOnly::renderSoulWard);

        event.registerAboveAll(MalumMod.malumPath("hidden_blade_cooldown"),
                CurioHiddenBladeNecklace.ClientOnly::renderHiddenBladeCooldown);

        event.registerAbove(VanillaGuiLayers.BOSS_OVERLAY, MalumMod.malumPath("touch_of_darkness"),
                TouchOfDarknessHandler.ClientOnly::renderDarknessVignette);
    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        ParticleRegistry.registerParticleFactory(event);
        ScreenParticleRegistry.registerParticleFactory(event);
    }
}