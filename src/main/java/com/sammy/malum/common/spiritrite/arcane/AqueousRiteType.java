package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class AqueousRiteType extends TotemicRiteType {
    public AqueousRiteType() {
        super("aqueous_rite", ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(SpiritTypeRegistry.AERIAL_SPIRIT, LivingEntity.class, MobEffectRegistry.POSEIDONS_GRASP);
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(SpiritTypeRegistry.AERIAL_SPIRIT, LivingEntity.class, MobEffectRegistry.ANGLERS_LURE);
    }
}