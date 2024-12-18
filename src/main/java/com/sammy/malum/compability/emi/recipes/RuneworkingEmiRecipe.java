package com.sammy.malum.compability.emi.recipes;

import com.google.common.collect.Lists;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.RunicWorkbenchRecipe;
import com.sammy.malum.compability.emi.EMIHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneworkingEmiRecipe implements EmiRecipe {

    private static final ResourceLocation BACKGROUND_LOCATION = MalumMod.malumPath("textures/gui/runeworking_jei.png");

    public ResourceLocation id;
    private final RunicWorkbenchRecipe recipe;

    private final List<EmiIngredient> primary = Lists.newArrayList();
    private final List<EmiIngredient> secondary = Lists.newArrayList();
    private final List<EmiStack> output = Lists.newArrayList();

    public RuneworkingEmiRecipe(ResourceLocation id, RunicWorkbenchRecipe recipe) {
        this.recipe = recipe;
        this.id = id;
        primary.add(EmiIngredient.of(recipe.primaryInput.ingredient()));
        secondary.add(EmiIngredient.of(recipe.secondaryInput.toVanilla()));
        output.add(EmiStack.of(recipe.output));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIHandler.RUNEWORKING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return primary;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 142;
    }

    @Override
    public int getDisplayHeight() {
        return 185;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKGROUND_LOCATION, 0, 0, this.getDisplayWidth(), this.getDisplayHeight(), 0, 0);

        widgets.addSlot(primary.get(0), 63, 14).drawBack(false);
        widgets.addSlot(secondary.get(0), 63, 57).drawBack(false);
        widgets.addSlot(output.get(0), 63, 124).drawBack(false);

    }
}
