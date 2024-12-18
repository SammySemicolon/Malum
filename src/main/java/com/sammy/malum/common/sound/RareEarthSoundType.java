package com.sammy.malum.common.sound;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.resources.sounds.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.systems.sound.*;

import java.util.function.*;

public class RareEarthSoundType extends ExtendedSoundType {
    public RareEarthSoundType(float volumeIn, float pitchIn, Supplier<SoundEvent> breakSoundIn, Supplier<SoundEvent> stepSoundIn, Supplier<SoundEvent> placeSoundIn, Supplier<SoundEvent> hitSoundIn, Supplier<SoundEvent> fallSoundIn) {
        super(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
    }

    @Override
    public void onPlayBreakSound(Level level, BlockPos pos) {
        level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundRegistry.CTHONIC_GOLD_BREAK.get(), SoundSource.BLOCKS, (getVolume() + 1.0F) / 2.0F, getPitch() - level.random.nextFloat() * 0.4f, false);
    }

    @Override
    public void onPlayPlaceSound(Level level, BlockPos pos, Player player) {
        level.playSound(player, pos, SoundRegistry.CTHONIC_GOLD_PLACE.get(), SoundSource.BLOCKS, (getVolume() + 2.0F) / 2.0F, getPitch() - level.random.nextFloat() * 0.4f);
    }

    @Override
    public void onPlayHitSound(BlockPos pos) {
        MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
        if (gameMode != null) {
            float progress = gameMode.destroyProgress;
            float volume = (getVolume() + progress * progress * 4f) / 8f;
            float pitch = getPitch() * (0.5f + 0.15f * progress);
            Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(SoundRegistry.QUARTZ_CLUSTER_BLOCK_HIT.get(), SoundSource.BLOCKS, volume, pitch, MalumMod.RANDOM, pos));
        }
    }
}