package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Predicate;

public class PotionRiteEffect extends TotemicRiteEffect {

    public final MalumSpiritType definingSpirit;
    public final Class<? extends LivingEntity> targetClass;
    public final DeferredHolder<MobEffect, MobEffect> mobEffectHolder;

    public PotionRiteEffect(MalumSpiritType definingSpirit, Class<? extends LivingEntity> targetClass, DeferredHolder<MobEffect, MobEffect> mobEffectSupplier) {
        super(MalumRiteEffectCategory.AURA);
        this.definingSpirit = definingSpirit;
        this.targetClass = targetClass;
        this.mobEffectHolder = mobEffectSupplier;
    }

    @Override
    public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
        getNearbyEntities(totemBase, targetClass).filter(getEntityPredicate()).forEach(e -> {
            MobEffectInstance instance = new MobEffectInstance(mobEffectHolder, 1200, 1, true, true);
            if (!e.hasEffect(instance.getEffect()) && e.addEffect(instance)) {
                ParticleEffectTypeRegistry.ENTITY_RITE_EFFECT.createEntityEffect(e, new ColorEffectData(definingSpirit));
            }
        });
    }

    public Predicate<LivingEntity> getEntityPredicate() {
        return e -> !(e instanceof Monster);
    }
}