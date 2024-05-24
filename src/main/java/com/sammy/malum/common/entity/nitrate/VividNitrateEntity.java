package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataBuilder;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static net.minecraft.util.Mth.nextFloat;

public class VividNitrateEntity extends AbstractNitrateEntity {

    public record ColorFunctionData(Level level, float duration, float offset, float partialTicks) {
        public ColorFunctionData(Level level, float offset) {
            this(level, 12f, offset, 0);
        }
    }

    public static final List<Color> COLORS = new ArrayList<>(List.of(
            new Color(231, 58, 73),
            new Color(246, 125, 70),
            new Color(249, 206, 77),
            new Color(48, 242, 71),
            new Color(48, 208, 242),
            new Color(59, 48, 242),
            new Color(145, 42, 247),
            new Color(231, 58, 73)));

    public static final Function<ColorFunctionData, Color> COLOR_FUNCTION = f -> {
        float time = ((f.level.getGameTime() + f.partialTicks) % f.duration) / f.duration;
        float lerp = time + f.offset;
        if (lerp > 1) {
            lerp -= Math.floor(lerp);
        }
        return ColorHelper.multicolorLerp(Easing.SINE_IN, lerp, VividNitrateEntity.COLORS);
    };

    public VividNitrateEntity(Level level) {
        super(EntityRegistry.VIVID_NITRATE.get(), level);
    }

    public VividNitrateEntity(LivingEntity owner, Level level) {
        super(EntityRegistry.VIVID_NITRATE.get(), owner, level);
    }

    @Override
    public float getExplosionRadius() {
        return 3f;
    }

    @Override
    public int getMaxPierce() {
        return 20;
    }

    @Override
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.ETHERIC_NITRATE_IMPACT;
    }

    @Override
    public ColorEffectData getImpactParticleEffectColor() {
        return new ColorEffectData(COLOR_FUNCTION.apply(new ColorFunctionData(level(), 0.125f)));
    }

    @Override
    public void onExplode() {
        Vec3 deltaMovement = getDeltaMovement();
        var random = level().random;
        double x = Mth.lerp(0.5f, deltaMovement.x, Math.min(2, (0.9f + random.nextFloat() * 0.3f) * (random.nextBoolean() ? -deltaMovement.x : deltaMovement.x) + 0.45f - random.nextFloat() * 0.9f));
        double y = Mth.lerp(0.5f, deltaMovement.y, Math.min(2, (deltaMovement.y * (0.7f + random.nextFloat() * 0.3f) + 0.2f + random.nextFloat() * 0.4f)));
        double z = Mth.lerp(0.5f, deltaMovement.z, Math.min(2, (0.9f + random.nextFloat() * 0.3f) * (random.nextBoolean() ? -deltaMovement.z : deltaMovement.z) + 0.45f - random.nextFloat() * 0.9f));
        if (random.nextFloat() < 0.2f) {
            x *= 1.5f + random.nextFloat() * 0.5f;
            y += 0.25f + random.nextFloat() * 0.5f;
            z *= 1.5f + random.nextFloat() * 0.5f;
        }
        y = Math.min(y, 1f);
        setDeltaMovement(x, y, z);
    }


    @Environment(EnvType.CLIENT)
    @Override
    public void spawnParticles() {
        float scalar = age > MAX_AGE - 10 ? 1f - (age - MAX_AGE + 10) / 10f : 1f;
        if (age < 5) {
            scalar = age / 5f;
        }
        Color color = COLOR_FUNCTION.apply(new ColorFunctionData(level(), 0));
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), position(), ColorParticleData.create(color, color).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.9f).build());
        lightSpecs.getBuilder().multiplyLifetime(1.5f).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(norm);
        lightSpecs.spawnParticles();
        Color smokeColor = AbstractNitrateEntity.SECOND_SMOKE_COLOR;
        for (int i = 0; i < 3; i++) {
            color = age < 3 ? smokeColor : COLOR_FUNCTION.apply(new ColorFunctionData(level(), i*0.25f));
            int lifetime = (int) (RandomHelper.randomBetween(random, 60, 80) * (1 - i / 3f));
            final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0, RandomHelper.randomBetween(random, 0f, 0.4f), 0).randomSpinOffset(random).build();
            final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.98f));
            WorldParticleBuilder.create(ParticleRegistry.STRANGE_SMOKE)
                    .setTransparencyData(GenericParticleData.create(0.7f * scalar, 0.9f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(spinData)
                    .setScaleData(GenericParticleData.create(0.2f * scalar, 0.4f * scalar, 0.6f * scalar).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, smokeColor).setEasing(Easing.QUAD_OUT).setCoefficient(1.1f).build())
                    .setLifetime(Math.min(6 + age * 3, lifetime))
                    .setLifeDelay(1)
                    .enableNoClip()
                    .enableForcedSpawn()
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .addTickActor(behavior)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(level(), position().x, position().y, position().z);
        }
    }
}
