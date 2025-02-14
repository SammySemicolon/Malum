package com.sammy.malum.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ImpetusData(Holder<Item> fracturedImpetus) {
    public static final Codec<ImpetusData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.ITEM_NON_AIR_CODEC.fieldOf("fractured_impetus").forGetter(ImpetusData::fracturedImpetus)
    ).apply(instance, ImpetusData::new));
}