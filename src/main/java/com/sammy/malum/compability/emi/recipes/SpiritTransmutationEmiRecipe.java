package com.sammy.malum.compability.emi.recipes;

import com.sammy.malum.MalumMod;
import com.sammy.malum.compability.emi.EMIHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class SpiritTransmutationEmiRecipe implements EmiRecipe {
    private static final ResourceLocation BACKGROUND_LOCATION = MalumMod.malumPath("textures/gui/spirit_transmutation_jei.png");
    public ResourceLocation id;
    private final SpiritTransmutationRecipeWrapper recipe;

    private final List<EmiIngredient> inputs;
    private final List<EmiIngredient> result;
    private final List<EmiStack> outputs;

    public SpiritTransmutationEmiRecipe(ResourceLocation id, SpiritTransmutationRecipeWrapper recipe) {
        this.recipe = recipe;
        this.id = id;
        this.inputs = List.of(EmiIngredient.of(recipe.subRecipes().stream().map((r) -> EmiIngredient.of(r.ingredient)).toList()));

        this.result = List.of(EmiIngredient.of(recipe.subRecipes().stream().map((r) -> EmiIngredient.of(Ingredient.of(r.output))).toList()));
        this.outputs = this.result.get(0).getEmiStacks();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIHandler.SPIRIT_TRANSMUTATION;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 142;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKGROUND_LOCATION, 1, 0, this.getDisplayWidth(), this.getDisplayHeight(), 0, 0);

        widgets.addSlot(inputs.get(0), 28, 26).drawBack(false);

        widgets.addSlot(result.get(0), 93, 26).recipeContext(this).drawBack(false);
    }
}
