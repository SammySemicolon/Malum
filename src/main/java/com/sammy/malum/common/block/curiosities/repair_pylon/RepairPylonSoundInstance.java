package com.sammy.malum.common.block.curiosities.repair_pylon;

import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.registry.common.*;
import team.lodestar.lodestone.systems.sound.*;

public class RepairPylonSoundInstance extends CachedBlockEntitySoundInstance<RepairPylonCoreBlockEntity> {
    public RepairPylonSoundInstance(RepairPylonCoreBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, SoundRegistry.REPAIR_PYLON_LOOP, volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
        this.pitch = 0.8f;
    }

    @Override
    public void tick() {
        if (!blockEntity.state.equals(RepairPylonCoreBlockEntity.RepairPylonState.CHARGING) &&
                !blockEntity.state.equals(RepairPylonCoreBlockEntity.RepairPylonState.REPAIRING)) {
            stop();
        }
        super.tick();
    }

    public static void playSound(RepairPylonCoreBlockEntity blockEntity) {
        playSound(blockEntity, new RepairPylonSoundInstance(blockEntity, 1, 1));
    }
}