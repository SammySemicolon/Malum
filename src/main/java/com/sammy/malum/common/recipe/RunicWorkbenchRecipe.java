package com.sammy.malum.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.*;

public class RunicWorkbenchRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput> {
    public static final String NAME = "runeworking";

    public final SizedIngredient primaryInput;
    public final SpiritIngredient secondaryInput;
    public final ItemStack output;

    public RunicWorkbenchRecipe(SizedIngredient primaryInput, SpiritIngredient secondaryInput, ItemStack output) {
        super(RecipeSerializerRegistry.RUNEWORKING_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.RUNEWORKING.get());
        this.primaryInput = primaryInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(primaryInput, secondaryInput);
    }

    @Override
    public ItemStack assemble(SpiritBasedRecipeInput input, HolderLookup.Provider registries) {
        return this.output.copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.output;
    }

    public static class Serializer implements RecipeSerializer<RunicWorkbenchRecipe> {

        public static final MapCodec<RunicWorkbenchRecipe> CODEC = RecordCodecBuilder.mapCodec(obj -> obj.group(
                SizedIngredient.FLAT_CODEC.fieldOf("primaryInput").forGetter(recipe -> recipe.primaryInput),
                SpiritIngredient.CODEC.fieldOf("secondaryInput").forGetter(recipe -> recipe.secondaryInput),
                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
        ).apply(obj, RunicWorkbenchRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RunicWorkbenchRecipe> STREAM_CODEC =
                ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

        @Override
        public MapCodec<RunicWorkbenchRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RunicWorkbenchRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
