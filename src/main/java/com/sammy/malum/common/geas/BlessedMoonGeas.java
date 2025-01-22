package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;

public class BlessedMoonGeas extends NightCycleBasedGeas {

    public BlessedMoonGeas() {
        super(MalumGeasEffectTypeRegistry.BLESSED_MOON.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers, boolean isNight) {
        if (isNight) {
            addAttributeModifier(modifiers, AttributeRegistry.SPIRIT_SPOILS, 1, AttributeModifier.Operation.ADD_VALUE);
            addAttributeModifier(modifiers, AttributeRegistry.SCYTHE_PROFICIENCY, 0.2f, AttributeModifier.Operation.ADD_VALUE);
            return modifiers;
        }
        addAttributeModifier(modifiers, Attributes.ATTACK_DAMAGE, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }
}