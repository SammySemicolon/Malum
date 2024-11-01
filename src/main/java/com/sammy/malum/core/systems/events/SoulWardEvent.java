package com.sammy.malum.core.systems.events;

import com.sammy.malum.core.handlers.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.event.entity.living.*;

public abstract class SoulWardEvent extends LivingEvent {

    private final SoulWardHandler soulWardHandler;
    private final DamageSource source;

    public SoulWardEvent(LivingEntity entity, SoulWardHandler soulWardHandler, DamageSource source) {
        super(entity);
        this.soulWardHandler = soulWardHandler;
        this.source = source;
    }

    public SoulWardHandler getSoulWardHandler() {
        return soulWardHandler;
    }

    public DamageSource getSource() {
        return source;
    }
}