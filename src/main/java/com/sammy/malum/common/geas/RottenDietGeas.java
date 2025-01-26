package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.registry.common.*;

public class RottenDietGeas extends GeasEffect {

    public RottenDietGeas() {
        super(MalumGeasEffectTypeRegistry.ROTTEN_DIET.get());
    }
}