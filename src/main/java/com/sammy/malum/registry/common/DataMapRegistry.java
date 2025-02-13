package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data.ImpetusRepairData;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class DataMapRegistry {

    public static final DataMapType<Item, ImpetusRepairData> DAMAGED_IMPETUS_VARIANT = DataMapType.builder(
            MalumMod.malumPath("damaged_impetus_variant"),
            Registries.ITEM,
            ImpetusRepairData.CODEC
    ).build();

    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(DAMAGED_IMPETUS_VARIANT);
    }
}
