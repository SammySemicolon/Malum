package com.sammy.malum.common.enchantment.scythe;

import com.sammy.malum.common.entity.scythe.ScytheBoomerangEntity;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.server.level.*;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

public class ReboundEnchantment extends Enchantment {
    public ReboundEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.SCYTHE_ONLY, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return !pOther.equals(EnchantmentRegistry.ASCENSION.get()) && super.checkCompatibility(pOther);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void throwScythe(Level level, Player player, InteractionHand hand, ItemStack scythe) {
        int slot = hand == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;
        if (player instanceof ServerPlayer serverPlayer) {
            final boolean isEnhanced = !MalumScytheItem.canSweep(player);
            float baseDamage = (float) player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
            float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());
            float velocity = (isEnhanced ? 3f : 1.75f);

            var position = player.position().add(0, player.getBbHeight()*0.5f, 0);
            if (isEnhanced) {
                int angle = hand == InteractionHand.MAIN_HAND ? 225 : 90;
                double radians = Math.toRadians(angle - player.yHeadRot);
                position = player.position().add(player.getLookAngle().scale(0.5f)).add(0.75f * Math.sin(radians), player.getBbHeight() * 0.9f, 0.75f * Math.cos(radians));
                baseDamage *= 1.5f;
                magicDamage *= 1.5f;
            }
            var entity = new ScytheBoomerangEntity(level, position.x, position.y, position.z);
            entity.setData(player, baseDamage, magicDamage, slot, 8);
            entity.setItem(scythe);
            entity.setEnhanced(isEnhanced);
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 0F);
            level.addFreshEntity(entity);

            SoundHelper.playSound(player, SoundRegistry.SCYTHE_THROW.get(), 2.0f, RandomHelper.randomBetween(level.getRandom(), 0.75f, 1.25f));
            TemporarilyDisabledItem.disable(serverPlayer, slot);
            player.awardStat(Stats.ITEM_USED.get(scythe.getItem()));
            player.swing(hand, true);
        }
    }

    public static void pickupScythe(ScytheBoomerangEntity entity, ItemStack scythe, ServerPlayer player) {
        if (!player.isCreative()) {
            int enchantmentLevel = scythe.getEnchantmentLevel(EnchantmentRegistry.REBOUND.get());
            if (enchantmentLevel < 4) {
                int duration = 100 - 25 * (enchantmentLevel - 1);
                if (entity.enemiesHit > 0) {
                    duration -= (entity.enemiesHit+1) * 20;
                }
                if (duration > 0) {
                    player.getCooldowns().addCooldown(scythe.getItem(), duration);
                }
            }
        }
        TemporarilyDisabledItem.enable(player, entity.slot);
    }
}