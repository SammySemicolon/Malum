package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.mixin.AccessorEvent;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

import java.util.function.Consumer;

public class RuneIgneousSolaceItem extends AbstractRuneCurioItem implements IEventResponderItem {

    public RuneIgneousSolaceItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("burning_resistance"));
    }

    @Override
    public void takeDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.isOnFire()) {
            ((AccessorEvent.PostDamage)event).malum$setNewDamage(event.getNewDamage()*0.75f);
        }
    }
}
