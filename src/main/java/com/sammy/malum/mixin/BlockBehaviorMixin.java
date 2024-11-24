package com.sammy.malum.mixin;

import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioProspectorBelt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {

    @Unique
    Explosion malum$explosion;

    @Inject(method = "onExplosionHit", at = @At(value = "HEAD"))
    private void malum$getBlockDrops$cacheExplosion(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer, CallbackInfo ci) {
        malum$explosion = explosion;
    }
    @ModifyArg(method = "onExplosionHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDrops(Lnet/minecraft/world/level/storage/loot/LootParams$Builder;)Ljava/util/List;"))
    private LootParams.Builder malum$getBlockDrops(LootParams.Builder builder) {
        if (malum$explosion == null) {
            return builder;
        }
        return CurioProspectorBelt.applyFortune(malum$explosion.getIndirectSourceEntity(), builder);
    }
}
