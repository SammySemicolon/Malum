package com.sammy.malum.client.screen.codex.entries;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.recipe.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class AugmentationEntries {

    public static void setupEntries(ArcanaProgressionScreen screen) {
        Item EMPTY = ItemStack.EMPTY.getItem();

        screen.addEntry("crucible_augmentation", 13, 8, b -> b
                .configureWidget(w -> w.setIcon(TUNING_FORK).setStyle(BookWidgetStyle.GILDED_RUNEWOOD))
                .addPage(new HeadlineTextPage("crucible_augmentation", "crucible_augmentation.1"))
                .addPage(new TextPage("crucible_augmentation.2"))
                .addPage(new TextPage("crucible_augmentation.3"))
                .addPage(SpiritInfusionPage.fromOutput(TUNING_FORK.get()))
        );

        screen.addEntry("mending_diffuser", 14, 7, b -> b
                .configureWidget(w -> w.setIcon(MENDING_DIFFUSER))
                .addPage(new HeadlineTextPage("mending_diffuser", "mending_diffuser.1"))
                .addPage(SpiritInfusionPage.fromOutput(MENDING_DIFFUSER.get()))
        );

        screen.addEntry("impurity_stabilizer", 15, 7, b -> b
                .configureWidget(w -> w.setIcon(IMPURITY_STABILIZER))
                .addPage(new HeadlineTextPage("impurity_stabilizer", "impurity_stabilizer.1"))
                .addPage(SpiritInfusionPage.fromOutput(IMPURITY_STABILIZER.get()))
        );

        screen.addEntry("accelerating_inlay", 16, 8, b -> b
                .configureWidget(w -> w.setIcon(ACCELERATING_INLAY))
                .addPage(new HeadlineTextPage("accelerating_inlay", "accelerating_inlay.1"))
                .addPage(SpiritInfusionPage.fromOutput(ACCELERATING_INLAY.get()))
        );

        screen.addEntry("blazing_diode", 15, 8, b -> b
                .configureWidget(w -> w.setIcon(BLAZING_DIODE))
                .addPage(new HeadlineTextPage("blazing_diode", "blazing_diode.1"))
                .addPage(SpiritInfusionPage.fromOutput(BLAZING_DIODE.get()))
        );

        screen.addEntry("prismatic_focus_lens", 15, 9, b -> b
                .configureWidget(w -> w.setIcon(PRISMATIC_FOCUS_LENS))
                .addPage(new HeadlineTextPage("prismatic_focus_lens", "prismatic_focus_lens.1"))
                .addPage(SpiritInfusionPage.fromOutput(PRISMATIC_FOCUS_LENS.get()))
        );

        screen.addEntry("intricate_assembly", 16, 9, b -> b
                .configureWidget(w -> w.setIcon(INTRICATE_ASSEMBLY))
                .addPage(new HeadlineTextPage("intricate_assembly", "intricate_assembly.1"))
                .addPage(SpiritInfusionPage.fromOutput(INTRICATE_ASSEMBLY.get()))
        );

        screen.addEntry("shielding_apparatus", 16, 10, b -> b
                .configureWidget(w -> w.setIcon(SHIELDING_APPARATUS))
                .addPage(new HeadlineTextPage("shielding_apparatus", "shielding_apparatus.1"))
                .addPage(SpiritInfusionPage.fromOutput(SHIELDING_APPARATUS.get()))
        );

        screen.addEntry("warping_engine", 17, 10, b -> b
                .configureWidget(w -> w.setIcon(WARPING_ENGINE))
                .addPage(new HeadlineTextPage("warping_engine", "warping_engine.1"))
                .addPage(new TextPage("warping_engine.2"))
                .addPage(SpiritInfusionPage.fromOutput(WARPING_ENGINE.get()))
        );
    }
}
