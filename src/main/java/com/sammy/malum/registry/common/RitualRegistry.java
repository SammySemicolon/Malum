package com.sammy.malum.registry.common;

import com.sammy.malum.common.spiritritual.*;
import com.sammy.malum.core.systems.ritual.*;
import net.minecraft.resources.*;

import java.util.*;

public class RitualRegistry {

    //TODO: convert to deferred register
    public static List<MalumRitualType> RITUALS = new ArrayList<>();

    public static MalumRitualType RITUAL_OF_IDLE_MENDING = create(new IdleMendingRitualType());
    public static MalumRitualType RITUAL_OF_GROTESQUE_EXPULSION = create(new GrotesqueExpulsionRitualType());
    public static MalumRitualType RITUAL_OF_MANABOUND_ENHANCEMENT = create(new ManaboundEnhancementRitualType());
    public static MalumRitualType RITUAL_OF_HEXING_TRANSMISSION = create(new HexingTransmissionRitualType());
    public static MalumRitualType RITUAL_OF_WARPED_TIME = create(new WarpedTimeRitualType());
    public static MalumRitualType RITUAL_OF_MARINE_SPOIL = create(new MarineSpoilRitualType());
    public static MalumRitualType RITUAL_OF_CTHONIC_EXCHANGE = create(new CthonicExchangeRitualType());
    public static MalumRitualType RITUAL_OF_TERRAN_UNEARTHING = create(new TerranUnearthingRitualType());

    public static MalumRitualType create(MalumRitualType type) {
        RITUALS.add(type);
        return type;
    }

    public static MalumRitualType get(ResourceLocation resourceLocation) {
        return RITUALS.stream().filter(r -> r.id.equals(resourceLocation)).findFirst().orElse(null);
    }
}
