package com.sammy.malum.visual_effects.networked.slash;

import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
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

public class SlashAttackParticleEffect extends ParticleEffectType {

    public SlashAttackParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(Vec3 direction, boolean mirror, float angle) {
        return createData(direction, mirror, angle, null);
    }
    public static NBTEffectData createData(Vec3 direction, boolean mirror, float angle, MalumSpiritType spiritType) {
        CompoundTag tag = new CompoundTag();
        CompoundTag directionTag = new CompoundTag();
        directionTag.putDouble("x", direction.x);
        directionTag.putDouble("y", direction.y);
        directionTag.putDouble("z", direction.z);
        tag.putFloat("angle", angle);
        tag.putBoolean("mirror", mirror);
        if (spiritType != null) {
            tag.putString("spiritType", spiritType.identifier);
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

            var slash = SlashParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), spirit);
            slash.getBuilder()
                    .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 2f, 3f)).build())
                    .setMotion(direction.scale(RandomHelper.randomBetween(random, 0.3f, 0.4f)))
                    .setBehavior(new PointyDirectionalBehaviorComponent(direction));
            slash.spawnParticles();
        };
    }

    public MalumSpiritType getSpiritType(NBTEffectData data) {
        return SpiritTypeRegistry.SPIRITS.get(data.compoundTag.getString("spiritType"));
    }
}