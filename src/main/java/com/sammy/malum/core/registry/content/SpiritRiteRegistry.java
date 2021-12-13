package com.sammy.malum.core.registry.content;

import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;

import java.util.ArrayList;

public class SpiritRiteRegistry
{
    public static ArrayList<MalumRiteType> RITES = new ArrayList<>();

    public static MalumRiteType SACRED_RITE = create(new SacredRiteType());
    public static MalumRiteType ELDRITCH_SACRED_RITE = create(new EldritchSacredRiteType());
    public static MalumRiteType WICKED_RITE = create(new WickedRiteType());
    public static MalumRiteType ELDRITCH_WICKED_RITE = create(new EldritchWickedRiteType());

    public static MalumRiteType EARTHEN_RITE = create(new EarthenRiteType());
    public static MalumRiteType AERIAL_RITE = create(new AerialRiteType());

    public static MalumRiteType ARCANE_RITE = create(new ArcaneRiteType());


    public static MalumRiteType create(MalumRiteType type)
    {
        RITES.add(type);
        return type;
    }

    public static MalumRiteType getRite(String identifier) {
        for (MalumRiteType rite : RITES) {
            if (rite.identifier.equals(identifier)) {
                return rite;
            }
        }
        return null;
    }
    public static MalumRiteType getRite(ArrayList<MalumSpiritType> spirits)
    {
        for (MalumRiteType rite : RITES)
        {
            if (rite.spirits.equals(spirits))
            {
                return rite;
            }
        }
        return null;
    }
}
