package com.sammy.malum.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.*;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.*;
import java.util.stream.*;

public class SpiritRepairRecipe extends AbstractSpiritListMalumRecipe {

    public static final MapCodec<SpiritRepairRecipe> CODEC = RecordCodecBuilder.mapCodec(obj -> obj.group(
            Codec.FLOAT.fieldOf("durabilityPercentage").forGetter(recipe -> recipe.durabilityPercentage),
            Codec.STRING.optionalFieldOf("itemIdRegex", "").forGetter(recipe -> ".*"),
            Codec.STRING.optionalFieldOf("modIdRegex", "").forGetter(recipe -> ".*"),
            ResourceLocation.CODEC.listOf().optionalFieldOf("inputs", List.of()).forGetter(recipe -> recipe.inputs.stream().map(BuiltInRegistries.ITEM::getKey).collect(Collectors.toList())),
            SizedIngredient.FLAT_CODEC.fieldOf("repairMaterial").forGetter(recipe -> recipe.repairMaterial),
            SpiritIngredient.CODEC.codec().listOf().fieldOf("spirits").forGetter(recipe -> recipe.spirits)
    ).apply(obj, SpiritRepairRecipe::new));

    public static final String NAME = "spirit_repair";

    public final float durabilityPercentage;
    public final String itemIdRegex;
    public final String modIdRegex;
    public final ArrayList<Item> inputs;
    public final SizedIngredient repairMaterial;

    public SpiritRepairRecipe(float durabilityPercentage, String itemIdRegex, String modIdRegex, List<ResourceLocation> inputs, SizedIngredient repairMaterial, List<SpiritIngredient> spirits) {
        super(RecipeSerializerRegistry.REPAIR_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_REPAIR.get(), spirits);
        this.durabilityPercentage = durabilityPercentage;
        this.itemIdRegex = itemIdRegex;
        this.modIdRegex = modIdRegex;
        this.repairMaterial = repairMaterial;
        this.inputs = inputs.stream().map(BuiltInRegistries.ITEM::get).collect(Collectors.toCollection(ArrayList::new));
        addToInputs(this.inputs, itemIdRegex, modIdRegex);
    }


    public boolean doesInputMatch(ItemStack input) {
        return this.inputs.stream().anyMatch(i -> i.equals(input.getItem()));
    }

    public boolean doesRepairMatch(ItemStack input) {
        return this.repairMaterial.test(input);
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
    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return inputs.contains(singleRecipeInput.item().getItem());
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
