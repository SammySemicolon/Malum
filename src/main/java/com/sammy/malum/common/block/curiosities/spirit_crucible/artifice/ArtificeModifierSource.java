package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

public abstract class ArtificeModifierSource {

    public final ResourceLocation type;
    public final BlockPos sourcePosition;
    public final int maxAmount;

    public IArtificeAcceptor target;

    protected ArtificeModifierSource(ResourceLocation type, BlockPos sourcePosition, int maxAmount) {
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

    public abstract void addParticles(IArtificeAcceptor target, MalumSpiritType spiritType);

    public interface CrucibleInfluencer {
        ArtificeModifierSource createFocusingModifierInstance();

        Optional<ArtificeModifierSource> getFocusingModifierInstance();

        default ArtificeModifierSource getActiveFocusingModifierInstance() {
            return getFocusingModifierInstance()
                    .filter(ArtificeModifierSource::isBound)
                    .orElseGet(this::createFocusingModifierInstance);
        }
    }
}