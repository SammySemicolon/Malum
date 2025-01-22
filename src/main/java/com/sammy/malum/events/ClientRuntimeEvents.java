package com.sammy.malum.events;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.core.handlers.client.*;
import com.sammy.malum.core.systems.item.HeldItemTracker;
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
    public static void lateRenderTick(RenderFrameEvent.Post event) {
    }

    @SubscribeEvent
    public static void clientTickEvent(ClientTickEvent.Pre event) {
        HeldItemTracker.tickTrackers();

        HiddenBladeRenderHandler.tick(event);
        SoulWardRenderHandler.tick(event);
    }

    @SubscribeEvent
    public static void itemTooltipEvent(ItemTooltipEvent event) {
        GeasItem.addEtchingTooltip(event);
        AugmentItem.addAugmentAttributeTooltip(event);
    }
}
