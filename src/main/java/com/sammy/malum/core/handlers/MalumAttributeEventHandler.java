package com.sammy.malum.core.handlers;

import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

public class MalumAttributeEventHandler {
    public static void processAttributes(LivingDamageEvent event) {
        if (event.getAmount() <= 0) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity attacker) {
            if (source.is(DamageTypeTagRegistry.IS_SCYTHE)) {
                var scytheProficiency = attacker.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY);
                if (scytheProficiency != null) {
                    event.setAmount((float) (event.getAmount() * scytheProficiency.getValue()));
                }
            }
        }
    }
}