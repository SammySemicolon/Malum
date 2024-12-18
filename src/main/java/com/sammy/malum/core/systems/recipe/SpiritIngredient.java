package com.sammy.malum.core.systems.recipe;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.recipe.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.world.item.*;

import java.util.List;
import java.util.stream.*;

public class SpiritIngredient implements CustomIngredient {

    public static final MapCodec<SpiritIngredient> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            MalumSpiritType.CODEC.fieldOf("type").forGetter(SpiritIngredient::getSpiritType),
                            Codec.INT.fieldOf("count").forGetter(SpiritIngredient::getCount))
                    .apply(builder, SpiritIngredient::new));

    protected final MalumSpiritType spiritType;
    protected final int count;

    public SpiritIngredient(MalumSpiritType spiritType, int count) {
        this.spiritType = spiritType;
        this.count = count;
    }

    public MalumSpiritType getSpiritType() {
        return spiritType;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return spiritType.test(itemStack) && itemStack.getCount() >= count;
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of(getStack());
    }

    @Override
    public boolean requiresTesting() {
        return false;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return null;
    }

    public ItemStack getStack() {
        return new ItemStack(spiritType.getSpiritShard(), count);
    }
}
