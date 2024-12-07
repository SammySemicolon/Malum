package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.recipe.spirit.focusing.*;
import com.sammy.malum.common.recipe.spirit.infusion.*;
import com.sammy.malum.common.recipe.spirit.transmutation.*;
import com.sammy.malum.common.recipe.void_favor.*;
import com.sammy.malum.core.systems.recipe.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.systems.recipe.*;

public class RecipeTypeRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<FavorOfTheVoidRecipe>> VOID_FAVOR = registerRecipeType(FavorOfTheVoidRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritInfusionRecipe>> SPIRIT_INFUSION = registerRecipeType(SpiritInfusionRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<RunicWorkbenchRecipe>> RUNEWORKING = registerRecipeType(RunicWorkbenchRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritFocusingRecipe>> SPIRIT_FOCUSING = registerRecipeType(SpiritFocusingRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritTransmutationRecipe>> SPIRIT_TRANSMUTATION = registerRecipeType(SpiritTransmutationRecipe.NAME);
    public static final DeferredHolder<RecipeType<?>, LodestoneRecipeType<SpiritRepairRecipe>> SPIRIT_REPAIR = registerRecipeType(SpiritRepairRecipe.NAME);

    public static <T extends Recipe<?>> DeferredHolder<RecipeType<?>, LodestoneRecipeType<T>> registerRecipeType(String identifier) {
        return RECIPE_TYPES.register(identifier, () -> new LodestoneRecipeType<>(MalumMod.malumPath(identifier)));
    }
}