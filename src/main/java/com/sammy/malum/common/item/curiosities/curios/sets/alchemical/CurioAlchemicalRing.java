package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioAlchemicalRing extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioAlchemicalRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_extend_effect"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        for (MobEffectInstance instance : collector.getActiveEffectsMap().values()) {
            var e = instance.getEffect().value();
            int durationChange = (int) (60 * arcaneResonance);
            if (e.isBeneficial()) {
                EntityHelper.extendEffect(instance, collector, durationChange, 1200);
                continue;
            }
            if (e.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(instance, collector, durationChange);
            }
        }
    }
}
