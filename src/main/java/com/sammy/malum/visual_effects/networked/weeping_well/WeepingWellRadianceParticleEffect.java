package com.sammy.malum.visual_effects.networked.weeping_well;

import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.neoforged.api.distmarker.*;

import java.util.function.*;

public class WeepingWellRadianceParticleEffect extends ParticleEffectType {

    public WeepingWellRadianceParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            RadiantParticleEffects.spitOutWeepingWellRadiance(level, positionData);
        };
    }
}