package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;

public class MalumRuneworkingRecipes {

    protected static void buildRecipes(RecipeOutput recipeOutput) {
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_IDLE_RESTORATION.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.SACRED_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_BOLSTERING.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.SACRED_SPIRIT, 16)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_CULLING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.WICKED_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.WICKED_SPIRIT, 16)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_REINFORCEMENT.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.ARCANE_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_SPELL_MASTERY.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.ARCANE_SPIRIT, 16)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_VOLATILE_DISTORTION.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_HERETIC.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_DEXTERITY.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AERIAL_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_UNNATURAL_STAMINA.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AERIAL_SPIRIT, 16)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AQUEOUS_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_TWINNED_DURATION.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AQUEOUS_SPIRIT, 16)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_REACTIVE_SHIELDING.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.EARTHEN_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_TOUGHNESS.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.EARTHEN_SPIRIT, 16)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_FERVOR.get(), 1)
                .setPrimaryInput(ItemRegistry.TAINTED_ROCK.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.INFERNAL_SPIRIT, 16)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_IGNEOUS_SOLACE.get(), 1)
                .setPrimaryInput(ItemRegistry.NULL_SLATE.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.INFERNAL_SPIRIT, 16)
                .save(recipeOutput);


        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_MOTION.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AERIAL_SPIRIT, 32)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_AETHER.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AERIAL_SPIRIT, 32)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_LOYALTY.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AQUEOUS_SPIRIT, 32)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_SEAS.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.AQUEOUS_SPIRIT, 32)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_WARDING.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.EARTHEN_SPIRIT, 32)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_ARENA.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.EARTHEN_SPIRIT, 32)
                .save(recipeOutput);

        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_HASTE.get(), 1)
                .setPrimaryInput(ItemRegistry.RUNEWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.INFERNAL_SPIRIT, 32)
                .save(recipeOutput);
        new RunicWorkbenchRecipeBuilder(ItemRegistry.RUNE_OF_THE_HELLS.get(), 1)
                .setPrimaryInput(ItemRegistry.SOULWOOD_PLANKS.get(), 4)
                .setSecondaryInput(SpiritTypeRegistry.INFERNAL_SPIRIT, 32)
                .save(recipeOutput);

    }
}
