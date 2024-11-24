package com.sammy.malum.core.handlers;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class MalumAttributeEventHandler {
    public static void processAttributes(LivingDamageEvent.Pre event) {
        if (event.getOriginalDamage() <= 0) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity attacker) {
            var stack = SoulDataHandler.getScytheWeapon(source, attacker);
            if (stack.isEmpty()) {
                return;
            }
            if (source.is(DamageTypes.THORNS)) {
                return;
            }
            if (!event.getSource().is(DamageTypeTagRegistry.IS_SCYTHE)) {
                var scytheProficiency = attacker.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY);
                if (scytheProficiency != null) {
                    event.setNewDamage((float) (event.getNewDamage() * scytheProficiency.getValue()));
                }
            }
        }
    }
}