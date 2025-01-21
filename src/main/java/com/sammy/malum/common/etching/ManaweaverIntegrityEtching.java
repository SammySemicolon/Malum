package com.sammy.malum.common.etching;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;

public class ManaweaverIntegrityEtching extends EtchingEffect {

    public ManaweaverIntegrityEtching() {
        super(MalumEtchingEffectTypeRegistry.MANAWEAVERS_INTEGRITY.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributes(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_CAPACITY, 6, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_INTEGRITY, 0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_RECOVERY_RATE, -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }
}