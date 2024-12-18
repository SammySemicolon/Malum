package com.sammy.malum.core.systems.ritual;


import com.sammy.malum.forge_stuff.SizedIngredient;

import java.util.*;

public class MalumRitualRecipeData {

    public final MalumRitualType ritualType;
    public final SizedIngredient input;
    public final List<SizedIngredient> extraItems;

    public MalumRitualRecipeData(MalumRitualType ritualType, SizedIngredient input, List<SizedIngredient> extraItems) {
        this.ritualType = ritualType;
        this.input = input;
        this.extraItems = extraItems;
    }
}