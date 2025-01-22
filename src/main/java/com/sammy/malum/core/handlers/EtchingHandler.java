package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.handlers.*;

import java.util.*;

public class EtchingHandler {
    public static final ItemEventHandler.EventResponderSource ETCHINGS = new ItemEventHandler.EventResponderSource(MalumMod.malumPath("etchings"), EtchingHandler::getGeasItemStacks, EtchingHandler::getEquippedGeasEffects);

    public static void init() {
        ItemEventHandler.registerLookup(ETCHINGS);
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            final Collection<GeasEffect> geasEffects = getGeasEffects(living).values();
            geasEffects.forEach(e -> {
                e.updateDirty(living);
                e.update(event);
            });
        }
    }

    public static void addGeasEffect(LivingEntity entity, ItemStack stack) {
        entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).addGeasEffect(stack);
    }

    public static List<ItemStack> getGeasItemStacks(LivingEntity entity) {
        return entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).getGeasItems();
    }

    public static boolean hasGeasEffect(LivingEntity entity, GeasEffectType type) {
        return entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).hasGeasEffect(entity, type);
    }

    public static Map<ItemStack, GeasEffect> getGeasEffects(LivingEntity entity) {
        return entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).getGeasEffects(entity);
    }
    public static Map.Entry<ItemStack, GeasEffect> getGeasEffect(LivingEntity entity, GeasEffectType type) {
        return entity.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).getGeasEffect(entity, type);
    }

    public static GeasEffect getEquippedGeasEffects(LivingEntity entity, ItemStack stack) {
        return getGeasEffects(entity).get(stack);
    }

    public static GeasEffect getStoredGeasEffect(ItemStack stack) {
        if (!stack.has(DataComponentRegistry.GEAS_EFFECT)) {
            throw new IllegalArgumentException("Stack does not have an etching effect");
        }
        return stack.get(DataComponentRegistry.GEAS_EFFECT).effectInstance();
    }
}