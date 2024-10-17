package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import team.lodestar.lodestone.systems.sound.*;

public class CrucibleSoundInstance extends CachedBlockEntitySoundInstance<SpiritCrucibleCoreBlockEntity> {
    public CrucibleSoundInstance(SpiritCrucibleCoreBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, SoundRegistry.CRUCIBLE_LOOP, volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (blockEntity.recipe == null) {
            stop();
        }
        super.tick();
    }

    public static void playSound(SpiritCrucibleCoreBlockEntity blockEntity) {
        playSound(blockEntity, new CrucibleSoundInstance(blockEntity, 1, 1));
    }
}