package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.damagesource.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

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
        if (source.is(DamageTypeTagRegistry.IS_SCYTHE) || source.is(DamageTypeRegistry.SOULWASHING_PROPAGATION)) {
            var effect = MobEffectRegistry.IMMINENT_DELIVERANCE;
            if (target.hasEffect(effect)) {
                triggerMalignantCrit(event.getContainer(), attacker, target);
                var particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.EDGE_OF_DELIVERANCE_CRIT);
                if (isNarrow(attacker)) {
                    particle.setVertical();
                }
                particle.spawnTargetBoundSlashingParticle(attacker, target);
                target.removeEffect(effect);
            }
            else {
                event.setNewDamage(event.getNewDamage() * 0.5f);
                if (source.is(DamageTypeTagRegistry.IS_HIDDEN_BLADE) && attacker.getRandom().nextFloat() >= 0.4f) {
                    return;
                }
                target.addEffect(new MobEffectInstance(MobEffectRegistry.IMMINENT_DELIVERANCE, 60));
            }
        }
    }

    @Override
    public Holder<SoundEvent> getScytheSound(boolean canSweep) {
        return canSweep ? SoundRegistry.EDGE_OF_DELIVERANCE_SWEEP : SoundRegistry.EDGE_OF_DELIVERANCE_CUT;
    }

    public static void triggerMalignantCrit(DamageContainer damageContainer, LivingEntity attacker, LivingEntity target) {
        damageContainer.setNewDamage(damageContainer.getNewDamage() * 2);
        var critEvent = new MalignantCritEvent(attacker, damageContainer);
        ItemEventHandler.getEventResponders(attacker).forEach(lookup -> lookup.run(IMalumEventResponderItem.class,
                (eventResponderItem, stack) -> eventResponderItem.malignantCritEvent(critEvent, attacker)));
        NeoForge.EVENT_BUS.post(critEvent);
        SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), SoundSource.PLAYERS, 2f, 0.75f);
        SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), SoundSource.PLAYERS, 3f, 1.25f);
        SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), SoundSource.PLAYERS, 3f, 1.75f);
    }
}