package com.sammy.malum.common.item.cosmetic.curios;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import team.lodestar.lodestone.handlers.*;

public class CurioTopHat extends MalumCurioItem implements ItemEventHandler.IEventResponderItem {

    public CurioTopHat(Properties builder) {
        super(builder, MalumTrinketType.CLOTH);
    }
}