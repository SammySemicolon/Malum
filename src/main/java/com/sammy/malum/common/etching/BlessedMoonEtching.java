package com.sammy.malum.common.etching;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import org.checkerframework.checker.nullness.qual.*;
import team.lodestar.lodestone.registry.common.*;

public class BlessedMoonEtching extends NightCycleBasedEtching {

    public BlessedMoonEtching() {
        super(MalumEtchingEffectTypeRegistry.BLESSED_MOON.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributes(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (isNight) {
            addAttributeModifier(modifiers, AttributeRegistry.SPIRIT_SPOILS, 1, AttributeModifier.Operation.ADD_VALUE);
            addAttributeModifier(modifiers, AttributeRegistry.SCYTHE_PROFICIENCY, 0.2f, AttributeModifier.Operation.ADD_VALUE);
        }
        addAttributeModifier(modifiers, Attributes.ATTACK_DAMAGE, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }
}