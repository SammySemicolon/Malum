package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EarthenRiteType extends TotemicRiteType {
    public EarthenRiteType() {
        super("earthen_rite", ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(SpiritTypeRegistry.AERIAL_SPIRIT, LivingEntity.class, MobEffectRegistry.GAIAS_BULWARK);
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(SpiritTypeRegistry.AERIAL_SPIRIT, LivingEntity.class, MobEffectRegistry.EARTHEN_MIGHT);
    }
}
