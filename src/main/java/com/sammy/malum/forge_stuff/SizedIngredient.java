package com.sammy.malum.forge_stuff;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

/**
 * Standard implementation for an ingredient and a count.
 *
 * <p>{@link Ingredient} does not perform count checks, so this class is used to wrap an ingredient with a count,
 * and provide a standard serialization format.
 */
public record SizedIngredient(Ingredient ingredient, int count) {
    /**
     * The "nested" codec for {@link SizedIngredient}.
     *
     * <p>The count is serialized separately from the rest of the ingredient, for example:
     *
     * <pre>{@code
     * {
     *     "ingredient": "minecraft:apple",
     *     "count": 3
     * }
     * }</pre>
     */

    public static final Codec<SizedIngredient> NESTED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(SizedIngredient::ingredient),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(SizedIngredient::count))
            .apply(instance, SizedIngredient::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SizedIngredient> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            SizedIngredient::ingredient,
            ByteBufCodecs.VAR_INT,
            SizedIngredient::count,
            SizedIngredient::new);

    /**
     * Helper method to create a simple sized ingredient that matches a single item.
     */
    public static SizedIngredient of(ItemLike item, int count) {
        return new SizedIngredient(Ingredient.of(item), count);
    }

    public static SizedIngredient of(TagKey<Item> tag, int count) {
        return new SizedIngredient(Ingredient.of(tag), count);
    }

    public SizedIngredient {
        if (count <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
    }

    /**
     * Performs a size-sensitive test on the given stack.
     *
     * @return {@code true} if the stack matches the ingredient and has at least the required count.
     */
    public boolean test(ItemStack stack) {
        return ingredient.test(stack) && stack.getCount() >= count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SizedIngredient other)) {
            return false;
        }
        return count == other.count && ingredient.equals(other.ingredient);
    }

    @Override
    public String toString() {
        return count + "x " + ingredient;
    }
}