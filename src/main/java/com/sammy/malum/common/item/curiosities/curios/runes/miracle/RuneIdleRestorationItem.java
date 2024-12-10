package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class RuneIdleRestorationItem extends AbstractRuneCurioItem {

    public RuneIdleRestorationItem(Properties builder) {
        super(builder, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("passive_healing"));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        final LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 40L == 0) {
            livingEntity.heal(1);
        }
        super.curioTick(slotContext, stack);
    }
}
