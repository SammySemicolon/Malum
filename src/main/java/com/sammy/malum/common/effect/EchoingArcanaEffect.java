package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class EchoingArcanaEffect extends MobEffect {
    public EchoingArcanaEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(255, 79, 234)));
        addAttributeModifier(AttributeRegistry.ARCANE_RESONANCE, MalumMod.malumPath("echoing_arcana"), 0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}