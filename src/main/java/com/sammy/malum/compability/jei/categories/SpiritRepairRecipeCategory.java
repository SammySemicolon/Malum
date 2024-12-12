package com.sammy.malum.compability.jei.categories;

import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.compability.jei.JEIHandler;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumMod.malumPath;

public class SpiritRepairRecipeCategory implements IRecipeCategory<SpiritRepairRecipe> {

    public static final ResourceLocation UID = malumPath("spirit_repair");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritRepairRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(malumPath("textures/gui/spirit_repair_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.REPAIR_PYLON.get()));
    }

    @Override
    public void draw(SpiritRepairRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
        if (!recipe.spirits.isEmpty()) {
            ArcanaCodexHelper.renderItemFrames(guiGraphics, recipe.spirits.size(), 61, 12, mouseX, mouseY, false, true);
        }
    }

    @Override
    public RecipeType<SpiritRepairRecipe> getRecipeType() {
        return JEIHandler.SPIRIT_REPAIR;
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
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritRepairRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> repaired = recipe.itemsForRepair.stream().map(Item::getDefaultInstance).collect(Collectors.toList());
        List<ItemStack> repairIngredient = Arrays.stream(recipe.repairMaterial.getItems()).toList();

        List<ItemStack> damaged = repaired.stream()
                .map(ItemStack::copy)
                .peek(s -> s.setDamageValue((int) (s.getMaxDamage() * recipe.durabilityPercentage)))
                .collect(Collectors.toCollection(ArrayList::new));

        JEIHandler.addCustomIngredientToJei(builder, RecipeIngredientRole.INPUT, 62, 13, false, recipe.spirits);

        IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 82, 57)
                .addItemStacks(damaged);

        builder.addSlot(RecipeIngredientRole.INPUT, 44, 57)
                .addItemStacks(repairIngredient);

        IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 124)
                .addItemStacks(repaired);

        builder.createFocusLink(input, output);
    }
}
