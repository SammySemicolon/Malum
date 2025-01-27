package com.sammy.malum.common.geas;

import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

public class ThanatophobiaGeas extends GeasEffect {

    public ThanatophobiaGeas() {
        super(MalumGeasEffectTypeRegistry.THANATOPHOBIA.get());
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        super.outgoingDamageEvent(event, attacker, target, stack);
    }
}