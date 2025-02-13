package com.sammy.malum.core.systems.rite;

import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;

import java.util.*;

public abstract class TotemicRiteType {

    public final List<MalumSpiritType> spirits;
    public final String identifier;
    public final TotemicRiteEffect effect;
    public final TotemicRiteEffect corruptedEffect;

    public TotemicRiteType(String identifier, MalumSpiritType... spirits) {
        this.identifier = identifier;
        this.spirits = new ArrayList<>(Arrays.asList(spirits));
        this.effect = getNaturalRiteEffect();
        this.corruptedEffect = getCorruptedEffect();
    }

    public List<Component> makeDetailedDescriptor(boolean corrupted) {
        List<Component> tooltip = new ArrayList<>();
        var spiritStyleModifier = getIdentifyingSpirit().getItemRarity().getStyleModifier();
        var riteEffect = getRiteEffect(corrupted);
        var riteCategory = riteEffect.category;
        tooltip.add(Component.translatable(translationIdentifier(corrupted)).withStyle(spiritStyleModifier));
        tooltip.add(makeDescriptorComponent("malum.gui.rite.type", riteCategory.getTranslationKey()));
        tooltip.add(makeDescriptorComponent("malum.gui.rite.medium", "malum.gui.rite.medium."+(corrupted?"soulwood":"runewood")));
        if (!riteCategory.equals(TotemicRiteEffect.MalumRiteEffectCategory.ONE_TIME_EFFECT)) {
            tooltip.add(makeDescriptorComponent("malum.gui.rite.coverage", riteEffect.getRiteCoverageDescriptor()));
        }
        tooltip.add(makeDescriptorComponent("malum.gui.rite.effect", "malum.gui.book.entry.page.text." + (corrupted ? "corrupt_" : "") + identifier + ".hover"));
        return tooltip;
    }

    public final Component makeDescriptorComponent(String translationKey1, String translationKey2) {
        return Component.translatable(translationKey1).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(translationKey2).withStyle(ChatFormatting.YELLOW));
    }

    public String translationIdentifier(boolean corrupt) {
        return "malum.gui.rite." + (corrupt ? "corrupted_" : "") + identifier;
    }

    public ResourceLocation getIcon() {
        return MalumMod.malumPath("textures/vfx/rite/" + identifier.replace("greater_", "").replace("_rite", "") + ".png");
    }

    public MalumSpiritType getIdentifyingSpirit() {
        return spirits.get(spirits.size() - 1);
    }

    protected abstract TotemicRiteEffect getNaturalRiteEffect();

    protected abstract TotemicRiteEffect getCorruptedEffect();

    public final TotemicRiteEffect getRiteEffect(boolean corrupted) {
        return corrupted ? corruptedEffect : effect;
    }

    public void executeRite(TotemBaseBlockEntity totemBase) {
        getRiteEffect(totemBase.isSoulwood).doRiteEffect(totemBase);
    }
}
