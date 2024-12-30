package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.*;

@SuppressWarnings("unused")
public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MalumMod.MALUM);

    public static DeferredHolder<ParticleType<?>, SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);

    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> WEIRD_SQUIGGLE = PARTICLES.register("weird_squiggle", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> LIGHT_SPEC_SMALL = PARTICLES.register("light_spec_small", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> LIGHT_SPEC_LARGE = PARTICLES.register("light_spec_large", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> STAR = PARTICLES.register("star", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> STRANGE_SMOKE = PARTICLES.register("strange_smoke", LodestoneWorldParticleType::new);

    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> ROUND_SPARK = PARTICLES.register("round_spark", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> BOLT = PARTICLES.register("bolt", LodestoneWorldParticleType::new);


    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> RITUAL_CIRCLE = PARTICLES.register("ritual_circle", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> RITUAL_CIRCLE_WISP = PARTICLES.register("ritual_circle_wisp", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SHARP_SPARK = PARTICLES.register("sharp_spark", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> HEXAGON = PARTICLES.register("hexagon", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> CIRCLE = PARTICLES.register("circle", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SQUARE = PARTICLES.register("square", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SAW = PARTICLES.register("saw", LodestoneWorldParticleType::new);

    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SLAM = PARTICLES.register("staff_slam", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SLASH = PARTICLES.register("slash", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> THIN_SLASH = PARTICLES.register("thin_slash", LodestoneWorldParticleType::new);

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticleType.Factory::new);

        event.registerSpriteSet(WEIRD_SQUIGGLE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(LIGHT_SPEC_SMALL.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(LIGHT_SPEC_LARGE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(STRANGE_SMOKE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(STAR.get(), LodestoneWorldParticleType.Factory::new);

        event.registerSpriteSet(ROUND_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(BOLT.get(), LodestoneWorldParticleType.Factory::new);

        event.registerSpriteSet(RITUAL_CIRCLE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(RITUAL_CIRCLE_WISP.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SHARP_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(HEXAGON.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(CIRCLE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SQUARE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SAW.get(), LodestoneWorldParticleType.Factory::new);

        event.registerSpriteSet(THIN_SLASH.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(SLASH.get(), LodestoneWorldParticleType.Factory::new);

        event.registerSpriteSet(SLAM.get(), LodestoneWorldParticleType.Factory::new);
    }
}