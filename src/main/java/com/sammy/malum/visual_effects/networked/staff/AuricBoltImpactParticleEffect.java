package com.sammy.malum.visual_effects.networked.staff;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;

import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;

public class AuricBoltImpactParticleEffect extends ParticleEffectType {

    public AuricBoltImpactParticleEffect(String id) {
        super(id);
    }

    
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!nbtData.compoundTag.contains("direction")) {
                return;
            }
            ColorParticleData colorParticleData = AuricFlameStaffItem.AURIC_COLOR_DATA;
            final CompoundTag directionData = nbtData.compoundTag.getCompound("direction");
            double dirX = directionData.getDouble("x");
            double dirY = directionData.getDouble("y");
            double dirZ = directionData.getDouble("z");
            Vec3 projectileDirection = new Vec3(dirX, dirY, dirZ);
            float yRot = ((float) (Mth.atan2(projectileDirection.x, projectileDirection.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            Vec3 up = left.cross(projectileDirection);

            double posX = positionData.posX;
            double posY = positionData.posY;
            double posZ = positionData.posZ;
            Vec3 pos = new Vec3(posX, posY, posZ);

            for (int i = 0; i < 32; i++) {
                float spread = RandomHelper.randomBetween(random, 0.05f, 0.075f);
                float speed = RandomHelper.randomBetween(random, 0.4f, 0.6f);
                float distance = RandomHelper.randomBetween(random, 4f, 12f);
                float angle = i / 32f * (float) Math.PI * 2f;

                Vec3 direction = projectileDirection
                        .add(left.scale(Math.sin(angle) * spread))
                        .add(up.scale(Math.cos(angle) * spread))
                        .normalize().scale(speed);
                Vec3 spawnPosition = pos.add(direction.scale(distance));
                direction = direction.reverse();
                float lifetimeMultiplier = 0.7f;
                if (random.nextFloat() < 0.8f) {
                    var lightSpecs = spiritLightSpecs(level, spawnPosition, colorParticleData);
                    lightSpecs.getBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .enableForcedSpawn()
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.25f))
                            .setMotion(direction);
                    lightSpecs.getBloomBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .setMotion(direction);
                    lightSpecs.spawnParticles();
                }
                if (random.nextFloat() < 0.8f) {
                    var sparks = SparkParticleEffects.spiritMotionSparks(level, spawnPosition, colorParticleData);
                    sparks.getBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .enableForcedSpawn()
                            .setMotion(direction.scale(1.5f))
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.25f))
                            .modifyOptionalData(b -> b.getBehaviorData(SparkBehaviorComponent.class, SparkBehaviorComponent::getLengthData), d -> d.multiplyValue(2f));
                    sparks.getBloomBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .setMotion(direction.scale(1.5f));
                    sparks.spawnParticles();
                }
            }
        };
    }
}