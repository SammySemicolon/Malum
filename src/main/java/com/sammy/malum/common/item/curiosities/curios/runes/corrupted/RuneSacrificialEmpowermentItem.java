package com.sammy.malum.common.item.curiosities.curios.runes.corrupted;

import com.sammy.malum.common.item.curiosities.curios.runes.MalumRuneCurioItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

import java.util.function.Consumer;

public class RuneSacrificialEmpowermentItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneSacrificialEmpowermentItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("scythe_chain"));
    }

    @Override
    public void killEvent(LivingEntity attacker, LivingEntity target, ItemStack stack) {
        MobEffect sacrificialEmpowerment = MobEffectRegistry.SACRIFICIAL_EMPOWERMENT.get();
        MobEffectInstance effect = attacker.getEffect(sacrificialEmpowerment);
        if (effect == null) {
            attacker.addEffect(new MobEffectInstance(sacrificialEmpowerment, 200, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, attacker, 1, 3);
            EntityHelper.extendEffect(effect, attacker, 50, 200);
        }
    }
}
