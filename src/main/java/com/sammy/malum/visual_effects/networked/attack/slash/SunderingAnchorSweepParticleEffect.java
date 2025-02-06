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
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.util.function.*;

public class SunderingAnchorSweepParticleEffect extends SlashAttackParticleEffect {

    public SunderingAnchorSweepParticleEffect(String id) {
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
            var spirit = getSpiritType(nbtData);

            float offsetBase = RandomHelper.randomBetween(random, 1.2f, 1.8f) * (random.nextBoolean() ? 1 : -1) + (mirror ? 3.14f : 0);
            for (int i = 0; i < 6; i++) {
                ParticleEffectSpawner slash;

                slash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), ParticleRegistry.ROUNDABOUT_SLASH, spirit);

                float spinOffset = angle + (i % 2 == 0 ? 1 : -1) * offsetBase;
                int lifeDelay = (i % 2 == 0 ? 3 : 0);
                slash.getBuilder()
                        .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                        .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 1f, 2f)).build())
                        .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.5f, 0.7f)))
                        .setLifeDelay(lifeDelay)
                        .setBehavior(new PointyDirectionalBehaviorComponent(direction));
                slash.spawnParticles();
            }
        };
    }
}