package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.JsonOps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.*;

public class SpiritDataReloadListener extends SimpleJsonResourceReloadListener {
    public static final Map<ResourceLocation, EntitySpiritDropData> SPIRIT_DATA = new HashMap<>();
    public static final Set<ResourceLocation> HAS_NO_DATA = new HashSet<>();

    public static final EntitySpiritDropData DEFAULT_MONSTER_SPIRIT_DATA = EntitySpiritDropData
            .builder(SpiritTypeRegistry.WICKED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_CREATURE_SPIRIT_DATA = EntitySpiritDropData
            .builder(SpiritTypeRegistry.SACRED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_AMBIENT_SPIRIT_DATA = EntitySpiritDropData
            .builder(SpiritTypeRegistry.AERIAL_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_WATER_CREATURE_SPIRIT_DATA = EntitySpiritDropData
            .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT)
            .withSpirit(SpiritTypeRegistry.SACRED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_WATER_AMBIENT_SPIRIT_DATA = EntitySpiritDropData
            .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA = EntitySpiritDropData
            .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT)
            .withSpirit(SpiritTypeRegistry.EARTHEN_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_AXOLOTL_SPIRIT_DATA = EntitySpiritDropData // They're their own category
            .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT, 2)
            .withSpirit(SpiritTypeRegistry.SACRED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_BOSS_SPIRIT_DATA = EntitySpiritDropData
            .builder(SpiritTypeRegistry.ELDRITCH_SPIRIT, 2)
            .build();

    private static final Gson GSON = (new GsonBuilder()).create();

    public SpiritDataReloadListener() {
        super(GSON, "spirit_data/entity");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        SPIRIT_DATA.clear();
        HAS_NO_DATA.clear();
        for (JsonElement entry : objectIn.values()) {
            var object = entry.getAsJsonObject();
            var name = object.getAsJsonPrimitive("registry_name").getAsString();
            var resourceLocation = ResourceLocation.tryParse(name);
            if (resourceLocation != null && !BuiltInRegistries.ENTITY_TYPE.containsKey(resourceLocation)) {
                continue;
            }
            if (!object.has("primary_type")) {
                MalumMod.LOGGER.warn("Entity with registry name: {} lacks a primary spirit type. This is a datapack error.", name);
                continue;
            }
            var primaryType = object.getAsJsonPrimitive("primary_type").getAsString();
            if (primaryType.equals("none")) {
                MalumMod.LOGGER.info("Removed spirit drops for entity with registry name: {}", name);
                SPIRIT_DATA.remove(resourceLocation);
                HAS_NO_DATA.add(resourceLocation);
            } else {
                MalumMod.LOGGER.info("Added spirit drops for entity with registry name: {}", name);
                JsonArray array = object.getAsJsonArray("spirits");
                SPIRIT_DATA.put(resourceLocation, new EntitySpiritDropData(MalumSpiritType.getSpiritType(primaryType), getSpiritDrops(array), getItemAsSoul(object)));
                HAS_NO_DATA.remove(resourceLocation);
            }
        }
    }

    private static List<SpiritIngredient> getSpiritDrops(JsonArray array) {
        List<SpiritIngredient> spiritData = new ArrayList<>();
        for (JsonElement spiritElement : array) {
            JsonObject spiritObject = spiritElement.getAsJsonObject();
            String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
            int count = spiritObject.getAsJsonPrimitive("count").getAsInt();
            spiritData.add(new SpiritIngredient(MalumSpiritType.getSpiritType(spiritName), count));
        }
        return spiritData;
    }

    private static Ingredient getItemAsSoul(JsonObject object) {
        return object.has("spirit_item") ? Ingredient.CODEC.decode(JsonOps.INSTANCE, object.get("spirit_item")).map(Pair::getFirst).result().orElse(null) : null;
    }
}