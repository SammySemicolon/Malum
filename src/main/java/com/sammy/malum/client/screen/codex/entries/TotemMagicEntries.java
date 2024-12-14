package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.pages.recipe.*;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static net.minecraft.world.item.Items.*;

public class TotemMagicEntries {

    public static void setupEntries(ArcanaProgressionScreen screen) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        screen.addEntry("totem_magic", 0, 9, b -> b
                .configureWidget(w -> w.setIcon(RUNEWOOD_TOTEM_BASE).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("totem_magic", "totem_magic.1", RUNEWOOD_TOTEM_BASE.get()))
                .addPage(new TextPage("totem_magic.2"))
                .addPage(new TextPage("totem_magic.3"))
                .addPage(new TextPage("totem_magic.4"))
                .addPage(new TextPage("totem_magic.5"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_TOTEM_BASE.get()))
        );

        screen.addEntry("managing_totems", 0, 10, b -> b
                .configureWidget(w -> w.setIcon(TOTEMIC_STAFF).setStyle(BookWidgetStyle.SMALL_RUNEWOOD))
                .addPage(new HeadlineTextItemPage("managing_totems", "managing_totems.1", TOTEMIC_STAFF.get()))
                .addPage(new TextPage("managing_totems.2"))
                .addPage(new TextPage("managing_totems.3"))
                .addPage(new CraftingPage(TOTEMIC_STAFF.get(),
                        EMPTY, EMPTY, RUNEWOOD_PLANKS.get(),
                        EMPTY, STICK, EMPTY,
                        STICK, EMPTY, EMPTY
                ))
        );

        screen.addEntry("totemic_runes", -4, 15, b -> b
                .configureWidget(w -> w.setIcon(RUNE_OF_THE_ARENA).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("totemic_runes", "totemic_runes.1"))
                .addPage(new TextPage("totemic_runes.2"))
                .addPage(new EntrySelectorPage(item -> {
                    final String translationKey = BuiltInRegistries.ITEM.getKey(item).getPath();
                    return new EntryReference(item,
                            BookEntry.build(translationKey)
                                    .addPage(new HeadlineTextPage(translationKey))
                                    .addPage(RuneworkingPage.fromOutput(item)));
                },
                        RUNE_OF_MOTION.get(), RUNE_OF_LOYALTY.get(), RUNE_OF_WARDING.get(), RUNE_OF_HASTE.get(),
                        RUNE_OF_THE_AETHER.get(), RUNE_OF_THE_SEAS.get(), RUNE_OF_THE_ARENA.get(), RUNE_OF_THE_HELLS.get()))
        );

        screen.addEntry("sacred_rite", -2, 10, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "greater_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        screen.addEntry("corrupt_sacred_rite", -3, 10, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.SACRED_RITE, "corrupt_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "corrupt_greater_sacred_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        screen.addEntry("infernal_rite", -3, 11, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "greater_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        screen.addEntry("corrupt_infernal_rite", -4, 11, b -> b
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.INFERNAL_RITE, "corrupt_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "corrupt_greater_infernal_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        screen.addEntry("earthen_rite", -3, 12, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "greater_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        screen.addEntry("corrupt_earthen_rite", -4, 12, b -> b
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.EARTHEN_RITE, "corrupt_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "corrupt_greater_earthen_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        screen.addEntry("wicked_rite", 2, 10, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "greater_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        screen.addEntry("corrupt_wicked_rite", 3, 10, b -> b
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.WICKED_RITE, "corrupt_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "corrupt_greater_wicked_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        screen.addEntry("aerial_rite", 3, 11, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "greater_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        screen.addEntry("corrupt_aerial_rite", 4, 11, b -> b
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AERIAL_RITE, "corrupt_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "corrupt_greater_aerial_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        screen.addEntry("aqueous_rite", 3, 12, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "greater_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        screen.addEntry("corrupt_aqueous_rite", 4, 12, b -> b
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_SOULWOOD))
                .setWidgetSupplier(RiteEntryObject::new)
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.AQUEOUS_RITE, "corrupt_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "corrupt_greater_aqueous_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        screen.addEntry("arcane_rite", 0, 11, b -> b
                .setWidgetSupplier(RiteEntryObject::new)
                .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_TOTEMIC_RUNEWOOD))
                .addPage(new HeadlineTextPage("arcane_rite", "arcane_rite.description.1"))
                .addPage(new TextPage("arcane_rite.description.2"))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ARCANE_RITE, "arcane_rite"))
                .addPage(new SpiritRiteRecipePage(SpiritRiteRegistry.ARCANE_RITE))
                .addPage(new TextPage("arcane_rite.description.3"))
                .addPage(new SpiritRiteTextPage(SpiritRiteRegistry.ARCANE_RITE, "corrupt_arcane_rite"))
                .addPage(SpiritTransmutationRecipePage.fromInput("arcane_rite.soulwood", RUNEWOOD_SAPLING.get()))
                .addPage(new TextPage("arcane_rite.description.4"))
                .addPage(SpiritInfusionPage.fromOutput(SOULWOOD_TOTEM_BASE.get()))
        );

        screen.addEntry("blight", -1, 12, b -> b
                .configureWidget(w -> w.setIcon(BLIGHTED_GUNK).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("blight.intro", "blight.intro.1"))
                .addPage(new HeadlineTextPage("blight.composition", "blight.composition.1"))
                .addPage(new HeadlineTextPage("blight.spread", "blight.spread.1"))
                .addPage(new HeadlineTextPage("blight.arcane_rite", "blight.arcane_rite.1"))
        );

        screen.addEntry("soulwood", 1, 12, b -> b
                .configureWidget(w -> w.setIcon(SOULWOOD_GROWTH).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
                .addPage(new HeadlineTextPage("soulwood.intro", "soulwood.intro.1"))
                .addPage(new HeadlineTextPage("soulwood.bonemeal", "soulwood.bonemeal.1"))
                .addPage(new HeadlineTextPage("soulwood.color", "soulwood.color.1"))
                .addPage(new HeadlineTextPage("soulwood.blight", "soulwood.blight.1"))
                .addPage(new HeadlineTextPage("soulwood.sap", "soulwood.sap.1"))
        );
        screen.addEntry("transmutation", 0, 13, b -> b
                .configureWidget(w -> w.setIcon(SOUL_SAND).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("transmutation", "transmutation.intro.1"))
                .addPage(new TextPage("transmutation.intro.2"))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.stone", STONE))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.deepslate", DEEPSLATE))
                .addPage(new SpiritTransmutationRecipeTreePage("transmutation.smooth_basalt", SMOOTH_BASALT))
        );
    }
}