package com.sammy.malum.data.block;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.block.*;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockTagsProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;

public class MalumBlockTags extends LodestoneBlockTagsProvider {

    public MalumBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public String getName() {
        return "Malum Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        Set<DeferredHolder<Block, ? extends Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        getOrCreateTagBuilder(BlockTagRegistry.UNCHAINED_RITE_CATALYST).add(BlockRegistry.BLIGHTED_EARTH.get(), BlockRegistry.BLIGHTED_SOIL.get());
        getOrCreateTagBuilder(BlockTagRegistry.RITE_IMMUNE).addTags(BlockTagRegistry.TAINTED_ROCK, BlockTagRegistry.TWISTED_ROCK);

        getOrCreateTagBuilder(BlockTagRegistry.ENDLESS_FLAME);
        getOrCreateTagBuilder(BlockTagRegistry.GREATER_AERIAL_WHITELIST);

        addTagsFromBlockProperties(blocks);
    }
}
