package com.sammy.malum.core.events;

import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.common.effect.aura.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.cosmetic.curios.*;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.curios.sets.misc.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.*;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.*;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.ExplosionEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityJoinLevelEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.*;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerInteractEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.tick.EntityTickEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.tick.PlayerTickEvent;
import io.github.fabricators_of_create.porting_lib.event.common.ExplosionEvents;
import net.minecraft.core.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.events.LodestoneItemEvent;

import java.util.List;


public class RuntimeEvents {

    public static void register(){
        EntityJoinLevelEvent.EVENT.register(RuntimeEvents::onEntityJoin);
        PlayerInteractEvent.LeftClickBlock.EVENT.register(RuntimeEvents::playerLeftClick);
        PlayerInteractEvent.LeftClickBlock.EVENT.register(RuntimeEvents::playerLeftClick);
        LivingFallEvent.EVENT.register(RuntimeEvents::onEntityFall);
        LivingEvent.LivingJumpEvent.EVENT.register(RuntimeEvents::onEntityJump);
        LivingChangeTargetEvent.EVENT.register(RuntimeEvents::onLivingTarget);
        LivingEvent.LivingVisibilityEvent.EVENT.register(RuntimeEvents::onLivingVisibility);
        EntityTickEvent.Pre.EVENT.register(RuntimeEvents::onLivingTick);
        PlayerEvent.BreakSpeed.EVENT.register(RuntimeEvents::onPlayerBreakSpeed);
        PlayerTickEvent.Post.EVENT.register(RuntimeEvents::onPlayerTick);
        MobEffectEvent.Applicable.EVENT.register(RuntimeEvents::isPotionApplicable);
        MobEffectEvent.Added.EVENT.register(RuntimeEvents::onPotionApplied);
        LivingEntityUseItemEvent.Start.EVENT.register(RuntimeEvents::onStartUsingItem);
        LivingDamageEvent.DAMAGE.register(RuntimeEvents::onHurt);
        LivingDeathEvent.EVENT.register(RuntimeEvents::onDeath);
        LivingDropsEvent.EVENT.register(RuntimeEvents::onDrops);
        LodestoneItemEvent.EXPIRE.register(RuntimeEvents::onItemExpire);
        ExplosionEvents.DETONATE.register(RuntimeEvents::onExplosionDetonate);
    }

    public static void onEntityJoin(EntityJoinLevelEvent event) {
        CurioTokenOfGratitude.giveItem(event);
        SoulDataHandler.entityJoin(event);
    }


    public static void playerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof SpiritJarBlock jarBlock) {
            Player player = event.getEntity();
            BlockHitResult target = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (target.getType() == HitResult.Type.BLOCK && target.getBlockPos().equals(pos) && target.getDirection().getAxis() == Direction.Axis.X) {
                if (player.isCreative()) {
                    event.setCanceled(jarBlock.handleAttack(level, pos, player));
                }
            }
        }
    }


    public static void onSpawnerSpawned(LivingEntity living, BaseSpawner baseSpawner) {
        SoulDataHandler.markAsSpawnerSpawned(living, baseSpawner);
    }


    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        CorruptedAerialAura.onEntityJump(event);
    }


    public static void onEntityFall(LivingFallEvent event) {
        AscensionEffect.onEntityFall(event);
        CorruptedAerialAura.onEntityFall(event);
    }


    public static void onLivingTarget(LivingChangeTargetEvent event) {
        SoulDataHandler.preventTargeting(event);
    }


    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        CurioHarmonyNecklace.preventDetection(event);
    }


    public static void onLivingTick(EntityTickEvent.Pre event) {
        SoulDataHandler.entityTick(event);
        SoulWardHandler.recoverSoulWard(event);
        MalignantConversionHandler.entityTick(event);
        TouchOfDarknessHandler.entityTick(event);
        CurioWatcherNecklace.entityTick(event);
        CurioHiddenBladeNecklace.entityTick(event);
    }


    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        InfernalAura.increaseDigSpeed(event);
        RuneFervorItem.increaseDigSpeed(event);
    }


    public static void onPlayerTick(PlayerTickEvent.Post event) {
        StaffAbilityHandler.recoverStaffCharges(event);
    }


    public static void isPotionApplicable(MobEffectEvent.Applicable event) {
        GluttonyEffect.canApplyPotion(event);
    }


    public static void onPotionApplied(MobEffectEvent.Added event) {
        RuneTwinnedDurationItem.onPotionApplied(event);
        RuneAlimentCleansingItem.onPotionApplied(event);
    }

    public static void onPotionExpired(MobEffectEvent.Expired event) {
    }


    public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
        CurioVoraciousRing.accelerateEating(event);
    }


    public static void onHurt(LivingDamageEvent event) {
        SoulWardHandler.shieldPlayer(event);
        MalumAttributeEventHandler.processAttributes(event);
        SoulDataHandler.exposeSoul(event);
    }


    public static void onDeath(LivingDeathEvent event) {
        SoulHarvestHandler.onDeath(event);
    }


    public static void onDrops(LivingDropsEvent event) {
        EnsouledItemHarvestHandler.onDrops(event);
    }


    private static int onItemExpire(ItemEntity itemEntity, ItemStack itemStack) {
        EnsouledItemHarvestHandler.onItemExpire(itemEntity, itemStack);
        return -1;
    }


    private static void onExplosionDetonate(Level level, Explosion explosion, List<Entity> entities, double v) {
        CurioProspectorBelt.processExplosion(level, explosion, entities);
        NitrateExplosion.processExplosion(level, explosion, entities);
    }
}

