package com.sammy.malum.compability.irons_spellbooks;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import io.redspace.ironsspellbooks.api.events.*;
import io.redspace.ironsspellbooks.api.magic.*;
import io.redspace.ironsspellbooks.item.weapons.*;
import net.minecraft.core.Holder;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import static com.sammy.malum.registry.common.item.EnchantmentRegistry.getEnchantmentLevel;

public class IronsSpellsCompat {

    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("irons_spellbooks");
        if (LOADED) {
            NeoForge.EVENT_BUS.addListener(LoadedOnly::spellDamage);
            NeoForge.EVENT_BUS.addListener(LoadedOnly::triggerReplenishing);
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
    public static void addSoulHunterSpellPower(ItemAttributeModifiers.Builder attributes) {
        if (LOADED) {
            LoadedOnly.addSoulHunterSpellPower(attributes);
        }
    }
    public static void addSpellPowerToCurio(MalumCurioItem item, Multimap<Holder<Attribute>, AttributeModifier> map, float amount) {
        if (LOADED) {
            LoadedOnly.addSpellPowerToCurio(item, map, amount);
        }
    }

    public static void addEchoingArcanaSpellCooldown(EchoingArcanaEffect effect) {
        if (LOADED) {
            LoadedOnly.addEchoingArcanaSpellCooldown(effect);
        }
    }

    public static void addGluttonySpellPower(GluttonyEffect effect) {
        if (LOADED) {
            LoadedOnly.addGluttonySpellPower(effect);
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

        public static void triggerReplenishing(LivingDamageEvent.Post event) {
            DamageSource source = event.getSource();
            Entity directEntity = source.getDirectEntity();
            if (directEntity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.getAttackStrengthScale(0) > 0.8f) {
                    ItemStack stack = serverPlayer.getMainHandItem();
                    int level = getEnchantmentLevel(serverPlayer.level(), EnchantmentRegistry.REPLENISHING, stack);
                    recoverSpellCooldowns(serverPlayer, 0.025f * level);
                }
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

        public static void recoverSpellCooldowns(ServerPlayer serverPlayer, float amount) {
            var cooldowns = MagicData.getPlayerMagicData(serverPlayer).getPlayerCooldowns();
            cooldowns.getSpellCooldowns().forEach((key, value) -> cooldowns.decrementCooldown(value, (int) (value.getSpellCooldown() * amount)));
            cooldowns.syncToPlayer(serverPlayer);
        }

        public static void addSoulHunterSpellPower(ItemAttributeModifiers.Builder attributes) {
            attributes.add(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER,
                    new AttributeModifier(MalumMod.malumPath("spell_power"), 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    EquipmentSlotGroup.ARMOR);
        }

        public static void addSpellPowerToCurio(MalumCurioItem item, Multimap<Holder<Attribute>, AttributeModifier> map, float amount) {
            item.addAttributeModifier(map, io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER,
                    new AttributeModifier(MalumMod.malumPath("spell_power"), amount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }

        public static void addEchoingArcanaSpellCooldown(EchoingArcanaEffect effect) {
            effect.addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.COOLDOWN_REDUCTION, MalumMod.malumPath("echoing_arcana"), 0.02f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        }

        public static void addGluttonySpellPower(GluttonyEffect effect) {
            effect.addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER, MalumMod.malumPath("gluttony_spell_power_multiplier"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }

        public static void addSilencedNegativeAttributeModifiers(SilencedEffect effect) {
            effect.addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MANA_REGEN, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            effect.addAttributeModifier(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER, MalumMod.malumPath("silenced"), -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
    }
}