package com.sammy.malum.visual_effects.networked.staff;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;

public class HexBoltImpactParticleEffect extends ParticleEffectType {

    public HexBoltImpactParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(Vec3 direction) {
        CompoundTag tag = new CompoundTag();
        CompoundTag directionTag = new CompoundTag();
        directionTag.putDouble("x", direction.x);
        directionTag.putDouble("y", direction.y);
        directionTag.putDouble("z", direction.z);
        tag.put("direction", directionTag);
        return new NBTEffectData(tag);
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
            Vec3 projectileDirection = new Vec3(dirX, dirY, dirZ);
            float yRot = ((float) (Mth.atan2(projectileDirection.x, projectileDirection.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            Vec3 up = left.cross(projectileDirection);

            double posX = positionData.posX;
            double posY = positionData.posY;
            double posZ = positionData.posZ;
            Vec3 pos = new Vec3(posX, posY, posZ);

            MalumSpiritType spiritType = colorData.getSpiritType();
            for (int i = 0; i < 32; i++) {
                float spread = RandomHelper.randomBetween(random, 0.1f, 0.5f);
                float speed = RandomHelper.randomBetween(random, 0.3f, 0.4f);
                float distance = RandomHelper.randomBetween(random, 3f, 6f);
                float angle = i / 32f * (float) Math.PI * 2f;

                Vec3 direction = projectileDirection
                        .add(left.scale(Math.sin(angle) * spread))
                        .add(up.scale(Math.cos(angle) * spread))
                        .normalize().scale(speed);
                Vec3 spawnPosition = pos.add(direction.scale(distance));
                direction = direction.reverse();
                float lifetimeMultiplier = 0.7f;
                if (random.nextFloat() < 0.8f) {
                    var lightSpecs = spiritLightSpecs(level, spawnPosition, spiritType);
                    lightSpecs.getBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .enableForcedSpawn()
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.75f))
                            .setMotion(direction);
                    lightSpecs.getBloomBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .setMotion(direction);
                    lightSpecs.spawnParticles();
                }
                if (random.nextFloat() < 0.8f) {
                    var sparks = SparkParticleEffects.spiritMotionSparks(level, spawnPosition, spiritType);
                    sparks.getBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .enableForcedSpawn()
                            .setMotion(direction.scale(1.5f))
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.75f))
                            .modifyOptionalData(b -> b.getBehaviorData(SparkBehaviorComponent.class, SparkBehaviorComponent::getLengthData), d -> d.multiplyValue(3f));
                    sparks.getBloomBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .setMotion(direction.scale(1.5f));
                    sparks.spawnParticles();
                }
            }
        };
    }
}