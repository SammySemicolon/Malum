package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.*;

public class FallDamageGeas extends GeasEffect {

    public FallDamageGeas() {
        super(MalumGeasEffectTypeRegistry.FALL_DAMAGE_GEAS.get());
    }
}