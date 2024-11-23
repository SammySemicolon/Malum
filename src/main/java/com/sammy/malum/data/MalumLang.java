package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.*;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.registry.common.MobEffectRegistry.EFFECTS;
import static com.sammy.malum.registry.common.SoundRegistry.SOUNDS;
import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.registry.common.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.registry.common.item.EnchantmentRegistry.ENCHANTMENTS;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class MalumLang extends LanguageProvider {
    public MalumLang(PackOutput gen) {
        super(gen, MalumMod.MALUM, "en_us");
    }

    @Override
    protected void addTranslations() {
        var blocks = new HashSet<>(BLOCKS.getEntries());
        var items = new HashSet<>(ITEMS.getEntries());
        var sounds = new HashSet<>(SOUNDS.getEntries());
        var enchantments = new HashSet<>(ENCHANTMENTS.getEntries());
        var effects = new HashSet<>(EFFECTS.getEntries());
        var attributes = new HashSet<>(ATTRIBUTES.getEntries());
        var entities = new HashSet<>(ENTITY_TYPES.getEntries());
        var spirits = new ArrayList<>(SpiritTypeRegistry.SPIRITS.values());
        var crucibleTuningTypes = CrucibleTuning.CrucibleAttributeType.values();

        add("item.malum.music_disc_arcane_elegy.desc", "Kultik - Arcane Elegy");
        add("item.malum.music_disc_aesthetica.desc", "Kultik - Aesthetica");

        add(DataHelper.take(blocks, BlockRegistry.PRIMORDIAL_SOUP).get(), "The Weeping Well");
        add(DataHelper.take(blocks, BlockRegistry.VOID_CONDUIT).get(), "The Weeping Well");

        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof EtherWallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b -> {
            String name = b.get().getDescriptionId().replaceFirst("block\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctItemName(name), "_"));
            add(b.get().getDescriptionId(), name);
        });
        DataHelper.getAll(items, i -> i.get() instanceof SpiritJarItem).forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.malum\\.", "").replaceFirst("block\\.malum\\.", "");
            String filled = "filled_" + name;
            add("item.malum." + filled, makeProper(DataHelper.toTitleCase(filled, "_")));
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

        enchantments.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getDescriptionId(), name);
        });

        effects.forEach(e -> {
            String name = DataHelper.toTitleCase(makeProperEnglish(e.getId().getPath()), "_");
            add("effect.malum." + ForgeRegistries.MOB_EFFECTS.getKey(e.get()).getPath(), name);
        });

        attributes.forEach(a -> {
            String name = DataHelper.toTitleCase(a.getId().getPath(), "_");
            add("attribute.name.malum." + ForgeRegistries.ATTRIBUTES.getKey(a.get()).getPath(), name);
        });

        entities.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add("entity.malum." + ForgeRegistries.ENTITY_TYPES.getKey(e.get()).getPath(), name);
        });

        spirits.forEach(s -> add(s.getSpiritDescription(), DataHelper.toTitleCase(s.identifier + "_spirit", "_")));

        for (CrucibleTuning.CrucibleAttributeType value : crucibleTuningTypes) {
            if (value.equals(CrucibleTuning.CrucibleAttributeType.NONE)) {
                continue;
            }
            final String translation = value.translation();
            String name = DataHelper.toTitleCase(value.toString().toLowerCase(Locale.ROOT), "_");
            add(translation, name);
        }

        add("malum.gui.crucible.attribute.weakest_boost", "Weakest Boost");
        add("malum.gui.crucible.attribute.tuning_potency", "Tuning Potency");

        add("malum.gui.augment.slot", "Slot: ");
        add("malum.gui.augment.installed", "When installed: ");
        add("malum.gui.augment.type.augment", "Augment");
        add("malum.gui.augment.type.core_augment", "Core Augment");

        add("malum.gui.rite.type", "Type: ");
        add("malum.gui.rite.medium", "Polarity: ");
        add("malum.gui.rite.coverage", "Coverage: ");
        add("malum.gui.rite.effect", "Effect: ");

        add("malum.gui.rite.medium.runewood", "Runewood");
        add("malum.gui.rite.medium.soulwood", "Soulwood");

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
            final String id = ritualType.identifier.getPath();
            String name = DataHelper.toTitleCase(id, "_");
            add("malum.gui.ritual." + id, name);
        }
        for (MalumRitualTier ritualTier : MalumRitualTier.TIERS) {
            final String id = ritualTier.identifier.getPath();
            String name = DataHelper.toTitleCase(id, "_");
            add("malum.gui.ritual.tier." + id, name);
        }


        add("curios.identifier.brooch", "Brooch");
        add("curios.modifiers.brooch", "When worn:");

        add("curios.identifier.rune", "Rune");
        add("curios.modifiers.rune", "When equipped:");

        add("malum.gui.curio.positive", "+%s");
        add("malum.gui.curio.negative", "-%s");

        add("malum.gui.curio.effect.passive_healing", "Passive Healing");
        add("malum.gui.curio.effect.scythe_chain", "Scythe Kill Chaining");
        add("malum.gui.curio.effect.erratic_damage", "Erratic Damage Output");
        add("malum.gui.curio.effect.crits", "Critical Strikes");
        add("malum.gui.curio.effect.silence", "Silences Attackers");
        add("malum.gui.curio.effect.extend_positive_effect", "Extends Positive Effects");
        add("malum.gui.curio.effect.shorten_negative_effect", "Shortens Negative Effects");
        add("malum.gui.curio.effect.attacked_resistance", "Damage Resistance When Attacked");
        add("malum.gui.curio.effect.low_health_speed", "Speed at Low Health");
        add("malum.gui.curio.effect.always_sprint", "Sprinting Always Available");
        add("malum.gui.curio.effect.fervor", "Increased Mining Speed");
        add("malum.gui.curio.effect.burning_resistance", "Damage Resistance While Burning");

        add("malum.gui.curio.effect.spirits_heal", "Spirit Collection Replenishes Health");
        add("malum.gui.curio.effect.spirits_xp", "Spirit Collection Generates Experience Points");
        add("malum.gui.curio.effect.spirits_extend_effect", "Spirit Collection Aids Potion Durations");
        add("malum.gui.curio.effect.spirits_weave_mana", "Spirit Collection Recovers Soul Ward");
        add("malum.gui.curio.effect.spirits_weave_mana_irons_spellbooks", "Spirit Collection Recovers Spell Mana [Iron's Spellbooks]");
        add("malum.gui.curio.effect.spirits_add_health", "Spirit Collection Grants Extra Hearts");
        add("malum.gui.curio.effect.spirits_weave_resonance", "Spirit Collection Generates Arcane Resonance");
        add("malum.gui.curio.effect.spirits_weave_resonance_irons_spellbooks", "Spirit Collection Reduces Spell Cooldown [Iron's Spellbooks]");
        add("malum.gui.curio.effect.hunger_drain", "Actively Drains Hunger");
        add("malum.gui.curio.effect.eat_rotten", "Rotten Foods are Tastier");
        add("malum.gui.curio.effect.growing_gluttony", "Eating Rotten Foods Extends Gluttony");
        add("malum.gui.curio.effect.explosion_drops_collected", "Automatic Collection of Explosion Drops");
        add("malum.gui.curio.effect.bigger_explosions", "Improves Explosions");
        add("malum.gui.curio.effect.better_conduit_power", "Conduit Power Provides Numerous Benefits");
        add("malum.gui.curio.effect.no_sweep", "Disables Scythe Sweeping");
        add("malum.gui.curio.effect.enhanced_maneuvers", "Augments Rebound and Ascension");
        add("malum.gui.curio.effect.friendly_enemies", "Reduces Enemy Aggression");
        add("malum.gui.curio.effect.soul_ward_magic_resilience", "Soul Ward Magic Resilience");
        add("malum.gui.curio.effect.rotten_gluttony", "Eating Rotten Food Generates Gluttony");
        add("malum.gui.curio.effect.scythe_counterattack", "Powerful Scythe Counterattack When Struck");
        add("malum.gui.curio.effect.pacifist_recharge", "Cooldown Extends if the Scythe is Used");
        add("malum.gui.curio.effect.full_health_fake_collection", "Striking Full Health Targets Triggers Spirit Collection Effects");
        add("malum.gui.curio.effect.soul_ward_physical_absorption", "Soul Ward Absorbs Physical Damage Equally to Magic Damage");
        add("malum.gui.curio.effect.spirits_gluttony", "Spirit Collection Generates Gluttony");
        add("malum.gui.curio.effect.enchanted_explosions", "Explosions are Enchanted with %s");
        add("malum.gui.curio.effect.explosions_spare_valuables", "Protects Valuable Items from Explosions");

        addSimpleEntryHeader("chronicles_of_the_void", "Chronicles of the Void", "A magecraft of madness");
        addSimpleEntryHeader("chronicles_of_the_soul", "Chronicles of the Soul", "A magecraft of miracles");

        addSimpleEntryHeader("void.the_weeping_well", "The Weeping Well", "Gate to the unknown");
        addPages("void.the_weeping_well",
                "I have discovered a... structure. One with implications beyond nearly anything I've found before, because the existence of this Weeping Well implies I am not the first to touch the arcana.",
                "The Well, which I name for its constant mournful tone, appears to be a small pool of... something. I am not entirely sure what, as though it doesn't appear to be liquid, it certainly doesn't physically interact like a solid does.",
                "The implication comes from the fact that the structure around it appears to be constructed of Tainted Rock, as if preventing the substance inside from spreading further. That said, the core of the structure itself is sturdier than the rock I have created, almost approaching Bedrock in toughness.",
                "In what was perhaps an ill-advised course of action, my first instinct was to throw a rock at it. It seemed stable enough, if it had been here for so long... and to my relief, nothing dangerous occurred. The rock shot out seconds later, with a belching sound and a spray of what appears to be a harmless concentration of the stuff in the Well.",
                "Certain items I had on me from the mining trip, such as a bit of Brilliance and a nodule of Cthonic Gold, tugged slightly against my pockets while I'm near the well. I wondered what that meant, and, if the stone was ejected out, surmised it made sense to try throwing them in too.",
                "When I did so, the materials had been transformed once ejected - interestingly, both the rock and these were ejected due south. Might there be other materials, other creations I could derive from this? And the fact that both materials that tugged were related to the arcana is fascinating.",
                "I am not a historian, so I will not investigate the cultural implications, beyond the fact that Tainted Rock, so far as I know, can only be created through Spirit Infusion. This implies a civilization once touched the arcana and... tried to contain it? Feared it? Used it? Is this civilization related to the other structures buried within the earth? I do not know, but I hope to find out.");

        addEntryHeader("void.material_study_soulstone", "Material Study: Soulstone", "An old friend");
        addHeadline("void.material_study_soulstone", "Study: Soulstone");
        addPages("void.material_study_soulstone",
                "Soulstone is, of course, nothing new. It is the basis of my craft. But, when most raw metals are passed through the Well, they become raw Soulstone instead.",
                "Does this imply that Soulstone itself is metallic? Perhaps. It would certainly explain the ease with which it attunes iron. This also implies that Soulstone itself is, somehow, connected to whatever the Well is. Perhaps it is an ensouled area of reality, or a means of bestowing souls? Both are doubtful, but I cannot discard even doubtful theories just yet.");

        addEntryHeader("void.material_study_mnemonic_fragment", "Material Study: Condensed Brilliance", "Not experience, but memory");
        addHeadline("void.material_study_mnemonic_fragment", "Study: Mnemosyne");
        addPages("void.material_study_mnemonic_fragment",
                "When passed through the Well, Brilliance becomes a substance I call Mnemosyne. These Mnemonic Fragments appear to be Brilliance in physical property, but with the contained power more condensed, more nuanced.",
                "Rather than containing simple experience, the sense I get from holding it is as though I am holding an entire memory, context and all.\nDoes this imply the Weeping Well is alive, and that this is its memory? Or is it collecting the memory of the soul which created the Brilliance in the first place, binding it into this more dense form? And why can I feel impressions while simply holding the stone?");

        addSimpleEntryHeader("void.material_study_mnemonic_fragment.reexamination", "Reexamination: Mnemnosyne", "Patterns holding true");
        addPages("void.material_study_mnemonic_fragment.reexamination",
                "Mnemnosyne appears to have the same internal patterning as Brilliance, but on a much smaller and more detailed scale. Presumably this is what gives it its properties.");

        addEntryHeader("void.material_study_null_slate", "Material Study: Refined Soulstone", "A blank slate, perhaps a precursor");
        addHeadline("void.material_study_null_slate", "Study: Null Slate");
        addPages("void.material_study_null_slate",
                "When passed through the Well, Soulstone becomes a substance I call Null Slate. While physically it is similar to Soulstone, it appears to be utterly devoid of a soul... and yet it interacts with the arcane much as Soulstone does.",
                "Might this be what Soulstone is, before it gains a soul's energy? I cannot determine how it interacts with souls, nor have I been able to transfer that property, as I have done to make Soulstained Steel. My only theory is that somehow the complete absence of arcana, beyond even the trace amounts threading existence, has an arcane power of its own. But what power would that be?");

        addSimpleEntryHeader("void.material_study_null_slate.reexamination", "Reexamination: Null Slate", "Attuned to Umbral");
        addPages("void.material_study_null_slate.reexamination",
                "A complete absence of arcana creating an effect. It should be obvious what Null Slate truly is - Soulstone, but attuned to the Void instead of the arcane.",
                "Perhaps still a precursor, or maybe they are related in other ways... The physical makeup is the same as Soulstone's. It follows that the differing properties are purely from the medium the stone interacts with.");

        addEntryHeader("void.material_study_void_salts", "Material Study: Purified Ash", "A clue to the nature of souls");
        addHeadline("void.material_study_void_salts", "Study: Void Salts");
        addPages("void.material_study_void_salts",
                "When passed through the Well, Hex Ash is reduced to a substance I call Void Salt. This dark powder appears to be chemically similar to Hex Ash, but without the carbonization that gives the Ash its name.",
                "It appears to be comprised of an unknown and unstable metal, bonded to something caustic I cannot identify. It is baffling beyond the physical, though. Carbon is the basis of life, and yet removing carbon makes this substance... almost seem to move? I haven't been able to verify that experimentally, but I could swear that the material is alive and shifting.");

        addSimpleEntryHeader("void.material_study_void_salts.reexamination", "Reexamination: Void Salts", "Concerning");
        addPages("void.material_study_void_salts.reexamination",
                "The fact that " + italic("absence") + " is creating " + italic("presence") + " implies Umbral. Perhaps it " + italic("is") + " alive... but with life defined by deeper nothingness instead of the presence of matter. Might this indicate there is more complex life adapted to the Void?");

        addEntryHeader("void.material_study_auric_embers", "Material Study: Blazing Exaltation", "The essence of progress");
        addHeadline("void.material_study_auric_embers", "Study: Auric Ember");
        addPages("void.material_study_auric_embers",
                "When passed through the Well, Blaze Powder becomes a substance I call Auric Ember. An ethereal flame of gold, yet a physical object at the same time. It burns like charcoal, yet its flame has no combustion.",
                "While such a brilliant substance may stand out among the other materials I have obtained from the Well, it fits the pattern cleanly. The Well has stripped something away from each material I pass through it, be that impurities, a portion of matter, or something more esoteric. Here, it appears to strip away anything besides the purity of fire, producing transformation incarnate.");

        addSimpleEntryHeader("void.material_study_auric_embers.reexamination", "Reexamination: Auric Ember", "Not stripping away, but inverting");
        addPages("void.material_study_auric_embers.reexamination",
                "The Well does not, as I previously thought, strip things away. It inverts them, replacing them with voidish counterparts. Auric Ember is perhaps the purest example of this - anything that is not the essence of progress is cast in void shadow, creating a material that is " + italic("more") + " than perfectly attuned to a purpose.");

        addEntryHeader("void.material_study_malignant_lead", "Material Study: Putrefacted Gold", "Perfection cast to base");
        addHeadline("void.material_study_malignant_lead", "Study: Malignant Lead");
        addPages("void.material_study_malignant_lead",
                "When passed through the Well, Cthonic Gold is warped into Malignant Lead. Much of the Arcana bonded to the gold resolves itself into Wicked, dragging the metal from the alchemist's apex to the lowest of materials.",
                "There appears to be roughly half as much arcana bound to the metal as before. Was it stripped away by the Well, or transmuted into something I have not yet isolated and identified? Either could be true. This might be related to the arcane interactivity displayed by Null Slate...");

        addEntryHeader("void.material_study_malignant_lead.reexamination", "Reexamination: Malignant Lead", "Wicked and Umbral");
        addHeadline("void.material_study_malignant_lead.reexamination", "Reexamining Malignant Lead");
        addPages("void.material_study_malignant_lead.reexamination",
                "Malignant Lead is at the moment my only means of assessing the physical form of an Umbral Crystal - for what else could have caused it to lose precisely half of its arcane weight?",
                "I have not had success in isolating the microcrystals like I have with Cthonic Gold or the Wicked crystals in the lead, but until I can obtain more, this is my best avenue to research Umbral's structure.");

        addSimpleEntryHeader("spirit_metals.reexamination", "Reexamination: Spirit Metals", "A different principle");
        addPages("spirit_metals.reexamination",
                "The inherent structures of the arcana simply don't exist in spirit metals, and yet they interact with the arcane all the same. There might be something analogous, but if so, I hardly have instruments precise enough to measure it. Why is this is different from the raw stones I have investigated, with even Soulstained Steel differing in structure vastly from Soulstone?",
                "This might explain why Cthonic Gold has arcana fused into its structure, unlike the spirit metals - they might be different phenomena, one a metal 'tuned' to the arcana, another physically alloyed with it. I wonder if I can tune other metals similarly...");

        addSimpleEntryHeader("spirit_stones.reexamination", "Reexamination: Spirit Stones", "Tessellation of crystal structures");
        addPages("spirit_stones.reexamination",
                "It appears that the crystal structures of at least Wicked and Sacred crystals can be tessellated infinitely, as that is exactly what my deconstruction and analysis of samples of Twisted and Tainted Rocks has found them to be. Is it possible that other spirit stones - or rather, tessellations - might exist? Very likely.",
                "But what would their properties be? Sacred and Wicked create stone which accept or reject magic... so might Aerial and Earthen create stone that either moves or locks magic in place? Redundant for both, with Hallowed Gold... Infernal might be used to accelerate while Aqueous mutates, though, which might be useful.",
                "I don't think I have the proper context to identify what Arcane, Eldritch, and Umbral might create. I lack the full understanding of what they represent, as destructive testing isn't an option for Umbral as of yet. But, with the proximity of bedrock to the Void... might bedrock's indestructibility be a sign of the Umbral stone?");

        addSimpleEntryHeader("cthonic_gold.reexamination", "Reexamination: Cthonic Gold", "A new principle in old material");
        addPages("cthonic_gold.reexamination",
                "I now know why I could not create Cthonic Gold - I did not understand the principles it was built on. Fusing crystal into matter is not something unique to this material. It may be possible to force crystals to form inside of objects to make similar 'natural' arcane alloys.",
                "Either I need to find a way to phase the crystal through solid matter, or I need to find a way to cause a similar effect to the Spirit Jar's crystal formation in a medium other than air. Either one might allow me to make Cthonic Gold, and potentially much, much more.");

        addEntryHeader("spirit_minerals.reexamination", "Reexamination: Soulstone and Brilliance", "More than just arcana");
        addHeadline("spirit_minerals.reexamination", "Reexamining Spirit Minerals");
        addPages("spirit_minerals.reexamination",
                "I now know the arcana have physical properties with meaning. I can use these properties to create a staff, and likely for many more things. But Soulstone and Brilliance... as far as I can tell, for every other base material relevant to the arcana save Runewood I work with, there are either spirit crystals fused within or patterned in the structure.",
                "The fact that Null Slate is physically identical to Soulstone implies a greater principle. I posit that Soulstone and Brilliance have similar fundamental resonances to the arcana, structures of inherent power at a precision far beyond what I can measure. This implies interaction with the arcane is simply... a quirk of an object's topology? I don't know how I would replicate it myself, but if I can...");

        addEntryHeader("void.catalyst_lobber", "Catalyst Lobber", "Progress overtakes");
        addHeadline("void.catalyst_lobber", "Catalyst Lobber");
        addPages("void.catalyst_lobber",
                "The flame of progress is a potent one, which bulldozes everything in its search for advancement. There had to be a destructive way to harness it, and so there was.",
                "I've created a device out of a pair of lamplighter's tongs I call the Catalyst Lobber. It \"unlocks\" Auric Embers by retuning them, turning their flame from a gentle one into an explosive blaze, containing the result until it's ready to fire.",
                "I implemented a safety, because... well, I don't want to rebuild my lab again. The flames are violently explosive. Standard explosive precautions work just as well, such as obsidian, of course.");

        addSimpleEntryHeader("fragment.void.black_crystal", "Scribbled notes", "Incomprehensible");
        addPages("fragment.void.black_crystal",
            italic("You attempt to read the entry, but the text seems to slide off the eyes, escaping from your mind every time you grasp it. What little fragments stick with you form an impression of something besides these materials being cast into the Well..."));

        addSimpleEntryHeader("void.black_crystal", "A Black Crystal", "A mistake, or a boon?");
        addPages("void.black_crystal",
                "Well, I now know what happens when a living being, or at least, a sapient one, enters the Well.",
                "I had grown too comfortable in my experiments, and tripped over one of the flasks of reagent I had left around... directly into the Well's black maw. As I'm writing this, clearly I survived... Though I'd rather not test that again.",
                "I was spat out by what I now know is liquid, much like the items I have thrown in. Further tests with monsters and cattle showed they do " + italic("not") + " get rejected, simply seeming to vanish into the pool, and I see no reason to waste resources to test that exhaustively. Especially with my attention set on what came back out with me.",
                "As if I had shattered a soul - and considering mine was the only one present, that is a concerning possibility, though all readings of myself I have taken are within tolerances - a black spirit crystal emerged from the Well alongside me, which I collected. Does this herald a ninth arcana? If so, what impulse does it represent? This will need more study.");

        addSimpleEntryHeader("fragment.void.umbral_arcana", "Strange equations", "Assuming an absence of existence...");
        addSimpleEntryHeader("void.umbral_arcana", "Umbral Arcana", "Utter impossibility");
        addPages("void.umbral_arcana",
                "I do not understand this arcana. What is it? It isn't any of the eight I know, and barely seems like one at all... yet a spirit crystal it remains. It can be contained in jars like the others, shares many of the same properties... But there is one deep and fundamental difference.",
                "This crystal " + italic("does not exist.") + " That is not to say that it cannot be obtained, or touched, or even seen. There is no matter there. This... " + italic("Umbral") + " arcana is a void. Would that make it the opposite of both Arcane and Eldritch? Both lack direction of impulse, and both are power. Ergo, Umbral arcana is the absence of power, where impulse is irrelevant.",
                "It is possible there are two types of this arcana, I suppose. One to pair with the null impulse of the Arcane, and one to pair with the complete impulse of the Eldritch. If those types exist, it is functionally impossible to distinguish them. They " + italic("must") + " act the same. Without power, the impulse is meaningless.",
                "And yet, despite being a void, it is power. Or perhaps the lack of power creates a pressure differential? I am not certain. Either way, it can be used. Infused, in theory, even, although that is hard to wrap my head around. It acts like matter, but is not. It is power, and the absence of it.",
                "My research into this arcana must continue. It has to. If anything holds the secrets of achieving the pinnacle of thaumaturgy, it is this paradoxical void. And I will grasp it with both hands.");

        addEntryHeader("fragment.void.inverse_and_hybrid_arcana", "A failed experiment", "An attempt to create something new?");
        addEntryHeader("void.inverse_and_hybrid_arcana", "Inverse and Hybrid Arcana?", "Failed theories");
        addHeadline("void.inverse_and_hybrid_arcana", "Theoretical Arcana");
        addPages("void.inverse_and_hybrid_arcana",
                "If Arcane and Eldritch had inverses, it stood to reason that there might be inverses for the other spirit crystals - an absolute absence of fire creating the inverse of Infernal, for example. Pulling the power of a spirit from a jar is one way to form a crystal, and in theory, recreating similar conditions could allow for a different type of crystal to form.",
                "Through a combination of Soulwood rites and careful placement of crystals, I was able to create the theoretical environment for such a crystal for the six base Arcana... But nothing happened at all. It would be far more useful if I could determine how to create the same environment for Umbral, but as of yet I have not determined how to remove " + italic("all") + " arcana from an area.",
                "Similarly, no amount of tuning an environment's contents with combinations of arcana was able to cause a crystal to form other than one of the base six. This implies that hybrid arcana don't exist, at least, not in the same way - that there are a finite number of states the arcana can be stable in. But the arcana can combine in other ways...");

        addEntryHeader("void.material_study_arcana", "Material Study: the Arcana", "New depths to old wells");
        addHeadline("void.material_study_arcana", "Study: Spirit Crystal");
        addPages("void.material_study_arcana",
                "I had not investigated deeper into the physical properties of the spirit crystals before now. This can be excused, as I was focused on their magical implications rather than their physical... but it isn't only the Umbral spirit that portrays strange properties. They all do.",
                "First and foremost, the spirit crystals - assumedly also the Umbral spirit, though I only have one - are physically identical to others of their type to any degree I am able to discern. Not simply similar, but " + italic("precisely") + " the same, down to at least millionths of a block's scale. This raised the question, of course, of attempting to carve or break part of one.",
                "Through testing - not quite " + italic("exhaustively,") + " as I have not tested Umbral - it appears that shape is the only one a crystal can exist in. Any removal or damage of even the smallest amount of the crystal is impossible, and the physical shape remains inviolable until enough force is applied to break it.",
                "Even more interestingly, the spirit crystals, while physically not particularly strong, appear to be utterly chemically inert, and I tried a very extensive set of reactants. Do they even have matter, in the traditional sense? And if not, what does that mean for the Umbral crystal?",
                "I am not sure of the precise implications of these physical properties, but given the strangeness of the Umbral crystal, there is clearly more to the more mundane aspect of the arcana than I knew.");

        addSimpleEntryHeader("void.staves_as_foci", "Staves as Foci", "Imitating the arcana");
        addPages("void.staves_as_foci",
                "Consideration of the properties I observed in spirit crystals led me to wonder if the structure itself was important somehow. To test this, I constructed a Mnemnosyne replica - the condensed soul memory being the closest substance I could think of - of the Wicked Arcana, precise to a scale of hundreds of thousandths of a block.",
                "I did not expect it to explode in my face.\n\nBut explosions are useful, if harnessed. So I did it again, but this time, I gave the false crystal a structure to operate off of - a housing of Soulwood and Soulstained Steel to direct it outwards. The resulting staff acts akin to a rite in miniature when I focus on it, though the resulting effects are different.",
                "When used in melee, staves are... adequate. I would tend to prefer a scythe, but I suppose this works well enough. They tend to focus more on magic damage than on physical damage, which admittedly can be helpful at times. But what makes them special is what happens if I " + italic("use") + " it.",
                "This Mnemonic Hex Staff is named for its simple effect of launching balls of liquefied Wicked energy, like a witch's hex. It takes a moment to activate, and cannot be quite used consecutively, but each deals potent damage to anything they hit.",
                "I have not found success in creating any other false Arcana this way, though that might be a matter of material. Mnemnosyne may simply resonate with Wicked through the death required to create Brilliance.");

        addSimpleEntryHeader("void.staves_as_foci.ring_of_the_endless_well", "Ring of the Endless Well", "Storing staff charges");
        addPages("void.staves_as_foci.ring_of_the_endless_well",
                "I have recreated the Arcane spirit in false form. A core of Mnemnosyne in Null Slate housing appears to neutralize the Wicked attunement of the Mnemnosyne, leaving raw arcana as the pattern it mimics. The false Arcane spirit appears to hold a charge, one very similar to the Wicked crystal in my staff.",
                "I have fashioned a ring - the Endless Well - with these false crystals in housing. The ring appears to concentrate my focus, allowing me to avoid the time the staff takes to charge back up. The ring charges slower, as it is not a symbolic " + italic("focus") + " like my staff, but it can hold three attacks in reserve for faster use.");

        addSimpleEntryHeader("void.malignant_pewter", "Malignant Pewter", "Progress cast false");
        addPages("void.malignant_pewter",
                "Now that I have identified the composition of Malignant Lead, it is easier to plan usages for it. As one might expect from Umbral's strangeness, alloying it creates a metal with fascinating properties. As Cthonic Gold was brought low to make the Lead, this Pewter seeks to do to others. It seeks to erode, to unmake, to unwind man's advances.",
                "While this property has its mundane uses - damaging armor more when used as a weapon, along with shattering souls - it has esoteric ones as well. Malignant Pewter isn't magically inert, nor an absorber of magic. Instead, it's as if magic cannot " + italic("form") + " within or around it; symbolically laying the pursuit of knowledge low.",
                "The metal itself is quite tough and takes a keen edge, but wearing an antimagic material would have its issues... and its uses. I have plans for a set of armor fashioned of it, which should prove quite potent, to harness these.");

        addEntryHeader("void.malignant_stronghold_armor", "Malignant Stronghold Armor", "The defense of the inevitable");
        addHeadline("void.malignant_stronghold_armor", "The Malignant Stronghold");
        addPages("void.malignant_stronghold_armor",
                "Malignant Pewter rejects magic, and as such, wearing it might seem an odd choice for a mage. And yet, it is " + italic("because") + " I am a mage that the Stronghold Armor I have made is so potent. It stands as an inverse to the Soulstained Steel armor beneath the Pewter plating - my soul is unwarded, but I am certainly not.",
                "In unraveling magic, energy still remains. While worn, the armor will react to that energy, growing stronger alongside it. Any strictly defensive or offensive magical attribute to be inscribed upon my soul is instead absorbed by the metal, granting armor and magic resistance bonuses. The only unique case I've found is magic proficiency, which the metal absorbs half as much.");

        addSimpleEntryHeader("void.weight_of_worlds", "The Weight of Worlds", "Existential burdens externalized");
        addPages("void.weight_of_worlds",
                "Harnessing the reality-corroding properties of Malignant Pewter is easy. What fits the revocation of progress better than a crude weapon? An axe, which I named the Weight of Worlds in a fit of pique. It is slow, it is clumsy, it is physical... and it is " + italic("powerful") + " beyond measure.",
                "Rarely, the Weight seems to lend its approval to my slaughter, doubling the force I strike with, with no seeming source. I believe slaughter is the key because of the resonance I feel when I take a life with it - that resonance seems to expend itself when I swing next, guaranteeing that doubling of force.");

        addSimpleEntryHeader("void.edge_of_deliverance", "The Edge of Deliverance", "A mad screamer's melody");
        addPages("void.edge_of_deliverance",
                "Progress can be revoked, and crude can overcome grand. But progress can be undone by progress, can it not? A scythe, familiar in ways the axe was not, which I name the Edge of Deliverance. It is the true opposite - where the Weight of Worlds is sharp and heavy, the Edge is thin and winding.",
                "This focuses the inconsistent force of the Weight of Worlds, seeking slaughter to wet the blade. Instead, a single attack's taste of blood empowers the scythe, creating an alternating pattern of killing strength.");

        addSimpleEntryHeader("void.erosion_scepter", "Erosion Scepter", "May mages fear my might");
        addPages("void.erosion_scepter",
                "A new false arcana has been created, using Malignant Pewter as a focus. In its corrosive reversal, the metal serves entropy - and so Eldritch answers its call. Like other uses of the metal, the resulting crystal in its Void Salt suspension seeks to unwind what humanity has wrought.",
                "The Erosion Scepter, made using this false crystal, is a staff which fires bolts of a crawling, corruptive substance - almost like Blight or the Void Salt used in its creation. Each charge fires two volleys, each of four bolts. These bolts chew away at the souls of their victims, which, delightfully, appears to inhibit the use of magic for a time.",
                "Each bolt applies another layer of this effect, reducing the effective magical might and soul force of the target by a tenth. Naturally, this can stack up to a complete seal on the target, and every application reinforces and lengthens the durations of the others.",
                "Striking a foe with the staff will inflict the same decay twice on the victim's soul, making it useful as more than just a weapon of desperation in close quarters. Not all magics require a stable soul, but the pain of the degradation of self should help distract them nonetheless.");

        addSimpleEntryHeader("void.ring_of_growing_flesh", "Ring of Growing Flesh", "Creeping and crawling");
        addPages("void.ring_of_growing_flesh",
                "I have overclocked the Ring of Curative Talent, creating a ring that does not simply heal, but overheal, granting additional health instead of regeneration whenever I collect a spirit crystal.");

        addSimpleEntryHeader("void.ring_of_echoing_arcana", "Ring of Echoing Arcana", "I can see see the future " + italic("future future"));
        addPages("void.ring_of_echoing_arcana",
                "Overclocking the Ring of Curative Talent created a useful ring, so I have applied that principle to another ring, Manaweaving, to not only bind, but create resonance in magic. The Ring of Echoing Arcana grants Arcane Resonance whenever I collect a spirit crystal, empowering all my other spirit-collection effects.");

        addEntryHeader("void.ring_of_gruesome_concentration", "Ring of Gruesome Concentration", "Consume and incorporate");
        addHeadline("void.ring_of_gruesome_concentration", "Gruesome Concentration");
        addPages("void.ring_of_gruesome_concentration",
                "Why would Gluttony be restricted to the collection of spirits? By eating foul food, I can make myself hungrier, and so cultivate and concentrate what remains, increasing my magical might even as I starve.");

        addSimpleEntryHeader("void.necklace_of_the_watcher", "Necklace of the Watcher", "It looks back");
        addPages("void.necklace_of_the_watcher",
                "Souls emit energy when damaged, not merely when shattered. This necklace allows me to harness that energy, causing effects that normally only occur when I collect a spirit to also happen when I strike an enemy at full health.");

        addEntryHeader("void.necklace_of_the_hidden_blade", "Necklace of the Hidden Blade", "A knife at their backs");
        addHeadline("void.necklace_of_the_hidden_blade", "The Hidden Blade");
        addPages("void.necklace_of_the_hidden_blade",
                "The Narrow Edge concentrates my scythe's edge, but the Hidden Blade goes further, inverting the edge and making it hunger. I lose the sweeping attack, yes, but as I am harmed, the blade drinks of my pain- feeding upon it to enable a seemingly impossible flurry of cuts and slashes.",
                "Upon activation, the necklace remains inert for a total of ten seconds, requiring rest and concentration in order to recover it's effect. Attacking at any point in this state will prolong this absence of function.",
                "The counterattack's nature is a strange one, one I have not fully identified. It is as though the blade asserts its existence in multiple places at once, demanding reality make it so.");

        addSimpleEntryHeader("introduction", "Introduction", "On the nature of souls");
        addPages("introduction",
                "\"Within our world, every living being has a soul. That soul is consciousness, what animates the body, and the meeting point between matter and magic. These represent our existence; as the body is presented to the physical world, so the soul is to the arcane.\"",
                "I seem to have stumbled upon something peculiar: a form of magic so far undocumented. I could hardly call myself a magus if I refused the opportunity to study it. In this codex, the Encyclopedia Arcana, I write my research into this power, hoping to document everything about it.",
                "The energies this thaumaturgical discipline manipulates seem to be rooted in the soul. More accurately, they are the energies of the soul, the inclinations and impulses that make up each one of us.",
                "So far, what I have described is basic. But I have found a way to separate, and then condense, the impulse of a soul into a physical form I call a spirit crystal. This forms the basis of my research.",
                "The natures of the soul I condense influence the crystal's properties. Each soul is slightly different, and that can result in changes to the crystals formed. I believe these spirit crystals to be just the breakthrough I need.");

        addSimpleEntryHeader("spirit_crystals", "Spirit Crystals", "Matter and magic");
        addPages("spirit_crystals",
                "The soul is a notoriously fickle thing. Even confirming its existence is difficult, requiring the highest thaumaturgies to get a reading. That is what sets spirit arcana apart from other magic. We don't need grand assemblies and esoteric artifice to see a soul. Simply destroying it is proof enough.",
                "A material I have named Soulstone is the means by which we do so. It appears mundane until refined, but once it is rid of impurities, it seems... out of phase with the world. By creating a blade using it as a core, I should be able to strike not only the physical form, but also the soul, shattering it to energy before it can disperse.",
                "These energies, as previously noted, have different 'frequencies' of sorts. A being burning with light would have a soul that reflects that radiance, and a being prone to adaptation would have a soul as malleable as itself. Occasionally, the energy has no flavor to it at all, leaving only the raw impulse of creation behind. That type of crystal bears further study.");

        addSimpleEntryHeader("soulstone", "Soulstone", "Out of phase");
        addPages("soulstone",
                "Sometimes, it appears that matter can be charged with the energies of a soul, despite not having a soul of its own.",
                "This serves as the basis for spirit arcana - the ensouling of the soulless. Soulstone is an ore that exists more in the arcane than the physical, and, refined, presents many uses for my magic. It strongly radiates magic.");

        addSimpleEntryHeader("natural_quartz", "Natural Quartz", "Deep in the earth");
        addPages("natural_quartz",
                "Natural Quartz is, as the name implies, a natural equivalent of the nether resource. It's used for most of the same things. It's rare, and found deep underground, sometimes in geodes.");

        addSimpleEntryHeader("cthonic_gold", "Cthonic Gold", "Fused with the arcane");
        addPages("cthonic_gold",
                "Cthonic Gold is a strange yet useful metal. Its physical makeup is that of gold, yet its properties are entirely distinct. The ore is found deep underground in the deepslate layer of the world, rooted deeply into existing veins of gold.",
                "Physically, Cthonic Gold resembles pyrite, albeit with the density of true gold. The altered nature of the metal appears to derive from a mix of earthen and infernal arcana somehow bonded to its physical structure, creating a strange alloy. Arcana does not normally interact with metal in this way, at least in my experiments.",
                "The alloy of physical and metaphysical causes this material to serve as a bridging point, a gate between realms, so to speak. Or perhaps a guardian of those gates? It exists as purely physical, yet the arcane acknowledges its passage.",
                "I'm not sure what use this metal will have quite yet, but I doubt I will be short for applications for a material with such atypical properties.");

        addSimpleEntryHeader("runewood", "Runewood", "Arcane oak");
        addPages("runewood",
                "Runewood is a strange mix of magic and nature, and a fairly common one at that. While pretty, I am more interested in practicality. Runewood is soaked in magic, and as such, can serve as the basis for the arcane.",
                "The tree is predominantly found within large open plains, however it can also be found in forests. The tree can be best identified by it's leaves, proudly displaying an orange-yellow palette.");
        addHeadline("runewood.arcane_charcoal", "Arcane Charcoal");
        addPages("runewood.arcane_charcoal",
                "Runewood's charcoal, as magic-infused as it is, burns with an arcane fervor for longer than regular charcoal. This makes it rather useful for fueling any smelting I need to do.");
        addHeadline("runewood.runic_sap", "Runic Sap");
        addPages("runewood.runic_sap",
                "Runewood trees tend to have a buildup of sticky sap on the sides of their logs. When this happens, if you strip off the bark, you'll be able to bottle the sap, which makes for a rejuvenating drink.",
                "The sap can also be used to create sapballs, able to be used interchangeably with slimeballs for most recipes. Runic sap can also be turned into a block form, which is rather sticky.");

        addSimpleEntryHeader("blazing_quartz", "Blazing Quartz", "Ignition");
        addPages("blazing_quartz",
                "It stands to reason that a place like the nether would have a substance that was flammable, and Blazing Quartz certainly fits the bill. It acts much like coal, even being able to form torches. A useful substance, even if fairly mundane.");

        addSimpleEntryHeader("brilliance", "Brilliance", "The stuff of experience");
        addPages("brilliance",
                "Brilliance is a term I have heard bandied about for what others call experience. It is a part of the soul, though improperly attached, and can be collected and used for enchanting and repairs.",
                "What many don't know is that it can condense into a physical form. I have heard rumors of solid Brilliance coming from crushing ore, but the most reliable source is small clusters of ore where a soul faded away, leaving its experiences engraved on the stone.");

        addSimpleEntryHeader("scythes", "Scythes", "Harvest");
        addPages("scythes",
                "After several inert attempts, I have socketed Soulstone into a weapon that can reliably harvest these spirit crystals. The long blade allows time for the body to die before I strike the soul, while also providing a wide sweep attack. It isn't as sharp as a sword, but for my purposes, it will do nicely.",
                "What I had managed to do before with careful, painstaking experiments, the scythe did in a matter of seconds. The souls of the monsters I slew shattered, streaming bits of deeply hued matter towards me: the spirit crystals. Finally, my research can begin in earnest.");
        addSimpleEntryHeader("scythes.enchanting", "Enchanting a Scythe", "Two ways to skin a soul");
        addPages("scythes.enchanting",
                "At its core, the scythe enchants like other weapons or tools I've used. It has its own set of enchantments, of course, due to its differing nature, but can take Unbreaking and the like as well as a sword can.");
        addHeadline("scythes.enchanting.spirit_plunder", "Spirit Plunder");
        addPages("scythes.enchanting.spirit_plunder",
                "This is not a perfect method. Some of the soul is unavoidably lost in the moment between blade and stone. But by enchanting the blade, that loss can be mitigated, and more of the soul condensed. This unfortunately strains the stone, and can result in my scythe's durability decreasing.");
        addHeadline("scythes.enchanting.haunted", "Haunted");
        addPages("scythes.enchanting.haunted",
                "Soulstone interacts with souls, obviously. The crudest and simplest use is blunt force, shattering a soul like a pane of glass. If I condense the arcane power of the stone, it would therefore hit and shatter harder, perhaps even damaging the body with the backlash.");
        addHeadline("scythes.enchanting.animated", "Animated");
        addPages("scythes.enchanting.animated",
                "If power can be concentrated, it can be diffused. Interestingly, by diffusing the power that would be drawn together for Haunted into the rest of the scythe, the entire weapon gains a conceptual \"lightness\", making its swings lighter and faster.");
        addHeadline("scythes.enchanting.rebound", "Rebound");
        addPages("scythes.enchanting.rebound",
                "A scythe reaps. That is its purpose, and that can be harnessed. Should a reaper need my hand? By harnessing the purpose of the blade as a weapon, I can let it act on its own to some extent, as if it were a boomerang. The stronger the enchantment, the sooner it’s ready to throw again.");
        addHeadline("scythes.enchanting.ascension", "Ascension");
        addPages("scythes.enchanting.ascension",
                "A fascinating bit of symbolism is the role of the tool in raising man up. Civilization was built by the food a scythe harvests. By harnessing the purpose of the blade as a tool, I can… well, raise myself, the winds I rise on sharp as the blade itself to my foes. Rebound is incompatible; literally, at cross purposes with this enchantment.");

        addSimpleEntryHeader("esoteric_reaping", "Esoteric Reaping", "Leaked magic");
        addPages("esoteric_reaping",
                "When a being dies, its soul disperses. This is basic theory, and well proven by this point. It's been proposed that sometimes, that power leaks into the body of the creature as it dies, to explain the existence of reagents they drop. That hadn't been proven yet.",
                "But now, with my scythe, I have proved it beyond doubt. When a soul is shattered, even if only for a brief moment, the energy collides with what's left of it's vessel. This phenomena appears to create a strong reaction, a change of sorts.",
                "I have discovered four reagents born through this process, which I will detail further in this entry. In summary, the flesh of zombies can curdle to Rotting Essence; the bones of skeletons can crystallize to Grim Talc; the wings of phantoms can spin to Astral Weave; and the magic of endermen can coalesce into Warp Flux.");
        addHeadline("esoteric_reaping.rotting_essence", "Rotting Essence");
        addPages("esoteric_reaping.rotting_essence",
                "When exposed to this magic, the flesh of the undead can curdle into Rotting Essence, a toxic and foul substance that smells like death itself.");
        addHeadline("esoteric_reaping.grim_talc", "Grim Talc");
        addPages("esoteric_reaping.grim_talc",
                "Bones exposed to this magic can crystallize into Grim Talc, a useful mineral that can also be broken down into bonemeal.");
        addHeadline("esoteric_reaping.astral_weave", "Astral Weave");
        addPages("esoteric_reaping.astral_weave",
                "The membrane of a phantom will spin into Astral Weave with this magic, a mystic cloth with strange arcane properties.");
        addHeadline("esoteric_reaping.warp_flux", "Warp Flux");
        addPages("esoteric_reaping.warp_flux",
                "The magic that envelops the endermen coalesces into Warp Flux, a strange essence that seems to be inimical to natural law.");

        addSimpleEntryHeader("spirit_infusion", "Spirit Infusion", "Creation of wonders");
        addPages("spirit_infusion",
                "By using Runewood's natural magic as a base, I have designed the altar that will serve as the basis for my magecraft - the Spirit Altar. It is the other piece of the equation, the use for the arcana. By infusing them into items, and using the energies to effect other fusions, I can begin to explore this.",
                "To use the altar, I must lay the item I wish to infuse on top of it, along with an appropriate set of arcana. If I wish to fuse other items in the process, I must place them on some form of Runewood item holder. They must be within four blocks of the altar to work.",
                "Once all the arcana are present, the power within the crystals will begin to flow into the central item. If other items are fused in, they are pulled in during this process. When all of that is done, the product of the infusion will appear. It " + italic("is") + " rather slow, though...");
        addHeadline("spirit_infusion.hex_ash", "Hex Ash");
        addPages("spirit_infusion.hex_ash",
                "My first product with this process is a powder I call Hex Ash, after its color. It is a simple and useful grit, with the niter and sulfur mostly transmuted by the raw arcana, leaving a mixture of reagent and carbon.");
        addHeadline("spirit_infusion.living_flesh", "Living Flesh");
        addPages("spirit_infusion.living_flesh",
                "Next, for the sake of understanding how spirit arcana interacts with living substance, I have created... " + italic("something") + " which is now known as Living Flesh. It is a disgusting meaty chunk completely unfit for human consumption. Who knows if I'll end up finding a proper use for it.");
        addHeadline("spirit_infusion.alchemical_calx", "Alchemical Calx");
        addPages("spirit_infusion.alchemical_calx",
                "Lastly, I have created an experimental substrate I named Alchemical Calx. It's initially strong and tallow-like, but when met with a lesser amount of force it turns extremely malleable. It's bound to prove an useful ingredient.");

        addEntryHeader("primary_arcana", "Primary Arcana", "The components of magic");
        addHeadline("primary_arcana.sacred", "Sacred Spirit");
        addPages("primary_arcana.sacred",
                "Sacred arcana is essential to any magic that enhances life. It can be defined as holy, the energy of particularly vibrant life, or even the simplicity of youth. It is pure and untainted, making it a useful component.",
                "It is the impulse of purity, the desire for optimism. It is found in those who are passive, innocent, or holy in origin.");
        addHeadline("primary_arcana.wicked", "Wicked Spirit");
        addPages("primary_arcana.wicked",
                "Wicked arcana is inimical to life. It seeks death and despair, and warps the living into something else. Even touching the crystal makes my soul shudder in pain.",
                "It is the impulse of corruption, the desire to cause suffering. It is found in those whose souls lack life, or those twisted by malice.");
        addHeadline("primary_arcana.arcane", "Arcane Spirit");
        addPages("primary_arcana.arcane",
                "While other arcana are impulses of the soul, it would be more accurate to say that the arcane is the impulse of the arcana themselves. This " + bold("raw arcana") + " lacks any particular quality, simply being undirected spiritual power.",
                "It is the impulse of creation, the first principle of all things. It is found within those who have opened their soul to power, or whose origins lie in that power.",
                "I suspect that this arcana, unlike others, can join a soul over time. Most things about the soul are defined early on. The impulses that define you are woven into your very self, after all. But lacking an impulse, perhaps this arcana is different. A witch was not born a mage, after all.");

        addSimpleEntryHeader("elemental_arcana", "Elemental Arcana", "Focused magic");
        addHeadline("elemental_arcana.aerial", "Aerial Spirit");
        addPages("elemental_arcana.aerial",
                "Aerial arcana is the simplest of the elemental arcana. That very simplicity that gives it its utility. I have heard tales of magi soaring on the winds, ruling the skies. If any arcana is to make those tales achievable, it is this.",
                "It is the impulse of speed given form, the desire to run and to soar. It is found in anything particularly swift or mobile.");
        addHeadline("elemental_arcana.earthen", "Earthen Spirit");
        addPages("elemental_arcana.earthen",
                "Earthen arcana is relatively simple as well. It lends itself easily to strength, communion with nature, and the force of vitality. If I wish to enhance myself, or reshape the world, this arcana will be the key.",
                "It is the impulse of stability, the desire to stand and endure. It is found in anything that is unconcerned with the world around it changing.");
        addHeadline("elemental_arcana.infernal", "Infernal Spirit");
        addPages("elemental_arcana.infernal",
                "Infernal arcana is more complex, but not nearly as malicious as it might seem. Fire is dangerous, yes, but it is also the source of light and heat. It can burn something down as easily as it can fuse two things together.",
                "It is the impulse of light, the desire to burn. It is found in anything that shines brightly, as well as most denizens of the nether.");
        addHeadline("elemental_arcana.aqueous", "Aqueous Spirit");
        addPages("elemental_arcana.aqueous",
                "And finally, Aqueous arcana. It is strange, to say the least. It is malleable, yet doesn't do much by itself. It grants an affinity for the sea, but beyond that, its effects are rather esoteric.",
                "It is the impulse of change, the desire to adapt. It is found in anything that embodies that adaptation, as well as anything which lives in the flowing waters.");

        addEntryHeader("eldritch_arcana", "Eldritch Arcana", "For every push there is a pull");
        addHeadline("eldritch_arcana", "Eldritch Spirit");
        addPages("eldritch_arcana",
                "Eldritch arcana is a mystery to me. It has no impulse, none that I can understand, at least. And yet, it doesn't act like raw arcana. It changes, emboldens, enlightens... Raw arcana merely amplifies. This... this alters.",
                "I am not sure I understand what impulse creates this arcana. I find it in very few beings, and those I find it in are those who already defy explanation. But if it must be the pair to raw arcana, then that would imply that it's the impulse of endings, the " + italic("last") + " principle of all things.\n\nI do not like that thought.");

        addEntryHeader("spirit_stones", "Spirit Stones", "Arcana suffused");
        addHeadline("spirit_stones.tainted_rock", "Tainted Rock");
        addPages("spirit_stones.tainted_rock",
                "Stone is reluctant to change, but nothing can endure the power of an unchained soul forever. By using raw arcana to force that change, I have created stones with useful magical properties. With Sacred arcana as the catalyst, it forms Tainted Rock, a stone that dissipates magic nearby.");
        addHeadline("spirit_stones.twisted_rock", "Twisted Rock");
        addPages("spirit_stones.twisted_rock",
                "With Wicked arcana's nature as the opposite of Sacred, it follows that the stone produced with it would act opposite. Twisted Rock has most of the same properties as Tainted Rock, but pushes magic away from it instead of dissipating it. Both can be fashioned into item holders, as Runewood can.");

        addSimpleEntryHeader("ether", "Ether", "All the colors of the wind");
        addPages("ether",
                "A common task for an apprentice magus is to create a flame that burns without heat or fuel. It serves as a test of magical control, as well as the ability to circumvent natural phenomena. Spirit arcana, of course, can produce this wonder as well.",
                "A peculiarity of Ether's flame is that it resonates with colors. As if it was leather to be dyed, I can tint its appearance. It is an emitter of light, so dyeing it darker colors will lower the intensity rather than change the color of the flame itself.");
        addHeadline("ether.iridescent", "Iridescent Ether");
        addPages("ether.iridescent",
                "As if this was not enough, I have found a way to imbue a second color into my Ether, creating Iridescent Ether. When created, this form of Ether locks in its original color, leaving a new, " + italic("second") + " color open to dyeing. The light will shift from the original color into the new color towards the peak of the flames.",
                "Getting the right coloring for this can be tricky, though. As stated, once Ether is made Iridescent, its original color can no longer be changed. This is hardly an issue, but should be kept in mind when tinting your flames.");

        addSimpleEntryHeader("spirit_fabric", "Spirit Fabric", "Wicked weaves");
        addPages("spirit_fabric", "Spirit Fabric is a light yet sturdy material that acts as an insulator for spirit energies. While other materials have the same properties, it's not exactly practical to craft a pouch or clothing from stone. I'm not willing to go quite so far for my research as to try wearing something like " + italic("that") + ".");
        addHeadline("spirit_fabric.pouch", "Spirit Pouch");
        // addPages("spirit_fabric.pouch", "But this fabric works wonderfully for storing spirit crystals. It keeps the arcana condensed within in a massless state, and will even store spirits I pick up before they so much as clutter my pockets. The amount of crystals it can store is near-infinite, making it incredibly convenient to carry around.");
        addPages("spirit_fabric.pouch", "But this fabric works wonderfully for storing spirit crystals. It keeps the arcana condensed within, and will even store spirits I pick up before they so much as clutter my pockets. It can store as many spirits as a single chest, making it quite convenient to carry around.");

        addSimpleEntryHeader("soulhunter_gear", "Soulhunter Gear", "Glass cannon");
        addPages("soulhunter_gear",
                "Spirit Fabric is an insulator, but that doesn't mean it has to dampen magic. This set of armor is designed to focus that magic, effectively amplifying the user's arcane abilities. Unfortunately, it's not exactly the strongest of materials, and it protects me just about as much as leather clothing.");

        addSimpleEntryHeader("spirit_focusing", "Spirit Focusing", "Mystic replication");
        addPages("spirit_focusing",
                "Using the opposing polarities of Twisted and Tainted Rock, I have created a device that draws in and focuses arcane energy. If given a compatible substrate, I can use this process to create things.",
                "The basic substrate here is the Alchemical Impetus, an artifact similar to those I've seen in the past. By focusing arcana into it, I can cause bits of the calx to transmute into something new, though this damages the Impetus in the process.");

        addSimpleEntryHeader("focus_ashes", "Arising of Ashes", "Creating powdered reagents");
        addPages("focus_ashes", "By applying differing qualities of arcana to an Alchemical Impetus, I can cause powders of various forms to be created. It is a simple yet very useful arcane recipe.");

        addSimpleEntryHeader("focus_metals", "Magecraft of Metals", "Forming banded crystals");
        addPages("focus_metals",
                "By altering the composition of the Alchemical Impetus with niter, sulfur, and cthonic gold, it is possible to alter the artifact in such a way that allows for forming nodes of most pure metals.",
                "It isn't particularly efficient or fast, but it is certainly better than having to mine for every ingot I need. Each metallic node can be processed at a furnace of any kind into two thirds of an ingot worth of metal nuggets.");

        addSimpleEntryHeader("focus_crystals", "Creation of Crystals", "Forming irregular crystals");
        addPages("focus_crystals", "By applying differing qualities of arcana to an Alchemical Impetus, I can cause more mundane crystals to be formed.");

        addSimpleEntryHeader("crucible_acceleration", "Crucible Acceleration", "Heating up");
        addPages("crucible_acceleration",
                "The Spirit Crucible is, unfortunately, a rather slow device. It takes time for it to coalesce the power of the arcana into the central item. This isn't without reason. Most matter simply can't take a faster stream, and you risk damaging the catalyst by overloading it.",
                "However, by heating the catalyst through mystic means, you can lessen this rejection and speed up the coalescence at once. That is what the Spirit Catalyzer is for. Unfortunately, this is not perfect, and instability often causes the catalyst to be damaged more than strictly necessary.",
                "Each fueled Catalyzer nearby to a Crucible will amplify the speed of the focusing process, up to a maximum of eight. Unfortunately, the risk of instability proportionally rises with each one, resulting in your impetus potentially receiving more damage than necessary.");

        addSimpleEntryHeader("arcane_restoration", "Arcane Restoration", "Mystic repair");
        addPages("arcane_restoration",
                "The Spirit Crucible has an annoying habit of breaking the tools to work with it. While a cost is to be expected, I'd rather pay it in installments. I have designed a device I call the Repair Pylon, intended to shore items up as they break down.",
                "It works not just on an Impetus, but practically anything at all, as long as it is placed on a nearby item holder, with a combination of spirits and a repair material (iron for iron tools, for instance) placed on the pylon. Unlike an anvil, no experience is needed to fuel this.",
                "It appears that materials in tune with spirit arcana, such as Soulstained Steel or Hallowed Gold, are more efficient in this process. They will be repaired more than their mundane counterparts would for the same cost.");

        addSimpleEntryHeader("crucible_augmentation", "Crucible Augmentation", "Tuning the attuner");
        addPages("crucible_augmentation",
                "The Spirit Crucible is a machine of great, but largely unrealized, potential. Through a process I call Augmentation, revolving around foci of Alchemical Calx, this potential can be extracted.",
                "Each augment provides a unique effect that can be activated by inserting it in the spirit catalyzer, or placing up to four in the spirit crucible itself. Using more than one instance of the same augment type will compound their effects.",
                "To assist in controlling this process, I have modified a Tuning Fork for the purpose. While held, I can see all the unique resonances of the crucible and it's augments. On top of that, by using this Tuning Fork on the crucible, I may choose an attribute to improve, at the cost of other attributes lessening in potency.");

        addSimpleEntryHeader("mending_diffuser", "Mending Diffuser", "Unliving scar tissue");
        addPages("mending_diffuser",
                "By using Living Flesh to sympathize with natural healing, the Mending Diffuser will, upon the Crucible completing a focusing cycle, potentially repair any impetus by a small amount. It cannot mend an already cracked impetus.");

        addSimpleEntryHeader("impurity_stabilizer", "Impurity Stabilizer", "Potency from weakness");
        addPages("impurity_stabilizer",
                "The wicked spirit is drawn to the weak, seeking to cull. The Impurity Stabilizer subverts that property, providing a powerful percentage bonus to the weakest crucible attribute besides fuel usage rate and instability, which receive a small improvement overall instead.");

        addSimpleEntryHeader("shielding_apparatus", "Shielding Apparatus", "A bulwark against the storm");
        addPages("shielding_apparatus",
                "By utilizing the multiphasic property of Soulstained Steel, the Shielding Apparatus provides a chance for the damage imposed upon the impetus to be " + italic("completely") + " absorbed, while also slightly stabilizing the focusing process. It does, however, reduce focusing speed.");

        addSimpleEntryHeader("warping_engine", "Warping Engine", "Suspension of linear time");
        addPages("warping_engine",
                "Warp Flux rejects natural law, and its application here is no less concerning. The Warping Engine rejects the sequence of cause and effect, allowing an additional cycle to sometimes be completed almost before it is begun whenever a cycle is completed normally.",
                "Furthermore, chained activations of the Warping Engine provide a stacking benefit to " + italic("all") + " other attributes. Perhaps predictably, this behavior takes quite a toll on the stability and fuel requirements of the focusing process.");

        addSimpleEntryHeader("prismatic_focus_lens", "Prismatic Focus Lens", "Stability");
        addPages("prismatic_focus_lens",
                "Sometimes, simplicity is the best goal. The Prismatic Focus Lens bends not light, but the flow of arcana, reducing instability of the spirit focusing process. It is important to note, stability can only prevent the impetus from suffering " + italic("additional") + " damage.");

        addSimpleEntryHeader("accelerating_inlay", "Accelerating Inlay", "Doubling down");
        addPages("accelerating_inlay",
                "Through the use of a superior conductor in Astral Weave, Accelerating Inlay simply provides a substantial bonus to the focusing speed of a Crucible without any drawbacks.");

        addSimpleEntryHeader("blazing_diode", "Blazing Diode", "The strongest force in the world");
        addPages("blazing_diode",
                "The Blazing Diode extracts the full force of a soul on fire, lessening the fuel requirement of any catalyzer powering the crucible while also slightly hastening the entire process.");

        addSimpleEntryHeader("intricate_assembly", "Intricate Assembly", "Fudging the numbers");
        addPages("intricate_assembly",
                "The Intricate Assembly, as its name suggests, draws its power from the unbounded nature of its fractal complexity. At the cost of an increased dependency on fuel, as well as a reduced focusing speed, it enables the crucible to potentially produce double the usual amount of items during each focusing cycle.");

        addEntryHeader("spirit_metals", "Spirit Metals", "Arcana refined");
        addHeadline("spirit_metals.soulstained_steel", "Soulstained Steel");
        addPages("spirit_metals.soulstained_steel",
                "Iron is mundane, in a word. By attuning the metal with Soulstone, I can create a steel that is " + italic("simultaneously") + " in and out of phase with the world.",
                "Anything made from Soulstained Steel is capable of striking the soul, without the need for specifics of engineering like with my crude scythe. Wearing the metal in its base form as armor is dangerous, as it will touch your own soul as well, so I must engineer a countermeasure.");
        addHeadline("spirit_metals.hallowed_gold", "Hallowed Gold");
        addPages("spirit_metals.hallowed_gold",
                "Gold is often used as a thaumaturgical base, its natural conductivity of magic making it quite useful. Spirit arcana are no exception. In fact, using Sacred arcana, we can enhance those conductive properties.",
                "Hallowed Gold, as a metal, acts much like its mundane counterpart. The inherent innocence of the arcana infused into the alloy makes other arcana glide through it smoothly, creating the perfect conductor for my purposes.");
        addHeadline("spirit_metals.hallowed_gold.spirit_jar", "Spirit Jar");
        addPages("spirit_metals.hallowed_gold.spirit_jar", "A simple application of this metal is the Spirit Jar. As spirits in their raw form don't have mass, by trapping them under Hallowed Gold you can store far more than you could physically. The capacity of these jars is near-infinite, though each only stores one type of spirit.");

        addSimpleEntryHeader("soulstained_scythe", "Soulstained Scythe", "Reap");
        addPages("soulstained_scythe", "The scythe I created to harvest spirits was useful, but ultimately has outlived that usefulness. I have grown fond of the utility it provides, though, and so instead of discarding it I sought to improve it. With Soulstained Steel, I was able to create a more effective weapon and maintain the scythe's advantages.");

        addSimpleEntryHeader("soulstained_armor", "Soulstained Armor", "Spiritual protection");
        addPages("soulstained_armor",
                "Much like the Soulstained Scythe, I have improved upon my mundane iron armor to create the Soulstained Armor. To avoid the metal touching me directly, and so jostling and rubbing against my very soul, I used thin plates of Twisted Rock beneath the metal of the armor.",
                "As it exists in both the arcane and physical realms, Soulstained Steel exhibits fascinating defensive properties. It can intercept attacks from both, creating an effect I call Soul Ward. It takes time to restore if the effect is disrupted, but it acts as additional armor which nearly absorbs magic damage completely, and dampens physical damage.",
                "This effect seems similar in nature to others I have studied, such as engraving runes into armor or invoking a black sun upon oneself. Though unlike those, it doesn't " + italic("appear") + " to have a cost. Where is the energy for Soul Ward coming from?");

        addSimpleEntryHeader("spirit_trinkets", "Spirit Trinkets", "Accessorizing");
        addPages("spirit_trinkets",
                "Many disciplines of magic, and even more mundane practices, allow the creation of useful trinkets. These are also referred to as baubles or curios by some. The metals I have alloyed have properties useful in their own rights, and can be used as the basis for even grander designs.",
                "In their most basic form, Hallowed Gold trinkets protect the user as if they were wearing weak armor, and Soulstained Steel trinkets increase the toughness of the armor being worn.");

        addEntryHeader("reactive_trinkets", "Reactive Trinkets", "Harnessing the harvest");
        addHeadline("reactive_trinkets.ring_of_curative_talent", "Ring of Curative Talent");
        addPages("reactive_trinkets.ring_of_curative_talent",
                "The trinkets documented within cause effects whenever a spirit crystal is collected, feeding off the excess energy. As an example, this restorative trinket will replenish a small division of my health any time I collect arcana.");
        addHeadline("reactive_trinkets.ring_of_alchemical_mastery", "Ring of Alchemical Mastery");
        addPages("reactive_trinkets.ring_of_alchemical_mastery",
                "This ring, through alchemical trickery, is able to manipulate the potions running through my blood. Whenever I collect arcana, the ring will partially filter out negative effects, while at the same time prolonging positive ones.");
        addHeadline("reactive_trinkets.ring_of_manaweaving", "Ring of Manaweaving");
        addPages("reactive_trinkets.ring_of_manaweaving",
                "Soul Ward is a powerful barrier, but in it's current state it leaves much to be desired. One of it's glaring issues is the burdensome recovery time. To combat this, I've created a ring that in reaction to spirit arcana accelerates the recovery process of Soul Ward");
        addHeadline("reactive_trinkets.ring_of_prowess", "Ring of Prowess");
        addPages("reactive_trinkets.ring_of_prowess",
                "Brilliance is attached to the soul, but isn't an impulse like the arcana. It is accumulated knowledge, and so is not inherently tied to the soul that learned it. Even strikes which pass through the soul harmlessly are capable of dislodging it.",
                "By using condensed Brilliance, I have created a ring that filters out that Brilliance out of arcana I collect, giving me a burst of Brilliant knowledge whenever I collect arcana.");

        addSimpleEntryHeader("ring_of_esoteric_spoils", "Ring of Esoteric Spoils", "Be fruitful and multiply");
        addPages("ring_of_esoteric_spoils",
                "It can be tiring, harvesting the sheer quantities of arcana I need for my research. This ring can increase the efficiency of the harvest, allowing me to reap an additional spirit from every slain soul. At a certain point, though, \"efficiency\" ceases to explain it. How am I obtaining more power than the soul itself has?");

        addSimpleEntryHeader("belt_of_the_starved", "Belt of the Starved", "Channeling voracity");
        addPages("belt_of_the_starved",
                "The arcana I collect occasionally have scraps of wishes and desires woven in. Often, given the base nature of what I reap, this comes in the form of hunger, lust, or petty grudges. All of these impurities can be harnessed, and beyond that, I can infuse this power into my magic.",
                "Doing this carries the perhaps predictable effect that my own hunger amplifies, draining quicker in the process. The magic proficiency this grants is immense, but... I must say, the means are rather distasteful.");
        addSimpleEntryHeader("belt_of_the_starved.ring_of_desperate_voracity", "Ring of Desperate Voracity", "Widening the channel");
        addPages("belt_of_the_starved.ring_of_desperate_voracity",
                "This ring makes rotten foods just a little bit more bearable, allowing me to amass more hunger and saturation from such an unusual diet. Normally, such a diet would be ill-advised, however, a secondary function of the ring allows it to extend the duration of the Gluttony status effect that the Belt of the Starved grants.");
        addSimpleEntryHeader("belt_of_the_starved.concentrated_gluttony", "Concentrated Gluttony", "Bypass");
        addPages("belt_of_the_starved.concentrated_gluttony",
                "I have grown somewhat annoyed with the rotten foods I rely on - they are hardly pleasant to eat. By concentrating them, I can minimize the time I spend tasting rot, granting Gluttony which is amplified by each rotten trinket I wear. It takes seconds to digest, but that is preferable to the taste.",
                "It is like a potion, and so, like potions, I have derived a Splash variant of it. Upon impact, the essence of rot stored inside is released in a small area, applying its usual benefits to every creature caught inside.");

        addSimpleEntryHeader("belt_of_the_prospector", "Belt of the Prospector", "Treasures of the earth");
        addPages("belt_of_the_prospector",
                "To fuel my various magics and other goals I more often than not find myself needing various earthen treasures. This belt prevents explosions " + italic("directly") + " caused by me from harming valuable items on the ground, and causes those explosions to break blocks as though I were using a Fortune III tool.");
        addSimpleEntryHeader("belt_of_the_prospector.ring_of_the_hoarder", "Ring of the Hoarder", "Directly into my veins");
        addPages("belt_of_the_prospector.ring_of_the_hoarder",
                "Explosions are chaotic, and messy, inherently. This is hardly a problem, when I want to cause such rampant destruction to collect resources, but collecting the items is a burden. This ring entangles the explosion with my soul, causing the debris and loot to appear at my location.");
        addSimpleEntryHeader("belt_of_the_prospector.ring_of_the_demolitionist", "Ring of the Demolitionist", bold("More dakka"));
        addPages("belt_of_the_prospector.ring_of_the_demolitionist",
                "If raw explosive power is not sufficient, you simply aren't using enough of it. This ring amplifies explosions, mitigating that issue.");

        addEntryHeader("necklace_of_blissful_harmony", "Necklace of Blissful Harmony", "No sign of morning coming");
        addHeadline("necklace_of_blissful_harmony", "The Blissful Harmony");
        addPages("necklace_of_blissful_harmony",
                "To focus on my magics I more often than not need peace and clarity. As such, I have devised a tool to redirect attention around me. While worn, this accessory will hide my presence from nearby adversaries, decreasing their likelihood of taking interest in me.",
                "Upon further studies, it would appear that the effects of my newly forged trinket are " + italic("especially") + " potent when exerting their influence over any soul bearing a Wicked spirit.");

        addEntryHeader("necklace_of_the_mystic_mirror", "Necklace of the Mystic Mirror", "As without, so within");
        addHeadline("necklace_of_the_mystic_mirror", "The Mystic Mirror");
        addPages("necklace_of_the_mystic_mirror",
                "I have devised another way to capture some of the lost energy from loose spirits. The Resonant Lens I socketed in is able to focus magic, collecting a little bit of excess energy as I pick up arcana. This energy is then redistributed to the rest of my trinkets, increasing the effect of any that act upon collecting spirits.");

        addEntryHeader("necklace_of_the_narrow_edge", "Necklace of the Narrow Edge", "Focused and sharpened");
        addHeadline("necklace_of_the_narrow_edge", "The Narrow Edge");
        addPages("necklace_of_the_narrow_edge",
                "The sweep of the scythe is its main draw. The ability to cut my targets like so much wheat is invaluable. But that comes at the cost of damage to a single target. This necklace mystically focuses the edge of my attack, directing all of the power into one target for a strong damage boost.");

        addEntryHeader("runeworking", "Runeworking", "The central pin");
        addHeadline("runeworking", "Runeworking");
        addPages("runeworking",
                "Every trinket I've made thus far has proven to have its place in my work, but it has become somewhat frustrating to have to spend time choosing out my jewelery before every task. After all, some effects may be able to be divorced from their genesis.",
                "The process of Runeworking allows me to do so via Brooches and Runes. The brooch is simply the focus I have chosen, its placement over the heart symbolizing the price of suffering inherent to runic power.",
                "To begin, I will need to create a Runic Workbench to inscribe these runes. The simplest beginning will be the Runic Brooch, which will allow me to equip what I have created.");

        addEntryHeader("runic_brooch", "Runic Brooch", "Power in the palms");
        addHeadline("runic_brooch", "Runic Brooch");
        addPages("runic_brooch",
                "The simplest of brooches is the Runic Brooch. It represents the quest for power at price, and the bloodied palms with which one grasps a razored enlightenment. When worn, by symbolically removing one of my hands' capacity to channel magic from a ring, it will grant me the opportunity to inscribe a rune in each palm.");

        addEntryHeader("glass_brooch", "Glass Brooch", "Fragile power");
        addHeadline("glass_brooch", "Glass Brooch");
        addPages("glass_brooch",
                "The Runic Brooch works well, but my hands are not the only symbolic home of power. I can instead imbue them into my blood with the Glass Brooch, stripping a portion of my physical health away to inscribe two runes within my chest.");

        addEntryHeader("elaborate_brooch", "Elaborate Brooch", "A change in purpose");
        addHeadline("elaborate_brooch", "Elaborate Brooch");
        addPages("elaborate_brooch",
                "Brooches symbolize a sacrifice, but that sacrifice can seem abstract, even comical, from the outside. A necklace is not so different from a belt in form, and by stripping away notions of " + italic("fashion") + " and propriety, I can wear a belt's power around my neck.");

        addEntryHeader("gluttonous_brooch", "Gluttonous Brooch", "Endlessly unsated");
        addHeadline("gluttonous_brooch", "Gluttonous Brooch");
        addPages("gluttonous_brooch",
                "Some sacrifices are mental, rather than physical. The Gluttonous Brooch strips away satiation, causing my body to crave food even when it should be full. In exchange for this, my loosened belly has the space to accommodate an additional belt.");

        addSimpleEntryHeader("rune_of_idle_restoration", "Rune of Idle Restoration", "The impulse to mend");
        addPages("rune_of_idle_restoration",
                "The Rune of Idle Restoration implores a body to restore itself, passively restoring the user's health at a rate of one half heart every two seconds.");
        addSimpleEntryHeader("rune_of_culling", "Rune of Culling", "The impulse to break");
        addPages("rune_of_culling",
                "The Rune of Culling implores a mind to seek ruin, granting the user a bonus to Magic Proficiency which improves magic damage output by roughly two fifths.");
        addSimpleEntryHeader("rune_of_aliment_cleansing", "Rune of Aliment Cleansing", "The impulse to process");
        addPages("rune_of_aliment_cleansing",
                "The Rune of Aliment Cleansing implores a body to catalyze and change substance, allowing it to burn through and process negative effects quicker than normal.");
        addSimpleEntryHeader("rune_of_fervor", "Rune of Fervor", "The impulse to expend");
        addPages("rune_of_fervor",
                "The Rune of Fervor implores a mind to move with haste, improving the user's mining speed by roughly a quarter.");
        addSimpleEntryHeader("rune_of_reactive_shielding", "Rune of Reactive Shielding", "The impulse to withstand");
        addPages("rune_of_reactive_shielding",
                "The Rune of Reactive Shielding implores a body to stand fast, granting an increase to effective Armor and Armor Toughness by one tenth upon taking damage. The strength of this effect scales as the user receives more damage, reaching an increase of three tenths at full power.");
        addSimpleEntryHeader("rune_of_dexterity", "Rune of Dexterity", "The impulse to flee");
        addPages("rune_of_dexterity",
                "The Rune of Dexterity implores a mind to move when cornered, boosting movement speed which can up to double as the user's health pool diminishes.");
        addSimpleEntryHeader("rune_of_reinforcement", "Rune of Reinforcement", "The impulse to make");
        addPages("rune_of_reinforcement",
                "The Rune of Reinforcement, rather than imploring the body, simply provides pressure to the Arcane quality of its existence, granting their Soul Ward capacity and strength.");
        addSimpleEntryHeader("rune_of_volatile_distortion", "Rune of Volatile Distortion", "The impulse to putrefy");
        addPages("rune_of_volatile_distortion",
                "The Rune of Volatile Distortion, rather than imploring the mind, corrupts its actions with random chance, making the user's attacks erratic in damage - anywhere from nine tenths as powerful to twelve tenths. Sometimes, this randomness aligns with weaknesses by chance, doubling the strength of the attack.");

        addSimpleEntryHeader("void.runes", "Voidish Runecraft", "An altered alphabet");
        addPages("void.runes",
                "By inscribing the runes on tablets of Null Slate, their effects run wild and warped, seeking Void instead of creation. Each seeks to tear itself apart, creating paradoxical and fascinating effects.",
                "The effects of the runes vary drastically from their original counterparts; I have my notes on their functions in the attached entries.");

        addSimpleEntryHeader("void.rune_of_bolstering", "Rune of Bolstering", "To heal what is whole");
        addPages("void.rune_of_bolstering",
                "The Rune of Bolstering does not heal like its counterpart. Instead, it forces the body to heal past its limits, granting a small amount of extra health.");
        addEntryHeader("void.rune_of_sacrificial_empowerment", "Rune of Sacrificial Empowerment", "To break what is broken");
        addHeadline("void.rune_of_sacrificial_empowerment", "Sacrificial Empowerment");
        addPages("void.rune_of_sacrificial_empowerment",
                "The Rune of Sacrificial Empowerment grants strength in exchange for the lives taken by a scythe, causing your weapon to grow more potent with each kill for a time.");
        addSimpleEntryHeader("void.rune_of_twinned_duration", "Rune of Twinned Duration", "To suspend what must process");
        addPages("void.rune_of_twinned_duration",
                "The Rune of Twinned Duration inhibits the body in breaking down substances, causing the body to hold on to positive effects for longer.");
        addSimpleEntryHeader("void.rune_of_igneous_solace", "Rune of Igneous Solace", "To fuel what must burn");
        addPages("void.rune_of_igneous_solace",
                "The Rune of Igneous Solace toughens the user's skin when burning, giving them a partial resistance to damage. It does not, however, do anything about the flames.");
        addSimpleEntryHeader("void.rune_of_toughness", "Rune of Toughness", "To bear what must break");
        addPages("void.rune_of_toughness",
                "The Rune of Toughness reinforces the user's armor, making any armor they already wear more effective and tough.");
        addSimpleEntryHeader("void.rune_of_unnatural_stamina", "Rune of Unnatural Stamina", "To flee what must pursue");
        addPages("void.rune_of_unnatural_stamina",
                "The Rune of Unnatural Stamina gives its user the speed of hysteria, constantly able to move faster, and even being able to sprint if your hunger would normally prevent you from doing so.");
        addSimpleEntryHeader("void.rune_of_spell_mastery", "Rune of Spell Mastery", "To make what destroys");
        addPages("void.rune_of_spell_mastery",
                "The Rune of Spell Mastery mirrors the false arcana, allowing me to hold two more staff charges in addition to improving the potency of my magic.");
        addSimpleEntryHeader("void.rune_of_the_heretic", "Rune of the Heretic", "To destroy what makes");
        addPages("void.rune_of_the_heretic",
                "The Rune of the Heretic decries magic, Silencing foes much like the Erosion Scepter can. This weakens their spirit magic, and the effect can stack up to complete suppression.");

        addSimpleEntryHeader("totemic_runes", "Totemic Runes", "Ritual as runecraft");
        addPages("totemic_runes",
                "Trinkets are not the only things that can be inscribed into Runes. I have managed to create runic tablets of Runewood and Soulwood, on which I can inscribe the patterns of what I refer to as \"aura rites\". The ritual's effect is more potent, but the rune doesn't need to stay in one place.",
                "These tablets are inscribed much the same as any other rune, though only the basic rites of the four elements are functional. These entries go into further detail on each.");

        addSimpleEntryHeader("rune_of_motion", "Rune of Motion", "Uplifting your impulses");
        addPages("rune_of_motion",
                "The Rune of Motion conveys the Rite of Motion, granting Zephyr's Courage to its wearer at a reduced potency, speeding them up.");
        addSimpleEntryHeader("rune_of_loyalty", "Rune of Loyalty", "Molding your impulses");
        addPages("rune_of_loyalty",
                "The Rune of Loyalty conveys the Rite of Loyalty, granting Poseidon's Grasp to its wearer at a reduced potency, extending their reach.");
        addSimpleEntryHeader("rune_of_warding", "Rune of Warding", "Grounding your impulses");
        addPages("rune_of_warding",
                "The Rune of Warding conveys the Rite of Warding, granting Gaia's Bulwark to its wearer at a reduced potency, effectively granting armor.");
        addSimpleEntryHeader("rune_of_haste", "Rune of Haste", "Igniting your impulses");
        addPages("rune_of_haste",
                "The Rune of Haste conveys the Rite of Haste, granting Miner's Rage to its wearer at a reduced potency, speeding up their swings of weapons and tools.");
        addSimpleEntryHeader("rune_of_the_aether", "Rune of the Aether", "Scattering your impulses");
        addPages("rune_of_the_aether",
                "The Rune of the Aether conveys the Rite of the Aether, granting Aether's Charm to its wearer at a reduced potency, lowering the influence of gravity on them.");
        addSimpleEntryHeader("rune_of_the_seas", "Rune of the Seas", "Deforming your impulses");
        addPages("rune_of_the_seas",
                "The Rune of the Seas conveys the Rite of the Seas, granting Angler's Lure to its wearer at a reduced potency, increasing their skill with fishing.");
        addSimpleEntryHeader("rune_of_the_arena", "Rune of the Arena", "Honing your impulses");
        addPages("rune_of_the_arena",
                "The Rune of the Arena conveys the Rite of the Arena, granting Earthen Might to its wearer at a reduced potency, causing their attacks to do more damage.");
        addSimpleEntryHeader("rune_of_the_hells", "Rune of the Hells", "Extinguishing your impulses");
        addPages("rune_of_the_hells",
                "The Rune of the Hells conveys the Rite of the Hells, granting Ifrit's Courage to its wearer at a reduced potency when they are on fire, extinguishing and healing them.");

        addSimpleEntryHeader("spirited_glass", "Spirited Glass", "Not suitable for Oculators");
        addPages("spirited_glass",
                "I have designed a simple but aesthetically pleasing glass which is tinted by the arcana, framed in iron. The particles of the glass are 'aligned' by the power placed within - which means Raw and Eldritch, having no direction, are somewhat chaotic. They do still look interesting, though.");

        addSimpleEntryHeader("mote_making", "Mote Making", "Worship the cube");
        addPages("mote_making",
                "Arcana crystals emit their own strange glow. Why not tune that to be stronger? The tool I use to do this is the Lamplighter's Tongs; simply hold them in one hand and the crystal in another to create a 'mote'.",
                "These Motes are concentrations of pure arcane energy, with a thin shell of warding magic to keep it from spilling. This has little magical implication, but the resulting lights are pretty.");

        addSimpleEntryHeader("mirror_magic", "Mirror magic", "Magic Funnels");
        addPages("mirror_magic", "The future holds many secrets..");

        addSimpleEntryHeader("voodoo_magic", "Voodoo magic", "Forbidden arts");
        addPages("voodoo_magic", "The future holds many secrets..");

        addSimpleEntryHeader("ritual_magic", "Ritual magic", "Grand Magics");
        addPages("ritual_magic", "The future holds many secrets..");

        addEntryHeader("altar_acceleration", "Altar Acceleration", "Obelisks");
        addHeadline("altar_acceleration.runewood_obelisk", "Runewood Obelisk");
        addPages("altar_acceleration.runewood_obelisk",
                "Spirit Infusion, as essential as it is, has grown to be tedious. Even producing a stack of simple Hex Ash takes several minutes. Using Hallowed Gold, I have found a way to accelerate it. By placing up to four hallowed obelisks nearby the altar I may increase the processing speed by one fourth with each obelisk.");
        addHeadline("altar_acceleration.brilliant_obelisk", "Brilliant Obelisk");
        addPages("altar_acceleration.brilliant_obelisk",
                "While not useful for Infusion, per-se, the design of the obelisk can be used in another way as well. By socketing Brilliance instead of Hallowed Gold, the obelisk will harmonize with the Brilliance of enchanting, causing it to provide as much force of enchanting as five bookshelves do.");

        addSimpleEntryHeader("totem_magic", "Totem Magic", "Arcana unleashed");
        addPages("totem_magic",
                "Up until now, when performing spirit arcana, I have limited my research to personal enhancement and material production. Now, I affect the world.",
                "To begin with Totem Magic, I may engrave spirit arcana into Runewood Logs, forming a rune representing the magic. If unwanted, engraved spirits can be stripped off with an axe, but I have uses in mind. With a Runewood Totem Base, and then a specific set of runes in a totem pole placed above my totem base, I can perform a Spirit Rite.",
                "While each rite does offer a unique function, they follow patterns and categorize easily. For what I term \"aura rites\", they are simple, effect-providing rites that affect anything living within eight blocks of the base. For other rituals which alter beings, most affect anything within half the range of an aura rite.",
                "For those which alter blocks, most affect the five-by-five area on the level beneath the base, towards where the runes are facing.",
                "One caveat is that no rite's totem may function within the range of another, identical ritual. If you try, the first one will simply fizzle out, the energies coursing through it disrupted.");

        addSimpleEntryHeader("managing_totems", "Totem Resonance", "Insight into the rites");
        addPages("managing_totems",
                "Totem rites are both complicated and simple, in their own ways. A simple, bounded effect, but dependent on the flow of arcana - and it can be difficult to discern their range.",
                "I have created a staff to act as a tuning fork of sorts for the energies of rites. Simply holding it resonates with the flow of arcana through the world, allowing me to visualize the area each totem can affect.",
                "Interestingly, the staff also allows me to 'tune' a rune into an active state by interacting with it, even if it's not on a totem. This is as far as I can tell purely visual, but if nothing else, it will make good decoration.");

        addSimpleEntryHeader("arcane_rite", "A Rite Unchained", "Creation uncontrolled");
        addPages("arcane_rite.description",
                "Raw arcana provides the basis for all rites. Without power, nothing would be accomplished. This naturally makes one wonder what effect raw arcana would have as the focus of a rite. The answer is a complex and dangerous one.",
                "It requires far more to focus than other rites, taking the entire five runes to activate. It's as though I am pushing on some threshold, and need to break through. And in breaking through... momentum is conserved.",
                "Once complete, the rite brings about erratic change to the totem; what I dub Soulwood bears scars from the violent method of its creation. Those scars warp magic, altering its fundamental nature. Any spirit rite performed with a Soulwood totem will produce a vastly different effect.",
                "The scars of this process linger, allowing me to make more Soulwood by placing Runewood on the results of the ritual.");
        addHeadline("arcane_rite.soulwood", "Soulwood Transmutation");

        addRiteEntry("arcane_rite",
                "The rite - if you could call something so chaotic that - corrupts and burns through the totem, altering its very base nature, and transmuting the world around it into some indeterminate blighted substance.",
                "Converts the totem structure into one made up of Soulwood and alters the nearby terrain into a blighted substance.\n- Soulwood totems produce different rite effects.");
        addRiteEntry("corrupt_arcane_rite",
                "Now already scarred, the power bleeds from the soulwood totem, corrupting and warping the nearby area. Any nearby block placed atop that blighted substance will be altered.",
                "Transmutes nearby blocks placed atop blighted gunk.");

        addEntryHeader("sacred_rite", "Sacred Rites", "Invigorating the soul");
        addRiteEntry("sacred_rite",
                "A simple rite, while active it will slowly mend the wounds of nearby entities.\n Avoids hostiles.",
                "Recovers one heart of damage every two seconds.");
        addRiteEntry("greater_sacred_rite",
                "An advanced rite, while active nearby crops planted on soil are filled with vigor and will grow more quickly.",
                "Periodically ages nearby crops. Coverage matches water coverage.");

        addEntryHeader("corrupt_sacred_rite", "Corrupting the Sacred Rites", "Stimulating the soul");
        addRiteEntry("corrupt_sacred_rite",
                "A simple rite, while active it will apply a spiritually nourishing effect to nearby animals, accelerating growth and certain biological processes.",
                """
                        Affected animals instantly gain 25 seconds worth of age
                         - Sheep will feed on grass more frequently
                         - Bees pollinate faster and more frequently
                         - Chickens lay eggs more frequently""");
        addRiteEntry("corrupt_greater_sacred_rite",
                "An advanced rite, while active... nearby animals are made... " + italic("vigorous") + ", as if I had fed them myself.",
                "Affected animals are fed until there are more than twenty.\n - This limit applies separately for each type of animal within the range of the rite.");

        addEntryHeader("wicked_rite", "Wicked Rites", "Maligning the soul");
        addRiteEntry("wicked_rite",
                "A simple rite, while active it will slowly bring nearby hostile beings to within an inch of death.",
                "Deals one heart of non-lethal damage every two seconds.");
        addRiteEntry("greater_wicked_rite",
                "An advanced rite, while active nearby beings on the brink of death are dealt a fatal blow to the body and soul.",
                "Affected entities are dealt a fatal blow, dropping items and spirits on death.\n - Avoids entities with more than two and a half hearts remaining.");

        addEntryHeader("corrupt_wicked_rite", "Corrupting the Wicked Rites", "Endangering the soul");
        addRiteEntry("corrupt_wicked_rite",
                "Rather than harm, this rite enhances nearby hostile beings, granting protection, force, and speed. Rather useless, but might have niche applications.",
                "Grants all nearby hostiles resistance, strength, and speed.");
        addRiteEntry("corrupt_greater_wicked_rite",
                "An advanced rite, while active it will cull herds of nearby overcrowded animals.",
                "While there are more than twenty animals within the range of the rite, the excess is removed.\n - This limit applies separately for each type of animal within the range of the rite.");

        addEntryHeader("aerial_rite", "Aerial Rites", "Uplifting the soul");
        addRiteEntry("aerial_rite", "A simple aura rite, while active nearby friendly beings will find their movements sped up.",
                "Applies Zephyr's Courage, increasing movement speed by two fifths.");
        addRiteEntry("greater_aerial_rite",
                "An advanced rite, by twisting the power of the air, blocks before the totem will be made to fall as though they were sand. Nothing Silk Touch cannot grab will be affected, though.",
                "Causes targeted blocks to fall downwards if there is nothing underneath them.");

        addEntryHeader("corrupt_aerial_rite", "Corrupting the Aerial Rites", "Scattering the soul");
        addRiteEntry("corrupt_aerial_rite",
                "A simple aura rite, while active nearby friendly beings will have their connection to the earth disrupted, lowering their gravity and increasing jump height.",
                "Applies Aether's Charm, decreasing gravity by three fifths while also providing a substantial benefit to jump height.");
        addRiteEntry("corrupt_greater_aerial_rite",
                "An advanced rite, while active it will slowly ease the stress of time on the mind, offsetting the effects of insomnia for those around it over time.",
                "Passively reduces the insomnia value of nearby players.\n - Assuming phantoms are just starting to appear, it will take a single totem executing the rite two and two fifths of a minute to fully cleanse insomnia.\n - Naturally, the totem will take longer to fully cleanse insomnia if the player has already been suffering from it for some time.");

        addEntryHeader("earthen_rite", "Earthen Rites", "Grounding the soul");
        addRiteEntry("earthen_rite",
                "A simple aura rite, while active nearby friendly beings will find their bodies are tougher and more resistant to damage.",
                "Applies Gaia's Bulwark, increasing armor by four and armor toughness by two.");
        addRiteEntry("greater_earthen_rite",
                "An advanced rite, while active it will cause blocks before the totem base to be broken.",
                "Breaks targeted blocks. Unbreakable blocks behave as to be expected.");

        addEntryHeader("corrupt_earthen_rite", "Corrupting the Earthen Rites", "Honing the soul");
        addRiteEntry("corrupt_earthen_rite",
                "A simple aura rite, while active nearby friendly beings will find their attacks deal more damage.",
                "Applies Earthen Might, increasing damage dealt by two hearts.");
        addRiteEntry("corrupt_greater_earthen_rite",
                "An advanced rite, while active the earth coalesces, and like lava meeting water, cobblestone is created before the totem base.",
                "Creates cobblestone in place of empty space.");

        addEntryHeader("infernal_rite", "Infernal Rites", "Igniting the soul");
        addRiteEntry("infernal_rite",
                "A simple aura rite, while active nearby friendly beings will find that their motions are infused with fiery vigor, letting them swing weapons and tools faster.",
                "Applies Miner's Rage, increasing attack rate and dig speed by two fifths.");
        addRiteEntry("greater_infernal_rite",
                "An advanced rite, while active it will cause blocks before the totem base to be smelted.",
                "Smelts targeted blocks that can be smelted into other blocks.");

        addEntryHeader("corrupt_infernal_rite", "Corrupting the Infernal Rites", "Extinguishing the soul");
        addRiteEntry("corrupt_infernal_rite",
                "A simple aura rite, while active nearby friendly beings and close fires will have the heat sucked out of them, extinguishing them and healing those who were burned, giving them the survivability of denizens of the nether.",
                "Extinguishes nearby flames, be it affecting the world or an entity.\n - Extinguished entities receive Ifrit's Embrace, recovering two hearts while being extinguished.");
        addRiteEntry("corrupt_greater_infernal_rite",
                "An advanced rite, instead of generating heat, this rite compresses it, causing nearby furnaces to operate more quickly.",
                "Speeds up nearby furnaces by one fourth.\n - Fuel consumption rate is unaffected, meaning the rite also improves fuel efficiency.");

        addEntryHeader("aqueous_rite", "Aqueous Rites", "Molding the soul");
        addRiteEntry("aqueous_rite",
                "A simple aura rite, while active nearby friendly beings will find that their reach is extended, letting them more easily interact with the world.",
                "Applies Poseidon's Grasp, increasing block reach by two units of space and increasing item pickup range significantly.");
        addRiteEntry("greater_aqueous_rite",
                "An advanced rite, while active, it will vastly increasing the drip speed of dripstone, causing more fluid to be produced.",
                "Speeds up dripstone fluid production, works on both lava and water.\n - Only the tip of hanging dripstone needs to be within range for the effect to trigger.");

        addEntryHeader("corrupt_aqueous_rite", "Corrupting the Aqueous Rites", "Deforming the soul");
        addRiteEntry("corrupt_aqueous_rite",
                "A simple aura rite, while active nearby friendly beings will find themselves better at fishing.",
                "Applies Angler's Lure, providing benefits to fishing skills equal to Lure I and Luck of the Sea I.\n - The effects stack with any enchantment already present on a fishing rod.");
        addRiteEntry("corrupt_greater_aqueous_rite",
                "An advanced rite, while active zombies near this rite will find themselves choking on their own breath, drowning even on land.",
                "Converts nearby zombies to drowned.");

        addEntryHeader("blight", "A Study on Blight", "What, why, and how");
        addHeadline("blight.intro", "Blight Study: Preface");
        addPages("blight.intro",
                "Blight. " + italic("Something which spoils or damages.") + " What the Undirected Rite has created has many strange properties, and I intend to categorize them.",
                "The naïve explanation is that it is simply another form of power that taints the world, but that isn't right. Blight isn't harmful, not inof itself. It's just... " + italic("gunk."));
        addHeadline("blight.composition", "Blight Study: Substance");
        addPages("blight.composition",
                "The Undirected Rite, as the name suggests, is random. It transmutes, but it has no pattern to transmute things to. So, instead, you get something random, bits of disparate matter all jumbled together into a foul-smelling powder. I wouldn't recommend eating it, or growing things on it, but it's otherwise harmless.");
        addHeadline("blight.spread", "Blight Study: Spread");
        addPages("blight.spread",
                "Blight does not spread on its own. It's just random matter, after all. But it has a spiritual memory, a pattern which to replicate. When given arcana, or a valid fertilizer, blight will haphazardly echo this pattern on the nearby area.");
        addHeadline("blight.arcane_rite", "Blight Study: Resonance");
        addPages("blight.arcane_rite",
                "That echo is why this substance is important for the Unchained Rite. The Rite remembers the violence of its creation, and resonates with the memory within the blight, applying its power to things laying on top of it.");

        addEntryHeader("soulwood", "A Study on Soulwood", "Twisted trees");
        addHeadline("soulwood.intro", "Soulwood Study: Preface");
        addPages("soulwood.intro",
                "After further study, I have discovered that the Soulwood produced by the Unchained Rite has actually become an entirely different species from the Runewood it is made from. It grows differently, it acts differently... it appears the spiritual scars that created it go deeper than just its color and magic.");
        addHeadline("soulwood.bonemeal", "Soulwood Study: Growth");
        addPages("soulwood.bonemeal",
                "Much like blight, the sapling accepts both spirit arcana and common fertilizers such as bonemeal. The end result is roughly the same across both options.");
        addHeadline("soulwood.color", "Soulwood Study: Color");
        addPages("soulwood.color",
                "The most obvious differences with the tree itself are shape and leaf color. Soulwood is more spindly than Runewood, and its leaves are a sickly purple-red hue instead of a rich orange-yellow. It can still be used for many of the same things, though.");
        addHeadline("soulwood.blight", "Soulwood Study: Blight");
        addPages("soulwood.blight",
                "Another obvious difference is in its effect on the surroundings - namely, the fact that it echoes the Undirected Rite with the energies of its growth, transmuting the world around it into blight. I ought to create a safely contained area if I wish to grow these trees.");
        addHeadline("soulwood.sap", "Soulwood Study: Sap");
        addPages("soulwood.sap",
                "The sticky lifeblood of the tree also seems to well up much more often in a tree than in Runewood. The sap's effects are corrupted as well; rather than restoring my vigor, Unholy Syrup enhances it, increasing my attack strength and providing damage resistance.");

        addSimpleEntryHeader("transmutation", "Transmutation", "Volatile reactions");
        addPages("transmutation.intro",
                "The Unchained Rite's echoes can scar more than simply Runewood. The patterns seem somewhat random, but then, blight is a substance of randomness. Trial and error has identified three categories; transmutation trees, of sorts.",
                "The effect of applying these scars depends on what material we start with. The pulsing of the Rite will shift any given block forward in the tree, with it degrading into blight given enough time.");
        addHeadline("transmutation.stone", "Transmutation: Basic");
        addHeadline("transmutation.deepslate", "Transmutation: Endothermic");
        addHeadline("transmutation.smooth_basalt", "Transmutation: Exothermic");

        addSimpleEntryHeader("corrupted_resonance", "Corrupted Resonance", "Advanced magics");
        addPages("corrupted_resonance", "To fuel any further magics, I will need a catalyst. Something beautiful, full of magic, serving as the basis for any complex craft born from it. Corrupted Resonance is perfect for the role.");

        addSimpleEntryHeader("tyrving", "Tyrving", "Ancient relic");
        addPages("tyrving", "The Tyrving is a rather esoteric blade. It's strange design makes it appear as a weak weapon not suited for combat. However, it's hex ash lining and twisted rock form cause it to deal extra magic damage to the soul, the greater the soul the more benefit.",
                "The weapon can also be repaired using arcane restoration quite efficiently.");

        addSimpleEntryHeader("belt_of_the_magebane", "Belt of the Magebane", "Newfound ruin");
        addPages("belt_of_the_magebane", "The Belt of the Magebane is a simple innovation, but a dangerously effective one. Normally, after being struck by any attack, soul ward will not recover until a long moment after. That moment of downtime has proven itself detrimental far too frequently. But that ends now.",
                "While worn, the belt provides a substantial bonus to soul ward recovery rate, while also improving capacity slightly. Furthermore, the belt will absorb the arcane essence of any instance of magical damage that strikes its bearer, converting that repurposed energy into immediate recovery of soul ward.");

        addSimpleEntryHeader("a_personal_note", "A Personal Note", "A page from another book");
        addPages("a_personal_note",
                "Within the 'Arcana, a page sticks out just barely. As you examine it, the writing seems different than usual- out of this world. The texts inside appear to be a show of gratitude of sorts. The names ring a bell, but not to you.");

        addSimpleEntryHeader("a_personal_note.commendations", "Malum; Commendations", "A Thank You Letter");
        addHeadline("a_personal_note.commendations.wiresegal", "Wiresegal");
        addPages("a_personal_note.commendations.wiresegal",
                "I'm not quite sure how we met, can't remember really. At some point, they offered to help out with Malum- most notably with the Encyclopedia Arcana, but also with a decent few bug fixes and refactors of dated systems. A large majority of the Entries are written by them :3");
        addHeadline("a_personal_note.commendations.alphalilly", "Alphalilly");
        addPages("a_personal_note.commendations.alphalilly",
                "I reached out to them after seeing their work for the Arcana project, the Malum' book background art was made by them per commission. They've done an incredible job with them and I'm very happy to have their work in game :3");
        addHeadline("a_personal_note.commendations.pessi_mysterio", "Pessi Mysterio");
        addPages("a_personal_note.commendations.pessi_mysterio",
                "I remember going on quite a witch hunt to find them, scouring through the internet and asking a bunch of people. They worked on Sound Design for a few mods and so I reached out to them to commission some for Malum. The Spirit Altar, Crucible and Totems are some of their works for this project.");
        addHeadline("a_personal_note.commendations.kultik", "Kultik");
        addPages("a_personal_note.commendations.kultik",
                "Once again, no idea how we got in touch, but they showed quite an interest in Malum and offered to have one of their tracks added to the mod as a music disc. They're responsible for the Aesthetica and Arcane Elegy Music Discs :3");
        addHeadline("a_personal_note.commendations.coalition_of_magic", "Coalition of Magic");
        addPages("a_personal_note.commendations.coalition_of_magic",
                "The Coalition of Magic is a Supporter exclusive server organized by me and Joefoxe, without everyone's voices and support of the mod, it'd be difficult to find motivation to work as much as I can now. Thank you!");
        addHeadline("a_personal_note.commendations.translation", "Translation");
        addPages("a_personal_note.commendations.translation",
                "There's this fella on github who very routinely pushes translations to the mod for the Chinese language, figured they deserve some thanks. They go by ChuijkYahus, so if you're reading this in Chinese it's thanks to them! If you plan to do a complete translation of Malum, let me know- I hope in the future I can replace this page with a list of credits.");

        addSimpleEntryHeader("a_personal_note.inspirations", "Malum; Inspirations", "What brought me here");
        addHeadline("a_personal_note.inspirations.thaumcraft", "Thaumcraft");
        addPages("a_personal_note.inspirations.thaumcraft",
                "By far the biggest inspiration for Malum, I enjoyed the mod immensely when I was first getting into modded minecraft. I only ever tried the 1.12 version though. The way the magic system is designed and the general sense of progression is something I love.");
        addHeadline("a_personal_note.inspirations.astral_sorcery", "Astral Sorcery");
        addPages("a_personal_note.inspirations.astral_sorcery",
                "I love the mod's visual identity and mechanics, anytime it was in a pack I'd make sure to make the most of it. The Path of Exiles is a huge inspiration for some upcoming stuff I wanna add to Malum.");
        addHeadline("a_personal_note.inspirations.eidolon", "Eidolon");
        addPages("a_personal_note.inspirations.eidolon",
                "Eidolon has such a charming atmosphere, when it released I got a little scared about how similar the mod was to my concept of what I want Malum to be, and it really pushed me to innovate. Additionally, the mod's author and I exchange a myriad of cat pictures, it is a charm.");
        addHeadline("a_personal_note.inspirations.mystic_modding", "Mystic Modding' Mods");
        addPages("a_personal_note.inspirations.mystic_modding",
                "Embers and Roots were some of my favourite magic mods I've come across, I found their very approachable nature a really appealing thing for a mod to incorporate.");

        addSimpleEntryHeader("the_device", "The Device.", "microwave to recharge");
        addPage("the_device", "even works while bended");

        add("malum.spirit.description.stored_spirit", "Contains: ");
        add("malum.spirit.description.stored_soul", "Stores Soul With: ");

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
        add("itemGroup.malum_void_chronicles", "Malum: Chronicles of the Void");
        add("itemGroup.malum_ritual_shards", "Malum: Ritual Shards");
        add("itemGroup.malum_cosmetics", "Malum: Self Expression");

        add("death.attack.voodoo", "%1$s had their soul shattered");
        add("death.attack.vodooo.player", "%1$s had their soul shattered by %2$s");
        add("death.attack.vodooo.player.item", "%1$s had their soul shattered by %2$s using %3$s");

        add("death.attack.vodooo_playerless", "%1$s had their soul shattered");
        add("death.attack.vodooo_playerless.player", "%1$s had their soul shattered while trying to escape %2$s");

        add("death.attack.scythe_melee", "%1$s was sliced in half");
        add("death.attack.scythe_melee.item", "%1$s was sliced in half by %2$s using %3$s");

        add("death.attack.scythe_sweep", "%1$s was sliced in half");
        add("death.attack.scythe_sweep.item", "%1$s was sliced in half by %2$s using %3$s");

        add("death.attack.hidden_blade_counter", "%1$s was sliced into innumerable pieces");
        add("death.attack.hidden_blade_counter.item", "%1$s was sliced into innumerable pieces by %2$s using %3$s");

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

        addEnchantmentDescription(EnchantmentRegistry.ANIMATED, "Improves the Scythe's attack rate.");
        addEnchantmentDescription(EnchantmentRegistry.REBOUND, "Allows the Scythe to be thrown much like a boomerang when used.");
        addEnchantmentDescription(EnchantmentRegistry.ASCENSION, "Enables the Scythe to propel the player upwards, pushing away nearby enemies when used.");
        addEnchantmentDescription(EnchantmentRegistry.REPLENISHING, "Reduces the Staff's ranged attack cooldown when dealing melee damage.");
        addEnchantmentDescription(EnchantmentRegistry.HAUNTED, "Improves the Weapon's Magic Damage");
        addEnchantmentDescription(EnchantmentRegistry.SPIRIT_PLUNDER, "Reaps extra Spirits when killing an enemy.");

        addAttributeLibAttributeDescription(AttributeRegistry.SCYTHE_PROFICIENCY, "Damage multiplier for Scythes");
        addAttributeLibAttributeDescription(AttributeRegistry.SPIRIT_SPOILS, "Flat increase to spirits looted from slain foes");
        addAttributeLibAttributeDescription(AttributeRegistry.ARCANE_RESONANCE, "Bonus potency for spirit-collection effects");

        addAttributeLibAttributeDescription(AttributeRegistry.SOUL_WARD_INTEGRITY, "A percentile increase in durability for Soul Ward");
        addAttributeLibAttributeDescription(AttributeRegistry.SOUL_WARD_RECOVERY_RATE, "A percentile increase in recovery rate for Soul Ward");
        addAttributeLibAttributeDescription(AttributeRegistry.SOUL_WARD_CAP, "The capacity for Soul Ward");

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

    public String obfuscate(String s) {
        return "$k" + s + "/$";
    }

    public String italic(String s) {
        return "$i" + s + "/$";
    }

    public String bold(String s) {
        return "$b" + s + "/$";
    }

    public String strike(String s) {
        return "$s" + s + "/$";
    }

    public String underline(String s) {
        return "$u" + s + "/$";
    }

    public void addRite(TotemicRiteType riteType, String basicName, String corruptName) {
        add(riteType.translationIdentifier(false), basicName);
        add(riteType.translationIdentifier(true), corruptName);
    }

    public void addRiteEffectCategory(TotemicRiteEffect.MalumRiteEffectCategory category) {
        add(category.getTranslationKey(), DataHelper.toTitleCase(category.name().toLowerCase(), "_"));
    }

    public void addRiteEntry(String identifier, String riteDescription, String riteHoverDescription) {
        add("malum.gui.book.entry.page.text." + identifier, riteDescription);
        add("malum.gui.book.entry.page.text." + identifier + ".hover", riteHoverDescription);
    }

    public void addEntryHeader(String identifier, String name, String description) {
        add("malum.gui.book.entry." + identifier, name);
        addDescription(identifier, description);
    }

    public void addSimpleEntryHeader(String identifier, String name, String description) {
        addHeadline(identifier, name);
        addEntryHeader(identifier, name, description);
    }

    public void addPage(String identifier, String page) {
        add("malum.gui.book.entry.page.text." + identifier, page);
    }

    public void addPages(String identifier, String... pages) {
        int i = 1;
        for (String s : pages) {
            addPage(identifier + "." + i++, s);
        }
    }

    public void addDescription(String identifier, String tooltip) {
        add("malum.gui.book.entry." + identifier + ".description", tooltip);
    }

    public void addHeadline(String identifier, String tooltip) {
        add("malum.gui.book.entry.page.headline." + identifier, tooltip);
    }

    public void addTetraMaterial(String identifier, String name) {
        add("tetra.material." + identifier, name);
        add("tetra.material." + identifier + ".prefix", name);
    }

    public void addTetraImprovement(String identifier, String name, String description) {
        add("tetra.improvement." + identifier + ".name", name);
        add("tetra.improvement." + identifier + ".description", description);
    }

    public void addEnchantmentDescription(RegistryObject<Enchantment> enchantment, String desc) {
        add("enchantment.malum." + enchantment.getId().getPath() + ".desc", desc);
    }

    public void addAttributeLibAttributeDescription(RegistryObject<Attribute> attribute, String desc) {
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
