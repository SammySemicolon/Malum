package com.sammy.malum.visual_effects.networked;

import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;

public class SapCollectionParticleEffect extends ParticleEffectType {

    public SapCollectionParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(Direction clickedFace) {
        NBTEffectData effectData = new NBTEffectData(new CompoundTag());
        effectData.compoundTag.putString("direction", clickedFace.getName());
        return effectData;
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!nbtData.compoundTag.contains("direction")) {
                return;
            }
            Direction direction = Direction.byName(nbtData.compoundTag.getString("direction"));
            ColorEffectData.ColorRecord colorRecord = colorData.getDefaultColorRecord();
            Color primaryColor = colorData.getPrimaryColor(colorRecord);
            Color secondaryColor = colorData.getSecondaryColor(colorRecord);
            BlockPos blockPos = positionData.getAsBlockPos();
            Vec3 pos = blockPos.getCenter().relative(direction, 0.5f);
            Vec3 playerPosition = Minecraft.getInstance().player.position();
            final Vec3i normal = direction.getNormal();
            float yRot = ((float) (Mth.atan2(normal.getX(), normal.getZ()) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            Vec3 up = left.cross(new Vec3(normal.getX(), normal.getY(), normal.getZ()));
            final Consumer<LodestoneWorldParticle> acceleration = p -> p.setParticleSpeed(p.getParticleSpeed().scale(1.2f));
            for (int i = 0; i < 12; i++) {
                final float leftOffset = (random.nextFloat() - 0.5f) * 0.75f;
                final float upOffset = (random.nextFloat() - 0.5f) * 0.75f;
                Vec3 particlePosition = pos.add(left.scale(leftOffset)).add(up.scale(upOffset));
                Vec3 particleMotion = playerPosition.subtract(particlePosition).normalize();
                Vec3 targetPosition = pos.add(particleMotion.scale(0.75f));
                Vec3 actualMotion = targetPosition.subtract(particlePosition).normalize().scale(0.01f);
                var lightSpecs = spiritLightSpecs(level, particlePosition, ColorParticleData.create(primaryColor, secondaryColor).build());
                lightSpecs.getBuilder().act(b -> b
                        .addTickActor(acceleration)
                        .setMotion(actualMotion)
                        .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f))));
                lightSpecs.getBloomBuilder().act(b -> b
                        .addTickActor(acceleration)
                        .setMotion(actualMotion)
                        .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.6f, 1.5f))));
                lightSpecs.spawnParticles();
            }
        };
    }
}