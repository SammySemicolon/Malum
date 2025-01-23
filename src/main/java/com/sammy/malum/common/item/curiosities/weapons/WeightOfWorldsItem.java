package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;
import team.lodestar.lodestone.systems.item.tools.*;

import java.util.*;

import static com.sammy.malum.common.item.curiosities.weapons.scythe.EdgeOfDeliveranceItem.triggerMalignantCrit;

public class WeightOfWorldsItem extends LodestoneAxeItem implements ItemEventHandler.IEventResponder {

    public WeightOfWorldsItem(Tier tier, float attackDamage, float attackSpeed, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.equals(Enchantments.BREACH)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(ComponentHelper.positiveEffect("weight_of_worlds_crit"));
        event.addTooltipLines(ComponentHelper.positiveEffect("weight_of_worlds_kill"));
    }

    @Override
    public void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        attacker.addEffect(new MobEffectInstance(MobEffectRegistry.GRIM_CERTAINTY, 200));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        var source = event.getSource();
        if (source.is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC) || source.is(DamageTypeRegistry.SOULWASHING_PROPAGATION)) {
            var particleEffectType = ParticleEffectTypeRegistry.SCYTHE_SLASH;
            var effect = MobEffectRegistry.GRIM_CERTAINTY;
            if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25f) {
                triggerMalignantCrit(event.getContainer(), attacker, target);
                particleEffectType = ParticleEffectTypeRegistry.WEIGHT_OF_WORLDS_CRIT;
                attacker.removeEffect(effect);
            } else {
                //We want only the crit to be present in case of exterior triggers such as Soulwashing
                //Regular swing animations are still tied to the melee attack only
                if (!source.is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
                    return;
                }
            }
            ParticleHelper.createSlashingEffect(particleEffectType)
                    .setVertical()
                    .spawnTargetBoundSlashingParticle(attacker, target);
            SoundHelper.playSound(target, SoundRegistry.WEIGHT_OF_WORLDS_CUT.get(), SoundSource.PLAYERS, 2f, 0.75f);
        }
    }
}