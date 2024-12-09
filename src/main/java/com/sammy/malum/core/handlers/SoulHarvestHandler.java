package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.common.entity.spirit.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;

import javax.annotation.*;
import java.util.*;

import static net.minecraft.world.entity.EquipmentSlot.*;
import static team.lodestar.lodestone.helpers.RandomHelper.*;

public class SoulHarvestHandler {

    public static void onDeath(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        var target = event.getEntity();
        if (target instanceof Player) {
            return;
        }
        var data = target.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO);
        if (data.isSoulless() || (CommonConfig.SOULLESS_SPAWNERS.getConfigValue() && data.isSpawnerSpawned())) {
            return;
        }
        var source = event.getSource();
        var attacker = source.getEntity() instanceof LivingEntity living ? living : target.getLastHurtByMob();
        if (data.shouldDropSpirits()) {
            var itemAsSoul = EntitySpiritDropData.getSpiritData(target).map(s -> s.itemAsSoul).orElse(null);
            if (itemAsSoul == null) {
                spawnSpirits(target, attacker, source);
            } else {
                var uuid = attacker != null ? attacker.getUUID() : null;
                target.setData(AttachmentTypeRegistry.CACHED_SPIRIT_DROPS, new CachedSpiritDropsData(getSpiritDrops(target, attacker, source), uuid));
            }
            dropSpiritInfusedDrops(target);
            dropEncyclopediaArcana(target, attacker);
            data.setSoulless(true);
        }
    }

    public static void dropSpiritInfusedDrops(LivingEntity target) {
        List<ReapingDataReloadListener.MalumReapingDropsData> data = ReapingDataReloadListener.REAPING_DATA.get(BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()));
        if (data != null) {
            for (ReapingDataReloadListener.MalumReapingDropsData dropData : data) {
                Level level = target.level();
                var random = level.random;
                if (random.nextFloat() < dropData.chance) {
                    var possibleDrops = dropData.drop.getItems();
                    var stack = ItemHelper.copyWithNewCount(possibleDrops[random.nextInt(possibleDrops.length)], Mth.nextInt(random, dropData.min, dropData.max));
                    var itemEntity = new ItemEntity(level, target.getX(), target.getY(), target.getZ(), stack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    level.addFreshEntity(itemEntity);
                }
            }
        }
    }

    public static void dropEncyclopediaArcana(LivingEntity target, LivingEntity attacker) {
        if (!CommonConfig.AWARD_CODEX_ON_KILL.getConfigValue()) {
            return;
        }
        if (target.getType().is(EntityTypeTags.UNDEAD) && attacker instanceof Player player) {
            var data = player.getData(AttachmentTypeRegistry.PROGRESSION_DATA);
            if (data.obtainedEncyclopedia) {
                return;
            }
            data.obtainedEncyclopedia = true;
            SoulHarvestHandler.spawnItemsAsSpirits(target, List.of(ItemRegistry.ENCYCLOPEDIA_ARCANA.get().getDefaultInstance()));
        }
    }

    public static void spawnSpirits(LivingEntity target) {
        spawnSpirits(target, null);
    }

    public static void spawnSpirits(LivingEntity target, @Nullable LivingEntity attacker) {
        spawnSpirits(target, attacker, null);
    }

    public static void spawnSpirits(LivingEntity target, @Nullable LivingEntity attacker, @Nullable DamageSource source) {
        spawnSpirits(target.level(), attacker, target.position().add(0, target.getBbHeight() / 2f, 0), getSpiritDrops(target, attacker, source));
    }

    public static void spawnItemsAsSpirits(LivingEntity target, Collection<ItemStack> spirits) {
        spawnItemsAsSpirits(target.level(), target.position().add(0, target.getBbHeight() / 2f, 0), spirits);
    }

    public static void spawnItemsAsSpirits(Level level, Vec3 position, Collection<ItemStack> spirits) {
        spawnSpirits(level, null, position, spirits);
    }

    public static void spawnSpirits(Level level, @Nullable LivingEntity attacker, Vec3 position, Collection<ItemStack> spirits) {
        boolean noFancySpirits = CommonConfig.NO_FANCY_SPIRITS.getConfigValue();
        if (attacker == null) {
            noFancySpirits = CommonConfig.NO_FANCY_SPIRITS_PLAYERLESS.getConfigValue();
            attacker = level.getNearestPlayer(position.x, position.y, position.z, 8, e -> true);
        }
        var random = level.random;
        for (ItemStack spirit : spirits) {
            if (spirit.isEmpty()) {
                continue;
            }
            for (int j = 0; j < spirit.getCount(); j++) {
                var stack = ItemHelper.copyWithNewCount(spirit, 1);
                if (noFancySpirits) {
                    var itemEntity = new ItemEntity(level, position.x, position.y, position.z, stack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(randomBetween(random, -0.1F, 0.1F), randomBetween(random, 0.25f, 0.5f), randomBetween(random, -0.1F, 0.1F));
                    level.addFreshEntity(itemEntity);
                    continue;
                }
                createSpiritEntity(level, attacker, stack, position);
            }
        }
        level.playSound(null, position.x, position.y, position.z, SoundRegistry.SOUL_SHATTER, SoundSource.PLAYERS, 1.0F, 0.7f + random.nextFloat() * 0.4f);
    }

    private static SpiritItemEntity createSpiritEntity(Level level, @Nullable LivingEntity spiritOwner, ItemStack stack, Vec3 position) {
        var random = level.getRandom();
        float speed = 0.3f + stack.getCount() * 0.05f;
        var entity = new SpiritItemEntity(level, spiritOwner == null ? null : spiritOwner.getUUID(), stack,
                position.x, position.y, position.z,
                randomBetween(random, -speed, speed), randomBetween(random, 0.05f, 0.06f), randomBetween(random, -speed, speed));
        level.addFreshEntity(entity);
        return entity;
    }

    public static List<ItemStack> getSpiritDrops(LivingEntity entity, LivingEntity attacker, @Nullable DamageSource source) {
        if (attacker == null || source == null) {
            return EntitySpiritDropData.getSpiritStacks(entity);
        }
        ItemStack stack = SoulDataHandler.getSoulHunterWeapon(source, attacker);
        return EntitySpiritDropData.getSpiritData(entity).map(data -> applySpiritLootBonuses(EntitySpiritDropData.getSpiritStacks(data), attacker, stack)).orElse(Collections.emptyList());
    }

    public static List<ItemStack> applySpiritLootBonuses(List<ItemStack> spirits, LivingEntity attacker, ItemStack stack) {
        if (spirits.isEmpty()) {
            return spirits;
        }
        int extra = 0;
        var spiritSpoils = AttributeRegistry.SPIRIT_SPOILS;
        if (attacker.getAttribute(spiritSpoils) != null) {
            extra += Mth.ceil(attacker.getAttributeValue(spiritSpoils));
        }
        if (!stack.isEmpty()) {
            int spiritPlunder = EnchantmentRegistry.getEnchantmentLevel(attacker.level(), EnchantmentRegistry.SPIRIT_PLUNDER, stack);
            if (spiritPlunder > 0) {
                stack.hurtAndBreak(spiritPlunder, attacker, MAINHAND);
            }
            extra += spiritPlunder;
        }
        for (int i = 0; i < extra; i++) {
            int random = attacker.getRandom().nextInt(spirits.size());
            spirits.get(random).grow(1);
        }
        return spirits;
    }


    public static void pickupSpirit(LivingEntity collector, ItemStack stack) {
        SoulHarvestHandler.triggerSpiritCollection(collector);
//        if (collector instanceof Player player) {
//            for (NonNullList<ItemStack> playerInventory : player.getInventory().compartments) {
//                //TODO: need AT for compartments
//                for (ItemStack item : playerInventory) {
//                    if (item.getItem() instanceof SpiritPouchItem) {
//                        var inventory = SpiritPouchItem.getInventory(item);
//                        ItemStack result = inventory.addItem(stack);
//                        if (result.isEmpty()) {
//                            var random = collector.getRandom();
//                            float pitch = ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F; //this kinda smells but we want it to match vanilla
//
//                            player.playSound(SoundEvents.ITEM_PICKUP, 0.2F, pitch);
//                            if (player.containerMenu instanceof SpiritPouchContainer pouchMenu) {
//                                pouchMenu.update(inventory);
//                            }
//                            return;
//                        }
//                    }
//                }
//            }
//        }
        ItemHelper.giveItemToEntity(collector, stack);
    }

    public static void triggerSpiritCollection(LivingEntity collector) {
        var collectionEvent = new CollectSpiritEvent(collector);
        var attribute = collector.getAttributeValue(AttributeRegistry.ARCANE_RESONANCE);
        ItemEventHandler.getEventResponders(collector).forEach(lookup -> lookup.run(IMalumEventResponderItem.class,
                (eventResponderItem, stack) -> eventResponderItem.spiritCollectionEvent(collectionEvent, collector, attribute)));
        NeoForge.EVENT_BUS.post(collectionEvent);
    }
}