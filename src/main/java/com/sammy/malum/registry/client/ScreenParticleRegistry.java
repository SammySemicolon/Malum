package com.sammy.malum.registry.client;

import com.sammy.malum.*;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import team.lodestar.lodestone.systems.particle.screen.*;

import static team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleTypes.*;


@SuppressWarnings("unused")
public class ScreenParticleRegistry {
    public static final ScreenParticleType<ScreenParticleOptions> SAW = registerType(new LodestoneScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> LIGHT_SPEC_SMALL = registerType(new LodestoneScreenParticleType());

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {//TODO maybe use event?
        registerProvider(SAW, new LodestoneScreenParticleType.Factory(getSpriteSet(MalumMod.malumPath("saw"))));
        registerProvider(LIGHT_SPEC_SMALL, new LodestoneScreenParticleType.Factory(getSpriteSet(MalumMod.malumPath("light_spec_small"))));
    }
}