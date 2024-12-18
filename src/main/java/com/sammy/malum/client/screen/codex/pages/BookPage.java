package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import org.jetbrains.annotations.Nullable;


import static com.sammy.malum.client.screen.codex.screens.EntryScreen.*;

public abstract class BookPage {
    @Nullable
    protected final ResourceLocation background;
    protected BookEntry bookEntry;

    public BookPage(@Nullable ResourceLocation background) {
        this.background = background;
    }

    public boolean isValid() {
        return true;
    }


    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
    }
    public void renderLate(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
    }

    public void click(EntryScreen screen, int left, int top, double mouseX, double mouseY, double relativeMouseX, double relativeMouseY) {
    }


    public void render(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
    }

    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {

    }

    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {

    }

    public ResourceLocation getBackground(boolean isRightSide) {
        return background;
    }

    public int getGuiLeft() {
        return (entryScreen.width - entryScreen.bookWidth) / 2;
    }

    public int getGuiTop() {
        return (entryScreen.height - entryScreen.bookHeight) / 2;
    }

    public BookPage setBookEntry(BookEntry bookEntry) {
        this.bookEntry = bookEntry;
        return this;
    }
}