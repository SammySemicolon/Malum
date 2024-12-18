package com.sammy.malum.visual_effects.networked.gluttony;

import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;


import java.util.function.*;

public class ThrownGluttonyParticleEffect extends ParticleEffectType {

    public ThrownGluttonyParticleEffect(String id) {
        super(id);
    }

    
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            GluttonyParticleEffects.thrownGluttonySplash(positionData);
        };
    }
}