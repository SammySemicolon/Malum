package com.sammy.malum.common.block.curiosities.spirit_catalyzer;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.artifice.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.visual_effects.SpiritCrucibleParticleEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

import java.util.function.Consumer;

public class CatalyzerArtificeModifierSourceInstance extends ArtificeModifierSourceInstance {

    public static final ResourceLocation ID = MalumMod.malumPath("spirit_catalyzer");
    public final SpiritCatalyzerCoreBlockEntity catalyzer;

    protected CatalyzerArtificeModifierSourceInstance(SpiritCatalyzerCoreBlockEntity catalyzer) {
        super(ID, catalyzer.getBlockPos(), 8);
        this.catalyzer = catalyzer;
    }

    @Override
    public void modifyFocusing(Consumer<ArtificeModifier> modifierConsumer) {
        modifierConsumer.accept(new ArtificeModifier(ArtificeAttributeType.FOCUSING_SPEED, 0.5f));
        modifierConsumer.accept(new ArtificeModifier(ArtificeAttributeType.INSTABILITY, 0.05f));
    }

    @Override
    public void applyAugments(Consumer<ItemStack> augmentConsumer) {
        ItemStack augment = catalyzer.augmentInventory.getStackInSlot(0);
        if (!augment.isEmpty()) {
            augmentConsumer.accept(augment);
        }
    }

    @Override
    public void tickFocusing(ArtificeAttributeData attributes) {
        float ratio = attributes.fuelUsageRate.getValue(attributes) * attributes.focusingSpeed.getValue(attributes);
        if (ratio > 0) {
            catalyzer.burnTicks -= ratio;
        }
    }

    @Override
    public boolean canModifyFocusing() {
        if (catalyzer.burnTicks > 0) {
            return true;
        } else {
            ItemStack stack = catalyzer.inventory.getStackInSlot(0);
            if (!stack.isEmpty() && stack.getBurnTime(RecipeType.SMELTING) > 0) {
                stack.shrink(1);
                catalyzer.burnTicks = stack.getBurnTime(RecipeType.SMELTING) / 2f;
                BlockStateHelper.updateAndNotifyState(catalyzer.getLevel(), catalyzer.getBlockPos());
            }
        }
        return catalyzer.burnTicks > 0;
    }

    @Override
    public boolean consumesFuel() {
        return true;
    }

    @Override
    public void addParticles(IArtificeAcceptor target, MalumSpiritType spiritType) {
        if (catalyzer.burnTicks > 0) {
            SpiritCrucibleParticleEffects.activeSpiritCatalyzerParticles(catalyzer, target, spiritType);
        }
    }
}
