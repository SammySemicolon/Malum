package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

import com.sammy.malum.common.block.curiosities.spirit_crucible.*;

import static com.sammy.malum.MalumMod.malumPath;

public class CatalyzerAcceleratorType extends ICrucibleAccelerator.CrucibleAcceleratorType {

    public static final CatalyzerAcceleratorType CATALYZER = new CatalyzerAcceleratorType();

    public CatalyzerAcceleratorType() {
        super(8, malumPath("catalyzer"));
    }

    @Override
    public float getExtraDamageRollChance(int count) {
        return count * 0.1f;
    }

    @Override
    public float getAcceleration(int count) {
        return 0.5f * count;
    }
}