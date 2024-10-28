package com.sammy.malum.core.events;

import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.item.augment.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ClientRuntimeEvents {

    @SubscribeEvent
    public static void renderOverlay(RenderGuiLayerEvent.Post event) {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void lateRenderTick(RenderFrameEvent event) {
    }

    @SubscribeEvent
    public static void clientTickEvent(ClientTickEvent.Pre event) {
        SpiritCrucibleRenderer.checkForTuningFork(event);
        TotemBaseRenderer.checkForTotemicStaff(event);
    }

    @SubscribeEvent
    public static void itemTooltipEvent(ItemTooltipEvent event) {
        AbstractAugmentItem.addAugmentAttributeTooltip(event);
    }
}
