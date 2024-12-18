package com.sammy.malum.mixin.client;

import com.sammy.malum.common.item.curiosities.curios.sets.weeping.CurioHiddenBladeNecklace;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import com.sammy.malum.core.handlers.client.HiddenBladeRenderHandler;
import com.sammy.malum.core.handlers.client.SoulWardRenderHandler;
import com.sammy.malum.core.handlers.client.TouchOfDarknessRenderHandler;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
final class GuiMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"))
    private void malum$renderArmorOverlay(GuiGraphics guiGraphics, CallbackInfo ci) {
        SoulWardRenderHandler.renderSoulWard(guiGraphics, this.minecraft.getTimer());
    }

    @Inject(method = "renderSleepOverlay", at = @At(value = "HEAD"))
    private void malum$renderDarknessOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        TouchOfDarknessRenderHandler.renderDarknessVignette(guiGraphics, deltaTracker);
    }

    @Inject(method = "renderCrosshair", at = @At(value = "HEAD"))
    private void malum$renderHiddenBladeOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        HiddenBladeRenderHandler.renderHiddenBladeCooldown(guiGraphics, deltaTracker);
    }
}