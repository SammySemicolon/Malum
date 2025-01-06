package com.sammy.malum.core.systems.artifice;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.function.Consumer;

public interface IArtificeAcceptor {

    ArtificeAttributeData getAttributes();

    void setAttributes(ArtificeAttributeData data);

    default int getLookupRadius() {
        return 4;
    }

    MalumSpiritType getActiveSpiritType();

    Vec3 getVisualAccelerationPoint();

    void applyAugments(Consumer<ItemStack> augmentConsumer);

    default void recalibrateAccelerators(Level level, BlockPos pos) {
        invalidateModifiers(level);
        setAttributes(new ArtificeAttributeData(this, ArtificeInfluenceData.createFreshData(getLookupRadius(), level, pos)));
        bindModifiers(level);
    }
    default void invalidateModifiers(Level level) {
        getAttributes().getInfluenceData(level).ifPresent(d -> {
            for (ArtificeModifierSourceInstance modifier : d.modifiers()) {
                modifier.invalidate();
            }
        });
    }
    default void bindModifiers(Level level) {
        getAttributes().getInfluenceData(level).ifPresent(d -> {
            for (ArtificeModifierSourceInstance modifier : d.modifiers()) {
                modifier.bind(this);
            }
        });
    }
}
