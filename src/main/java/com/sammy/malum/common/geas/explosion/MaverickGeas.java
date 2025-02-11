package com.sammy.malum.common.geas.explosion;

import com.google.common.collect.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.network.*;

import java.util.*;
import java.util.function.*;

public class MaverickGeas extends GeasEffect {

    protected static final int STOCK_RECHARGE_TIME = 600;
    protected static final int MAX_STOCKS = 5;
    protected int cooldown;
    public int stocks;
    public int miningSpeedStacks;

    public MaverickGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get());
    }

    public static void onExplosionStart(ExplosionEvent.Start event) {
        final Explosion explosion = event.getExplosion();

        float radius = explosion.radius() * 2.0F;
        final Vec3 center = explosion.center();
        int k1 = Mth.floor(center.x - (double) radius - 1.0);
        int l1 = Mth.floor(center.x + (double) radius + 1.0);
        int i2 = Mth.floor(center.y - (double) radius - 1.0);
        int i1 = Mth.floor(center.y + (double) radius + 1.0);
        int j2 = Mth.floor(center.z - (double) radius - 1.0);
        int j1 = Mth.floor(center.z + (double) radius + 1.0);
        List<Entity> entities = new ArrayList<>(event.getLevel().getEntities(explosion.getDirectSourceEntity(), new AABB(k1, i2, j2, l1, i1, j1)));
        final LivingEntity indirectSourceEntity = event.getExplosion().getIndirectSourceEntity();
        if (indirectSourceEntity != null) {
            entities.add(indirectSourceEntity);
        }

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                if (explosion.damageCalculator.shouldDamageEntity(explosion, livingEntity)) {
                    final MaverickGeas stockedInstance = getStockedInstance(livingEntity);
                    if (stockedInstance != null) {
                        event.setCanceled(true);
                        stockedInstance.miningSpeedStacks = 50;
                        stockedInstance.sync(livingEntity);
                        return;
                    }
                }
            }
        }
    }

    public static MaverickGeas getStockedInstance(LivingEntity entity) {
        var effect = GeasEffectHandler.getGeasEffect(entity, MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get());
        if (effect != null) {
            return (MaverickGeas) effect.getValue();
        }
        return null;
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (stocks == MAX_STOCKS) {
            return;
        }
        if (miningSpeedStacks > 0 && entity.level().getGameTime() % 20L == 0) {
            miningSpeedStacks--;
        }
        if (cooldown < STOCK_RECHARGE_TIME) {
            cooldown++;
            if (cooldown == STOCK_RECHARGE_TIME) {
                stocks++;
                cooldown = 0;
                sync(entity);
            }
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("explosion_eating"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("explosion_mining_cooldown"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("explosion_mining_exhaustion"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (stocks < 0) {
            addAttributeModifier(modifiers, Attributes.MINING_EFFICIENCY, -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            addAttributeModifier(modifiers, Attributes.ATTACK_SPEED, -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        if (miningSpeedStacks > 0) {
            addAttributeModifier(modifiers, Attributes.MINING_EFFICIENCY, miningSpeedStacks/100f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }

    public void sync(LivingEntity entity) {
        markDirty();
        if (!entity.level().isClientSide()) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new SyncCloudHopperStreakPayload(entity.getId(), stocks));
        }
    }
}
