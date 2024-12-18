package com.sammy.malum.core.systems.events;


import com.sammy.malum.client.screen.codex.PlacedBookEntry;
import com.sammy.malum.client.screen.codex.screens.ArcanaProgressionScreen;
import com.sammy.malum.client.screen.codex.screens.VoidProgressionScreen;
import net.fabricmc.fabric.api.event.Event;

import java.util.List;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

/**
 * Called when the book is opened for the first time per minecraft client instance, prior to setting up book objects.
 * All the relevant book parameters are static, so you may simply edit those.
 */
public interface SetupMalumCodexEntriesEvent {

    Event<Setup> EVENT = createArrayBacked(Setup.class, listeners -> (arcanaProgressionScreen, entries) -> {
        for (Setup listener : listeners) {
            listener.onSetup(arcanaProgressionScreen, entries);
        }
    });

    Event<SetupVoid> VOID_EVENT = createArrayBacked(SetupVoid.class, listeners -> (voidProgressionScreen, entries) -> {
        for (SetupVoid listener : listeners) {
            listener.onSetup(voidProgressionScreen, entries);
        }
    });

    @FunctionalInterface
    interface Setup {
        void onSetup(ArcanaProgressionScreen arcanaProgressionScreen, List<PlacedBookEntry> bookEntry);
    }

    @FunctionalInterface
    interface SetupVoid {
        void onSetup(VoidProgressionScreen voidProgressionScreen, List<PlacedBookEntry> bookEntry);
    }
}
