package com.sammy.malum.common.item.ether;

import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;

import java.util.*;

public abstract class AbstractEtherItem extends BlockItem implements ParticleEmitterHandler.ItemParticleSupplier {
    public static final DyedItemColor DEFAULT_FIRST_COLOR = new DyedItemColor(15712278, false);
    public static final DyedItemColor DEFAULT_SECOND_COLOR = new DyedItemColor(4607909, false);
  
    public final boolean iridescent;

    public AbstractEtherItem(Block blockIn, Properties builder, boolean iridescent) {
        super(blockIn, applyColor(builder, iridescent));
        this.iridescent = iridescent;
    }

    public DataComponentType<DyedItemColor> colorLookup() {
        return iridescent ? DataComponentRegistry.SECONDARY_DYE_COLOR.get() : DataComponents.DYED_COLOR;
    }

    public static Properties applyColor(Properties builder, boolean iridescent) {
        builder.component(DataComponents.DYED_COLOR, DEFAULT_FIRST_COLOR);
        if (iridescent) {
            builder.component(DataComponentRegistry.SECONDARY_DYE_COLOR, DEFAULT_SECOND_COLOR);
        }
        return builder;
    }

    public int getSecondColor(ItemStack stack) {
        return Objects.requireNonNullElse(
                stack.get(colorLookup()), DEFAULT_SECOND_COLOR
        ).rgb();
    }

    public void setSecondColor(ItemStack stack, int color) {
        stack.set(DataComponentRegistry.SECONDARY_DYE_COLOR.get(), new DyedItemColor(color, false));
    }

    public int getFirstColor(ItemStack stack) {
        return Objects.requireNonNullElse(
                stack.get(DataComponents.DYED_COLOR), DEFAULT_FIRST_COLOR
        ).rgb();
    }

    public void setFirstColor(ItemStack stack, int color) {
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color, false));
    }
}