package com.sammy.malum.visual_effects.networked.totem;

import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.neoforged.api.distmarker.*;

import java.util.function.*;

public class BlockFallTotemParticleEffect extends ParticleEffectType {

    public BlockFallTotemParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) ->
                TotemParticleEffects.triggerBlockFallEffect(level, colorData.getSpiritType(), positionData.getAsBlockPos());
    }
}