package com.sammy.malum.core.handlers.enchantment;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.sets.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.server.level.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;

import static com.sammy.malum.registry.common.item.EnchantmentRegistry.getEnchantmentLevel;

public class AscensionHandler {

    public static void triggerAscension(Level level, Player player, InteractionHand hand, ItemStack scythe) {
        final boolean isEnhanced = MalumScytheItem.isEnhanced(player);
        player.resetFallDistance();
        if (level.isClientSide()) {
            Vec3 motion = player.getDeltaMovement();
            player.setDeltaMovement(motion.x, player.getJumpPower() * 2f, motion.z);
            if (player.isSprinting()) {
                float f = player.getYRot() * 0.017453292F;
                float x = -Mth.sin(f);
                float z = Mth.cos(f);

                var newMotion = player.getDeltaMovement();
                if (isEnhanced) {
                    newMotion = newMotion.subtract(x * 0.6f, 0, z * 0.6f);
                } else {
                    newMotion = newMotion.add(x * 0.75f, 0, z * 0.75f);
                }
                player.setDeltaMovement(newMotion);
            }
            player.hasImpulse = true;
            CommonHooks.onLivingJump(player);
        }
        boolean hasFunnyRing = CurioHelper.hasCurioEquipped(player, ItemRegistry.RING_OF_THE_RISING_EDGE.get());
        if (level instanceof ServerLevel serverLevel) {
            var random = serverLevel.getRandom();
            float baseDamage = (float) player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
            float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE);
            var aabb = player.getBoundingBox().inflate(4, 1f, 4);
            var sound = SoundRegistry.SCYTHE_SWEEP.get();
            var particleEffect = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.SCYTHE_ASCENSION_SPIN).mirrorRandomly(random);
            if (isEnhanced) {
                baseDamage *= 1.3f;
                magicDamage *= 1.3f;
                aabb = aabb.move(player.getLookAngle().scale(2f)).inflate(-2f, 1f, -2f);
                sound = SoundRegistry.SCYTHE_CUT.get();
                particleEffect = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.SCYTHE_ASCENSION_UPPERCUT).setVerticalSlashAngle().setMirrored(true);
            }
            if (scythe.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
                particleEffect.setSpiritType(spiritAffiliatedItem);
            }
            boolean hasNarrowNecklace = !MalumScytheItem.isEnhanced(player);
            boolean dealtDamage = false;
            for (Entity target : serverLevel.getEntities(player, aabb, t -> ascensionCanHitEntity(player, t))) {
                var damageSource = DamageTypeHelper.create(serverLevel, DamageTypeRegistry.SCYTHE_ASCENSION, player);
                target.invulnerableTime = 0;
                boolean success = target.hurt(damageSource, baseDamage);
                if (success && target instanceof LivingEntity livingentity) {
                    if (magicDamage > 0) {
                        if (!livingentity.isDeadOrDying()) {
                            livingentity.invulnerableTime = 0;
                            livingentity.hurt(DamageTypeHelper.create(serverLevel, DamageTypeRegistry.VOODOO, player), magicDamage);
                        }
                    }
                    SoundHelper.playSound(player, sound, 2.0f, RandomHelper.randomBetween(random, 0.75f, 1.25f));
                    dealtDamage = true;
                }
            }
            if (dealtDamage) {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.ASCENSION, 80, 0));
            }
            var slashPosition = player.position().add(0, player.getBbHeight() * 0.75, 0);
            var slashDirection = player.getLookAngle().multiply(1, 0, 1);
            particleEffect.spawnSlashingParticle(serverLevel, slashPosition, slashDirection);
            for (int i = 0; i < 3; i++) {
                SoundHelper.playSound(player, sound, 1f, RandomHelper.randomBetween(random, 1.25f, 1.75f));
            }
            SoundHelper.playSound(player, SoundRegistry.SCYTHE_ASCENSION.get(), 2f, RandomHelper.randomBetween(random, 1.25f, 1.5f));
        }
        if (!player.isCreative()) {
            int enchantmentLevel = getEnchantmentLevel(level, EnchantmentRegistry.ASCENSION, scythe);
            if (enchantmentLevel < 6) {
                int cooldown = 150 - 25 * (enchantmentLevel - 1);
                if (hasFunnyRing) {
                    cooldown += 50;
                }
                player.getCooldowns().addCooldown(scythe.getItem(), cooldown);
            }
        }
        player.swing(hand, false);
        player.awardStat(Stats.ITEM_USED.get(scythe.getItem()));
    }

    protected static boolean ascensionCanHitEntity(Player attacker, Entity pTarget) {
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else {
            return pTarget != attacker && !attacker.isPassengerOfSameVehicle(pTarget);
        }
    }
}