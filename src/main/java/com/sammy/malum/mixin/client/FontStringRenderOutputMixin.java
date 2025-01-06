package com.sammy.malum.mixin.client;

import com.google.common.collect.*;
import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.renderer.text.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.font.glyphs.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import org.joml.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(Font.StringRenderOutput.class)
public class FontStringRenderOutputMixin {

	@Final
	@Shadow
    MultiBufferSource bufferSource;

	@Final
	@Shadow
	private Matrix4f pose;

	@Final
	@Shadow
	private int packedLightCoords;

	@Shadow @Final private boolean dropShadow;
	@Unique
	private List<BakedGlyph.Effect> malum$inverseEffects;

	@ModifyExpressionValue(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;getColor()Lnet/minecraft/network/chat/TextColor;"))
	public TextColor enableSubtractiveBlending(TextColor color, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (color != null && color.getValue() == UmbralSpiritType.INVERT_COLOR) {
			subtractiveEnabled.set(true);
			return TextColor.fromLegacyFormat(ChatFormatting.WHITE);
		}

		return color;
	}

	@WrapOperation(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;renderType(Lnet/minecraft/client/gui/Font$DisplayMode;)Lnet/minecraft/client/renderer/RenderType;"))
	public RenderType useSubtractiveRenderingType(BakedGlyph instance, Font.DisplayMode displayMode, Operation<RenderType> original, @Local(ordinal = 0) float alpha, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
//		if (subtractiveEnabled.get() && alpha >= 0.5f) {
//			return ((SubtractiveTextGlyphRenderTypes) (Object) ((AccessorBakedGlyph) instance).malum$getRenderTypes()).malum$getSubtractiveType();
//		}

		return original.call(instance, displayMode);
	}

	@WrapOperation(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;renderChar(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;ZZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFI)V"))
	public void shouldRenderCharacter(Font self, BakedGlyph glyph, boolean bold, boolean italic, float boldOffset, float x, float y, Matrix4f matrix, VertexConsumer vertexConsumer, float red, float green, float blue, float alpha, int packedLight, Operation<Void> original, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (subtractiveEnabled.get()) {
			if (dropShadow) return;

			if (alpha >= 0.5f) {
				red = green = blue = alpha * 2 - 1;
			} else {
				red = green = blue = 0;
			}
		}

		original.call(self, glyph, bold, italic, boldOffset, x, y, matrix, vertexConsumer, red, green, blue, alpha, packedLight);
	}

	@WrapWithCondition(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$StringRenderOutput;addEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;)V"))
	public boolean flagEffectAsSubtractive(Font.StringRenderOutput self, BakedGlyph.Effect effect, @Local(ordinal = 0) float alpha, @Share("subtractiveEnabled") LocalBooleanRef subtractiveEnabled) {
		if (subtractiveEnabled.get() && !dropShadow && alpha >= 0.5f) {
			if (malum$inverseEffects == null)
				malum$inverseEffects = Lists.newArrayList();
			malum$inverseEffects.add(effect);
			return false;
		}

		return true;
	}

	@Inject(method = "finish", at = @At("RETURN"))
	public void renderSubtractiveEffects(int pBackgroundColor, float pX, CallbackInfoReturnable<Float> cir) {
		if (malum$inverseEffects != null) {
//			BakedGlyph bakedglyph = ((AccessorFont) this).malum$getFontSet(Style.DEFAULT_FONT).whiteGlyph();
//			RenderType subtractiveType = ((SubtractiveTextGlyphRenderTypes) (Object) ((AccessorBakedGlyph) bakedglyph).malum$getRenderTypes()).malum$getSubtractiveType();
//			VertexConsumer vertexconsumer = bufferSource.getBuffer(subtractiveType);
//
//			for(BakedGlyph.Effect bakedglyph$effect : malum$inverseEffects) {
//				bakedglyph.renderEffect(bakedglyph$effect, pose, vertexconsumer, packedLightCoords);
//			}
		}
	}

}
