package com.sammy.malum.registry.client;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.void_favor.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityJoinLevelEvent;
import net.minecraft.client.player.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;

import static com.sammy.malum.visual_effects.ScreenParticleEffects.VoidTransmutableParticleEffect.*;

public class ParticleEmitterRegistry {

    public static boolean registeredVoidParticleEmitters = false;


    public static void addParticleEmitters(EntityJoinLevelEvent event) {
        if (!registeredVoidParticleEmitters) {
            if (event.getEntity() instanceof AbstractClientPlayer player) {
                final List<FavorOfTheVoidRecipe> recipes = LodestoneRecipeType.getRecipes(player.level(), RecipeTypeRegistry.VOID_FAVOR.get());
                if (!recipes.isEmpty()) {
                    for (FavorOfTheVoidRecipe recipe : recipes) {
                        for (ItemStack item : recipe.ingredient.getItems()) {
                            ParticleEmitterHandler.registerItemParticleEmitter(item.getItem(), INSTANCE);
                        }
                    }
                    registeredVoidParticleEmitters = true;
                }
            }
        }
    }
}
