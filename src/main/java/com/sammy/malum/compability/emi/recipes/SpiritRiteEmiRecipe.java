package com.sammy.malum.compability.emi.recipes;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.compability.emi.EMIHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SpiritRiteEmiRecipe implements EmiRecipe {
    private static final ResourceLocation BACKGROUND_LOCATION = MalumMod.malumPath("textures/gui/spirit_rite_jei.png");
    public ResourceLocation id;
    private final Font font;

    private final TotemicRiteType rite;
    private final List<EmiIngredient> spirits;

    public SpiritRiteEmiRecipe(TotemicRiteType rite) {
        this.rite = rite;
        this.spirits = rite.spirits.stream().map((spirit) -> (EmiIngredient) EmiStack.of(spirit.getSpiritShard())).toList();
        this.id = id;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIHandler.SPIRIT_RITE;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return spirits;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of();
    }

    @Override
    public int getDisplayWidth() {
        return 142;
    }

    @Override
    public int getDisplayHeight() {
        return 185;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BACKGROUND_LOCATION, 0, 0, this.getDisplayWidth(), this.getDisplayHeight(), 0, 0);

        widgets.addDrawable(0, 0, 0, 0, (matrices, mx, my, d) ->
        {
            Component text = Component.translatable(rite.translationIdentifier(false));
            ArcanaCodexHelper.renderText(matrices, text, 71 - font.width(text) / 2, 160);
        });

        for (int i = 0; i < rite.spirits.size(); i++) {
            widgets.addSlot(EmiStack.of(rite.spirits.get(i).getSpiritShard()), 62, 120 - 20 * i).catalyst(true).drawBack(false);
        }
    }
}
