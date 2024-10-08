package com.sammy.malum.common.enchantment;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;

public class ReboundEnchantment extends Enchantment {
    public ReboundEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.REBOUND_SCYTHE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        HolderGetter<Enchantment> enchantmentLookup = player.level().registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT);
        if (stack.getEnchantmentLevel(enchantmentLookup.getOrThrow(EnchantmentRegistry.SPIRIT_PLUNDER)) > 0) {
            Level level = player.level();
            if (!level.isClientSide) {
                float baseDamage = (float) player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE);
                float velocity = (float) (1.5F * player.getAttributeValue(AttributeRegistry.SCYTHE_PROFICIENCY));

                int slot = event.getHand() == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;
                ScytheBoomerangEntity entity = new ScytheBoomerangEntity(level, player.position().x, player.position().y + player.getBbHeight() / 2f, player.position().z);
                entity.setData(player, baseDamage, magicDamage, slot);
                entity.setItem(stack);
                entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 0F);
                level.addFreshEntity(entity);
                player.setItemInHand(event.getHand(), ItemStack.EMPTY);
//                SoundHelper.playSound(player, SoundRegistry.SCYTHE_THROW.get(),2.0f, RandomHelper.randomBetween(level.getRandom(), 0.75f, 1.25f));
            }
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
    }

}