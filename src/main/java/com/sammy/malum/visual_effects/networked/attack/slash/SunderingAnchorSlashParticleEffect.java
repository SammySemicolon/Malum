package com.sammy.malum.visual_effects.networked.attack.slash;

import com.sammy.malum.*;
import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.util.function.*;

public class SunderingAnchorSlashParticleEffect extends SlashAttackParticleEffect {

    public SunderingAnchorSlashParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(Vec3 direction, boolean mirror, float angle, int slashCount) {
        return createData(direction, mirror, angle, slashCount, null);
    }

    public static NBTEffectData createData(Vec3 direction, boolean mirror, float angle, int slashCount, MalumSpiritType spiritType) {
        var data = SlashAttackParticleEffect.createData(direction, mirror, angle, spiritType);
        data.compoundTag.putInt("slashCount", slashCount);
        return data;
    }

    public MalumSpiritType getRandomSpirit() {
        var spirits = new MalumSpiritType[]{SpiritTypeRegistry.INFERNAL_SPIRIT, SpiritTypeRegistry.SACRED_SPIRIT, SpiritTypeRegistry.AQUEOUS_SPIRIT, SpiritTypeRegistry.EARTHEN_SPIRIT};
        return spirits[MalumMod.RANDOM.nextInt(spirits.length)];
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
            int slashCount = nbtData.compoundTag.getInt("slashCount");
            var spirit = getSpiritType(nbtData);

            for (int i = 0; i < slashCount; i++) {
                float spinOffset = angle + RandomHelper.randomBetween(random, -3.14f, 3.14f) + (mirror ? 3.14f : 0);
                for (int j = 0; j < 2; j++) {
                    var slash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), ParticleRegistry.THIN_SLASH, spirit);
                    int lifeDelay = (i+j) * 2;
                    slash.getBuilder()
                            .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                            .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 1f, 2f)).build())
                            .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.8f, 1.3f)))
                            .setLifeDelay(lifeDelay)
                            .setLifetime(4)
                            .setBehavior(new PointyDirectionalBehaviorComponent(direction));
                    slash.spawnParticles();
                }
                spirit = getRandomSpirit();
            }
        };
    }
}