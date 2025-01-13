package com.sammy.malum.core.systems.artifice;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

import javax.annotation.Nonnull;
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

    default void recalibrateAccelerators(@Nonnull Level level) {
        BlockPos pos = asBlockEntity().getBlockPos();
        invalidateModifiers(level);
        var attributes = new ArtificeAttributeData(this);
        var influence = ArtificeInfluenceData.createFreshData(getLookupRadius(), level, pos, attributes);
        setAttributes(attributes.applyModifierInfluence(influence));
        bindModifiers(level);
        BlockStateHelper.updateState(level, pos);
    }
    default void invalidateModifiers(@Nonnull Level level) {
        getAttributes().getInfluenceData(level).ifPresent(d -> {
            for (ArtificeModifierSourceInstance modifier : d.modifiers()) {
                modifier.invalidate();
            }
        });
    }
    default void bindModifiers(@Nonnull Level level) {
        getAttributes().getInfluenceData(level).ifPresent(d -> {
            for (ArtificeModifierSourceInstance modifier : d.modifiers()) {
                modifier.bind(this);
            }
        });
    }

    default BlockEntity asBlockEntity() {
        return (BlockEntity) this;
    }
}
