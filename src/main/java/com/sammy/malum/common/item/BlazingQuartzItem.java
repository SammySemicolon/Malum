package com.sammy.malum.common.item;

import net.minecraft.world.level.block.*;
import team.lodestar.lodestone.systems.item.LodestoneFuelBlockItem;


public class BlazingQuartzItem extends LodestoneFuelBlockItem {
    public final int fuel;

    public BlazingQuartzItem(Block pBlock, int fuel, Properties pProperties) {
        super(pBlock, pProperties, fuel);
        this.fuel = fuel;
    }
}
