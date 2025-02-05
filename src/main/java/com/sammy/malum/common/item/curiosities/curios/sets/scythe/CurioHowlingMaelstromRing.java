package com.sammy.malum.common.item.curiosities.curios.sets.scythe;

import com.google.common.collect.*;
import com.sammy.malum.*;
import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public class CurioHowlingMaelstromRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioHowlingMaelstromRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("rebound_maelstrom"));
        consumer.accept(ComponentHelper.negativeCurioEffect("longer_rebound_cooldown"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SCYTHE_PROFICIENCY,
                new AttributeModifier(MalumMod.malumPath("howling_maelstrom_ring"), 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }

    public static void handleMaelstrom(ServerLevel serverLevel, LivingEntity scytheOwner, AbstractScytheProjectileEntity entity) {
        if (serverLevel.getGameTime() % 5L == 0) {
            boolean dealtDamage = false;
            final AABB aabb = entity.getBoundingBox().inflate(2);
            float damage = entity.damage * 0.2f;
            float magicDamage = entity.magicDamage * 0.2f;
            for (Entity target : serverLevel.getEntities(entity, aabb, t -> maelstromCanHitEntity(scytheOwner, t))) {
                var damageSource = DamageTypeHelper.create(serverLevel, DamageTypeRegistry.SCYTHE_MAELSTROM, entity, scytheOwner);
                target.invulnerableTime = 0;
                boolean success = target.hurt(damageSource, damage);
                if (success && target instanceof LivingEntity livingentity) {
                    if (entity.magicDamage > 0) {
                        if (!livingentity.isDeadOrDying()) {
                            livingentity.invulnerableTime = 0;
                            livingentity.hurt(DamageTypeHelper.create(serverLevel, DamageTypeRegistry.VOODOO, entity, scytheOwner), magicDamage);
                        }
                    }
                    dealtDamage = true;
                }
            }
            if (dealtDamage) {
                entity.returnTimer += 1;
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.7f));
            }
        }
    }

    protected static boolean maelstromCanHitEntity(LivingEntity scytheOwner, Entity pTarget) {
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else {
            return pTarget != scytheOwner && !scytheOwner.isPassengerOfSameVehicle(pTarget);
        }
    }
}