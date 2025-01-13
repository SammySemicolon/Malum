package com.sammy.malum.visual_effects;

import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.ParticleEffectSpawner;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.AbstractParticleBuilder;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.LodestoneBehaviorComponent;

import java.util.function.Consumer;

public class SpiritDiodeParticleEffects {

    public static void openSpiritDiode(PositionEffectData positionData, ColorEffectData colorData) {
        ClientLevel level = Minecraft.getInstance().level;
        var random = level.random;

        for (int i = 0; i < 2; i++) {
            int lifeDelay = i * 3;
            float yVelocity = RandomHelper.randomBetween(random, 0.01f, 0.02f);
            var square = waveformSquare(level, positionData.getAsVector(), ColorParticleData.create(colorData.getPrimaryColor(), colorData.getSecondaryColor()).build());
            square.getBuilder()
                    .setBehavior(LodestoneBehaviorComponent.DIRECTIONAL)
                    .setLifeDelay(lifeDelay)
                    .setMotion(0, yVelocity, 0);
            square.spawnParticles();
        }
    }
    public static void closeSpiritDiode(PositionEffectData positionData, ColorEffectData colorData) {
        ClientLevel level = Minecraft.getInstance().level;
        var random = level.random;

        for (int i = 0; i < 4; i++) {
            int lifeDelay = i * 2;
            var square = waveformSquare(level, positionData.getAsVector(), ColorParticleData.create(colorData.getPrimaryColor(), colorData.getSecondaryColor()).build());
            square.getBuilder()
                    .setSpinData(SpinParticleData.createRandomDirection(random, 2f, 0).setEasing(Easing.EXPO_OUT).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                    .setLifeDelay(lifeDelay);
            square.spawnParticles();
        }
    }


    public static ParticleEffectSpawner waveformSquare(Level level, Vec3 pos, ColorParticleData colorData) {
        RandomSource rand = level.random;
        final GenericParticleData scaleData = GenericParticleData.create(0.1f, RandomHelper.randomBetween(rand, 0.5f, 0.6f), 0.5f)
                .setEasing(Easing.SINE_OUT, Easing.SINE_IN)
                .setCoefficient(RandomHelper.randomBetween(rand, 1f, 1.25f)).build();
        final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.99f));
        var squares = WorldParticleBuilder.create(ParticleRegistry.SQUARE.get())
                .setTransparencyData(GenericParticleData.create(0.7f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setScaleData(scaleData)
                .setColorData(colorData)
                .setLifetime(15)
                .enableNoClip()
                .addTickActor(behavior);
        Consumer<WorldParticleBuilder> squareSpawner = b -> b
                .spawn(level, pos.x, pos.y, pos.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level, pos.x, pos.y, pos.z);

        return new ParticleEffectSpawner(squares, squareSpawner);
    }
}
