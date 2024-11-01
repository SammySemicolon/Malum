package com.sammy.malum.common.item.food;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.gluttony.*;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class ConcentratedGluttonyItem extends BottledDrinkItem {
    public static final Collection<Supplier<Item>> ROTTEN_TRINKETS = new ArrayList<>(List.of(ItemRegistry.RING_OF_DESPERATE_VORACITY, ItemRegistry.GLUTTONOUS_BROOCH, ItemRegistry.BELT_OF_THE_STARVED));

    public ConcentratedGluttonyItem(Properties builder) {
        super(builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        final MobEffectInstance gluttonyEffect = createGluttonyEffect(pEntityLiving);
        pEntityLiving.addEffect(gluttonyEffect);
        SoundHelper.playSound(pEntityLiving, SoundRegistry.CONCENTRATED_GLUTTONY_DRINK.get(), 1f, RandomHelper.randomBetween(pLevel.random, 1.5f, 2f));
        if (pLevel instanceof ServerLevel serverLevel) {
            createGluttonyVFX(serverLevel, pEntityLiving, gluttonyEffect.getAmplifier());
        }
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }

    public static MobEffectInstance createGluttonyEffect(@Nullable Entity source) {
        return createGluttonyEffect(source, 1);
    }

    public static MobEffectInstance createGluttonyEffect(@Nullable Entity source, float durationScalar) {
        int amplifier = 3;
        int duration = 20;

        if (source instanceof LivingEntity livingEntity) {
            if (CurioHelper.hasCurioEquipped(livingEntity, ItemRegistry.RING_OF_GRUESOME_CONCENTRATION.get())) {
                amplifier++;
                duration += 40;
            }
            for (Supplier<Item> rottenTrinket : ROTTEN_TRINKETS) {
                if (CurioHelper.hasCurioEquipped(livingEntity, rottenTrinket.get())) {
                    amplifier++;
                    duration += 10;
                }
            }
        }
        return new MobEffectInstance(MobEffectRegistry.GLUTTONY, (int) (duration * 20 * durationScalar), amplifier);
    }

    public static void createGluttonyVFX(ServerLevel serverLevel, LivingEntity target, int amplifier) {
        createGluttonyVFX(serverLevel, target, 1f + amplifier * 0.05f);
    }

    public static void createGluttonyVFX(ServerLevel serverLevel, LivingEntity target, float potency) {
        var position = target.position().add(0, target.getBbHeight() / 2f, 0);
        ParticleEffectTypeRegistry.GLUTTONY_ABSORB.createPositionedEffect(serverLevel, new PositionEffectData(position), AbsorbGluttonyParticleEffect.createData(potency));
    }
}