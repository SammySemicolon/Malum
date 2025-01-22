package com.sammy.malum.core.systems.etching;

import com.mojang.serialization.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;

import java.util.function.*;

public class GeasEffectType {
    public static final Codec<GeasEffectType> CODEC = ResourceLocation.CODEC.comapFlatMap(s -> {
        final GeasEffectType geasEffectType = MalumGeasEffectTypeRegistry.GEAS_TYPES_REGISTRY.get(s);
        return DataResult.success(geasEffectType);
    }, GeasEffectType::getId);

    public final Supplier<GeasEffect> effect;

    public GeasEffectType(Supplier<GeasEffect> effect) {
        this.effect = effect;
    }

    public MutableComponent getDescription() {
        return Component.translatable(getId().getNamespace() + ".gui.geas." + getId().getPath() + ".tooltip");
    }

    public String getLangKey() {
        return getId().getNamespace() + ".gui.geas." + getId().getPath();
    }

    public ResourceLocation getId() {
        return MalumGeasEffectTypeRegistry.GEAS_TYPES_REGISTRY.getKey(this);
    }

    public GeasEffect createEffect() {
        return effect.get();
    }
}

