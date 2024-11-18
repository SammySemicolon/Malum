package com.sammy.malum.core.events;

import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.common.effect.aura.*;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.enchantment.scythe.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.cosmetic.curios.*;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.curios.sets.misc.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.*;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.registry.common.*;

import java.util.*;
import java.util.function.*;

@Mod.EventBusSubscriber
public class RuntimeEvents {

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        MalumPlayerDataCapability.attachPlayerCapability(event);
        MalumLivingEntityDataCapability.attachEntityCapability(event);
        MalumItemDataCapability.attachItemCapability(event);
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        MalumPlayerDataCapability.playerJoin(event);
        CurioTokenOfGratitude.giveItem(event);
        SoulDataHandler.updateAi(event);
        TetraCompat.onEntityJoin(event);

        if (event.getEntity() instanceof LivingEntity living) {
            var attributes = new ArrayList<>(List.of(
                    LodestoneAttributeRegistry.MAGIC_RESISTANCE, LodestoneAttributeRegistry.MAGIC_PROFICIENCY,
                    AttributeRegistry.SCYTHE_PROFICIENCY, AttributeRegistry.SOUL_WARD_RECOVERY_RATE, AttributeRegistry.ARCANE_RESONANCE
            ));

            for (RegistryObject<Attribute> attribute : attributes) {
                final AttributeInstance instance = living.getAttribute(attribute.get());
                if (instance != null) {
                    if (instance.getBaseValue() <= 0.01) {
                        instance.setBaseValue(1.0);
                    }
                }
            }
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
    public static void onEntityJoin(MobSpawnEvent.PositionCheck event) {
        SoulDataHandler.markAsSpawnerSpawned(event);
    }

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        CorruptedAerialAura.onEntityJump(event);
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        AscensionEffect.onEntityFall(event);
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
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        SoulDataHandler.livingTick(event);
        MalignantConversionHandler.checkForAttributeChanges(event);
        TouchOfDarknessHandler.entityTick(event);
        CurioWatcherNecklace.entityTick(event);
        CurioHiddenBladeNecklace.entityTick(event);
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
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ReserveStaffChargeHandler.recoverStaffCharges(event);
        SoulWardHandler.playerTick(event);
    }

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        SpiritDataReloadListener.register(event);
        ReapingDataReloadListener.register(event);
        RitualRecipeReloadListener.register(event);
        MalignantConversionReloadListener.register(event);
    }

    @SubscribeEvent
    public static void addItemAttributes(ItemAttributeModifierEvent event) {
        HauntedEnchantment.addMagicDamage(event);
        AnimatedEnchantment.addAttackSpeed(event);
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
    public static void onHurt(LivingHurtEvent event) {
        MalumAttributeEventHandler.processAttributes(event);
        SoulDataHandler.exposeSoul(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateHurt(LivingHurtEvent event) {
        SoulWardHandler.livingHurt(event);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        EsotericReapingHandler.onDeath(event);
        SpiritHarvestHandler.spawnSpiritsOnDeath(event);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        SpiritHarvestHandler.primeItemForShatter(event);
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

