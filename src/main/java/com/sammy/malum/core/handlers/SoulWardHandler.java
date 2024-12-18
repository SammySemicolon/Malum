package com.sammy.malum.core.handlers;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.tick.EntityTickEvent;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;

import static team.lodestar.lodestone.handlers.ItemEventHandler.*;

public class SoulWardHandler {

    public static void recoverSoulWard(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            var level = living.level();
            if (!level.isClientSide) {
                var data = living.getAttachedOrCreate(AttachmentTypeRegistry.SOUL_WARD);
                var capacity = living.getAttribute(AttributeRegistry.SOUL_WARD_CAPACITY);
                if (capacity != null) {
                    if (data.getSoulWard() < capacity.getValue()) {
                        data.tickCooldown();
                        if (data.getCooldown() <= 0) {
                            data.recoverSoulWard(living, 1);
                        }
                    }
                    if (data.getSoulWard() > capacity.getValue()) {
                        data.setSoulWard(capacity.getValue());
                    }
                }
                if (data.isDirty()) {
                    PacketRegistry.sendToPlayersTrackingEntityAndSelf(living, new SyncSoulWardDataPayload(living.getId(), data));
                    data.setDirty(false);
                }
            }
        }
    }

    public static void shieldPlayer(LivingDamageEvent event) {
        var living = event.getEntity();
        if (living.level().isClientSide()) {
            return;
        }
        if (event.getAmount() <= 0) {
            return;
        }

        var data = living.getAttachedOrCreate(AttachmentTypeRegistry.SOUL_WARD);
        data.addCooldown(living, 4f);
        if (!data.isDepleted()) {
            var source = event.getSource();
            float amount = event.getAmount();
            double magicDamageAbsorption = CommonConfig.SOUL_WARD_MAGIC.getConfigValue();
            double physicalDamageAbsorption = CommonConfig.SOUL_WARD_PHYSICAL.getConfigValue();
            double integrity = living.getAttributeValue(AttributeRegistry.SOUL_WARD_INTEGRITY)*2;
            var eventResponders = getEventResponders(living);
            var propertiesEvent = new ModifySoulWardPropertiesEvent(living, data, source, magicDamageAbsorption, physicalDamageAbsorption, integrity);
            eventResponders.forEach(lookup -> lookup.run(IMalumEventResponderItem.class, (eventResponderItem, stack) ->
                    eventResponderItem.modifySoulWardPropertiesEvent(propertiesEvent, living, stack)));
            propertiesEvent.sendEvent();
            magicDamageAbsorption = propertiesEvent.getNewMagicDamageAbsorption();
            physicalDamageAbsorption = propertiesEvent.getNewPhysicalDamageAbsorption();
            integrity = propertiesEvent.getNewIntegrity();

            double damageMultiplier = source.is(LodestoneDamageTypeTags.IS_MAGIC) ? magicDamageAbsorption : physicalDamageAbsorption;
            double absorbedDamage = amount * damageMultiplier;
            double soulWardDamage = (amount - absorbedDamage) / Math.max(integrity, 0.01f);
            data.reduceSoulWard(soulWardDamage);

            var damageEvent = new SoulWardDamageEvent(living, data, source, absorbedDamage, soulWardDamage);
            eventResponders.forEach(lookup -> lookup.run(IMalumEventResponderItem.class, (eventResponderItem, stack) ->
                    eventResponderItem.soulWardDamageEvent(damageEvent, living, stack)));
            damageEvent.sendEvent();

            var sound = data.getSoulWard() == 0 ? SoundRegistry.SOUL_WARD_DEPLETE : SoundRegistry.SOUL_WARD_HIT;
            SoundHelper.playSound(living, sound.get(), 1, Mth.nextFloat(living.getRandom(), 1f, 1.5f));
            event.setAmount((float) absorbedDamage);
        }
    }
}