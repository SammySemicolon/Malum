package com.sammy.malum.compability.emi.recipes;

import com.google.common.collect.Lists;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.void_favor.FavorOfTheVoidRecipe;
import com.sammy.malum.compability.emi.EMIHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeepingWellEmiRecipe implements EmiRecipe {

    private static final ResourceLocation BACKGROUND_LOCATION = MalumMod.malumPath("textures/gui/weeping_well_emi.png");

    public ResourceLocation id;
    private final FavorOfTheVoidRecipe recipe;

    private final List<EmiIngredient> input = Lists.newArrayList();
    private final List<EmiStack> output = Lists.newArrayList();

    public WeepingWellEmiRecipe(ResourceLocation id, FavorOfTheVoidRecipe recipe) {
        this.recipe = recipe;
        this.id = id;
        input.add(EmiIngredient.of(recipe.ingredient));
        output.add(EmiStack.of(recipe.output));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIHandler.WEEPING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
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
        return 185 - 27;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKGROUND_LOCATION, 0, 0, this.getDisplayWidth(), this.getDisplayHeight(), 0, 0);

        widgets.addSlot(input.get(0), 63, 57 - 27).drawBack(false);
        widgets.addSlot(output.get(0), 63, 124 - 27).drawBack(false);

    }
}
