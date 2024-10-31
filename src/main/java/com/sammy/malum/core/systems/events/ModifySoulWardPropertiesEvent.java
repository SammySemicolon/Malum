package com.sammy.malum.core.systems.events;

import com.sammy.malum.core.handlers.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.event.entity.living.*;

public class ModifySoulWardPropertiesEvent extends LivingEvent {

    private final SoulWardHandler soulWardHandler;

    private final double originalPhysicalAbsorptionRate;
    private double newPhysicalAbsorptionRate;
    private final double originalMagicalAbsorptionRate;
    private double newMagicalAbsorptionRate;
    private final double originalSoulWardIntegrity;
    private double newSoulWardIntegrity;


    public ModifySoulWardPropertiesEvent(LivingEntity entity, SoulWardHandler soulWardHandler, double originalPhysicalAbsorptionRate, double originalMagicalAbsorptionRate, double originalSoulWardIntegrity) {
        super(entity);
        this.soulWardHandler = soulWardHandler;
        this.originalPhysicalAbsorptionRate = originalPhysicalAbsorptionRate;
        this.originalMagicalAbsorptionRate = originalMagicalAbsorptionRate;
        this.originalSoulWardIntegrity = originalSoulWardIntegrity;
    }

    public SoulWardHandler getSoulWardHandler() {
        return soulWardHandler;
    }

    public double getOriginalPhysicalAbsorptionRate() {
        return originalPhysicalAbsorptionRate;
    }

    public double getOriginalMagicalAbsorptionRate() {
        return originalMagicalAbsorptionRate;
    }

    public double getOriginalSoulWardIntegrity() {
        return originalSoulWardIntegrity;
    }

    public double getNewPhysicalAbsorptionRate() {
        return newPhysicalAbsorptionRate;
    }

    public double getNewMagicalAbsorptionRate() {
        return newMagicalAbsorptionRate;
    }

    public double getNewSoulWardIntegrity() {
        return newSoulWardIntegrity;
    }

    public void setNewPhysicalAbsorptionRate(double newPhysicalAbsorptionRate) {
        this.newPhysicalAbsorptionRate = newPhysicalAbsorptionRate;
    }

    public void setNewMagicalAbsorptionRate(double newMagicalAbsorptionRate) {
        this.newMagicalAbsorptionRate = newMagicalAbsorptionRate;
    }

    public void setNewSoulWardIntegrity(double newSoulWardIntegrity) {
        this.newSoulWardIntegrity = newSoulWardIntegrity;
    }

}
