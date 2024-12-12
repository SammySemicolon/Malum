package com.sammy.malum.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
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
            Codec.FLOAT.fieldOf("durabilityPercentage").forGetter(recipe -> recipe.durabilityPercentage),
            Codec.STRING.optionalFieldOf("itemIdRegex", "").forGetter(recipe -> recipe.itemIdRegex),
            Codec.STRING.optionalFieldOf("modIdRegex", "").forGetter(recipe -> recipe.modIdRegex),
            ResourceLocation.CODEC.listOf().optionalFieldOf("inputs", List.of()).forGetter(recipe -> recipe.itemsForRepair.stream().map(BuiltInRegistries.ITEM::getKey).collect(Collectors.toList())),
            SizedIngredient.FLAT_CODEC.fieldOf("repairMaterial").forGetter(recipe -> recipe.repairMaterial),
            SpiritIngredient.CODEC.codec().listOf().fieldOf("spirits").forGetter(recipe -> recipe.spirits)
    ).apply(obj, SpiritRepairRecipe::new));

    public static final String NAME = "spirit_repair";

    public final float durabilityPercentage;
    public final String itemIdRegex;
    public final String modIdRegex;
    public final ArrayList<Item> itemsForRepair;
    public final List<SpiritIngredient> spirits;
    public final SizedIngredient repairMaterial;

    public SpiritRepairRecipe(float durabilityPercentage, String itemIdRegex, String modIdRegex, List<ResourceLocation> itemsForRepair, SizedIngredient repairMaterial, List<SpiritIngredient> spirits) {
        super(RecipeSerializerRegistry.REPAIR_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_REPAIR.get());
        this.durabilityPercentage = durabilityPercentage;
        this.itemIdRegex = itemIdRegex;
        this.modIdRegex = modIdRegex;
        this.repairMaterial = repairMaterial;
        this.itemsForRepair = itemsForRepair.stream().map(BuiltInRegistries.ITEM::get).collect(Collectors.toCollection(ArrayList::new));
        this.spirits = spirits;
        addToInputs(this.itemsForRepair, itemIdRegex, modIdRegex);
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(repairMaterial, spirits);
    }

    public boolean isValidItemForRepair(ItemStack input) {
        return this.itemsForRepair.stream().anyMatch(i -> i.equals(input.getItem()));
    }

    protected static void addToInputs(ArrayList<Item> inputs, String itemIdRegex, String modIdRegex) {
        for (int i = 0; i < BuiltInRegistries.ITEM.size(); i++) {
            Item item = BuiltInRegistries.ITEM.byId(i);
            if (item.isRepairable(item.getDefaultInstance())) {
                if (BuiltInRegistries.ITEM.getKey(item).getPath().matches(itemIdRegex)) {
                    if (!modIdRegex.isEmpty() && !BuiltInRegistries.ITEM.getKey(item).getNamespace().matches(modIdRegex)) {
                        break;
                    }
                    if (item instanceof IRepairOutputOverride repairOutputOverride && repairOutputOverride.ignoreDuringLookup()) {
                        break;
                    }
                    if (!inputs.contains(item)) {
                        inputs.add(item);
                    }
                }
            }
        }
    }

    public interface IRepairOutputOverride {
        default Item overrideRepairResult() {
            return Items.AIR;
        }

        default boolean ignoreDuringLookup() {
            return false;
        }
    }
}
