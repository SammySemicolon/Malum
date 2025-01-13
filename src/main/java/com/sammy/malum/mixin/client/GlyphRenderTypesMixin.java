package com.sammy.malum.mixin.client;

import com.sammy.malum.client.renderer.text.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.gui.font.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

@Mixin(GlyphRenderTypes.class)
public class GlyphRenderTypesMixin implements SubtractiveTextGlyphRenderTypes {

	@Unique
	private RenderType malum$subtractive;

	@Inject(method = "createForIntensityTexture", at = @At("RETURN"))
	private static void setSubtractiveLayerForIntensity(ResourceLocation pId, CallbackInfoReturnable<GlyphRenderTypes> cir) {
		((GlyphRenderTypesMixin) (Object) cir.getReturnValue()).malum$subtractive = RenderTypeRegistry.SUBTRACTIVE_INTENSE_TEXT.apply(RenderTypeToken.createToken(pId));
	}

	@Inject(method = "createForColorTexture", at = @At("RETURN"))
	private static void setSubtractiveLayerForColor(ResourceLocation pId, CallbackInfoReturnable<GlyphRenderTypes> cir) {
		((GlyphRenderTypesMixin) (Object) cir.getReturnValue()).malum$subtractive = RenderTypeRegistry.SUBTRACTIVE_TEXT.apply(RenderTypeToken.createToken(pId));
	}

	@Override
	public RenderType malum$getSubtractiveType() {
		return malum$subtractive;
	}
}
