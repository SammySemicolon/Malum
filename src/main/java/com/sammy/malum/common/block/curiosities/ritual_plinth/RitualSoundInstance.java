package com.sammy.malum.common.block.curiosities.ritual_plinth;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import team.lodestar.lodestone.systems.sound.LodestoneBlockEntitySoundInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RitualSoundInstance extends LodestoneBlockEntitySoundInstance<RitualPlinthBlockEntity> {
    private static final Map<BlockPos, RitualSoundInstance> ACTIVE_SOUNDS = new HashMap<>();
    public final Predicate<RitualPlinthBlockEntity> stopCondition;

    public RitualSoundInstance(RitualPlinthBlockEntity blockEntity, Supplier<SoundEvent> soundEvent, Predicate<RitualPlinthBlockEntity> stopCondition) {
        super(blockEntity, soundEvent.get(), 1f, 1f);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
        this.stopCondition = stopCondition;
    }

    @Override
    public void tick() {
        if (blockEntity.isRemoved() || stopCondition.test(blockEntity)) {
            stop();
            ACTIVE_SOUNDS.remove(blockEntity.getBlockPos());
        }
        super.tick();
    }

    public static void playSound(RitualPlinthBlockEntity blockEntity, Supplier<SoundEvent> soundEvent, Predicate<RitualPlinthBlockEntity> stopCondition) {
        RitualSoundInstance ritualSound = new RitualSoundInstance(blockEntity, soundEvent, stopCondition);
        BlockPos blockPos = blockEntity.getBlockPos();
        if (ACTIVE_SOUNDS.containsKey(blockPos)) {
            RitualSoundInstance existingSound = ACTIVE_SOUNDS.get(blockPos);
            if (!existingSound.location.equals(ritualSound.location)) {
                existingSound.stop();
                ACTIVE_SOUNDS.put(blockPos, ritualSound);
                Minecraft.getInstance().getSoundManager().queueTickingSound(ritualSound);
            }
        }
        else {
            ACTIVE_SOUNDS.put(blockPos, ritualSound);
            Minecraft.getInstance().getSoundManager().queueTickingSound(ritualSound);
        }
    }
}