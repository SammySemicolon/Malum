package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;

public abstract class AbstractStaffItem extends ModCombatItem implements IMalumEventResponderItem {

    public final float chargeDuration;
    public final float magicDamage;

    public AbstractStaffItem(Tier tier, float attackSpeed, int chargeDuration, float magicDamage, Properties builderIn) {
        super(tier, 1,  -2.8f+attackSpeed, builderIn);
        this.chargeDuration = chargeDuration;
        this.magicDamage = magicDamage;
    }
    public AbstractStaffItem(Tier tier, int chargeDuration, float magicDamage, Properties builderIn) {
        this(tier, 0f, chargeDuration, magicDamage, builderIn);
    }

    
    public abstract void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float chargePercentage);

    public abstract int getCooldownDuration(Level level, LivingEntity livingEntity);
    public abstract int getProjectileCount(Level level, LivingEntity livingEntity, float chargePercentage);

    public abstract void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, float chargePercentage, int count);

    @Override
    public ItemAttributeModifiers.Builder createExtraAttributes() {
        var builder = ItemAttributeModifiers.builder();
        builder.add(LodestoneAttributes.MAGIC_DAMAGE, new AttributeModifier(LodestoneAttributes.MAGIC_DAMAGE.unwrapKey().get().location(), magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder;
    }


    @Override
    public void outgoingDamageEvent(LivingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof Player player && event.getSource().is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
            var level = player.level();
            SoundHelper.playSound(target, SoundRegistry.STAFF_STRIKES.get(), attacker.getSoundSource(), 0.75f, RandomHelper.randomBetween(level.random, 0.5f, 1.0f));
            spawnSweepParticles(player, ParticleRegistry.STAFF_SLAM_PARTICLE.get());

            if (EnchantmentRegistry.getEnchantmentLevel(level, EnchantmentRegistry.REPLENISHING, stack) > 0) {
                ReplenishingHandler.triggerReplenishing(event.getSource(), attacker, stack);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        final float chargePercentage = Math.min(chargeDuration, getUseDuration(pStack, pLivingEntity) - pTimeCharged) / chargeDuration;
        int projectileCount = getProjectileCount(pLevel, pLivingEntity, chargePercentage);
        if (projectileCount > 0) {
            InteractionHand hand = pLivingEntity.getUsedItemHand();
            if (!pLevel.isClientSide) {
                float magicDamage = (float) pLivingEntity.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE);
                if (magicDamage == 0) {
                    float pitch = Mth.nextFloat(pLevel.random, 0.5f, 0.8f);
                    pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_SIZZLES.get(), SoundSource.PLAYERS, 0.5f, pitch);
                    pLivingEntity.swing(hand, true);
                    return;
                }
                for (int i = 0; i < projectileCount; i++) {
                    fireProjectile(pLivingEntity, pStack, pLevel, hand, chargePercentage, i);
                }
                if (pLivingEntity instanceof Player player) {
                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (!player.getAbilities().instabuild) {
                        pStack.hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
                        var data = player.getAttachedOrCreate(AttachmentTypeRegistry.RESERVE_STAFF_CHARGES);
                        if (data.reserveChargeCount > 0) {
                            data.reserveChargeCount--;
                        }
                        else {
                            player.getCooldowns().addCooldown(this, getCooldownDuration(pLevel, pLivingEntity));
                        }
                    }
                    player.swing(hand, true);
                }
            }
        }
        else {
            float pitch = Mth.nextFloat(pLevel.random, 0.5f, 0.8f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_SIZZLES.get(), SoundSource.PLAYERS, 0.5f, pitch);
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        final int useDuration = getUseDuration(pStack, pLivingEntity);
        final float chargePercentage = Math.min(chargeDuration, useDuration - pRemainingUseDuration) / chargeDuration;
        if (pLevel.isClientSide) {
            InteractionHand hand = pLivingEntity.getUsedItemHand();
            Vec3 pos = getProjectileSpawnPos(pLivingEntity, hand, 1.5f, 0.6f);
            spawnChargeParticles(pLevel, pLivingEntity, pos, pStack, chargePercentage);
        }
        if (pRemainingUseDuration == useDuration - chargeDuration) {
            float pitch = Mth.nextFloat(pLevel.random, 1.2f, 1.6f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_CHARGED.get(), SoundSource.PLAYERS, 1.25f, pitch);
        }
        else if (pRemainingUseDuration > useDuration - chargeDuration && pRemainingUseDuration % 5 == 0) {
            float pitch = 0.25f + chargePercentage + Mth.nextFloat(pLevel.random, 0.2f, 0.6f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.75f, pitch);
        }
        else if (pRemainingUseDuration % 5 == 0) {
            float pitch = Mth.nextFloat(pLevel.random, 0.2f, 0.6f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.5f, pitch);
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.getCooldowns().isOnCooldown(itemstack.getItem())) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public static void spawnSweepParticles(Player player, SimpleParticleType type) {
        double d0 = (-Mth.sin(player.getYRot() * ((float) Math.PI / 180F)));
        double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(type, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }
    public Vec3 getProjectileSpawnPos(LivingEntity player, InteractionHand hand, float distance, float spread) {
        int angle = hand == InteractionHand.MAIN_HAND ? 225 : 90;
        double radians = Math.toRadians(angle - player.yHeadRot);
        return player.position().add(player.getLookAngle().scale(distance)).add(spread * Math.sin(radians), player.getBbHeight() * 0.9f, spread * Math.cos(radians));
    }
}