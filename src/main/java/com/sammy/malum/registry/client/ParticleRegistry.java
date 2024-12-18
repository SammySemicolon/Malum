package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.cut.ScytheAttackParticle;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
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

    public static DeferredHolder<ParticleType<?>, SimpleParticleType> SCYTHE_CUT_PARTICLE = PARTICLES.register("scythe_cut", () -> new SimpleParticleType(true));
    public static DeferredHolder<ParticleType<?>, SimpleParticleType> SCYTHE_SWEEP_PARTICLE = PARTICLES.register("scythe_sweep", () -> new SimpleParticleType(true));
    public static DeferredHolder<ParticleType<?>, SimpleParticleType> STAFF_SLAM_PARTICLE = PARTICLES.register("staff_slam", () -> new SimpleParticleType(true));
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SLASH = PARTICLES.register("slash", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> HEAVY_SLASH = PARTICLES.register("heavy_slash", LodestoneWorldParticleType::new);

    public static void registerParticleFactory() {
        ParticleFactoryRegistry.getInstance().register(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticleType.Factory::new);

        ParticleFactoryRegistry.getInstance().register(WEIRD_SQUIGGLE.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(LIGHT_SPEC_SMALL.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(LIGHT_SPEC_LARGE.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(STRANGE_SMOKE.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(STAR.get(), LodestoneWorldParticleType.Factory::new);

        ParticleFactoryRegistry.getInstance().register(ROUND_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(BOLT.get(), LodestoneWorldParticleType.Factory::new);

        ParticleFactoryRegistry.getInstance().register(RITUAL_CIRCLE.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(RITUAL_CIRCLE_WISP.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SHARP_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(HEXAGON.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(CIRCLE.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SQUARE.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SAW.get(), LodestoneWorldParticleType.Factory::new);

        ParticleFactoryRegistry.getInstance().register(SLASH.get(), LodestoneWorldParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(HEAVY_SLASH.get(), LodestoneWorldParticleType.Factory::new);

        ParticleFactoryRegistry.getInstance().register(SCYTHE_CUT_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SCYTHE_SWEEP_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(STAFF_SLAM_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
}