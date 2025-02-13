package com.sammy.malum.visual_effects.networked.totem;

import com.sammy.malum.visual_effects.TotemParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class EntityTotemParticleEffect extends ParticleEffectType {

    public EntityTotemParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) ->
                TotemParticleEffects.triggerEntityEffect(level, colorData.getSpiritType(), positionData.getAsVector());
    }
}