package com.sammy.malum.client.screen.codex.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.VoidRevelationHandler;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.BookWidgetStyle;
import com.sammy.malum.client.screen.codex.PlacedBookEntry;
import com.sammy.malum.client.screen.codex.objects.progression.IconObject;
import com.sammy.malum.client.screen.codex.objects.progression.ScreenOpenerObject;
import com.sammy.malum.client.screen.codex.pages.CyclingPage;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import com.sammy.malum.client.screen.codex.pages.EntrySelectorPage;
import com.sammy.malum.client.screen.codex.pages.recipe.RuneworkingPage;
import com.sammy.malum.client.screen.codex.pages.recipe.SpiritInfusionPage;
import com.sammy.malum.client.screen.codex.pages.recipe.vanilla.CraftingPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage;
import com.sammy.malum.client.screen.codex.pages.text.TextPage;
import com.sammy.malum.client.screen.codex.pages.text.WeepingWellTextPage;
import com.sammy.malum.common.events.SetupMalumCodexEntriesEvent;
import com.sammy.malum.common.item.codex.EncyclopediaEsotericaItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.VOID_READER;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class VoidProgressionScreen extends AbstractProgressionCodexScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = malumPath("textures/gui/book/void_background.png");

    public static VoidProgressionScreen screen;

    public static final List<PlacedBookEntry> VOID_ENTRIES = new ArrayList<>();

    protected VoidProgressionScreen() {
        super(SoundRegistry.ARCANA_SWEETENER_EVIL, 1024, 768);
        minecraft = Minecraft.getInstance();
        setupEntries();
        NeoForge.EVENT_BUS.post(new SetupMalumCodexEntriesEvent());
        setupObjects();
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        renderBackground(poseStack, BACKGROUND_TEXTURE, 0.2f, 0.2f);
    }

    @Override
    public Collection<PlacedBookEntry> getEntries() {
        return VOID_ENTRIES;
    }

    public static VoidProgressionScreen getScreenInstance() {
        if (screen == null) {
            screen = new VoidProgressionScreen();
        }
        return screen;
    }

    public static void openCodexViaItem() {
        getScreenInstance().openScreen(true);
        screen.playSweetenedSound(SoundRegistry.ARCANA_CODEX_OPEN, 1.25f);
    }

    public static void openCodexViaTransition() {
        getScreenInstance().openScreen(false);
        screen.faceObject(screen.bookObjectHandler.get(0));
        screen.playSound(SoundRegistry.ARCANA_TRANSITION_EVIL, 1.25f, 1f);
        screen.timesTransitioned++;
        screen.transitionTimer = screen.getTransitionDuration();
        EncyclopediaEsotericaItem.shouldOpenVoidCodex = true;
        VoidRevelationHandler.seeTheRevelation(VOID_READER);
    }

    public void setupEntries() {
        Item EMPTY = ItemStack.EMPTY.getItem();

        addEntry("chronicles_of_the_soul", 0, 0, b -> b
            .setWidgetSupplier((e, x, y) -> new ScreenOpenerObject(e, x, y, ArcanaProgressionScreen::openCodexViaTransition, malumPath("textures/gui/book/icons/arcana_button.png"), 20, 20))
            .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_GRAND_SOULWOOD))
        );
        addEntry("void.the_weeping_well", 0, 1, b -> b
            .configureWidget(w -> w.setIcon(VOID_DEPOT).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
            .addPage(new HeadlineTextPage("void.the_weeping_well", "void.the_weeping_well.1"))
            .addPage(new TextPage("void.the_weeping_well.2"))
            .addPage(new TextPage("void.the_weeping_well.3"))
            .addPage(new TextPage("void.the_weeping_well.4"))
            .addPage(new TextPage("void.the_weeping_well.5"))
            .addPage(new TextPage("void.the_weeping_well.6"))
            .addPage(new TextPage("void.the_weeping_well.7"))
        );

        addEntry("void.material_study_soulstone", 0, 2, b -> b
            .configureWidget(w -> w.setIcon(RAW_SOULSTONE).setStyle(BookWidgetStyle.SMALL_SOULWOOD))
            .addPage(new WeepingWellTextPage("void.material_study_soulstone", "void.material_study_soulstone.1", RAW_SOULSTONE.get()))
            .addPage(new TextPage("void.material_study_soulstone.2"))
        );

        addEntry("void.material_study_null_slate", -2, 3, b -> b
            .configureWidget(w -> w.setIcon(NULL_SLATE).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new WeepingWellTextPage("void.material_study_null_slate", "void.material_study_null_slate.1", NULL_SLATE.get()))
            .addPage(new TextPage("void.material_study_null_slate.2"))
            .addReference(new EntryReference(UMBRAL_SPIRIT,
                BookEntry.build("void.material_study_null_slate.reexamination")
                    .addPage(new HeadlineTextPage("void.material_study_null_slate.reexamination", "void.material_study_null_slate.reexamination.1"))
                    .addPage(new TextPage("void.material_study_null_slate.reexamination.2"))
                    .afterUmbralCrystal()
            ))
        );

        addEntry("void.material_study_mnemonic_fragment", -3, 4, b -> b
            .configureWidget(w -> w.setIcon(MNEMONIC_FRAGMENT).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new WeepingWellTextPage("void.material_study_mnemonic_fragment", "void.material_study_mnemonic_fragment.1", MNEMONIC_FRAGMENT.get()))
            .addPage(new TextPage("void.material_study_mnemonic_fragment.2"))
            .addReference(new EntryReference(UMBRAL_SPIRIT,
                BookEntry.build("void.material_study_mnemonic_fragment.reexamination")
                    .addPage(new HeadlineTextPage("void.material_study_mnemonic_fragment.reexamination", "void.material_study_mnemonic_fragment.reexamination.1"))
                    .afterUmbralCrystal()
            ))
        );

        addEntry("void.material_study_void_salts", 0, 3, b -> b
            .configureWidget(w -> w.setIcon(VOID_SALTS).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new WeepingWellTextPage("void.material_study_void_salts", "void.material_study_void_salts.1", VOID_SALTS.get()))
            .addPage(new TextPage("void.material_study_void_salts.2"))
            .addReference(new EntryReference(UMBRAL_SPIRIT,
                BookEntry.build("void.material_study_void_salts.reexamination")
                    .addPage(new HeadlineTextPage("void.material_study_void_salts.reexamination", "void.material_study_void_salts.reexamination.1"))
                    .afterUmbralCrystal()
            ))
        );

        addEntry("void.material_study_malignant_lead", 2, 3, b -> b
            .configureWidget(w -> w.setIcon(MALIGNANT_LEAD).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new WeepingWellTextPage("void.material_study_malignant_lead", "void.material_study_malignant_lead.1", MALIGNANT_LEAD.get()))
            .addPage(new TextPage("void.material_study_malignant_lead.2"))
            .addReference(new EntryReference(UMBRAL_SPIRIT,
                BookEntry.build("void.material_study_malignant_lead.reexamination")
                    .addPage(new HeadlineTextPage("void.material_study_malignant_lead.reexamination", "void.material_study_malignant_lead.reexamination.1"))
                    .addPage(new TextPage("void.material_study_malignant_lead.reexamination.2"))
                    .afterUmbralCrystal()
            ))
        );

        addEntry("void.material_study_auric_embers", 3, 4, b -> b
            .configureWidget(w -> w.setIcon(AURIC_EMBERS).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new WeepingWellTextPage("void.material_study_auric_embers", "void.material_study_auric_embers.1", AURIC_EMBERS.get()))
            .addPage(new TextPage("void.material_study_auric_embers.2"))
            .addReference(new EntryReference(UMBRAL_SPIRIT,
                BookEntry.build("void.material_study_auric_embers.reexamination")
                    .addPage(new HeadlineTextPage("void.material_study_auric_embers.reexamination", "void.material_study_auric_embers.reexamination.1"))
                    .afterUmbralCrystal()
            ))
        );

        addEntry("void.catalyst_lobber", 5, 5, b -> b
                .configureWidget(w -> w.setIcon(CATALYST_LOBBER).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new WeepingWellTextPage("void.catalyst_lobber", "void.catalyst_lobber.1", CATALYST_LOBBER.get()))
                .addPage(new TextPage("void.catalyst_lobber.2"))
                .addPage(new TextPage("void.catalyst_lobber.3"))
                .addPage(SpiritInfusionPage.fromOutput(CATALYST_LOBBER.get()))
        );

        addEntry("void.black_crystal", 0, 5, b -> b
            .withFragmentEntry(fragment -> fragment.addPage(new WeepingWellTextPage("fragment.void.black_crystal", "fragment.void.black_crystal.1", EMPTY)))
            .configureWidget(w -> w.setIcon(UMBRAL_SPIRIT).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
            .addPage(new WeepingWellTextPage("void.black_crystal", "void.black_crystal.1", UMBRAL_SPIRIT.get()))
            .addPage(new TextPage("void.black_crystal.2"))
            .addPage(new TextPage("void.black_crystal.3"))
            .addPage(new TextPage("void.black_crystal.4"))
            .afterUmbralCrystal()
        );

        addEntry("void.umbral_arcana", -1, 6, b -> b
            .withEmptyFragmentEntry(BookWidgetStyle.WITHERED)
            .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_SOULWOOD))
            .setWidgetSupplier((e, x, y) -> new IconObject(e, x, y, malumPath("textures/gui/book/icons/umbral_shard.png")))
            .addPage(new HeadlineTextPage("void.umbral_arcana", "void.umbral_arcana.1"))
            .addPage(new TextPage("void.umbral_arcana.2"))
            .addPage(new TextPage("void.umbral_arcana.3"))
            .addPage(new TextPage("void.umbral_arcana.4"))
            .addPage(new TextPage("void.umbral_arcana.5"))
            .afterUmbralCrystal()
        );

        addEntry("void.inverse_and_hybrid_arcana", 0, 7, b -> b
            .withEmptyFragmentEntry(BookWidgetStyle.SMALL_WITHERED)
            .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_SOULWOOD))
            .setWidgetSupplier((e, x, y) -> new IconObject(e, x, y, malumPath("textures/gui/book/icons/umbral_shard.png")))
            .addPage(new HeadlineTextPage("void.inverse_and_hybrid_arcana", "void.inverse_and_hybrid_arcana.1"))
            .addPage(new TextPage("void.inverse_and_hybrid_arcana.2"))
            .addPage(new TextPage("void.inverse_and_hybrid_arcana.3"))
            .afterUmbralCrystal()
        );

        addEntry("void.material_study_arcana", 1, 8, b -> b
            .withTraceFragmentEntry()
            .configureWidget(w -> w.setStyle(BookWidgetStyle.DARK_SOULWOOD))
            .setWidgetSupplier((e, x, y) -> new IconObject(e, x, y, malumPath("textures/gui/book/icons/umbral_shard.png")))
            .addPage(new HeadlineTextPage("void.material_study_arcana", "void.material_study_arcana.1"))
            .addPage(new TextPage("void.material_study_arcana.2"))
            .addPage(new TextPage("void.material_study_arcana.3"))
            .afterUmbralCrystal()
        );

        addEntry("void.staves_as_foci", 0, 9, b -> b
            .withTraceFragmentEntry()
            .configureWidget(w -> w.setIcon(MNEMONIC_HEX_STAFF).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
            .addPage(new HeadlineTextPage("void.staves_as_foci", "void.staves_as_foci.1"))
            .addPage(new TextPage("void.staves_as_foci.2"))
            .addPage(new TextPage("void.staves_as_foci.3"))
            .addPage(new TextPage("void.staves_as_foci.4"))
            .addPage(new TextPage("void.staves_as_foci.5"))
            .addPage(SpiritInfusionPage.fromOutput(MNEMONIC_HEX_STAFF.get()))
            .addReference(new EntryReference(RING_OF_THE_ENDLESS_WELL,
                BookEntry.build("void.staves_as_foci.ring_of_the_endless_well")
                    .addPage(new HeadlineTextPage("void.staves_as_foci.ring_of_the_endless_well", "void.staves_as_foci.ring_of_the_endless_well.1"))
                    .addPage(SpiritInfusionPage.fromOutput(RING_OF_THE_ENDLESS_WELL.get()))
            ))
            .afterUmbralCrystal()
        );

        addEntry("void.ring_of_gruesome_concentration", -3, 9, b -> b
            .configureWidget(w -> w.setIcon(RING_OF_GRUESOME_CONCENTRATION).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new HeadlineTextPage("void.ring_of_gruesome_concentration", "void.ring_of_gruesome_concentration.1"))
            .addPage(SpiritInfusionPage.fromOutput(RING_OF_GRUESOME_CONCENTRATION.get()))
            .afterUmbralCrystal()
        );
        addEntry("void.ring_of_growing_flesh", -4, 10, b -> b
                .withTraceFragmentEntry()
                .configureWidget(w -> w.setIcon(RING_OF_GROWING_FLESH).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.ring_of_growing_flesh", "void.ring_of_growing_flesh.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_GROWING_FLESH.get()))
                .afterUmbralCrystal()
        );
        addEntry("void.ring_of_echoing_arcana", -5, 10, b -> b
                .withTraceFragmentEntry()
                .configureWidget(w -> w.setIcon(RING_OF_ECHOING_ARCANA).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.ring_of_echoing_arcana", "void.ring_of_echoing_arcana.1"))
                .addPage(SpiritInfusionPage.fromOutput(RING_OF_ECHOING_ARCANA.get()))
                .afterUmbralCrystal()
        );
        addEntry("void.necklace_of_the_watcher", -3, 11, b -> b
            .configureWidget(w -> w.setIcon(NECKLACE_OF_THE_WATCHER).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new HeadlineTextPage("void.necklace_of_the_watcher", "void.necklace_of_the_watcher.1"))
            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_WATCHER.get()))
            .afterUmbralCrystal()
        );
        addEntry("void.necklace_of_the_hidden_blade", -4, 12, b -> b
            .configureWidget(w -> w.setIcon(NECKLACE_OF_THE_HIDDEN_BLADE).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new HeadlineTextPage("void.necklace_of_the_hidden_blade", "void.necklace_of_the_hidden_blade.1"))
            .addPage(SpiritInfusionPage.fromOutput(NECKLACE_OF_THE_HIDDEN_BLADE.get()))
            .afterUmbralCrystal()
        );

        addEntry("void.malignant_pewter", 3, 9, b -> b
            .withTraceFragmentEntry()
            .configureWidget(w -> w.setIcon(MALIGNANT_PEWTER_INGOT).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new HeadlineTextPage("void.malignant_pewter", "void.malignant_pewter.1"))
            .addPage(SpiritInfusionPage.fromOutput(MALIGNANT_PEWTER_INGOT.get()))
            .addPage(new TextPage("void.malignant_pewter.2"))
            .addPage(new TextPage("void.malignant_pewter.3"))
            .afterUmbralCrystal()
        );

        addEntry("void.weight_of_worlds", 4, 10, b -> b
                .configureWidget(w -> w.setIcon(WEIGHT_OF_WORLDS).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.weight_of_worlds", "void.weight_of_worlds.1"))
                .addPage(SpiritInfusionPage.fromOutput(WEIGHT_OF_WORLDS.get()))
                .addPage(new TextPage("void.weight_of_worlds.2"))
                .afterUmbralCrystal()
        );

        addEntry("void.edge_of_deliverance", 5, 10, b -> b
                .configureWidget(w -> w.setIcon(EDGE_OF_DELIVERANCE).setStyle(BookWidgetStyle.SOULWOOD))
                .addPage(new HeadlineTextPage("void.edge_of_deliverance", "void.edge_of_deliverance.1"))
                .addPage(SpiritInfusionPage.fromOutput(EDGE_OF_DELIVERANCE.get()))
                .addPage(new TextPage("void.edge_of_deliverance.2"))
                .afterUmbralCrystal()
        );

        addEntry("void.erosion_scepter", 3, 11, b -> b
            .configureWidget(w -> w.setIcon(EROSION_SCEPTER).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new HeadlineTextPage("void.erosion_scepter", "void.erosion_scepter.1"))
            .addPage(SpiritInfusionPage.fromOutput(EROSION_SCEPTER.get()))
            .addPage(new TextPage("void.erosion_scepter.2"))
            .addPage(new TextPage("void.erosion_scepter.3"))
            .addPage(new TextPage("void.erosion_scepter.4"))
            .afterUmbralCrystal()
        );

        addEntry("void.malignant_stronghold_armor", 4, 12, b -> b
            .configureWidget(w -> w.setIcon(MALIGNANT_STRONGHOLD_HELMET).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new HeadlineTextPage("void.malignant_stronghold_armor", "void.malignant_stronghold_armor.1"))
            .addPage(new TextPage("void.malignant_stronghold_armor.2"))
            .addPage(new CyclingPage(
                SpiritInfusionPage.fromOutput(MALIGNANT_STRONGHOLD_HELMET.get()),
                SpiritInfusionPage.fromOutput(MALIGNANT_STRONGHOLD_CHESTPLATE.get()),
                SpiritInfusionPage.fromOutput(MALIGNANT_STRONGHOLD_LEGGINGS.get()),
                SpiritInfusionPage.fromOutput(MALIGNANT_STRONGHOLD_BOOTS.get())
            ))
            .addPage(new CraftingPage(new ItemStack(MALIGNANT_PEWTER_PLATING.get(), 2), EMPTY, MALIGNANT_PEWTER_NUGGET.get(), EMPTY, MALIGNANT_PEWTER_NUGGET.get(), MALIGNANT_PEWTER_INGOT.get(), MALIGNANT_PEWTER_NUGGET.get(), EMPTY, MALIGNANT_PEWTER_NUGGET.get(), EMPTY))
            .afterUmbralCrystal()
        );

        addEntry("void.runes", 0, 11, b -> b
            .configureWidget(w -> w.setIcon(RUNE_OF_THE_HERETIC).setStyle(BookWidgetStyle.SOULWOOD))
            .addPage(new HeadlineTextPage("void.runes", "void.runes.1"))
            .addPage(SpiritInfusionPage.fromOutput(VOID_TABLET.get()))
            .addPage(new TextPage("void.runes.2"))
            .addPage(new EntrySelectorPage(item -> {
                final String translationKey = "void." + ForgeRegistries.ITEMS.getKey(item).getPath();
                return new EntryReference(item,
                    BookEntry.build(translationKey)
                        .addPage(new HeadlineTextPage(translationKey))
                        .addPage(RuneworkingPage.fromOutput(item)));
            },
                RUNE_OF_BOLSTERING.get(), RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(), RUNE_OF_SPELL_MASTERY.get(), RUNE_OF_THE_HERETIC.get(),
                RUNE_OF_UNNATURAL_STAMINA.get(), RUNE_OF_TWINNED_DURATION.get(), RUNE_OF_TOUGHNESS.get(), RUNE_OF_IGNEOUS_SOLACE.get()))
            .afterUmbralCrystal()
        );

//        addEntry("void.anomalous_design", 0, 7, b -> b
//                .setWidgetConfig(w -> w.setIcon(ANOMALOUS_DESIGN).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextItemPage("void.anomalous_design", "void.anomalous_design.1", ANOMALOUS_DESIGN.get()))
//                .addPage(SpiritInfusionPage.fromOutput(COMPLETE_DESIGN.get()))
//        );
//        addEntry("void.fused_consciousness", 0, 8, b -> b
//                .setWidgetConfig(w -> w.setIcon(FUSED_CONSCIOUSNESS).setStyle(BookWidgetStyle.GILDED_SOULWOOD))
//                .addPage(new HeadlineTextItemPage("void.fused_consciousness", "void.fused_consciousness.1", FUSED_CONSCIOUSNESS.get()))
//        );
//        addEntry("void.belt_of_the_limitless", -2, 9, b -> b
//                .setWidgetConfig(w -> w.setIcon(BELT_OF_THE_LIMITLESS).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextPage("void.belt_of_the_limitless", "void.belt_of_the_limitless.1"))
//                .addPage(SpiritInfusionPage.fromOutput(BELT_OF_THE_LIMITLESS.get()))
//        );
//        addEntry("void.stellar_mechanism", 2, 9, b -> b
//                .setWidgetConfig(w -> w.setIcon(STELLAR_MECHANISM).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextPage("void.stellar_mechanism", "void.stellar_mechanism.1"))
//                .addPage(SpiritInfusionPage.fromOutput(STELLAR_MECHANISM.get()))
//        );
//        addEntry("void.staff_of_the_auric_flame", 0, 10, b -> b
//                .setWidgetConfig(w -> w.setIcon(STAFF_OF_THE_AURIC_FLAME).setStyle(BookWidgetStyle.SOULWOOD))
//                .addPage(new HeadlineTextPage("void.staff_of_the_auric_flame", "void.staff_of_the_auric_flame.1"))
//                .addPage(SpiritInfusionPage.fromOutput(STAFF_OF_THE_AURIC_FLAME.get()))
//        );
    }
}
