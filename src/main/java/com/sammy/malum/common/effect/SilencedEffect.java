package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.compability.irons_spellbooks.*;
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
        addAttributeModifier(LodestoneAttributes.MAGIC_PROFICIENCY, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(AttributeRegistry.SOUL_WARD_INTEGRITY, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_CAPACITY, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_RECOVERY_RATE, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_RECOVERY_MULTIPLIER, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(AttributeRegistry.ARCANE_RESONANCE, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        IronsSpellsCompat.addSilencedNegativeAttributeModifiers(this);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        TouchOfDarknessHandler handler = MalumLivingEntityDataCapability.getCapability(pLivingEntity).touchOfDarknessHandler;
        handler.afflict((int) (20 + pAmplifier * 2.5));
        return true;
    }
}