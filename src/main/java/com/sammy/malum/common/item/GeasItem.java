package com.sammy.malum.common.item;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.entity.player.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class GeasItem extends Item {
    public GeasItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        GeasEffectHandler.addGeasEffect(player, player.getItemInHand(usedHand));
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(DataComponentRegistry.GEAS_EFFECT)) {
            var etchingType = GeasEffectHandler.getStoredGeasEffect(stack).geasEffectType().getEffectForDisplay().type;
            var component = tooltipComponents.getFirst().copy()
                    .append(": [")
                    .append(Component.translatable(etchingType.getLangKey()).withStyle(ChatFormatting.GOLD))
                    .append("]");
            tooltipComponents.set(0, component);
        }
    }

    public static void addEtchingTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (!itemStack.has(DataComponentRegistry.GEAS_EFFECT)) {
            return;
        }

        var geas = GeasEffectHandler.getStoredGeasEffect(itemStack).geasEffectType();
        List<Component> tooltip = event.getToolTip();
        var index = new AtomicInteger(1);
        Consumer<Component> tooltipConsumer = c -> tooltip.add(index.getAndIncrement(), c);
        tooltipConsumer.accept(geas.getDescription().withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        tooltipConsumer.accept(Component.translatable("malum.gui.slot").withStyle(ChatFormatting.GOLD)
                .append(Component.translatable("malum.gui.geas.any").withStyle(ChatFormatting.YELLOW)));
        tooltipConsumer.accept(Component.empty());
        tooltipConsumer.accept(Component.translatable("malum.gui.geas.sworn").withStyle(ChatFormatting.GOLD));
        geas.getEffectForDisplay().addTooltipComponents(event.getEntity(), tooltipConsumer, event.getFlags());
    }
}