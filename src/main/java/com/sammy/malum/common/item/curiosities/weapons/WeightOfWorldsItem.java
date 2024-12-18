package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDeathEvent;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.tools.*;

import java.util.*;

public class WeightOfWorldsItem extends LodestoneAxeItem implements ItemEventHandler.IEventResponderItem {
    public WeightOfWorldsItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(ComponentHelper.positiveEffect("weight_of_worlds_crit"));
        tooltipComponents.add(ComponentHelper.positiveEffect("weight_of_worlds_kill"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }


    @Override
    public void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        attacker.addEffect(new MobEffectInstance(MobEffectRegistry.GRIM_CERTAINTY, 200));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        var particleEffectType = ParticleEffectTypeRegistry.SCYTHE_SLASH;
        var effect = MobEffectRegistry.GRIM_CERTAINTY;
        if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25f) {
            event.setAmount(event.getAmount() * 2);
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
