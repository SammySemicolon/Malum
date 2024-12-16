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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    public static IClientItemExtensions SPIRIT_JAR_RENDERER = new IClientItemExtensions() {
        BlockEntityWithoutLevelRenderer renderer;

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (renderer != null) {
                return renderer = new SpiritJarItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                        Minecraft.getInstance().getEntityModels());
            }
            return null;
        }
    };

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(SPIRIT_JAR_RENDERER, ItemRegistry.SPIRIT_JAR.get());
    }

    @SubscribeEvent
    public static void registerTooltipComponentManagers(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SoulwovenPouchContents.class, ClientSoulwovenPouchTooltip::new);
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, MalumMod.malumPath("soul_ward"),
                SoulWardRenderHandler::renderSoulWard);

        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, MalumMod.malumPath("hidden_blade_cooldown"),
                HiddenBladeRenderHandler::renderHiddenBladeCooldown);

        event.registerAboveAll(MalumMod.malumPath("touch_of_darkness"),
                TouchOfDarknessRenderHandler::renderDarknessVignette);
    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        ParticleRegistry.registerParticleFactory(event);
        ScreenParticleRegistry.registerParticleFactory(event);
    }
}