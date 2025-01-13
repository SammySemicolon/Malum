package com.sammy.malum.visual_effects.networked.spirit_diode;

import com.sammy.malum.visual_effects.SpiritDiodeParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class SpiritDiodeCloseParticleEffect extends ParticleEffectType {

    public SpiritDiodeCloseParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            SpiritDiodeParticleEffects.closeSpiritDiode(positionData, colorData);
        };
    }
}