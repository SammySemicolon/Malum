package com.sammy.malum.visual_effects.networked.ritual;

import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;


import java.util.function.*;

public class RitualPlinthBeginChargingParticleEffect extends ParticleEffectType {

    public RitualPlinthBeginChargingParticleEffect(String id) {
        super(id);
    }

    
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RitualPlinthBlockEntity ritualPlinth)) {
                return;
            }
            RitualPlinthParticleEffects.beginChargingParticles(ritualPlinth, colorData);
        };
    }
}