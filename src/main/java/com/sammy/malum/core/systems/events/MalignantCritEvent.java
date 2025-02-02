package com.sammy.malum.core.systems.events;

import com.sammy.malum.common.capabilities.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.common.damagesource.*;
import net.neoforged.neoforge.event.entity.living.*;

public class MalignantCritEvent extends LivingEvent {

    private final LivingEntity livingEntity;
    private final DamageContainer container;

    public MalignantCritEvent(LivingEntity livingEntity, DamageContainer container) {
        super(livingEntity);
        this.livingEntity = livingEntity;
        this.container = container;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    /**
     * {@return the {@link DamageContainer} instance for this damage sequence}
     */
    public DamageContainer getContainer() {
        return container;
    }

    /**
     * {@return the damage source for this damage sequence}
     */
    public DamageSource getSource() {
        return container.getSource();
    }

    /**
     * {@return the current value to be applied to the entity's health after this event}
     */
    public float getNewDamage() {
        return container.getNewDamage();
    }

    /**
     * {@return the original damage amount from the damage source}
     */
    public float getOriginalDamage() {
        return container.getOriginalDamage();
    }

    /**
     * Sets the amount to reduce the entity health by
     *
     * @param newDamage the new damage value
     */
    public void setNewDamage(float newDamage) {
        container.setNewDamage(newDamage);
    }
}