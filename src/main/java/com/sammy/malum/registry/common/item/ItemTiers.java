package com.sammy.malum.registry.common.item;

import com.google.common.base.Suppliers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum ItemTiers implements Tier{
    SOUL_STAINED_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1250, 7.5f, 2.5f, 16, ItemRegistry.SOUL_STAINED_STEEL_INGOT),
    MALIGNANT_ALLOY(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2500, 8f, 4f, 24, ItemRegistry.MALIGNANT_PEWTER_INGOT),
    HARNESSED_CHAOS(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2500, 8f, 2.5f, 24, ItemRegistry.FUSED_CONSCIOUSNESS),

    TYRVING(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 8f, 1f, 16, ItemRegistry.TWISTED_ROCK),
    HEX_STAFF(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1250, 8f, 2.5f, 16, ItemRegistry.MNEMONIC_FRAGMENT);
    
    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;
    
    ItemTiers(TagKey<Block> incorrectBlockForDrops, int uses, float speed, float damage, int enchantmentValue, Supplier<Item> repairItem) {
        this.incorrectBlocksForDrops = incorrectBlockForDrops;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = Suppliers.memoize(()->Ingredient.of(repairItem.get()));
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
