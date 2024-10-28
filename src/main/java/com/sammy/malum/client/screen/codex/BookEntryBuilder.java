package com.sammy.malum.client.screen.codex;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.pages.EntryReference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.UnaryOperator;

public class BookEntryBuilder {

    protected final String identifier;
    protected final boolean isVoid;

    protected UnaryOperator<Style> titleStyle = UnaryOperator.identity();
    protected UnaryOperator<Style> subtitleStyle = (style) -> style.withColor(ChatFormatting.GRAY);

    protected List<BookPage> pages = new ArrayList<>();
    protected List<EntryReference> references = new ArrayList<>();
    protected BooleanSupplier entryVisibleChecker = () -> true;
    protected boolean tooltipDisabled = false;

    protected BookEntryBuilder(String identifier, boolean isVoid) {
        this.identifier = identifier;
        this.isVoid = isVoid;
    }

    public BookEntryBuilder(String identifier) {
        this(identifier, identifier.startsWith("void."));
    }

    public BookEntryBuilder addPage(BookPage page) {
        if (page.isValid()) {
            pages.add(page);
        }
        return this;
    }

    public BookEntryBuilder addReference(EntryReference reference) {
        references.add(reference);
        return this;
    }

    public BookEntryBuilder styleTitle(UnaryOperator<Style> styleFunction) {
        final UnaryOperator<Style> existingStyle = titleStyle;
        titleStyle = (style) -> styleFunction.apply(existingStyle.apply(style));
        return this;
    }

    public BookEntryBuilder styleSubtitle(UnaryOperator<Style> styleFunction) {
        final UnaryOperator<Style> existingStyle = subtitleStyle;
        subtitleStyle = (style) -> styleFunction.apply(existingStyle.apply(style));
        return this;
    }

    public BookEntryBuilder setEntryVisibleWhen(BooleanSupplier condition) {
        this.entryVisibleChecker = condition;
        return this;
    }

    public BookEntryBuilder afterSomeTime() {
        this.entryVisibleChecker = BookEntry.AFTER_SOME_TIME;
        return this;
    }

    public BookEntryBuilder afterUmbralCrystal() {
        this.entryVisibleChecker = BookEntry.AFTER_UMBRAL_CRYSTAL;
        return this;
    }

    public BookEntryBuilder disableTooltip() {
        this.tooltipDisabled = true;
        return this;
    }

    public BookEntry build() {
        ImmutableList<BookPage> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference> entryReferences = ImmutableList.copyOf(references);
        BookEntry bookEntry = new BookEntry(identifier, isVoid, bookPages, entryReferences, entryVisibleChecker, titleStyle, subtitleStyle, tooltipDisabled, false);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}
