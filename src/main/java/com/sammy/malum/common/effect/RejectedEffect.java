package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
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
        addAttributeModifier(Attributes.MOVEMENT_SPEED, MalumMod.malumPath("rejected"), -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.getAttachedOrCreate(AttachmentTypeRegistry.VOID_INFLUENCE).setAfflictionLevel(40);
        if (pLivingEntity.level().getGameTime() % 60L == 0) {
            if (pLivingEntity instanceof Player player && player.isCreative()) {
                return false;
            }
            pLivingEntity.hurt(DamageTypeHelper.create(pLivingEntity.level(), DamageTypeRegistry.VOODOO_PLAYERLESS), 1);
            return true;
        }
        return false;
    }
}