package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class AerialRiteType extends TotemicRiteType {
    public AerialRiteType() {
        super("aerial_rite", ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(SpiritTypeRegistry.AERIAL_SPIRIT, LivingEntity.class, MobEffectRegistry.ZEPHYRS_COURAGE);
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(SpiritTypeRegistry.AERIAL_SPIRIT, LivingEntity.class, MobEffectRegistry.AETHERS_CHARM);
    }
}