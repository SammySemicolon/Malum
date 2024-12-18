package com.sammy.malum.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.forge_stuff.SizedIngredient;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.systems.recipe.*;

public class RunicWorkbenchRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput> {

    public static final MapCodec<RunicWorkbenchRecipe> CODEC = RecordCodecBuilder.mapCodec(obj -> obj.group(
            SizedIngredient.NESTED_CODEC.fieldOf("primaryInput").forGetter(recipe -> recipe.primaryInput),
            SpiritIngredient.CODEC.fieldOf("secondaryInput").forGetter(recipe -> recipe.secondaryInput),
            ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
    ).apply(obj, RunicWorkbenchRecipe::new));

    public static final String NAME = "runeworking";

    public final SizedIngredient primaryInput;
    public final SpiritIngredient secondaryInput;

    public RunicWorkbenchRecipe(SizedIngredient primaryInput, SpiritIngredient secondaryInput, ItemStack output) {
        super(RecipeSerializerRegistry.RUNEWORKING_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.RUNEWORKING.get(), output);
        this.primaryInput = primaryInput;
        this.secondaryInput = secondaryInput;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(primaryInput, secondaryInput);
    }
}