package com.sammy.malum.common.enchantment.scythe;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;

import java.util.*;

public class AscensionEnchantment extends Enchantment {
    public AscensionEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.SCYTHE_ONLY, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return !pOther.equals(EnchantmentRegistry.REBOUND.get()) && super.checkCompatibility(pOther);
    }

    public static void triggerAscension(Level level, Player player, InteractionHand hand, ItemStack scythe) {
        final boolean isEnhanced = !MalumScytheItem.canSweep(player);
        if (level.isClientSide()) {
            Vec3 motion = player.getDeltaMovement();
            player.setDeltaMovement(motion.x, player.getJumpPower()*1.25f, motion.z);
            if (player.isSprinting()) {
                float f = player.getYRot() * 0.017453292F;
                float x = -Mth.sin(f);
                float z = Mth.cos(f);

                var newMotion = player.getDeltaMovement();
                if (isEnhanced) {
                    newMotion = newMotion.subtract(x * 0.6f, -0.2, z * 0.6f);
                }
                else {
                    newMotion = newMotion.add(x * 0.75f, -0.2, z * 0.75f);
                }
                player.setDeltaMovement(newMotion);
            }
            player.hasImpulse = true;
            ForgeHooks.onLivingJump(player);
        }
        else {
            float baseDamage = (float) player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
            float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());
            var aabb = player.getBoundingBox().inflate(4, 1f, 4);
            var sound = SoundRegistry.SCYTHE_SWEEP.get();
            var particleEffect = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.SCYTHE_ASCENSION_SPIN).mirrorRandomly(level.getRandom());
            if (isEnhanced) {
                baseDamage *= 1.25f;
                magicDamage *= 1.25f;
                aabb = aabb.move(player.getLookAngle().scale(2f)).inflate(-2f, 1f, -2f);
                sound = SoundRegistry.SCYTHE_CUT.get();
                particleEffect = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.SCYTHE_ASCENSION_UPPERCUT).setVerticalSlashAngle().setMirrored(true);
            }
            if (scythe.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
                particleEffect.setSpiritType(spiritAffiliatedItem);
            }
            for(Entity target : level.getEntities(player, aabb, t -> canHitEntity(player, t))) {
                var damageSource = DamageTypeHelper.create(level, DamageTypeRegistry.SCYTHE_SWEEP, player);
                target.invulnerableTime = 0;
                boolean success = target.hurt(damageSource, baseDamage);
                if (success && target instanceof LivingEntity livingentity) {
                    ItemHelper.applyEnchantments(player, livingentity, scythe);
                    int i = scythe.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
                    if (i > 0) {
                        livingentity.setSecondsOnFire(i * 4);
                    }
                    if (magicDamage > 0) {
                        if (!livingentity.isDeadOrDying()) {
                            livingentity.invulnerableTime = 0;
                            livingentity.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOODOO, player), magicDamage);
                        }
                    }
                    SoundHelper.playSound(player, sound, 2.0f, RandomHelper.randomBetween(level.getRandom(), 0.75f, 1.25f));
                }
            }
            var slashPosition = player.position().add(0, player.getBbHeight() * 0.75, 0);
            var slashDirection = player.getLookAngle().multiply(1, 0, 1);
            particleEffect.spawnSlashingParticle(level, slashPosition, slashDirection);
            for (int i = 0; i < 3; i++) {
                SoundHelper.playSound(player, sound, 1f, RandomHelper.randomBetween(level.getRandom(), 1.25f, 1.75f));
            }
            SoundHelper.playSound(player, SoundRegistry.SCYTHE_ASCENSION.get(), 2f, RandomHelper.randomBetween(level.getRandom(), 1.25f, 1.5f));
        }
        if (!player.isCreative()) {
            int enchantmentLevel = scythe.getEnchantmentLevel(EnchantmentRegistry.ASCENSION.get());
            if (enchantmentLevel < 6) {
                player.getCooldowns().addCooldown(scythe.getItem(), 150 - 25 * (enchantmentLevel - 1));
            }
        }
        player.swing(hand, false);
        player.awardStat(Stats.ITEM_USED.get(scythe.getItem()));
    }

    protected static boolean canHitEntity(Player attacker, Entity pTarget) {
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else {
            return pTarget != attacker && !attacker.isPassengerOfSameVehicle(pTarget);
        }
    }
}