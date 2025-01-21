package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

public class EdgeOfDeliveranceItem extends MalumScytheItem {
    
    public EdgeOfDeliveranceItem(Tier tier, float attackDamage, float attackSpeed, LodestoneItemProperties properties) {
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
        event.addTooltipLines(ComponentHelper.positiveEffect("edge_of_deliverance_crit"));
        event.addTooltipLines(ComponentHelper.negativeEffect("edge_of_deliverance_unpowered_attack"));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        super.outgoingDamageEvent(event, attacker, target, stack);
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        var source = event.getSource();
        if (source.is(DamageTypeTagRegistry.IS_SCYTHE)) {
            var effect = MobEffectRegistry.IMMINENT_DELIVERANCE;
            if (target.hasEffect(effect)) {
                event.setNewDamage(event.getNewDamage() * 2);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2f, 0.75f);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3f, 1.25f);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3f, 1.75f);
                var particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.EDGE_OF_DELIVERANCE_CRIT);
                if (!canSweep(attacker)) {
                    particle.setVertical();
                }
                particle.spawnTargetBoundSlashingParticle(attacker, target);
                target.removeEffect(effect);
            }
            else {
                event.setNewDamage(event.getNewDamage() * 0.5f);
                if (source.is(DamageTypeRegistry.HIDDEN_BLADE_COUNTER) && attacker.getRandom().nextFloat() >= 0.4f) {
                    return;
                }
                target.addEffect(new MobEffectInstance(MobEffectRegistry.IMMINENT_DELIVERANCE, 60));
            }
        }
    }

    @Override
    public SoundEvent getScytheSound(boolean canSweep) {
        return canSweep ? SoundRegistry.EDGE_OF_DELIVERANCE_SWEEP.get() : SoundRegistry.EDGE_OF_DELIVERANCE_CUT.get();
    }
}
