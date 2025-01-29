package com.sammy.malum.common.geas;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

import java.util.function.*;

public class ReinforcingDeliveranceGeas extends GeasEffect {

    public ReinforcingDeliveranceGeas() {
        super(MalumGeasEffectTypeRegistry.OATH_OF_UNSIGHTED_RESISTANCE.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("malignant_crit_reinforcement"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }
}
