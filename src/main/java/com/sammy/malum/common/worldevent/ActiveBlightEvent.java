package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.block.blight.BlightedSoilBlock;
import com.sammy.malum.common.worldgen.tree.SoulwoodTreeFeature;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.WorldEventTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.systems.worldevent.*;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller;
import team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

import java.util.Map;

import static com.sammy.malum.common.worldgen.tree.SoulwoodTreeFeature.BLIGHT;

public class ActiveBlightEvent extends WorldEventInstance {
    public int blightTimer, intensity, rate, times;
    public BlockPos sourcePos;
    public Map<Integer, Double> noiseValues;

    public ActiveBlightEvent() {
        this(WorldEventTypeRegistry.ACTIVE_BLIGHT.get());
    }
    public ActiveBlightEvent(WorldEventType type) {
        super(type);
    }

    public ActiveBlightEvent setBlightData(int intensity, int rate, int times) {
        this.intensity = intensity;
        this.rate = rate;
        this.times = times;
        return this;
    }

    public ActiveBlightEvent setPosition(BlockPos sourcePos) {
        this.sourcePos = sourcePos;
        return this;
    }

    @Override
    public void tick(Level level) {
        if (times == 0) {
            end(level);
            return;
        }
        if (blightTimer == 0) {
            blightTimer = rate;
            createBlight((ServerLevel) level);
            times--;
            intensity += 2;
        } else {
            blightTimer--;
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    public void createBlight(ServerLevel level) {
        LodestoneBlockFiller filler = new LodestoneBlockFiller(new LodestoneBlockFillerLayer(BLIGHT));
        if (noiseValues == null) {
            noiseValues = SoulwoodTreeFeature.generateBlight(level, filler, sourcePos, intensity);
        } else {
            SoulwoodTreeFeature.generateBlight(level, filler, noiseValues, sourcePos, intensity);
        }
        createBlightVFX(level, filler);
        level.playSound(null, sourcePos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
    }

    public static void createBlightVFX(ServerLevel level, LodestoneBlockFiller filler) {
        filler.getLayer(BLIGHT).entrySet().stream().filter(e -> e.getValue().getState().getBlock() instanceof BlightedSoilBlock).map(Map.Entry::getKey)
                .forEach(p -> ParticleEffectTypeRegistry.BLIGHTING_MIST.createPositionedEffect(level, new PositionEffectData(p)));
    }
}