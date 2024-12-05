package com.sammy.malum.core.handlers;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.*;

import static team.lodestar.lodestone.handlers.ItemEventHandler.getEventResponders;

public class SoulWardHandler {

    public static Codec<SoulWardHandler> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.DOUBLE.fieldOf("soulWard").forGetter(sw -> sw.soulWard),
            Codec.DOUBLE.fieldOf("soulWardProgress").forGetter(sw -> sw.soulWardCooldown)
    ).apply(obj, SoulWardHandler::new));

    private double soulWard;
    private double soulWardCooldown;

    private boolean isDirty;


    public SoulWardHandler() {
    }

    public SoulWardHandler(double soulWard, double soulWardCooldown) {
        this.soulWard = soulWard;
        this.soulWardCooldown = soulWardCooldown;
    }

    public static void recoverSoulWard(PlayerTickEvent event) {
        var player = event.getEntity();
        if (!player.level().isClientSide) {
            var handler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
            var capacity = player.getAttribute(AttributeRegistry.SOUL_WARD_CAPACITY);
            if (capacity != null) {
                if (handler.getSoulWard() < capacity.getValue()) {
                    if (handler.getCooldown() <= 0) {
                        handler.recoverSoulWard(player, 1);
                    } else {
                        handler.soulWardCooldown--;
                    }
                }
                if (handler.getSoulWard() > capacity.getValue()) {
                    handler.setSoulWard(capacity.getValue());
                }
            }
            if (handler.isDirty) {
                MalumPlayerDataCapability.syncTrackingAndSelf(player);
                handler.isDirty = false;
            }
        }
    }

    public static void shieldPlayer(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getOriginalDamage() <= 0) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            var handler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
            handler.addCooldown(player, 4f);
            if (!handler.isDepleted()) {
                var source = event.getSource();
                float amount = event.getOriginalDamage();
                double magicDamageAbsorption = CommonConfig.SOUL_WARD_MAGIC.getConfigValue();
                double physicalDamageAbsorption = CommonConfig.SOUL_WARD_PHYSICAL.getConfigValue();
                double integrity = player.getAttributeValue(AttributeRegistry.SOUL_WARD_INTEGRITY);
                var eventResponders = getEventResponders(player);

                var propertiesEvent = new ModifySoulWardPropertiesEvent(player, handler, source, magicDamageAbsorption, physicalDamageAbsorption, integrity);
                eventResponders.forEach(lookup -> lookup.run(IMalumEventResponderItem.class, (eventResponderItem, stack) ->
                        eventResponderItem.modifySoulWardPropertiesEvent(propertiesEvent, player, stack)));
                NeoForge.EVENT_BUS.post(propertiesEvent);
                magicDamageAbsorption = propertiesEvent.getNewMagicDamageAbsorption();
                physicalDamageAbsorption = propertiesEvent.getNewPhysicalDamageAbsorption();
                integrity = propertiesEvent.getNewIntegrity();

                double damageMultiplier = source.is(LodestoneDamageTypeTags.IS_MAGIC) ? magicDamageAbsorption : physicalDamageAbsorption;
                double absorbedDamage = amount * damageMultiplier;
                double soulWardDamage = (amount - absorbedDamage) / Math.max(integrity, 0.01f);
                handler.reduceSoulWard(soulWardDamage);

                var damageEvent = new SoulWardDamageEvent(player, handler, source, absorbedDamage, soulWardDamage);
                eventResponders.forEach(lookup -> lookup.run(IMalumEventResponderItem.class, (eventResponderItem, stack) ->
                        eventResponderItem.soulWardDamageEvent(damageEvent, player, stack)));
                NeoForge.EVENT_BUS.post(damageEvent);

                var sound = handler.getSoulWard() == 0 ? SoundRegistry.SOUL_WARD_DEPLETE : SoundRegistry.SOUL_WARD_HIT;
                SoundHelper.playSound(player, sound.get(), 1, Mth.nextFloat(player.getRandom(), 1f, 1.5f));
                event.setNewDamage((float) absorbedDamage);
            }
        }
    }


    public void recoverSoulWard(Player player, double amount) {
        var multiplier = Optional.ofNullable(player.getAttribute(AttributeRegistry.SOUL_WARD_RECOVERY_MULTIPLIER)).map(AttributeInstance::getValue).orElse(1.0);
        addSoulWard(amount * multiplier);
        if (!player.isCreative()) {
            var capacity = player.getAttribute(AttributeRegistry.SOUL_WARD_CAPACITY);
            if (capacity != null) {
                var sound = soulWard >= capacity.getValue() ? SoundRegistry.SOUL_WARD_CHARGE : SoundRegistry.SOUL_WARD_GROW;
                double pitchOffset = (soulWard / capacity.getValue()) * 0.5f + (Mth.ceil(soulWard) % 3) * 0.25f;
                SoundHelper.playSound(player, sound.get(), 0.25f, (float) (1f + pitchOffset));
            }
        }
        addCooldown(player, 1);
    }

    public void addSoulWard(double added) {
        setSoulWard(soulWard + added);
    }

    public void reduceSoulWard(double removed) {
        setSoulWard(soulWard - removed);
    }

    public void setSoulWard(double soulWard) {
        this.soulWard = Math.max(soulWard, 0);
        isDirty = true;
    }

    public void addCooldown(Player player, double multiplier) {
        final double newCooldown = getSoulWardCooldown(player) * multiplier;
        if (soulWardCooldown < newCooldown) {
            soulWardCooldown = newCooldown;
        }
    }

    public boolean isDepleted() {
        return soulWard == 0;
    }

    public double getSoulWard() {
        return soulWard;
    }

    public double getCooldown() {
        return soulWardCooldown;
    }

    public static float getSoulWardCooldown(Player player) {
        return getSoulWardCooldown(player.getAttributeValue(AttributeRegistry.SOUL_WARD_RECOVERY_RATE));
    }

    public static float getSoulWardCooldown(double recoverySpeed) {
        return Mth.floor(CommonConfig.SOUL_WARD_RATE.getConfigValue() / recoverySpeed);
    }
}