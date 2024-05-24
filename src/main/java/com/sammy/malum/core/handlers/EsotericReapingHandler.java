package com.sammy.malum.core.handlers;

import com.sammy.malum.common.components.MalumComponents;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.listeners.ReapingDataReloadListener;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingDeathEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.ItemHelper;

import java.util.List;

public class EsotericReapingHandler {

    public static boolean tryCreateReapingDrops(LivingEntity livingEntity, DamageSource damageSource, float v) {
        LivingEntity target = livingEntity;
        LivingEntity attacker = null;
        if (damageSource.getEntity() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getLastHurtByMob();
        }
        if (CommonConfig.AWARD_CODEX_ON_KILL.getConfigValue()) {
            if (target.getMobType().equals(MobType.UNDEAD) && attacker instanceof Player player) {
                MalumComponents.MALUM_PLAYER_COMPONENT.maybeGet(player).ifPresent(c -> {
                    if (!c.obtainedEncyclopedia && player.getRandom().nextFloat() < 0.2f) {
                        c.obtainedEncyclopedia = true;
                        SpiritHarvestHandler.spawnItemAsSpirit(ItemRegistry.ENCYCLOPEDIA_ARCANA.get().getDefaultInstance(), target, player);
                    }
                });
            }
        }
        List<ReapingDataReloadListener.MalumReapingDropsData> data = ReapingDataReloadListener.REAPING_DATA.get(BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()));
        if (data != null) {
            SoulDataHandler soulData = MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.get(target).soulData;
            if (soulData.exposedSoulDuration > 0) {
                for (ReapingDataReloadListener.MalumReapingDropsData dropData : data) {
                    Level level = target.level();
                    var random = level.random;
                    if (random.nextFloat() < dropData.chance) {
                        Ingredient ingredient = dropData.drop;
                        ItemStack stack = ItemHelper.copyWithNewCount(ingredient.getItems()[random.nextInt(ingredient.getItems().length)], Mth.nextInt(random, dropData.min, dropData.max));
                        ItemEntity itemEntity = new ItemEntity(level, target.getX(), target.getY(), target.getZ(), stack);
                        itemEntity.setDefaultPickUpDelay();
                        itemEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                        level.addFreshEntity(itemEntity);
                    }
                }
            }
        }
        return true;
    }
}