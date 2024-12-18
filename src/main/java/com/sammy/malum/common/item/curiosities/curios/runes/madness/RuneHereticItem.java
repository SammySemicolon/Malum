package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.handlers.*;

import java.util.function.Consumer;

public class RuneHereticItem extends AbstractRuneCurioItem implements ItemEventHandler.IEventResponderItem {

    public RuneHereticItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("silence"));
    }


    @Override
    public void incomingDamageEvent(LivingDamageEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        Holder<MobEffect> silenced = MobEffectRegistry.SILENCED;
        MobEffectInstance effect = attacker.getEffect(silenced);
        if (effect == null) {
            attacker.addEffect(new MobEffectInstance(silenced, 300, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, attacker, 1, 9);
            EntityHelper.extendEffect(effect, attacker, 60, 600);
        }
        SoundHelper.playSound(attacked, SoundRegistry.DRAINING_MOTIF.get(), 1f, 1.5f);
    }
}
