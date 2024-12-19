package com.sammy.malum.registry.common.item;

import com.mojang.serialization.Codec;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.common.item.curiosities.TemporarilyDisabledItem.Disabled;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.function.UnaryOperator;

public class DataComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulwovenPouchContents>> SOULWOVEN_POUCH_CONTENTS = register("soulwoven_pouch_data", builder ->
            builder.persistent(SoulwovenPouchContents.CODEC).networkSynchronized(SoulwovenPouchContents.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulwovenBannerPatternData>> SOULWOVEN_BANNER_PATTERN = register("soulwoven_banner_pattern", builder ->
            builder.persistent(SoulwovenBannerPatternData.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RitualData>> RITUAL_DATA = register("ritual_data", builder ->
            builder.persistent(RitualData.CODEC).networkSynchronized(RitualData.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ITEM_SKIN = register("item_skin", builder ->
            builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CatalystFlingerState>> CATALYST_LOBBER_STATE = register("catalyst_flinger_state", builder ->
            builder.persistent(CatalystFlingerState.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SpiritJarItem.Contents>> SPIRIT_JAR_CONTENTS = register("spirit_jar_contents", builder ->
            builder.persistent(SpiritJarItem.Contents.CODEC).networkSynchronized(SpiritJarItem.Contents.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DyedItemColor>> SECONDARY_DYE_COLOR = register("secondary_dye_color", builder ->
            builder.persistent(DyedItemColor.CODEC).networkSynchronized(DyedItemColor.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Disabled>> DISABLED = register("disabled_item_storage", builder ->
            builder.persistent(Disabled.CODEC).networkSynchronized(Disabled.STREAM_CODEC));

    static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}