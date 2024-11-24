package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class GrowingFleshEffect extends MobEffect {
    public GrowingFleshEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(126, 25, 95)));
        addAttributeModifier(Attributes.MAX_HEALTH, MalumMod.malumPath("cancer"), 0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}