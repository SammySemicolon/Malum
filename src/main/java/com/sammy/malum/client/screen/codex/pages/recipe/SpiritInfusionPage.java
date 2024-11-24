package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import com.sammy.malum.common.recipe.spirit.infusion.SpiritInfusionRecipe;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.*;

import java.util.function.Predicate;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritInfusionPage extends BookPage {
    private final SpiritInfusionRecipe recipe;

    public SpiritInfusionPage(Predicate<SpiritInfusionRecipe> predicate) {
        this(LodestoneRecipeType.findRecipe(Minecraft.getInstance().level, RecipeTypeRegistry.SPIRIT_INFUSION.get(), predicate));
    }

    public SpiritInfusionPage(SpiritInfusionRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }

    public static SpiritInfusionPage fromInput(Item inputItem) {
        return new SpiritInfusionPage(s -> s.ingredient.test(inputItem.getDefaultInstance()));
    }

    public static SpiritInfusionPage fromOutput(Item outputItem) {
        return new SpiritInfusionPage(s -> s.output.is(outputItem));
    }

    public static SpiritInfusionPage fromId(ResourceLocation recipeId) {
        var level = Minecraft.getInstance().level;
        var recipe = LodestoneRecipeType.getRecipeHolders(level, RecipeTypeRegistry.SPIRIT_INFUSION.get())
                .stream()
                .filter(r -> r.id().equals(recipeId))
                .findFirst()
                .map(RecipeHolder::value)
                .orElse(null);
        return new SpiritInfusionPage(recipe);
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        Runnable renderSpirits = () -> renderIngredients(screen, guiGraphics, recipe.spirits, left + 15, top + 59, mouseX, mouseY, true);
        if (!recipe.extraIngredients.isEmpty()) {
            renderIngredients(screen, guiGraphics, recipe.extraIngredients, left + 107, top + 59, mouseX, mouseY, true);
        }
        renderSpirits.run();
        renderIngredient(screen, guiGraphics, recipe.ingredient, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }
}
