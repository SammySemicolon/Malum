package com.sammy.malum.registry.common;

import com.sammy.malum.common.spiritrite.arcane.*;
import com.sammy.malum.common.spiritrite.eldritch.*;
import com.sammy.malum.core.systems.rite.TotemicRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;

import java.util.ArrayList;
import java.util.List;

public class SpiritRiteRegistry {
    public static List<TotemicRiteType> RITES = new ArrayList<>();

    public static TotemicRiteType SACRED_RITE = create(new SacredRiteType());
    public static TotemicRiteType ELDRITCH_SACRED_RITE = create(new EldritchSacredRiteType());
    public static TotemicRiteType WICKED_RITE = create(new WickedRiteType());
    public static TotemicRiteType ELDRITCH_WICKED_RITE = create(new EldritchWickedRiteType());

    public static TotemicRiteType EARTHEN_RITE = create(new EarthenRiteType());
    public static TotemicRiteType ELDRITCH_EARTHEN_RITE = create(new EldritchEarthenRiteType());
    public static TotemicRiteType INFERNAL_RITE = create(new InfernalRiteType());
    public static TotemicRiteType ELDRITCH_INFERNAL_RITE = create(new EldritchInfernalRiteType());
    public static TotemicRiteType AERIAL_RITE = create(new AerialRiteType());
    public static TotemicRiteType ELDRITCH_AERIAL_RITE = create(new EldritchAerialRiteType());
    public static TotemicRiteType AQUEOUS_RITE = create(new AqueousRiteType());
    public static TotemicRiteType ELDRITCH_AQUEOUS_RITE = create(new EldritchAqueousRiteType());

    public static TotemicRiteType ARCANE_RITE = create(new ArcaneRiteType());

    public static TotemicRiteType create(TotemicRiteType type) {
        RITES.add(type);
        return type;
    }

    public static TotemicRiteType getRite(String identifier) {
        for (TotemicRiteType rite : RITES) {
            if (rite.identifier.equals(identifier)) {
                return rite;
            }
        }
        return null;
    }

    public static TotemicRiteType getRite(List<MalumSpiritType> spirits) {
        for (TotemicRiteType rite : RITES) {
            if (rite.spirits.equals(spirits)) {
                return rite;
            }
        }
        return null;
    }
}
