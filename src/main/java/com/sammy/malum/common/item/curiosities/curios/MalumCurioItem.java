package com.sammy.malum.common.item.curiosities.curios;

import dev.emi.trinkets.api.Trinket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MalumCurioItem extends AbstractMalumCurioItem implements Trinket {

    public MalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties, type);
    }

    public void addExtraTooltipLines(Consumer<Component> consumer, TooltipContext context) {
        addExtraTooltipLines(consumer);
    }

    public void addExtraTooltipLines(Consumer<Component> consumer) {
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        addExtraTooltipLines(tooltipComponents::add);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static Component positiveEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.positive", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.BLUE);
    }
    public static Component negativeEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.negative", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.RED);
    }
}
