package com.sammy.malum.core.handlers;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.network.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.*;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

@EventBusSubscriber
public class EnchantmentEffectEventHandler {

    @SubscribeEvent
    public static void triggerReplenishing(LivingDamageEvent.Post event) {
        var attacker = event.getSource().getDirectEntity();
        if (attacker instanceof Player player) {
            if (event.getSource().is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
                var cooldowns = player.getCooldowns();
                var stack = SoulDataHandler.getSoulHunterWeapon(event.getSource(), player);
                if (stack.getItem() instanceof AbstractStaffItem staff) {
                    if (cooldowns.isOnCooldown(staff) && player.getAttackStrengthScale(0) > 0.8f) {
                        int level = getEnchantmentLevel(attacker.level(), EnchantmentRegistry.REPLENISHING, stack);
                        replenishStaffCooldown(staff, player, level);
                        if (player instanceof ServerPlayer serverPlayer) {
                            PacketDistributor.sendToPlayer(serverPlayer, new ReplenishingCooldownUpdatePayload(staff, level));
                        }
                    }
                }
            }
        }
    }

    public static void replenishStaffCooldown(AbstractStaffItem staff, Player player, int pLevel) {
        ItemCooldowns cooldowns = player.getCooldowns();
        int ratio = (int) (staff.getCooldownDuration(player.level(), player) * (0.25f * pLevel));
        cooldowns.tickCount += ratio; //TODO: access transform these fellas
        for (Map.Entry<Item, ItemCooldowns.CooldownInstance> itemCooldownInstanceEntry : cooldowns.cooldowns.entrySet()) {
            if (itemCooldownInstanceEntry.getKey().equals(staff)) {
                continue;
            }
            ItemCooldowns.CooldownInstance value = itemCooldownInstanceEntry.getValue();
            ItemCooldowns.CooldownInstance cooldownInstance = new ItemCooldowns.CooldownInstance(value.startTime + ratio, value.endTime + ratio);
            cooldowns.cooldowns.put(itemCooldownInstanceEntry.getKey(), cooldownInstance);
        }
    }

    public static int getEnchantmentLevel(Level level, ResourceKey<Enchantment> key, ItemStack stack) {
        HolderGetter<Enchantment> enchantmentLookup = level.registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT);
        return stack.getEnchantmentLevel(enchantmentLookup.getOrThrow(key));
    }
}
