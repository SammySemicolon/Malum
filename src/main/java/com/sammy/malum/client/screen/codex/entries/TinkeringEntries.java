package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
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

public class TinkeringEntries {

    public static void setupEntries(ArcanaProgressionScreen screen) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        var metalReexamination = new EntryReference(UMBRAL_SPIRIT, BookEntry.build("spirit_metals.reexamination")
                .addPage(new HeadlineTextPage("spirit_metals.reexamination", "spirit_metals.reexamination.1"))
                .addPage(new TextPage("spirit_metals.reexamination.2"))
                .afterUmbralCrystal()
        );

        screen.addEntry("spirit_metals.soul_stained_steel", -4, 5, b -> b
                .configureWidget(w -> w.setIcon(SOUL_STAINED_STEEL_INGOT))
                .addPage(new HeadlineTextItemPage("spirit_metals.soul_stained_steel", "spirit_metals.soul_stained_steel.1", SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new TextPage("spirit_metals.soul_stained_steel.2"))
                .addPage(new CyclingPage(
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.toolPage(SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_INGOT.get()),
                        CraftingPage.knifePage(SOUL_STAINED_STEEL_KNIFE.get(), SOUL_STAINED_STEEL_INGOT.get())
                ))
                .addReference(metalReexamination)
        );

        screen.addEntry("spirit_metals.hallowed_gold", -3, 7, b -> b
                .configureWidget(w -> w.setIcon(HALLOWED_GOLD_INGOT))
                .addPage(new HeadlineTextItemPage("spirit_metals.hallowed_gold", "spirit_metals.hallowed_gold.1", HALLOWED_GOLD_INGOT.get()))
                .addPage(SpiritInfusionPage.fromOutput(HALLOWED_GOLD_INGOT.get()))
                .addPage(new TextPage("spirit_metals.hallowed_gold.2"))
                .addPage(new CyclingPage(
                        new CraftingPage(GILDED_RUNEWOOD_ITEM_PEDESTAL.get(), EMPTY, EMPTY, EMPTY, HALLOWED_GOLD_NUGGET.get(), RUNEWOOD_ITEM_PEDESTAL.get(), HALLOWED_GOLD_NUGGET.get()),
                        new CraftingPage(GILDED_RUNEWOOD_ITEM_STAND.get(), EMPTY, EMPTY, EMPTY, HALLOWED_GOLD_NUGGET.get(), RUNEWOOD_ITEM_STAND.get(), HALLOWED_GOLD_NUGGET.get())
                ))
                .addReference(metalReexamination)
        );

        screen.addEntry("altar_acceleration", -4, 8, b -> b
                .configureWidget(w -> w.setIcon(RUNEWOOD_OBELISK))
                .addPage(new HeadlineTextPage("altar_acceleration.runewood_obelisk", "altar_acceleration.runewood_obelisk.1"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_OBELISK.get()))
                .addPage(new HeadlineTextPage("altar_acceleration.brilliant_obelisk", "altar_acceleration.brilliant_obelisk.1"))
                .addPage(SpiritInfusionPage.fromOutput(BRILLIANT_OBELISK.get()))
        );

        screen.addEntry("spirit_jar", -5, 8, b -> b
                .configureWidget(w -> w.setIcon(SPIRIT_JAR))
                .addPage(new HeadlineTextPage("spirit_jar", "spirit_jar.1"))
                .addPage(new CraftingPage(SPIRIT_JAR.get(), GLASS_PANE, HALLOWED_GOLD_INGOT.get(), GLASS_PANE, GLASS_PANE, EMPTY, GLASS_PANE, GLASS_PANE, GLASS_PANE, GLASS_PANE))
        );

        screen.addEntry("soulstained_scythe", -5, 6, b -> b
                .configureWidget(w -> w.setIcon(SOUL_STAINED_STEEL_SCYTHE))
                .addPage(new HeadlineTextPage("soulstained_scythe", "soulstained_scythe.1"))
                .addPage(SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_SCYTHE.get()))
        );

        screen.addEntry("soulstained_armor", -6, 6, b -> b
                .configureWidget(w -> w.setIcon(SOUL_STAINED_STEEL_HELMET))
                .addPage(new HeadlineTextPage("soulstained_armor", "soulstained_armor.1"))
                .addPage(new TextPage("soulstained_armor.2"))
                .addPage(new TextPage("soulstained_armor.3"))
                .addPage(new CyclingPage(
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_HELMET.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_CHESTPLATE.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_LEGGINGS.get()),
                        SpiritInfusionPage.fromOutput(SOUL_STAINED_STEEL_BOOTS.get())
                ))
                .addPage(new CraftingPage(new ItemStack(SOUL_STAINED_STEEL_PLATING.get(), 2), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_NUGGET.get(), EMPTY, SOUL_STAINED_STEEL_NUGGET.get(), EMPTY))
        );

        screen.addEntry("spirit_trinkets", -10, 7, b -> b
                .configureWidget(w -> w.setIcon(ORNATE_RING).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("spirit_trinkets", "spirit_trinkets.1"))
                .addPage(new TextPage("spirit_trinkets.2"))
                .addPage(CraftingPage.ringPage(ORNATE_RING.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CraftingPage(ORNATE_NECKLACE.get(), EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))
                .addPage(CraftingPage.ringPage(GILDED_RING.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(new CraftingPage(GILDED_BELT.get(), LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT.get(), REFINED_SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addReference(new EntryReference(RING_OF_ESOTERIC_SPOILS.get(),
                        BookEntry.build("ring_of_esoteric_spoils")
                                .addPage(new HeadlineTextPage("ring_of_esoteric_spoils", "ring_of_esoteric_spoils.1"))
                                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ESOTERIC_SPOILS.get()))))
        );

        screen.addEntry("reactive_trinkets",-12, 6, b -> b
                .configureWidget(w -> w.setIcon(RING_OF_CURATIVE_TALENT))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_curative_talent", "reactive_trinkets.ring_of_curative_talent.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_CURATIVE_TALENT.get()))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_alchemical_mastery", "reactive_trinkets.ring_of_alchemical_mastery.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ALCHEMICAL_MASTERY.get()))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_manaweaving", "reactive_trinkets.ring_of_manaweaving.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_MANAWEAVING.get()))
                .addPage(new HeadlineTextPage("reactive_trinkets.ring_of_prowess", "reactive_trinkets.ring_of_prowess.1"))
                .addPage(new TextPage("reactive_trinkets.ring_of_prowess.2"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ARCANE_PROWESS.get()))
                .addReference(new EntryReference(NECKLACE_OF_THE_MYSTIC_MIRROR.get(),
                        BookEntry.build("necklace_of_the_mystic_mirror")
                                .addPage(new HeadlineTextPage("necklace_of_the_mystic_mirror", "necklace_of_the_mystic_mirror.1"))
                                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_MYSTIC_MIRROR.get()))))
        );

        screen.addEntry("belt_of_the_starved",-11, 8, b -> b
                .configureWidget(w -> w.setIcon(BELT_OF_THE_STARVED))
                .addPage(new HeadlineTextPage("belt_of_the_starved", "belt_of_the_starved.1"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_STARVED.get()))
                .addPage(new TextPage("belt_of_the_starved.2"))
                .addReference(new EntryReference(
                        RING_OF_DESPERATE_VORACITY.get(),
                        BookEntry.build("belt_of_the_starved.ring_of_desperate_voracity")
                                .addPage(new HeadlineTextPage("belt_of_the_starved.ring_of_desperate_voracity", "belt_of_the_starved.ring_of_desperate_voracity.1"))
                                .addPage(SpiritInfusionPage.fromOutput(RING_OF_DESPERATE_VORACITY.get()))
                ))
                .addReference(new EntryReference(
                        CONCENTRATED_GLUTTONY.get(),
                        BookEntry.build("belt_of_the_starved.concentrated_gluttony")
                                .addPage(new HeadlineTextPage("belt_of_the_starved.concentrated_gluttony", "belt_of_the_starved.concentrated_gluttony.1"))
                                .addPage(SpiritInfusionPage.fromOutput(CONCENTRATED_GLUTTONY.get()))
                                .addPage(new TextPage("belt_of_the_starved.concentrated_gluttony.2"))
                                .addPage(new CyclingPage(
                                        SpiritInfusionPage.fromId(malumPath("spirit_infusion/splash_of_gluttony")),
                                        SpiritInfusionPage.fromId(malumPath("spirit_infusion/splash_of_gluttony_from_concentrated_gluttony"))
                                ))
                ))
        );

        screen.addEntry("necklace_of_the_narrow_edge", -10, 9, b -> b
                .configureWidget(w -> w.setIcon(NECKLACE_OF_THE_NARROW_EDGE))
                .addPage(new HeadlineTextPage("necklace_of_the_narrow_edge", "necklace_of_the_narrow_edge.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_NARROW_EDGE.get()))
        );

        screen.addEntry("belt_of_the_prospector", -9, 6, b -> b
                .configureWidget(w -> w.setIcon(BELT_OF_THE_PROSPECTOR))
                .addPage(new HeadlineTextPage("belt_of_the_prospector", "belt_of_the_prospector.1"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_PROSPECTOR.get()))
                .addReference(new EntryReference(
                        RING_OF_THE_HOARDER.get(),
                        BookEntry.build("belt_of_the_prospector.ring_of_the_hoarder")
                                .addPage(new HeadlineTextPage("belt_of_the_prospector.ring_of_the_hoarder", "belt_of_the_prospector.ring_of_the_hoarder.1"))
                                .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_HOARDER.get()))
                ))
                .addReference(new EntryReference(
                        RING_OF_THE_DEMOLITIONIST.get(),
                        BookEntry.build("belt_of_the_prospector.ring_of_the_demolitionist")
                                .addPage(new HeadlineTextPage("belt_of_the_prospector.ring_of_the_demolitionist", "belt_of_the_prospector.ring_of_the_demolitionist.1"))
                                .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_DEMOLITIONIST.get()))
                ))
        );

        screen.addEntry("necklace_of_blissful_harmony", -10, 5, b -> b
                .configureWidget(w -> w.setIcon(NECKLACE_OF_BLISSFUL_HARMONY))
                .addPage(new HeadlineTextPage("necklace_of_blissful_harmony", "necklace_of_blissful_harmony.1"))
                .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_BLISSFUL_HARMONY.get()))
                .addPage(new TextPage("necklace_of_blissful_harmony.2"))
        );

        screen.addEntry("belt_of_the_magebane", -5, 14, b -> b
                .configureWidget(w -> w.setStyle(BookWidgetStyle.SOULWOOD).setIcon(BELT_OF_THE_MAGEBANE))
                .addPage(new HeadlineTextPage("belt_of_the_magebane", "belt_of_the_magebane.1"))
                .addPage(new TextPage("belt_of_the_magebane.2"))
                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_MAGEBANE.get()))
        );

        screen.addEntry("tyrving", -3, 16, b -> b
                .configureWidget(w -> w.setStyle(BookWidgetStyle.SOULWOOD).setIcon(TYRVING))
                .addPage(new HeadlineTextPage("tyrving", "tyrving.1"))
                .addPage(SpiritInfusionPage.fromOutput(TYRVING.get()))
                .addPage(new TextPage("tyrving.2"))
                .addPage(SpiritRepairPage.fromId("tyrving"))
        );
    }
}
