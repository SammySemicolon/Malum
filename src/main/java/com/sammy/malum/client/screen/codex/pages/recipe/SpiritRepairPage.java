package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritRepairPage extends BookPage {

    public static final Component BASE = Component.translatable("malum.gui.book.entry.page.info.spirit_repair");
    public static final Component SPIRIT = Component.translatable("malum.gui.book.entry.page.info.spirit_repair.spirit");
    public static final Component DAMAGED = Component.translatable("malum.gui.book.entry.page.info.spirit_repair.damaged");

    private final SpiritRepairRecipe recipe;

    private final List<ItemStack> damagedStacks;
    private final List<ItemStack> repairedStacks;

    public SpiritRepairPage(Predicate<SpiritRepairRecipe> predicate) {
        this(LodestoneRecipeType.findRecipe(Minecraft.getInstance().level, RecipeTypeRegistry.SPIRIT_REPAIR.get(), predicate));
    }

    public SpiritRepairPage(SpiritRepairRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_repair_page.png"));
        this.recipe = recipe;
        this.damagedStacks = recipe.itemsForRepair.stream().map(Item::getDefaultInstance).peek(s -> s.setDamageValue(Mth.floor(s.getMaxDamage() * recipe.durabilityPercentage))).collect(Collectors.toList());
        this.repairedStacks = recipe.itemsForRepair.stream().map(Item::getDefaultInstance).collect(Collectors.toList());
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritRepairPage fromOutput(Item outputItem) {
        return new SpiritRepairPage(s -> s.repairOutputOverride.equals(outputItem));
    }

    public static SpiritRepairPage fromId(String recipeId) {
        return fromId(MalumMod.malumPath(recipeId));
    }
    public static SpiritRepairPage fromId(ResourceLocation recipeId) {
        var level = Minecraft.getInstance().level;
        var recipe = LodestoneRecipeType.getRecipeHolders(level, RecipeTypeRegistry.SPIRIT_REPAIR.get())
                .stream()
                .filter(r -> r.id().equals(recipeId.withPrefix("spirit_crucible/repair/")))
                .findFirst()
                .map(RecipeHolder::value)
                .orElse(null);
        return new SpiritRepairPage(recipe);
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderIngredients(screen, guiGraphics, recipe.spirits, SPIRIT, left + 59, top + 16, mouseX, mouseY, false);
        renderItem(screen, guiGraphics, damagedStacks, left + 82, top + 59, mouseX, mouseY);
        renderIngredient(screen, guiGraphics, recipe.repairMaterial.ingredient(), left + 44, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, repairedStacks, left + 63, top + 126, mouseX, mouseY);
        screen.renderLater(() -> {
            if (screen.isHovering(mouseX, mouseY, left + 43, top + 78, 18, 18)) {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, BASE, mouseX, mouseY);
            }
            if (screen.isHovering(mouseX, mouseY, left + 82, top + 78, 18, 18)) {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, DAMAGED, mouseX, mouseY);
            }
        });
    }
}