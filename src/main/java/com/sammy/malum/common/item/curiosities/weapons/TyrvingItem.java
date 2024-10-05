package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.packets.particle.rite.generic.MajorEntityEffectParticlePacket;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.tools.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;

public class TyrvingItem extends LodestoneSwordItem implements IMalumEventResponderItem {
    public TyrvingItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void hurtEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
            return;
        }
        final Level level = attacker.level();
        if (!level.isClientSide) {
            float damage = SpiritHarvestHandler.getSpiritData(target).map(d -> d.totalSpirits).orElse(0) * 2f;
            if (target instanceof Player) {
                damage = 4 * Math.max(1, (1 + target.getArmorValue() / 12f) * (1 + (1 - 1 / (float) target.getArmorValue())) / 12f);
            }

            if (target.isAlive()) {
                target.invulnerableTime = 0;
                target.hurt(DamageTypeRegistry.create(level, DamageTypeRegistry.VOODOO, attacker), damage);
            }
            SoundHelper.playSound(attacker, SoundRegistry.TYRVING_SLASH.get(), 1, RandomHelper.randomBetween(attacker.getRandom(), 1f, 1.5f));
            ParticleHelper.spawnVerticalSlashParticle(ParticleEffectTypeRegistry.TYRVING_SLASH, attacker);
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return itemAbility.equals(ItemAbilities.SWORD_DIG);
    }
}