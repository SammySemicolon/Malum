package com.sammy.malum.registry.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.WeepingWellStructure;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class StructureRegistry {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<StructureType<?>, StructureType<WeepingWellStructure>> WEEPING_WELL = STRUCTURES.register("weeping_well", () -> () -> (MapCodec<WeepingWellStructure>) WeepingWellStructure.CODEC);

}
