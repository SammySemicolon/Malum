package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MalumMod.MALUM);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONTENT = CREATIVE_MODE_TABS.register("malum_content",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_basis_of_magic"))
                    .withTabsAfter(MalumMod.malumPath("malum_nature"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> ItemRegistry.SPIRIT_ALTAR.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NATURE = CREATIVE_MODE_TABS.register("malum_nature",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_natural_wonders"))
                    .withTabsBefore(CONTENT.getId())
                    .withTabsAfter(MalumMod.malumPath("malum_building"))
                    .icon(() -> ItemRegistry.RUNEWOOD_SAPLING.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BUILDING = CREATIVE_MODE_TABS.register("malum_building",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_arcane_construct"))
                    .withTabsBefore(NATURE.getId())
                    .withTabsAfter(MalumMod.malumPath("malum_metallurgy"))
                    .icon(() -> ItemRegistry.TAINTED_ROCK.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> METALLURGY = CREATIVE_MODE_TABS.register("malum_metallurgy",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_metallurgic_magics"))
                    .withTabsBefore(BUILDING.getId())
                    .withTabsAfter(MalumMod.malumPath("ritual_shards"))
                    .icon(() -> ItemRegistry.ALCHEMICAL_IMPETUS.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GEAS = CREATIVE_MODE_TABS.register("malum_geas",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_geas"))
                    .withTabsBefore(METALLURGY.getId())
                    .withTabsAfter(MalumMod.malumPath("malum_ritual_shards"))
                    .displayItems((p, o) -> {
                        for (DeferredHolder<GeasEffectType, ? extends GeasEffectType> etchingType : MalumGeasEffectTypeRegistry.GEAS_TYPES.getEntries()) {
                            final GeasEffectType geasEffectType = etchingType.get();
                            if (geasEffectType.equals(MalumGeasEffectTypeRegistry.CREED_OF_THE_BLIGHT_EATER.get())) {
                                continue;
                            }
                            o.accept(geasEffectType.createDefaultStack());
                        }
                    })
                    .icon(() -> ItemRegistry.GEAS.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RITUAL_SHARDS = CREATIVE_MODE_TABS.register("malum_ritual_shards",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_ritual_shards"))
                    .withTabsBefore(GEAS.getId())
                    .withTabsAfter(MalumMod.malumPath("malum_cosmetic"))
                    .displayItems((p, o) -> {
                        for (MalumRitualType ritualType : RitualRegistry.RITUALS) {
                            for (MalumRitualTier ritualTier : MalumRitualTier.TIERS) {
                                ItemStack shard = new ItemStack(RITUAL_SHARD.get());
                                shard.set(DataComponentRegistry.RITUAL_DATA, ritualType.createDataComponent(ritualTier));
                                o.accept(shard);
                            }
                        }
                    })
                    .icon(() -> RITUAL_PLINTH.get().getDefaultInstance()).build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COSMETIC = CREATIVE_MODE_TABS.register("malum_cosmetic",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_cosmetics"))
                    .withTabsBefore(RITUAL_SHARDS.getId())
                    .icon(() -> ItemRegistry.WEAVERS_WORKBENCH.get().getDefaultInstance()).build()
    );
}
