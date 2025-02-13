package com.sammy.malum.visual_effects.networked.attack.slash;

import com.sammy.malum.client.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.nbt.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.util.function.*;

public class SunderingAnchorScanParticleEffect extends SlashAttackParticleEffect {

    public SunderingAnchorScanParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!nbtData.compoundTag.contains("direction")) {
                return;
            }
            final CompoundTag directionData = nbtData.compoundTag.getCompound("direction");
            double dirX = directionData.getDouble("x");
            double dirY = directionData.getDouble("y");
            double dirZ = directionData.getDouble("z");
            Vec3 direction = new Vec3(dirX, dirY, dirZ);
            float angle = nbtData.compoundTag.getFloat("angle");
            boolean mirror = nbtData.compoundTag.getBoolean("mirror");

            for (int i = 0; i < 3; i++) {
                float offsetBase = RandomHelper.randomBetween(random, -0.2f, 0.8f) * (random.nextBoolean() ? 1 : -1) + (mirror ? 3.14f : 0);
                ParticleEffectSpawner slash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), ParticleRegistry.THIN_ROUNDABOUT_SLASH, colorData);
                final boolean isEven = i % 2 == 0;
                float spinOffset = angle + (isEven ? 1 : -1) * offsetBase;
                int lifeDelay = (isEven ? 3 : 0);
                int lifetime = isEven ? 6+i : 3+i*2;
                int directionScalar = isEven ? -1 : 1;
                slash.getBuilder()
                        .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                        .setLifetime(lifetime)
                        .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 4f, 6f)).build())
                        .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.1f, 0.3f)))
                        .setRandomOffset(0.5f)
                        .setLifeDelay(lifeDelay)
                        .setBehavior(new PointyDirectionalBehaviorComponent(direction.scale(directionScalar)));
                slash.spawnParticles();
                slash.getBuilder()
                        .setTransparencyData(GenericParticleData.create(0.6f, 0.3f).build())
                        .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 6f, 8f)).build())
                        .setLifeDelay(lifeDelay+4);
                slash.spawnParticles();
                slash.getBuilder()
                        .setTransparencyData(GenericParticleData.create(0.3f, 0f).build())
                        .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 8f, 10f)).build())
                        .setLifeDelay(lifeDelay+8);
                slash.spawnParticles();
            }
        };
    }
}