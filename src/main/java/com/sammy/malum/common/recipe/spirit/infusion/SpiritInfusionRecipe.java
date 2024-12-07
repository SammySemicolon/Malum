package com.sammy.malum.common.recipe.spirit.infusion;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;

public class SpiritInfusionRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput> {

    public static final MapCodec<SpiritInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
            SizedIngredient.FLAT_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
            ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
            SizedIngredient.FLAT_CODEC.listOf().fieldOf("extraIngredients").forGetter(recipe -> recipe.extraIngredients),
            SpiritIngredient.CODEC.codec().listOf().fieldOf("spirits").forGetter(recipe -> recipe.spirits),
            Codec.BOOL.fieldOf("carryOverComponentData").forGetter(recipe -> recipe.carryOverData)
    ).apply(obj, SpiritInfusionRecipe::new));

    public static final String NAME = "spirit_infusion";

    public final SizedIngredient ingredient;
    public final ItemStack output;

    public final List<SizedIngredient> extraIngredients;
    public final List<SpiritIngredient> spirits;
    public final boolean carryOverData;

    public SpiritInfusionRecipe(SizedIngredient ingredient, ItemStack output, List<SizedIngredient> extraIngredients, List<SpiritIngredient> spirits, boolean carryOverData) {
        super(RecipeSerializerRegistry.INFUSION_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_INFUSION.get());
        this.ingredient = ingredient;
        this.output = output;
        this.extraIngredients = extraIngredients;
        this.spirits = spirits;
        this.carryOverData = carryOverData;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(ingredient, extraIngredients, spirits);
    }
}
