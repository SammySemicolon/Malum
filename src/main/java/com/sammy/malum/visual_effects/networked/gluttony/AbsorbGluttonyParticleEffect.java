package com.sammy.malum.visual_effects.networked.gluttony;

import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.neoforged.api.distmarker.*;

import java.util.function.*;

public class AbsorbGluttonyParticleEffect extends ParticleEffectType {

    public static NBTEffectData createData(float potency) {
        NBTEffectData effectData = new NBTEffectData();
        effectData.compoundTag.putFloat("potency", potency);
        return effectData;
    }

    public AbsorbGluttonyParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            GluttonyParticleEffects.incrementGluttonyStatusEffect(positionData, nbtData.compoundTag.getFloat("potency"));
        };
    }
}