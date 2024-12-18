package com.sammy.malum.registry.common.recipe;

import com.sammy.malum.*;
import com.sammy.malum.core.systems.recipe.*;

public class IngredientTypeRegistry {

    private static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, MalumMod.MALUM);

    public static final DeferredHolder<IngredientType<?>, IngredientType<SpiritIngredient>> SPIRIT = INGREDIENT_TYPES.register("spirit", () -> new IngredientType<>(SpiritIngredient.CODEC));
}
