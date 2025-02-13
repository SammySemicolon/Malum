package com.sammy.malum.compability.jei;

import com.google.common.collect.Maps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.recipe.spirit.focusing.*;
import com.sammy.malum.common.recipe.spirit.infusion.*;
import com.sammy.malum.common.recipe.spirit.transmutation.*;
import com.sammy.malum.common.recipe.void_favor.*;
import com.sammy.malum.core.systems.rite.TotemicRiteType;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.compability.jei.categories.*;
import com.sammy.malum.compability.jei.recipes.SpiritTransmutationWrapper;
import com.sammy.malum.core.handlers.hiding.HiddenTagHandler;
import com.sammy.malum.registry.client.HiddenTagRegistry;
import com.sammy.malum.registry.common.SpiritRiteRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.apache.commons.compress.utils.Lists;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;


@JeiPlugin
public class JEIHandler implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MalumMod.MALUM, "main");

    public static final RecipeType<SpiritInfusionRecipe> SPIRIT_INFUSION = new RecipeType<>(SpiritInfusionRecipeCategory.UID, SpiritInfusionRecipe.class);
    public static final RecipeType<SpiritTransmutationWrapper> TRANSMUTATION = new RecipeType<>(SpiritTransmutationRecipeCategory.UID, SpiritTransmutationWrapper.class);
    public static final RecipeType<SpiritFocusingRecipe> FOCUSING = new RecipeType<>(SpiritFocusingRecipeCategory.UID, SpiritFocusingRecipe.class);
    public static final RecipeType<TotemicRiteType> RITES = new RecipeType<>(SpiritRiteRecipeCategory.UID, TotemicRiteType.class);
    public static final RecipeType<SpiritRepairRecipe> SPIRIT_REPAIR = new RecipeType<>(SpiritRepairRecipeCategory.UID, SpiritRepairRecipe.class);
    public static final RecipeType<FavorOfTheVoidRecipe> WEEPING_WELL = new RecipeType<>(WeepingWellRecipeCategory.UID, FavorOfTheVoidRecipe.class);
    public static final RecipeType<RunicWorkbenchRecipe> RUNEWORKING = new RecipeType<>(RuneworkingRecipeCategory.UID, RunicWorkbenchRecipe.class);

    public JEIHandler() {
    }

    public static void addItemsToJei(IRecipeLayoutBuilder iRecipeLayout, RecipeIngredientRole role, int left, int top, boolean isVertical, List<? extends Ingredient> components) {
        int slots = components.size();
        int startingOffset = 9 * (slots - 1);
        if (isVertical) {
            top -= startingOffset;
        } else {
            left -= startingOffset;
        }
        for (int i = 0; i < slots; i++) {
            int offset = i * 18;
            int oLeft = left + (isVertical ? 0 : offset);
            int oTop = top + (isVertical ? offset : 0);
            iRecipeLayout.addSlot(role, oLeft, oTop).addItemStacks(List.of(components.get(i).getItems()));
        }
    }

    public static void addCustomIngredientToJei(IRecipeLayoutBuilder iRecipeLayout, RecipeIngredientRole role, int left, int top, boolean isVertical, List<? extends ICustomIngredient> components) {
        int slots = components.size();
        int startingOffset = 9 * (slots - 1);
        if (isVertical) {
            top -= startingOffset;
        } else {
            left -= startingOffset;
        }
        for (int i = 0; i < slots; i++) {
            int offset = i * 18;
            int oLeft = left + (isVertical ? 0 : offset);
            int oTop = top + (isVertical ? offset : 0);
            iRecipeLayout.addSlot(role, oLeft, oTop).addItemStacks(components.get(i).getItems().toList());
        }
    }

    public static void addSizedIngredientsToJei(IRecipeLayoutBuilder iRecipeLayout, RecipeIngredientRole role, int left, int top, boolean isVertical, List<SizedIngredient> components) {
        int slots = components.size();
        int startingOffset = 9 * (slots - 1);
        if (isVertical) {
            top -= startingOffset;
        } else {
            left -= startingOffset;
        }
        for (int i = 0; i < slots; i++) {
            int offset = i * 18;
            int oLeft = left + (isVertical ? 0 : offset);
            int oTop = top + (isVertical ? offset : 0);
            iRecipeLayout.addSlot(role, oLeft, oTop).addItemStacks(List.of(components.get(i).getItems()));
        }
    }

    @Override
    public void registerRuntime(IRuntimeRegistration registration) {
        HiddenTagRegistry.blankOutHidingTags();
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new SpiritInfusionRecipeCategory(guiHelper),
            new SpiritTransmutationRecipeCategory(guiHelper),
            new SpiritFocusingRecipeCategory(guiHelper),
            new SpiritRiteRecipeCategory(guiHelper),
            new SpiritRepairRecipeCategory(guiHelper),
            new RuneworkingRecipeCategory(guiHelper),
            new WeepingWellRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            registry.addRecipes(SPIRIT_INFUSION, LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.SPIRIT_INFUSION.get()));

            List<SpiritTransmutationRecipe> transmutation = LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get());
            List<SpiritTransmutationRecipe> leftovers = Lists.newArrayList();
            Map<String, List<SpiritTransmutationRecipe>> groups = Maps.newLinkedHashMap();
            for (SpiritTransmutationRecipe recipe : transmutation) {
                if (!recipe.getGroup().isEmpty()) {
                    var group = groups.computeIfAbsent(recipe.getGroup(), k -> Lists.newArrayList());
                    group.add(recipe);
                } else
                    leftovers.add(recipe);
            }

            registry.addRecipes(TRANSMUTATION, groups.values().stream()
                .map(list -> list.stream().filter(it -> !it.output.isEmpty() && !it.ingredient.isEmpty()).collect(Collectors.toList()))
                .map(SpiritTransmutationWrapper::new)
                .collect(Collectors.toList()));
            registry.addRecipes(TRANSMUTATION, leftovers.stream()
                .filter(it -> !it.output.isEmpty() && !it.ingredient.isEmpty())
                .map(List::of)
                .map(SpiritTransmutationWrapper::new)
                .collect(Collectors.toList()));

            registry.addRecipes(FOCUSING, LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get()).stream()
                .filter(it -> !it.output.isEmpty()).collect(Collectors.toList()));
            registry.addRecipes(RITES, SpiritRiteRegistry.RITES);
            registry.addRecipes(SPIRIT_REPAIR, LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.SPIRIT_REPAIR.get()).stream()
                .filter(it -> !it.itemsForRepair.isEmpty()).collect(Collectors.toList()));
            registry.addRecipes(WEEPING_WELL, LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.VOID_FAVOR.get()).stream()
                .filter(it -> !it.output.isEmpty()).collect(Collectors.toList()));
            registry.addRecipes(RUNEWORKING, LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.RUNEWORKING.get()).stream()
                .filter(it -> !it.output.isEmpty()).collect(Collectors.toList()));
            if (FarmersDelightCompat.LOADED) {
                FarmersDelightCompat.AndJeiLoadedOnly.addInfo(registry);
            }
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()), SPIRIT_INFUSION);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()), FOCUSING);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.REPAIR_PYLON.get()), SPIRIT_REPAIR);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()), RITES);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SOULWOOD_TOTEM_BASE.get()), TRANSMUTATION);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNIC_WORKBENCH.get()), RUNEWORKING);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.VOID_DEPOT.get()), WEEPING_WELL);
    }

    private static final Map<RecipeType<?>, HiddenRecipeSet<?>> hiddenRecipeSets = new HashMap<>();
    private static final List<UUID> callbacks = new ArrayList<>();
    private static final Set<ItemStack> hiddenStacks = new LinkedHashSet<>();

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeRegistry = jeiRuntime.getRecipeManager();
        IIngredientManager ingredientManager = jeiRuntime.getIngredientManager();
        IJeiHelpers helpers = jeiRuntime.getJeiHelpers();
        IFocusFactory focusFactory = helpers.getFocusFactory();

        if (true) {
            return;
        }
        HiddenTagRegistry.rebuildHidingTags();
        callbacks.add(HiddenTagHandler.registerHiddenItemListener(() -> {
            var output = HiddenTagHandler.tagsToHide();

            if (!hiddenStacks.isEmpty()) {
                ingredientManager.addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, hiddenStacks);
                hiddenStacks.clear();
            }

            if (!output.isEmpty()) {
                Collection<ItemStack> ingredients = ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK);

                for (ItemStack stack : ingredients) {
                    if (output.stream().anyMatch(stack::is)) {
                        hiddenStacks.add(stack);
                    }
                }

                if (!hiddenStacks.isEmpty())
                    ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, hiddenStacks);
            }

            helpers.getAllRecipeTypes().forEach(it -> {
                HiddenRecipeSet<?> hiddenRecipes = hiddenRecipeSets.computeIfAbsent(it, HiddenRecipeSet::createSet);

                hiddenRecipes.unhidePreviouslyHiddenRecipes(recipeRegistry);
                if (!output.isEmpty())
                    hiddenRecipes.scanAndHideRecipes(recipeRegistry, focusFactory, output);
            });
        }));
    }

    @Override
    public void onRuntimeUnavailable() {
        if (true) {
            return;
        }
        callbacks.forEach(HiddenTagHandler::removeListener);
        callbacks.clear();
        hiddenRecipeSets.clear();
        hiddenStacks.clear();
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
