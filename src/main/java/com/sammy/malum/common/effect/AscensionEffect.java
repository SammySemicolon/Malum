package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

public class AscensionEffect extends MobEffect {
    public AscensionEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AERIAL_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.GRAVITY, MalumMod.malumPath("ascension_lower_gravity"), -0.10f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = entity.getEffect(MobEffectRegistry.ASCENSION);
        if (effectInstance != null) {
            event.setDistance(event.getDistance() / (6 + effectInstance.getAmplifier()));
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

}