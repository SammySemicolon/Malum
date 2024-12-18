package com.sammy.malum.client.screen.container;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.*;

import java.lang.Math;

public class WeaversWorkbenchContainerScreen extends AbstractContainerScreen<WeaversWorkbenchContainer> {

    public static final Vector3f TRANSLATION = new Vector3f();
    public static final Quaternionf ANGLE = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, (float) Math.PI);
    private static final ResourceLocation TEXTURE = MalumMod.malumPath("textures/gui/weavers_workbench.png");

    private final WeaversWorkbenchContainer weaversWorkbenchContainer;

    private ItemStack cachedOutput;
    private ArmorStand armorStand;

    public WeaversWorkbenchContainerScreen(WeaversWorkbenchContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.weaversWorkbenchContainer = pMenu;
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
        setupArmorStand();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        //RenderSystem.setShaderTexture(0, TEXTURE);
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        ItemStack output = weaversWorkbenchContainer.itemHandler.getStackInSlot(2);

        if (!output.equals(cachedOutput)) {
            setupArmorStand();
        }
        cachedOutput = output;
        InventoryScreen.renderEntityInInventory(pGuiGraphics, this.leftPos + 141, this.topPos + 75, 25.0F, TRANSLATION, ANGLE, null, this.armorStand);
    }

    protected void setupArmorStand() {
        this.armorStand = new ArmorStand(Minecraft.getInstance().level, 0.0, 0.0, 0.0);
        this.armorStand.setNoBasePlate(true);
        this.armorStand.setShowArms(true);
        this.armorStand.yBodyRot = 210.0F;
        this.armorStand.setXRot(25.0F);
        this.armorStand.yHeadRot = this.armorStand.yBodyRot;
        this.armorStand.yHeadRotO = this.armorStand.yBodyRot;
        this.dressUpStand(weaversWorkbenchContainer.itemHandler.getStackInSlot(2));
    }

    private void dressUpStand(ItemStack stack) {
        if (this.armorStand != null) {
            EquipmentSlot[] var2 = EquipmentSlot.values();

            for (EquipmentSlot equipmentSlot : var2) {
                this.armorStand.setItemSlot(equipmentSlot, ItemStack.EMPTY);
            }

            if (!stack.isEmpty()) {
                ItemStack itemStack = stack.copy();
                Item var8 = stack.getItem();
                if (var8 instanceof ArmorItem armorItem) {
                    this.armorStand.setItemSlot(armorItem.getEquipmentSlot(), itemStack);
                }
            }
        }
    }
}

