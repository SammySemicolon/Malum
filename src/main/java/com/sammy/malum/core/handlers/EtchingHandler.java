package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.handlers.*;

import java.util.*;

public class EtchingHandler {
    public static final ItemEventHandler.EventResponderSource ETCHINGS = new ItemEventHandler.EventResponderSource(MalumMod.malumPath("etchings"), EtchingHandler::getEtchingStacks, EtchingHandler::getEtching);

    public static void init() {
        ItemEventHandler.registerLookup(ETCHINGS);
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            getEtchingEffects(living).forEach(e -> {
                e.updateDirty(living);
                e.update(event);
            });
        }
    }

    public static void addEtching(LivingEntity entity, ItemStack stack) {
        entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).addEtching(stack);
    }

    public static List<ItemStack> getEtchingStacks(LivingEntity entity) {
        return entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).getEtchings();
    }

    public static Collection<EtchingEffect> getEtchingEffects(LivingEntity entity) {
        return getEtchings(entity).values();
    }

    public static Map<ItemStack, EtchingEffect> getEtchings(LivingEntity entity) {
        return entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).getEtchingEffects(entity);
    }

    public static EtchingEffect getEtching(LivingEntity entity, ItemStack stack) {
        return getEtchings(entity).get(stack);
    }
}