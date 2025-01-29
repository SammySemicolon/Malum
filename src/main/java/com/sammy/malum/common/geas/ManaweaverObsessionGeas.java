package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.registry.common.*;

public class ManaweaverObsessionGeas extends GeasEffect {

    public ManaweaverObsessionGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_SHIELD.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_CAPACITY, 6, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_RECOVERY_RATE, 0.8f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_INTEGRITY, -0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(modifiers, LodestoneAttributes.MAGIC_PROFICIENCY, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }
}
