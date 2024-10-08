package com.sammy.malum.visual_effects.networked.slash;

import com.sammy.malum.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.util.function.*;

public class HiddenBladeCounterParticleEffect extends SlashAttackParticleEffect {

    public HiddenBladeCounterParticleEffect(String id) {
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

            final float maxBackwardsOffset = 1.5f;
            final float maxForwardsOffset = 4.5f;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 100; j++) {
                    float offsetBase = RandomHelper.randomBetween(random, 0.314f, 3.14f) * (random.nextBoolean() ? 1 : -1) + (mirror ? 3.14f : 0);
                    float spinOffset = angle + (j % 2 == 0 ? 1 : -1) * offsetBase;
                    float scale = RandomHelper.randomBetween(random, 2f, 6f);
                    int lifeDelay = (j % 2 == 0 ? 2 : 0) + i + j / 6;
                    var position = positionData.getAsVector().add(direction.multiply(
                            RandomHelper.randomBetween(random, -maxBackwardsOffset, maxForwardsOffset),
                            RandomHelper.randomBetween(random, -maxBackwardsOffset, maxForwardsOffset),
                            RandomHelper.randomBetween(random, -maxBackwardsOffset, maxForwardsOffset)));
                    var slash = SlashParticleEffects.spawnSlashParticle(level, position, spirit);
                    slash.getBuilder()
                            .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                            .setScaleData(GenericParticleData.create(scale).build())
                            .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.3f, 0.5f)))
                            .setLifeDelay(lifeDelay)
                            .setLifetime(2+i)
                            .setBehavior(new PointyDirectionalBehaviorComponent(direction));
                    slash.spawnParticles();
                }
            }
        };
    }
}