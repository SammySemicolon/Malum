package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import com.sammy.malum.common.recipe.spirit.infusion.SpiritInfusionRecipe;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;
import java.util.function.Predicate;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritInfusionPage extends BookPage {

    private static final Component BASE = Component.translatable("malum.gui.book.entry.page.info.spirit_infusion");
    private static final Component SPIRIT = Component.translatable("malum.gui.book.entry.page.info.spirit_infusion.spirit");
    private static final Component ITEM = Component.translatable("malum.gui.book.entry.page.info.spirit_infusion.item");

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
        renderIngredients(screen, guiGraphics, recipe.spirits, SPIRIT, left + 15, top + 59, mouseX, mouseY, true);
        if (!recipe.extraIngredients.isEmpty()) {
            renderIngredients(screen, guiGraphics, recipe.extraIngredients, ITEM, left + 103, top + 59, mouseX, mouseY, true);
        }
        renderIngredient(screen, guiGraphics, recipe.ingredient, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
        screen.renderLater(() -> {
            if (screen.isHovering(mouseX, mouseY, left + 62, top + 78, 18, 18)) {
                guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, wrapComponent(BASE, 180), mouseX, mouseY);
            }
        });
    }
}