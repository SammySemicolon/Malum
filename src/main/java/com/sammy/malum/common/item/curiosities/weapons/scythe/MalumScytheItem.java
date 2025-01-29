package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
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
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;

public class MalumScytheItem extends LodestoneCombatItem implements IMalumEventResponderItem {

    public MalumScytheItem(Tier tier, float attackDamage, float attackSpeed, LodestoneItemProperties properties) {
        super(tier, attackDamage + 3, attackSpeed - 3.2f, properties);
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
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        if (!event.getSource().is(DamageTypeRegistry.SCYTHE_MELEE)) {
            return;
        }
        var particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.SCYTHE_SLASH);
        if (stack.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
            particle.setSpiritType(spiritAffiliatedItem);
        }
        if (isEnhanced(attacker)) {
            SoundHelper.playSound(attacker, getScytheSound(false).value(), 1, 0.75f);
            particle.setVertical().spawnForwardSlashingParticle(attacker);
            return;
        }
        SoundHelper.playSound(attacker, getScytheSound(true).value(), 1, 1);
        particle.mirrorRandomly(attacker.getRandom()).spawnForwardSlashingParticle(attacker);

        int sweeping = EnchantmentRegistry.getEnchantmentLevel(level, Enchantments.SWEEPING_EDGE, stack);
        float damage = event.getOriginalDamage() * (0.66f + sweeping * 0.33f);
        float radius = 1 + sweeping * 0.25f;
        level.getEntities(attacker, target.getBoundingBox().inflate(radius)).forEach(e -> {
            if (e instanceof LivingEntity sweepTarget) {
                if (sweepTarget.isAlive() && sweepTarget != target) {
                    sweepTarget.hurt((DamageTypeHelper.create(level, DamageTypeRegistry.SCYTHE_SWEEP, attacker)), damage);
                    sweepTarget.knockback(0.4F,
                            Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)),
                            (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                }
            }
        });
    }
    public Holder<SoundEvent> getScytheSound(boolean canSweep) {
        return canSweep ? SoundRegistry.SCYTHE_SWEEP : SoundRegistry.SCYTHE_CUT;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.equals(Enchantments.SWEEPING_EDGE)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    public static boolean isEnhanced(LivingEntity attacker) {
        //TODO: convert this to a ToolAction, or something alike
        return CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get());
    }

    public static DamageSource replaceDamageSource(Player player, DamageSource source) {
        if (player.getMainHandItem().is(ItemTagRegistry.SCYTHES)) {
            return DamageTypeHelper.create(player.level(), DamageTypeRegistry.SCYTHE_MELEE, player);
        }
        return source;
    }
}