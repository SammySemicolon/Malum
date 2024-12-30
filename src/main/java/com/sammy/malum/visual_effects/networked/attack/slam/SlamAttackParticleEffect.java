package com.sammy.malum.visual_effects.networked.attack.slam;

import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.DirectionalBehaviorComponent;

import java.util.function.*;

public class SlamAttackParticleEffect extends ParticleEffectType {

    public SlamAttackParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(Vec3 direction, float angle) {
        return createData(direction, angle, null);
    }
    public static NBTEffectData createData(Vec3 direction, float angle, MalumSpiritType spiritType) {
        CompoundTag tag = new CompoundTag();
        CompoundTag directionTag = new CompoundTag();
        directionTag.putDouble("x", direction.x);
        directionTag.putDouble("y", direction.y);
        directionTag.putDouble("z", direction.z);
        tag.putFloat("angle", angle);
        if (spiritType != null) {
            tag.putString("spiritType", spiritType.getIdentifier());
        }
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
            Vec3 direction = new Vec3(dirX, dirY, dirZ);
            float angle = nbtData.compoundTag.getFloat("angle");
            boolean mirror = nbtData.compoundTag.getBoolean("mirror");
            var spirit = getSpiritType(nbtData);
            float spinOffset = angle + RandomHelper.randomBetween(random, -0.5f, 0.5f) + (mirror ? 3.14f : 0);

            var slam = WeaponParticleEffects.spawnSlamParticle(level, positionData.getAsVector(), spirit);
            slam.getBuilder()
                    .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 1f, 2f)).build())
                    .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.6f, 0.8f)))
                    .setBehavior(new DirectionalBehaviorComponent(direction));
            slam.spawnParticles();
        };
    }

    public MalumSpiritType getSpiritType(NBTEffectData data) {
        return SpiritTypeRegistry.SPIRITS.get(data.compoundTag.getString("spiritType"));
    }
}