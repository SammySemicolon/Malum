package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.*;

public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MalumMod.MALUM);

    public static DeferredHolder<ParticleType<?>, SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);

    //Simpler Shapes
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> LIGHT_SPEC_SMALL = PARTICLES.register("light_spec_small", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> LIGHT_SPEC_LARGE = PARTICLES.register("light_spec_large", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> STAR = PARTICLES.register("star", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> STRANGE_SMOKE = PARTICLES.register("strange_smoke", LodestoneWorldParticleType::new);

    //Shapes :3
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> HEXAGON = PARTICLES.register("hexagon", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> CIRCLE = PARTICLES.register("circle", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SQUARE = PARTICLES.register("square", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SAW = PARTICLES.register("saw", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SHINE = PARTICLES.register("shine", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SPARK = PARTICLES.register("spark", LodestoneWorldParticleType::new);

    //Weapon Effects
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SLASH = PARTICLES.register("slash", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> THIN_SLASH = PARTICLES.register("thin_slash", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> SLAM = PARTICLES.register("staff_slam", LodestoneWorldParticleType::new);

    //Staff Charge Effects
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> HEX_TARGET = PARTICLES.register("hex_target", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> AURIC_TARGET = PARTICLES.register("auric_target", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> DRAINING_TARGET = PARTICLES.register("draining_target", LodestoneWorldParticleType::new);



    //Glowing Bits
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> GIANT_GLOWING_STAR = PARTICLES.register("giant_glowing_star", LodestoneWorldParticleType::new);

    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> GIANT_ARROW = PARTICLES.register("giant_arrow", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> RITUAL_CIRCLE = PARTICLES.register("ritual_circle", LodestoneWorldParticleType::new);
    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> RITUAL_CIRCLE_WISP = PARTICLES.register("ritual_circle_wisp", LodestoneWorldParticleType::new);

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        for (DeferredHolder<ParticleType<?>, ? extends ParticleType<?>> entry : PARTICLES.getEntries()) {
            LodestoneWorldParticleType particleType = (LodestoneWorldParticleType)entry.get();
            if (particleType.equals(SPIRIT_FLAME_PARTICLE.get())) {
                continue;
            }
            event.registerSpriteSet(particleType, LodestoneWorldParticleType.Factory::new);
        }
    }
}