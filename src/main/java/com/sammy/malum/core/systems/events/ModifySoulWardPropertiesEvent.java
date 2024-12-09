package com.sammy.malum.core.systems.events;

import com.sammy.malum.common.capabilities.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;

public class ModifySoulWardPropertiesEvent extends SoulWardEvent {

    private final double physicalDamageAbsorption;
    private final double magicDamageAbsorption;
    private final double soulWardIntegrity;

    private double newPhysicalDamageAbsorption;
    private double newMagicDamageAbsorption;
    private double newSoulWardIntegrity;

    public ModifySoulWardPropertiesEvent(LivingEntity entity, SoulWardData soulWardData, DamageSource source, double physicalDamageAbsorption, double magicDamageAbsorption, double soulWardIntegrity) {
        super(entity, soulWardData, source);
        this.physicalDamageAbsorption = physicalDamageAbsorption;
        this.magicDamageAbsorption = magicDamageAbsorption;
        this.soulWardIntegrity = soulWardIntegrity;
        this.newPhysicalDamageAbsorption = physicalDamageAbsorption;
        this.newMagicDamageAbsorption = magicDamageAbsorption;
        this.newSoulWardIntegrity = soulWardIntegrity;
    }

    public double getPhysicalDamageAbsorption() {
        return physicalDamageAbsorption;
    }

    public double getNewPhysicalDamageAbsorption() {
        return newPhysicalDamageAbsorption;
    }

    public void setNewMagicDamageAbsorption(double newMagicDamageAbsorption) {
        this.newMagicDamageAbsorption = newMagicDamageAbsorption;
    }

    public double getMagicDamageAbsorption() {
        return magicDamageAbsorption;
    }

    public double getNewMagicDamageAbsorption() {
        return newMagicDamageAbsorption;
    }

    public void setNewPhysicalDamageAbsorption(double newPhysicalDamageAbsorption) {
        this.newPhysicalDamageAbsorption = newPhysicalDamageAbsorption;
    }

    public double getOriginalIntegrity() {
        return soulWardIntegrity;
    }

    public double getNewIntegrity() {
        return newSoulWardIntegrity;
    }

    public void setNewIntegrity(double newSoulWardIntegrity) {
        this.newSoulWardIntegrity = newSoulWardIntegrity;
    }
}