package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

import java.util.function.Consumer;

public class RuneHereticItem extends AbstractRuneCurioItem implements IEventResponderItem {

    public RuneHereticItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ELDRITCH_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("silence"));
    }

    @Override
    public void takeDamageEvent(LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        Holder<MobEffect> silenced = MobEffectRegistry.SILENCED;
        MobEffectInstance effect = attacker.getEffect(silenced);
        if (effect == null) {
            attacker.addEffect(new MobEffectInstance(silenced, 300, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, attacker, 1, 9);
            EntityHelper.extendEffect(effect, attacker, 60, 600);
        }
        attacked.level().playSound(null, attacked.getX(), attacked.getY(), attacked.getZ(), SoundRegistry.DRAINING_MOTIF.get(), attacked.getSoundSource(), 1f, 1.5f);
    }
}
