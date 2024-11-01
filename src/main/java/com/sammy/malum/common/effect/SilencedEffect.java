package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;

public class SilencedEffect extends MobEffect {
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, ColorHelper.getColor(20, 14, 22));
        var id = MalumMod.malumPath("silenced");
        addAttributeModifier(LodestoneAttributes.MAGIC_DAMAGE, id, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(LodestoneAttributes.MAGIC_PROFICIENCY, id, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(LodestoneAttributes.MAGIC_RESISTANCE, id, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(AttributeRegistry.SOUL_WARD_INTEGRITY, id, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_CAPACITY, id, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_RECOVERY_RATE, id, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(AttributeRegistry.ARCANE_RESONANCE, id, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        TouchOfDarknessHandler handler = MalumLivingEntityDataCapability.getCapability(pLivingEntity).touchOfDarknessHandler;
        handler.afflict(20);
        return true;
    }
}