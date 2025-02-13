package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;

import com.sammy.malum.core.systems.rite.TotemicRiteEffect;
import com.sammy.malum.core.systems.rite.TotemicRiteType;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import team.lodestar.lodestone.helpers.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class WickedRiteType extends TotemicRiteType {
    public WickedRiteType() {
        super("wicked_rite", ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyEntities(totemBase, LivingEntity.class, e -> !(e instanceof Player)).forEach(e -> {
                    final DamageSource damageSource = DamageTypeHelper.create(e.level(), DamageTypeRegistry.VOODOO_PLAYERLESS);
                    if (e.getHealth() > 2.5f && !e.isInvulnerableTo(damageSource)) {
                        ParticleEffectTypeRegistry.ENTITY_RITE_EFFECT.createEntityEffect(e, new ColorEffectData(WICKED_SPIRIT));
                        e.hurt(damageSource, 2);
                    }
                });
            }
        };

    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyEntities(totemBase, LivingEntity.class, e -> !(e instanceof Player)).forEach(e -> {
                    final boolean hadNoEffect = !e.hasEffect(MobEffects.DAMAGE_BOOST);
                    final boolean success = e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
                    if (hadNoEffect && success) {
                        ParticleEffectTypeRegistry.ENTITY_RITE_EFFECT.createEntityEffect(e, new ColorEffectData(WICKED_SPIRIT));
                    }
                    e.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1200, 1));
                    e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1));
                });
            }
        };
    }
}