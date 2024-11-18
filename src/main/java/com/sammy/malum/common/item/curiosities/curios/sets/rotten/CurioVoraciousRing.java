package com.sammy.malum.common.item.curiosities.curios.sets.rotten;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.*;

public class CurioVoraciousRing extends MalumCurioItem {

    public CurioVoraciousRing(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("eat_rotten"));
        consumer.accept(positiveEffect("growing_gluttony"));
    }

    public static void accelerateEating(LivingEntityUseItemEvent.Start event) {
        if (CurioHelper.hasCurioEquipped(event.getEntity(), ItemRegistry.RING_OF_DESPERATE_VORACITY.get())) {
            if (event.getItem().is(GROSS_FOODS)) {
                event.setDuration((int) (event.getDuration() * 0.5f));
            }
        }
    }

    public static void onEat(Level level, LivingEntity livingEntity, ItemStack food) {
        if (food.is(GROSS_FOODS)) {
            if (CurioHelper.hasCurioEquipped(livingEntity, ItemRegistry.RING_OF_DESPERATE_VORACITY.get())) {
                var gluttony = livingEntity.getEffect(MobEffectRegistry.GLUTTONY.get());
                var hunger = livingEntity.getEffect(MobEffects.HUNGER);
                if (gluttony != null) {
                    EntityHelper.extendEffect(gluttony, livingEntity, 300, 3000);
                }
                if (hunger != null) {
                    EntityHelper.shortenEffect(hunger, livingEntity, 150);
                }
                if (livingEntity instanceof Player player) {
                    player.getFoodData().eat(1, 1f);
                }
                livingEntity.playSound(SoundRegistry.VORACIOUS_RING_FEEDS.get(), 0.5f, RandomHelper.randomBetween(level.random, 1.2f, 1.6f));
            }
        }
    }
}