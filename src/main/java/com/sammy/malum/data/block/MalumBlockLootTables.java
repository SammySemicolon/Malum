package com.sammy.malum.data.block;

import com.google.common.collect.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.flag.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.level.storage.loot.providers.nbt.*;
import net.minecraft.world.level.storage.loot.providers.number.*;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;
import static team.lodestar.lodestone.helpers.DataHelper.*;

public class MalumBlockLootTables extends LootTableProvider {

    /*private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();*/
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] MAGIC_SAPLING_DROP_CHANCE = new float[]{0.015F, 0.0225F, 0.033333336F, 0.05F};

    public MalumBlockLootTables(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(pOutput, Set.of(), List.of(
                new SubProviderEntry(BlocksLoot::new, LootContextParamSets.BLOCK)
        ), provider);
    }

    public static class BlocksLoot extends BlockLootSubProvider {

        protected BlocksLoot(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BlockRegistry.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }

        @Override
        protected void generate() {
            Set<DeferredHolder<Block, ? extends Block>> blocks = new HashSet<>(BLOCKS.getEntries());

            takeAll(blocks, b -> b.get().properties() instanceof LodestoneBlockProperties && ((LodestoneBlockProperties) b.get().properties()).getDatagenData().hasInheritedLootTable);

            takeAll(blocks, BlockRegistry.RUNEWOOD_LEAVES, BlockRegistry.HANGING_RUNEWOOD_LEAVES).forEach((b) -> add(b.get(), createLeavesDrops(b.get(), BlockRegistry.RUNEWOOD_SAPLING.get(), MAGIC_SAPLING_DROP_CHANCE)));
            takeAll(blocks, BlockRegistry.AZURE_RUNEWOOD_LEAVES, BlockRegistry.HANGING_AZURE_RUNEWOOD_LEAVES).forEach((b) -> add(b.get(), createLeavesDrops(b.get(), BlockRegistry.AZURE_RUNEWOOD_SAPLING.get(), MAGIC_SAPLING_DROP_CHANCE)));
            takeAll(blocks, BlockRegistry.SOULWOOD_LEAVES, BlockRegistry.BUDDING_SOULWOOD_LEAVES, BlockRegistry.HANGING_SOULWOOD_LEAVES).forEach((b) -> add(b.get(), createLeavesDrops(b.get(), BlockRegistry.SOULWOOD_GROWTH.get(), MAGIC_SAPLING_DROP_CHANCE)));

            add(take(blocks, BlockRegistry.BLIGHTED_SOULWOOD).get(), createSingleItemTableWithSilkTouch(BlockRegistry.BLIGHTED_SOULWOOD.get(), ItemRegistry.SOULWOOD_LOG.get()));
            add(take(blocks, BlockRegistry.BLIGHTED_SOIL).get(), createBlightedDrop(BlockRegistry.BLIGHTED_SOIL.get(), 4));
            add(take(blocks, BlockRegistry.BLIGHTED_EARTH).get(), createBlightedDrop(BlockRegistry.BLIGHTED_EARTH.get(), 4).withPool(LootPool.lootPool().add(applyExplosionDecay(BlockRegistry.BLIGHTED_EARTH.get(), LootItem.lootTableItem(Blocks.DIRT)))));
            add(take(blocks, BlockRegistry.BLIGHTED_GROWTH).get(), createBlightedPlantDrop(BlockRegistry.BLIGHTED_GROWTH.get(), 1));

            add(take(blocks, BlockRegistry.BRILLIANT_STONE).get(), createOreDrop(BlockRegistry.BRILLIANT_STONE.get(), ItemRegistry.RAW_BRILLIANCE.get()));
            add(take(blocks, BlockRegistry.BRILLIANT_DEEPSLATE).get(), createOreDrop(BlockRegistry.BRILLIANT_DEEPSLATE.get(), ItemRegistry.RAW_BRILLIANCE.get()));
            add(take(blocks, BlockRegistry.SOULSTONE_ORE).get(), createOreDrop(BlockRegistry.SOULSTONE_ORE.get(), ItemRegistry.RAW_SOULSTONE.get()));
            add(take(blocks, BlockRegistry.DEEPSLATE_SOULSTONE_ORE).get(), createOreDrop(BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get(), ItemRegistry.RAW_SOULSTONE.get()));
            add(take(blocks, BlockRegistry.BLAZING_QUARTZ_ORE).get(), createOreDrop(BlockRegistry.BLAZING_QUARTZ_ORE.get(), ItemRegistry.BLAZING_QUARTZ.get()));
            add(take(blocks, BlockRegistry.NATURAL_QUARTZ_ORE).get(), createOreDrop(BlockRegistry.NATURAL_QUARTZ_ORE.get(), ItemRegistry.NATURAL_QUARTZ.get()));
            add(take(blocks, BlockRegistry.DEEPSLATE_QUARTZ_ORE).get(), createOreDrop(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get(), ItemRegistry.NATURAL_QUARTZ.get()));
            add(take(blocks, BlockRegistry.CTHONIC_GOLD_ORE).get(), createCthonicGoldOreDrop(BlockRegistry.CTHONIC_GOLD_ORE.get()));

            takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(b -> add(b.get(), createSingleItemTable(b.get().asItem())));
            takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouchOrShears(b.get(), b.get().asItem())));
            takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouchOrShears(b.get(), b.get().asItem())));

            takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouch(b.get(), Items.DIRT)));
            takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> add(b.get(), createSlabItemTable(b.get())));
            takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> add(b.get(), createDoorTable(b.get())));

            takeAll(blocks, b -> b.get() instanceof EtherBlock).forEach(b -> add(b.get(), createEtherDrop(b.get())));
            takeAll(blocks, b -> b.get() instanceof SpiritJarBlock).forEach(b -> add(b.get(), createJarDrop(b.get())));

            takeAll(blocks, b -> true).forEach(b -> add(b.get(), createSingleItemTable(b.get().asItem())));
        }

        protected LootTable.Builder createCthonicGoldOreDrop(Block block) {
            return createSilkTouchDispatchTable(block,
                    applyExplosionDecay(block, LootItem.lootTableItem(ItemRegistry.CTHONIC_GOLD_FRAGMENT.get())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F)))));
        }

        protected LootTable.Builder createBlightedDrop(Block block, int gunkAmount) {
            return createSilkTouchDispatchTable(block,
                    applyExplosionCondition(ItemRegistry.BLIGHTED_GUNK.get(), LootItem.lootTableItem(ItemRegistry.BLIGHTED_GUNK.get())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(gunkAmount)))));
        }

        protected LootTable.Builder createBlightedPlantDrop(Block block, int gunkAmount) {
            return createSilkTouchOrShearsDispatchTable(block,
                    applyExplosionCondition(ItemRegistry.BLIGHTED_GUNK.get(), LootItem.lootTableItem(ItemRegistry.BLIGHTED_GUNK.get())
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(gunkAmount)))));
        }

        protected LootTable.Builder createEtherDrop(Block block) {
            return LootTable.lootTable().withPool(
                    applyExplosionCondition(block,
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(block)
                                            .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                            .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY))
                                            .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                    .copy("firstColor", "display.firstColor")
                                                    .copy("secondColor", "display.secondColor")))));
        }

        protected LootTable.Builder createJarDrop(Block block) {
            return LootTable.lootTable().withPool(
                    applyExplosionCondition(block,
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(block)
                                            .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                            .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY))
                                            .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                    .copy("spirit", "spirit")
                                                    .copy("count", "count")))));
        }

        protected LootTable.Builder createSingleItemTableWithSilkTouchOrShears(Block p_124258_, ItemLike p_124259_) {
            return createSilkTouchOrShearsDispatchTable(p_124258_, applyExplosionCondition(p_124258_, LootItem.lootTableItem(p_124259_)));
        }
    }
}