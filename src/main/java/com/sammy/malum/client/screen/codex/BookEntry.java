package com.sammy.malum.client.screen.codex;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.client.VoidRevelationHandler;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import net.minecraft.client.*;
import net.minecraft.network.chat.Style;
import net.minecraft.stats.*;

import java.util.function.BooleanSupplier;
import java.util.function.UnaryOperator;

import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.BLACK_CRYSTAL;

public class BookEntry {

    public static final BooleanSupplier AFTER_SOME_TIME = () -> Minecraft.getInstance().player != null && (Minecraft.getInstance().player.getName().getString().equals("Dev") || Minecraft.getInstance().player.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME)) > 14400);
    public static final BooleanSupplier AFTER_UMBRAL_CRYSTAL = () -> VoidRevelationHandler.hasSeenTheRevelation(BLACK_CRYSTAL);

    public final String identifier;
    public final boolean isVoid;

    public final boolean isFragment;

    public final UnaryOperator<Style> titleStyle;
    public final UnaryOperator<Style> subtitleStyle;

    public final ImmutableList<BookPage> pages;
    public final ImmutableList<EntryReference> references;
    public final BooleanSupplier entryVisibleChecker;
    public final boolean tooltipDisabled;

    public BookEntry(String identifier, boolean isVoid,
                     ImmutableList<BookPage> pages, ImmutableList<EntryReference> references, BooleanSupplier validityChecker,
                     UnaryOperator<Style> titleStyle, UnaryOperator<Style> subtitleStyle, boolean tooltipDisabled, boolean isFragment) {
        this.identifier = identifier;
        this.isVoid = isVoid;
        this.pages = pages;
        this.references = references;
        this.entryVisibleChecker = validityChecker;
        this.titleStyle = titleStyle;
        this.subtitleStyle = subtitleStyle;
        this.tooltipDisabled = tooltipDisabled;
        this.isFragment = isFragment;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + identifier;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + identifier + ".description";
    }

    public boolean hasContents() {
        return !pages.isEmpty();
    }

    public boolean shouldShow() {
        return entryVisibleChecker.getAsBoolean();
    }

    public boolean hasTooltip() {
        return !tooltipDisabled;
    }

    public static PlacedBookEntryBuilder build(String identifier, int xOffset, int yOffset) {
        return new PlacedBookEntryBuilder(identifier, xOffset, yOffset);
    }

    public static BookEntryBuilder build(String identifier) {
        return new BookEntryBuilder(identifier);
    }
}
