package com.sammy.malum.core.systems.etching;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.resources.*;

import java.util.function.*;

public class EtchingEffectType {
    public static final Codec<EtchingEffectType> CODEC = ResourceLocation.CODEC.comapFlatMap(s -> {
        final EtchingEffectType etchingEffectType = MalumEtchingEffectTypeRegistry.ETCHING_TYPE_REGISTRY.get(s);
        return DataResult.success(etchingEffectType);
    }, EtchingEffectType::getId);

    public final Supplier<EtchingEffect> effect;

    public EtchingEffectType(Supplier<EtchingEffect> effect) {
        this.effect = effect;
    }

    public ResourceLocation getId() {
        return MalumEtchingEffectTypeRegistry.ETCHING_TYPE_REGISTRY.getKey(this);
    }

    public EtchingEffect createEffect() {
        return effect.get();
    }

    public Supplier<EtchingEffect> getEffect() {
        return effect;
    }
}