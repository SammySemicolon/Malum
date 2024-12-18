package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;

public class EdgeOfDeliveranceItem extends MalumScytheItem {

    public EdgeOfDeliveranceItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(ComponentHelper.positiveEffect("edge_of_deliverance_crit"));
        tooltipComponents.add(ComponentHelper.negativeEffect("edge_of_deliverance_unpowered_attack"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        super.outgoingDamageEvent(event, attacker, target, stack);
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        var source = event.getSource();
        if (source.is(DamageTypeTagRegistry.IS_SCYTHE)) {
            var effect = MobEffectRegistry.IMMINENT_DELIVERANCE;
            if (target.hasEffect(effect)) {
                event.setAmount(event.getAmount() * 2);
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
                event.setAmount(event.getAmount() * 0.5f);
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
