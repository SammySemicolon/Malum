package com.sammy.malum.registry.common.block;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.level.block.state.properties.*;

public class WoodTypeRegistry {

    public static final WoodType RUNEWOOD = WoodType.register(new WoodType("malum:runewood", MalumBlockSetTypes.RUNEWOOD,
            SoundRegistry.RUNEWOOD, SoundRegistry.RUNEWOOD_HANGING_SIGN,
            SoundRegistry.RUNEWOOD_FENCE_GATE_CLOSE.get(), SoundRegistry.RUNEWOOD_FENCE_GATE_OPEN.get()));
    public static final WoodType SOULWOOD = WoodType.register(new WoodType("malum:soulwood", MalumBlockSetTypes.SOULWOOD,
            SoundRegistry.SOULWOOD, SoundRegistry.SOULWOOD_HANGING_SIGN,
            SoundRegistry.SOULWOOD_FENCE_GATE_CLOSE.get(), SoundRegistry.SOULWOOD_FENCE_GATE_OPEN.get()));

}