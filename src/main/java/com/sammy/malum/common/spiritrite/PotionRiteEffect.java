package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.rite.generic.MajorEntityEffectParticlePacket;
import com.sammy.malum.registry.common.PacketRegistry;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;

import java.util.function.Predicate;

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
                PacketRegistry.sendToPlayersTrackingEntityAndSelf(e, new MajorEntityEffectParticlePacket(totemBase.activeRite.getIdentifyingSpirit().getPrimaryColor(), e.getX(), e.getY()+ e.getBbHeight() / 2f, e.getZ()));
            }
            e.addEffect(instance);
        });
    }

    public Predicate<LivingEntity> getEntityPredicate() {
        return e -> !(e instanceof Monster);
    }
}