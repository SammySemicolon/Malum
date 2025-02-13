package com.sammy.malum.client.screen.codex.objects.progression;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.pages.text.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.core.systems.rite.*;
import net.minecraft.client.gui.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class RiteEntryObject extends ProgressionEntryObject {
    public final TotemicRiteType riteType;

    public RiteEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
        Optional<SpiritRiteTextPage> page = entry.pages.stream().filter(p -> p instanceof SpiritRiteTextPage).map(p -> ((SpiritRiteTextPage) p)).findAny();
        if (page.isPresent()) {
            this.riteType = page.get().riteType;
        } else {
            throw new IllegalArgumentException("Entry " + entry.translationKey() + " lacks a spirit rite page");
        }
    }

    @Override
    public void render(AbstractProgressionCodexScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(screen, guiGraphics, mouseX, mouseY, partialTicks);
        renderRiteIcon(riteType, guiGraphics.pose(), isCorrupted(), 0.4f, getOffsetXPosition() + 8, getOffsetYPosition() + 8);
    }

    public boolean isCorrupted() {
        return entry.identifier.contains("corrupt");
    }
}