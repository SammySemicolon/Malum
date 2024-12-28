package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.Optional;

public interface IArtificeAcceptor {

    ArtificeAttributeData getAttributes();

    void setAttributes(ArtificeAttributeData data);

    default int getLookupRadius() {
        return 4;
    }

    MalumSpiritType getActiveSpiritType();

    Vec3 getVisualAccelerationPoint();

    default void recalibrateAccelerators(Level level, BlockPos pos) {
        invalidateAccelerators(level);
        setAttributes(new ArtificeAttributeData(ArtificeInfluenceData.createData(getLookupRadius(), level, pos)));
    }
    default void invalidateAccelerators(Level level) {
        getAttributes().getInfluenceData(level).ifPresent(d -> {
            for (ArtificeModifierSource modifier : d.modifiers()) {
                modifier.invalidate();
            }
        });
    }
}
