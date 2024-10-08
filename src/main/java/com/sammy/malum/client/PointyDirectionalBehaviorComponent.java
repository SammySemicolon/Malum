package com.sammy.malum.client;

import net.minecraft.world.phys.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

import java.util.function.*;

public class PointyDirectionalBehaviorComponent implements LodestoneBehaviorComponent {

    //TODO: move this to lodestone
    public static PointyDirectionalBehaviorComponent DIRECTIONAL = new PointyDirectionalBehaviorComponent();
    private final Function<LodestoneWorldParticle, Vec3> direction;

    protected PointyDirectionalBehaviorComponent(Function<LodestoneWorldParticle, Vec3> direction) {
        this.direction = direction;
    }

    public PointyDirectionalBehaviorComponent(Vec3 direction) {
        this(p -> direction);
    }

    public PointyDirectionalBehaviorComponent() {
        this(p -> p.getParticleSpeed().normalize());
    }

    public Vec3 getDirection(LodestoneWorldParticle particle) {
        return direction.apply(particle);
    }

    @Override
    public LodestoneParticleBehavior getBehaviorType() {
        return PointyDirectionalParticleBehavior.DIRECTIONAL;
    }
}