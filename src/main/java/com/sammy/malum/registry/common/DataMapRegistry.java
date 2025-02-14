package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data.ImpetusData;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class DataMapRegistry {

    public static final DataMapType<Item, ImpetusData> FRACTURED_IMPETUS_VARIANT = DataMapType.builder(
            MalumMod.malumPath("fractured_impetus_variant"),
            Registries.ITEM,
            ImpetusData.CODEC
    ).build();

    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(FRACTURED_IMPETUS_VARIANT);
    }
}
