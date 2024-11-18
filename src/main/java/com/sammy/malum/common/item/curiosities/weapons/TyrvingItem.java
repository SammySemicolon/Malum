package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.tools.*;

public class TyrvingItem extends LodestoneSwordItem implements IMalumEventResponderItem {
    public TyrvingItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        final Level level = attacker.level();
        if (level.isClientSide) {
            return;
        }
        if (!event.getSource().is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
            return;
        }
        float damage = SpiritHarvestHandler.getSpiritData(target).map(d -> d.totalSpirits).orElse(0) * 2f;
        if (target instanceof Player) {
            damage = 4 * Math.max(1, (1 + target.getArmorValue() / 12f) * (1 + (1 - 1 / (float) target.getArmorValue())) / 12f);
        }

        if (target.isAlive()) {
            target.invulnerableTime = 0;
            target.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOODOO, attacker), damage);
        }

        SoundHelper.playSound(attacker, SoundRegistry.TYRVING_SLASH.get(), 1, RandomHelper.randomBetween(attacker.getRandom(), 1f, 1.5f));
        ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.TYRVING_SLASH)
                .setSpiritType(SpiritTypeRegistry.WICKED_SPIRIT)
                .setVerticalSlashAngle()
                .spawnForwardSlashingParticle(attacker);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return toolAction.equals(ToolActions.SWORD_DIG);
    }
}