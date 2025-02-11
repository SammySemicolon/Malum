package com.sammy.malum.core.systems.geas;

import com.mojang.serialization.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

import java.util.function.*;

public class GeasEffectType {
    public static final Codec<GeasEffectType> CODEC = ResourceLocation.CODEC.xmap(s -> {
        final GeasEffectType geasEffectType = MalumGeasEffectTypeRegistry.GEAS_TYPES_REGISTRY.get(s);
        if (geasEffectType == null) {
            return MalumGeasEffectTypeRegistry.CREED_OF_THE_BLIGHT_EATER.get();
        }
        return geasEffectType;
    }, GeasEffectType::getId);

    public final Supplier<GeasEffect> effect;

    public GeasEffect dummyEffectInstance;

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

    public ItemStack createDefaultStack() {
        ItemStack geas = new ItemStack(ItemRegistry.GEAS.get());
        geas.set(DataComponentRegistry.GEAS_EFFECT, new GeasDataComponent(this));
        return geas;
    }
    public GeasEffect createEffect() {
        return effect.get();
    }
    public GeasEffect getEffectForDisplay() {
        if (dummyEffectInstance == null) {
            dummyEffectInstance = effect.get();
        }
        return dummyEffectInstance;
    }
}

