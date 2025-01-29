package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.network.*;

import java.util.*;
import java.util.function.*;

public class LionsHeartGeas extends GeasEffect {

    public int lionsHeartDuration;

    public LionsHeartGeas() {
        super(MalumGeasEffectTypeRegistry.OATH_OF_THE_GLEEFUL_TARGET.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("authority_of_greed"));
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("authority_of_greed_arcane_resonance"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, AttributeRegistry.HEALING_MULTIPLIER, -0.75f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (lionsHeartDuration > 0) {
            lionsHeartDuration--;
        }
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTagRegistry.LIONS_HEART_BLACKLIST)) {
            return;
        }
        lionsHeartDuration += Mth.floor(100 * target.getAttributeValue(AttributeRegistry.ARCANE_RESONANCE));
        if (lionsHeartDuration > 3600) {
            lionsHeartDuration = 3600;
        }
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(target, new SyncLionsHeartDurationPayload(target.getId(), lionsHeartDuration));
    }

    public static boolean pausePotionEffects(LivingEntity entity, MobEffectInstance instance) {
        final Map.Entry<ItemStack, GeasEffect> geasEffect = GeasEffectHandler.getGeasEffect(entity, MalumGeasEffectTypeRegistry.OATH_OF_THE_GLEEFUL_TARGET.get());
        if (geasEffect != null && geasEffect.getValue() instanceof LionsHeartGeas effect) {
            if (effect.lionsHeartDuration > 0) {
                final MobEffect type = instance.getEffect().value();
                return !type.isInstantenous();
            }
        }
        return false;
    }
}
