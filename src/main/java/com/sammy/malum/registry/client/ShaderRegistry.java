package com.sammy.malum.registry.client;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import net.neoforged.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {
    public static ShaderHolder TOUCH_OF_DARKNESS = new ShaderHolder(MalumMod.malumPath("touch_of_darkness"), DefaultVertexFormat.POSITION_COLOR_TEX, "Speed", "Zoom", "Distortion", "Intensity", "Wibble");

    public static ShaderHolder DISTORTION = new ShaderHolder(MalumMod.malumPath("distortion"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, "Speed", "Distortion", "Width", "Height", "UVEncasement");

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) {
        LodestoneShaderRegistry.registerShader(event, TOUCH_OF_DARKNESS);
        LodestoneShaderRegistry.registerShader(event, DISTORTION);
    }
}
