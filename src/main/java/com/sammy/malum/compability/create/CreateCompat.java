package com.sammy.malum.compability.create;

import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.registry.common.item.*;
import mezz.jei.api.constants.*;
import mezz.jei.api.registration.*;
import net.minecraft.world.item.*;
import net.neoforged.fml.*;
import team.lodestar.lodestone.systems.item.*;
import vectorwing.farmersdelight.common.utility.*;

public class CreateCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("create");
        if (LOADED) {
            LoadedOnly.init();
            return;
        }
        AbsentOnly.init();
    }

    public static class LoadedOnly {

        public static void init() {
        }
    }

    public static class AbsentOnly {

        public static void init() {
            ItemRegistry.register("copper_nugget", new LodestoneItemProperties(CreativeModeTabs.INGREDIENTS), Item::new);
        }
    }
}
