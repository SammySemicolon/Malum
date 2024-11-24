package com.sammy.malum.compability.jei.categories;

import com.sammy.malum.common.recipe.void_favor.FavorOfTheVoidRecipe;
import com.sammy.malum.compability.jei.JEIHandler;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumMod.malumPath;

public class WeepingWellRecipeCategory implements IRecipeCategory<FavorOfTheVoidRecipe> {

    public static final ResourceLocation UID = malumPath("weeping_well");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public WeepingWellRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(malumPath("textures/gui/weeping_well_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.VOID_DEPOT.get()));
    }

    @Override
    public void draw(FavorOfTheVoidRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
    }

    @Override
    public RecipeType<FavorOfTheVoidRecipe> getRecipeType() {
        return JEIHandler.WEEPING_WELL;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("malum.jei." + UID.getPath());
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FavorOfTheVoidRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 57)
            .addIngredients(recipe.ingredient);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
            .addItemStack(recipe.output);
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST)
            .addItemStack(new ItemStack(ItemRegistry.VOID_DEPOT.get()));
    }
}
