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

import java.util.*;

public class SpiritCollectionActivatorEntity extends FloatingEntity {

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
    public void spawnParticles(double x, double y, double z) {
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), new Vec3(x, y, z), SpiritTypeRegistry.ELDRITCH_SPIRIT);
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