package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.sammy.malum.data.recipe.MalumVanillaRecipeReplacements;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(VanillaRecipeProvider.class)
public class VanillaRecipeProviderMixin {

    @Unique
    private static Class<?> lastInvoker = VanillaRecipeProvider.class;

    @Inject(method = "buildRecipes", at = @At("HEAD"))
    private void captureClass(RecipeOutput recipeOutput, CallbackInfo ci) {
        lastInvoker = this.getClass();
    }

    @Mixin(RecipeBuilder.class)
    public static class RecipeBuilderMixin {

        @WrapMethod(method = "save(Lnet/minecraft/data/recipes/RecipeOutput;)V")
        private void save(RecipeOutput recipeOutput, Operation<Void> original) {
            if (lastInvoker == MalumVanillaRecipeReplacements.class) {
                RecipeBuilder modified = MalumVanillaRecipeReplacements.enhance(
                        (RecipeBuilder) this
                );
                try { modified.save(recipeOutput); } catch (NullPointerException e) {
                    original.call(recipeOutput);
                }
            }
        }

    }
}
