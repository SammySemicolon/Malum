package com.sammy.malum.common.geas.explosion;

import com.google.common.collect.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.mixin.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.network.*;

import java.util.function.*;

public class MaverickGeas extends GeasEffect {

    protected static final int MAX_STOCKS = 5;
    protected static final int COOLDOWN_DURATION = 100;
    protected int cooldown;
    public int stocks;
    public MaverickGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get());
    }

    public static void onExplosionKnockback(ExplosionKnockbackEvent event) {
        var sourceEntity = event.getExplosion().getIndirectSourceEntity();
        if (sourceEntity != null) {
            var instance = getStockedInstance(sourceEntity);
            if (instance != null) {
                final boolean isReal = !event.getAffectedBlocks().isEmpty();
                if (instance.stocks >= 0) {
                    double multiplier = 4f / (event.getKnockbackVelocity().length() * 2);
                    event.setKnockbackVelocity(event.getKnockbackVelocity().scale(Math.clamp(multiplier, 0, 1.75f)));
                    if (isReal) {
                        event.getAffectedBlocks().clear();
                        instance.stocks--;
                        instance.sync(sourceEntity);
                    }
                }
                else {
                    if (isReal) {
                        instance.stocks--;
                        instance.sync(sourceEntity);
                    }
                }
            }
        }
    }

    public static boolean modifyExplosionParticles(LivingEntity explosionOwner) {
        return explosionOwner != null && getStockedInstance(explosionOwner) != null;
    }

    public static MaverickGeas getStockedInstance(LivingEntity entity) {
        var effect = GeasEffectHandler.getGeasEffect(entity, MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get());
        if (effect != null) {
            var instance = (MaverickGeas) effect.getValue();
            return instance.stocks > 0 ? instance : null;
        }
        return null;
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (stocks == MAX_STOCKS) {
            return;
        }
        if (cooldown < COOLDOWN_DURATION) {
            cooldown++;
            if (cooldown == COOLDOWN_DURATION) {
                stocks++;
                cooldown = 0;
                sync(entity);
            }
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("explosion_absorption"));
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("rocket_jumping"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("explosion_absorption_cooldown"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (stocks < 0) {
            float modifier = 0.5f * stocks;
            addAttributeModifier(modifiers, Attributes.MOVEMENT_SPEED, modifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            addAttributeModifier(modifiers, Attributes.JUMP_STRENGTH, modifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        if (stocks > 0) {
            addAttributeModifier(modifiers, Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }
    public void sync(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncMaverickStocksPayload(entity.getId(), stocks));
        }
    }
}
