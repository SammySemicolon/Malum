package com.sammy.malum.common.recipe.vanilla;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.*;

public class NodeCookingSerializer<T extends AbstractCookingRecipe & INodeCookingRecipe> implements RecipeSerializer<T> {
    public final int defaultCookingTime;
    public final Factory<T> factory;
    public final MapCodec<T> codec;
    public final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public NodeCookingSerializer(Factory<T> pFactory, int defaultCookingTime) {
        this.defaultCookingTime = defaultCookingTime;
        this.factory = pFactory;
        this.codec = RecordCodecBuilder.mapCodec(obj -> obj.group(
                Codec.STRING.fieldOf("group").forGetter(AbstractCookingRecipe::getGroup),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(INodeCookingRecipe::getInput),
                Ingredient.CODEC_NONEMPTY.fieldOf("output").forGetter(INodeCookingRecipe::getRawOutput),
                Codec.INT.fieldOf("outputCount").forGetter(INodeCookingRecipe::getOutputCount),
                Codec.FLOAT.fieldOf("experience").forGetter(AbstractCookingRecipe::getExperience),
                Codec.INT.fieldOf("cookingTime").forGetter(AbstractCookingRecipe::getCookingTime)
        ).apply(obj, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient result = Ingredient.of(ItemStack.STREAM_CODEC.decode(buffer));
        int outputCount = buffer.readInt();
        float xp = buffer.readFloat();
        int ctime = buffer.readInt();
        return this.factory.create(group, ingredient, result, outputCount, xp, ctime);
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T pRecipe) {
        buffer.writeUtf(pRecipe.getGroup());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, pRecipe.getInput());
        ItemStack.STREAM_CODEC.encode(buffer, pRecipe.getOutput());
        buffer.writeInt(pRecipe.getOutputCount());
        buffer.writeFloat(pRecipe.getExperience());
        buffer.writeInt(pRecipe.getCookingTime());
    }

    @Override
    public MapCodec<T> codec() {
        return codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return streamCodec;
    }

    public static ItemStack getStackFromIngredient(Ingredient ingredient) {
        final ItemStack[] items = ingredient.getItems();
        final Optional<ItemStack> stack = Arrays.stream(items).min(Comparator.comparing(c -> c.getItem().getDescriptionId()));
        return stack.orElse(new ItemStack(Items.BARRIER));
    }

    public interface Factory<T extends AbstractCookingRecipe> {
        T create(String group, Ingredient ingredient, Ingredient output, int outputCount, float experience, int cookingTime);
    }
}