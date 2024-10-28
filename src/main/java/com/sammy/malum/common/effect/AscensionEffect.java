package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

public class AscensionEffect extends MobEffect {
    public AscensionEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AERIAL_SPIRIT.getPrimaryColor()));
        addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "9c450960-d8f0-45e0-b442-971efe52cccd", -0.10f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = entity.getEffect(MobEffectRegistry.ASCENSION.get());
        if (effectInstance != null) {
            event.setDistance(event.getDistance() / (6 + effectInstance.getAmplifier()));
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return super.getAttributeModifierValue(Math.min(1, amplifier), modifier);
    }
}