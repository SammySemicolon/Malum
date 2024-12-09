package com.sammy.malum.core.systems.events;

import com.sammy.malum.common.capabilities.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.event.entity.living.*;

public abstract class SoulWardEvent extends LivingEvent {

    private final SoulWardData soulWardData;
    private final DamageSource source;

    public SoulWardEvent(LivingEntity entity, SoulWardData soulWardData, DamageSource source) {
        super(entity);
        this.soulWardData = soulWardData;
        this.source = source;
    }

    public SoulWardData getSoulWardHandler() {
        return soulWardData;
    }

    public DamageSource getSource() {
        return source;
    }
}