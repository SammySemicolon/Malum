package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Predicate;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.SACRED_SPIRIT;

public class PotionRiteEffect extends TotemicRiteEffect {

    public final Class<? extends LivingEntity> targetClass;
    public final DeferredHolder<MobEffect, MobEffect> mobEffectHolder;

    public PotionRiteEffect(Class<? extends LivingEntity> targetClass, DeferredHolder<MobEffect, MobEffect> mobEffectSupplier) {
        super(MalumRiteEffectCategory.AURA);
        this.targetClass = targetClass;
        this.mobEffectHolder = mobEffectSupplier;
    }

    @Override
    public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
        getNearbyEntities(totemBase, targetClass).filter(getEntityPredicate()).forEach(e -> {
            MobEffectInstance instance = new MobEffectInstance(mobEffectHolder, 1200, 1, true, true);
            if (!e.hasEffect(instance.getEffect())) {
                ParticleEffectTypeRegistry.RITE_EFFECT_TRIGGERED.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT));
            }
            e.addEffect(instance);
        });
    }

    public Predicate<LivingEntity> getEntityPredicate() {
        return e -> !(e instanceof Monster);
    }
}