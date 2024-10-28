package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.container.*;
import com.sammy.malum.common.entity.spirit.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.mixin.AccessorEvent;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import team.lodestar.lodestone.helpers.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

import static net.minecraft.world.entity.EquipmentSlot.*;

public class SpiritHarvestHandler {

    public static void shatterSoul(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntity();
        LivingEntity attacker = null;
        if (event.getSource().getEntity() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getLastHurtByMob();
        }
        if (attacker == null && source.is(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE)) {
            spawnSpirits(event.getEntity());
            return;
        }
        if (attacker != null) {
            ItemStack stack = SoulDataHandler.getSoulHunterWeapon(source, attacker);
            if (!(target instanceof Player)) {
                SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(target).soulData;
                if (CommonConfig.SOULLESS_SPAWNERS.getConfigValue() && soulData.spawnerSpawned) {
                    return;
                }
                if (soulData.exposedSoulDuration > 0 && !soulData.soulless) {
                    spawnSpirits(target, attacker, stack);
                    soulData.soulless = true;
                }
            }
        }
    }

    public static void modifyDroppedItems(LivingDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }
        LivingEntity entityLiving = event.getEntity();
        MalumLivingEntityDataCapability capability = MalumLivingEntityDataCapability.getCapability(entityLiving);
        if (capability.soulsToApplyToDrops != null) {
            getSpiritData(entityLiving).ifPresent(spiritData -> {
                Ingredient spiritItem = spiritData.spiritItem;
                if (spiritItem != null) {
                    for (ItemEntity itemEntity : event.getDrops()) {
                        if (spiritItem.test(itemEntity.getItem())) {
                            MalumItemDataCapability.getCapabilityOptional(itemEntity).ifPresent((e) -> {
                                e.soulsToDrop = capability.soulsToApplyToDrops.stream().map(ItemStack::copy).collect(Collectors.toList());
                                e.attackerForSouls = capability.killerUUID;
                                e.totalSoulCount = spiritData.totalSpirits;
                            });
                            itemEntity.setNeverPickUp();
                            itemEntity.age = itemEntity.lifespan - 20;
                            itemEntity.setNoGravity(true);
                            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().multiply(1, 0.5, 1));
                        }
                    }
                }
            });
        }
    }

    public static void shatterItem(ItemExpireEvent event) {
        if (((AccessorEvent)event).malum$isCancelled()) {
            return;
        }

        ItemEntity entity = event.getEntity();
        if (entity.level() instanceof ServerLevel level) {
            MalumItemDataCapability.getCapabilityOptional(entity).ifPresent((e) -> {
                LivingEntity attacker = null;
                if (e.attackerForSouls != null) {
                    if (level.getEntity(e.attackerForSouls) instanceof LivingEntity living) {
                        attacker = living;
                    }
                }

                if (e.soulsToDrop != null) {
                    List<ItemStack> stacks = new ArrayList<>();
                    for (int i = 0; i < entity.getItem().getCount(); i++) {
                        e.soulsToDrop.stream().map(ItemStack::copy).forEach(stacks::add);
                    }
                    createSpiritEntities(level, stacks, entity.position(), e.totalSoulCount, attacker);
                }
            });
        }
    }

    public static void pickupSpirit(LivingEntity collector, ItemStack stack) {
        if (collector instanceof Player player) {
            AttributeInstance instance = player.getAttribute(AttributeRegistry.ARCANE_RESONANCE);
            ItemHelper.getEventResponders(collector).forEach(s -> {
                if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                    eventItem.pickupSpirit(collector, instance != null ? instance.getValue() : 0);
                }
            });
            for (NonNullList<ItemStack> playerInventory : player.getInventory().compartments) {
                for (ItemStack item : playerInventory) {
                    if (item.getItem() instanceof SpiritPouchItem) {
                        ItemInventory inventory = SpiritPouchItem.getInventory(item);
                        ItemStack result = inventory.addItem(stack);
                        if (result.isEmpty()) {
                            Level level = player.level();
                            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((level.random.nextFloat() - level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            if (player.containerMenu instanceof SpiritPouchContainer pouchMenu) {
                                pouchMenu.update(inventory);
                            }
                            return;
                        }
                    }
                }
            }
        }
        ItemHelper.giveItemToEntity(collector, stack);
    }

    public static void spawnSpirits(LivingEntity target) {
        spawnSpirits(getSpiritDropsRaw(target), target, null);
    }

    public static void spawnSpirits(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
        spawnSpirits(getSpawnedSpirits(target, attacker, harvestStack), target, attacker);
    }

    public static void spawnSpirits(List<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        getSpiritData(target).ifPresent(data -> {
            if (data.spiritItem != null) {
                MalumLivingEntityDataCapability.getCapabilityOptional(target).ifPresent((e) -> {
                    e.soulsToApplyToDrops = spirits;
                    if (attacker != null) {
                        e.killerUUID = attacker.getUUID();
                    }
                });
            } else {
                spawnItemsAsSpirits(spirits, target, attacker);
            }
        });
    }

    public static void spawnItemAsSpirit(ItemStack spirit, LivingEntity target, LivingEntity attacker) {
        spawnItemsAsSpirits(List.of(spirit), target, attacker);
    }

    public static void spawnItemsAsSpirits(Collection<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        createSpiritEntities(target.level(), spirits, target.position().add(0, target.getBbHeight() / 2f, 0), spirits.stream().mapToInt(ItemStack::getCount).sum(), attacker);
    }

    private static void createSpiritEntities(Level level, Collection<ItemStack> spirits, Vec3 position, float totalCount, @Nullable LivingEntity attacker) {
        if (attacker == null) {
            attacker = level.getNearestPlayer(position.x, position.y, position.z, 8, e -> true);
        }
        final UUID attackerUUID = attacker == null ? null : attacker.getUUID();
        float speed = 0.15f + 0.25f / (totalCount + 1);
        var random = level.random;
        for (ItemStack stack : spirits) {
            if (stack.isEmpty()) {
                continue;
            }
            boolean noFancySpirits = CommonConfig.NO_FANCY_SPIRITS.getConfigValue();
            for (int j = 0; j < stack.getCount(); j++) {
                if (noFancySpirits) {
                    ItemEntity itemEntity = new ItemEntity(level, position.x, position.y, position.z, stack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(
                            Mth.nextFloat(random, -0.1F, 0.1F),
                            Mth.nextFloat(random, 0.25f, 0.5f),
                            Mth.nextFloat(random, -0.1F, 0.1F));
                    level.addFreshEntity(itemEntity);
                    continue;
                }
                SpiritItemEntity entity = new SpiritItemEntity(level, attackerUUID, ItemHelper.copyWithNewCount(stack, 1),
                        position.x,
                        position.y,
                        position.z,
                        RandomHelper.randomBetween(random, -speed, speed),
                        RandomHelper.randomBetween(random, 0.05f, 0.06f),
                        RandomHelper.randomBetween(random, -speed, speed));
                level.addFreshEntity(entity);
            }
        }
        level.playSound(null, position.x, position.y, position.z, SoundRegistry.SOUL_SHATTER.get(), SoundSource.PLAYERS, 1.0F, 0.7f + random.nextFloat() * 0.4f);
    }

    public static List<ItemStack> getSpawnedSpirits(LivingEntity entity, LivingEntity attacker, ItemStack harvestStack) {
        return getSpiritData(entity).map(data -> applySpiritLootBonuses(getSpiritDropsRaw(data), attacker, harvestStack)).orElse(Collections.emptyList());
    }

    public static List<ItemStack> applySpiritLootBonuses(List<ItemStack> spirits, LivingEntity attacker, ItemStack weapon) {
        if (spirits.isEmpty()) {
            return spirits;
        }
        int spiritBonus = 0;
        if (attacker.getAttribute(AttributeRegistry.SPIRIT_SPOILS) != null) {
            spiritBonus += attacker.getAttributeValue(AttributeRegistry.SPIRIT_SPOILS);
        }
        if (!weapon.isEmpty()) {
            HolderGetter<Enchantment> enchantmentLookup = attacker.level().registryAccess()
                    .asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT);
            final int spiritPlunder = weapon.getEnchantmentLevel(enchantmentLookup.getOrThrow(EnchantmentRegistry.SPIRIT_PLUNDER));
            if (spiritPlunder > 0) {
                weapon.hurtAndBreak(spiritPlunder, attacker, MAINHAND);
            }
            spiritBonus += spiritPlunder;
        }
        for (int i = 0; i < spiritBonus; i++) {
            int random = attacker.getRandom().nextInt(spirits.size());
            spirits.get(random).grow(1);
        }
        return spirits;
    }

    public static List<ItemStack> getSpiritDropsRaw(LivingEntity entity) {
        return getSpiritData(entity).map(SpiritHarvestHandler::getSpiritDropsRaw).orElse(Collections.emptyList());
    }

    public static List<ItemStack> getSpiritDropsRaw(EntitySpiritDropData data) {
        return data != null ? data.dataEntries.stream().map(SpiritIngredient::getStack).collect(Collectors.toList()) : (Collections.emptyList());
    }

    public static Optional<EntitySpiritDropData> getSpiritData(LivingEntity entity) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (SpiritDataReloadListener.HAS_NO_DATA.contains(key))
            return Optional.empty();

        EntitySpiritDropData spiritData = SpiritDataReloadListener.SPIRIT_DATA.get(key);
        if (spiritData != null)
            return Optional.of(spiritData);

        if (entity.isPassenger() || entity.isVehicle())
            return Optional.of(SpiritDataReloadListener.DEFAULT_BOSS_SPIRIT_DATA);

        if (!CommonConfig.USE_DEFAULT_SPIRIT_VALUES.getConfigValue())
            return Optional.empty();

        return switch (entity.getType().getCategory()) {
            case MONSTER -> Optional.of(SpiritDataReloadListener.DEFAULT_MONSTER_SPIRIT_DATA);
            case CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_CREATURE_SPIRIT_DATA);
            case AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_AMBIENT_SPIRIT_DATA);
            case AXOLOTLS -> Optional.of(SpiritDataReloadListener.DEFAULT_AXOLOTL_SPIRIT_DATA);
            case UNDERGROUND_WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA);
            case WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_CREATURE_SPIRIT_DATA);
            case WATER_AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_AMBIENT_SPIRIT_DATA);
            default -> Optional.empty();
        };
    }

    public static MalumSpiritType getSpiritType(String spirit) {
        return SpiritTypeRegistry.SPIRITS.getOrDefault(spirit, SpiritTypeRegistry.SACRED_SPIRIT);
    }
}