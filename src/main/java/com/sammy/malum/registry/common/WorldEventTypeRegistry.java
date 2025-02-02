package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.worldevent.*;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class WorldEventTypeRegistry {

    public static DeferredRegister<WorldEventType> WORLD_EVENT_TYPES = DeferredRegister.create(LodestoneWorldEventTypes.WORLD_EVENT_TYPE_REGISTRY, MalumMod.MALUM);

    public static DeferredHolder<WorldEventType, WorldEventType> UNCHAINED_TOTEM_CONVERSION = WORLD_EVENT_TYPES.register("unchained_totem_conversion", r -> new WorldEventType(r, UnchainedTotemConversionWorldEvent::new, false));
    public static DeferredHolder<WorldEventType, WorldEventType> SUSPICIOUS_DEVICE_TRIGGER = WORLD_EVENT_TYPES.register("suspicious_device_trigger", r -> new WorldEventType(r, SuspiciousDeviceTriggerWorldEvent::new, false));

    public static DeferredHolder<WorldEventType, WorldEventType> DELAYED_DAMAGE = WORLD_EVENT_TYPES.register("delayed_damage", r -> new WorldEventType(r, DelayedDamageWorldEvent::new, false));

}