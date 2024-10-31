package com.sammy.malum.client.screen.codex.pages.recipe.vanilla;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.core.systems.recipe.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.Optional;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderItem;

public class SmeltingPage extends BookPage {
    private final ItemStack inputStack;
    private final ItemStack outputStack;

    public SmeltingPage(ItemStack inputStack, ItemStack outputStack) {
        super(MalumMod.malumPath("textures/gui/book/pages/smelting_page.png"));
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }

    public SmeltingPage(Item inputItem, Item outputItem) {
        this(inputItem.getDefaultInstance(), outputItem.getDefaultInstance());
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderItem(screen, guiGraphics, inputStack, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, outputStack, left + 63, top + 126, mouseX, mouseY);
    }

    @Override
    public boolean isValid() {
        return !inputStack.isEmpty() && !outputStack.isEmpty();
    }

    public static SmeltingPage fromInput(Item input) {
        var level = Minecraft.getInstance().level;
        SmeltingRecipe recipe = LodestoneRecipeType.getRecipe(level, RecipeType.SMELTING, new SingleRecipeInput(new ItemStack(input, 1)));
        if (recipe != null) {
            return new SmeltingPage(new ItemStack(input), recipe.getResultItem(level.registryAccess()));
        }
        return new SmeltingPage(ItemStack.EMPTY, ItemStack.EMPTY);
    }
}