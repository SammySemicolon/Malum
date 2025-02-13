package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.item.*;
import net.minecraft.core.component.*;
import net.minecraft.world.item.*;

public class FusedConsciousnessItem extends Item implements IVoidItem {
    public FusedConsciousnessItem(Properties builder) {
        super(builder.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    }

    @Override
    public float getVoidParticleIntensity() {
        return 1.25f;
    }
}