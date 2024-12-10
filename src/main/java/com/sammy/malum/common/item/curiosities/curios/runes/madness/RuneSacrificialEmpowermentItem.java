package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.handlers.*;

import java.util.function.Consumer;

public class RuneSacrificialEmpowermentItem extends AbstractRuneCurioItem implements ItemEventHandler.IEventResponderItem {

    public RuneSacrificialEmpowermentItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("scythe_chain"));
    }

    @Override
    public void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        Holder<MobEffect> sacrificialEmpowerment = MobEffectRegistry.SACRIFICIAL_EMPOWERMENT;
        MobEffectInstance effect = attacker.getEffect(sacrificialEmpowerment);
        if (effect == null) {
            attacker.addEffect(new MobEffectInstance(sacrificialEmpowerment, 200, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, attacker, 1, 3);
            EntityHelper.extendEffect(effect, attacker, 50, 200);
        }
    }
}
