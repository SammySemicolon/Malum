package com.sammy.malum.common.etching;

import com.sammy.malum.core.systems.etching.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.tick.*;

public abstract class NightCycleBasedEtching extends EtchingEffect {

    public boolean isNight;

    public NightCycleBasedEtching(EtchingEffectType type) {
        super(type);
    }

    @Override
    public void update(EntityTickEvent event) {
        boolean wasNight = isNight;
        isNight = event.getEntity().level().isNight();
        if (wasNight != isNight) {
            markDirty();
        }
    }
}
