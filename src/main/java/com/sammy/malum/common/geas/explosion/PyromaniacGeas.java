package com.sammy.malum.common.geas.explosion;

import com.google.common.collect.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.function.*;

public class PyromaniacGeas extends GeasEffect {

    protected static final int STREAK_FALLOFF_TIMER = 2400;
    protected static final int STREAK_LIMIT = 10;
    protected int streak;
    protected int cooldown;

    public PyromaniacGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_PYROMANIAC.get());
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        final Explosion explosion = event.getExplosion();
        for (Entity entity : event.getAffectedEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (explosion.damageCalculator.shouldDamageEntity(explosion, livingEntity)) {
                    var geas = GeasEffectHandler.getGeasEffect(livingEntity, MalumGeasEffectTypeRegistry.PACT_OF_THE_PYROMANIAC.get());
                    if (geas != null) {
                        var pyromaniacGeas = (PyromaniacGeas) geas.getValue();
                        if (pyromaniacGeas.streak < STREAK_LIMIT) {
                            pyromaniacGeas.streak++;
                            if (pyromaniacGeas.streak >= 5) {
                                livingEntity.igniteForSeconds(5);
                            }
                            pyromaniacGeas.markDirty();
                        }
                        if (!entity.equals(explosion.getIndirectSourceEntity()) && !entity.equals(explosion.getDirectSourceEntity())) {
                            event.getAffectedBlocks().clear();
                        }
                        livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.MINERS_RAGE, 600, 2));
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (streak == 0) {
            return;
        }
        if (cooldown < STREAK_FALLOFF_TIMER) {
            cooldown++;
            if (cooldown == STREAK_FALLOFF_TIMER) {
                streak--;
                cooldown = 0;
                markDirty();
            }
        }
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setNewDamage(event.getNewDamage() * 1.5f);
        }
        if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            float health = target.getHealth();
            event.setNewDamage(Mth.clamp(event.getNewDamage() * 0.25f, 0, health*0.5f));
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("explosion_lover"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("explosion_fire"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("scary_fire"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (streak > 0) {
            float multiplier = 0.05f * streak;
            if (streak >= 5) {
                multiplier *= 2;
            }
            addAttributeModifier(modifiers, Attributes.MOVEMENT_SPEED, multiplier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }
}
