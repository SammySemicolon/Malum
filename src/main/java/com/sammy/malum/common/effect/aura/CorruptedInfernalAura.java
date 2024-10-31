package com.sammy.malum.common.effect.aura;

import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.ColorHelper;

public class CorruptedInfernalAura extends MobEffect {
    public CorruptedInfernalAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.INFERNAL_SPIRIT.getPrimaryColor()));
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isOnFire()) {
            entityLivingBaseIn.extinguishFire();
            return true;
        }
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.heal(amplifier + 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
