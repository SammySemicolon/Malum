package com.sammy.malum.compability.irons_spellbooks;

import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import io.redspace.ironsspellbooks.api.events.*;
import io.redspace.ironsspellbooks.api.magic.*;
import io.redspace.ironsspellbooks.api.util.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.*;

public class IronsSpellsCompat {

    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("irons_spellbooks");
    }

    public static void generateMana(LivingEntity collector, double amount) {
        generateMana(collector, (float) amount);
    }

    public static void generateMana(LivingEntity collector, float amount) {
        if (LOADED) {
            LoadedOnly.generateMana(collector, amount);
        }
    }

    public static class LoadedOnly {

        public static void generateMana(LivingEntity collector, float amount) {
            var magicData = MagicData.getPlayerMagicData(collector);
            magicData.addMana(amount);
            if (collector instanceof ServerPlayer serverPlayer) {
                UpdateClient.SendManaUpdate(serverPlayer, magicData);
            }
        }
    }
}