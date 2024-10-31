package com.sammy.malum.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import team.lodestar.lodestone.helpers.ColorHelper;

public class WickedIntentEffect extends MobEffect {
    public WickedIntentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
    }


}