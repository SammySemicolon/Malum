package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;
import team.lodestar.lodestone.systems.item.tools.*;

public class TyrvingItem extends LodestoneSwordItem implements IMalumEventResponderItem {

    public TyrvingItem(Tier tier, float attackDamage, float attackSpeed, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(ComponentHelper.positiveEffect("soul_based_damage"));
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var level = attacker.level();
        if (level.isClientSide) {
            return;
        }
        if (!event.getSource().is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
            return;
        }
        float magicDamage = EntitySpiritDropData.getSpiritData(target).map(d -> d.totalSpirits).orElse(0);
        if (target instanceof Player) {
            magicDamage = 2 * Math.max(1, (1 + target.getArmorValue() / 12f) * (1 + (1 - 1 / (float) target.getArmorValue())) / 12f);
        }
        if (target.isAlive()) {
            target.invulnerableTime = 0;
            target.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.TYRVING, attacker), magicDamage);
        }
        if (target.isAlive()) {
            WorldEventHandler.addWorldEvent(level,
                    new DelayedDamageWorldEvent(target)
                            .setAttacker(attacker)
                            .setMagicDamageType(DamageTypeRegistry.TYRVING)
                            .setDamageData(0, magicDamage, 3));
        }

        SoundHelper.playSound(attacker, SoundRegistry.TYRVING_SLASH.get(), 1, RandomHelper.randomBetween(attacker.getRandom(), 1f, 1.5f));
        ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.TYRVING_SLASH)
                .setSpiritType(SpiritTypeRegistry.WICKED_SPIRIT)
                .setVerticalSlashAngle()
                .spawnForwardSlashingParticle(attacker);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return itemAbility.equals(ItemAbilities.SWORD_DIG);
    }
}