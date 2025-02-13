package com.sammy.malum.compability.jei.categories;

import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.compability.jei.JEIHandler;
import com.sammy.malum.core.systems.rite.TotemicRiteType;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumMod.malumPath;

public class SpiritRiteRecipeCategory implements IRecipeCategory<TotemicRiteType> {
    public static final ResourceLocation UID = malumPath("spirit_rite");
    private final IDrawable overlay;
    private final IDrawable icon;
    private final Font font;

    public SpiritRiteRecipeCategory(IGuiHelper guiHelper) {
        overlay = guiHelper.createDrawable(malumPath("textures/gui/spirit_rite_jei.png"), 0, 0, 142, 185);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()));
        font = Minecraft.getInstance().font;
    }

    @Override
    public void draw(TotemicRiteType rite, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        overlay.draw(guiGraphics);
        String translated = I18n.get(rite.translationIdentifier(false));
        ArcanaCodexHelper.renderText(guiGraphics, Component.literal(translated), 71 - font.width(translated) / 2, 160);
    }

    @Override
    public RecipeType<TotemicRiteType> getRecipeType() {
        return JEIHandler.RITES;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("malum.jei." + UID.getPath());
    }

    @Override
    public int getHeight() {
        return overlay.getHeight();
    }

    @Override
    public int getWidth() {
        return overlay.getWidth();
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TotemicRiteType rite, IFocusGroup focuses) {
        for (int i = 0; i < rite.spirits.size(); i++) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 63, 121 - 20 * i)
                    .addItemStack(rite.spirits.get(i).getSpiritShard().getDefaultInstance());
        }
    }
}
