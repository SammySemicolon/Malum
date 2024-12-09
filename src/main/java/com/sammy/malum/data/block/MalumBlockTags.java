package com.sammy.malum.data.block;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockTagsProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;

public class MalumBlockTags extends LodestoneBlockTagsProvider {

    public MalumBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Malum Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        Set<DeferredHolder<Block, ? extends Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        tag(BlockTagRegistry.UNCHAINED_RITE_CATALYST).add(BlockRegistry.BLIGHTED_EARTH.get(), BlockRegistry.BLIGHTED_SOIL.get());
        tag(BlockTagRegistry.RITE_IMMUNE).addTags(BlockTagRegistry.TAINTED_ROCK, BlockTagRegistry.TWISTED_ROCK);

        tag(BlockTagRegistry.ENDLESS_FLAME);
        tag(BlockTagRegistry.GREATER_AERIAL_WHITELIST);

        addTagsFromBlockProperties(blocks.stream().map(DeferredHolder::get).collect(Collectors.toList()));
    }
}
