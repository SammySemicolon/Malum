package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import com.sammy.malum.common.recipe.spirit.transmutation.SpiritTransmutationRecipe;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderItem;
import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderText;

public class SpiritTransmutationRecipePage extends BookPage {
    private final String headlineTranslationKey;
    private final List<SpiritTransmutationRecipe> recipes;

    public SpiritTransmutationRecipePage(String headlineTranslationKey, Predicate<SpiritTransmutationRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        final Level level = Minecraft.getInstance().level;
        if (level != null) {
            this.recipes = new ArrayList<>();
            var recipe = LodestoneRecipeType.findRecipe(level, RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get(), predicate);
            if (recipe != null) {
                recipes.add(recipe);
                if (recipe.group != null) {
                    for (SpiritTransmutationRecipe otherRecipe : LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get())) {
                        if (!recipe.equals(otherRecipe) && recipe.group.equals(otherRecipe.group)) {
                            recipes.add(otherRecipe);
                        }
                    }
                }
            }
        } else {
            this.recipes = null;
        }
    }

    public SpiritTransmutationRecipePage(String headlineTranslationKey, SpiritTransmutationRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.recipes = new ArrayList<>(List.of(recipe));
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public static SpiritTransmutationRecipePage fromInput(String headlineTranslationKey, Item inputItem) {
        return new SpiritTransmutationRecipePage(headlineTranslationKey, s -> s.ingredient.test(inputItem.getDefaultInstance()));
    }

    public static SpiritTransmutationRecipePage fromOutput(String headlineTranslationKey, Item outputItem) {
        return new SpiritTransmutationRecipePage(headlineTranslationKey, s -> s.output.is(outputItem));
    }

    @Override
    public boolean isValid() {
        return recipes != null;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, left + 70 - Minecraft.getInstance().font.width(component.getString()) / 2, top + 5);

        SpiritTransmutationRecipe recipe = recipes.get(getIndex());
        renderItem(screen, guiGraphics, recipe.ingredient, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }

    public int getIndex() {
        return (int) (Minecraft.getInstance().level.getGameTime() % (20L * recipes.size()) / 20);
    }
}
