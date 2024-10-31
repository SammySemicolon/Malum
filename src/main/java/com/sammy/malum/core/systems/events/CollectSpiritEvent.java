package com.sammy.malum.core.systems.events;

import com.sammy.malum.core.handlers.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.event.entity.living.*;

public class CollectSpiritEvent extends LivingEvent {

    public CollectSpiritEvent(LivingEntity entity) {
        super(entity);
    }
}
