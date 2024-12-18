package com.sammy.malum.core.events;

import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.core.handlers.client.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import team.lodestar.lodestone.events.LodestoneItemEvent;

public class ClientRuntimeEvents {


    public static void clientTickEvent() {
        ClientTickEvents.START_CLIENT_TICK.register(SpiritCrucibleRenderer::checkForTuningFork);
        ClientTickEvents.START_CLIENT_TICK.register(HiddenBladeRenderHandler::tick);
        ClientTickEvents.START_CLIENT_TICK.register(SoulWardRenderHandler::tick);
        ClientTickEvents.START_CLIENT_TICK.register(TotemBaseRenderer::checkForTotemicStaff);
    }

    public static void itemTooltipEvent() {
        LodestoneItemEvent.ON_ITEM_TOOLTIP.register(AbstractAugmentItem::addAugmentAttributeTooltip);
    }
}
