package com.sammy.malum.visual_effects;

import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.options.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

import java.awt.*;
import java.util.function.*;

public class WeaponParticleEffects {

    public static final PointyDirectionalBehaviorComponent MAELSTROM_DIRECTION = new PointyDirectionalBehaviorComponent(new Vec3(0, 0, 0));

    public static void spawnMaelstromParticles(AbstractScytheProjectileEntity entity) {
        var level = entity.level();
        if (level.getGameTime() % 2L == 0) {
            var random = level.getRandom();
            var spirit = entity.getItem().getItem() instanceof ISpiritAffiliatedItem spiritItem ? spiritItem.getDefiningSpiritType() : null;
            var slash = WeaponParticleEffects.spawnSlashParticle(level, entity.position(), ParticleRegistry.ROUNDABOUT_SLASH, spirit);
            float spinOffset = RandomHelper.randomBetween(random, -0.8f, 0.8f);
            int age = RandomHelper.randomBetween(random, 8, 18);
            slash.getBuilder()
                    .setBehavior(MAELSTROM_DIRECTION)
                    .setSpinData(SpinParticleData.create(0).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 1.5f, 3f)).build())
                    .setTransparencyData(GenericParticleData.create(0.9f, 0.6f).build())
                    .addTickActor(p -> p.setParticlePosition(entity.position()))
                    .setLifetime(age);
            slash.spawnParticles();
            slash.getBuilder()
                    .setTransparencyData(GenericParticleData.create(0.6f, 0.3f).build())
                    .setLifeDelay(6);
            slash.spawnParticles();
            slash.getBuilder()
                    .setTransparencyData(GenericParticleData.create(0.3f, 0f).build())
                    .setLifeDelay(12);
            slash.spawnParticles();
        }
    }


    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, Supplier<LodestoneWorldParticleType> particleType, ColorEffectData color) {
        if (color == null) {
            return spawnSlashParticle(level, pos, particleType);
        }
        if (color.isSpiritBased()) {
            return spawnSlashParticle(level, pos, particleType, color.getSpirit());
        }
        return spawnSlashParticle(level, pos, particleType, color.getColor());
    }
    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, Supplier<LodestoneWorldParticleType> particleType, MalumSpiritType spiritType) {
        if (spiritType == null) {
            return spawnSlashParticle(level, pos, particleType);
        }
        return spawnSlashParticle(level, pos, SpiritBasedParticleBuilder.createSpirit(particleType.get()).setSpirit(spiritType));
    }

    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, Supplier<LodestoneWorldParticleType> particleType, ColorParticleData colorData) {
        return spawnSlashParticle(level, pos, WorldParticleBuilder.create(particleType.get()).setColorData(colorData));
    }

    public static ParticleEffectSpawner spawnSlashParticle(Level level, Vec3 pos, Supplier<LodestoneWorldParticleType> particleType) {
        return spawnSlashParticle(level, pos, WorldParticleBuilder.create(particleType).setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT).setColorData(createGrayParticleColor(level.random)));
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