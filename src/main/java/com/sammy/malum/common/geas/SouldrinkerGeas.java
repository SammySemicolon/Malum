package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.registry.common.*;

public class SouldrinkerGeas extends GeasEffect {

    public SouldrinkerGeas() {
        super(MalumGeasEffectTypeRegistry.SOULDRINKERS_ECSTASY.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, AttributeRegistry.SPIRIT_SPOILS, 1, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(modifiers, AttributeRegistry.ARCANE_RESONANCE, 0.5f, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(modifiers, LodestoneAttributes.MAGIC_RESISTANCE, -0.25f, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}