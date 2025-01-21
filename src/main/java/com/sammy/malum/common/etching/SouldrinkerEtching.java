package com.sammy.malum.common.etching;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;

public class SouldrinkerEtching extends EtchingEffect {

    public SouldrinkerEtching() {
        super(MalumEtchingEffectTypeRegistry.SOULDRINKER.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributes(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, AttributeRegistry.SPIRIT_SPOILS, 1, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(modifiers, AttributeRegistry.ARCANE_RESONANCE, 0.5f, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}