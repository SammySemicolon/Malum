package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.registry.common.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class SoulwashingGeas extends GeasEffect {

    public WeakHashMap<UUID, LivingEntity> visibleTargets = new WeakHashMap<>();

    public SoulwashingGeas() {
        super(MalumGeasEffectTypeRegistry.SOULWASHING.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("authority_of_wrath"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, LodestoneAttributes.MAGIC_PROFICIENCY, 0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(modifiers, LodestoneAttributes.MAGIC_RESISTANCE, -0.75f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }

    @Override
    public void finalizedIncomingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTagRegistry.SOULWASHING)) {
            return;
        }
        damageTargets(target, null, DamageTypeRegistry.SOULWASHING_RETALIATION, event.getOriginalDamage());
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTagRegistry.SOULWASHING)) {
            return;
        }
        if (!attacker.equals(event.getSource().getEntity())) {
            return;
        }
        var damage = event.getNewDamage() / 2f;
        damageTargets(attacker, target, DamageTypeRegistry.SOULWASHING_PROPAGATION, damage);
    }

    @Override
    public void update(EntityTickEvent event) {
        final Entity geasHolder = event.getEntity();
        final Level level = geasHolder.level();
        if (level.getGameTime() % 40L == 0) {
            visibleTargets.clear();
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, geasHolder.getBoundingBox().inflate(32f, 16f, 32f))) {
                if (entity == geasHolder || entity.isSpectator()) {
                    continue;
                }
                if (geasHolder instanceof Player player) {
                    if (entity.isInvisibleTo(player)) {
                        continue;
                    }
                }
                if (entity instanceof TamableAnimal tamableAnimal) {
                    if (tamableAnimal.isTame()) {
                        continue;
                    }
                }
                if (entity.hasCustomName()) {
                    continue;
                }
                if (!hasLineOfSight(geasHolder, entity)) {
                    continue;
                }
                visibleTargets.put(entity.getUUID(), entity);
            }
        }
    }

    public void damageTargets(Entity wrathBearer, @Nullable Entity excludedTarget, ResourceKey<DamageType> damageType, float damage) {
        var targets = new ArrayList<>(visibleTargets.values());
        for (int i = 0; i < targets.size(); i++) {
            LivingEntity target = targets.get(i);
            if (target.equals(excludedTarget)) {
                continue;
            }
            if (target.isAlive()) {
                WorldEventHandler.addWorldEvent(target.level(),
                        new DelayedDamageWorldEvent()
                                .setMagicDamageType(damageType)
                                .setImpactParticleEffect(ParticleEffectTypeRegistry.SOULWASHING_IMPACT, target.getRandom().nextBoolean() ? SpiritTypeRegistry.WICKED_SPIRIT : SpiritTypeRegistry.ELDRITCH_SPIRIT)
                                .setSound(SoundRegistry.SOULWASHING_IMPACT, 1f, 2f, 0.5f)
                                .setData(wrathBearer.getUUID(), target.getUUID(), 0, damage, 8 + i * 2));
            }
        }
    }

    public boolean hasLineOfSight(Entity wrathBearer, Entity target) {
        Vec3 wrathPosition = new Vec3(wrathBearer.getX(), wrathBearer.getEyeY(), wrathBearer.getZ());
        Vec3 targetPosition = new Vec3(target.getX(), target.getEyeY(), target.getZ());
        var clipResult = wrathBearer.level().clip(new ClipContext(wrathPosition, targetPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, wrathBearer));
        return clipResult.getType().equals(HitResult.Type.MISS);
    }
}