package com.sammy.malum.core.systems.artifice;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public abstract class ArtificeModifierSourceInstance {

    public final ResourceLocation type;
    public final BlockPos sourcePosition;
    public final int maxAmount;

    public IArtificeAcceptor target;

    protected ArtificeModifierSourceInstance(ResourceLocation type, BlockPos sourcePosition, int maxAmount) {
        this.type = type;
        this.sourcePosition = sourcePosition;
        this.maxAmount = maxAmount;
    }

    public abstract void modifyFocusing(Consumer<ArtificeModifier> modifierConsumer);

    public abstract void applyAugments(Consumer<ItemStack> augmentConsumer);

    public boolean isBound() {
        return target != null;
    }

    public void invalidate() {
        target = null;
    }

    public void bind(IArtificeAcceptor target) {
        this.target = target;
    }

    public abstract void tickFocusing(ArtificeAttributeData attributes);

    public abstract boolean canModifyFocusing();

    public abstract boolean consumesFuel();

    public abstract void addParticles(IArtificeAcceptor target, MalumSpiritType spiritType);

}