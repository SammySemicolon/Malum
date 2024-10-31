package com.sammy.malum.client.screen.codex.screens;

import com.google.common.collect.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.handlers.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.objects.progression.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

//first generic represents the screen itself, the second represents the screen that it was opened from
public class EntryScreen extends AbstractMalumScreen {

    public static EntryScreen entryScreen;

    public static final ResourceLocation BOOK_TEXTURE = MalumMod.malumPath("textures/gui/book/entry.png");
    public static final ResourceLocation ELEMENT_SOCKET = MalumMod.malumPath("textures/gui/book/entry_elements/element_socket.png");

    public final int bookWidth = 312;
    public final int bookHeight = 200;
    public final BookEntry openEntry;
    protected final Consumer<Boolean> onClose;

    public final BookObjectHandler<EntryScreen> bookObjectHandler = new BookObjectHandler<>();

    public List<Runnable> lateRendering = new ArrayList<>();
    public int grouping;

    public EntryScreen(BookEntry openEntry, Consumer<Boolean> onClose) {
        super(Component.empty(), openEntry.isVoid ? SoundRegistry.ARCANA_SWEETENER_EVIL : SoundRegistry.ARCANA_SWEETENER_NORMAL);
        this.openEntry = openEntry;
        this.onClose = onClose;
        final int left = -21;
        final int right = bookWidth - 15;
        bookObjectHandler.add(new ArrowObject(left, 150, false));
        bookObjectHandler.add(new ArrowObject(right, 150, true));

        final ImmutableList<EntryReference> references = openEntry.references;
        if (references != null) {
            int counter = 0;
            for (int i = 0; i < references.size(); i++) {
                final EntryReference entryReference = references.get(i);
                if (entryReference.entry.shouldShow()) {
                    bookObjectHandler.add(new LinkedEntryObject(right, 15 + counter * 30, true, entryReference));
                    counter++;
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        PoseStack poseStack = guiGraphics.pose();
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        renderTexture(BOOK_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight);

        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    final boolean isRightSide = i % 2 == 1;
                    int pageLeft = guiLeft + (isRightSide ? 161 : 9);
                    int pageTop = guiTop + 8;
                    final ResourceLocation background = page.getBackground(isRightSide);
                    if (background != null) {
                        renderTexture(background, poseStack, pageLeft, pageTop, 0, 0, 142, 172);
                    }
                }
            }
        }
        bookObjectHandler.renderObjects(this, guiGraphics, guiLeft, guiTop, mouseX, mouseY, partialTicks);
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    final boolean isRightSide = i % 2 == 1;
                    int pageLeft = guiLeft + (isRightSide ? 161 : 9);
                    int pageTop = guiTop + 8;
                    boolean isRepeat = i % 2 != 0 && page.getClass().equals(openEntry.pages.get(i - 1).getClass());
                    page.render(this, guiGraphics, pageLeft, pageTop, mouseX, mouseY, partialTicks, isRepeat);
                    lateRendering.add(() -> page.renderLate(this, guiGraphics, pageLeft, pageTop, mouseX, mouseY, partialTicks, isRepeat));
                }
            }
            lateRendering.forEach(Runnable::run);
            lateRendering.clear();
        }
        bookObjectHandler.renderObjectsLate(this, guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        bookObjectHandler.click(this, mouseX, mouseY);

        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();

        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    final boolean isRightSide = i % 2 == 1;
                    int pageLeft = guiLeft + (isRightSide ? 161 : 9);
                    int pageTop = guiTop + 8;
                    if (isHovering(mouseX, mouseY, pageLeft, pageTop, 142, 172)) {
                        double relativeX = Mth.clamp(mouseX - guiLeft, guiLeft, guiLeft + 142);
                        double relativeY = Mth.clamp(mouseY - guiTop, guiTop, guiTop + 172);
                        page.click(this, pageLeft, pageTop, mouseX, mouseY, relativeX, relativeY);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (ClientConfig.SCROLL_DIRECTION.getConfigValue()) {
            scrollY = -scrollY;
        }
        if (scrollY > 0) {
            nextPage();
        } else {
            previousPage(false);
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (minecraft.options.keyRight.matches(keyCode, scanCode)) {
            nextPage();
            return true;
        } else if (minecraft.options.keyLeft.matches(keyCode, scanCode)) {
            previousPage(false);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        close(false);
    }

    public boolean hasNextPage() {
        return grouping < openEntry.pages.size() / 2f - 1;
    }

    public void nextPage() {
        if (hasNextPage()) {
            grouping += 1;
            playPageFlipSound(SoundRegistry.ARCANA_PAGE_FLIP, getSweetenerPitch());
        }
    }

    public void previousPage(boolean ignore) {
        if (grouping > 0) {
            grouping -= 1;
            playPageFlipSound(SoundRegistry.ARCANA_PAGE_FLIP, getSweetenerPitch());
        } else {
            close(ignore);
        }
    }

    public void close(boolean ignoreNextInput) {
        onClose.accept(ignoreNextInput);
        playSweetenedSound(SoundRegistry.ARCANA_ENTRY_CLOSE, 0.85f);
    }

    public static<K extends AbstractProgressionCodexScreen> void openScreen(K screen, ProgressionEntryObject progressionEntryObject) {
        openScreen(progressionEntryObject.entry, b -> {
            screen.openScreen(b);
            progressionEntryObject.exit(screen);
        });
    }

    public static void openScreen(AbstractMalumScreen screen, BookEntry entry) {
        openScreen(entry, screen::openScreen);
    }

    public static void openScreen(BookEntry bookEntry, Consumer<Boolean> onClose) {
        entryScreen = new EntryScreen(bookEntry, onClose);
        entryScreen.playSweetenedSound(SoundRegistry.ARCANA_ENTRY_OPEN, 1.15f);
        Minecraft.getInstance().setScreen(entryScreen);
    }

    public float getSweetenerPitch() {
        return 1 + (float) grouping / openEntry.pages.size();
    }

    public int getGuiLeft() {
        return (width - bookWidth) / 2;
    }

    public int getGuiTop() {
        return (height - bookHeight) / 2;
    }

    public void renderLate(Runnable runnable) {
        lateRendering.add(runnable);
    }
}
