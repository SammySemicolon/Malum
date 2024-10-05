package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import team.lodestar.lodestone.helpers.ColorHelper;

public class WickedIntentEffect extends MobEffect {
    public WickedIntentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY.get(), "0cd21cec-758c-456b-9955-06713e732303", 4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}