package com.sammy.malum.compability.irons_spellbooks;

import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.item.tabs.*;
import io.redspace.ironsspellbooks.api.events.*;
import io.redspace.ironsspellbooks.api.magic.*;
import io.redspace.ironsspellbooks.api.util.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.javafmlmod.*;

public class IronsSpellsCompat {

    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("irons_spellbooks");
        if (LOADED) {
            MinecraftForge.EVENT_BUS.addListener(LoadedOnly::spellDamage);
        }
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

        public static void spellDamage(SpellDamageEvent event) {
            if (CommonConfig.IRONS_SPELLBOOKS_SOUL_SHATTER.getConfigValue()) {
                SoulDataHandler.exposeSoul(event.getEntity());
            }
        }

        public static void generateMana(LivingEntity collector, float amount) {
            var magicData = MagicData.getPlayerMagicData(collector);
            magicData.addMana(amount);
            if (collector instanceof ServerPlayer serverPlayer) {
                UpdateClient.SendManaUpdate(serverPlayer, magicData);
            }
        }
    }
}