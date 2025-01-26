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
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.registry.common.*;

import java.util.function.*;

public class HungeringDeliveranceGeas extends GeasEffect {

    private int timer;
    private int streak;

    public HungeringDeliveranceGeas() {
        super(MalumGeasEffectTypeRegistry.HUNGERING_DELIVERANCE.get());
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (streak > 0) {
            timer++;
            if (timer > 1200) {
                streak = 0;
                markDirty();
            }
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (streak > 3) {
            addAttributeModifier(modifiers, AttributeRegistry.HEALING_MULTIPLIER, -0.1f * (streak - 3), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }

    @Override
    public void malignantCritEvent(MalignantCritEvent event, LivingEntity attacker) {
        final LivingEntity target = event.getLivingEntity();
        float multiplier = event.getNewDamage() / Math.max(event.getOriginalDamage(), 0.01f);
        if (target instanceof Player player) {
            player.causeFoodExhaustion(0.5f * multiplier);
        }
        attacker.heal(4f * multiplier);
        streak++;
        timer /= 2;
        if (streak > 3) {
            markDirty();
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("malignant_crit_leech"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }
}