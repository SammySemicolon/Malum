package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.LodestoneRecipeType;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritRepairPage extends BookPage {
    private final SpiritRepairRecipe recipe;

    public SpiritRepairPage(Predicate<SpiritRepairRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_repair_page.png"));
        //this is null during datagen
        if (Minecraft.getInstance() instanceof Minecraft mcInstance) {
            this.recipe = LodestoneRecipeType.findRecipe(mcInstance.level, RecipeTypeRegistry.SPIRIT_REPAIR.get(), predicate);
        } else this.recipe = null;
    }

    public SpiritRepairPage(SpiritRepairRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_repair_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritRepairPage fromInput(Item inputItem) {
        return new SpiritRepairPage(s -> s.matches(new SingleRecipeInput(inputItem.getDefaultInstance()), null));
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderComponents(screen, guiGraphics, recipe.spirits.stream().map(ICustomIngredient::toVanilla).toList(), left + 63, top + 16, mouseX, mouseY, false);
        final List<ItemStack> damaged = recipe.inputs.stream().map(Item::getDefaultInstance).peek(s -> s.setDamageValue(Mth.floor(s.getMaxDamage() * recipe.durabilityPercentage))).collect(Collectors.toList());
        final List<ItemStack> repaired = recipe.inputs.stream().map(Item::getDefaultInstance).collect(Collectors.toList());
        renderItem(screen, guiGraphics, damaged, left + 82, top + 59, mouseX, mouseY);
        renderComponent(screen, guiGraphics, recipe.repairMaterial.ingredient(), left + 44, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, repaired, left + 63, top + 126, mouseX, mouseY);
    }
}