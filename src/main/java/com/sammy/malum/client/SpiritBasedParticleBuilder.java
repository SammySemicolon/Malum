package com.sammy.malum.client;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import net.minecraft.client.particle.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.Nullable;
import org.joml.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

import java.util.function.*;

public class SpiritBasedParticleBuilder extends WorldParticleBuilder {

    public static SpiritBasedParticleBuilder createSpirit(AbstractLodestoneParticleType<?> particle) {
        return createSpirit(particle, null);
    }

    public static SpiritBasedParticleBuilder createSpirit(AbstractLodestoneParticleType<?> particle, LodestoneBehaviorComponent behavior) {
        return createSpirit(new WorldParticleOptions(particle).setBehavior(behavior));
    }

    public static SpiritBasedParticleBuilder createSpirit(DeferredHolder<? extends AbstractLodestoneParticleType<?>, ? extends AbstractLodestoneParticleType<?>> particle) {
        return createSpirit(particle, null);
    }

    public static SpiritBasedParticleBuilder createSpirit(DeferredHolder<? extends AbstractLodestoneParticleType<?>, ? extends AbstractLodestoneParticleType<?>> particle, LodestoneBehaviorComponent behavior) {
        return createSpirit(new WorldParticleOptions(particle.get()).setBehavior(behavior));
    }

    public static SpiritBasedParticleBuilder createSpirit(WorldParticleOptions options) {
        return new SpiritBasedParticleBuilder(options);
    }


    @Nullable
    public MalumSpiritType spiritType;

    protected SpiritBasedParticleBuilder(WorldParticleOptions options) {
        super(options);
    }

    public SpiritBasedParticleBuilder setSpirit(MalumSpiritType spiritType) {
        this.spiritType = spiritType;
        if (isUmbral()) {
            super.setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT);
        }
        return setColorData(spiritType.createColorData().build());
    }

    public boolean isUmbral() {
        return spiritType != null && spiritType.equals(SpiritTypeRegistry.UMBRAL_SPIRIT);
    }

    @Override
    public SpiritBasedParticleBuilder setRenderType(ParticleRenderType renderType) {
        if (isUmbral()) {
            return (SpiritBasedParticleBuilder) super.setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT);
        }
        return (SpiritBasedParticleBuilder)super.setRenderType(renderType);
    }

    @Override
    public SpiritBasedParticleBuilder setLifetime(Supplier<Integer> lifetimeSupplier) {
        if (isUmbral()) {
            return (SpiritBasedParticleBuilder) super.setLifetime(()->(int) (lifetimeSupplier.get()*2.5f));
        }
        return (SpiritBasedParticleBuilder)super.setLifetime(lifetimeSupplier);
    }

    @Override
    public SpiritBasedParticleBuilder setScaleData(GenericParticleData scaleData) {
        if (isUmbral()) {
            scaleData.multiplyCoefficient(1.5f);
        }
        return (SpiritBasedParticleBuilder)super.setScaleData(scaleData);
    }

    @Override
    public SpiritBasedParticleBuilder setTransparencyData(GenericParticleData transparencyData) {
        if (isUmbral()) {
            transparencyData.multiplyValue(4f).multiplyCoefficient(1.5f);
        }
        return (SpiritBasedParticleBuilder)super.setTransparencyData(transparencyData);
    }

    @Override
    public SpiritBasedParticleBuilder enableNoClip() {
        return (SpiritBasedParticleBuilder)super.enableNoClip();
    }

    @Override
    public SpiritBasedParticleBuilder disableNoClip() {
        return (SpiritBasedParticleBuilder)super.disableNoClip();
    }

    @Override
    public SpiritBasedParticleBuilder setNoClip(boolean noClip) {
        return (SpiritBasedParticleBuilder)super.setNoClip(noClip);
    }

    @Override
    public SpiritBasedParticleBuilder setRenderTarget(RenderHandler.LodestoneRenderLayer renderLayer) {
        return (SpiritBasedParticleBuilder)super.setRenderTarget(renderLayer);
    }

    @Override
    public SpiritBasedParticleBuilder enableForcedSpawn() {
        return (SpiritBasedParticleBuilder)super.enableForcedSpawn();
    }

    @Override
    public SpiritBasedParticleBuilder disableForcedSpawn() {
        return (SpiritBasedParticleBuilder)super.disableForcedSpawn();
    }

    @Override
    public SpiritBasedParticleBuilder setForceSpawn(boolean forceSpawn) {
        return (SpiritBasedParticleBuilder)super.setForceSpawn(forceSpawn);
    }

    @Override
    public SpiritBasedParticleBuilder enableCull() {
        return (SpiritBasedParticleBuilder)super.enableCull();
    }

    @Override
    public SpiritBasedParticleBuilder disableCull() {
        return (SpiritBasedParticleBuilder)super.disableCull();
    }

    @Override
    public SpiritBasedParticleBuilder setShouldCull(boolean shouldCull) {
        return (SpiritBasedParticleBuilder)super.setShouldCull(shouldCull);
    }

    @Override
    public SpiritBasedParticleBuilder setRandomMotion(double maxSpeed) {
        return (SpiritBasedParticleBuilder)super.setRandomMotion(maxSpeed);
    }

    @Override
    public SpiritBasedParticleBuilder setRandomMotion(double maxHSpeed, double maxVSpeed) {
        return (SpiritBasedParticleBuilder)super.setRandomMotion(maxHSpeed, maxVSpeed);
    }

    @Override
    public SpiritBasedParticleBuilder setRandomMotion(double maxXSpeed, double maxYSpeed, double maxZSpeed) {
        return (SpiritBasedParticleBuilder)super.setRandomMotion(maxXSpeed, maxYSpeed, maxZSpeed);
    }

    @Override
    public SpiritBasedParticleBuilder addMotion(Vector3f motion) {
        return (SpiritBasedParticleBuilder)super.addMotion(motion);
    }

    @Override
    public SpiritBasedParticleBuilder addMotion(Vec3 motion) {
        return (SpiritBasedParticleBuilder)super.addMotion(motion);
    }

    @Override
    public SpiritBasedParticleBuilder addMotion(double vx, double vy, double vz) {
        return (SpiritBasedParticleBuilder)super.addMotion(vx, vy, vz);
    }

    @Override
    public SpiritBasedParticleBuilder setMotion(Vector3f motion) {
        return (SpiritBasedParticleBuilder)super.setMotion(motion);
    }

    @Override
    public SpiritBasedParticleBuilder setMotion(Vec3 motion) {
        return (SpiritBasedParticleBuilder)super.setMotion(motion);
    }

    @Override
    public SpiritBasedParticleBuilder setMotion(double vx, double vy, double vz) {
        return (SpiritBasedParticleBuilder)super.setMotion(vx, vy, vz);
    }

    @Override
    public SpiritBasedParticleBuilder setRandomOffset(double maxDistance) {
        return (SpiritBasedParticleBuilder)super.setRandomOffset(maxDistance);
    }

    @Override
    public SpiritBasedParticleBuilder setRandomOffset(double maxHDist, double maxVDist) {
        return (SpiritBasedParticleBuilder)super.setRandomOffset(maxHDist, maxVDist);
    }

    @Override
    public SpiritBasedParticleBuilder setRandomOffset(double maxXDist, double maxYDist, double maxZDist) {
        return (SpiritBasedParticleBuilder)super.setRandomOffset(maxXDist, maxYDist, maxZDist);
    }

    @Override
    public SpiritBasedParticleBuilder act(Consumer<WorldParticleBuilder> worldParticleBuilderConsumer) {
        return (SpiritBasedParticleBuilder)super.act(worldParticleBuilderConsumer);
    }

    @Override
    public SpiritBasedParticleBuilder addTickActor(Consumer<LodestoneWorldParticle> particleActor) {
        return (SpiritBasedParticleBuilder)super.addTickActor(particleActor);
    }

    @Override
    public SpiritBasedParticleBuilder addSpawnActor(Consumer<LodestoneWorldParticle> particleActor) {
        return (SpiritBasedParticleBuilder)super.addSpawnActor(particleActor);
    }

    @Override
    public SpiritBasedParticleBuilder addRenderActor(Consumer<LodestoneWorldParticle> particleActor) {
        return (SpiritBasedParticleBuilder)super.addRenderActor(particleActor);
    }

    @Override
    public SpiritBasedParticleBuilder clearActors() {
        return (SpiritBasedParticleBuilder)super.clearActors();
    }

    @Override
    public SpiritBasedParticleBuilder clearTickActor() {
        return (SpiritBasedParticleBuilder)super.clearTickActor();
    }

    @Override
    public SpiritBasedParticleBuilder clearSpawnActors() {
        return (SpiritBasedParticleBuilder)super.clearSpawnActors();
    }

    @Override
    public SpiritBasedParticleBuilder clearRenderActors() {
        return (SpiritBasedParticleBuilder)super.clearRenderActors();
    }

    @Override
    public SpiritBasedParticleBuilder spawn(Level level, double x, double y, double z) {
        return (SpiritBasedParticleBuilder)super.spawn(level, x, y, z);
    }

    @Override
    public SpiritBasedParticleBuilder repeat(Level level, double x, double y, double z, int n) {
        return (SpiritBasedParticleBuilder)super.repeat(level, x, y, z, n);
    }

    @Override
    public SpiritBasedParticleBuilder surroundBlock(Level level, BlockPos pos, Direction... directions) {
        return (SpiritBasedParticleBuilder)super.surroundBlock(level, pos, directions);
    }

    @Override
    public SpiritBasedParticleBuilder repeatSurroundBlock(Level level, BlockPos pos, int n) {
        return (SpiritBasedParticleBuilder)super.repeatSurroundBlock(level, pos, n);
    }

    @Override
    public SpiritBasedParticleBuilder repeatSurroundBlock(Level level, BlockPos pos, int n, Direction... directions) {
        return (SpiritBasedParticleBuilder)super.repeatSurroundBlock(level, pos, n, directions);
    }

    @Override
    public SpiritBasedParticleBuilder surroundVoxelShape(Level level, BlockPos pos, VoxelShape voxelShape, int max) {
        return (SpiritBasedParticleBuilder)super.surroundVoxelShape(level, pos, voxelShape, max);
    }

    @Override
    public SpiritBasedParticleBuilder surroundVoxelShape(Level level, BlockPos pos, BlockState state, int max) {
        return (SpiritBasedParticleBuilder)super.surroundVoxelShape(level, pos, state, max);
    }

    @Override
    public SpiritBasedParticleBuilder spawnAtRandomFace(Level level, BlockPos pos) {
        return (SpiritBasedParticleBuilder)super.spawnAtRandomFace(level, pos);
    }

    @Override
    public SpiritBasedParticleBuilder repeatRandomFace(Level level, BlockPos pos, int n) {
        return (SpiritBasedParticleBuilder)super.repeatRandomFace(level, pos, n);
    }

    @Override
    public SpiritBasedParticleBuilder createCircle(Level level, double x, double y, double z, double distance, double currentCount, double totalCount) {
        return (SpiritBasedParticleBuilder)super.createCircle(level, x, y, z, distance, currentCount, totalCount);
    }

    @Override
    public SpiritBasedParticleBuilder repeatCircle(Level level, double x, double y, double z, double distance, int times) {
        return (SpiritBasedParticleBuilder)super.repeatCircle(level, x, y, z, distance, times);
    }

    @Override
    public SpiritBasedParticleBuilder createBlockOutline(Level level, BlockPos pos, BlockState state) {
        return (SpiritBasedParticleBuilder)super.createBlockOutline(level, pos, state);
    }

    @Override
    public SpiritBasedParticleBuilder spawnLine(Level level, Vec3 one, Vec3 two) {
        return (SpiritBasedParticleBuilder)super.spawnLine(level, one, two);
    }

    @Override
    public SpiritBasedParticleBuilder modifyData(Supplier<GenericParticleData> dataType, Consumer<GenericParticleData> dataConsumer) {
        return (SpiritBasedParticleBuilder)super.modifyData(dataType, dataConsumer);
    }

    @Override
    public SpiritBasedParticleBuilder modifyData(Function<WorldParticleBuilder, GenericParticleData> dataType, Consumer<GenericParticleData> dataConsumer) {
        return (SpiritBasedParticleBuilder) super.modifyData(dataType, dataConsumer);
    }

    @Override
    public SpiritBasedParticleBuilder modifyColorData(Consumer<ColorParticleData> dataConsumer) {
        return (SpiritBasedParticleBuilder)super.modifyColorData(dataConsumer);
    }

    @Override
    public SpiritBasedParticleBuilder setColorData(ColorParticleData colorData) {
        return (SpiritBasedParticleBuilder)super.setColorData(colorData);
    }

    @Override
    public SpiritBasedParticleBuilder setSpinData(SpinParticleData spinData) {
        return (SpiritBasedParticleBuilder)super.setSpinData(spinData);
    }

    @Override
    public SpiritBasedParticleBuilder multiplyGravity(float gravityMultiplier) {
        return (SpiritBasedParticleBuilder)super.multiplyGravity(gravityMultiplier);
    }

    @Override
    public SpiritBasedParticleBuilder modifyGravity(Function<Float, Supplier<Float>> gravityReplacement) {
        return (SpiritBasedParticleBuilder)super.modifyGravity(gravityReplacement);
    }

    @Override
    public SpiritBasedParticleBuilder setGravityStrength(float gravity) {
        return (SpiritBasedParticleBuilder)super.setGravityStrength(gravity);
    }

    @Override
    public SpiritBasedParticleBuilder setGravityStrength(Supplier<Float> gravityStrengthSupplier) {
        return (SpiritBasedParticleBuilder)super.setGravityStrength(gravityStrengthSupplier);
    }

    @Override
    public SpiritBasedParticleBuilder multiplyLifetime(float lifetimeMultiplier) {
        return (SpiritBasedParticleBuilder)super.multiplyLifetime(lifetimeMultiplier);
    }

    @Override
    public SpiritBasedParticleBuilder modifyLifetime(Function<Integer, Supplier<Integer>> lifetimeReplacement) {
        return (SpiritBasedParticleBuilder)super.modifyLifetime(lifetimeReplacement);
    }

    @Override
    public SpiritBasedParticleBuilder setLifetime(int lifetime) {
        return (SpiritBasedParticleBuilder)super.setLifetime(lifetime);
    }

    @Override
    public SpiritBasedParticleBuilder multiplyLifeDelay(float lifeDelayMultiplier) {
        return (SpiritBasedParticleBuilder)super.multiplyLifeDelay(lifeDelayMultiplier);
    }

    @Override
    public SpiritBasedParticleBuilder modifyLifeDelay(Function<Integer, Supplier<Integer>> lifeDelayReplacement) {
        return (SpiritBasedParticleBuilder)super.modifyLifeDelay(lifeDelayReplacement);
    }

    @Override
    public SpiritBasedParticleBuilder setLifeDelay(int lifeDelay) {
        return (SpiritBasedParticleBuilder)super.setLifeDelay(lifeDelay);
    }

    @Override
    public SpiritBasedParticleBuilder setLifeDelay(Supplier<Integer> lifeDelaySupplier) {
        return (SpiritBasedParticleBuilder)super.setLifeDelay(lifeDelaySupplier);
    }

    @Override
    public SpiritBasedParticleBuilder setSpritePicker(SimpleParticleOptions.ParticleSpritePicker spritePicker) {
        return (SpiritBasedParticleBuilder)super.setSpritePicker(spritePicker);
    }

    @Override
    public SpiritBasedParticleBuilder setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType discardFunctionType) {
        return (SpiritBasedParticleBuilder)super.setDiscardFunction(discardFunctionType);
    }
}