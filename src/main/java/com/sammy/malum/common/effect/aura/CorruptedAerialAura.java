package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import team.lodestar.lodestone.helpers.ColorHelper;

public class CorruptedAerialAura extends MobEffect {
    public CorruptedAerialAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AERIAL_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.GRAVITY, MalumMod.malumPath("corrupted_aerial_aura"), -0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = entity.getEffect(MobEffectRegistry.AETHERS_CHARM);
        if (effectInstance != null) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1f + effectInstance.getAmplifier() * 0.05f, 0));
        }
    }

    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = entity.getEffect(MobEffectRegistry.AETHERS_CHARM);
        if (effectInstance != null) {
            event.setDistance(event.getDistance() / (6 + effectInstance.getAmplifier()));
        }
    }
}