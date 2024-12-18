package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.handlers.*;

import java.util.function.Consumer;

public class RuneIgneousSolaceItem extends AbstractRuneCurioItem implements ItemEventHandler.IEventResponderItem {

    public RuneIgneousSolaceItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("burning_resistance"));
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.isOnFire()) {
            event.setAmount(event.getAmount()*0.75f);
        }
    }
}
