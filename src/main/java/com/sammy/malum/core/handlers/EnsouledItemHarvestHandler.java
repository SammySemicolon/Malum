package com.sammy.malum.core.handlers;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import static team.lodestar.lodestone.helpers.RandomHelper.randomBetween;

public class EnsouledItemHarvestHandler {

    public static void onDrops(LivingDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }
        var entity = event.getEntity();
        EntitySpiritDropData.getSpiritData(entity).map(d -> d.itemAsSoul).ifPresent(itemAsSoul -> {
            for (ItemEntity item : event.getDrops()) {
                if (itemAsSoul.test(item.getItem())) {
                    moveSpiritDropsOntoItem(item, entity);
                }
            }
        });
    }

    public static void moveSpiritDropsOntoItem(ItemEntity item, LivingEntity entity) {
        var entityData = entity.getData(AttachmentTypeRegistry.CACHED_SPIRIT_DROPS);
        item.setData(AttachmentTypeRegistry.CACHED_SPIRIT_DROPS, entityData);
        item.setNeverPickUp();
        item.age = item.lifespan - 20;
        item.setNoGravity(true);
        item.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0.5, 1));
    }

    public static void onItemExpire(ItemExpireEvent event) {
        var item = event.getEntity();
        var data = item.getData(AttachmentTypeRegistry.CACHED_SPIRIT_DROPS);
        if (!data.getSpiritDrops().isEmpty()) {
            LivingEntity spiritOwner = data.getSpiritOwner() != null ? item.level().getPlayerByUUID(data.getSpiritOwner()) : null;
            SoulHarvestHandler.spawnSpirits(item.level(), spiritOwner, item.position().add(0, item.getBbHeight()/2, 0), data.getSpiritDrops());
        }
    }
}