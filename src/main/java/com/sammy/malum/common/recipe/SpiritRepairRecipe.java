package com.sammy.malum.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;
import java.util.stream.*;

public class SpiritRepairRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput> {

    public static final MapCodec<SpiritRepairRecipe> CODEC = RecordCodecBuilder.mapCodec(obj -> obj.group(
            Codec.FLOAT.optionalFieldOf("durabilityPercentage", 0.5f).forGetter(recipe -> recipe.durabilityPercentage),
            Codec.STRING.optionalFieldOf("itemIdRegex", "").forGetter(recipe -> recipe.itemIdRegex),
            Codec.STRING.optionalFieldOf("modIdRegex", "").forGetter(recipe -> recipe.modIdRegex),
            ResourceLocation.CODEC.listOf().optionalFieldOf("inputs", List.of()).forGetter(recipe -> recipe.itemsForRepair.stream().map(BuiltInRegistries.ITEM::getKey).collect(Collectors.toList())),
            SizedIngredient.FLAT_CODEC.fieldOf("repairMaterial").forGetter(recipe -> recipe.repairMaterial),
            SpiritIngredient.CODEC.codec().listOf().fieldOf("spirits").forGetter(recipe -> recipe.spirits),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("repairOutputOverride", Items.AIR).forGetter(recipe -> recipe.repairOutputOverride)
    ).apply(obj, SpiritRepairRecipe::new));

    public static final String NAME = "spirit_repair";
    public final float durabilityPercentage;
    public final String itemIdRegex;
    public final String modIdRegex;
    public final ArrayList<Item> itemsForRepair;
    public final List<SpiritIngredient> spirits;
    public final SizedIngredient repairMaterial;
    public final Item repairOutputOverride;

    public SpiritRepairRecipe(float durabilityPercentage, String itemIdRegex, String modIdRegex, List<ResourceLocation> itemsForRepair, SizedIngredient repairMaterial, List<SpiritIngredient> spirits, Item repairOutputOverride) {
        super(RecipeSerializerRegistry.REPAIR_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_REPAIR.get());
        this.durabilityPercentage = durabilityPercentage;
        this.itemIdRegex = itemIdRegex;
        this.modIdRegex = modIdRegex;
        this.repairMaterial = repairMaterial;
        this.itemsForRepair = itemsForRepair.stream().map(BuiltInRegistries.ITEM::get).collect(Collectors.toCollection(ArrayList::new));
        this.spirits = spirits;
        this.repairOutputOverride = repairOutputOverride;
        addToInputs(this.itemsForRepair, itemIdRegex, modIdRegex);
    }

    public boolean isValidItemForRepair(ItemStack input) {
        return this.itemsForRepair.stream().anyMatch(i -> i.equals(input.getItem()));
    }

    public ItemStack getResultItem(ItemStack repaired) {
        if (repairOutputOverride != Items.AIR) {
            var output = repairOutputOverride.getDefaultInstance();
            output.applyComponents(repaired.getComponents());
            return output;
        }
        var output = repaired.copy();
        output.setDamageValue(Math.max(0, repaired.getDamageValue() - (int) (output.getMaxDamage() * durabilityPercentage)));
        return output;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(repairMaterial, spirits);
    }

    @Override
    public final ItemStack getResultItem(HolderLookup.Provider registries) {
        return repairMaterial.getItems()[0];
    }

    protected static void addToInputs(ArrayList<Item> inputs, String itemIdRegex, String modIdRegex) {
        for (int i = 0; i < BuiltInRegistries.ITEM.size(); i++) {
            Item item = BuiltInRegistries.ITEM.byId(i);
            if (item.isRepairable(item.getDefaultInstance())) {
                var damagedImpetusVariant = item.builtInRegistryHolder().getData(DataMapRegistry.FRACTURED_IMPETUS_VARIANT);
                if (damagedImpetusVariant != null) {
                    continue;
                }
                var id = BuiltInRegistries.ITEM.getKey(item);
                if (id.getPath().matches(itemIdRegex)) {
                    if (!modIdRegex.isEmpty() && !id.getNamespace().matches(modIdRegex)) {
                        break;
                    }
                    if (!inputs.contains(item)) {
                        inputs.add(item);
                    }
                }
            }
        }
    }
}