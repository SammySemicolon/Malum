package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.item.*;

import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class RuneworkingPage extends BookPage {
    private final RunicWorkbenchRecipe recipe;

    public RuneworkingPage(Predicate<RunicWorkbenchRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/runeworking_page.png"));
        if (Minecraft.getInstance() == null) {
            this.recipe = null;
            return;
        }
        this.recipe = LodestoneRecipeType.findRecipe(Minecraft.getInstance().level, RecipeTypeRegistry.RUNEWORKING.get(), predicate);
    }

    public RuneworkingPage(RunicWorkbenchRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/runic_workbench_page.png"));
        this.recipe = recipe;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderComponent(screen, guiGraphics, recipe.primaryInput, left + 63, top + 59, mouseX, mouseY);
        renderComponent(screen, guiGraphics, recipe.secondaryInput, left + 63, top + 16, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static RuneworkingPage fromOutput(Item outputItem) {
        return new RuneworkingPage(s -> s.output.is(outputItem));
    }
}