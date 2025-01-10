package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class HeadlineTextPage extends BookPage {
    private final String headlineTranslationKey;
    private final String translationKey;

    public HeadlineTextPage(String translationKey) {
        this(translationKey, translationKey +".1");
    }
    public HeadlineTextPage(String headlineTranslationKey, String translationKey) {
        super(MalumMod.malumPath("textures/gui/book/pages/headline_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, left + 72 - Minecraft.getInstance().font.width(component.getString()) / 2, top + 5);
        renderWrappingText(guiGraphics, translationKey(), left + 6, top + 25, 130);
    }
}