package com.sammy.malum.compability.attributelib;

import dev.shadowsoffire.attributeslib.client.*;
import net.minecraftforge.fml.*;

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
