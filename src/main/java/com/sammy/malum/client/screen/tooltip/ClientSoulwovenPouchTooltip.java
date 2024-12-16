package com.sammy.malum.client.screen.tooltip;

import com.sammy.malum.common.data_components.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.*;
import org.apache.commons.lang3.math.*;

public class ClientSoulwovenPouchTooltip implements ClientTooltipComponent {
    private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/background");
    private final SoulwovenPouchContents contents;

    public ClientSoulwovenPouchTooltip(SoulwovenPouchContents contents) {
        this.contents = contents;
    }

    @Override
    public int getHeight() {
        return this.backgroundHeight() + 4;
    }

    @Override
    public int getWidth(Font font) {
        return this.backgroundWidth();
    }

    private int backgroundWidth() {
        return this.gridSizeX() * 18 + 2;
    }

    private int backgroundHeight() {
        return this.gridSizeY() * 20 + 2;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        int i = this.gridSizeX();
        int j = this.gridSizeY();
        guiGraphics.blitSprite(BACKGROUND_SPRITE, x, y, this.backgroundWidth(), this.backgroundHeight());
        boolean flag = this.contents.weight().compareTo(Fraction.ONE) >= 0;
        int k = 0;

        for (int l = 0; l < j; l++) {
            for (int i1 = 0; i1 < i; i1++) {
                int j1 = x + i1 * 18 + 1;
                int k1 = y + l * 20 + 1;
                this.renderSlot(j1, k1, k++, flag, guiGraphics, font);
            }
        }
    }

    private void renderSlot(int x, int y, int itemIndex, boolean isBundleFull, GuiGraphics guiGraphics, Font font) {
        if (itemIndex >= this.contents.size()) {
            this.blit(guiGraphics, x, y, isBundleFull ? ClientSoulwovenPouchTooltip.Texture.BLOCKED_SLOT : ClientSoulwovenPouchTooltip.Texture.SLOT);
        } else {
            ItemStack itemstack = this.contents.getItemUnsafe(itemIndex);
            this.blit(guiGraphics, x, y, ClientSoulwovenPouchTooltip.Texture.SLOT);
            guiGraphics.renderItem(itemstack, x + 1, y + 1, itemIndex);
            guiGraphics.renderItemDecorations(font, itemstack, x + 1, y + 1);
            if (itemIndex == 0) {
                AbstractContainerScreen.renderSlotHighlight(guiGraphics, x + 1, y + 1, 0);
            }
        }
    }

    private void blit(GuiGraphics guiGraphics, int x, int y, ClientSoulwovenPouchTooltip.Texture texture) {
        guiGraphics.blitSprite(texture.sprite, x, y, 0, texture.w, texture.h);
    }

    private int gridSizeX() {
        return Math.max(2, (int) Math.ceil(Math.sqrt((double) this.contents.size() + 1.0)));
    }

    private int gridSizeY() {
        return (int) Math.ceil(((double) this.contents.size() + 1.0) / (double) this.gridSizeX());
    }

    @OnlyIn(Dist.CLIENT)
    enum Texture {
        BLOCKED_SLOT(ResourceLocation.withDefaultNamespace("container/bundle/blocked_slot"), 18, 20),
        SLOT(ResourceLocation.withDefaultNamespace("container/bundle/slot"), 18, 20);

        public final ResourceLocation sprite;
        public final int w;
        public final int h;

        Texture(ResourceLocation sprite, int w, int h) {
            this.sprite = sprite;
            this.w = w;
            this.h = h;
        }
    }
}