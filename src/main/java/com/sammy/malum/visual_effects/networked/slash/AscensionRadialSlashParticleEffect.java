package com.sammy.malum.visual_effects.networked.slash;

import com.sammy.malum.client.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.util.function.*;

public class AscensionRadialSlashParticleEffect extends SlashAttackParticleEffect {

    public AscensionRadialSlashParticleEffect(String id) {
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
            float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            float angle = nbtData.compoundTag.getFloat("angle");
            boolean mirror = nbtData.compoundTag.getBoolean("mirror");
            var spirit = getSpiritType(nbtData);

            for(int i = 0; i < 3; i++) {
                for (int j = 0; j < 16; j++) {
                    float spinOffset = angle + RandomHelper.randomBetween(random, -0.5f, 0.5f) + (mirror ? 3.14f : 0);
                    float slashAngle = (i*0.33f+j) / 16f * (float) Math.PI * 2f;
                    var slashDirection = left.scale(Math.sin(slashAngle))
                            .add(direction.scale(Math.cos(slashAngle)))
                            .normalize();
                    var slashPosition = positionData.getAsVector().add(slashDirection.scale(1.75f));

                    var slash = SlashParticleEffects.spawnSlashParticle(level, slashPosition, spirit);
                    slash.getBuilder()
                            .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                            .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 2.5f, 3f)).build())
                            .setMotion(slashDirection.scale(RandomHelper.randomBetween(random, 0.2f, 0.4f)).add(0, 0.8f, 0))
                            .setLifetime(3+i)
                            .setLifeDelay(i+j/4)
                            .setBehavior(new PointyDirectionalBehaviorComponent(slashDirection));
                    slash.spawnParticles();
                }
            }
        };
    }
}