package com.sammy.malum.core.systems.ritual;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.*;

public abstract class MalumRitualType {

    public static final Codec<MalumRitualType> CODEC = ResourceLocation.CODEC.comapFlatMap(s -> {
        var ritual = RitualRegistry.get(s);
        if (ritual == null) {
            throw new JsonParseException("No Such Spirit Type: " + s);
        }
        return DataResult.success(ritual);
    }, r -> r.id);

    public final MalumSpiritType spirit;
    public final ResourceLocation id;
    protected MalumRitualRecipeData recipeData;

    public MalumRitualType(ResourceLocation id, MalumSpiritType spirit) {
        this.id = id;
        this.spirit = spirit;
    }

    public ItemInteractionResult onUsePlinth(RitualPlinthBlockEntity ritualPlinth, Player player, InteractionHand hand) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    public boolean isItemStackValid(RitualPlinthBlockEntity ritualPlinth, ItemStack stack) {
        return false;
    }

    public abstract void triggerRitualEffect(RitualPlinthBlockEntity ritualPlinth);

    public void setRecipeData(MalumRitualRecipeData recipeData) {
        this.recipeData = recipeData;
    }

    public MalumRitualRecipeData getRecipeData() {
        return recipeData;
    }

    public String translationIdentifier() {
        return id.getNamespace() + ".gui.ritual." + id.getPath();
    }

    public ResourceLocation getIcon() {
        return id.withPrefix("textures/vfx/ritual/").withSuffix(".png");
    }

    public RitualData createDataComponent(MalumRitualTier tier) {
        return new RitualData(this, tier);
    }

    public List<Component> makeRitualShardDescriptor(MalumRitualTier ritualTier) {
        List<Component> tooltip = new ArrayList<>();
        var spiritStyleModifier = spirit.getItemRarity().getStyleModifier();
        tooltip.add(makeDescriptorComponent("malum.gui.ritual.type", translationIdentifier(), spiritStyleModifier));
        tooltip.add(makeDescriptorComponent("malum.gui.ritual.tier", ritualTier.translationIdentifier(), spiritStyleModifier));
        return tooltip;
    }

    public List<Component> makeCodexDetailedDescriptor() {
        List<Component> tooltip = new ArrayList<>();
        var spiritStyleModifier = spirit.getItemRarity().getStyleModifier();
        tooltip.add(Component.translatable(translationIdentifier()).withStyle(spiritStyleModifier));
        tooltip.add(makeDescriptorComponent("malum.gui.rite.effect", "malum.gui.book.entry.page.text." + id + ".hover"));
        return tooltip;
    }

    public final Component makeDescriptorComponent(String translationKey1, String translationKey2) {
        return Component.translatable(translationKey1).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(translationKey2).withStyle(ChatFormatting.YELLOW));
    }

    public final Component makeDescriptorComponent(String translationKey1, String translationKey2, UnaryOperator<Style> style) {
        return Component.translatable(translationKey1).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(translationKey2).withStyle(style));
    }
}