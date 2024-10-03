package com.sammy.malum.compability.attributelib;

import com.sammy.malum.common.item.curiosities.*;
import dev.shadowsoffire.attributeslib.client.*;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.*;

import static com.sammy.malum.registry.common.item.ItemTiers.ItemTierEnum.*;

public class AttributeLibCompat {

    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("attributeslib");
        if (LOADED) {
            LoadedOnly.init();
        }
    }

    public static class LoadedOnly {

        public static void init() {
            ModifierSourceType.register(new MalignantConversionModifierSourceType());
        }
    }
}
