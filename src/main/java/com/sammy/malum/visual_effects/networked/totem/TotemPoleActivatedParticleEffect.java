package com.sammy.malum.visual_effects.networked.totem;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;


import java.util.function.*;

public class TotemPoleActivatedParticleEffect extends ParticleEffectType {

    public TotemPoleActivatedParticleEffect(String id) {
        super(id);
    }

    
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) ->
                TotemParticleEffects.activateTotemPoleParticles(level, colorData.getSpiritType(), positionData.getAsBlockPos().getCenter());
    }
}