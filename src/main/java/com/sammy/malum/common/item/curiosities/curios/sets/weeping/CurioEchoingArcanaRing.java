package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioEchoingArcanaRing extends MalumCurioItem implements IVoidItem, IMalumEventResponderItem {
    public CurioEchoingArcanaRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_buff_spirit_collection"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        var arcaneReverberation = MobEffectRegistry.ARCANE_REVERBERATION.get();
        var effect = collector.getEffect(arcaneReverberation);
        int addedDuration = (int) (150 * arcaneResonance);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(arcaneReverberation, addedDuration*4, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, addedDuration, 72000);
            EntityHelper.amplifyEffect(effect, collector, 1, 19);
        }
        collector.playSound(SoundRegistry.ECHOING_RING_ABSORBS.get(), 0.3f, RandomHelper.randomBetween(collector.getRandom(), 1.5f, 2f));
    }
}
