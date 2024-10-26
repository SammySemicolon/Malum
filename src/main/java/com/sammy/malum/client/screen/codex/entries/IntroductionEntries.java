package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.pages.recipe.*;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;

public class IntroductionEntries {


    public static void setupEntries(ArcanaProgressionScreen screen) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        var soulstoneAndBrillianceReexamination = BookEntry.build("spirit_minerals.reexamination")
                .addPage(new HeadlineTextPage("spirit_minerals.reexamination", "spirit_minerals.reexamination.1"))
                .addPage(new TextPage("spirit_minerals.reexamination.2"))
                .afterUmbralCrystal()
                .build();

        screen.addEntry("introduction", 0, 0, b -> b
                .configureWidget(w -> w.setIcon(ENCYCLOPEDIA_ARCANA).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("introduction", "introduction.1"))
                .addPage(new TextPage("introduction.2"))
                .addPage(new TextPage("introduction.3"))
                .addPage(new TextPage("introduction.4"))
                .addPage(new TextPage("introduction.5"))
        );

        screen.addEntry("spirit_crystals", 0, 1, b -> b
                .setWidgetSupplier((e, x, y) -> new IconObject(e, x, y, malumPath("textures/gui/book/icons/soul_shard.png")))
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_SMALL_RUNEWOOD))
                .addPage(new HeadlineTextPage("spirit_crystals", "spirit_crystals.1"))
                .addPage(new TextPage("spirit_crystals.2"))
                .addPage(new TextPage("spirit_crystals.3"))
        );

        screen.addEntry("runewood", 1, 2, b -> b
                .configureWidget(w -> w.setIcon(RUNEWOOD_SAPLING))
                .addPage(new HeadlineTextItemPage("runewood", "runewood.1", RUNEWOOD_SAPLING.get()))
                .addPage(new TextPage("runewood.2"))
                .addPage(new HeadlineTextItemPage("runewood.arcane_charcoal", "runewood.arcane_charcoal.1", ARCANE_CHARCOAL.get()))
                .addPage(new SmeltingPage(RUNEWOOD_LOG.get(), ARCANE_CHARCOAL.get()))
                .addPage(CraftingPage.fullPage(BLOCK_OF_ARCANE_CHARCOAL.get(), ARCANE_CHARCOAL.get()))
                .addPage(new HeadlineTextPage("runewood.runic_sap", "runewood.runic_sap.1"))
                .addPage(new TextPage("runewood.runic_sap.2"))
                .addPage(new CraftingPage(new ItemStack(RUNIC_SAPBALL.get()), RUNIC_SAP.get(), RUNIC_SAP.get()))
                .addPage(new CraftingPage(new ItemStack(RUNIC_SAP_BLOCK.get(), 8), RUNIC_SAP.get(), RUNIC_SAP.get(), EMPTY, RUNIC_SAP.get(), RUNIC_SAP.get()))
        );

        screen.addEntry("natural_quartz", 3, 1, b -> b
                .configureWidget(w -> w.setIcon(NATURAL_QUARTZ).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("natural_quartz", "natural_quartz.1", NATURAL_QUARTZ.get()))
        );

        screen.addEntry("blazing_quartz", 4, 2, b -> b
                .configureWidget(w -> w.setIcon(BLAZING_QUARTZ).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("blazing_quartz", "blazing_quartz.1", BLAZING_QUARTZ.get()))
                .addPage(CraftingPage.fullPage(BLOCK_OF_BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get()))
        );

        screen.addEntry("brilliance", -3, 1, b -> b
                .configureWidget(w -> w.setIcon(CLUSTER_OF_BRILLIANCE).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("brilliance", "brilliance.1", CLUSTER_OF_BRILLIANCE.get()))
                .addPage(new TextPage("brilliance.2"))
                .addPage(CraftingPage.fullPage(BLOCK_OF_BRILLIANCE.get(), CLUSTER_OF_BRILLIANCE.get()))
                .addPage(new SmeltingPage(new ItemStack(CLUSTER_OF_BRILLIANCE.get()), new ItemStack(CHUNK_OF_BRILLIANCE.get(), 2)))
                .addReference(new EntryReference(UMBRAL_SPIRIT, soulstoneAndBrillianceReexamination))
        );

        screen.addEntry("cthonic_gold", -4, 2, b -> b
                .configureWidget(w -> w.setIcon(CTHONIC_GOLD).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("cthonic_gold", "cthonic_gold.1", CTHONIC_GOLD.get()))
                .addPage(new TextPage("cthonic_gold.2"))
                .addPage(new TextPage("cthonic_gold.3"))
                .addPage(new TextPage("cthonic_gold.4"))
                .addReference(new EntryReference(UMBRAL_SPIRIT, BookEntry.build("cthonic_gold.reexamination")
                        .addPage(new HeadlineTextPage("cthonic_gold.reexamination", "cthonic_gold.reexamination.1"))
                        .addPage(new TextPage("cthonic_gold.reexamination.2"))
                        .afterUmbralCrystal()
                ))
        );

        screen.addEntry("soulstone", -1, 2, b -> b
                .configureWidget(w -> w.setIcon(PROCESSED_SOULSTONE))
                .addPage(new HeadlineTextItemPage("soulstone", "soulstone.1", PROCESSED_SOULSTONE.get()))
                .addPage(new TextPage("soulstone.2"))
                .addPage(new SmeltingPage(new ItemStack(RAW_SOULSTONE.get()), new ItemStack(PROCESSED_SOULSTONE.get(), 2)))
                .addPage(CraftingPage.fullPage(BLOCK_OF_SOULSTONE.get(), PROCESSED_SOULSTONE.get()))
                .addPage(CraftingPage.fullPage(BLOCK_OF_RAW_SOULSTONE.get(), RAW_SOULSTONE.get()))
                .addReference(new EntryReference(UMBRAL_SPIRIT, soulstoneAndBrillianceReexamination))
        );

        screen.addEntry("scythes", 0, 3, b -> b
                .configureWidget(w -> w.setIcon(CRUDE_SCYTHE))
                .addPage(new HeadlineTextPage("scythes", "scythes.1"))
                .addPage(CraftingPage.scythePage(ItemRegistry.CRUDE_SCYTHE.get(), Items.IRON_INGOT, PROCESSED_SOULSTONE.get()))
                .addPage(new TextPage("scythes.2"))
                .addReference(new EntryReference(ENCHANTED_BOOK,
                        BookEntry.build("scythes.enchanting")
                                .addPage(new HeadlineTextPage("scythes.enchanting", "scythes.enchanting.1"))
                                .addPage(new HeadlineTextPage("scythes.enchanting.spirit_plunder", "scythes.enchanting.spirit_plunder.1"))
                                .addPage(new HeadlineTextPage("scythes.enchanting.haunted", "scythes.enchanting.haunted.1"))
                                .addPage(new HeadlineTextPage("scythes.enchanting.winded", "scythes.enchanting.winded.1"))
                                .addPage(new HeadlineTextPage("scythes.enchanting.rebound", "scythes.enchanting.rebound.1"))
                                .addPage(new HeadlineTextPage("scythes.enchanting.ascension", "scythes.enchanting.ascension.1"))
                ))
        );

        screen.addEntry("spirit_infusion", 0, 5, b -> b
                .configureWidget(w -> w.setIcon(SPIRIT_ALTAR).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("spirit_infusion", "spirit_infusion.1"))
                .addPage(new CraftingPage(SPIRIT_ALTAR.get(), AIR, PROCESSED_SOULSTONE.get(), AIR, GOLD_INGOT, RUNEWOOD_PLANKS.get(), GOLD_INGOT, RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get()))
                .addPage(new TextPage("spirit_infusion.2"))
                .addPage(new TextPage("spirit_infusion.3"))
                .addPage(CraftingPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(CraftingPage.itemStandPage(RUNEWOOD_ITEM_STAND.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(new HeadlineTextPage("spirit_infusion.hex_ash", "spirit_infusion.hex_ash.1"))
                .addPage(SpiritInfusionPage.fromOutput(HEX_ASH.get()))
                .addPage(new HeadlineTextPage("spirit_infusion.living_flesh", "spirit_infusion.living_flesh.1"))
                .addPage(SpiritInfusionPage.fromOutput(LIVING_FLESH.get()))
                .addPage(new HeadlineTextPage("spirit_infusion.alchemical_calx", "spirit_infusion.alchemical_calx.1"))
                .addPage(SpiritInfusionPage.fromOutput(ALCHEMICAL_CALX.get()))
        );

        screen.addEntry("esoteric_reaping", 0, 6, b -> b
                .configureWidget(w -> w.setIcon(ROTTING_ESSENCE))
                .addPage(new HeadlineTextPage("esoteric_reaping", "esoteric_reaping.1"))
                .addPage(new TextPage("esoteric_reaping.2"))
                .addPage(new TextPage("esoteric_reaping.3"))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.rotting_essence", "esoteric_reaping.rotting_essence.1", ROTTING_ESSENCE.get()))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.grim_talc", "esoteric_reaping.grim_talc.1", GRIM_TALC.get()))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.astral_weave", "esoteric_reaping.astral_weave.1", ASTRAL_WEAVE.get()))
                .addPage(new HeadlineTextItemPage("esoteric_reaping.warp_flux", "esoteric_reaping.warp_flux.1", WARP_FLUX.get()))
        );

        screen.addEntry("primary_arcana", -2, 4, b -> b
                .configureWidget(w -> w.setIcon(SACRED_SPIRIT))
                .addPage(new HeadlineTextItemPage("primary_arcana.sacred", "primary_arcana.sacred.1", SACRED_SPIRIT.get()))
                .addPage(new TextPage("primary_arcana.sacred.2"))
                .addPage(new HeadlineTextItemPage("primary_arcana.wicked", "primary_arcana.wicked.1", WICKED_SPIRIT.get()))
                .addPage(new TextPage("primary_arcana.wicked.2"))
                .addPage(new HeadlineTextItemPage("primary_arcana.arcane", "primary_arcana.arcane.1", ARCANE_SPIRIT.get()))
                .addPage(new TextPage("primary_arcana.arcane.2"))
                .addPage(new TextPage("primary_arcana.arcane.3"))
        );

        screen.addEntry("elemental_arcana", 2, 4, b -> b
                .configureWidget(w -> w.setIcon(EARTHEN_SPIRIT))
                .addPage(new HeadlineTextItemPage("elemental_arcana.aerial", "elemental_arcana.aerial.1", AERIAL_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.aerial.2"))
                .addPage(new HeadlineTextItemPage("elemental_arcana.earthen", "elemental_arcana.earthen.1", EARTHEN_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.earthen.2"))
                .addPage(new HeadlineTextItemPage("elemental_arcana.infernal", "elemental_arcana.infernal.1", INFERNAL_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.infernal.2"))
                .addPage(new HeadlineTextItemPage("elemental_arcana.aqueous", "elemental_arcana.aqueous.1", AQUEOUS_SPIRIT.get()))
                .addPage(new TextPage("elemental_arcana.aqueous.2"))
        );

        screen.addEntry("eldritch_arcana", 0, 7, b -> b
                .configureWidget(w -> w.setIcon(ELDRITCH_SPIRIT))
                .addPage(new HeadlineTextItemPage("eldritch_arcana", "eldritch_arcana.1", ELDRITCH_SPIRIT.get()))
                .addPage(new TextPage("eldritch_arcana.2"))
        );

        screen.addEntry("altar_acceleration", -1, 8, b -> b
                .configureWidget(w -> w.setIcon(RUNEWOOD_OBELISK))
                .addPage(new HeadlineTextPage("altar_acceleration.runewood_obelisk", "altar_acceleration.runewood_obelisk.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_OBELISK.get()))
                .addPage(new HeadlineTextPage("altar_acceleration.brilliant_obelisk", "altar_acceleration.brilliant_obelisk.1"))
                .addPage(SpiritInfusionPage.fromOutput(BRILLIANT_OBELISK.get()))
        );
    }
}
