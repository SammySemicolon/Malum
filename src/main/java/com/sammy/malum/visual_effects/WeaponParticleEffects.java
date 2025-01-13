package com.sammy.malum.visual_effects;

import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;
import java.util.function.*;

public class WeaponParticleEffects {

    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, MalumSpiritType spiritType) {
        if (spiritType == null) {
            return spawnSlashParticle(level, pos);
        }
        return spawnSlashParticle(level, pos, o -> SpiritBasedParticleBuilder.createSpirit(o).setSpirit(spiritType));
    }

    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos) {
        return spawnSlashParticle(level, pos, o -> WorldParticleBuilder.create(o).setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT).setColorData(createGrayParticleColor(level.random)));
    }

    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, ColorParticleData colorData) {
        return spawnSlashParticle(level, pos, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier) {
        var builder = builderSupplier.apply(new WorldParticleOptions(ParticleRegistry.SLASH));
        return spawnSlashParticle(level, pos, builder);
    }

    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, WorldParticleBuilder builder) {
        var rand = level.getRandom();
        final WorldParticleBuilder worldParticleBuilder = builder
                .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(rand, 1.5f, 2f)).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setTransparencyData(GenericParticleData.create(1f, 0.9f).build())
                .enableForcedSpawn()
                .setLifetime(5)
                .enableNoClip();
        return new ParticleEffectSpawner(level, pos, worldParticleBuilder);
    }

    public static ParticleEffectSpawner spawnSlamParticle(Level level, Vec3 pos, MalumSpiritType spiritType) {
        if (spiritType == null) {
            return spawnSlamParticle(level, pos);
        }
        return spawnSlamParticle(level, pos, o -> SpiritBasedParticleBuilder.createSpirit(o).setSpirit(spiritType));
    }

    public static ParticleEffectSpawner spawnSlamParticle(Level level, Vec3 pos) {
        return spawnSlamParticle(level, pos, o -> WorldParticleBuilder.create(o).setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT).setColorData(createGrayParticleColor(level.random)));
    }

    public static ParticleEffectSpawner spawnSlamParticle(Level level, Vec3 pos, ColorParticleData colorData) {
        return spawnSlamParticle(level, pos, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner spawnSlamParticle(Level level, Vec3 pos, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier) {
        var builder = builderSupplier.apply(new WorldParticleOptions(ParticleRegistry.SLAM));
        return spawnSlamParticle(level, pos, builder);
    }

    public static ParticleEffectSpawner spawnSlamParticle(Level level, Vec3 pos, WorldParticleBuilder builder) {
        var rand = level.getRandom();
        final WorldParticleBuilder worldParticleBuilder = builder
                .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(rand, 1.5f, 2f)).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setTransparencyData(GenericParticleData.create(1f, 0.9f).build())
                .enableForcedSpawn()
                .setLifetime(5)
                .enableNoClip();
        return new ParticleEffectSpawner(level, pos, worldParticleBuilder);
    }

    public static ColorParticleData createGrayParticleColor(RandomSource random) {
        int brightness = (int) (255 * RandomHelper.randomBetween(random, 0.6f, 1f));
        Color color = new Color(brightness, brightness, brightness);
        return ColorParticleData.create(color, color.darker()).setEasing(Easing.SINE_IN_OUT).build();
    }
}