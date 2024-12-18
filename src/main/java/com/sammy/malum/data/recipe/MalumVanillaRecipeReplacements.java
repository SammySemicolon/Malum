package com.sammy.malum.data.recipe;

import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CompletableFuture;


public class MalumVanillaRecipeReplacements extends VanillaRecipeProvider {

    private static final Map<Item, TagKey<Item>> replacements = new HashMap<>();
    private static final Set<ResourceLocation> excludes = new HashSet<>();

    public MalumVanillaRecipeReplacements(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {
        replace(Items.QUARTZ, Tags.Items.GEMS_QUARTZ);
        replace(Items.SLIME_BALL, Tags.Items.SLIMEBALLS);

        super.buildRecipes(pWriter);
    }


    private void exclude(ItemLike item) {
        excludes.add(BuiltInRegistries.ITEM.getKey(item.asItem()));
    }

    private void replace(ItemLike item, TagKey<Item> tag) {
        replacements.put(item.asItem(), tag);
    }

    public static RecipeBuilder enhance(RecipeBuilder vanilla) {
        if (vanilla instanceof ShapelessRecipeBuilder shapeless)
            return enhance(shapeless);
        if (vanilla instanceof ShapedRecipeBuilder shaped)
            return enhance(shaped);
        return null;
    }

    private static RecipeBuilder enhance(ShapelessRecipeBuilder vanilla) {
        List<Ingredient> ingredients = getField(ShapelessRecipeBuilder.class, vanilla, 4);
        ResourceLocation id = RecipeBuilder.getDefaultRecipeId(vanilla.getResult());
        boolean modified = false;
        for (int x = 0; x < ingredients.size(); x++) {
            Ingredient ing = enhance(id, ingredients.get(x));
            if (ing != null) {
                ingredients.set(x, ing);
                modified = true;
            }
        }
        return modified ? vanilla : null;
    }

    private static RecipeBuilder enhance(ShapedRecipeBuilder vanilla) {
        Map<Character, Ingredient> ingredients = getField(ShapedRecipeBuilder.class, vanilla, 5);
        ResourceLocation id = RecipeBuilder.getDefaultRecipeId(vanilla.getResult());
        boolean modified = false;
        for (Character x : ingredients.keySet()) {
            Ingredient ing = enhance(id, ingredients.get(x));
            if (ing != null) {
                ingredients.put(x, ing);
                modified = true;
            }
        }
        return modified ? vanilla : null;
    }

    private static Ingredient enhance(ResourceLocation name, Ingredient vanilla) {
        if (excludes.contains(name))
            return null;

        boolean modified = false;
        List<Ingredient.Value> items = new ArrayList<>();
        Ingredient.Value[] vanillaItems = getField(Ingredient.class, vanilla, 2); //This will probably crash between versions, if null fix index
        for (Ingredient.Value entry : vanillaItems) {
            if (entry instanceof Ingredient.ItemValue) {
                ItemStack stack = entry.getItems().stream().findFirst().orElse(ItemStack.EMPTY);
                TagKey<Item> replacement = replacements.get(stack.getItem());
                if (replacement != null) {
                    items.add(new Ingredient.TagValue(replacement));
                    modified = true;
                } else
                    items.add(entry);
            } else
                items.add(entry);
        }
        return modified ? Ingredient.fromValues(items.stream()) : null;
    }

    @SuppressWarnings("unchecked")
    private static <T, R> R getField(Class<T> clz, T inst, int index) {
        Field fld = clz.getDeclaredFields()[index];
        fld.setAccessible(true);
        try {
            return (R) fld.get(inst);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}