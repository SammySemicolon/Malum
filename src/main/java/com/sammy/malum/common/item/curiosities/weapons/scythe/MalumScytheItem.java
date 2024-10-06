package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.entity.boomerang.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;

public class MalumScytheItem extends ModCombatItem implements IMalumEventResponderItem {

    public MalumScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn + 3 + tier.getAttackDamageBonus(), attackSpeedIn - 3.2f, builderIn);
    }

    @Override
    public void hurtEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        boolean canSweep = canSweep(attacker);
        var level = attacker.level();

        if (!event.getSource().is(DamageTypeRegistry.SCYTHE_MELEE)) {
            return;
        }
        if (!canSweep) {
            SoundHelper.playSound(attacker, SoundRegistry.SCYTHE_CUT, 1, 0.75f);
            ParticleHelper.spawnVerticalSlashParticle(ParticleEffectTypeRegistry.SCYTHE_SLASH, attacker);
            return;
        }

        SoundHelper.playSound(attacker, SoundRegistry.SCYTHE_SWEEP, 1, 1);
        ParticleHelper.spawnHorizontalSlashParticle(ParticleEffectTypeRegistry.SCYTHE_SLASH, attacker);
        int sweeping = stack.getEnchantmentLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.SWEEPING_EDGE));
        float damage = event.getOriginalDamage() * (0.66f + sweeping * 0.33f);
        float radius = 1 + sweeping * 0.25f;
        level.getEntities(attacker, target.getBoundingBox().inflate(radius)).forEach(e -> {
            if (e instanceof LivingEntity livingEntity) {
                if (livingEntity.isAlive()) {
                    livingEntity.hurt((DamageTypeHelper.create(level, DamageTypeRegistry.SCYTHE_SWEEP, attacker)), damage);
                    livingEntity.knockback(0.4F,
                            Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)),
                            (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                }
            }
        });
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.equals(Enchantments.SWEEPING_EDGE)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    public static boolean canSweep(LivingEntity attacker) {
        //TODO: convert this to a ToolAction, or something alike
        return !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get()) &&
                !CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get());
    }

}