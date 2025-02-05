package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.core.systems.rite.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritRiteTextPage extends BookPage {
    public final TotemicRiteType riteType;
    private final String translationKey;

    public SpiritRiteTextPage(TotemicRiteType riteType, String translationKey) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_rite_page.png"));
        this.riteType = riteType;
        this.translationKey = translationKey;
    }

    public String headlineTranslationKey() {
        return riteType.translationIdentifier(isCorrupted());
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, left + 70 - Minecraft.getInstance().font.width(component.getString()) / 2, top + 5);
        renderWrappingText(guiGraphics, translationKey(), left + 6, top + 78, 130);

        final int riteIconX = left + 63;
        final int riteIconY = top + 38;
        renderRiteIcon(riteType, guiGraphics.pose(), isCorrupted(), 0.4f, riteIconX, riteIconY);
        if (screen.isHovering(mouseX, mouseY, riteIconX, riteIconY, 16, 16)) {
            screen.renderLater(()->guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, riteType.makeDetailedDescriptor(isCorrupted()), mouseX, mouseY));
        }
    }

    public boolean isCorrupted() {
        return translationKey.contains("corrupt");
    }
}
