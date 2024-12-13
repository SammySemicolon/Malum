package com.sammy.malum.client.screen.codex.objects.progression;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.BookWidgetStyle;
import com.sammy.malum.client.screen.codex.objects.BookObject;
import com.sammy.malum.client.screen.codex.screens.AbstractProgressionCodexScreen;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderTexture;

public class ProgressionEntryObject extends BookObject<AbstractProgressionCodexScreen> {

    public final BookEntry entry;
    public BookWidgetStyle style = BookWidgetStyle.RUNEWOOD;
    public Predicate<AbstractProgressionCodexScreen> isValid = t -> true;
    public ItemStack iconStack;

    public ProgressionEntryObject(BookEntry entry, int posX, int posY) {
        super(posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public boolean isValid(AbstractProgressionCodexScreen screen) {
        return isValid.test(screen) && entry.shouldShow();
    }

    @Override
    public void click(AbstractProgressionCodexScreen screen, double mouseX, double mouseY) {
        if (entry.hasContents()) {
            EntryScreen.openScreen(screen, this);
        }
    }

    @Override
    public void render(AbstractProgressionCodexScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final PoseStack poseStack = guiGraphics.pose();
        int posX = getOffsetXPosition() - (style.textureWidth() - 32) / 2;
        int posY = getOffsetYPosition() - (style.textureHeight() - 32) / 2;
        final int centerX = posX + style.textureWidth() / 2;
        final int centerY = posY + style.textureHeight() / 2;
        renderTexture(WIDGET_FADE_TEXTURE, poseStack, centerX - 29, centerY - 29, 0, 0, 58, 58);
        renderTexture(style.frameTexture(), poseStack, posX, posY, 0, 0, style.textureWidth(), style.textureHeight());
        renderTexture(style.fillingTexture(), poseStack, posX, posY, 0, 0, style.textureWidth(), style.textureHeight());
        if (iconStack != null) {
            guiGraphics.renderItem(iconStack, centerX - style.itemXOffset(), centerY - style.itemYOffset());
        }
    }

    @Override
    public void renderLate(AbstractProgressionCodexScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (isHoveredOver && entry.hasTooltip()) {
            final List<Component> list = Arrays.asList(
                ArcanaCodexHelper.convertToComponent(entry.translationKey(), entry.titleStyle),
                ArcanaCodexHelper.convertToComponent(entry.descriptionTranslationKey(), entry.subtitleStyle));
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, list, mouseX, mouseY);
        }
    }

    public ProgressionEntryObject setIcon(Supplier<? extends Item> item) {
        return setIcon(item.get());
    }

    public ProgressionEntryObject setIcon(Item item) {
        iconStack = item.getDefaultInstance();
        return this;
    }

    public ProgressionEntryObject setIcon(ItemStack itemStack) {
        iconStack = itemStack;
        return this;
    }

    public ProgressionEntryObject setStyle(BookWidgetStyle style) {
        this.style = style;
        return this;
    }

    public ProgressionEntryObject setValidityChecker(Predicate<AbstractProgressionCodexScreen> isValid) {
        this.isValid = isValid;
        return this;
    }
}
