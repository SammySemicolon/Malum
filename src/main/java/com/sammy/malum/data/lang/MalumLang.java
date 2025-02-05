package com.sammy.malum.data.lang;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.*;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.registry.common.MobEffectRegistry.EFFECTS;
import static com.sammy.malum.registry.common.SoundRegistry.SOUNDS;
import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.registry.common.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class MalumLang extends LanguageProvider {
    public static MalumLang lang;

    public MalumLang(PackOutput gen) {
        super(gen, MalumMod.MALUM, "en_us");
        lang = this;
    }

    @Override
    protected void addTranslations() {
        CodexLangDatagen.generateEntries();

        var blocks = new HashSet<>(BLOCKS.getEntries());
        var items = new HashSet<>(ITEMS.getEntries());
        var sounds = new HashSet<>(SOUNDS.getEntries());
        var effects = new HashSet<>(EFFECTS.getEntries());
        var attributes = new HashSet<>(ATTRIBUTES.getEntries());
        var entities = new HashSet<>(ENTITY_TYPES.getEntries());

        add(DataHelper.take(blocks, BlockRegistry.PRIMORDIAL_SOUP).get(), "The Weeping Well");
        add(DataHelper.take(blocks, BlockRegistry.VOID_CONDUIT).get(), "The Weeping Well");

        add("item.malum.filled.spirit_jar", "Filled Spirit Jar");
        add("malum.spirit.description.stored_spirit", "Contains: ");
        add("malum.spirit.description.stored_soul", "Stores Soul With: ");

        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof EtherWallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b -> {
            String name = b.get().getDescriptionId().replaceFirst("block\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctItemName(name), "_"));
            add(b.get().getDescriptionId(), name);
        });
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && !(i.get() instanceof ItemNameBlockItem));
        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctItemName(name), "_"));
            add(i.get().getDescriptionId(), name);
        });

        sounds.forEach(s -> {
            String name = correctSoundName(s.getId().getPath()).replaceAll("_", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            add("malum.subtitle." + s.getId().getPath(), name);
        });

        effects.forEach(e -> {
            String name = DataHelper.toTitleCase(makeProperEnglish(e.getId().getPath()), "_");
            add("effect.malum." + BuiltInRegistries.MOB_EFFECT.getKey(e.get()).getPath(), name);
        });

        attributes.forEach(a -> {
            String name = DataHelper.toTitleCase(a.getId().getPath(), "_");
            add("attribute.name.malum." + BuiltInRegistries.ATTRIBUTE.getKey(a.get()).getPath(), name);
        });

        entities.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add("entity.malum." + BuiltInRegistries.ENTITY_TYPE.getKey(e.get()).getPath(), name);
        });

        for (MalumSpiritType spirit : SpiritTypeRegistry.SPIRITS.values()) {
            add(spirit.getSpiritDescription(), DataHelper.toTitleCase(spirit.getIdentifier() + "_spirit", "_"));
        }
        for (SoulwovenBannerPatternDataComponent pattern : SoulwovenBannerPatternDataComponent.REGISTERED_PATTERNS) {
            add(pattern.translationKey(), DataHelper.toTitleCase(pattern.type().getPath(), "_"));
        }
        for (ArtificeAttributeType attribute : ArtificeAttributeType.CRUCIBLE_ATTRIBUTES) {
            add(attribute.getLangKey(), DataHelper.toTitleCase(attribute.id.getPath().toLowerCase(Locale.ROOT), "_"));
        }

        for (DeferredHolder<GeasEffectType, ? extends GeasEffectType> geas : MalumGeasEffectTypeRegistry.GEAS_TYPES.getEntries()) {
            var effectType = geas.get();
            add(effectType.getLangKey(), DataHelper.toTitleCase(geas.getId().getPath().toLowerCase(Locale.ROOT), "_"));
        }

        add("malum.gui.slot", "Slot: ");

        add("malum.gui.augment.installed", "When installed: ");
        add("malum.gui.augment.type.augment", "Augment");
        add("malum.gui.augment.type.core_augment", "Core Augment");

        add("malum.gui.geas.sworn", "When Sworn: ");
        add("malum.gui.geas.any", "Geas");
        add("malum.gui.geas.day_effect", "During Day: ");
        add("malum.gui.geas.night_effect", "During Night: ");

        add("malum.gui.rite.type", "Type: ");
        add("malum.gui.rite.medium", "Polarity: ");
        add("malum.gui.rite.coverage", "Coverage: ");
        add("malum.gui.rite.effect", "Effect: ");

        add("malum.gui.rite.medium.runewood", "Runewood");
        add("malum.gui.rite.medium.soulwood", "Soulwood");

        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_NIGHTCHILD.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_DAYBLESSED.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_SHATTERING_ADDICT.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_FORTRESS.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_SHIELD.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_RECIPROCATION.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_REAPER.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_SKYBREAKER.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_MAVERICK.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_PROFANE_ASCETIC.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_THE_PROFANE_GLUTTON.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.PACT_OF_WYRD_RECONSTRUCTION.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.BOND_OF_BELOVED_CHAINS.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.BOND_OF_DEATHS_SEEKERS.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.OATH_OF_THE_OVERKEEN_EYE.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.OATH_OF_THE_OVEREAGER_FIST.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.OATH_OF_THE_UNDISCERNED_MAW.get(), "");
        addGeasDescription(MalumGeasEffectTypeRegistry.OATH_OF_UNSIGHTED_RESISTANCE.get(), "");

        addGeasDescription(MalumGeasEffectTypeRegistry.OATH_OF_THE_INVERTED_HEART.get(), "Witch Factor of Wrath");
        addGeasDescription(MalumGeasEffectTypeRegistry.OATH_OF_THE_GLEEFUL_TARGET.get(), "Witch Factor of Greed");

        add("malum.waveform_artifice.wavecharger", "Redstone Interpolation Time: %s");
        add("malum.waveform_artifice.wavebanker", "Redstone Pulse Duration: %s");
        add("malum.waveform_artifice.wavemaker", "Redstone Pulse Frequency: %s");
        add("malum.waveform_artifice.wavebreaker", "Redstone Pulse Delay: %s");

        addRiteEffectCategory(TotemicRiteEffect.MalumRiteEffectCategory.AURA);
        addRiteEffectCategory(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT);
        addRiteEffectCategory(TotemicRiteEffect.MalumRiteEffectCategory.DIRECTIONAL_BLOCK_EFFECT);
        addRiteEffectCategory(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT);
        addRiteEffectCategory(TotemicRiteEffect.MalumRiteEffectCategory.ONE_TIME_EFFECT);

        addRite(SpiritRiteRegistry.SACRED_RITE, "Rite of Healing", "Rite of Nourishment");
        addRite(SpiritRiteRegistry.WICKED_RITE, "Rite of Decay", "Rite of Empowerment");
        addRite(SpiritRiteRegistry.EARTHEN_RITE, "Rite of Warding", "Rite of the Arena");
        addRite(SpiritRiteRegistry.INFERNAL_RITE, "Rite of Haste", "Rite of the Hells");
        addRite(SpiritRiteRegistry.AERIAL_RITE, "Rite of Motion", "Rite of the Aether");
        addRite(SpiritRiteRegistry.AQUEOUS_RITE, "Rite of Loyalty", "Rite of the Seas");

        addRite(SpiritRiteRegistry.ARCANE_RITE, "Undirected Rite", "Unchained Rite");

        addRite(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "Rite of Growth", "Rite of Lust");
        addRite(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "Rite of Exorcism", "Rite of Culling");
        addRite(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "Rite of Destruction", "Rite of Shaping");
        addRite(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "Rite of Smelting", "Rite of Quickening");
        addRite(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "Rite of Gravity", "Rite of Unwinding");
        addRite(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "Rite of Sapping", "Rite of Drowning");

        add("malum.gui.ritual.type", "Ritual Type: ");
        add("malum.gui.ritual.tier", "Ritual Tier: ");

        for (MalumRitualType ritualType : RitualRegistry.RITUALS) {
            final String id = ritualType.id.getPath();
            String name = DataHelper.toTitleCase(id, "_");
            add("malum.gui.ritual." + id, name);
        }
        for (MalumRitualTier ritualTier : MalumRitualTier.TIERS) {
            final String id = ritualTier.identifier.getPath();
            String name = DataHelper.toTitleCase(id, "_");
            add("malum.gui.ritual.tier." + id, name);
        }

        add("item.malum.music_disc_arcane_elegy.desc", "Kultik - Arcane Elegy");
        add("item.malum.music_disc_aesthetica.desc", "Kultik - Aesthetica");

        add("curios.identifier.brooch", "Brooch");
        add("curios.modifiers.brooch", "When worn:");

        add("curios.identifier.rune", "Rune");
        add("curios.modifiers.rune", "When equipped:");

        add("malum.effect.positive", "+%s");
        add("malum.effect.negative", "-%s");

        add("malum.effect.curio.passive_healing", "Passive Healing");
        add("malum.effect.curio.scythe_chain", "Scythe Kill Chaining");
        add("malum.effect.curio.erratic_damage", "Erratic Damage Output");
        add("malum.effect.curio.crits", "Critical Strikes");
        add("malum.effect.curio.silence", "Silences Attackers");
        add("malum.effect.curio.extend_positive_effect", "Extends Positive Effects");
        add("malum.effect.curio.shorten_negative_effect", "Shortens Negative Effects");
        add("malum.effect.curio.attacked_resistance", "Damage Resistance When Attacked");
        add("malum.effect.curio.low_health_speed", "Speed at Low Health");
        add("malum.effect.curio.always_sprint", "Sprinting Always Available");
        add("malum.effect.curio.fervor", "Increased Mining Speed");
        add("malum.effect.curio.burning_damage", "Burning Damage");

        add("malum.effect.curio.spirits_heal", "Spirit Collection Replenishes Health");
        add("malum.effect.curio.spirits_xp", "Spirit Collection Generates Experience Points");
        add("malum.effect.curio.spirits_extend_effect", "Spirit Collection Aids Potion Durations");
        add("malum.effect.curio.spirits_weave_mana", "Spirit Collection Recovers Soul Ward");
        add("malum.effect.curio.spirits_weave_mana_irons_spellbooks", "Spirit Collection Recovers Spell Mana [Iron's Spellbooks]");
        add("malum.effect.curio.spirits_add_health", "Spirit Collection Grants Extra Hearts");
        add("malum.effect.curio.spirits_weave_resonance", "Spirit Collection Generates Arcane Resonance");
        add("malum.effect.curio.spirits_weave_resonance_irons_spellbooks", "Spirit Collection Reduces Spell Cooldown [Iron's Spellbooks]");
        add("malum.effect.curio.hunger_drain", "Actively Drains Hunger");
        add("malum.effect.curio.eat_rotten", "Rotten Foods are Tastier");
        add("malum.effect.curio.growing_gluttony", "Eating Rotten Foods Extends Gluttony");
        add("malum.effect.curio.explosion_drops_collected", "Automatic Collection of Explosion Drops");
        add("malum.effect.curio.bigger_explosions", "Improves Explosions");
        add("malum.effect.curio.no_sweep", "Disables Scythe Sweeping");
        add("malum.effect.curio.enhanced_maneuvers", "Augments Rebound and Ascension");
        add("malum.effect.curio.ascension_launch", "Ascension Launches Targets Upwards");
        add("malum.effect.curio.longer_ascension_cooldown", "Ascension Suffers a Longer Cooldown");
        add("malum.effect.curio.rebound_maelstrom", "Rebound Creates A Windborne Maelstrom");
        add("malum.effect.curio.longer_rebound_cooldown", "Rebound Suffers a Longer Cooldown");
        add("malum.effect.curio.friendly_enemies", "Reduces Enemy Aggression");
        add("malum.effect.curio.soul_ward_magic_resilience", "Soul Ward Magic Rerouting");
        add("malum.effect.curio.soul_ward_long_shatter_cooldown", "Lengthy Soul Ward Recharge upon Disintegration");
        add("malum.effect.curio.rotten_gluttony", "Eating Rotten Food Generates Gluttony");
        add("malum.effect.curio.scythe_counterattack", "Powerful Scythe Counterattack When Struck");
        add("malum.effect.curio.pacifist_recharge", "Cooldown Extends if the Scythe is Used");
        add("malum.effect.curio.full_health_fake_collection", "Striking Full Health Targets Triggers Spirit Collection Effects");
        add("malum.effect.curio.soul_ward_complete_absorption", "Soul Ward Absorbs All Damage");
        add("malum.effect.curio.soul_ward_escalating_integrity", "Soul Ward Gains Integrity When Nearing Disintegration");
        add("malum.effect.curio.spirits_gluttony", "Spirit Collection Generates Gluttony");
        add("malum.effect.curio.enchanted_explosions", "Explosions are Enchanted with %s");
        add("malum.effect.curio.explosions_spare_valuables", "Protects Valuable Items from Explosions");

        add("malum.effect.geas.hunger_as_withdrawal", "Addiction to Slaughter");
        add("malum.effect.geas.soul_ward_on_hit", "Magic Damage Recovers Soul Ward");
        add("malum.effect.geas.scythe_combo", "Scythe Cuts Create After-attacks");
        add("malum.effect.geas.only_scythe", "Regular Weapons Crumble In Your Hands");
        add("malum.effect.geas.fall_damage_auto_attack", "Outgoing Fall Damage Strikes With Your Weapon");
        add("malum.effect.geas.explosion_absorption", "Explosions Become Propulsive Gusts of Wind");
        add("malum.effect.geas.rocket_jumping", "Gusts of Wind Provide Greater Propulsion");
        add("malum.effect.geas.explosion_absorption_cooldown", "Repeated Activations Exhaust The Effect And Drain Stamina");
        add("malum.effect.geas.wyrd_reconstruction", "Prevents Death And Repeatedly Activates Spirit-Collection Effects");
        add("malum.effect.geas.wyrd_reconstruction_cooldown", "Effect Has A Lengthy Cooldown And Halves Arcane Resonance Until Recharged");
        add("malum.effect.geas.malignant_crit_leech", "Malignant Deliverance Leeches Life Essence");
        add("malum.effect.geas.malignant_crit_healing_overexertion", "Repeated Activations Nullify All Healing");
        add("malum.effect.geas.malignant_crit_reinforcement", "Malignant Deliverance Improves Conversion");
        add("malum.effect.geas.staff_homing", "Staff Projectiles Home In on Targets");
        add("malum.effect.geas.staff_autofire", "Staff Charges Automatically Fire");
        add("malum.effect.geas.authority_of_wrath", "Injuries, Emotions, Senses are Shared with Witnesses");
        add("malum.effect.geas.authority_of_wrath_arcane_resonance", "Arcane Resonance Favors Influence Radius");
        add("malum.effect.geas.authority_of_greed", "Aliments, Blessings, Curses are Paused When Wounded");
        add("malum.effect.geas.authority_of_greed_arcane_resonance", "Arcane Resonance Aids Stasis Duration");

        add("malum.effect.soul_based_damage", "Deals Soulbound Magic Damage");
        add("malum.effect.weight_of_worlds_crit", "Sometimes Strikes With Critical Force");
        add("malum.effect.weight_of_worlds_kill", "Kills Guarantee a Critical Strike");
        add("malum.effect.edge_of_deliverance_crit", "Follow-up Strikes Hit Critically");
        add("malum.effect.edge_of_deliverance_unpowered_attack", "Non-Critical Strikes Deal Reduced Damage");
        add("malum.effect.hex_bolts", "Charges a Burst of Mnemonic Blades");
        add("malum.effect.erosive_spread", "Charges a Spread of Eroding Sub-munitions");
        add("malum.effect.erosive_silence", "Erosion Damage Silences Targets");
        add("malum.effect.unwinding_chaos_volley", "Charges a Volley of Composite Energy");
        add("malum.effect.unwinding_chaos_burn", "Burn Damage Empowers Volley");
        add("malum.effect.sundering_anchor_damage_split", "Damage is Split Between Several Cuts");
        add("malum.effect.sundering_anchor_chaos_curse", "Each Cut Inflicts Chaos Curse");

        add("malum.effect.wayne_june.0", "The Iron Crown. Enigmatic, and Ubiquitous");
        add("malum.effect.wayne_june.1", "A Semi-Circle, Radiating Five Points of Power. A Symbol Hidden Deep in the Iconography of Every Ancient Empire");
        add("malum.effect.wayne_june.2", "The Point of No Return Welcomes You, With Open Arms");
        add("malum.effect.wayne_june.3", "The Greatest Horror It Would Seem, Is Nothing At All");
        add("malum.effect.wayne_june.4", "An Ocean of Emptiness, Slowly Swallowing the World");
        add("malum.effect.wayne_june.5", "A Nebulous Nightmare, an Apocalypse that Only We Can Oppose");
        add("malum.effect.wayne_june.6", "Stagnant And Sprawling, This Hellish Abyss Extends Beyond Sanity Itself");
        add("malum.effect.wayne_june.7", "We Travel Through The Incalculable Dimensions Of Human Weakness");
        add("malum.effect.wayne_june.8", "Success, So Long Pursued, Is Rewarded Only With Creeping Revalation");
        add("malum.effect.wayne_june.9", "You Have Cowered In Your Cowering Denial Long Enough");
        add("malum.effect.wayne_june.10", "Let Us Drag Your Agglutinated Indignities Out Into The Light");

        add("malum.spirit.flavour.sacred", "Innocent");
        add("malum.spirit.flavour.wicked", "Malicious");
        add("malum.spirit.flavour.arcane", "Fundamental");
        add("malum.spirit.flavour.eldritch", "Esoteric");
        add("malum.spirit.flavour.aerial", "Swift");
        add("malum.spirit.flavour.aqueous", "Malleable");
        add("malum.spirit.flavour.infernal", "Radiant");
        add("malum.spirit.flavour.earthen", "Steady");
        add("malum.spirit.flavour.umbral", "Antithesis");

        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.spirit_focusing", "Spirit Focusing");
        add("malum.jei.spirit_repair", "Spirit Repair");
        add("malum.jei.spirit_rite", "Spirit Rites");
        add("malum.jei.runeworking", "Runeworking");
        add("malum.jei.weeping_well", "The Weeping Well");
        add("malum.jei.spirit_transmutation", "The Unchained Rite");

        add("itemGroup.malum_basis_of_magic", "Malum: Basis of Magic");
        add("itemGroup.malum_arcane_construct", "Malum: Arcane Construct");
        add("itemGroup.malum_natural_wonders", "Malum: Born from Arcana");
        add("itemGroup.malum_metallurgic_magics", "Malum: Metallurgic Magics");
        add("itemGroup.malum_geas", "Malum: Bleghhh:P x3");
        add("itemGroup.malum_ritual_shards", "Malum: Ritual Shards");
        add("itemGroup.malum_cosmetics", "Malum: Self Expression");

        addDeathMessage(DamageTypeRegistry.VOODOO, "%1$s had their soul shattered by %2$s", "%1$s had their soul shattered by %2$s using %3$s");
        addPlayerlessDeathMessage(DamageTypeRegistry.VOODOO_PLAYERLESS, "%1$s had their soul shattered", "%1$s had their soul shattered while trying to escape %2$s");

        addPlayerlessDeathMessage(DamageTypeRegistry.VOID, "%1$s underwent reality erosion", "%1$s underwent reality erosion while trying to escape %2$s");

        addDeathMessage(DamageTypeRegistry.NITRATE, "%1$s had their soul detonated by %2$s", "%1$s had their soul detonated by %2$s using %3$s");
        addPlayerlessDeathMessage(DamageTypeRegistry.NITRATE_PLAYERLESS, "%1$s had their soul detonated", "%1$s had their soul detonated while trying to escape %2$s");

        addDeathMessage(DamageTypeRegistry.SCYTHE_MELEE, "%1$s was sliced in half by %2$s", "%1$s was sliced in half by %2$s using %3$s");
        addDeathMessage(DamageTypeRegistry.SCYTHE_SWEEP, "%1$s was sliced in half by %2$s", "%1$s was sliced in half by %2$s using %3$s");
        addDeathMessage(DamageTypeRegistry.SCYTHE_REBOUND, "%1$s was boomeranged by %2$s", "%1$s was boomeranged by %2$s using %3$s");
        addDeathMessage(DamageTypeRegistry.SCYTHE_ASCENSION, "%1$s was cleaved using ascension by %2$s", "%1$s was cleaved using ascension by %2$s using %3$s");
        addDeathMessage(DamageTypeRegistry.SCYTHE_COMBO, "%1$s was sliced in half and then again by %2$s", "%1$s was sliced in half and then again by %2$s using %3$s");
        addDeathMessage(DamageTypeRegistry.SCYTHE_MAELSTROM, "%1$s was sliced and diced by %2$s", "%1$s was sliced and diced by %2$s using %3$s");

        addDeathMessage(DamageTypeRegistry.HIDDEN_BLADE_PHYSICAL_COUNTER, "%1$s was sliced into innumerable pieces by %2$s", "%1$s was sliced into innumerable pieces by %2$s using %3$s");
        addDeathMessage(DamageTypeRegistry.HIDDEN_BLADE_MAGIC_COUNTER, "%1$s had their soul sliced into innumerable pieces by %2$s", "%1$s had their soul sliced into innumerable pieces by %2$s using %3$s");

        addDeathMessage(DamageTypeRegistry.TYRVING, "%1$s had their soul scarred by %2$s", "%1$s had their soul scarred by %2$s using %3$s");

        addDeathMessage(DamageTypeRegistry.SOULWASHING_PROPAGATION, "%1$s was caught in %2$s's karmic flow", "%1$s was caught in %2$s's karmic flow using %3$s");
        addDeathMessage(DamageTypeRegistry.SOULWASHING_RETALIATION, "%1$s was caught in %2$s's karmic flow", "%1$s was caught in %2$s's karmic flow using %3$s");

        addJEEDEffectDescription(MobEffectRegistry.GAIAS_BULWARK, "An earthen carapace surrounds your body, functioning as extra armor.");
        addJEEDEffectDescription(MobEffectRegistry.EARTHEN_MIGHT, "Your fists and tools are reinforced with earth, increasing your strength.");
        addJEEDEffectDescription(MobEffectRegistry.MINERS_RAGE, "Your tools are bolstered with radiance, increasing your mining and attack speed.");
        addJEEDEffectDescription(MobEffectRegistry.IFRITS_EMBRACE, "The warm embrace of fire coats your soul, mending your seared scars.");
        addJEEDEffectDescription(MobEffectRegistry.ZEPHYRS_COURAGE, "The zephyr propels you forward, increasing your movement speed.");
        addJEEDEffectDescription(MobEffectRegistry.AETHERS_CHARM, "The heavens call for you, increasing jump height and decreasing gravity.");
        addJEEDEffectDescription(MobEffectRegistry.POSEIDONS_GRASP, "You reach out for further power, increasing your reach and item pickup distance.");
        addJEEDEffectDescription(MobEffectRegistry.ANGLERS_LURE, "Let any fish who meets my gaze learn the true meaning of fear; for I am the harbinger of death. The bane of creatures sub-aqueous, my rod is true and unwavering as I cast into the aquatic abyss. A man, scorned by this uncaring Earth, finds solace in the sea. My only friend, the worm upon my hook. Wriggling, writhing, struggling to surmount the mortal pointlessness that permeates this barren world. I am alone. I am empty. And yet, I fish.");

        addJEEDEffectDescription(MobEffectRegistry.REACTIVE_SHIELDING, "A Runic Power bolsters your armor and toughness by a tenth.");
        addJEEDEffectDescription(MobEffectRegistry.SACRIFICIAL_EMPOWERMENT, "A Runic Power reaps extra scythe proficiency for your blade.");

        addJEEDEffectDescription(MobEffectRegistry.ASCENSION, "Eases your fall and reduces gravity after a successful Scythe Ascenison.");
        addJEEDEffectDescription(MobEffectRegistry.GLUTTONY, "You feed on the vulnerable, increasing magic proficiency at the expense of hunger./");
        addJEEDEffectDescription(MobEffectRegistry.CANCEROUS_GROWTH, "You are emboldened by uncontrolled growth, increasing maximum health.");
        addJEEDEffectDescription(MobEffectRegistry.ECHOING_ARCANA, "You are made wiser by uncontrolled magnification, increasing arcane resonance.");
        addJEEDEffectDescription(MobEffectRegistry.WICKED_INTENT, "You bring forth a powerful counter attack, your next scythe attack will unleash an impossible volley of cuts.");
        addJEEDEffectDescription(MobEffectRegistry.SILENCED, "You are silenced, leaving your magical capabilities neutered.");
        addJEEDEffectDescription(MobEffectRegistry.GRIM_CERTAINTY, "The Weight of Worlds oscillates, sealing the next strike as a critical blow.");
        addJEEDEffectDescription(MobEffectRegistry.IMMINENT_DELIVERANCE, "The Edge of Deliverance oscillates, sealing it's next strike as a critical blow.");

        addTetraMaterial("soul_stained_steel", "Soulstained Steel");
        addTetraMaterial("hallowed_gold", "Hallowed Gold");
        addTetraMaterial("runewood", "Runewood");
        addTetraMaterial("soulwood", "Soulwood");
        addTetraMaterial("tainted_rock", "Tainted Rock");
        addTetraMaterial("twisted_rock", "Twisted Rock");
        addTetraMaterial("spirit_fabric", "Spirit Fabric");

        addTetraImprovement("malum.soul_strike", "Soul Strike", "The item's material allows it to strike the soul.");

        addEnchantmentNameAndDescription(EnchantmentRegistry.ANIMATED, "Improves attack speed.");
        addEnchantmentNameAndDescription(EnchantmentRegistry.REBOUND, "Allows the Scythe to be thrown much like a boomerang when used.");
        addEnchantmentNameAndDescription(EnchantmentRegistry.ASCENSION, "Enables the Scythe to propel the player upwards, pushing away nearby enemies when used.");
        addEnchantmentNameAndDescription(EnchantmentRegistry.REPLENISHING, "Reduces the Staff's ranged attack cooldown when dealing melee damage.");
        addEnchantmentNameAndDescription(EnchantmentRegistry.CAPACITOR, "Adds Reserve Staff Charges for use with the staff");
        addEnchantmentNameAndDescription(EnchantmentRegistry.HAUNTED, "Improves the Weapon's Magic Damage");
        addEnchantmentNameAndDescription(EnchantmentRegistry.SPIRIT_PLUNDER, "Reaps extra Spirits when killing an enemy.");

        addAttributeLibAttributeDescription(AttributeRegistry.SCYTHE_PROFICIENCY, "Damage multiplier for Scythes");
        addAttributeLibAttributeDescription(AttributeRegistry.SPIRIT_SPOILS, "Flat increase to spirits looted from slain foes");
        addAttributeLibAttributeDescription(AttributeRegistry.ARCANE_RESONANCE, "Bonus potency for spirit-collection effects");

        addAttributeLibAttributeDescription(AttributeRegistry.SOUL_WARD_INTEGRITY, "A percentile increase in durability for Soul Ward");
        addAttributeLibAttributeDescription(AttributeRegistry.SOUL_WARD_RECOVERY_RATE, "A percentile increase in recovery rate for Soul Ward");
        addAttributeLibAttributeDescription(AttributeRegistry.SOUL_WARD_CAPACITY, "The capacity for Soul Ward");

        addAttributeLibAttributeDescription(AttributeRegistry.RESERVE_STAFF_CHARGES, "A capacity for extra staff charges, replenished overtime, consumed when casting.");
        addAttributeLibAttributeDescription(AttributeRegistry.MALIGNANT_CONVERSION, "A percentile conversion rate in which certain magical attributes are converted into armor, armor toughness and magic resistance");

    }

    @Override
    public String getName() {
        return "Malum Lang Entries";
    }

    public String makeProper(String s) {
        s = s
                .replaceAll("Of", "of")
                .replaceAll("The", "the")
                .replaceAll("Soul Stained", "Soulstained")
                .replaceAll("Soul Hunter", "Soulhunter");
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public void addRite(TotemicRiteType riteType, String basicName, String corruptName) {
        add(riteType.translationIdentifier(false), basicName);
        add(riteType.translationIdentifier(true), corruptName);
    }

    public void addRiteEffectCategory(TotemicRiteEffect.MalumRiteEffectCategory category) {
        add(category.getTranslationKey(), DataHelper.toTitleCase(category.name().toLowerCase(), "_"));
    }

    public void addGeasDescription(GeasEffectType effectType, String description) {
        add(effectType.getLangKey() + ".tooltip", description);
    }

    public void addTetraMaterial(String identifier, String name) {
        add("tetra.material." + identifier, name);
        add("tetra.material." + identifier + ".prefix", name);
    }

    public void addTetraImprovement(String identifier, String name, String description) {
        add("tetra.improvement." + identifier + ".name", name);
        add("tetra.improvement." + identifier + ".description", description);
    }

    public void addPlayerlessDeathMessage(ResourceKey<DamageType> damageType, String base, String player) {
        final String key = "death.attack." + damageType.location().getPath();
        add(key, base);
        add(key + ".player", player);
    }

    public void addDeathMessage(ResourceKey<DamageType> damageType, String base, String item) {
        final String key = "death.attack." + damageType.location().getPath();
        add(key, base);
        add(key + ".item", item);
    }

    public void addEnchantmentNameAndDescription(ResourceKey<Enchantment> enchantment, String desc) {
        var name = enchantment.location().getPath();
        var key = "enchantment.malum." + name;
        add(key, DataHelper.toTitleCase(name, "_"));
        add(key + ".desc", desc);
    }

    public void addAttributeLibAttributeDescription(DeferredHolder<Attribute, Attribute> attribute, String desc) {
        add("attribute.name.malum." + attribute.getId().getPath() + ".desc", desc);
    }

    public void addJEEDEffectDescription(Supplier<MobEffect> mobEffectSupplier, String description) {
        add(mobEffectSupplier.get().getDescriptionId() + ".description", description);
    }

    public String correctSoundName(String name) {
        if ((name.endsWith("_step"))) {
            return "footsteps";
        }
        if ((name.endsWith("_place"))) {
            return "block_placed";
        }
        if ((name.endsWith("_break"))) {
            return "block_broken";
        }
        if ((name.endsWith("_hit"))) {
            return "block_breaking";
        }
        return name;
    }

    public String correctItemName(String name) {
        if (name.contains("music_disc")) {
            return "music_disc";
        }
        if ((!name.endsWith("_bricks"))) {
            if (name.contains("bricks")) {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if ((!name.endsWith("_boards"))) {
            if (name.contains("boards")) {
                name = name.replaceFirst("boards", "board");
            }
        }
        if (name.contains("_fence") || name.contains("_button")) {
            if (name.contains("planks")) {
                name = name.replaceFirst("_planks", "");
            }
        }
        if (name.startsWith("trans_")) {
            //TODO: replace this with just...
            // replace(ItemRegistry.WEAVERS_WORKBENCH.get(), this::makeProperEnglish);
            // no need to run the damn code on every single item, while filtering prideweaves
            return name;
        }
        return makeProperEnglish(name);
    }

    public String makeProperEnglish(String name) {
        String[] replacements = new String[]{"ns_", "rs_", "ts_"};
        String properName = name;
        for (String replacement : replacements) {
            int index = properName.indexOf(replacement);
            if (index != -1) {
                properName = properName.replaceFirst("s_", "'s_");
                break;
            }
        }
        return properName;
    }
}
