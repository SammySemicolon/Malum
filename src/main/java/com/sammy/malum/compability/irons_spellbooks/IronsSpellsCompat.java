package com.sammy.malum.compability.irons_spellbooks;

import com.sammy.malum.*;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import io.redspace.ironsspellbooks.api.events.*;
import io.redspace.ironsspellbooks.api.magic.*;
import io.redspace.ironsspellbooks.item.weapons.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.fml.*;
import net.neoforged.neoforge.common.*;

public class IronsSpellsCompat {

    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("irons_spellbooks");
        if (LOADED) {
            NeoForge.EVENT_BUS.addListener(LoadedOnly::spellDamage);
        }
    }

    public static boolean isStaff(ItemStack stack) {
        if (LOADED) {
            return LoadedOnly.isStaff(stack);
        }
        return false;
    }

    public static void generateMana(ServerPlayer collector, double amount) {
        generateMana(collector, (float) amount);
    }

    public static void generateMana(ServerPlayer collector, float amount) {
        if (LOADED) {
            LoadedOnly.generateMana(collector, amount);
        }
    }
    public static void recoverSpellCooldowns(ServerPlayer serverPlayer, int enchantmentLevel) {
        if (LOADED) {
            LoadedOnly.recoverSpellCooldowns(serverPlayer, enchantmentLevel);
        }
    }

    public static void addEchoingArcanaSpellCooldown(EchoingArcanaEffect effect) {
        if (LOADED) {
            LoadedOnly.addEchoingArcanaSpellCooldown(effect);
        }
    }

    public static void addSilencedNegativeAttributeModifiers(SilencedEffect effect) {
        if (LOADED) {
            LoadedOnly.addSilencedNegativeAttributeModifiers(effect);
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

        public static boolean isStaff(ItemStack stack) {
            return stack.getItem() instanceof StaffItem;
        }

        public static void generateMana(ServerPlayer collector, float amount) {
            var magicData = MagicData.getPlayerMagicData(collector);
            magicData.addMana(amount);
//            UpdateClient.SendManaUpdate(collector, magicData);
        }

        public static void recoverSpellCooldowns(ServerPlayer serverPlayer, int enchantmentLevel) {
            var cooldowns = MagicData.getPlayerMagicData(serverPlayer).getPlayerCooldowns();
            cooldowns.getSpellCooldowns().forEach((key, value) -> cooldowns.decrementCooldown(value, (int) (value.getSpellCooldown() * .05f * enchantmentLevel)));
            cooldowns.syncToPlayer(serverPlayer);
        }

        public static void addEchoingArcanaSpellCooldown(EchoingArcanaEffect effect) {
            effect.addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.COOLDOWN_REDUCTION, MalumMod.malumPath("echoing_arcana"), 0.02f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        }

        public static void addSilencedNegativeAttributeModifiers(SilencedEffect effect) {
            effect.addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MANA_REGEN, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            effect.addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
    }
}