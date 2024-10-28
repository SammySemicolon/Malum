package com.sammy.malum.client.renderer.entity.nitrate;

import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import java.awt.*;
import java.util.function.*;

public class VividNitrateEntityRenderer extends AbstractNitrateEntityRenderer<VividNitrateEntity> {

    public static final Function<Float, Color> COLOR_FUNCTION = f -> VividNitrateEntity.COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(Minecraft.getInstance().level, f));

    public VividNitrateEntityRenderer(EntityRendererProvider.Context context) {
        super(context, COLOR_FUNCTION, COLOR_FUNCTION);
    }
}
