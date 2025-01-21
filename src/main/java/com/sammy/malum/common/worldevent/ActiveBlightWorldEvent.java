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

import java.util.*;

import static com.sammy.malum.common.worldgen.tree.SoulwoodTreeFeature.BLIGHT;

public abstract class ActiveBlightWorldEvent extends WorldEventInstance {
    protected List<Integer> intensity = new ArrayList<>();
    protected int frequency;
    protected int delay;
    protected int timer;
    protected BlockPos position;
    public Map<Integer, Double> noiseValues;

    public ActiveBlightWorldEvent(WorldEventType type) {
        super(type);
    }

    public ActiveBlightWorldEvent setData(List<Integer> intensity, int frequency, int delay) {
        this.intensity.addAll(intensity);
        this.frequency = frequency;
        this.delay = delay;
        return this;
    }

    public ActiveBlightWorldEvent setPosition(BlockPos position) {
        this.position = position;
        return this;
    }

    @Override
    public void tick(Level level) {
        if (delay > 0) {
            delay--;
            return;
        }
        if (timer == 0) {
            timer = frequency;
            if (intensity.isEmpty()) {
                end(level);
                return;
            }
            createBlight((ServerLevel) level, intensity.removeFirst());
        }
        timer--;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    public void createBlight(ServerLevel level, int intensity) {
        LodestoneBlockFiller filler = new LodestoneBlockFiller(new LodestoneBlockFillerLayer(BLIGHT));
        if (noiseValues == null) {
            noiseValues = SoulwoodTreeFeature.generateBlight(level, filler, position, intensity);
        } else {
            SoulwoodTreeFeature.generateBlight(level, filler, noiseValues, position, intensity);
        }
        createBlightVFX(level, filler);
        level.playSound(null, position, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
    }

    public static void createBlightVFX(ServerLevel level, LodestoneBlockFiller filler) {
        filler.getLayer(BLIGHT).entrySet().stream().filter(e -> e.getValue().getState().getBlock() instanceof BlightedSoilBlock).map(Map.Entry::getKey)
                .forEach(p -> ParticleEffectTypeRegistry.BLIGHTING_MIST.createPositionedEffect(level, new PositionEffectData(p)));
    }
}