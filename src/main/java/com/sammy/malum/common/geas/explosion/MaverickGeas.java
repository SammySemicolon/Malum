package com.sammy.malum.common.geas.explosion;

import com.google.common.collect.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.level.*;

import java.util.function.*;

public class MaverickGeas extends GeasEffect {

    public MaverickGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get());
    }

    public static void onExplosionKnockback(ExplosionKnockbackEvent event) {
        final LivingEntity sourceEntity = event.getExplosion().getIndirectSourceEntity();
        if (sourceEntity != null) {
            if (GeasEffectHandler.hasGeasEffect(sourceEntity, MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get())) {
                double multiplier = 4f / (event.getKnockbackVelocity().length()*2);
                event.setKnockbackVelocity(event.getKnockbackVelocity().scale(Math.clamp(multiplier, 0, 1.5f)));
                event.getAffectedBlocks().clear();
            }
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("rocket_jumping"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("no_block_breaking"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(modifiers, Attributes.KNOCKBACK_RESISTANCE, -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }
}
