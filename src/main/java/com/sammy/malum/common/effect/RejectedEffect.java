package com.sammy.malum.common.effect;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.helpers.*;

public class RejectedEffect extends MobEffect {
    public RejectedEffect() {
        super(MobEffectCategory.NEUTRAL, ColorHelper.getColor(20, 14, 22));
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "248da214-2292-4637-a040-5597fb65db58", -0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        TouchOfDarknessHandler handler = MalumLivingEntityDataCapability.getCapability(pLivingEntity).touchOfDarknessHandler;
        handler.afflict(40);
        if (pLivingEntity.level().getGameTime() % 60L == 0) {
            if (pLivingEntity instanceof Player player && player.isCreative()) {
                return;
            }
            pLivingEntity.hurt(DamageTypeHelper.create(pLivingEntity.level(), DamageTypeRegistry.VOODOO_PLAYERLESS), 1);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}