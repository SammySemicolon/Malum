package com.sammy.malum.core.events;

import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.common.effect.aura.*;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.cosmetic.curios.*;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.curios.sets.misc.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.*;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class RuntimeEvents {
/*
    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        MalumPlayerDataCapability.attachPlayerCapability(event);
        MalumLivingEntityDataCapability.attachEntityCapability(event);
        MalumItemDataCapability.attachItemCapability(event);
    }


 */
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        MalumPlayerDataCapability.playerJoin(event);
        CurioTokenOfGratitude.giveItem(event);
        SoulDataHandler.updateAi(event);
        if (TetraCompat.LOADED) {
            TetraCompat.LoadedOnly.fireArrow(event);
        }
    }


    @SubscribeEvent
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

    @SubscribeEvent
    public static void anvilUpdate(AnvilUpdateEvent event) {
        CatalystFlingerItem.anvilUpdate(event);
    }


    @SubscribeEvent
    public static void onEntityJoin(MobSpawnEvent.PositionCheck event) {
        SoulDataHandler.markAsSpawnerSpawned(event);
    }

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        CorruptedAerialAura.onEntityJump(event);
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        CorruptedAerialAura.onEntityFall(event);
    }

    @SubscribeEvent
    public static void onLivingTarget(LivingChangeTargetEvent event) {
        SoulDataHandler.preventTargeting(event);
    }

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        CurioHarmonyNecklace.preventDetection(event);
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent event) {
        SoulDataHandler.manageSoul(event);
        MalignantConversionHandler.checkForAttributeChanges(event);
        TouchOfDarknessHandler.entityTick(event);
        CurioWatcherNecklace.entityTick(event);
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        MalumLivingEntityDataCapability.syncEntityCapability(event);
        MalumPlayerDataCapability.syncPlayerCapability(event);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        MalumPlayerDataCapability.playerClone(event);
    }

    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        InfernalAura.increaseDigSpeed(event);
        RuneFervorItem.increaseDigSpeed(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        ReserveStaffChargeHandler.recoverStaffCharges(event);
        SoulWardHandler.recoverSoulWard(event);
    }

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        SpiritDataReloadListener.register(event);
        ReapingDataReloadListener.register(event);
        RitualRecipeReloadListener.register(event);
        MalignantConversionReloadListener.register(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ReboundEnchantment.onRightClickItem(event);
    }

    @SubscribeEvent
    public static void onSwapEquipment(LivingEquipmentChangeEvent event) {
    }

    @SubscribeEvent
    public static void addItemAttributes(ItemAttributeModifierEvent event) {
        HauntedEnchantment.addMagicDamage(event);
    }

    @SubscribeEvent
    public static void isPotionApplicable(MobEffectEvent.Applicable event) {
        GluttonyEffect.canApplyPotion(event);
    }

    @SubscribeEvent
    public static void onPotionApplied(MobEffectEvent.Added event) {
        RuneTwinnedDurationItem.onPotionApplied(event);
        RuneAlimentCleansingItem.onPotionApplied(event);
    }
    @SubscribeEvent
    public static void onPotionExpired(MobEffectEvent.Expired event) {
    }

    @SubscribeEvent
    public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
        CurioVoraciousRing.accelerateEating(event);
    }

    @SubscribeEvent
    public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event) {
        CurioGruesomeConcentrationRing.finishEating(event);
        CurioVoraciousRing.finishEating(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingDamageEvent.Post event) {
        SoulDataHandler.exposeSoul(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingDamageEvent.Pre event) {
        MalumAttributeEventHandler.processAttributes(event);
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateHurt(LivingDamageEvent.Post event) {
        SoulWardHandler.shieldPlayer(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateDamage(LivingDamageEvent.Pre event) {
        WickedIntentEffect.removeWickedIntent(event);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        EsotericReapingHandler.tryCreateReapingDrops(event);
        SpiritHarvestHandler.shatterSoul(event);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        SpiritHarvestHandler.modifyDroppedItems(event);
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        SpiritHarvestHandler.shatterItem(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        CurioProspectorBelt.processExplosion(event);
        NitrateExplosion.processExplosion(event);
    }
}

