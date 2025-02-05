package com.sammy.malum.core.handlers;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.*;

public class MalumAttributeEventHandler {
    public static void processAttributes(LivingDamageEvent.Pre event) {
        if (event.getNewDamage() <= 0) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity attacker) {
            if (source.is(DamageTypeTagRegistry.IS_SCYTHE)) {
                var scytheProficiency = attacker.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY);
                if (scytheProficiency != null) {
                    event.setNewDamage((float) (event.getNewDamage() * scytheProficiency.getValue()));
                }
            }
        }
    }
    public static void heal(LivingHealEvent event) {
        if (event.getAmount() <= 0) {
            return;
        }
        final LivingEntity entity = event.getEntity();
        if (!entity.getAttributes().hasAttribute(AttributeRegistry.HEALING_MULTIPLIER)) {
            return;
        }
        float multiplier = (float) entity.getAttributeValue(AttributeRegistry.HEALING_MULTIPLIER);
        event.setAmount(event.getAmount() * multiplier);
    }
}