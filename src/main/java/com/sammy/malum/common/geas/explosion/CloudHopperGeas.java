package com.sammy.malum.common.geas.explosion;

import com.google.common.collect.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.network.*;

import java.util.*;
import java.util.function.*;

public class CloudHopperGeas extends GeasEffect {

    protected static final int FALLOFF_DURATION = 120;
    protected static final int STAMINA_FALLOFF_START = 5;
    protected int cooldown;
    public int streak;

    public CloudHopperGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_CLOUDHOPPER.get());
    }

    public static void onExplosionKnockback(ExplosionKnockbackEvent event) {
        final Explosion explosion = event.getExplosion();
        var sourceEntity = explosion.getIndirectSourceEntity();

        List<LivingEntity> entities = new ArrayList<>();
        if (event.getAffectedEntity() instanceof LivingEntity livingEntity) {
            entities.add(livingEntity);
        }
        if (sourceEntity != null) {
            entities.add(sourceEntity);
        }
        for (LivingEntity entity : entities) {
            if (!explosion.damageCalculator.shouldDamageEntity(explosion, entity)) {
                var instance = getInstance(entity);
                if (instance instanceof CloudHopperGeas cloudHopper) {
                    double multiplier = 6f / (event.getKnockbackVelocity().length() * 2);
                    final double scalar = Math.clamp(multiplier, 1, 1.75f);
                    double horizontalScalar = scalar * 1.5f;
                    event.setKnockbackVelocity(event.getKnockbackVelocity().multiply(horizontalScalar, scalar, horizontalScalar));
                    if (event.getAffectedEntity().equals(entity)) {
                        entity.addEffect(new MobEffectInstance(MobEffectRegistry.ASCENSION, 100, 1));
                    }
                    cloudHopper.streak++;
                    cloudHopper.sync(entity);
                }
            }
        }
    }

    public static boolean canModifyExplosion(LivingEntity explosionOwner) {
        return explosionOwner != null && getInstance(explosionOwner) != null;
    }

    public static GeasEffect getInstance(LivingEntity entity) {
        var effect = GeasEffectHandler.getGeasEffect(entity, MalumGeasEffectTypeRegistry.PACT_OF_THE_CLOUDHOPPER.get());
        if (effect != null) {
            return effect.getValue();
        }
        return null;
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (streak == 0) {
            return;
        }
        if (cooldown < FALLOFF_DURATION) {
            cooldown++;
            if (cooldown == FALLOFF_DURATION) {
                streak = Math.max(streak - 2, 0);
                cooldown = 0;
                sync(entity);
            }
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("rocket_jumping"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("wind_charge_exhaustion"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (streak >= STAMINA_FALLOFF_START) {
            float modifier = 0.15f * (streak - STAMINA_FALLOFF_START);
            addAttributeModifier(modifiers, Attributes.MOVEMENT_SPEED, -modifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            addAttributeModifier(modifiers, Attributes.JUMP_STRENGTH, -modifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            addAttributeModifier(modifiers, Attributes.GRAVITY, modifier / 2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }

    public void sync(LivingEntity entity) {
        markDirty();
        if (!entity.level().isClientSide()) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncCloudHopperStreakPayload(entity.getId(), streak));
        }
    }
}