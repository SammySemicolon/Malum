package com.sammy.malum.common.geas.special;

import com.google.common.collect.Multimap;
import com.sammy.malum.core.systems.geas.GeasEffect;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.MalumGeasEffectTypeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;

public class TheLastCurse extends GeasEffect {

    public TheLastCurse() {
        super(MalumGeasEffectTypeRegistry.THE_LAST_CURSE.get());
    }


    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        return modifiers;
    }
}
