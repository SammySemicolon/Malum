package com.sammy.malum.common.geas.explosion;

import com.google.common.collect.*;
import com.sammy.malum.common.packets.*;
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
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.network.*;

import java.util.*;
import java.util.function.*;

public class CloudHopperGeas extends GeasEffect {

    protected static final int FALLOFF_DURATION = 60;
    protected static final int STAMINA_FALLOFF_START = 10;
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
                    final double scalar = 1.25f;
                    double horizontalScalar = 2f;
                    final Vec3 knockbackVelocity = event.getKnockbackVelocity();
                    if (knockbackVelocity.y < 0.5f) {
                        event.setKnockbackVelocity(new Vec3(knockbackVelocity.x, 0.5f, knockbackVelocity.z));
                    }
                    event.setKnockbackVelocity(knockbackVelocity.multiply(horizontalScalar, scalar, horizontalScalar));
                    if (event.getAffectedEntity().equals(entity)) {
                        entity.addEffect(new MobEffectInstance(MobEffectRegistry.ASCENSION, 100, 1));
                    }
                    cloudHopper.streak++;
                    cloudHopper.sync(entity);
                }
            }
        }
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
            return;
        }
        streak = Math.max(Mth.floor(streak / 2f), 0);
        cooldown = 0;
        sync(entity);
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTags.IS_FALL)) {
            event.setNewDamage(event.getNewDamage() * 1.5f);
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("rocket_jumping"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("wind_charge_exhaustion"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("weak_legs"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (streak >= STAMINA_FALLOFF_START) {
            float modifier = 0.04f * (streak - STAMINA_FALLOFF_START);
            addAttributeModifier(modifiers, Attributes.GRAVITY, modifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
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