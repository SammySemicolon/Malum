package com.sammy.malum.core.handlers;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDropsEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.events.LodestoneItemEvent;

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
        var entityData = entity.getAttachedOrCreate(AttachmentTypeRegistry.CACHED_SPIRIT_DROPS);
        item.setAttached(AttachmentTypeRegistry.CACHED_SPIRIT_DROPS, entityData);
        item.setNeverPickUp();
        item.age = 6000 - 20;
        item.setNoGravity(true);
        item.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0.5, 1));
    }

    public static void onItemExpire(ItemEntity item, ItemStack itemStack) {
        var data = item.getAttachedOrCreate(AttachmentTypeRegistry.CACHED_SPIRIT_DROPS);
        if (!data.getSpiritDrops().isEmpty()) {
            LivingEntity spiritOwner = data.getSpiritOwner() != null ? item.level().getPlayerByUUID(data.getSpiritOwner()) : null;
            SoulHarvestHandler.spawnSpirits(item.level(), spiritOwner, item.position().add(0, item.getBbHeight()/2, 0), data.getSpiritDrops());
        }
    }
}