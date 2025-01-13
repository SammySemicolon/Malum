package com.sammy.malum.client.renderer.entity.bolt;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import static com.sammy.malum.MalumMod.malumPath;

public class DrainingBoltEntityRenderer extends AbstractBoltEntityRenderer<DrainingBoltEntity> {

    public DrainingBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, ErosionScepterItem.MALIGNANT_PURPLE, ErosionScepterItem.MALIGNANT_BLACK);
    }

    @Override
    public RenderType getTrailRenderType(boolean isTransparent) {
        return LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL, ShaderUniformHandler.LUMITRANSPARENT);
    }

    @Override
    public float getAlphaMultiplier() {
        return 1.8f;
    }

    @Override
    public float getScaleMultiplier() {
        return 1.6f;
    }
}
