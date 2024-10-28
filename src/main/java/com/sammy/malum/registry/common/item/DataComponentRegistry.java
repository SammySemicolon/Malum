package com.sammy.malum.registry.common.item;

import com.mojang.serialization.Codec;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.spirit.RitualShardItem;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public interface DataComponentRegistry {
    DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MalumMod.MALUM);

    DeferredHolder<DataComponentType<?>, DataComponentType<RitualShardItem.Props>> RITUAL_SHARD_PROPS = register("shard_properties", builder ->
            builder.persistent(RitualShardItem.Props.CODEC).networkSynchronized(RitualShardItem.Props.STREAM_CODEC)
    );

    DeferredHolder<DataComponentType<?>, DataComponentType<String>> ITEM_SKIN = register("item_skin", builder ->
            builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8)
    );

    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CATALYST_FLINGER_STATE = register("state", builder ->
            builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
    );

    DeferredHolder<DataComponentType<?>, DataComponentType<SpiritJarItem.Contents>> SPIRIT_JAR_CONTENTS = register("spirit_jar_contents", builder ->
            builder.persistent(SpiritJarItem.Contents.CODEC).networkSynchronized(SpiritJarItem.Contents.STREAM_CODEC)
    );

    DeferredHolder<DataComponentType<?>, DataComponentType<DyedItemColor>> SECONDARY_DYE_COLOR = register("secondary_dye_color", builder ->
            builder.persistent(DyedItemColor.CODEC).networkSynchronized(DyedItemColor.STREAM_CODEC)
    );


    static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}
