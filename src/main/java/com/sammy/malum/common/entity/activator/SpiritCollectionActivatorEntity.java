package com.sammy.malum.common.entity.activator;

import com.sammy.malum.common.entity.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.*;

public class SpiritCollectionActivatorEntity extends FloatingEntity {

    public TrailPointBuilder secondaryTrailPointBuilder = TrailPointBuilder.create(4);
    public TrailPointBuilder trinaryTrailPointBuilder = TrailPointBuilder.create(4);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    public SpiritCollectionActivatorEntity(Level level) {
        super(EntityRegistry.SPIRIT_COLLECTION_ACTIVATOR.get(), level);
        maxAge = 4000;
    }

    public SpiritCollectionActivatorEntity(Level level, UUID ownerUUID, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(level);
        setOwner(ownerUUID);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
        maxAge = 800;

    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public void collect() {
        SoulHarvestHandler.triggerSpiritCollection(owner);
        SoundHelper.playSound(this, SoundRegistry.SPIRIT_PICKUP.get(), 0.3f, Mth.nextFloat(random, 1.2f, 1.5f));
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            float offsetScale = 0.1f + random.nextFloat() * 0.2f;
            for (int i = 0; i < 2; i++) {
                float progress = (i + 1) * 0.5f;
                Vec3 position = getPosition(progress).add(0, getYOffset(progress), 0);
                float scalar = (age + progress) / 6f;
                double xOffset = Math.cos(spinOffset + scalar) * offsetScale;
                double zOffset = Math.sin(spinOffset + scalar) * offsetScale;
                secondaryTrailPointBuilder.addTrailPoint(position.add(xOffset, 0, zOffset));
                xOffset = Math.cos(spinOffset + scalar + 3.14f) * offsetScale;
                zOffset = Math.sin(spinOffset + scalar + 3.14f) * offsetScale;
                trinaryTrailPointBuilder.addTrailPoint(position.add(xOffset, 0, zOffset));
            }
            secondaryTrailPointBuilder.tickTrailPoints();
            trinaryTrailPointBuilder.tickTrailPoints();
        }
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), new Vec3(x, y, z), SpiritTypeRegistry.UMBRAL_SPIRIT);
        lightSpecs.getBuilder().setMotion(norm);
        lightSpecs.getBloomBuilder().setMotion(norm);
        lightSpecs.spawnParticles();
    }

    @Override
    public float getMotionCoefficient() {
        return 0.04f;
    }

    @Override
    public float getFriction() {
        return 0.9f;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }
}