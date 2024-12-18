package com.sammy.malum.compability.jei.categories;

import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.common.recipe.spirit.infusion.SpiritInfusionRecipe;
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

import java.util.Arrays;

import static com.sammy.malum.MalumMod.malumPath;

public class SpiritInfusionRecipeCategory implements IRecipeCategory<SpiritInfusionRecipe> {

    public static final ResourceLocation UID = malumPath("spirit_infusion");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritInfusionRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(malumPath("textures/gui/spirit_infusion_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()));
    }

    @Override
    public void draw(SpiritInfusionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
        int spiritOffset = recipe.spirits.size() > 5 ? (recipe.spirits.size()-5)*10 : 0;
        ArcanaCodexHelper.renderItemFrames(guiGraphics, recipe.spirits.size(), 19, 48+spiritOffset, mouseX, mouseY, true, true);
        if (!recipe.extraIngredients.isEmpty()) {
            int itemOffset = recipe.extraIngredients.size() > 5 ? (recipe.extraIngredients.size()-5)*10 : 0;
            ArcanaCodexHelper.renderItemFrames(guiGraphics, recipe.extraIngredients.size(), 103, 48+itemOffset, mouseX, mouseY, true, false);
        }
    }

    @Override
    public RecipeType<SpiritInfusionRecipe> getRecipeType() {
        return JEIHandler.SPIRIT_INFUSION;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("malum.jei." + UID.getPath());
    }


    @Override
    public IDrawable getBackground() {
        return background;
    }


    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritInfusionRecipe recipe, IFocusGroup focuses) {
        int spiritOffset = recipe.spirits.size() > 5 ? (recipe.spirits.size()-5)*10 : 0;
        int itemOffset = recipe.extraIngredients.size() > 5 ? (recipe.extraIngredients.size()-5)*10 : 0;
        JEIHandler.addCustomIngredientToJei(builder, RecipeIngredientRole.INPUT, 20, 49+spiritOffset, true, recipe.spirits);
        JEIHandler.addSizedIngredientsToJei(builder, RecipeIngredientRole.INPUT, 104, 49+itemOffset, true, recipe.extraIngredients);

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 57)
                .addItemStacks(Arrays.stream(recipe.ingredient.ingredient().getItems()).toList());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
                .addItemStack(recipe.output);
    }
}
