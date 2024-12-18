package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReapingDataReloadListener extends SimpleJsonResourceReloadListener {
    public static Map<ResourceLocation, List<MalumReapingDropsData>> REAPING_DATA = new HashMap<>();
    private static final Gson GSON = (new GsonBuilder()).create();

    public ReapingDataReloadListener() {
        super(GSON, "reaping_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        REAPING_DATA.clear();
        for (int i = 0; i < objectIn.size(); i++) {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            ResourceLocation resourceLocation = ResourceLocation.tryParse(name);
            if (resourceLocation != null && !BuiltInRegistries.ENTITY_TYPE.containsKey(resourceLocation)) {
                continue;
            }
            if (REAPING_DATA.containsKey(resourceLocation)) {
                MalumMod.LOGGER.info("Entity with registry name: " + name + " already has reaping data associated with it. Overwriting.");
            }
            JsonArray drops = object.getAsJsonArray("drops");
            List<MalumReapingDropsData> dropsList = new ArrayList<>();
            for (JsonElement drop : drops) {
                JsonObject dropObject = drop.getAsJsonObject();
                if (!dropObject.has("ingredient")) {
                    MalumMod.LOGGER.info("Entity with registry name: " + name + " lacks a reaping ingredient. Skipping drops entry.");
                    continue;
                }
                Ingredient dropIngredient = Ingredient.CODEC.parse(JsonOps.INSTANCE, dropObject.getAsJsonObject("ingredient")).getOrThrow(JsonParseException::new);
                float chance = dropObject.getAsJsonPrimitive("chance").getAsFloat();
                int min = dropObject.getAsJsonPrimitive("min").getAsInt();
                int max = dropObject.getAsJsonPrimitive("max").getAsInt();
                dropsList.add(new MalumReapingDropsData(dropIngredient, chance, min, max));
            }
            REAPING_DATA.put(resourceLocation, dropsList);
        }
    }

    public static class MalumReapingDropsData {

        public final Ingredient drop;
        public final float chance;
        public final int min;
        public final int max;

        public MalumReapingDropsData(Ingredient drop, float chance, int min, int max) {
            this.drop = drop;
            this.chance = chance;
            this.min = min;
            this.max = max;
        }
    }
}
