package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
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
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;

public abstract class AbstractStaffItem extends LodestoneCombatItem implements IMalumEventResponderItem {

    public AbstractStaffItem(Tier tier, float magicDamage, float chargeDuration, LodestoneItemProperties properties) {
        this(tier, -3f, magicDamage, chargeDuration, properties);
    }
    public AbstractStaffItem(Tier tier, float attackSpeed, float magicDamage, float chargeDuration, LodestoneItemProperties properties) {
        this(tier, 1f, attackSpeed, magicDamage, chargeDuration, properties);
    }
    public AbstractStaffItem(Tier tier, float attackDamage, float attackSpeed, float magicDamage, float chargeDuration, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, properties.mergeAttributes(
                ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(AttributeRegistry.CHARGE_DURATION, new AttributeModifier(AttributeRegistry.CHARGE_DURATION.getId(), chargeDuration, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(LodestoneAttributes.MAGIC_DAMAGE, new AttributeModifier(LodestoneAttributes.MAGIC_DAMAGE.getId(), magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()));
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float chargePercentage);

    public abstract int getCooldownDuration(Level level, LivingEntity livingEntity);

    public abstract int getProjectileCount(Level level, LivingEntity livingEntity, float chargePercentage);

    public abstract void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, int count);

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof Player player && event.getSource().is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
            var level = player.level();
            if (!level.isClientSide) {
                SoundHelper.playSound(target, SoundRegistry.STAFF_STRIKES.get(), attacker.getSoundSource(), 2f, RandomHelper.randomBetween(level.random, 0.85f, 1.25f));
                var particle = ParticleHelper.createSlamEffect(ParticleEffectTypeRegistry.STAFF_SLAM)
                        .setVerticalSlashAngle();
                if (stack.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
                    particle.setColor(spiritAffiliatedItem);
                }
                particle.spawnTargetBoundSlashingParticle(attacker, target);
            }
            if (EnchantmentRegistry.getEnchantmentLevel(level, EnchantmentRegistry.REPLENISHING, stack) > 0) {
                ReplenishingHandler.triggerReplenishing(event.getSource(), attacker, stack);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.getCooldowns().isOnCooldown(itemstack.getItem())) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            float duration = (float) pPlayer.getAttributes().getValue(AttributeRegistry.CHARGE_DURATION);
            if (pLevel instanceof ServerLevel serverLevel) {
                if (duration <= 0) {
                    shoot(itemstack, serverLevel, pPlayer, getProjectileCount(pLevel, pPlayer, 1f));
                }
            }
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        int useDuration = getUseDuration(pStack, pLivingEntity);
        float duration = (float) pLivingEntity.getAttributes().getValue(AttributeRegistry.CHARGE_DURATION);
        if (duration <= 0) {
            pLivingEntity.releaseUsingItem();
            return;
        }
        float delta = Math.min(duration, useDuration - pRemainingUseDuration) / duration;
        final InteractionHand hand = pLivingEntity.getUsedItemHand();
        if (pLevel.isClientSide) {
            Vec3 pos = getProjectileSpawnPos(pLivingEntity, hand, 1.5f, 0.6f);
            spawnChargeParticles(pLevel, pLivingEntity, pos, pStack, delta);
            return;
        }
        int fullyCharged = useDuration - Mth.ceil(duration);
        if (pRemainingUseDuration == fullyCharged) {
            float pitch = Mth.nextFloat(pLevel.random, 1.6f, 1.8f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_CHARGED.get(), SoundSource.PLAYERS, 1.25f, pitch);
            if (GeasEffectHandler.hasGeasEffect(pLivingEntity, MalumGeasEffectTypeRegistry.OATH_OF_THE_OVEREAGER_FIST.get())) {
                if (pLivingEntity instanceof Player player) {
                    player.getCooldowns().addCooldown(this, 5);
                }
                pLivingEntity.releaseUsingItem();
            }
            return;
        }
        if (pRemainingUseDuration > fullyCharged && pRemainingUseDuration % 5 == 0) {
            float pitch = delta + Mth.nextFloat(pLevel.random, 0.6f, 0.7f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.75f, pitch);
            return;
        }
        if (pRemainingUseDuration % 5 == 0) {
            float pitch = Mth.nextFloat(pLevel.random, 0.3f, 0.4f);
            pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_POWERS_UP.get(), SoundSource.PLAYERS, 0.5f, pitch);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        float chargeDuration = (float) pLivingEntity.getAttributes().getValue(AttributeRegistry.CHARGE_DURATION);
        float chargePercentage = Math.min(chargeDuration, getUseDuration(pStack, pLivingEntity) - pTimeCharged) / chargeDuration;
        int projectileCount = getProjectileCount(pLevel, pLivingEntity, chargePercentage);
        if (projectileCount > 0) {
            if (pLevel instanceof ServerLevel serverLevel) {
                shoot(pStack, serverLevel, pLivingEntity, projectileCount);
            }
            return;
        }
        float pitch = Mth.nextFloat(pLevel.random, 0.5f, 0.8f);
        pLevel.playSound(null, pLivingEntity.blockPosition(), SoundRegistry.STAFF_SIZZLES_OUT.get(), SoundSource.PLAYERS, 0.5f, pitch);
    }

    public void shoot(ItemStack stack, ServerLevel level, LivingEntity entity, int projectileCount) {
        InteractionHand hand = entity.getUsedItemHand();
        float magicDamage = (float) entity.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE);
        if (magicDamage == 0) {
            float pitch = Mth.nextFloat(level.random, 0.5f, 0.8f);
            level.playSound(null, entity.blockPosition(), SoundRegistry.STAFF_SIZZLES_OUT.get(), SoundSource.PLAYERS, 0.5f, pitch);
            entity.swing(hand, true);
            return;
        }
        for (int i = 0; i < projectileCount; i++) {
            fireProjectile(entity, stack, level, hand, i);
        }
        if (entity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
                var data = player.getData(AttachmentTypeRegistry.STAFF_ABILITIES);
                if (data.reserveChargeCount > 0) {
                    data.reserveChargeCount--;
                } else {
                    player.getCooldowns().addCooldown(this, getCooldownDuration(level, entity));
                }
            }
            player.swing(hand, true);
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

    public Vec3 getProjectileSpawnPos(LivingEntity player, InteractionHand hand, float distance, float spread) {
        int angle = hand == InteractionHand.MAIN_HAND ? 225 : 90;
        double radians = Math.toRadians(angle - player.yHeadRot);
        return player.position().add(player.getLookAngle().scale(distance)).add(spread * Math.sin(radians), player.getBbHeight() * 0.9f, spread * Math.cos(radians));
    }
}
