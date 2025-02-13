package com.sammy.malum.visual_effects.networked.attack.slash;

import com.sammy.malum.client.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.awt.*;
import java.util.function.*;

public class SunderingAnchorSweepParticleEffect extends ParticleEffectType {

    public SunderingAnchorSweepParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {

            float angle = random.nextFloat() * 6.28f;
            float x = Mth.sin(angle);
            float z = Mth.cos(angle);
            Vec3 direction = new Vec3(x, 0, z);

            ParticleEffectSpawner slash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), ParticleRegistry.ROUNDABOUT_SLASH, colorData);
            int lifetime = RandomHelper.randomBetween(random, 8, 12);
            int directionScalar = random.nextBoolean() ? -1 : 1;
            slash.getBuilder()
                    .setSpinData(SpinParticleData.create(0).randomSpinOffset(random).build())
                    .setLifetime(lifetime)
                    .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 2f, 3f)).build())
                    .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.02f, 0.05f)))
                    .setRandomOffset(0.5f)
                    .setBehavior(new PointyDirectionalBehaviorComponent(direction.scale(directionScalar)));
            slash.spawnParticles();
            slash.getBuilder()
                    .setTransparencyData(GenericParticleData.create(0.6f, 0.3f).build())
                    .setLifeDelay(lifetime);
            slash.spawnParticles();
            slash.getBuilder()
                    .setTransparencyData(GenericParticleData.create(0.3f, 0f).build())
                    .setLifeDelay(lifetime*2);
            slash.spawnParticles();

        };
    }
}