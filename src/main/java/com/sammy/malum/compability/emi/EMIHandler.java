package com.sammy.malum.compability.emi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.spirit.transmutation.SpiritTransmutationRecipe;
import com.sammy.malum.compability.emi.recipes.*;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.forge_stuff.SizedIngredient;
import com.sammy.malum.registry.common.SpiritRiteRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.Map;


public class EMIHandler implements EmiPlugin {

    private static final EmiStack SPIRIT_INFUSION_WORKSTATION = EmiStack.of(ItemRegistry.SPIRIT_ALTAR.get());
    public static final EmiRecipeCategory SPIRIT_INFUSION = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_infusion"),
            SPIRIT_INFUSION_WORKSTATION
    );

    private static final EmiStack SPIRIT_FOCUSING_WORKSTATION = EmiStack.of(ItemRegistry.SPIRIT_CRUCIBLE.get());
    public static final EmiRecipeCategory SPIRIT_FOCUSING = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_focusing"),
            SPIRIT_FOCUSING_WORKSTATION
    );

    private static final EmiStack SPIRIT_TRANSMUTATION_WORKSTATION = EmiStack.of(ItemRegistry.SOULWOOD_TOTEM_BASE.get());
    public static final EmiRecipeCategory SPIRIT_TRANSMUTATION = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_transmutation"),
            SPIRIT_TRANSMUTATION_WORKSTATION
    );

    private static final EmiStack SPIRIT_RITE_WORKSTATION = EmiStack.of(ItemRegistry.RUNEWOOD_TOTEM_BASE.get());
    public static final EmiRecipeCategory SPIRIT_RITE = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_rite"),
            SPIRIT_RITE_WORKSTATION
    );

    private static final EmiStack SPIRIT_REPAIR_WORKSTATION = EmiStack.of(ItemRegistry.REPAIR_PYLON.get());
    public static final EmiRecipeCategory SPIRIT_REPAIR = new EmiRecipeCategory(
            MalumMod.malumPath("spirit_repair"),
            SPIRIT_REPAIR_WORKSTATION
    );

    private static final EmiStack RUNEWORKING_WORKSTATION = EmiStack.of(ItemRegistry.RUNIC_WORKBENCH.get());
    public static final EmiRecipeCategory RUNEWORKING = new EmiRecipeCategory(
            MalumMod.malumPath("runeworking"),
            RUNEWORKING_WORKSTATION
    );

    private static final EmiStack WEEPING_WORKSTATION = EmiStack.of(ItemRegistry.VOID_DEPOT.get());
    public static final EmiRecipeCategory WEEPING = new EmiRecipeCategory(
            MalumMod.malumPath("weeping_well"),
            WEEPING_WORKSTATION
    );

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(SPIRIT_INFUSION);
        registry.addCategory(SPIRIT_FOCUSING);
        registry.addCategory(RUNEWORKING);
        registry.addCategory(WEEPING);
        registry.addCategory(SPIRIT_REPAIR);
        registry.addCategory(SPIRIT_TRANSMUTATION);
        registry.addCategory(SPIRIT_RITE);

        registry.addWorkstation(SPIRIT_INFUSION, SPIRIT_INFUSION_WORKSTATION);
        registry.addWorkstation(SPIRIT_FOCUSING, SPIRIT_FOCUSING_WORKSTATION);
        registry.addWorkstation(RUNEWORKING, RUNEWORKING_WORKSTATION);
        registry.addWorkstation(WEEPING, WEEPING_WORKSTATION);
        registry.addWorkstation(SPIRIT_REPAIR, SPIRIT_REPAIR_WORKSTATION);
        registry.addWorkstation(SPIRIT_TRANSMUTATION, SPIRIT_TRANSMUTATION_WORKSTATION);
        registry.addWorkstation(SPIRIT_RITE, SPIRIT_RITE_WORKSTATION);

        registry.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.SPIRIT_INFUSION.get()).forEach((recipe)
                -> registry.addRecipe(new SpiritInfusionEmiRecipe(recipe.id(), recipe.value())));

        registry.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.SPIRIT_FOCUSING.get()).forEach((recipe)
                -> registry.addRecipe(new SpiritFocusingEmiRecipe(recipe.id(), recipe.value())));

        registry.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.RUNEWORKING.get()).forEach((recipe)
                -> registry.addRecipe(new RuneworkingEmiRecipe(recipe.id(), recipe.value())));

        registry.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.VOID_FAVOR.get()).forEach((recipe)
                -> registry.addRecipe(new WeepingWellEmiRecipe(recipe.id(), recipe.value())));

        registry.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.SPIRIT_REPAIR.get()).forEach((recipe)
                -> registry.addRecipe(new SpiritRepairEmiRecipe(recipe.id(), recipe.value())));


        var transmutation = registry.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get());
        List<RecipeHolder<SpiritTransmutationRecipe>> leftovers = Lists.newArrayList();
        Map<String, List<RecipeHolder<SpiritTransmutationRecipe>>> groups = Maps.newLinkedHashMap();

        transmutation.forEach((recipe) -> {
            if (recipe.value().group != null) {
                List<RecipeHolder<SpiritTransmutationRecipe>> group = groups.computeIfAbsent(recipe.value().group, (k) -> Lists.newArrayList());
                group.add(recipe);
            } else {
                leftovers.add(recipe);
            }
        });
        groups.forEach((groupName, groupRecipes) -> {
            registry.addRecipe(new SpiritTransmutationEmiRecipe(groupRecipes.get(0).id(), new SpiritTransmutationRecipeWrapper(groupRecipes.stream()
                    .map(RecipeHolder::value)
                    .toList())));
        });

        leftovers.forEach(recipe -> {
            registry.addRecipe(new SpiritTransmutationEmiRecipe(recipe.id(), new SpiritTransmutationRecipeWrapper(List.of(recipe.value()))));
        });

        SpiritRiteRegistry.RITES.forEach((rite) -> registry.addRecipe(new SpiritRiteEmiRecipe(rite)));

        this.removeHiddenRecipes(registry);
    }

    private void removeHiddenRecipes(EmiRegistry registry) {

    }

    public static void addItems(WidgetHolder widgets, int left, int top, boolean vertical, List<EmiIngredient> ingredients) {
        int slots = ingredients.size();
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        for (int i = 0; i < slots; i++) {
            int offset = i * 20;
            int offsetLeft = left + 1 + (vertical ? 0 : offset);
            int offsetTop = top + 1 + (vertical ? offset : 0);
            widgets.addSlot(ingredients.get(i), offsetLeft, offsetTop).drawBack(false);
        }
    }

    public static EmiIngredient convertIngredientWithCount(SizedIngredient ingredient) {
        return EmiIngredient.of(ingredient.ingredient(), ingredient.count());
    }

    public static List<EmiIngredient> convertIngredientWithCounts(List<SizedIngredient> ingredients) {
        return ingredients.stream().map(EMIHandler::convertIngredientWithCount).toList();
    }

    public static EmiIngredient convertSpiritWithCount(SpiritIngredient spirit) {
        return EmiIngredient.of(Ingredient.of(spirit.getSpiritType().getSpiritShard()), spirit.getCount());
    }

    public static List<EmiIngredient> convertSpiritWithCounts(List<SpiritIngredient> spirits) {
        return spirits.stream().map(EMIHandler::convertSpiritWithCount).toList();
    }
}
