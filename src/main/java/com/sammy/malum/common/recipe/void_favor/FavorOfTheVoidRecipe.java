package com.sammy.malum.common.recipe.void_favor;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.systems.recipe.*;

public class FavorOfTheVoidRecipe extends LodestoneInWorldRecipe<SingleRecipeInput> {

    public static final String NAME = "favor_of_the_void";

    public final Ingredient ingredient;

    public final ItemStack output;

    public FavorOfTheVoidRecipe(Ingredient ingredient, ItemStack output) {
        super(RecipeSerializerRegistry.VOID_FAVOR_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.VOID_FAVOR.get(), output);
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item());
    }

    public static class Serializer implements RecipeSerializer<FavorOfTheVoidRecipe> {

        public static final MapCodec<FavorOfTheVoidRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient),
                ItemStack.CODEC.fieldOf("output").forGetter((recipe) -> recipe.output)
        ).apply(obj, FavorOfTheVoidRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FavorOfTheVoidRecipe> STREAM_CODEC =
                ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

        @Override
        public MapCodec<FavorOfTheVoidRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FavorOfTheVoidRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}