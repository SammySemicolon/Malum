package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

public class MalumScytheItem extends ModCombatItem implements IMalumEventResponderItem, CustomEnchantingBehaviorItem {

    public MalumScytheItem(Tier tier, float damage, float speed, Properties builderIn) {
        super(tier, damage + 3 + tier.getAttackDamageBonus(), speed - 3.2f, builderIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (EnchantmentRegistry.getEnchantmentLevel(level, EnchantmentRegistry.REBOUND, stack) > 0) {
            ReboundHandler.throwScythe(level, player, hand, stack);
            return InteractionResultHolder.success(stack);
        }
        if (EnchantmentRegistry.getEnchantmentLevel(level, EnchantmentRegistry.ASCENSION, stack) > 0) {
            AscensionHandler.triggerAscension(level, player, hand, stack);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, hand);
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        if (!event.getSource().is(DamageTypeRegistry.SCYTHE_MELEE)) {
            return;
        }
        boolean canSweep = canSweep(attacker);
        var particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.SCYTHE_SLASH);
        if (stack.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
            particle.setSpiritType(spiritAffiliatedItem);
        }
        if (!canSweep) {
            SoundHelper.playSound(attacker, getScytheSound(false), 1, 0.75f);
            particle.setVertical().spawnForwardSlashingParticle(attacker);
            return;
        }
        SoundHelper.playSound(attacker, getScytheSound(true), 1, 1);
        particle.mirrorRandomly(attacker.getRandom()).spawnForwardSlashingParticle(attacker);

        int sweeping = EnchantmentRegistry.getEnchantmentLevel(level, Enchantments.SWEEPING_EDGE, stack);
        float damage = event.getAmount() * (0.66f + sweeping * 0.33f);
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
    public SoundEvent getScytheSound(boolean canSweep) {
        return canSweep ? SoundRegistry.SCYTHE_SWEEP.get() : SoundRegistry.SCYTHE_CUT.get();
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, Holder<Enchantment> enchantment, EnchantingContext context) {
        if (enchantment.equals(Enchantments.SWEEPING_EDGE)) {
            return true;
        }
        return super.canBeEnchantedWith(stack, enchantment, context);
    }


    public static boolean canSweep(LivingEntity attacker) {
        //TODO: convert this to a ToolAction, or something alike
        return !TrinketsHelper.hasTrinketEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get()) &&
                !TrinketsHelper.hasTrinketEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get());
    }

    public static DamageSource replaceDamageSource(Player player, DamageSource source) {
        if (player.getMainHandItem().is(ItemTagRegistry.SCYTHES)) {
            return DamageTypeHelper.create(player.level(), DamageTypeRegistry.SCYTHE_MELEE, player);
        }
        return source;
    }
}