package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.handlers.*;

import java.util.function.Consumer;

public class RuneReactiveShieldingItem extends AbstractRuneCurioItem implements ItemEventHandler.IEventResponderItem {

    public RuneReactiveShieldingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("attacked_resistance"));
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        Holder<MobEffect> shielding = MobEffectRegistry.REACTIVE_SHIELDING;
        MobEffectInstance effect = attacked.getEffect(shielding);
        if (effect == null) {
            if (attacked.level().random.nextFloat() < 0.5f) {
                attacked.addEffect(new MobEffectInstance(shielding, 80, 0, true, true, true));
            }
        } else {
            if (attacked.level().random.nextFloat() < 0.5f) {
                EntityHelper.amplifyEffect(effect, attacked, 1, 3);
            }
            EntityHelper.extendEffect(effect, attacked, 40, 100);
        }
    }
}
