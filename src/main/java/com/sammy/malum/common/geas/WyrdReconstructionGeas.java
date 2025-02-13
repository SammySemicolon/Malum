package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;

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
                if (cooldown % 10 == 0) {
                    SoulHarvestHandler.triggerSpiritCollection(entity);
                    SoundHelper.playSound(entity, SoundRegistry.SPIRIT_PICKUP.get(), 0.3f, Mth.nextFloat(entity.getRandom(), 1.2f, 1.5f));
                    spiritCollectionActivations--;
                    if (spiritCollectionActivations == 0) {
                        markDirty();
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
        if (source.is(Tags.DamageTypes.IS_TECHNICAL)) {
            return;
        }
        if (source.is(DamageTypeRegistry.VOID)) {
            return;
        }
        if (cooldown > 0) {
            return;
        }
        float health = RandomHelper.randomBetween(attacker.getRandom(), 1, attacker.getMaxHealth()*0.66f);
        int collectionTriggers = RandomHelper.randomBetween(attacker.getRandom(), 2, 6);
        event.setCanceled(true);
        target.setHealth(health);
        cooldown = COOLDOWN_DURATION;
        spiritCollectionActivations = collectionTriggers;
    }
}