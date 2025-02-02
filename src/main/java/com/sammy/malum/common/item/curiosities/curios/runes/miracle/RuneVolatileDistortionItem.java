package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.handlers.*;

import java.util.function.Consumer;

public class RuneVolatileDistortionItem extends AbstractRuneCurioItem implements ItemEventHandler.IEventResponder {

    public RuneVolatileDistortionItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("erratic_damage"));
        consumer.accept(ComponentHelper.positiveCurioEffect("crits"));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        final RandomSource random = attacker.getRandom();
        float multiplier = Mth.nextFloat(random, 0.9f, 1.2f);
        if (random.nextFloat() < 0.1f) {
            multiplier *= 2;
        }
        event.setNewDamage(event.getNewDamage() * multiplier);
    }
}
