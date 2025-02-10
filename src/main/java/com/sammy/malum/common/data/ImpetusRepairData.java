package com.sammy.malum.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ImpetusRepairData(Holder<Item> damagedImpetus) {
    public static final Codec<ImpetusRepairData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.ITEM_NON_AIR_CODEC.fieldOf("damaged_impetus").forGetter(ImpetusRepairData::damagedImpetus)
    ).apply(instance, ImpetusRepairData::new));
}