package com.sammy.malum.visual_effects.networked.weeping_well;

import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import net.minecraft.util.Mth;

import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;

import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.awt.*;
import java.util.function.Supplier;

public class WeepingWellReactionParticleEffect extends ParticleEffectType {

    public WeepingWellReactionParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            WeepingWellParticleEffects.spitOutItemParticles(level, positionData);
        };
    }
}