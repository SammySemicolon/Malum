package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.worldevent.ActiveBlightEvent;
import com.sammy.malum.common.worldevent.UnchainedTotemConversionEvent;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class WorldEventTypeRegistry {

    public static DeferredRegister<WorldEventType> WORLD_EVENT_TYPES = DeferredRegister.create(LodestoneWorldEventTypes.WORLD_EVENT_TYPE_REGISTRY, MalumMod.MALUM);

    public static DeferredHolder<WorldEventType, WorldEventType> ACTIVE_BLIGHT = WORLD_EVENT_TYPES.register("active_blight", r -> new WorldEventType(r, ActiveBlightEvent::new, false));
    public static DeferredHolder<WorldEventType, WorldEventType> UNCHAINED_TOTEM_CONVERSION = WORLD_EVENT_TYPES.register("unchained_totem_conversion", r -> new WorldEventType(r, UnchainedTotemConversionEvent::new, false));

}