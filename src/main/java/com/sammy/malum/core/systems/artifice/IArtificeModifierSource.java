package com.sammy.malum.core.systems.artifice;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.lodestar.lodestone.helpers.block.BlockEntityHelper;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

import java.util.Optional;

public interface IArtificeModifierSource {
    ArtificeModifierSourceInstance createFocusingModifierInstance();

    Optional<ArtificeModifierSourceInstance> getFocusingModifierInstance();

    default ArtificeModifierSourceInstance getActiveFocusingModifierInstance() {
        return getFocusingModifierInstance()
                .filter(ArtificeModifierSourceInstance::isBound)
                .orElseGet(this::createFocusingModifierInstance);
    }

    default void triggerRecalibration(Level level, BlockPos pos) {
        getFocusingModifierInstance().ifPresent(ArtificeModifierSourceInstance::invalidate);
        var nearbyAcceptors = BlockEntityHelper.getBlockEntities(IArtificeAcceptor.class, level, pos, 6);
        for (IArtificeAcceptor acceptor : nearbyAcceptors) {
            acceptor.recalibrateAccelerators(level);
        }
    }
}
