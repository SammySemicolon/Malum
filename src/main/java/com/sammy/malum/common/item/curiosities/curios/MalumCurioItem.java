package com.sammy.malum.common.item.curiosities.curios;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLLoader;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MalumCurioItem extends AbstractMalumCurioItem implements ICurioItem {

    public MalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties, type);
    }

    public void addExtraTooltipLines(Consumer<Component> consumer, TooltipContext context) {
        addExtraTooltipLines(consumer);
    }

    public void addExtraTooltipLines(Consumer<Component> consumer) {}


    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        final List<Component> attributesTooltip = super.getAttributesTooltip(tooltips, context, stack);

        final List<Component> extraTooltipLines = new ArrayList<>();
        addExtraTooltipLines(extraTooltipLines::add, context);

        if (!extraTooltipLines.isEmpty()) {
            if (attributesTooltip.isEmpty()) {
                attributesTooltip.add(Component.empty());
                final Map<String, ISlotType> itemStackSlots = CuriosApi.getItemStackSlots(stack, FMLLoader.getDist().isClient());

                itemStackSlots.keySet().stream().findFirst().ifPresent(s ->
                    attributesTooltip.add(Component.translatable("curios.modifiers." + s)
                        .withStyle(ChatFormatting.GOLD)));
            }

            attributesTooltip.addAll(extraTooltipLines);
        }
        return attributesTooltip;
    }

    public static Component positiveEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.positive", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.BLUE);
    }
    public static Component negativeEffect(String name, Object... args) {
        return Component.translatable("malum.gui.curio.negative", Component.translatable("malum.gui.curio.effect." + name, args)).withStyle(ChatFormatting.RED);
    }
}
