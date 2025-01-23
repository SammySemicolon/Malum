package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

import java.util.function.*;

public class HungeringDeliveranceGeas extends GeasEffect {

    public HungeringDeliveranceGeas() {
        super(MalumGeasEffectTypeRegistry.HUNGERING_DELIVERANCE.get());
    }

    @Override
    public void malignantCritEvent(MalignantCritEvent event, LivingEntity attacker) {
        final LivingEntity target = event.getLivingEntity();
        float multiplier = event.getNewDamage() / Math.max(event.getOriginalDamage(), 0.01f);
        if (target instanceof Player player) {
            player.causeFoodExhaustion(0.5f * multiplier);
        }
        attacker.heal(4f * multiplier);
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("malignant_crit_leech"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }
}