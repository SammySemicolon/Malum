package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.*;

public class MalignantInfluenceData {

    public static Codec<MalignantInfluenceData> CODEC = Codec.unit(MalignantInfluenceData::new);

    public final HashMap<Holder<Attribute>, Double> cachedAttributeValues = new HashMap<>();
    public boolean skipConversionLogic;

    public MalignantInfluenceData() {
    }
}
