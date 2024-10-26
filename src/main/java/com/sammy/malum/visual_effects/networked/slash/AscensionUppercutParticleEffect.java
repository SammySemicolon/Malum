package com.sammy.malum.visual_effects.networked.slash;

import com.sammy.malum.client.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.util.function.*;

public class AscensionUppercutParticleEffect extends SlashAttackParticleEffect {

    public AscensionUppercutParticleEffect(String id) {
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
            Vec3 up = left.cross(direction);
            float angle = nbtData.compoundTag.getFloat("angle");
            boolean mirror = nbtData.compoundTag.getBoolean("mirror");
            var spirit = getSpiritType(nbtData);

            for(int i = 0; i < 6; i++) {
                float upwardsOffset = i*0.4f;
                float slashOffset = 2 - i*0.6f;
                for (int j = 0; j < 2; j++) {
                    float spinOffset = angle + RandomHelper.randomBetween(random, -0.25f, 0.25f) + (mirror ? 3.14f : 0);

                    var slashPosition = positionData.getAsVector().add(direction.scale(slashOffset)).add(up.scale(upwardsOffset));

                    var slash = SlashParticleEffects.spawnSlashParticle(level, slashPosition, spirit);
                    slash.getBuilder()
                            .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                            .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 2.5f, 3f)).build())
                            .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.2f, 0.4f)).add(0, 0.8f, 0))
                            .setLifetime(3+i)
                            .setLifeDelay(i/2)
                            .setBehavior(new PointyDirectionalBehaviorComponent(direction));
                    slash.spawnParticles();
                }
            }
        };
    }
}