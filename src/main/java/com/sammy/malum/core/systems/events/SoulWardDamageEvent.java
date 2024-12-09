package com.sammy.malum.core.systems.events;

import com.sammy.malum.common.capabilities.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;

public class SoulWardDamageEvent extends SoulWardEvent {

    private final double absorbedDamage;
    private final double soulWardLost;

    public SoulWardDamageEvent(LivingEntity entity, SoulWardData soulWardData, DamageSource source, double absorbedDamage, double soulWardLost) {
        super(entity, soulWardData, source);
        this.absorbedDamage = absorbedDamage;
        this.soulWardLost = soulWardLost;
    }

    public double getAbsorbedDamage() {
        return absorbedDamage;
    }

    public double getSoulWardLost() {
        return soulWardLost;
    }
}