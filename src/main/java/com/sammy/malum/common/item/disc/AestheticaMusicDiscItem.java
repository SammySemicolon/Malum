package com.sammy.malum.common.item.disc;

import com.sammy.malum.common.item.*;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.world.item.*;

public class AestheticaMusicDiscItem extends Item implements IVoidItem {

    public AestheticaMusicDiscItem(Properties builder) {
        super(builder.jukeboxPlayable(SoundRegistry.AESTHETICA_KEY));
    }

    @Override
    public float getVoidParticleIntensity() {
        return 1.25f;
    }
}