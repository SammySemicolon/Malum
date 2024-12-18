package com.sammy.malum.compability.jei.categories;

import com.sammy.malum.MalumMod;
import com.sammy.malum.compability.jei.JEIHandler;
import com.sammy.malum.compability.jei.recipes.SpiritTransmutationWrapper;
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

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.MalumMod.malumPath;

public class SpiritTransmutationRecipeCategory implements IRecipeCategory<SpiritTransmutationWrapper> {
    public static final ResourceLocation UID = malumPath("spirit_transmutation");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritTransmutationRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 83);
        overlay = guiHelper.createDrawable(MalumMod.malumPath("textures/gui/spirit_transmutation_jei.png"), 0, 0, 140, 81);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.ARCANE_SPIRIT.get()));
    }

    @Override
    public void draw(SpiritTransmutationWrapper recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
    }

    @Override
    public RecipeType<SpiritTransmutationWrapper> getRecipeType() {
        return JEIHandler.TRANSMUTATION;
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
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritTransmutationWrapper recipe, IFocusGroup focuses) {
        List<ItemStack> inputs = new ArrayList<>();
        List<ItemStack> outputs = new ArrayList<>();

        for (var subRecipe : recipe.subRecipes()) {
            for (ItemStack stack : subRecipe.ingredient.getItems()) {
                inputs.add(stack);
                outputs.add(subRecipe.output);
            }
        }

        var input = builder.addSlot(RecipeIngredientRole.INPUT, 28, 27)
                .addItemStacks(inputs);
        var output = builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 27)
                .addItemStacks(outputs);
        builder.createFocusLink(input, output);
    }
}
