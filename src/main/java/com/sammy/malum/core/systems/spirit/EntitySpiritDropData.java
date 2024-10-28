package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.item.crafting.*;

import javax.annotation.*;
import java.util.*;

public class EntitySpiritDropData {
    public static final String SOUL_DATA = "soul_data";
    public static final EntitySpiritDropData EMPTY = new EntitySpiritDropData(SpiritTypeRegistry.SACRED_SPIRIT, new ArrayList<>(), null);
    public final MalumSpiritType primaryType;
    public final int totalSpirits;
    public final List<SpiritIngredient> dataEntries;
    @Nullable
    public final Ingredient spiritItem;

    public EntitySpiritDropData(MalumSpiritType primaryType, List<SpiritIngredient> dataEntries, @Nullable Ingredient spiritItem) {
        this.primaryType = primaryType;
        this.totalSpirits = dataEntries.stream().mapToInt(SpiritIngredient::getCount).sum();
        this.dataEntries = dataEntries;
        this.spiritItem = spiritItem;
    }

    public static Builder builder(MalumSpiritType type) {
        return builder(type, 1);
    }

    public static Builder builder(MalumSpiritType type, int count) {
        return new Builder(type).withSpirit(type, count);
    }

    public static class Builder {
        private final MalumSpiritType type;
        private final List<SpiritIngredient> spirits = new ArrayList<>();
        private Ingredient spiritItem = null;

        public Builder(MalumSpiritType type) {
            this.type = type;
        }

        public Builder withSpirit(MalumSpiritType spiritType) {
            return withSpirit(spiritType, 1);
        }

        public Builder withSpirit(MalumSpiritType spiritType, int count) {
            spirits.add(new SpiritIngredient(spiritType, count));
            return this;
        }

        public Builder withSpiritItem(Ingredient spiritItem) {
            this.spiritItem = spiritItem;
            return this;
        }

        public EntitySpiritDropData build() {
            return new EntitySpiritDropData(type, spirits, spiritItem);
        }
    }
}