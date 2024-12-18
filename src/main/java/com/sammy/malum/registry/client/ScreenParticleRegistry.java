package com.sammy.malum.registry.client;

import com.sammy.malum.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import static team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleTypes.*;


@SuppressWarnings("unused")
public class ScreenParticleRegistry {
    public static final ScreenParticleType<ScreenParticleOptions> SAW = registerType(new LodestoneScreenParticleType());

    public static void registerParticleFactory() {//TODO maybe use event?
        registerProvider(SAW, new LodestoneScreenParticleType.Factory(getSpriteSet(MalumMod.malumPath("saw"))));
    }
}