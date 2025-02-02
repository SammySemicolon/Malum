package com.sammy.malum.common.geas.deliverance;

import com.google.common.collect.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.function.*;

public class UndiscernedMawGeas extends GeasEffect {

    private int timer;
    private int streak;

    public UndiscernedMawGeas() {
        super(MalumGeasEffectTypeRegistry.OATH_OF_THE_UNDISCERNED_MAW.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("malignant_crit_leech"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("malignant_crit_healing_overexertion"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
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
            addAttributeModifier(modifiers, AttributeRegistry.HEALING_MULTIPLIER, -0.05f * (streak - 3), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }

    @Override
    public void malignantCritEvent(MalignantCritEvent event, LivingEntity attacker) {
        final LivingEntity target = event.getLivingEntity();
        float amount = event.getNewDamage() / Math.max(event.getOriginalDamage(), 0.01f);
        if (target instanceof Player player) {
            player.causeFoodExhaustion(0.5f * amount);
        }
        attacker.heal(amount);
        streak++;
        timer /= 2;
        if (streak > 3) {
            markDirty();
        }
    }
}
