package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;
import team.lodestar.lodestone.systems.item.tools.*;

import java.util.*;

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
        var particleEffectType = ParticleEffectTypeRegistry.SCYTHE_SLASH;
        var effect = MobEffectRegistry.GRIM_CERTAINTY;
        if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25f) {
            event.setNewDamage(event.getNewDamage() * 2);
            SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2f, 0.75f);
            SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3f, 1.25f);
            SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3f, 1.75f);
            particleEffectType = ParticleEffectTypeRegistry.WEIGHT_OF_WORLDS_CRIT;
            attacker.removeEffect(effect);
        }
        ParticleHelper.createSlashingEffect(particleEffectType)
                .setVertical()
                .spawnForwardSlashingParticle(attacker);
        SoundHelper.playSound(target, SoundRegistry.WEIGHT_OF_WORLDS_CUT.get(), 2f, 0.75f);
    }
}
