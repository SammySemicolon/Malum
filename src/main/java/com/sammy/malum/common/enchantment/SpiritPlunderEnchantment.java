package com.sammy.malum.common.enchantment;

import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class SpiritPlunderEnchantment extends Enchantment {
    public SpiritPlunderEnchantment() {
        super(Rarity.COMMON, EnchantmentRegistry.SOUL_SHATTER_CAPABLE_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}