package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.compability.irons_spellbooks.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;

public class SilencedEffect extends MobEffect {
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, ColorHelper.getColor(20, 14, 22));
        float ratio = -0.05f;
        addAttributeModifier(LodestoneAttributes.MAGIC_PROFICIENCY, MalumMod.malumPath("silenced"), ratio, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(AttributeRegistry.SOUL_WARD_INTEGRITY, MalumMod.malumPath("silenced"), ratio, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_CAPACITY, MalumMod.malumPath("silenced"), ratio, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_RECOVERY_RATE, MalumMod.malumPath("silenced"), ratio, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_RECOVERY_MULTIPLIER, MalumMod.malumPath("silenced"), ratio, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(AttributeRegistry.ARCANE_RESONANCE, MalumMod.malumPath("silenced"), ratio/2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        IronsSpellsCompat.addSilencedNegativeAttributeModifiers(this);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.getData(AttachmentTypeRegistry.TOUCH_OF_DARKNESS).setAfflictionLevel(10 + pAmplifier * 4);
        return true;
    }
}