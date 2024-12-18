package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.config.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.*;

public class EntitySpiritDropData {

    public static final EntitySpiritDropData EMPTY = new EntitySpiritDropData(SpiritTypeRegistry.SACRED_SPIRIT, new ArrayList<>(), null);

    public final MalumSpiritType primaryType;
    public final int totalSpirits;
    public final List<SpiritIngredient> dataEntries;
    @Nullable
    public final Ingredient itemAsSoul;

    public EntitySpiritDropData(MalumSpiritType primaryType, List<SpiritIngredient> dataEntries, @Nullable Ingredient itemAsSoul) {
        this.primaryType = primaryType;
        this.totalSpirits = dataEntries.stream().mapToInt(SpiritIngredient::getCount).sum();
        this.dataEntries = dataEntries;
        this.itemAsSoul = itemAsSoul;
    }

    public static List<ItemStack> getSpiritStacks(LivingEntity entity) {
        return getSpiritData(entity).map(EntitySpiritDropData::getSpiritStacks).orElse(Collections.emptyList());
    }

    public static List<ItemStack> getSpiritStacks(EntitySpiritDropData data) {
        return data != null ? data.dataEntries.stream().map(SpiritIngredient::getStack).collect(Collectors.toList()) : (Collections.emptyList());
    }

    public static Optional<EntitySpiritDropData> getSpiritData(LivingEntity entity) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (SpiritDataReloadListener.HAS_NO_DATA.contains(key))
            return Optional.empty();

        EntitySpiritDropData spiritData = SpiritDataReloadListener.SPIRIT_DATA.get(key);
        if (spiritData != null)
            return Optional.of(spiritData);

        if (!entity.canUsePortal(false))
            return Optional.of(SpiritDataReloadListener.DEFAULT_BOSS_SPIRIT_DATA);

        if (!CommonConfig.USE_DEFAULT_SPIRIT_VALUES.getConfigValue())
            return Optional.empty();

        return switch (entity.getType().getCategory()) {
            case MONSTER -> Optional.of(SpiritDataReloadListener.DEFAULT_MONSTER_SPIRIT_DATA);
            case CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_CREATURE_SPIRIT_DATA);
            case AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_AMBIENT_SPIRIT_DATA);
            case AXOLOTLS -> Optional.of(SpiritDataReloadListener.DEFAULT_AXOLOTL_SPIRIT_DATA);
            case UNDERGROUND_WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA);
            case WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_CREATURE_SPIRIT_DATA);
            case WATER_AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_AMBIENT_SPIRIT_DATA);
            default -> Optional.empty();
        };
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
        private Ingredient itemAsSoul = null;

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

        public Builder withItemAsSoul(Ingredient itemAsSoul) {
            this.itemAsSoul = itemAsSoul;
            return this;
        }

        public EntitySpiritDropData build() {
            return new EntitySpiritDropData(type, spirits, itemAsSoul);
        }
    }
}