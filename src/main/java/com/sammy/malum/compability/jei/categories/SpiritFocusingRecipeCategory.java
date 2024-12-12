package com.sammy.malum.compability.jei.categories;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.common.recipe.spirit.focusing.SpiritFocusingRecipe;
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
import net.neoforged.neoforge.common.crafting.*;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumMod.malumPath;

public class SpiritFocusingRecipeCategory implements IRecipeCategory<SpiritFocusingRecipe> {

    public static final ResourceLocation UID = malumPath("spirit_focusing");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritFocusingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(MalumMod.malumPath("textures/gui/spirit_focusing_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()));
    }

    @Override
    public void draw(SpiritFocusingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
        if (!recipe.spirits.isEmpty()) {
            ArcanaCodexHelper.renderItemFrames(guiGraphics, recipe.spirits.size(), 61, 12, mouseX, mouseY, false, true);
        }
    }

    @Override
    public RecipeType<SpiritFocusingRecipe> getRecipeType() {
        return JEIHandler.FOCUSING;
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
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritFocusingRecipe recipe, IFocusGroup focuses) {
        JEIHandler.addItemsToJei(builder, RecipeIngredientRole.INPUT, 62, 13, false, recipe.spirits.stream().map(ICustomIngredient::toVanilla).toList());

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 57)
                .addIngredients(recipe.ingredient);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
                .addItemStack(recipe.output);
    }
}
