package com.sammy.malum.compability.irons_spellbooks;

import com.google.common.collect.Multimap;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.RuneSpellMasteryItem;
import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import io.redspace.ironsspellbooks.api.events.*;
import io.redspace.ironsspellbooks.api.magic.*;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.*;
import io.redspace.ironsspellbooks.item.weapons.*;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.*;

import java.util.UUID;

public class IronsSpellsCompat {

    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("irons_spellbooks");
        if (LOADED) {
            MinecraftForge.EVENT_BUS.addListener(LoadedOnly::spellDamage);
            MinecraftForge.EVENT_BUS.addListener(LoadedOnly::triggerReplenishing);
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

    public static void addSoulHunterSpellPower(Multimap<Attribute, AttributeModifier> attributes, UUID uuid) {
        if (LOADED) {
            LoadedOnly.addSoulHunterSpellPower(attributes, uuid);
        }
    }

    public static void addGluttonySpellPower(GluttonyEffect effect) {
        if (LOADED) {
            LoadedOnly.addGluttonySpellPower(effect);
        }
    }

    public static void addSpellPowerToCurio(MalumCurioItem item, Multimap<Attribute, AttributeModifier> map, float amount) {
        if (LOADED) {
            LoadedOnly.addSpellPowerToCurio(item, map, amount);
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

        public static void triggerReplenishing(LivingHurtEvent event) {
            DamageSource source = event.getSource();
            Entity directEntity = source.getDirectEntity();
            if (directEntity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.getAttackStrengthScale(0) > 0.8f) {
                    ItemStack stack = serverPlayer.getMainHandItem();
                    int level = stack.getEnchantmentLevel(EnchantmentRegistry.REPLENISHING.get());
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
            UpdateClient.SendManaUpdate(collector, magicData);
        }

        public static void recoverSpellCooldowns(ServerPlayer serverPlayer, float amount) {
            var cooldowns = MagicData.getPlayerMagicData(serverPlayer).getPlayerCooldowns();
            cooldowns.getSpellCooldowns().forEach((key, value) -> cooldowns.decrementCooldown(value, (int) (value.getSpellCooldown() * amount)));
            cooldowns.syncToPlayer(serverPlayer);
        }

        public static void addSoulHunterSpellPower(Multimap<Attribute, AttributeModifier> attributes, UUID uuid) {
            attributes.put(AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(uuid, "Malum Spell Power", 0.1f, AttributeModifier.Operation.MULTIPLY_BASE));
        }

        public static void addGluttonySpellPower(GluttonyEffect effect) {
            effect.addAttributeModifier(AttributeRegistry.SPELL_POWER.get(), "90523925-900e-49bf-b07d-12e2e7350f2d", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }

        public static void addSpellPowerToCurio(MalumCurioItem item, Multimap<Attribute, AttributeModifier> map, float amount) {
            item.addAttributeModifier(map, AttributeRegistry.SPELL_POWER.get(), uuid -> new AttributeModifier(uuid,
                    "Curio Spell Power", amount, AttributeModifier.Operation.MULTIPLY_BASE));
        }
            public static void addEchoingArcanaSpellCooldown(EchoingArcanaEffect effect) {
            effect.addAttributeModifier(AttributeRegistry.COOLDOWN_REDUCTION.get(), "8949b9d4-2505-4248-9667-0ece857af8a4", 0.02f, AttributeModifier.Operation.MULTIPLY_BASE);
        }

        public static void addSilencedNegativeAttributeModifiers(SilencedEffect effect) {
            effect.addAttributeModifier(AttributeRegistry.MANA_REGEN.get(), "47bad2ce-0eff-4472-9b97-fd7328eca05d", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
            effect.addAttributeModifier(AttributeRegistry.SPELL_POWER.get(), "dabe8298-d6db-4f8c-8fb4-a6c28a4148cd", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
    }
}