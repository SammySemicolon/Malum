package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.common.data_components.ArtificeAugmentData;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static net.minecraft.world.item.component.ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT;

public class AugmentItem extends Item {

    public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(
            new DecimalFormat("#.##%"), f -> {
                f.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
            }
    );

    public final MalumSpiritType spiritType;

    public AugmentItem(Properties pProperties, MalumSpiritType spiritType, ArtificeModifier... modifiers) {
        this(pProperties, spiritType, false, modifiers);
    }
    public AugmentItem(Properties pProperties, MalumSpiritType spiritType, boolean isCoreAugment, ArtificeModifier... modifiers) {
        super(pProperties.component(DataComponentRegistry.ARTIFICE_AUGMENT, new ArtificeAugmentData(isCoreAugment, List.of(modifiers))));
        this.spiritType = spiritType;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("malum.gui.augment.slot").withStyle(ChatFormatting.GOLD)
                .append(Component.translatable("malum.gui.augment.type." + getAugmentTypeTranslator()).withStyle(ChatFormatting.YELLOW)));
    }

    public static void addAugmentAttributeTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (!itemStack.has(DataComponentRegistry.ARTIFICE_AUGMENT)) {
            return;
        }
        ArtificeAugmentData augmentData = itemStack.get(DataComponentRegistry.ARTIFICE_AUGMENT);
        List<Component> tooltip = event.getToolTip();
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("malum.gui.augment.installed").withStyle(ChatFormatting.GOLD));
        for (ArtificeModifier modifier : augmentData.modifiers()) {
            addAugmentStatComponent(tooltip, modifier.attribute(), modifier.value());
        }
    }

    public String getAugmentTypeTranslator() {
        return "augment";
    }

    public static void addAugmentStatComponent(List<Component> tooltip, ArtificeAttributeType attributeType, float value) {
        boolean inverse = attributeType.equals(ArtificeAttributeType.FUEL_USAGE_RATE)
                || attributeType.equals(ArtificeAttributeType.INSTABILITY)
                || attributeType.equals(ArtificeAttributeType.TUNING_STRAIN);
        makeAugmentStatComponent(attributeType, value, inverse).ifPresent(tooltip::add);
    }

    public static Optional<Component> makeAugmentStatComponent(ArtificeAttributeType attributeType, float value, boolean inverse) {
        return makeAugmentStatComponent(attributeType.getLangKey(), value, inverse);
    }

    public static Optional<Component> makeAugmentStatComponent(String id, float value, boolean inverse) {
        if (value == 0) {
            return Optional.empty();
        }
        boolean isPositive = value > 0f;
        String modifierSign = isPositive ? "attribute.modifier.plus.0" : "attribute.modifier.take.0";
        ChatFormatting style = ChatFormatting.BLUE;
        if ((inverse && isPositive) || (!inverse && !isPositive)) {
            style = ChatFormatting.RED;
        }
        return Optional.of(Component.translatable(
                modifierSign,
                ATTRIBUTE_MODIFIER_FORMAT.format(Math.abs(value)),
                Component.translatable(id)).withStyle(
                style)
        );
    }
}