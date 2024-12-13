package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.pages.CyclingPage;
import com.sammy.malum.client.screen.codex.pages.recipe.SpiritInfusionPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextItemPage;
import com.sammy.malum.client.screen.codex.pages.text.HeadlineTextPage;
import com.sammy.malum.client.screen.codex.pages.text.TextPage;
import com.sammy.malum.client.screen.codex.screens.ArcanaProgressionScreen;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class MiscellaneousKnowledgeEntries {

    public static void setupEntries(ArcanaProgressionScreen screen) {
        screen.addEntry("spirited_glass", -8, 10, b -> b
                .configureWidget(w -> w.setIcon(WICKED_SPIRITED_GLASS))
                .addPage(new HeadlineTextPage("spirited_glass", "spirited_glass.1"))
                .addPage(new CyclingPage(
                        SpiritInfusionPage.fromOutput(SACRED_SPIRITED_GLASS.get()),
                        SpiritInfusionPage.fromOutput(WICKED_SPIRITED_GLASS.get()),
                        SpiritInfusionPage.fromOutput(ARCANE_SPIRITED_GLASS.get()),
                        SpiritInfusionPage.fromOutput(ELDRITCH_SPIRITED_GLASS.get()),
                        SpiritInfusionPage.fromOutput(AERIAL_SPIRITED_GLASS.get()),
                        SpiritInfusionPage.fromOutput(AQUEOUS_SPIRITED_GLASS.get()),
                        SpiritInfusionPage.fromOutput(INFERNAL_SPIRITED_GLASS.get()),
                        SpiritInfusionPage.fromOutput(EARTHEN_SPIRITED_GLASS.get())
                ))
        );

        screen.addEntry("mote_making", 8, 10    , b -> b
                .configureWidget(w -> w.setIcon(LAMPLIGHTERS_TONGS))
                .addPage(new HeadlineTextItemPage("mote_making", "mote_making.1", LAMPLIGHTERS_TONGS.get()))
                .addPage(new TextPage("mote_making.2"))
                .addPage(SpiritInfusionPage.fromOutput(LAMPLIGHTERS_TONGS.get()))
        );
    }
}
