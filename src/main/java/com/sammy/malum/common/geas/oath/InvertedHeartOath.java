package com.sammy.malum.common.geas.oath;

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

public class InvertedHeartOath extends GeasEffect {

    public WeakHashMap<UUID, LivingEntity> visibleTargets = new WeakHashMap<>();

    public InvertedHeartOath() {
        super(MalumGeasEffectTypeRegistry.OATH_OF_THE_INVERTED_HEART.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("authority_of_wrath"));
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("authority_of_wrath_arcane_resonance"));
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
        if (event.getSource().is(DamageTypeTagRegistry.IS_SOULWASHING)) {
            return;
        }
        damageTargets(target, null, DamageTypeRegistry.SOULWASHING_RETALIATION, event.getOriginalDamage());
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTagRegistry.SOULWASHING_BLACKLIST)) {
            return;
        }
        if (!attacker.equals(event.getSource().getEntity())) {
            return;
        }
        var damage = event.getNewDamage() / 2f;
        damageTargets(attacker, target, DamageTypeRegistry.SOULWASHING_PROPAGATION, damage);
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        var geasHolder = event.getEntity();
        var level = geasHolder.level();
        if (level.getGameTime() % 40L == 0) {
            visibleTargets.clear();
            double influence = 8f;
            influence *= entity.getAttributeValue(AttributeRegistry.ARCANE_RESONANCE);
            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, geasHolder.getBoundingBox().inflate(influence*2, influence, influence*2))) {
                if (target == geasHolder || target.isSpectator()) {
                    continue;
                }
                if (target instanceof Player player) {
                    if (geasHolder.isInvisibleTo(player)) {
                        continue;
                    }
                }
                if (target.getTeam() != null && target.getTeam().isAlliedTo(geasHolder.getTeam())) {
                    if (!target.getTeam().isAllowFriendlyFire()) {
                        continue;
                    }
                }
                if (target instanceof TamableAnimal tamableAnimal) {
                    if (tamableAnimal.isTame()) {
                        continue;
                    }
                }
                if (target.hasCustomName()) {
                    continue;
                }
                if (!hasLineOfSight(geasHolder, target)) {
                    continue;
                }
                visibleTargets.put(target.getUUID(), target);
            }
        }
    }

    public void damageTargets(LivingEntity wrathBearer, @Nullable LivingEntity excludedTarget, ResourceKey<DamageType> damageType, float damage) {
        var targets = new ArrayList<>(visibleTargets.values());
        Collections.shuffle(targets);

        for (int i = 0; i < targets.size(); i++) {
            LivingEntity target = targets.get(i);
            if (target.equals(excludedTarget)) {
                continue;
            }
            if (target.isAlive()) {
                WorldEventHandler.addWorldEvent(target.level(),
                        new DelayedDamageWorldEvent(target)
                                .setMagicDamageType(damageType)
                                .setImpactParticleEffect(ParticleEffectTypeRegistry.SOULWASHING_IMPACT, target.getRandom().nextBoolean() ? SpiritTypeRegistry.WICKED_SPIRIT : SpiritTypeRegistry.ELDRITCH_SPIRIT)
                                .setSound(SoundRegistry.SOULWASHING_IMPACT, 1f, 2f, 0.5f)
                                .setAttacker(wrathBearer)
                                .setDamageData(0, damage, 8 + i * 2));
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