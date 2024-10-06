package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;

import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import team.lodestar.lodestone.helpers.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.WICKED_SPIRIT;

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
                    final DamageSource damageSource = DamageTypeHelper.create(e.level(), DamageTypeRegistry.VOODOO);
                    if (e.getHealth() > 2.5f && !e.isInvulnerableTo(damageSource)) {
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(WICKED_SPIRIT.getPrimaryColor()));
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
                getNearbyEntities(totemBase, Monster.class).forEach(e -> {
                    ParticleEffectTypeRegistry.MAJOR_HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(WICKED_SPIRIT.getPrimaryColor()));
                    e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1));
                    e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1));
                    e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1));
                });
            }
        };
    }
}