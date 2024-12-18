package com.sammy.malum.client.screen.codex;

import com.google.common.collect.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.Nullable;

import java.util.function.*;

public class PlacedBookEntryBuilder extends BookEntryBuilder {

    protected PlacedBookEntry.WidgetSupplier widgetSupplier = ProgressionEntryObject::new;
    @Nullable
    protected Consumer<ProgressionEntryObject> widgetConfig = null;

    @Nullable
    protected Consumer<PlacedBookEntryBuilder> fragmentProperties = null;
    protected boolean isFragment = false;

    protected final int xOffset;
    protected final int yOffset;

    protected PlacedBookEntryBuilder(String identifier, boolean isVoid, int xOffset, int yOffset) {
        super(identifier, isVoid);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public PlacedBookEntryBuilder(String identifier, int xOffset, int yOffset) {
        super(identifier);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public PlacedBookEntryBuilder setWidgetSupplier(PlacedBookEntry.WidgetSupplier widgetSupplier) {
        this.widgetSupplier = widgetSupplier;
        return this;
    }

    public PlacedBookEntryBuilder configureWidget(Consumer<ProgressionEntryObject> configure) {
        this.widgetConfig = this.widgetConfig == null ? configure : this.widgetConfig.andThen(configure);
        return this;
    }

    public PlacedBookEntryBuilder withEmptyFragmentEntry(BookWidgetStyle style) {
        this.fragmentProperties = b -> b
            .configureWidget(widget -> widget.setStyle(style))
            .styleTitle(s -> s.withColor(ChatFormatting.GRAY))
            .styleSubtitle(s -> s.withColor(ChatFormatting.DARK_GRAY));
        return this;
    }

    public PlacedBookEntryBuilder withTraceFragmentEntry() {
        this.fragmentProperties = b -> b
            .configureWidget(widget -> widget.setStyle(BookWidgetStyle.FRAMELESS)) // todo: add cool visual effects for Traces
            .disableTooltip();

        return this;
    }

    public PlacedBookEntryBuilder withFragmentEntry(Consumer<PlacedBookEntryBuilder> properties) {
        this.fragmentProperties = this.fragmentProperties == null ? properties : this.fragmentProperties.andThen(properties);
        return this;
    }

    // Should only be invoked internally
    protected PlacedBookEntryBuilder setFragment(boolean fragmentState) {
        this.isFragment = fragmentState;
        return this;
    }

    public boolean hasFragment() {
        return fragmentProperties != null;
    }

    public PlacedBookEntry buildFragment() {
        if (fragmentProperties == null)
            return null;

        PlacedBookEntryBuilder builder = new PlacedBookEntryBuilder("fragment." + identifier, isVoid, xOffset, yOffset);
        builder
            .setFragment(true)
            .configureWidget(widgetConfig)
            .setWidgetSupplier(widgetSupplier)
            .setEntryVisibleWhen(() -> !entryVisibleChecker.getAsBoolean())
            .styleTitle(style -> style.withItalic(true))
            .styleSubtitle(style -> style.withItalic(true));
        fragmentProperties.accept(builder);
        return builder.build();
    }

    @Override
    public PlacedBookEntry build() {
        ImmutableList<BookPage> bookPages = ImmutableList.copyOf(pages);
        ImmutableList<EntryReference> entryReferences = ImmutableList.copyOf(references);
        PlacedBookEntry.BookEntryWidgetPlacementData data = new PlacedBookEntry.BookEntryWidgetPlacementData(xOffset*40, yOffset*40, widgetSupplier, widgetConfig);
        PlacedBookEntry bookEntry = new PlacedBookEntry(identifier, isVoid, data, bookPages, entryReferences, entryVisibleChecker, titleStyle, subtitleStyle, tooltipDisabled, isFragment);
        bookPages.forEach(p -> p.setBookEntry(bookEntry));
        return bookEntry;
    }
}
