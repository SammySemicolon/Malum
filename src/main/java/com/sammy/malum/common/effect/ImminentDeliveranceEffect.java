package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class ImminentDeliveranceEffect extends MobEffect {
    public ImminentDeliveranceEffect() {
        super(MobEffectCategory.HARMFUL, ColorHelper.getColor(235, 207, 249));
    }
}