package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.ritual.MalumRitualTier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MalumDataComponentRegistry {

    public static final DeferredRegister<DataComponentType<?>> DATA = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MalumRitualTier>> RITUAL_TIER = DATA.register("ritual_tier",
            () -> DataComponentType.<MalumRitualTier>builder()
                    .persistent(MalumRitualTier.CODEC)
                    .build()
    );
}