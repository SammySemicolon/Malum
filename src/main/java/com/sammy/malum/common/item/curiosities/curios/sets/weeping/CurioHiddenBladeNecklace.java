package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.function.Consumer;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {
    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("scythe_counterattack"));
        consumer.accept(negativeEffect("no_sweep"));
    }

    @Override
    public void takeDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        float amount = event.getNewDamage();
        int amplifier = (int) Math.ceil(amount / 4f);
        if (amplifier >= 6) {
            amplifier *= amplifier / 6f;
        }
        Holder<MobEffect> effect = MobEffectRegistry.WICKED_INTENT;
        attacked.addEffect(new MobEffectInstance(effect, 40, amplifier + 1));
    }
}
