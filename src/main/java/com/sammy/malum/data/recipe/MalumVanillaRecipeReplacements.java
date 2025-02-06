package com.sammy.malum.data.recipe;

import net.minecraft.advancements.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.*;
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
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.neoforged.fml.util.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.*;
import net.neoforged.neoforge.common.crafting.*;
import net.neoforged.neoforge.common.data.internal.*;
import org.jetbrains.annotations.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CompletableFuture;


public class MalumVanillaRecipeReplacements  extends VanillaRecipeProvider {
    private final Map<Item, TagKey<Item>> replacements = new HashMap<>();
    private final Map<Item, Ingredient> specialReplacements = new HashMap<>();
    private final Set<ResourceLocation> excludes = new HashSet<>();

    public MalumVanillaRecipeReplacements(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    private void exclude(ItemLike item) {
        excludes.add(BuiltInRegistries.ITEM.getKey(item.asItem()));
    }

    private void exclude(String name) {
        excludes.add(ResourceLocation.parse(name));
    }

    private void replace(ItemLike item, TagKey<Item> tag) {
        replacements.put(item.asItem(), tag);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        replace(Items.SLIME_BALL, Tags.Items.SLIME_BALLS);

        replace(Items.QUARTZ, Tags.Items.GEMS_QUARTZ);

        exclude(Blocks.QUARTZ_BLOCK);
        exclude(Blocks.SLIME_BLOCK);

        super.buildRecipes(new RecipeOutput() {
            @Override
            public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
                Recipe<?> modified = enhance(id, recipe);
                if (modified != null)
                    recipeOutput.accept(id, modified, null, conditions);
            }

            @Override
            public Advancement.Builder advancement() {
                return recipeOutput.advancement();
            }
        });
    }

    @Nullable
    private Recipe<?> enhance(ResourceLocation id, Recipe<?> vanilla) {
        if (vanilla instanceof ShapelessRecipe shapeless)
            return enhance(id, shapeless);
        if (vanilla instanceof ShapedRecipe shaped)
            return enhance(id, shaped);
        return null;
    }

    @Nullable
    private ShapelessRecipe enhance(ResourceLocation id, ShapelessRecipe vanilla) {
        List<Ingredient> ingredients = vanilla.getIngredients();
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

    @Override
    protected CompletableFuture<?> buildAdvancement(CachedOutput p_253674_, HolderLookup.Provider p_323646_, AdvancementHolder p_301116_) {
        // NOOP - We don't replace any of the advancement things yet...
        return CompletableFuture.allOf();
    }

    @Nullable
    private ShapedRecipe enhance(ResourceLocation id, ShapedRecipe vanilla) {
        ShapedRecipePattern pattern = ObfuscationReflectionHelper.getPrivateValue(ShapedRecipe.class, vanilla, "pattern");
        if (pattern == null) throw new IllegalStateException(ShapedRecipe.class.getName() + " has no field pattern");
        ShapedRecipePattern.Data data = ((Optional<ShapedRecipePattern.Data>) ObfuscationReflectionHelper.getPrivateValue(ShapedRecipePattern.class, pattern, "data")).orElseThrow(() -> new IllegalArgumentException("recipe " + id + " does not have pattern data"));
        Map<Character, Ingredient> ingredients = data.key();
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

    @Nullable
    private Ingredient enhance(ResourceLocation name, Ingredient vanilla) {
        if (excludes.contains(name))
            return null;

        boolean modified = false;
        List<Ingredient.Value> items = new ArrayList<>();
        Ingredient.Value[] vanillaItems = vanilla.getValues();
        if (vanillaItems.length == 1 && vanillaItems[0] instanceof Ingredient.ItemValue itemValue) {
            Item item = itemValue.item().getItem();
            Ingredient replacement = specialReplacements.get(item);
            if (replacement != null) {
                return replacement;
            }
        }

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
}
