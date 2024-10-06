package com.sammy.malum.mixin;

import com.sammy.malum.common.effect.aura.AqueousAura;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import team.lodestar.lodestone.helpers.*;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyArg(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"), index = 1)
    private AABB malum$aiStep(AABB aabb) {
        Player player = (Player) (Object) this;
        return AqueousAura.growBoundingBox(player, aabb);
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private DamageSource malum$attack(DamageSource pSource) {
        Player player = (Player) (Object) this;
        if (player.getMainHandItem().getItem() instanceof MalumScytheItem) {
            return DamageTypeHelper.create(player.level(), DamageTypeRegistry.SCYTHE_MELEE, player);
        }
        return pSource;
    }
}
