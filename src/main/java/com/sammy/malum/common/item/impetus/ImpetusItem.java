package com.sammy.malum.common.item.impetus;

import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Supplier;

public class ImpetusItem extends Item implements SpiritRepairRecipe.IRepairOutputOverride {
    private Supplier<CrackedImpetusItem> cracked;

    public ImpetusItem(Properties properties) {
        super(properties.durability(800));
    }

    public ImpetusItem setCrackedVariant(Supplier<CrackedImpetusItem> cracked) {
        this.cracked = cracked;
        return this;
    }

    public CrackedImpetusItem getCrackedVariant() {
        return cracked.get();
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    @Override
    public boolean ignoreDuringLookup() {
        return true;
    }
}