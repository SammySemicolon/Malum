package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

public class SkyBreakerGeas extends GeasEffect {

    public SkyBreakerGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_SKYBREAKER.get());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, Attributes.ATTACK_KNOCKBACK, -0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof Player player) {
            if (event.getSource().is(DamageTypes.FALL)) {
                if (target.isAlive()) {
                    player.attackStrengthTicker = 1000;
                    player.swing(InteractionHand.MAIN_HAND, true);
                    target.invulnerableTime = 0;
                    player.attack(target);
                }
            }
        }
    }
}