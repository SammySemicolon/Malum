package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.capabilities.soul_data.*;
import net.minecraft.core.*;
import net.minecraft.world.item.*;

import java.util.*;

public class CachedSpiritDropsData {

    public static final Codec<CachedSpiritDropsData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.list(ItemStack.CODEC).optionalFieldOf("spiritDrops", Collections.emptyList()).forGetter(c -> c.spiritDrops),
            UUIDUtil.CODEC.optionalFieldOf("spiritOwner", null).forGetter(c -> c.spiritOwner)
    ).apply(obj, CachedSpiritDropsData::new));

    private List<ItemStack> spiritDrops = new ArrayList<>();
    private UUID spiritOwner;

    public CachedSpiritDropsData() {

    }
    public CachedSpiritDropsData(List<ItemStack> spiritDrops, UUID spiritOwner) {
        this.spiritDrops = spiritDrops;
        this.spiritOwner = spiritOwner;
    }

    public List<ItemStack> getSpiritDrops() {
        return spiritDrops;
    }

    public UUID getSpiritOwner() {
        return spiritOwner;
    }
}
