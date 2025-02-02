package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.HolderLookup.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.tags.*;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.concurrent.*;

public class MalumEnchantmentTags extends EnchantmentTagsProvider {

    public MalumEnchantmentTags(PackOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(EnchantmentTags.NON_TREASURE).add(
                EnchantmentRegistry.HAUNTED, EnchantmentRegistry.ANIMATED,
                EnchantmentRegistry.ASCENSION, EnchantmentRegistry.REBOUND,
                EnchantmentRegistry.REPLENISHING, EnchantmentRegistry.CAPACITOR,
                EnchantmentRegistry.SPIRIT_PLUNDER);
        tag(EnchantmentTags.IN_ENCHANTING_TABLE).add(
                EnchantmentRegistry.HAUNTED, EnchantmentRegistry.ANIMATED,
                EnchantmentRegistry.ASCENSION, EnchantmentRegistry.REBOUND,
                EnchantmentRegistry.REPLENISHING, EnchantmentRegistry.CAPACITOR,
                EnchantmentRegistry.SPIRIT_PLUNDER);
    }
}