package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.worldevent.ActiveBlightEvent;
import com.sammy.malum.common.worldevent.TotemCreatedBlightEvent;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class WorldEventTypes {

    public static DeferredRegister<WorldEventType> TYPES = DeferredRegister.create(LodestoneWorldEventTypes.WORLD_EVENT_TYPE_REGISTRY, MalumMod.MALUM);

    public static DeferredHolder<WorldEventType, WorldEventType> ACTIVE_BLIGHT = TYPES.register("active_blight", r -> new WorldEventType(r, ActiveBlightEvent::new, false));
    public static DeferredHolder<WorldEventType, WorldEventType> TOTEM_CREATED_BLIGHT = TYPES.register("totem_blight", r -> new WorldEventType(r, TotemCreatedBlightEvent::new, false));

}