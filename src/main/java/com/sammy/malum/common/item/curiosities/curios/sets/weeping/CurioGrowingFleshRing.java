package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.util.function.Consumer;

public class CurioGrowingFleshRing extends MalumCurioItem implements IVoidItem, IMalumEventResponderItem {
    public CurioGrowingFleshRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_add_health"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        Holder<MobEffect> cancerousGrowth = MobEffectRegistry.CANCEROUS_GROWTH;
        MobEffectInstance effect = collector.getEffect(cancerousGrowth);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(cancerousGrowth, 1200, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, (int) (300+arcaneResonance*300), 72000);
            EntityHelper.amplifyEffect(effect, collector, 1, 19);
        }
        Level level = collector.level();
        level.playSound(null, collector.blockPosition(), SoundRegistry.FLESH_RING_ABSORBS.get(), SoundSource.PLAYERS, 0.3f, 1.5f + level.random.nextFloat() * 0.5f);
    }
}
