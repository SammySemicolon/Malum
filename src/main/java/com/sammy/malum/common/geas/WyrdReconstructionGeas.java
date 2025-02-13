package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.geas.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;
import java.util.function.*;

public class WyrdReconstructionGeas extends GeasEffect {

    private static final int COOLDOWN_DURATION = 48000;
    public int spiritCollectionActivations;
    public int cooldown;

    public WyrdReconstructionGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_WYRD_RECONSTRUCTION.get());
    }
    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("wyrd_reconstruction"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("wyrd_reconstruction_cooldown"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }


    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        if (cooldown > 0 && spiritCollectionActivations == 0) {
            addAttributeModifier(modifiers, AttributeRegistry.ARCANE_RESONANCE, -0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
        return modifiers;
    }
    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (cooldown > 0) {
            if (spiritCollectionActivations > 0) {
                if (cooldown <= COOLDOWN_DURATION - 15) {
                    if (cooldown % 3 == 0) {
                        SoulHarvestHandler.triggerSpiritCollection(entity);
                        spiritCollectionActivations--;
                        if (spiritCollectionActivations == 0) {
                            markDirty();
                        }
                    }
                }
            }
            cooldown--;
            if (cooldown == 0) {
                markDirty();
            }
        }
    }

    @Override
    public void incomingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
//        if (source.is(Tags.DamageTypes.IS_TECHNICAL)) {
//            return;
//        }
        if (source.is(DamageTypeRegistry.VOID)) {
            return;
        }
//        if (cooldown > 0) {
//            return;
//        }
        float health = RandomHelper.randomBetween(target.getRandom(), 1, target.getMaxHealth() * 0.66f);
        if (target.level() instanceof ServerLevel serverLevel) {
            for (Entity knockbackTarget : serverLevel.getEntities(target, target.getBoundingBox().inflate(2f), t -> canApplyKnockback(target, t))) {
                knockbackTarget.setDeltaMovement(knockbackTarget.position().subtract(target.position()).normalize().scale(2f).add(0, 0.5f, 0));
            }
            SoundHelper.playSound(target, SoundRegistry.WYRD_RECONSTRUCTION.get(), 1, 1);
            ParticleEffectTypeRegistry.WYRD_RECONSTRUCTION_REVIVE.createPositionedEffect(serverLevel,
                    new PositionEffectData(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ()),
                    new ColorEffectData(SpiritTypeRegistry.SACRED_SPIRIT, SpiritTypeRegistry.INFERNAL_SPIRIT),
                    WyrdReconstructionReviveParticleEffect.createData(target));
        }

        event.setCanceled(true);
        target.setHealth(health);
        cooldown = COOLDOWN_DURATION;
        spiritCollectionActivations = 12;
    }

    protected static boolean canApplyKnockback(LivingEntity attacker, Entity pTarget) {
        if (pTarget instanceof TamableAnimal tamableAnimal) {
            if (tamableAnimal.isTame()) {
                return false;
            }
        }
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else {
            return pTarget != attacker && !attacker.isPassengerOfSameVehicle(pTarget);
        }
    }
}