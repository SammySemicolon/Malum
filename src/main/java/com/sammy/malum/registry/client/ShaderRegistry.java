package com.sammy.malum.registry.client;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

@EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MALUM, bus = EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {
    public static ShaderHolder TOUCH_OF_DARKNESS = new ShaderHolder(MalumMod.malumPath("touch_of_darkness"), DefaultVertexFormat.POSITION_TEX_COLOR, "Speed", "Zoom", "Distortion", "Intensity", "Wibble");
    public static ShaderHolder WEEPING_WELL_DISTORTION = new ShaderHolder(MalumMod.malumPath("weeping_distortion"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, "Speed", "Distortion", "Width", "Height", "UVCoordinates");

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) {
        LodestoneShaders.registerShader(event, TOUCH_OF_DARKNESS);
        LodestoneShaders.registerShader(event, WEEPING_WELL_DISTORTION);
    }
}