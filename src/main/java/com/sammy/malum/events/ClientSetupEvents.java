package com.sammy.malum.events;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.extensions.*;
import com.sammy.malum.client.extensions.SpiritJarClientItemExtensions;
import com.sammy.malum.client.screen.tooltip.ClientSoulwovenPouchTooltip;
import com.sammy.malum.common.block.curiosities.mana_mote.ManaMoteBlockClientExtension;
import com.sammy.malum.common.data_components.SoulwovenPouchContentsComponent;
import com.sammy.malum.core.handlers.client.HiddenBladeRenderHandler;
import com.sammy.malum.core.handlers.client.SoulWardRenderHandler;
import com.sammy.malum.core.handlers.client.TouchOfDarknessRenderHandler;
import com.sammy.malum.registry.client.ModelRegistry;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.client.ScreenParticleRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new SpiritJarClientItemExtensions(),
                ItemRegistry.SPIRIT_JAR);
        event.registerItem(new ArmorClientItemExtensions(() -> ModelRegistry.SOUL_HUNTER_ARMOR),
                ItemRegistry.SOUL_HUNTER_CLOAK,
                ItemRegistry.SOUL_HUNTER_ROBE,
                ItemRegistry.SOUL_HUNTER_LEGGINGS,
                ItemRegistry.SOUL_HUNTER_BOOTS);
        event.registerItem(new ArmorClientItemExtensions(() -> ModelRegistry.SOUL_STAINED_ARMOR),
                ItemRegistry.SOUL_STAINED_STEEL_HELMET,
                ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE,
                ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS,
                ItemRegistry.SOUL_STAINED_STEEL_BOOTS);
        event.registerItem(new MalignantArmorItemExtensions(() -> ModelRegistry.MALIGNANT_LEAD_ARMOR),
                ItemRegistry.MALIGNANT_STRONGHOLD_HELMET,
                ItemRegistry.MALIGNANT_STRONGHOLD_CHESTPLATE,
                ItemRegistry.MALIGNANT_STRONGHOLD_LEGGINGS,
                ItemRegistry.MALIGNANT_STRONGHOLD_BOOTS);

        event.registerBlock(new ManaMoteBlockClientExtension(),
            BlockRegistry.SPIRIT_MOTE);
    }

    @SubscribeEvent
    public static void registerTooltipComponentManagers(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SoulwovenPouchContentsComponent.class, ClientSoulwovenPouchTooltip::new);
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
