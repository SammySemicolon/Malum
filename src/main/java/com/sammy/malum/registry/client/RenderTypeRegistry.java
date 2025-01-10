package com.sammy.malum.registry.client;

import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.*;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypes.createGenericRenderType;

public class RenderTypeRegistry extends RenderStateShard {

    //TODO: move this to lodestone

    private static final RenderStateShard.TransparencyStateShard SUBTRACTIVE_TEXT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("malum:subtractive_text_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        RenderSystem.blendEquation(GL14.GL_FUNC_SUBTRACT);
    }, () -> {
        RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderTypeProvider WEEPING_WELL_DISTORTED_TEXTURE = new RenderTypeProvider((token) ->
            createGenericRenderType("weeping_well_distorted_texture",
                    LodestoneRenderTypes.builder(token, StateShards.ADDITIVE_TRANSPARENCY, ShaderRegistry.WEEPING_WELL_DISTORTION, CULL, LIGHTMAP, COLOR_WRITE)));

    public static final RenderTypeProvider SUBTRACTIVE_TEXT = new RenderTypeProvider((token) ->
            createGenericRenderType("subtractive_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypes.builder()
            .setShaderState(RENDERTYPE_TEXT_SEE_THROUGH_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .setTextureState(token.get())));

    public static final RenderTypeProvider SUBTRACTIVE_INTENSE_TEXT = new RenderTypeProvider((token) ->
        createGenericRenderType("subtractive_intense_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypes.builder()
            .setShaderState(RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .setTextureState(token.get())));

    public static final RenderTypeProvider MALIGNANT_GLOW = new RenderTypeProvider((token) -> createGenericRenderType("malignant_glow", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypes.builder()
            .setShaderState(LodestoneShaders.LODESTONE_TEXTURE)
            .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
            .setTextureState(token.get())
            .setCullState(RenderStateShard.NO_CULL)));

    public RenderTypeRegistry(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }
}
