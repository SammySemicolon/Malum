package com.sammy.malum.registry.client;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.void_favor.*;
import net.minecraft.client.player.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import team.lodestar.lodestone.handlers.screenparticle.*;

import java.util.*;

import static com.sammy.malum.visual_effects.ScreenParticleEffects.VoidTransmutableParticleEffect.*;

@Mod.EventBusSubscriber(modid= MalumMod.MALUM, bus= Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ParticleEmitterRegistry {

    public static boolean registeredVoidParticleEmitters = false;

    @SubscribeEvent
    public static void addParticleEmitters(EntityJoinLevelEvent event) {
        if (!registeredVoidParticleEmitters) {
            if (event.getEntity() instanceof AbstractClientPlayer player) {
                final List<FavorOfTheVoidRecipe> recipes = FavorOfTheVoidRecipe.getRecipes(player.level());
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
