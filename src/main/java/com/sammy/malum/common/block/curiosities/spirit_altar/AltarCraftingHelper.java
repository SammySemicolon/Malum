package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.recipe.spirit.infusion.SpiritInfusionRecipe;
import com.sammy.malum.core.systems.recipe.SpiritBasedRecipeInput;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sammy.malum.common.block.curiosities.spirit_altar.SpiritAltarBlockEntity.*;

public class AltarCraftingHelper {
	public record Ranking(int inputItemCount, int spiritStackCount, int spiritItemCount, int ingredientStackCount, int ingredientItemCount) implements Comparable<Ranking> {
		@Override
		public int compareTo(@NotNull AltarCraftingHelper.Ranking o) {
			int comparison = inputItemCount - o.inputItemCount;
			if (comparison != 0) return comparison;

			comparison = spiritStackCount - o.spiritStackCount;
			if (comparison != 0) return comparison;

			comparison = spiritItemCount - o.spiritItemCount;
			if (comparison != 0) return comparison;

			comparison = ingredientStackCount - o.ingredientStackCount;
			if (comparison != 0) return comparison;

			return ingredientItemCount - o.ingredientItemCount;
		}
	}

	public record Extraction(int numberOfStacks, int numberOfItems) {}

	/**
	 * Attempts to simulate pulling all of a recipe's ingredients from an inventory.
	 *
	 * Returns null if not everything could be found.
	 * Otherwise, returns the number of stacks and the total count of items.
	 */
	public static Extraction simulateExtraction(IItemHandler inventory, List<SizedIngredient> ingredients) {
		IItemHandler frozen = frozenCopy(inventory);
		int numberOfItems = 0;
		int numberOfIngredients = 0;

		for (SizedIngredient ingredient : ingredients) {
			NonNullList<ItemStack> extracted = extractIngredient(frozen, ingredient.ingredient(), ingredient.count(), false);

			int numExtracted = extracted.stream().mapToInt(ItemStack::getCount).sum();
			if (numExtracted != ingredient.count())
				return null;

			numberOfIngredients++;
			numberOfItems += numExtracted;
		}

		return new Extraction(numberOfIngredients, numberOfItems);
	}

	public static SizedIngredient getFirstMissingIngredient(IItemHandler inventory, List<SizedIngredient> ingredients) {
		IItemHandler frozen = frozenCopy(inventory);

		for (SizedIngredient ingredient : ingredients) {
			NonNullList<ItemStack> extracted = extractIngredient(frozen, ingredient.ingredient(), ingredient.count(), false);
			int numExtracted = extracted.stream().mapToInt(ItemStack::getCount).sum();
			if (numExtracted != ingredient.count())
				return new SizedIngredient(ingredient.ingredient(), ingredient.count() - numExtracted);
		}

		return null;
	}

	private static List<SizedIngredient> convertSpiritsToIngredients(List<SpiritIngredient> ingredients) {
		return ingredients.stream()
			.map(ingredient -> new SizedIngredient(Ingredient.of(ingredient.getItems()), ingredient.getCount()))
			.toList();
	}

	private static List<ItemStack> convertSpiritsToItems(List<SpiritIngredient> ingredients) {
		return ingredients.stream().map(SpiritIngredient::getStack).toList();
	}

	/**
	 * Attempts to extract an ingredient from an inventory.
	 */
	public static NonNullList<ItemStack> extractIngredient(IItemHandler inventory, Predicate<ItemStack> ingredient, int count, boolean simulate) {
		int leftToExtract = count;
		NonNullList<ItemStack> extracted = NonNullList.create();
		int[] toExtract = new int[inventory.getSlots()];
		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.extractItem(i, leftToExtract, true);
			if (ingredient.test(stack)) {
				extracted.add(stack);
				toExtract[i] = stack.getCount();
				leftToExtract -= stack.getCount();

				if (leftToExtract <= 0) {
					if (!simulate) {
						extracted.clear();
						for (int slot = 0; slot < toExtract.length; slot++) {
							extracted.add(inventory.extractItem(slot, toExtract[slot], false));
						}
					}
					return extracted;
				}
			}
		}

		return extracted;
	}

	public static SizedIngredient getNextIngredientToTake(SpiritInfusionRecipe recipe, IItemHandlerModifiable consumedItems) {
		IItemHandler frozen = frozenCopy(consumedItems);

		return getFirstMissingIngredient(frozen, recipe.extraIngredients);
	}

	/**
	 * Creates a copy of every item the inventory will allow us to extract.
	 */
	private static IItemHandlerModifiable frozenCopy(IItemHandler inventory) {
		ItemStackHandler handler = new ItemStackHandler(inventory.getSlots());
		for (int i = 0; i < inventory.getSlots(); i++) {
			handler.setStackInSlot(i, inventory.extractItem(i, 64, true));
		}
		return handler;
	}

	/**
	 * Returns a recipe ranking for a given spirit infusion recipe, or null if the recipe does not match.
	 */
	public static Ranking rankRecipe(SpiritInfusionRecipe recipe, ItemStack inputItem, IItemHandlerModifiable spiritContainer, IItemHandlerModifiable pedestalItems, IItemHandlerModifiable consumedItems) {
		SpiritBasedRecipeInput input = new SpiritBasedRecipeInput(inputItem, convertSpiritsToItems(recipe.spirits));
		if (!recipe.matches(input, null))
			return null;

		int inputCount = recipe.ingredient.count();

		Extraction spiritRanking = simulateExtraction(spiritContainer, convertSpiritsToIngredients(recipe.spirits));
		if (spiritRanking == null)
			return null;

		Extraction itemRanking = simulateExtraction(new CombinedInvWrapper(pedestalItems, consumedItems), recipe.extraIngredients);
		if (itemRanking == null)
			return null;

		return new Ranking(inputCount, spiritRanking.numberOfStacks, spiritRanking.numberOfItems, itemRanking.numberOfStacks, itemRanking.numberOfItems);
	}

	public static IItemHandlerModifiable createPedestalInventoryCapture(List<IMalumSpecialItemAccessPoint> pedestals) {
		return new CombinedInvWrapper(pedestals.stream().map(IMalumSpecialItemAccessPoint::getSuppliedInventory).toArray(IItemHandlerModifiable[]::new));
	}

	public static List<IMalumSpecialItemAccessPoint> capturePedestals(Level level, BlockPos pos) {
		return capturePedestals(level, pos, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
	}
	public static List<IMalumSpecialItemAccessPoint> capturePedestals(Level level, BlockPos pos, int xRange, int yRange, int zRange) {
		return BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, pos, xRange, yRange, zRange).stream()
			.sorted(Comparator.comparingDouble(it -> -pos.distSqr(it.getAccessPointBlockPos())))
			.collect(Collectors.toList());
	}
}
