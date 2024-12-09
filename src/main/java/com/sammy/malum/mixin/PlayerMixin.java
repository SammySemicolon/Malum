package com.sammy.malum.mixin;

import com.sammy.malum.common.effect.aura.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @ModifyArg(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"))
    private AABB malum$aiStep(AABB aabb) {
        Player player = (Player) (Object) this;
        return AqueousAura.growBoundingBox(player, aabb);
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private DamageSource malum$attack(DamageSource pSource) {
        Player player = (Player) (Object) this;
        return MalumScytheItem.replaceDamageSource(player, pSource);
    }
}
