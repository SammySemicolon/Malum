package com.sammy.malum.compability.irons_spellbooks;

import com.sammy.malum.common.effect.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import io.redspace.ironsspellbooks.api.events.*;
import io.redspace.ironsspellbooks.api.magic.*;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.*;

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

    public static void addEchoingArcanaSpellCooldown(EchoingArcanaEffect effect) {
        if (LOADED) {
            LoadedOnly.addEchoingArcanaSpellCooldown(effect);
        }
    }

    public static class LoadedOnly {

        public static void spellDamage(SpellDamageEvent event) {
            boolean canShatter = event.getEntity() instanceof Player ?
                    CommonConfig.IRONS_SPELLBOOKS_SPIRIT_DAMAGE.getConfigValue() :
                    CommonConfig.IRONS_SPELLBOOKS_NON_PLAYER_SPIRIT_DAMAGE.getConfigValue();
            if (canShatter) {
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

        public static void addEchoingArcanaSpellCooldown(EchoingArcanaEffect effect) {
            effect.addAttributeModifier(AttributeRegistry.COOLDOWN_REDUCTION.get(), "8949b9d4-2505-4248-9667-0ece857af8a4", 0.0125f, AttributeModifier.Operation.ADDITION);
        }
    }
}