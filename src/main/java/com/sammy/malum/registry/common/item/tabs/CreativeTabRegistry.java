package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;

import java.util.Optional;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MalumMod.MALUM);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONTENT = CREATIVE_MODE_TABS.register("malum_content",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_basis_of_magic"))
                    .icon(() -> ItemRegistry.SPIRIT_ALTAR.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NATURE = CREATIVE_MODE_TABS.register("malum_nature",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_natural_wonders"))
                    .icon(() -> ItemRegistry.RUNEWOOD_SAPLING.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BUILDING = CREATIVE_MODE_TABS.register("malum_building",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_arcane_construct"))
                    .icon(() -> ItemRegistry.TAINTED_ROCK.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> METALLURGY = CREATIVE_MODE_TABS.register("malum_metallurgy",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_metallurgic_magics"))
                    .icon(() -> ItemRegistry.ALCHEMICAL_IMPETUS.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RITUAL_SHARDS = CREATIVE_MODE_TABS.register("malum_ritual_shards",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_ritual_shards"))
                    .displayItems((p, o) -> {
                        for (MalumRitualType ritualType : RitualRegistry.RITUALS) {
                            for (MalumRitualTier ritualTier : MalumRitualTier.TIERS) {
                                ItemStack shard = new ItemStack(RITUAL_SHARD.get());
                                shard.set(DataComponentRegistry.RITUAL_DATA.get(), ritualType.createDataComponent(ritualTier));
                                o.accept(shard);
                            }
                        }
                    })
                    .icon(() -> RITUAL_PLINTH.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COSMETIC = CREATIVE_MODE_TABS.register("malum_cosmetic",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_cosmetics"))
                    .icon(() -> ItemRegistry.WEAVERS_WORKBENCH.get().getDefaultInstance()).build()
    );
}
