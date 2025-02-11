package com.sammy.malum.common.geas.oath;

import com.google.common.collect.*;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class LastStandOath extends GeasEffect {

    public static final int DEATH_DELAY = 1200;

    public int deaths;
    public long scheduledDeath;

    public LastStandOath() {
        super(MalumGeasEffectTypeRegistry.OATH_OF_THE_LAST_STAND.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("last_stand"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("last_stand_patient_death"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, Attributes.ARMOR, -0.75f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        final Level level = entity.level();

        final long time = level.getGameTime();
        if (deaths > 0) {
            long interval = 20 - 2 * Math.min(deaths-1, 5) - Mth.clamp(deaths-6, 0, 5);
            if (time % (interval*5) == 0) {
                SoundHelper.playSound(entity, SoundRegistry.UNCANNY_VALLEY.get(), SoundSource.MASTER, 2f, Mth.nextFloat(level.getRandom(), 0.55f, 1.75f));
            }
            if (time % interval == 0) {
                SoundHelper.playSound(entity, SoundRegistry.VOID_HEARTBEAT.get(), SoundSource.MASTER, 2.5f, Mth.nextFloat(level.getRandom(), 0.95f, 1.15f));
            }
            if (time >= scheduledDeath) {
                entity.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOID), 1000f);
                deaths = 0;
                scheduledDeath = 0;
            }
        }
    }

    @Override
    public void incomingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        if (source.is(Tags.DamageTypes.IS_TECHNICAL)) {
            return;
        }
        if (source.is(DamageTypeRegistry.VOID)) {
            return;
        }
        event.setCanceled(true);
        target.setHealth(1);
        var effect = target.getEffect(MobEffectRegistry.SILENCED);
        if (effect == null) {
            target.addEffect(new MobEffectInstance(MobEffectRegistry.SILENCED, DEATH_DELAY, 4, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, target, 5, 19);
            EntityHelper.extendEffect(effect, target, DEATH_DELAY);
        }
        target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, DEATH_DELAY, 4, true, true, true));
        deaths++;
        if (deaths == getLuckyNumber(target)) {
            deaths = 0;
            scheduledDeath = 0;
            return;
        }
        long newDeathTime = target.level().getGameTime() + DEATH_DELAY;
        if (scheduledDeath != 0) {
            scheduledDeath = (long) Mth.lerp(0.3f, scheduledDeath, newDeathTime);
            return;
        }
        scheduledDeath = newDeathTime;
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeRegistry.VOID)) {
            final float damage = event.getNewDamage() * target.getMaxHealth() * 1000f;
            event.setNewDamage(damage);
        }
    }
    public int getLuckyNumber(LivingEntity entity) {
        int hash = entity.getUUID().hashCode();
        return 4 + Math.floorMod(hash, 9);
    }
}