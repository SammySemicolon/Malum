package com.sammy.malum.registry.client;

import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.*;
import team.lodestar.lodestone.events.LodestoneShaderRegistrationEvent;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

import static team.lodestar.lodestone.registry.client.LodestoneShaders.getConsumer;

public class ShaderRegistry {
    public static ShaderHolder TOUCH_OF_DARKNESS = new ShaderHolder(MalumMod.malumPath("touch_of_darkness"), DefaultVertexFormat.POSITION_COLOR, "Speed", "Zoom", "Distortion", "Intensity", "Wibble");

    public static void shaderRegistry() {
        LodestoneShaderRegistrationEvent.EVENT.register((provider, shaderList1) -> {
            shaderList1.add(Pair.of(TOUCH_OF_DARKNESS.createInstance(provider), getConsumer()));
        });
    }
}