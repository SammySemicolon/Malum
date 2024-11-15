package com.sammy.malum.registry.common.item;

import net.minecraft.world.effect.*;
import net.minecraft.world.food.*;

public class FoodPropertyRegistry {

    public static final FoodProperties RUNIC_SAP = new FoodProperties.Builder().nutrition(6).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1)
            .build();
    public static final FoodProperties CURSED_SAP = new FoodProperties.Builder().nutrition(6).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 0), 1).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0), 1)
            .build();

    public static final FoodProperties ROTTING_ESSENCE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 1), 0.95f)
            .build();
    public static final FoodProperties CONCENTRATED_GLUTTONY = new FoodProperties.Builder().nutrition(2).saturationModifier(0.05F)
            .build();
}
