package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.tools.*;

public class WeightOfWorldsItem extends LodestoneAxeItem implements ItemEventHandler.IEventResponderItem {
    public WeightOfWorldsItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
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
