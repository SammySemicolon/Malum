package com.sammy.malum.visual_effects.networked.weeping_well;

import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;


import java.util.function.Supplier;

public class WeepingWellReactionParticleEffect extends ParticleEffectType {

    public WeepingWellReactionParticleEffect(String id) {
        super(id);
    }

    
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            WeepingWellParticleEffects.spitOutItemParticles(level, positionData);
        };
    }
}