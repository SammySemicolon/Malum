package com.sammy.malum.client.screen.codex.screens;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.handlers.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static org.lwjgl.opengl.GL11C.*;

public abstract class AbstractProgressionCodexScreen extends AbstractMalumScreen {

    public static final ResourceLocation FRAME_TEXTURE = malumPath("textures/gui/book/frame.png");
    public static final ResourceLocation FRAME_FADE_TEXTURE = malumPath("textures/gui/book/frame_fade.png");

    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;
    public int transitionTimer;
    public int timesTransitioned;
    public boolean isVoidTouched;

    public final EntryObjectHandler bookObjectHandler = new EntryObjectHandler();

    public final int bookWidth;
    public final int bookHeight;
    public final int bookInsideWidth;
    public final int bookInsideHeight;

    public final int backgroundImageWidth;
    public final int backgroundImageHeight;

    protected AbstractProgressionCodexScreen(Supplier<SoundEvent> sweetenerSound, int backgroundImageWidth, int backgroundImageHeight) {
        this(sweetenerSound, 378, 250, 344, 218, backgroundImageWidth, backgroundImageHeight);
    }

    protected AbstractProgressionCodexScreen(Supplier<SoundEvent> sweetenerSound, int bookWidth, int bookHeight, int bookInsideWidth, int bookInsideHeight, int backgroundImageWidth, int backgroundImageHeight) {
        super(Component.empty(), sweetenerSound);
        this.bookWidth = bookWidth;
        this.bookHeight = bookHeight;
        this.bookInsideWidth = bookInsideWidth;
        this.bookInsideHeight = bookInsideHeight;
        this.backgroundImageWidth = backgroundImageWidth;
        this.backgroundImageHeight = backgroundImageHeight;
        minecraft = Minecraft.getInstance();
    }

    public abstract void renderBackground(PoseStack poseStack);

    public abstract Collection<PlacedBookEntry> getEntries();

    public void addEntry(String identifier, int xOffset, int yOffset) {
        addEntry(identifier, xOffset, yOffset, b -> {
        });
    }

    public void addEntry(String identifier, int xOffset, int yOffset, Consumer<PlacedBookEntryBuilder> consumer) {
        final PlacedBookEntryBuilder builder = PlacedBookEntry.build(identifier, xOffset, yOffset);
        consumer.accept(builder);
        if (builder.hasFragment())
            getEntries().add(builder.buildFragment());
        getEntries().add(builder.build());
    }

    public PlacedBookEntry getEntry(String identifier) {
        return getEntries().stream().filter(p -> p.identifier.matches(identifier)).findFirst().orElseThrow(()-> new NullPointerException("Encyclopedia Arcana tried initiating a reference to the " + identifier + " entry, which doesn't exist"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics.pose());
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        PoseStack poseStack = guiGraphics.pose();

        renderBackground(poseStack);
        GL11.glEnable(GL_SCISSOR_TEST);
        cut();

        bookObjectHandler.renderObjects(this, guiGraphics, guiLeft + xOffset, guiTop + yOffset, mouseX, mouseY, partialTicks);
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTexture(FRAME_FADE_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight);
        if (transitionTimer > 0) {
            ArcanaCodexHelper.renderTransitionFade(this, poseStack);
        }
        renderTexture(FRAME_TEXTURE, poseStack, guiLeft, guiTop, 400, 0, 0, bookWidth, bookHeight);
        bookObjectHandler.renderObjectsLate(this, guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (ignoreNextMouseInput) {
            ignoreNextMouseInput = false;
            return super.mouseReleased(mouseX, mouseY, button);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset) {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        bookObjectHandler.click(this, mouseX, mouseY);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        xOffset += dragX;
        yOffset += dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        super.onClose();
        playSweetenedSound(SoundRegistry.ARCANA_CODEX_CLOSE, 0.75f);
    }

    @Override
    public void tick() {
        if (transitionTimer > 0) {
            transitionTimer--;
        }
        super.tick();
    }

    @Override
    public boolean isHovering(double mouseX, double mouseY, float posX, float posY, int width, int height) {
        if (!isInView(mouseX, mouseY)) {
            return false;
        }
        return super.isHovering(mouseX, mouseY, posX, posY, width, height);
    }

    public void setupObjects() {
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        bookObjectHandler.setupEntryObjects(this);
    }

    public void faceObject(BookObject<?> object) {
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        xOffset = -object.posX + bookInsideWidth / 2f;
        yOffset = -object.posY + bookInsideHeight / 2f;
    }

    public void openScreen(boolean silentMouseInput) {
        Minecraft.getInstance().setScreen(this);
        this.ignoreNextMouseInput = silentMouseInput;
    }

    public void renderBackground(PoseStack poseStack, ResourceLocation texture, float xModifier, float yModifier) {
        int insideLeft = getInsideLeft();
        int insideTop = getInsideTop();
        float uOffset = (bookInsideWidth / 8f - xOffset * xModifier);
        float vOffset = (backgroundImageHeight - bookInsideHeight - yOffset * yModifier);
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > bookInsideWidth / 2f) {
            uOffset = bookInsideWidth / 2f;
        }
        if (vOffset <= backgroundImageHeight / 2f) {
            vOffset = backgroundImageHeight / 2f;
        }
        if (vOffset > backgroundImageHeight - bookInsideHeight) {
            vOffset = backgroundImageHeight - bookInsideHeight;
        }
        renderTexture(texture, poseStack, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, backgroundImageWidth / 2, backgroundImageHeight / 2);
    }

    public boolean isInView(double mouseX, double mouseY) {
        return mouseX >= getInsideLeft()
                && mouseY >= getInsideTop()
                && mouseX <= (getInsideLeft() + bookInsideWidth)
                && mouseY <= (getInsideTop() + bookInsideHeight);
    }

    public void cut() {
        int scale = (int) getMinecraft().getWindow().getGuiScale();
        GL11.glScissor(
                getInsideLeft() * scale,
                getMinecraft().getWindow().getHeight() - (getInsideTop() + bookInsideHeight) * scale,
                bookInsideWidth * scale,
                bookInsideHeight * scale);
    }

    public int getInsideLeft() {
        return getGuiLeft() + 17;
    }

    public int getInsideTop() {
        return getGuiTop() + 14;
    }

    public int getGuiLeft() {
        return (width - bookWidth) / 2;
    }

    public int getGuiTop() {
        return (height - bookHeight) / 2;
    }

    public int getTransitionDuration() {
        return 80 - Mth.clamp(timesTransitioned - 2, 0, 4) * 10;
    }
}
