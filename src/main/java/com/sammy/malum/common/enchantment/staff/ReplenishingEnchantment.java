package com.sammy.malum.common.enchantment.staff;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.compability.irons_spellbooks.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.network.*;

import java.util.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;

public class ReplenishingEnchantment extends Enchantment {
    public ReplenishingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.STAFF_ONLY, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public static void replenishStaffCooldown(LivingEntity attacker, ItemStack stack) {
        if (attacker instanceof ServerPlayer player && stack.getItem() instanceof AbstractStaffItem staff) {
            ItemCooldowns cooldowns = player.getCooldowns();
            if (cooldowns.isOnCooldown(staff) && player.getAttackStrengthScale(0) > 0.8f) {
                int level = stack.getEnchantmentLevel(EnchantmentRegistry.REPLENISHING.get());
                replenishStaffCooldown(staff, player, level);
                IronsSpellsCompat.recoverSpellCooldowns(player, level);
                MALUM_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncStaffCooldownChangesPacket(staff, level));
            }
        }
    }

    public static void replenishStaffCooldown(AbstractStaffItem staff, Player player, int pLevel) {
        ItemCooldowns cooldowns = player.getCooldowns();
        int ratio = (int) (staff.getCooldownDuration(player.level(), player) * (0.25f * pLevel));
        cooldowns.tickCount+=ratio;
        for (Map.Entry<Item, ItemCooldowns.CooldownInstance> itemCooldownInstanceEntry : cooldowns.cooldowns.entrySet()) {
            if (itemCooldownInstanceEntry.getKey().equals(staff)) {
                continue;
            }
            ItemCooldowns.CooldownInstance value = itemCooldownInstanceEntry.getValue();
            ItemCooldowns.CooldownInstance cooldownInstance = new ItemCooldowns.CooldownInstance(value.startTime+ratio, value.endTime+ratio);
            cooldowns.cooldowns.put(itemCooldownInstanceEntry.getKey(), cooldownInstance);
        }
    }
}