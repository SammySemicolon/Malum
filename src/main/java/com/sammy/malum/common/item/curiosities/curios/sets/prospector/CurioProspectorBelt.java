package com.sammy.malum.common.item.curiosities.curios.sets.prospector;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import dev.architectury.event.events.common.ExplosionEvent;
import net.minecraft.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.List;
import java.util.function.Consumer;

public class CurioProspectorBelt extends MalumCurioItem {

    public CurioProspectorBelt(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer, TooltipContext context) {
        consumer.accept(ComponentHelper.positiveCurioEffect("enchanted_explosions", Enchantment.getFullname(context.registries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 3).copy().withStyle(ChatFormatting.BLUE)));
        consumer.accept(ComponentHelper.positiveCurioEffect("explosions_spare_valuables"));
    }

    public static void processExplosion(Level level, Explosion explosion, List<Entity> entities) {
        LivingEntity exploder = explosion.getIndirectSourceEntity();
        if (exploder != null && TrinketsHelper.hasTrinketEquipped(exploder, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
            entities.removeIf(e -> e instanceof ItemEntity itemEntity && itemEntity.getItem().is(ItemTagRegistry.PROSPECTORS_TREASURE));
        }
    }

    public static LootParams.Builder applyFortune(Entity source, LootParams.Builder builder) {
        if (source instanceof LivingEntity livingEntity) {
            HolderLookup.RegistryLookup<Enchantment> registriesProvider = livingEntity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            if (TrinketsHelper.hasTrinketEquipped(livingEntity, ItemRegistry.BELT_OF_THE_PROSPECTOR.get())) {
                int fortuneBonus = 3 + TrinketsHelper.getEquippedTrinkets(livingEntity).stream().map(h -> {
                    var data = h.getB().get(DataComponents.ENCHANTMENTS);
                    if (data != null) {
                        for (net.minecraft.core.Holder<Enchantment> key : data.keySet()) {
                            if (key.is(Enchantments.FORTUNE)) {
                                return data.getLevel(key);
                            }
                        }
                    }
                    return 0;
                }).findFirst().orElse(0);
                ItemStack diamondPickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
                diamondPickaxe.enchant(registriesProvider.getOrThrow(Enchantments.FORTUNE), fortuneBonus);
                return builder.withParameter(LootContextParams.TOOL, diamondPickaxe);
            }
        }
        return builder;
    }
}
