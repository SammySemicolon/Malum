package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.*;

import java.util.function.Consumer;

public class CurioGrowingFleshRing extends MalumCurioItem implements IVoidItem, IMalumEventResponderItem {
    public CurioGrowingFleshRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("spirits_add_health"));
    }

    @Override
    public void spiritCollectionEvent(CollectSpiritEvent event, LivingEntity collector, double arcaneResonance) {
        Holder<MobEffect> cancerousGrowth = MobEffectRegistry.CANCEROUS_GROWTH;
        MobEffectInstance effect = collector.getEffect(cancerousGrowth);
        int addedDuration = (int) (150 * arcaneResonance);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(cancerousGrowth, addedDuration*4, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, addedDuration, 72000);
            EntityHelper.amplifyEffect(effect, collector, 1, 19);
        }
        collector.playSound(SoundRegistry.FLESH_RING_ABSORBS.get(), 0.3f, RandomHelper.randomBetween(collector.getRandom(), 1.5f, 2f));
    }
}
