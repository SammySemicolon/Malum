package com.sammy.malum.registry.common.item;

import com.sammy.malum.*;
import com.sammy.malum.client.renderer.curio.*;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.*;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.*;
import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_catalyzer.*;
import com.sammy.malum.common.block.nature.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.*;
import com.sammy.malum.common.item.codex.*;
import com.sammy.malum.common.item.cosmetic.curios.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import com.sammy.malum.common.item.cosmetic.weaves.*;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.curiosities.armor.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.curiosities.curios.brooches.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.curios.sets.alchemical.*;
import com.sammy.malum.common.item.curiosities.curios.sets.misc.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.*;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.*;
import com.sammy.malum.common.item.curiosities.curios.sets.scythe.*;
import com.sammy.malum.common.item.curiosities.curios.sets.soulward.*;
import com.sammy.malum.common.item.curiosities.curios.sets.spirit.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.common.item.disc.*;
import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.common.item.food.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.tabs.*;
import net.minecraft.client.color.item.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.*;
import net.minecraft.world.food.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;
import team.lodestar.lodestone.systems.item.tools.magic.*;
import team.lodestar.lodestone.systems.multiblock.*;
import top.theillusivec4.curios.api.client.*;

import java.util.List;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemTiers.*;
import static net.minecraft.world.item.Rarity.*;

@SuppressWarnings("unused")
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MALUM);

    public static final List<Item> CONTENT = new ArrayList<>();

    public static LodestoneItemProperties DEFAULT_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.CONTENT);
    }

    public static LodestoneItemProperties GEAR_PROPERTIES() {
        return DEFAULT_PROPERTIES().stacksTo(1);
    }

    public static LodestoneItemProperties BUILDING_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.BUILDING);
    }

    public static LodestoneItemProperties NATURE_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.NATURE);
    }

    public static LodestoneItemProperties METALLURGIC_NODE_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.METALLURGY);
    }

    public static LodestoneItemProperties METALLURGIC_PROPERTIES() {
        return METALLURGIC_NODE_PROPERTIES().stacksTo(1);
    }

    public static LodestoneItemProperties COSMETIC_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.COSMETIC);
    }

    public static LodestoneItemProperties HIDDEN_PROPERTIES() {
        return new LodestoneItemProperties().stacksTo(1);
    }

    public static <T extends Item> DeferredHolder<Item, T> register(String name, LodestoneItemProperties properties, Function<LodestoneItemProperties, T> function) {
        LodestoneItemProperties.addToTabSorting(MalumMod.malumPath(name), properties);
        return ITEMS.register(name, () -> function.apply(properties));
    }

    public static final DeferredHolder<Item, Item> ENCYCLOPEDIA_ARCANA = register("encyclopedia_arcana", GEAR_PROPERTIES().rarity(UNCOMMON), EncyclopediaArcanaItem::new);

    public static final DeferredHolder<Item, RitualShardItem> RITUAL_SHARD = register("ritual_shard", HIDDEN_PROPERTIES(), RitualShardItem::new);
    public static final DeferredHolder<Item, GeasItem> GEAS = register("geas", HIDDEN_PROPERTIES(), GeasItem::new);

    //region spirits
    public static final DeferredHolder<Item, SpiritShardItem> SACRED_SPIRIT = register("sacred_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.SACRED_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> WICKED_SPIRIT = register("wicked_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.WICKED_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> ARCANE_SPIRIT = register("arcane_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> ELDRITCH_SPIRIT = register("eldritch_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> AERIAL_SPIRIT = register("aerial_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> AQUEOUS_SPIRIT = register("aqueous_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.AQUEOUS_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> EARTHEN_SPIRIT = register("earthen_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> INFERNAL_SPIRIT = register("infernal_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final DeferredHolder<Item, SpiritShardItem> UMBRAL_SPIRIT = register("umbral_spirit", HIDDEN_PROPERTIES(), (p) -> new UmbralSpiritShardItem(p, SpiritTypeRegistry.UMBRAL_SPIRIT));
    //endregion

    public static final DeferredHolder<Item, Item> ENCYCLOPEDIA_ESOTERICA = register("encyclopedia_esoterica", GEAR_PROPERTIES().rarity(EPIC), EncyclopediaEsotericaItem::new);

    public static final DeferredHolder<Item, Item> ARCANE_ELEGY = register("music_disc_arcane_elegy", HIDDEN_PROPERTIES().rarity(RARE), ArcaneElegyMusicDiscItem::new);
    public static final DeferredHolder<Item, Item> AESTHETICA = register("music_disc_aesthetica", HIDDEN_PROPERTIES().rarity(RARE), AestheticaMusicDiscItem::new);

    //region random stuff
    public static final DeferredHolder<Item, Item> COPPER_NUGGET = register("copper_nugget", new LodestoneItemProperties(CreativeModeTabs.INGREDIENTS), Item::new);
    public static final DeferredHolder<Item, Item> COAL_FRAGMENT = register("coal_fragment", new LodestoneItemProperties(CreativeModeTabs.INGREDIENTS), (p) -> new LodestoneFuelItem(p, 200));
    public static final DeferredHolder<Item, Item> CHARCOAL_FRAGMENT = register("charcoal_fragment", new LodestoneItemProperties(CreativeModeTabs.INGREDIENTS), (p) -> new LodestoneFuelItem(p, 200));
    //endregion

    //region spirited glass
    public static final DeferredHolder<Item, Item> SACRED_SPIRITED_GLASS = register("sacred_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SACRED_SPIRITED_GLASS.get(), p));
    public static final DeferredHolder<Item, Item> WICKED_SPIRITED_GLASS = register("wicked_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WICKED_SPIRITED_GLASS.get(), p));
    public static final DeferredHolder<Item, Item> ARCANE_SPIRITED_GLASS = register("arcane_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.ARCANE_SPIRITED_GLASS.get(), p));
    public static final DeferredHolder<Item, Item> ELDRITCH_SPIRITED_GLASS = register("eldritch_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.ELDRITCH_SPIRITED_GLASS.get(), p));
    public static final DeferredHolder<Item, Item> AERIAL_SPIRITED_GLASS = register("aerial_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.AERIAL_SPIRITED_GLASS.get(), p));
    public static final DeferredHolder<Item, Item> AQUEOUS_SPIRITED_GLASS = register("aqueous_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.AQUEOUS_SPIRITED_GLASS.get(), p));
    public static final DeferredHolder<Item, Item> INFERNAL_SPIRITED_GLASS = register("infernal_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.INFERNAL_SPIRITED_GLASS.get(), p));
    public static final DeferredHolder<Item, Item> EARTHEN_SPIRITED_GLASS = register("earthen_spirited_glass", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.EARTHEN_SPIRITED_GLASS.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOVEN_BANNER = register("soulwoven_banner", BUILDING_PROPERTIES(), SoulwovenBannerBlockItem::new);

    //endregion

    //region tainted rock
    public static final DeferredHolder<Item, Item> TAINTED_ROCK = register("tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TAINTED_ROCK = register("smooth_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TAINTED_ROCK = register("polished_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_BRICKS = register("tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_TILES = register("tainted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TAINTED_ROCK_BRICKS = register("small_tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_BRICKS = register("runic_tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_TILES = register("runic_tainted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS = register("runic_small_tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS.get(), p));

    public static final DeferredHolder<Item, Item> TAINTED_ROCK_COLUMN = register("tainted_rock_column", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_COLUMN_CAP = register("tainted_rock_column_cap", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN_CAP.get(), p));

    public static final DeferredHolder<Item, Item> CUT_TAINTED_ROCK = register("cut_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_TAINTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> CHECKERED_TAINTED_ROCK = register("checkered_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHECKERED_TAINTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> CHISELED_TAINTED_ROCK = register("chiseled_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHISELED_TAINTED_ROCK.get(), p));

    public static final DeferredHolder<Item, Item> TAINTED_ROCK_STAIRS = register("tainted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TAINTED_ROCK_STAIRS = register("smooth_tainted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TAINTED_ROCK_STAIRS = register("polished_tainted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_BRICKS_STAIRS = register("tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_TILES_STAIRS = register("tainted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TAINTED_ROCK_BRICKS_STAIRS = register("small_tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_BRICKS_STAIRS = register("runic_tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_TILES_STAIRS = register("runic_tainted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS = register("runic_small_tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), p));

    public static final DeferredHolder<Item, Item> TAINTED_ROCK_SLAB = register("tainted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TAINTED_ROCK_SLAB = register("smooth_tainted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TAINTED_ROCK_SLAB = register("polished_tainted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_BRICKS_SLAB = register("tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_TILES_SLAB = register("tainted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TAINTED_ROCK_BRICKS_SLAB = register("small_tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_BRICKS_SLAB = register("runic_tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_TILES_SLAB = register("runic_tainted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB = register("runic_small_tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), p));

    public static final DeferredHolder<Item, Item> TAINTED_ROCK_WALL = register("tainted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_WALL.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TAINTED_ROCK_WALL = register("smooth_tainted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_WALL.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TAINTED_ROCK_WALL = register("polished_tainted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_WALL.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_BRICKS_WALL = register("tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_WALL.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_TILES_WALL = register("tainted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_WALL.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TAINTED_ROCK_BRICKS_WALL = register("small_tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_BRICKS_WALL = register("runic_tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_WALL.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TAINTED_ROCK_TILES_WALL = register("runic_tainted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_WALL.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL = register("runic_small_tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), p));

    public static final DeferredHolder<Item, Item> TAINTED_ROCK_PRESSURE_PLATE = register("tainted_rock_pressure_plate", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_BUTTON = register("tainted_rock_button", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BUTTON.get(), p));

    public static final DeferredHolder<Item, Item> TAINTED_ROCK_ITEM_STAND = register("tainted_rock_item_stand", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_STAND.get(), p));
    public static final DeferredHolder<Item, Item> TAINTED_ROCK_ITEM_PEDESTAL = register("tainted_rock_item_pedestal", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(), p));
    //endregion

    //region twisted rock
    public static final DeferredHolder<Item, Item> TWISTED_ROCK = register("twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TWISTED_ROCK = register("smooth_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TWISTED_ROCK = register("polished_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_BRICKS = register("twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_TILES = register("twisted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TWISTED_ROCK_BRICKS = register("small_twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_BRICKS = register("runic_twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_TILES = register("runic_twisted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS = register("runic_small_twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS.get(), p));

    public static final DeferredHolder<Item, Item> TWISTED_ROCK_COLUMN = register("twisted_rock_column", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_COLUMN_CAP = register("twisted_rock_column_cap", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN_CAP.get(), p));

    public static final DeferredHolder<Item, Item> CUT_TWISTED_ROCK = register("cut_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_TWISTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> CHECKERED_TWISTED_ROCK = register("checkered_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHECKERED_TWISTED_ROCK.get(), p));
    public static final DeferredHolder<Item, Item> CHISELED_TWISTED_ROCK = register("chiseled_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHISELED_TWISTED_ROCK.get(), p));

    public static final DeferredHolder<Item, Item> TWISTED_ROCK_STAIRS = register("twisted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TWISTED_ROCK_STAIRS = register("smooth_twisted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TWISTED_ROCK_STAIRS = register("polished_twisted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_BRICKS_STAIRS = register("twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_TILES_STAIRS = register("twisted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TWISTED_ROCK_BRICKS_STAIRS = register("small_twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_BRICKS_STAIRS = register("runic_twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_TILES_STAIRS = register("runic_twisted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS = register("runic_small_twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), p));

    public static final DeferredHolder<Item, Item> TWISTED_ROCK_SLAB = register("twisted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TWISTED_ROCK_SLAB = register("smooth_twisted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TWISTED_ROCK_SLAB = register("polished_twisted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_BRICKS_SLAB = register("twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_TILES_SLAB = register("twisted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TWISTED_ROCK_BRICKS_SLAB = register("small_twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_BRICKS_SLAB = register("runic_twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_TILES_SLAB = register("runic_twisted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB = register("runic_small_twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), p));

    public static final DeferredHolder<Item, Item> TWISTED_ROCK_WALL = register("twisted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_WALL.get(), p));
    public static final DeferredHolder<Item, Item> SMOOTH_TWISTED_ROCK_WALL = register("smooth_twisted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_WALL.get(), p));
    public static final DeferredHolder<Item, Item> POLISHED_TWISTED_ROCK_WALL = register("polished_twisted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_WALL.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_BRICKS_WALL = register("twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_WALL.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_TILES_WALL = register("twisted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_WALL.get(), p));
    public static final DeferredHolder<Item, Item> SMALL_TWISTED_ROCK_BRICKS_WALL = register("small_twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_BRICKS_WALL = register("runic_twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_WALL.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_TWISTED_ROCK_TILES_WALL = register("runic_twisted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_WALL.get(), p));
    public static final DeferredHolder<Item, Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL = register("runic_small_twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), p));

    public static final DeferredHolder<Item, Item> TWISTED_ROCK_PRESSURE_PLATE = register("twisted_rock_pressure_plate", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_BUTTON = register("twisted_rock_button", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BUTTON.get(), p));

    public static final DeferredHolder<Item, Item> TWISTED_ROCK_ITEM_STAND = register("twisted_rock_item_stand", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_STAND.get(), p));
    public static final DeferredHolder<Item, Item> TWISTED_ROCK_ITEM_PEDESTAL = register("twisted_rock_item_pedestal", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), p));
    //endregion twisted rock

    //region runewood
    public static final DeferredHolder<Item, Item> RUNIC_SAP = register("runic_sap", NATURE_PROPERTIES(), (p) -> new BottledDrinkItem(NATURE_PROPERTIES().food(FoodPropertyRegistry.RUNIC_SAP)));
    public static final DeferredHolder<Item, Item> RUNIC_SAPBALL = register("runic_sapball", NATURE_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> RUNIC_SAP_BLOCK = register("runic_sap_block", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SAP_BLOCK.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_LEAVES = register("runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_LEAVES.get(), p));
    public static final DeferredHolder<Item, Item> HANGING_RUNEWOOD_LEAVES = register("hanging_runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.HANGING_RUNEWOOD_LEAVES.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_SAPLING = register("runewood_sapling", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_SAPLING.get(), p));

    public static final DeferredHolder<Item, Item> AZURE_RUNEWOOD_LEAVES = register("azure_runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.AZURE_RUNEWOOD_LEAVES.get(), p));
    public static final DeferredHolder<Item, Item> HANGING_AZURE_RUNEWOOD_LEAVES = register("hanging_azure_runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.HANGING_AZURE_RUNEWOOD_LEAVES.get(), p));
    public static final DeferredHolder<Item, Item> AZURE_RUNEWOOD_SAPLING = register("azure_runewood_sapling", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.AZURE_RUNEWOOD_SAPLING.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_LOG = register("runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_LOG.get(), p));
    public static final DeferredHolder<Item, Item> STRIPPED_RUNEWOOD_LOG = register("stripped_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD_LOG.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD = register("runewood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD.get(), p));
    public static final DeferredHolder<Item, Item> STRIPPED_RUNEWOOD = register("stripped_runewood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD.get(), p));

    public static final DeferredHolder<Item, Item> EXPOSED_RUNEWOOD_LOG = register("exposed_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.EXPOSED_RUNEWOOD_LOG.get(), p));
    public static final DeferredHolder<Item, Item> REVEALED_RUNEWOOD_LOG = register("revealed_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.REVEALED_RUNEWOOD_LOG.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_BOARDS = register("runewood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BOARDS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUNEWOOD_BOARDS = register("vertical_runewood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_BOARDS.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_PLANKS = register("runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUNEWOOD_PLANKS = register("vertical_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_TILES = register("runewood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_RUNEWOOD_PLANKS = register("rustic_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUSTIC_RUNEWOOD_PLANKS = register("vertical_rustic_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_RUNEWOOD_TILES = register("rustic_runewood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_TILES.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_PANEL = register("runewood_panel", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL.get(), p));
    public static final DeferredHolder<Item, Item> CUT_RUNEWOOD_PLANKS = register("cut_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_RUNEWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_BEAM = register("runewood_beam", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BEAM.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_BOARDS_STAIRS = register("runewood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BOARDS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUNEWOOD_BOARDS_STAIRS = register("vertical_runewood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_BOARDS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_PLANKS_STAIRS = register("runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUNEWOOD_PLANKS_STAIRS = register("vertical_runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_TILES_STAIRS = register("runewood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_RUNEWOOD_PLANKS_STAIRS = register("rustic_runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS = register("vertical_rustic_runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_RUNEWOOD_TILES_STAIRS = register("rustic_runewood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_TILES_STAIRS.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_BOARDS_SLAB = register("runewood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BOARDS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUNEWOOD_BOARDS_SLAB = register("vertical_runewood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_BOARDS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_PLANKS_SLAB = register("runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUNEWOOD_PLANKS_SLAB = register("vertical_runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_TILES_SLAB = register("runewood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_RUNEWOOD_PLANKS_SLAB = register("rustic_runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB = register("vertical_rustic_runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_RUNEWOOD_TILES_SLAB = register("rustic_runewood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_TILES_SLAB.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_DOOR = register("runewood_door", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_DOOR.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_TRAPDOOR = register("runewood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TRAPDOOR.get(), p));
    public static final DeferredHolder<Item, Item> SOLID_RUNEWOOD_TRAPDOOR = register("solid_runewood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_BUTTON = register("runewood_planks_button", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BUTTON.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_PRESSURE_PLATE = register("runewood_planks_pressure_plate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PRESSURE_PLATE.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_FENCE = register("runewood_planks_fence", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_FENCE_GATE = register("runewood_planks_fence_gate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE_GATE.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_BOARDS_WALL = register("runewood_boards_wall", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BOARDS_WALL.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_ITEM_STAND = register("runewood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_STAND.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_ITEM_PEDESTAL = register("runewood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_PEDESTAL.get(), p));
    public static final DeferredHolder<Item, Item> GILDED_RUNEWOOD_ITEM_STAND = register("gilded_runewood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.GILDED_RUNEWOOD_ITEM_STAND.get(), p));
    public static final DeferredHolder<Item, Item> GILDED_RUNEWOOD_ITEM_PEDESTAL = register("gilded_runewood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.GILDED_RUNEWOOD_ITEM_PEDESTAL.get(), p));

    public static final DeferredHolder<Item, Item> RUNEWOOD_SIGN = register("runewood_sign", NATURE_PROPERTIES(), (p) -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.RUNEWOOD_SIGN.get(), BlockRegistry.RUNEWOOD_WALL_SIGN.get()));
    public static final DeferredHolder<Item, Item> RUNEWOOD_BOAT = register("runewood_boat", NATURE_PROPERTIES(), (p) -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.RUNEWOOD_BOAT));
    //endregion

    //region blight
    public static final DeferredHolder<Item, Item> BLIGHTED_EARTH = register("blighted_earth", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_EARTH.get(), p));
    public static final DeferredHolder<Item, Item> BLIGHTED_SOIL = register("blighted_soil", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_SOIL.get(), p));
    //endregion

    //region soulwood
    public static final DeferredHolder<Item, Item> CURSED_SAP = register("cursed_sap", NATURE_PROPERTIES(), (p) -> new BottledDrinkItem(NATURE_PROPERTIES().food(FoodPropertyRegistry.CURSED_SAP)));
    public static final DeferredHolder<Item, Item> CURSED_SAPBALL = register("cursed_sapball", NATURE_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> CURSED_SAP_BLOCK = register("cursed_sap_block", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CURSED_SAP_BLOCK.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_LEAVES = register("soulwood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_LEAVES.get(), p));
    public static final DeferredHolder<Item, Item> HANGING_SOULWOOD_LEAVES = register("hanging_soulwood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.HANGING_SOULWOOD_LEAVES.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_GROWTH = register("soulwood_growth", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_GROWTH.get(), p));

    public static final DeferredHolder<Item, Item> BLIGHTED_SOULWOOD = register("blighted_soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_SOULWOOD.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_LOG = register("soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_LOG.get(), p));
    public static final DeferredHolder<Item, Item> STRIPPED_SOULWOOD_LOG = register("stripped_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD_LOG.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD = register("soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD.get(), p));
    public static final DeferredHolder<Item, Item> STRIPPED_SOULWOOD = register("stripped_soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD.get(), p));

    public static final DeferredHolder<Item, Item> EXPOSED_SOULWOOD_LOG = register("exposed_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.EXPOSED_SOULWOOD_LOG.get(), p));
    public static final DeferredHolder<Item, Item> REVEALED_SOULWOOD_LOG = register("revealed_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.REVEALED_SOULWOOD_LOG.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_BOARDS = register("soulwood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BOARDS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_SOULWOOD_BOARDS = register("vertical_soulwood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_BOARDS.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_PLANKS = register("soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_SOULWOOD_PLANKS = register("vertical_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_TILES = register("soulwood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_SOULWOOD_PLANKS = register("rustic_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUSTIC_SOULWOOD_PLANKS = register("vertical_rustic_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_SOULWOOD_TILES = register("rustic_soulwood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_TILES.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_PANEL = register("soulwood_panel", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PANEL.get(), p));
    public static final DeferredHolder<Item, Item> CUT_SOULWOOD_PLANKS = register("cut_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_SOULWOOD_PLANKS.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_BEAM = register("soulwood_beam", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BEAM.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_BOARDS_STAIRS = register("soulwood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BOARDS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_SOULWOOD_BOARDS_STAIRS = register("vertical_soulwood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_BOARDS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_PLANKS_STAIRS = register("soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_SOULWOOD_PLANKS_STAIRS = register("vertical_soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_TILES_STAIRS = register("soulwood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_SOULWOOD_PLANKS_STAIRS = register("rustic_soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS = register("vertical_rustic_soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_SOULWOOD_TILES_STAIRS = register("rustic_soulwood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_TILES_STAIRS.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_BOARDS_SLAB = register("soulwood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BOARDS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_SOULWOOD_BOARDS_SLAB = register("vertical_soulwood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_BOARDS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_PLANKS_SLAB = register("soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_SOULWOOD_PLANKS_SLAB = register("vertical_soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_TILES_SLAB = register("soulwood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_SOULWOOD_PLANKS_SLAB = register("rustic_soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB = register("vertical_rustic_soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB.get(), p));
    public static final DeferredHolder<Item, Item> RUSTIC_SOULWOOD_TILES_SLAB = register("rustic_soulwood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_TILES_SLAB.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_DOOR = register("soulwood_door", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_DOOR.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_TRAPDOOR = register("soulwood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TRAPDOOR.get(), p));
    public static final DeferredHolder<Item, Item> SOLID_SOULWOOD_TRAPDOOR = register("solid_soulwood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_BUTTON = register("soulwood_planks_button", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BUTTON.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_PRESSURE_PLATE = register("soulwood_planks_pressure_plate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PRESSURE_PLATE.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_FENCE = register("soulwood_planks_fence", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_FENCE.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_FENCE_GATE = register("soulwood_planks_fence_gate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_FENCE_GATE.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_BOARDS_WALL = register("soulwood_boards_wall", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BOARDS_WALL.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_ITEM_STAND = register("soulwood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_STAND.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_ITEM_PEDESTAL = register("soulwood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_PEDESTAL.get(), p));
    public static final DeferredHolder<Item, Item> ORNATE_SOULWOOD_ITEM_STAND = register("ornate_soulwood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.ORNATE_SOULWOOD_ITEM_STAND.get(), p));
    public static final DeferredHolder<Item, Item> ORNATE_SOULWOOD_ITEM_PEDESTAL = register("ornate_soulwood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.ORNATE_SOULWOOD_ITEM_PEDESTAL.get(), p));

    public static final DeferredHolder<Item, Item> SOULWOOD_SIGN = register("soulwood_sign", NATURE_PROPERTIES(), (p) -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.SOULWOOD_SIGN.get(), BlockRegistry.SOULWOOD_WALL_SIGN.get()));
    public static final DeferredHolder<Item, Item> SOULWOOD_BOAT = register("soulwood_boat", NATURE_PROPERTIES(), (p) -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.SOULWOOD_BOAT));
    //endregion

    //region ores
    public static final DeferredHolder<Item, Item> BLOCK_OF_SOULSTONE = register("block_of_soulstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_SOULSTONE.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_RAW_SOULSTONE = register("block_of_raw_soulstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_RAW_SOULSTONE.get(), p));
    public static final DeferredHolder<Item, Item> DEEPSLATE_SOULSTONE_ORE = register("deepslate_soulstone_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get(), p));
    public static final DeferredHolder<Item, Item> SOULSTONE_ORE = register("soulstone_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULSTONE_ORE.get(), p));
    public static final DeferredHolder<Item, Item> RAW_SOULSTONE = register("raw_soulstone", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> CRUSHED_SOULSTONE = register("crushed_soulstone", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> REFINED_SOULSTONE = register("refined_soulstone", DEFAULT_PROPERTIES(), Item::new);

    public static final DeferredHolder<Item, Item> BLOCK_OF_BRILLIANCE = register("block_of_brilliance", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_BRILLIANCE.get(), p));
    public static final DeferredHolder<Item, Item> BRILLIANT_DEEPSLATE = register("brilliant_deepslate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BRILLIANT_DEEPSLATE.get(), p));
    public static final DeferredHolder<Item, Item> BRILLIANT_STONE = register("brilliant_stone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BRILLIANT_STONE.get(), p));
    public static final DeferredHolder<Item, Item> RAW_BRILLIANCE = register("raw_brilliance", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> CRUSHED_BRILLIANCE = register("crushed_brilliance", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> REFINED_BRILLIANCE = register("refined_brilliance", DEFAULT_PROPERTIES(), (p) -> new BrillianceChunkItem(p.food((new FoodProperties.Builder()).fast().alwaysEdible().build())));

    public static final DeferredHolder<Item, Item> BLOCK_OF_ARCANE_CHARCOAL = register("block_of_arcane_charcoal", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_ARCANE_CHARCOAL.get(), p, 32000));
    public static final DeferredHolder<Item, Item> ARCANE_CHARCOAL = register("arcane_charcoal", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 3200));
    public static final DeferredHolder<Item, Item> ARCANE_CHARCOAL_FRAGMENT = register("arcane_charcoal_fragment", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 400));

    public static final DeferredHolder<Item, Item> BLOCK_OF_BLAZING_QUARTZ = register("block_of_blazing_quartz", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get(), p, 16000));
    public static final DeferredHolder<Item, Item> BLAZING_QUARTZ_ORE = register("blazing_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLAZING_QUARTZ_ORE.get(), p));
    public static final DeferredHolder<Item, Item> BLAZING_QUARTZ = register("blazing_quartz", DEFAULT_PROPERTIES(), (p) -> new BlazingQuartzItem(BlockRegistry.BLAZING_QUARTZ_CLUSTER.get(), 1600, p));
    public static final DeferredHolder<Item, Item> BLAZING_QUARTZ_FRAGMENT = register("blazing_quartz_fragment", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 200));

    public static final DeferredHolder<Item, Item> DEEPSLATE_QUARTZ_ORE = register("deepslate_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get(), p));
    public static final DeferredHolder<Item, Item> NATURAL_QUARTZ_ORE = register("natural_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.NATURAL_QUARTZ_ORE.get(), p));
    public static final DeferredHolder<Item, Item> NATURAL_QUARTZ = register("natural_quartz", DEFAULT_PROPERTIES(), (p) -> new ItemNameBlockItem(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get(), p));

    public static final DeferredHolder<Item, Item> BLOCK_OF_CTHONIC_GOLD = register("block_of_cthonic_gold", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_CTHONIC_GOLD.get(), p));
    public static final DeferredHolder<Item, Item> CTHONIC_GOLD_ORE = register("cthonic_gold_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CTHONIC_GOLD_ORE.get(), p));
    public static final DeferredHolder<Item, Item> CTHONIC_GOLD = register("cthonic_gold", DEFAULT_PROPERTIES().rarity(UNCOMMON), Item::new);
    public static final DeferredHolder<Item, Item> CTHONIC_GOLD_FRAGMENT = register("cthonic_gold_fragment", DEFAULT_PROPERTIES(), (p) -> new ItemNameBlockItem(BlockRegistry.CTHONIC_GOLD_CLUSTER.get(), p));
    //endregion

    //region crafting blocks
    public static final DeferredHolder<Item, Item> SPIRIT_ALTAR = register("spirit_altar", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SPIRIT_ALTAR.get(), p));
    public static final DeferredHolder<Item, Item> RUNEWOOD_OBELISK = register("runewood_obelisk", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.RUNEWOOD_OBELISK.get(), p, RunewoodObeliskBlockEntity.STRUCTURE));
    public static final DeferredHolder<Item, Item> BRILLIANT_OBELISK = register("brilliant_obelisk", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.BRILLIANT_OBELISK.get(), p, BrilliantObeliskBlockEntity.STRUCTURE));
    public static final DeferredHolder<Item, Item> RUNIC_WORKBENCH = register("runic_workbench", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_WORKBENCH.get(), p));
    public static final DeferredHolder<Item, Item> SPIRIT_JAR = register("spirit_jar", DEFAULT_PROPERTIES(), (p) -> new SpiritJarItem(BlockRegistry.SPIRIT_JAR.get(), p));
    public static final DeferredHolder<Item, Item> SPIRIT_CRUCIBLE = register("spirit_crucible", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.SPIRIT_CRUCIBLE.get(), p, SpiritCrucibleCoreBlockEntity.STRUCTURE));
    public static final DeferredHolder<Item, Item> SPIRIT_CATALYZER = register("spirit_catalyzer", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.SPIRIT_CATALYZER.get(), p, SpiritCatalyzerCoreBlockEntity.STRUCTURE));
    public static final DeferredHolder<Item, Item> REPAIR_PYLON = register("repair_pylon", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.REPAIR_PYLON.get(), p, RepairPylonCoreBlockEntity.STRUCTURE));
    public static final DeferredHolder<Item, Item> RUNEWOOD_TOTEM_BASE = register("runewood_totem_base", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TOTEM_BASE.get(), p));
    public static final DeferredHolder<Item, Item> SOULWOOD_TOTEM_BASE = register("soulwood_totem_base", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TOTEM_BASE.get(), p));
    public static final DeferredHolder<Item, Item> RITUAL_PLINTH = register("ritual_plinth", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RITUAL_PLINTH.get(), p));

    public static final DeferredHolder<Item, Item> WAVECHARGER = register("wavecharger", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WAVECHARGER.get(), p));
    public static final DeferredHolder<Item, Item> WAVEBANKER = register("wavebanker", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WAVEBANKER.get(), p));
    public static final DeferredHolder<Item, Item> WAVEMAKER = register("wavemaker", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WAVEMAKER.get(), p));
    public static final DeferredHolder<Item, Item> WAVEBREAKER = register("wavebreaker", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WAVEBREAKER.get(), p));

    public static final DeferredHolder<Item, Item> WEAVERS_WORKBENCH = register("weavers_workbench", COSMETIC_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEAVERS_WORKBENCH.get(), p));
    //endregion

    //region materials
    public static final DeferredHolder<Item, Item> BLOCK_OF_ROTTING_ESSENCE = register("block_of_rotting_essence", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ROTTING_ESSENCE.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_GRIM_TALC = register("block_of_grim_talc", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_GRIM_TALC.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_ASTRAL_WEAVE = register("block_of_astral_weave", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ASTRAL_WEAVE.get(), p));
    //    public static final DeferredHolder<Item, Item> BLOCK_OF_WARP_FLUX = register("block_of_warp_flux", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_WARP_FLUX.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_HEX_ASH = register("block_of_hex_ash", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_HEX_ASH.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_LIVING_FLESH = register("block_of_living_flesh", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_LIVING_FLESH.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_ALCHEMICAL_CALX = register("block_of_alchemical_calx", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ALCHEMICAL_CALX.get(), p));
    //    public static final DeferredHolder<Item, Item> BLOCK_OF_CALCIFIED_BLIGHT = register("block_of_calcified_blight", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_CALCIFIED_BLIGHT.get(), p));
    public static final DeferredHolder<Item, Item> MASS_OF_BLIGHTED_GUNK = register("mass_of_blighted_gunk", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.MASS_OF_BLIGHTED_GUNK.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_NULL_SLATE = register("block_of_null_slate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_NULL_SLATE.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_VOID_SALTS = register("block_of_void_salts", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_VOID_SALTS.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_MNEMONIC_FRAGMENT = register("block_of_mnemonic_fragment", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_MNEMONIC_FRAGMENT.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_AURIC_EMBERS = register("block_of_auric_embers", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_AURIC_EMBERS.get(), p));
    public static final DeferredHolder<Item, Item> BLOCK_OF_MALIGNANT_LEAD = register("block_of_malignant_lead", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_MALIGNANT_LEAD.get(), p));

    public static final DeferredHolder<Item, Item> ROTTING_ESSENCE = register("rotting_essence", DEFAULT_PROPERTIES().food(FoodPropertyRegistry.ROTTING_ESSENCE), Item::new);
    public static final DeferredHolder<Item, Item> GRIM_TALC = register("grim_talc", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> ASTRAL_WEAVE = register("astral_weave", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> WARP_FLUX = register("warp_flux", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> HEX_ASH = register("hex_ash", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> LIVING_FLESH = register("living_flesh", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> ALCHEMICAL_CALX = register("alchemical_calx", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> CALCIFIED_BLIGHT = register("calcified_blight", HIDDEN_PROPERTIES(), (p) -> new CalcifiedBlightItem(BlockRegistry.CALCIFIED_BLIGHT.get(), p));
    public static final DeferredHolder<Item, Item> BLIGHTED_GUNK = register("blighted_gunk", DEFAULT_PROPERTIES(), BlightedGunkItem::new);
    public static final DeferredHolder<Item, Item> NULL_SLATE = register("null_slate", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> VOID_SALTS = register("void_salts", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> MNEMONIC_FRAGMENT = register("mnemonic_fragment", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> AURIC_EMBERS = register("auric_embers", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> MALIGNANT_LEAD = register("malignant_lead", DEFAULT_PROPERTIES().rarity(RARE), Item::new);

    public static final DeferredHolder<Item, Item> SOULWOVEN_SILK = register("soulwoven_silk", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> SPECTRAL_LENS = register("spectral_lens", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> SPECTRAL_OPTIC = register("spectral_optic", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> POPPET = register("poppet", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> ANOMALOUS_DESIGN = register("anomalous_design", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> COMPLETE_DESIGN = register("complete_design", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> FUSED_CONSCIOUSNESS = register("fused_consciousness", DEFAULT_PROPERTIES(), (p) -> new FusedConsciousnessItem(p.rarity(RARE)));

    public static final DeferredHolder<Item, Item> BLOCK_OF_SOUL_STAINED_STEEL = register("block_of_soul_stained_steel", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get(), p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_INGOT = register("soul_stained_steel_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_PLATING = register("soul_stained_steel_plating", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_NUGGET = register("soul_stained_steel_nugget", DEFAULT_PROPERTIES(), Item::new);

    public static final DeferredHolder<Item, Item> BLOCK_OF_HALLOWED_GOLD = register("block_of_hallowed_gold", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_HALLOWED_GOLD.get(), p));
    public static final DeferredHolder<Item, Item> HALLOWED_GOLD_INGOT = register("hallowed_gold_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> HALLOWED_GOLD_NUGGET = register("hallowed_gold_nugget", DEFAULT_PROPERTIES(), Item::new);

    public static final DeferredHolder<Item, Item> BLOCK_OF_MALIGNANT_PEWTER = register("block_of_malignant_pewter", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_MALIGNANT_PEWTER.get(), p));
    public static final DeferredHolder<Item, Item> MALIGNANT_PEWTER_INGOT = register("malignant_pewter_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> MALIGNANT_PEWTER_PLATING = register("malignant_pewter_plating", DEFAULT_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> MALIGNANT_PEWTER_NUGGET = register("malignant_pewter_nugget", DEFAULT_PROPERTIES(), Item::new);


    //region impetus
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_IRON_IMPETUS = register("cracked_iron_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> IRON_IMPETUS = register("iron_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_IRON_IMPETUS));
    public static final DeferredHolder<Item, Item> IRON_NODE = register("iron_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_COPPER_IMPETUS = register("cracked_copper_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> COPPER_IMPETUS = register("copper_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_COPPER_IMPETUS));
    public static final DeferredHolder<Item, Item> COPPER_NODE = register("copper_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_GOLD_IMPETUS = register("cracked_gold_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> GOLD_IMPETUS = register("gold_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_GOLD_IMPETUS));
    public static final DeferredHolder<Item, Item> GOLD_NODE = register("gold_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_LEAD_IMPETUS = register("cracked_lead_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> LEAD_IMPETUS = register("lead_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_LEAD_IMPETUS));
    public static final DeferredHolder<Item, Item> LEAD_NODE = register("lead_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_SILVER_IMPETUS = register("cracked_silver_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> SILVER_IMPETUS = register("silver_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_SILVER_IMPETUS));
    public static final DeferredHolder<Item, Item> SILVER_NODE = register("silver_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_ALUMINUM_IMPETUS = register("cracked_aluminum_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> ALUMINUM_IMPETUS = register("aluminum_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ALUMINUM_IMPETUS));
    public static final DeferredHolder<Item, Item> ALUMINUM_NODE = register("aluminum_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_NICKEL_IMPETUS = register("cracked_nickel_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> NICKEL_IMPETUS = register("nickel_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_NICKEL_IMPETUS));
    public static final DeferredHolder<Item, Item> NICKEL_NODE = register("nickel_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_URANIUM_IMPETUS = register("cracked_uranium_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> URANIUM_IMPETUS = register("uranium_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_URANIUM_IMPETUS));
    public static final DeferredHolder<Item, Item> URANIUM_NODE = register("uranium_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_OSMIUM_IMPETUS = register("cracked_osmium_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> OSMIUM_IMPETUS = register("osmium_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_OSMIUM_IMPETUS));
    public static final DeferredHolder<Item, Item> OSMIUM_NODE = register("osmium_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_ZINC_IMPETUS = register("cracked_zinc_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> ZINC_IMPETUS = register("zinc_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ZINC_IMPETUS));
    public static final DeferredHolder<Item, Item> ZINC_NODE = register("zinc_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_TIN_IMPETUS = register("cracked_tin_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> TIN_IMPETUS = register("tin_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_TIN_IMPETUS));
    public static final DeferredHolder<Item, Item> TIN_NODE = register("tin_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);

    public static final DeferredHolder<Item, CrackedImpetusItem> CRACKED_ALCHEMICAL_IMPETUS = register("cracked_alchemical_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final DeferredHolder<Item, ImpetusItem> ALCHEMICAL_IMPETUS = register("alchemical_impetus", METALLURGIC_PROPERTIES(), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ALCHEMICAL_IMPETUS));
    //endregion

    //region ether
    public static final DeferredHolder<Item, Item> ETHER = register("ether", DEFAULT_PROPERTIES(), (p) -> new EtherItem(BlockRegistry.ETHER.get(), p, false));
    public static final DeferredHolder<Item, Item> ETHER_TORCH = register("ether_torch", DEFAULT_PROPERTIES(), (p) -> new EtherTorchItem(BlockRegistry.ETHER_TORCH.get(), BlockRegistry.WALL_ETHER_TORCH.get(), p, false));
    public static final DeferredHolder<Item, Item> TAINTED_ETHER_BRAZIER = register("tainted_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TAINTED_ETHER_BRAZIER.get(), p, false));
    public static final DeferredHolder<Item, Item> TWISTED_ETHER_BRAZIER = register("twisted_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TWISTED_ETHER_BRAZIER.get(), p, false));

    public static final DeferredHolder<Item, Item> IRIDESCENT_ETHER = register("iridescent_ether", DEFAULT_PROPERTIES(), (p) -> new EtherItem(BlockRegistry.IRIDESCENT_ETHER.get(), p, true));
    public static final DeferredHolder<Item, Item> IRIDESCENT_ETHER_TORCH = register("iridescent_ether_torch", DEFAULT_PROPERTIES(), (p) -> new EtherTorchItem(BlockRegistry.IRIDESCENT_ETHER_TORCH.get(), BlockRegistry.IRIDESCENT_WALL_ETHER_TORCH.get(), p, true));
    public static final DeferredHolder<Item, Item> TAINTED_IRIDESCENT_ETHER_BRAZIER = register("tainted_iridescent_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), p, true));
    public static final DeferredHolder<Item, Item> TWISTED_IRIDESCENT_ETHER_BRAZIER = register("twisted_iridescent_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), p, true));
    //endregion

    //region autmgnets
    public static final DeferredHolder<Item, Item> MENDING_DIFFUSER = register("mending_diffuser", DEFAULT_PROPERTIES(), MendingDiffuserItem::new);
    public static final DeferredHolder<Item, Item> IMPURITY_STABILIZER = register("impurity_stabilizer", DEFAULT_PROPERTIES(), ImpurityStabilizer::new);
    public static final DeferredHolder<Item, Item> SHIELDING_APPARATUS = register("shielding_apparatus", DEFAULT_PROPERTIES(), ShieldingApparatusItem::new);
    public static final DeferredHolder<Item, Item> WARPING_ENGINE = register("warping_engine", DEFAULT_PROPERTIES(), WarpingEngineItem::new);
    public static final DeferredHolder<Item, Item> ACCELERATING_INLAY = register("accelerating_inlay", DEFAULT_PROPERTIES(), AcceleratingInlayItem::new);
    public static final DeferredHolder<Item, Item> PRISMATIC_FOCUS_LENS = register("prismatic_focus_lens", DEFAULT_PROPERTIES(), PrismaticFocusLensItem::new);
    public static final DeferredHolder<Item, Item> BLAZING_DIODE = register("blazing_diode", DEFAULT_PROPERTIES(), BlazingDiodeItem::new);
    public static final DeferredHolder<Item, Item> INTRICATE_ASSEMBLY = register("intricate_assembly", DEFAULT_PROPERTIES(), IntricateAssemblyItem::new);

    public static final DeferredHolder<Item, Item> SYMPATHY_DRIVE = register("sympathy_drive", DEFAULT_PROPERTIES(), SympathyDrive::new);
    public static final DeferredHolder<Item, Item> SUSPICIOUS_DEVICE = register("suspicious_device", DEFAULT_PROPERTIES(), SuspiciousDeviceItem::new);
    public static final DeferredHolder<Item, Item> CAUSTIC_CATALYST = register("caustic_catalyst", DEFAULT_PROPERTIES(), CausticCatalystItem::new);
    public static final DeferredHolder<Item, Item> RESONANCE_TUNER = register("resonance_tuner", DEFAULT_PROPERTIES(), ResonanceTuner::new);

    public static final DeferredHolder<Item, Item> STELLAR_MECHANISM = register("stellar_mechanism", DEFAULT_PROPERTIES(), StellarMechanismItem::new);
    //endregion

    //region contents
    public static final DeferredHolder<Item, Item> CONCENTRATED_GLUTTONY = register("concentrated_gluttony", DEFAULT_PROPERTIES().food(FoodPropertyRegistry.CONCENTRATED_GLUTTONY), ConcentratedGluttonyItem::new);
    public static final DeferredHolder<Item, Item> SPLASH_OF_GLUTTONY = register("splash_of_gluttony", DEFAULT_PROPERTIES(), SplashOfGluttonyItem::new);
    public static final DeferredHolder<Item, Item> SOULWOVEN_POUCH = register("soulwoven_pouch", GEAR_PROPERTIES(), SoulwovenPouchItem::new);
    public static final DeferredHolder<Item, Item> TOTEMIC_STAFF = register("totemic_staff", GEAR_PROPERTIES(), TinkeringToolItem::new);
    public static final DeferredHolder<Item, Item> ARTIFICERS_CLAW = register("artificers_claw", GEAR_PROPERTIES(), TinkeringToolItem::new);
    public static final DeferredHolder<Item, Item> TUNING_FORK = register("tuning_fork", GEAR_PROPERTIES(), TinkeringToolItem::new);
    public static final DeferredHolder<Item, Item> LAMPLIGHTERS_TONGS = register("lamplighters_tongs", GEAR_PROPERTIES(), LamplightersTongsItem::new);

    public static final DeferredHolder<Item, Item> CATALYST_LOBBER = register("catalyst_lobber", GEAR_PROPERTIES(), (p) -> new CatalystLobberItem(p.durability(500), EthericNitrateEntity::new));

    public static final DeferredHolder<Item, Item> CRUDE_SCYTHE = register("crude_scythe", GEAR_PROPERTIES(), (p) -> new MalumScytheItem(Tiers.IRON, 0, 0.1f, p.durability(500)));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_SCYTHE = register("soul_stained_steel_scythe", GEAR_PROPERTIES(), (p) -> new MagicScytheItem(SOUL_STAINED_STEEL, -2.5f, 0.1f, 4, p));

    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_HELMET = register("soul_stained_steel_helmet", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.HELMET, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_CHESTPLATE = register("soul_stained_steel_chestplate", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.CHESTPLATE, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_LEGGINGS = register("soul_stained_steel_leggings", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.LEGGINGS, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_BOOTS = register("soul_stained_steel_boots", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.BOOTS, p));

    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_SWORD = register("soul_stained_steel_sword", GEAR_PROPERTIES(), (p) -> new MagicSwordItem(SOUL_STAINED_STEEL, -3, 0, 3, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_PICKAXE = register("soul_stained_steel_pickaxe", GEAR_PROPERTIES(), (p) -> new MagicPickaxeItem(SOUL_STAINED_STEEL, -2, 0, 2, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_AXE = register("soul_stained_steel_axe", GEAR_PROPERTIES(), (p) -> new MagicAxeItem(SOUL_STAINED_STEEL, -3, 0, 4, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_SHOVEL = register("soul_stained_steel_shovel", GEAR_PROPERTIES(), (p) -> new MagicShovelItem(SOUL_STAINED_STEEL, -2, 0, 2, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_HOE = register("soul_stained_steel_hoe", GEAR_PROPERTIES(), (p) -> new MagicHoeItem(SOUL_STAINED_STEEL, 0, -1.5f, 1, p));
    public static final DeferredHolder<Item, Item> SOUL_STAINED_STEEL_KNIFE = register("soul_stained_steel_knife", FarmersDelightCompat.LOADED ? GEAR_PROPERTIES() : HIDDEN_PROPERTIES(), (p) -> FarmersDelightCompat.LOADED ? FarmersDelightCompat.LoadedOnly.makeMagicKnife(p) : new Item(p));

    public static final DeferredHolder<Item, Item> SOUL_HUNTER_CLOAK = register("soul_hunter_cloak", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.HELMET, p));
    public static final DeferredHolder<Item, Item> SOUL_HUNTER_ROBE = register("soul_hunter_robe", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.CHESTPLATE, p));
    public static final DeferredHolder<Item, Item> SOUL_HUNTER_LEGGINGS = register("soul_hunter_leggings", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.LEGGINGS, p));
    public static final DeferredHolder<Item, Item> SOUL_HUNTER_BOOTS = register("soul_hunter_boots", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.BOOTS, p));

    public static final DeferredHolder<Item, Item> TYRVING = register("tyrving", GEAR_PROPERTIES(), (p) -> new TyrvingItem(ItemTiers.TYRVING, 0, -0.3f, p));

    public static final DeferredHolder<Item, Item> MNEMONIC_HEX_STAFF = register("mnemonic_hex_staff", GEAR_PROPERTIES(), (p) -> new HexStaffItem(HEX_STAFF, 5, 20, p));
    public static final DeferredHolder<Item, Item> EROSION_SCEPTER = register("erosion_scepter", GEAR_PROPERTIES(), (p) -> new ErosionScepterItem(MALIGNANT_ALLOY, 5, 10, p));

    public static final DeferredHolder<Item, Item> WEIGHT_OF_WORLDS = register("weight_of_worlds", GEAR_PROPERTIES(), (p) -> new WeightOfWorldsItem(ItemTiers.MALIGNANT_ALLOY, 1, -0.2f, p));
    public static final DeferredHolder<Item, Item> EDGE_OF_DELIVERANCE = register("edge_of_deliverance", GEAR_PROPERTIES(), (p) -> new EdgeOfDeliveranceItem(ItemTiers.MALIGNANT_ALLOY, 2, -0.1f, p));

    public static final DeferredHolder<Item, Item> MALIGNANT_STRONGHOLD_HELMET = register("malignant_stronghold_helmet", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.HELMET, p));
    public static final DeferredHolder<Item, Item> MALIGNANT_STRONGHOLD_CHESTPLATE = register("malignant_stronghold_chestplate", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.CHESTPLATE, p));
    public static final DeferredHolder<Item, Item> MALIGNANT_STRONGHOLD_LEGGINGS = register("malignant_stronghold_leggings", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.LEGGINGS, p));
    public static final DeferredHolder<Item, Item> MALIGNANT_STRONGHOLD_BOOTS = register("malignant_stronghold_boots", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.BOOTS, p));

    public static final DeferredHolder<Item, Item> UNWINDING_CHAOS = register("unwinding_chaos", GEAR_PROPERTIES().rarity(EPIC), (p) -> new UnwindingChaosStaffItem(HARNESSED_CHAOS, 7, 30, p));
    public static final DeferredHolder<Item, Item> SUNDERING_ANCHOR = register("sundering_anchor", GEAR_PROPERTIES().rarity(EPIC), (p) -> new SunderingAnchorItem(HARNESSED_CHAOS, 4, p));

    public static final DeferredHolder<Item, Item> GILDED_RING = register("gilded_ring", GEAR_PROPERTIES(), CurioGildedRing::new);
    public static final DeferredHolder<Item, Item> GILDED_BELT = register("gilded_belt", GEAR_PROPERTIES(), CurioGildedBelt::new);
    public static final DeferredHolder<Item, Item> ORNATE_RING = register("ornate_ring", GEAR_PROPERTIES(), CurioOrnateRing::new);
    public static final DeferredHolder<Item, Item> ORNATE_NECKLACE = register("ornate_necklace", GEAR_PROPERTIES(), CurioOrnateNecklace::new);

    public static final DeferredHolder<Item, Item> RUNIC_BROOCH = register("runic_brooch", GEAR_PROPERTIES(), CurioRunicBrooch::new);
    public static final DeferredHolder<Item, Item> ELABORATE_BROOCH = register("elaborate_brooch", GEAR_PROPERTIES(), CurioElaborateBrooch::new);
    public static final DeferredHolder<Item, Item> GLASS_BROOCH = register("glass_brooch", GEAR_PROPERTIES(), CurioGlassBrooch::new);
    public static final DeferredHolder<Item, Item> GLUTTONOUS_BROOCH = register("gluttonous_brooch", GEAR_PROPERTIES(), CurioGluttonousBrooch::new);

    public static final DeferredHolder<Item, Item> RING_OF_ESOTERIC_SPOILS = register("ring_of_esoteric_spoils", GEAR_PROPERTIES(), CurioArcaneSpoilRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_THE_RISING_EDGE = register("ring_of_the_rising_edge", GEAR_PROPERTIES(), CurioRisingEdgeRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_CURATIVE_TALENT = register("ring_of_curative_talent", GEAR_PROPERTIES(), CurioCurativeRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_ARCANE_PROWESS = register("ring_of_arcane_prowess", GEAR_PROPERTIES(), CurioProwessRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_MANAWEAVING = register("ring_of_manaweaving", GEAR_PROPERTIES(), CurioManaweavingRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_ALCHEMICAL_MASTERY = register("ring_of_alchemical_mastery", GEAR_PROPERTIES(), CurioAlchemicalRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_DESPERATE_VORACITY = register("ring_of_desperate_voracity", GEAR_PROPERTIES(), CurioVoraciousRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_THE_HOARDER = register("ring_of_the_hoarder", GEAR_PROPERTIES(), CurioHoarderRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_THE_DEMOLITIONIST = register("ring_of_the_demolitionist", GEAR_PROPERTIES(), CurioDemolitionistRing::new);

    public static final DeferredHolder<Item, Item> NECKLACE_OF_THE_MYSTIC_MIRROR = register("necklace_of_the_mystic_mirror", GEAR_PROPERTIES(), CurioMirrorNecklace::new);
    public static final DeferredHolder<Item, Item> NECKLACE_OF_THE_NARROW_EDGE = register("necklace_of_the_narrow_edge", GEAR_PROPERTIES(), CurioNarrowEdgeNecklace::new);
    public static final DeferredHolder<Item, Item> NECKLACE_OF_BLISSFUL_HARMONY = register("necklace_of_blissful_harmony", GEAR_PROPERTIES(), CurioHarmonyNecklace::new);

    public static final DeferredHolder<Item, Item> BELT_OF_THE_STARVED = register("belt_of_the_starved", GEAR_PROPERTIES(), CurioStarvedBelt::new);
    public static final DeferredHolder<Item, Item> BELT_OF_THE_PROSPECTOR = register("belt_of_the_prospector", GEAR_PROPERTIES(), CurioProspectorBelt::new);
    public static final DeferredHolder<Item, Item> BELT_OF_THE_MAGEBANE = register("belt_of_the_magebane", GEAR_PROPERTIES(), CurioMagebaneBelt::new);

    public static final DeferredHolder<Item, Item> RING_OF_THE_ENDLESS_WELL = register("ring_of_the_endless_well", GEAR_PROPERTIES(), CurioEndlessRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_ECHOING_ARCANA = register("ring_of_echoing_arcana", GEAR_PROPERTIES(), CurioEchoingArcanaRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_GROWING_FLESH = register("ring_of_growing_flesh", GEAR_PROPERTIES(), CurioGrowingFleshRing::new);
    public static final DeferredHolder<Item, Item> RING_OF_GRUESOME_CONCENTRATION = register("ring_of_gruesome_concentration", GEAR_PROPERTIES(), CurioGruesomeConcentrationRing::new);
    public static final DeferredHolder<Item, Item> NECKLACE_OF_THE_HIDDEN_BLADE = register("necklace_of_the_hidden_blade", GEAR_PROPERTIES(), CurioHiddenBladeNecklace::new);
    public static final DeferredHolder<Item, Item> NECKLACE_OF_THE_WATCHER = register("necklace_of_the_watcher", GEAR_PROPERTIES(), CurioWatcherNecklace::new);
    public static final DeferredHolder<Item, Item> BELT_OF_THE_LIMITLESS = register("belt_of_the_limitless", GEAR_PROPERTIES(), CurioLimitlessBelt::new);


    public static final DeferredHolder<Item, Item> RUNE_OF_IDLE_RESTORATION = register("rune_of_idle_restoration", GEAR_PROPERTIES(), RuneIdleRestorationItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_CULLING = register("rune_of_culling", GEAR_PROPERTIES(), RuneCullingItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_REINFORCEMENT = register("rune_of_reinforcement", GEAR_PROPERTIES(), RuneReinforcementItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_VOLATILE_DISTORTION = register("rune_of_volatile_distortion", GEAR_PROPERTIES(), RuneVolatileDistortionItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_DEXTERITY = register("rune_of_dexterity", GEAR_PROPERTIES(), RuneDexterityItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_ALIMENT_CLEANSING = register("rune_of_aliment_cleansing", GEAR_PROPERTIES(), RuneAlimentCleansingItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_REACTIVE_SHIELDING = register("rune_of_reactive_shielding", GEAR_PROPERTIES(), RuneReactiveShieldingItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_FERVOR = register("rune_of_fervor", GEAR_PROPERTIES(), RuneFervorItem::new);

    public static final DeferredHolder<Item, Item> RUNE_OF_MOTION = register("rune_of_motion", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.AERIAL_RITE, false));
    public static final DeferredHolder<Item, Item> RUNE_OF_LOYALTY = register("rune_of_loyalty", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.AQUEOUS_RITE, false));
    public static final DeferredHolder<Item, Item> RUNE_OF_WARDING = register("rune_of_warding", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.EARTHEN_RITE, false));
    public static final DeferredHolder<Item, Item> RUNE_OF_HASTE = register("rune_of_haste", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.INFERNAL_RITE, false));
    public static final DeferredHolder<Item, Item> RUNE_OF_THE_AETHER = register("rune_of_the_aether", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.AERIAL_RITE, true));
    public static final DeferredHolder<Item, Item> RUNE_OF_THE_SEAS = register("rune_of_the_seas", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.AQUEOUS_RITE, true));
    public static final DeferredHolder<Item, Item> RUNE_OF_THE_ARENA = register("rune_of_the_arena", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.EARTHEN_RITE, true));
    public static final DeferredHolder<Item, Item> RUNE_OF_THE_HELLS = register("rune_of_the_hells", GEAR_PROPERTIES(), p -> new TotemicRuneCurioItem(p, SpiritRiteRegistry.INFERNAL_RITE, true, 10));

    public static final DeferredHolder<Item, Item> RUNE_OF_BOLSTERING = register("rune_of_bolstering", GEAR_PROPERTIES(), RuneBolsteringItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_SACRIFICIAL_EMPOWERMENT = register("rune_of_sacrificial_empowerment", GEAR_PROPERTIES(), RuneSacrificialEmpowermentItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_SPELL_MASTERY = register("rune_of_spell_mastery", GEAR_PROPERTIES(), RuneSpellMasteryItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_THE_HERETIC = register("rune_of_the_heretic", GEAR_PROPERTIES(), RuneHereticItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_UNNATURAL_STAMINA = register("rune_of_unnatural_stamina", GEAR_PROPERTIES(), RuneUnnaturalStaminaItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_TWINNED_DURATION = register("rune_of_twinned_duration", GEAR_PROPERTIES(), RuneTwinnedDurationItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_TOUGHNESS = register("rune_of_toughness", GEAR_PROPERTIES(), RuneToughnessItem::new);
    public static final DeferredHolder<Item, Item> RUNE_OF_IGNEOUS_SOLACE = register("rune_of_igneous_solace", GEAR_PROPERTIES(), RuneIgneousSolaceItem::new);
    //endregion

    //region cosmetics
    public static final DeferredHolder<Item, Item> ESOTERIC_SPOOL = register("esoteric_spool", COSMETIC_PROPERTIES(), Item::new);
    public static final DeferredHolder<Item, Item> ANCIENT_WEAVE = register("ancient_weave", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final DeferredHolder<Item, Item> CORNERED_WEAVE = register("cornered_weave", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final DeferredHolder<Item, Item> DREADED_WEAVE = register("dreaded_weave", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final DeferredHolder<Item, Item> MECHANICAL_WEAVE_V1 = register("mechanical_weave_v1", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final DeferredHolder<Item, Item> MECHANICAL_WEAVE_V2 = register("mechanical_weave_v2", COSMETIC_PROPERTIES(), GenericWeaveItem::new);

    public static final DeferredHolder<Item, PrideweaveItem> ACE_PRIDEWEAVE = register("ace_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> AGENDER_PRIDEWEAVE = register("agender_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> ARO_PRIDEWEAVE = register("aro_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> AROACE_PRIDEWEAVE = register("aroace_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> BI_PRIDEWEAVE = register("bi_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> DEMIBOY_PRIDEWEAVE = register("demiboy_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> DEMIGIRL_PRIDEWEAVE = register("demigirl_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> ENBY_PRIDEWEAVE = register("enby_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> GAY_PRIDEWEAVE = register("gay_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> GENDERFLUID_PRIDEWEAVE = register("genderfluid_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> GENDERQUEER_PRIDEWEAVE = register("genderqueer_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> INTERSEX_PRIDEWEAVE = register("intersex_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> LESBIAN_PRIDEWEAVE = register("lesbian_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> PAN_PRIDEWEAVE = register("pan_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> PLURAL_PRIDEWEAVE = register("plural_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> POLY_PRIDEWEAVE = register("poly_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> PRIDE_PRIDEWEAVE = register("pride_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final DeferredHolder<Item, PrideweaveItem> TRANS_PRIDEWEAVE = register("trans_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);

    public static final DeferredHolder<Item, Item> TOPHAT = register("tophat", COSMETIC_PROPERTIES().stacksTo(1), CurioTopHat::new);

    //endregion

    //region hidden items
    public static final DeferredHolder<Item, Item> SOUL_OF_A_SCYTHE = register("soul_of_a_scythe", HIDDEN_PROPERTIES(), TemporarilyDisabledItem::new);

    public static final DeferredHolder<Item, Item> THE_DEVICE = register("the_device", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.THE_DEVICE.get(), p));
    public static final DeferredHolder<Item, Item> THE_VESSEL = register("the_vessel", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.THE_VESSEL.get(), p));
    public static final DeferredHolder<Item, Item> TOKEN_OF_GRATITUDE = register("token_of_gratitude", HIDDEN_PROPERTIES(), CurioTokenOfGratitude::new);

    public static final DeferredHolder<Item, Item> PRIMORDIAL_SOUP = register("primordial_soup", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.PRIMORDIAL_SOUP.get(), p));
    public static final DeferredHolder<Item, Item> VOID_CONDUIT = register("void_conduit", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VOID_CONDUIT.get(), p));
    public static final DeferredHolder<Item, Item> VOID_DEPOT = register("void_depot", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VOID_DEPOT.get(), p));

    public static final DeferredHolder<Item, Item> WEEPING_WELL_BRICKS = register("weeping_well_bricks", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_BRICKS.get(), p));
    public static final DeferredHolder<Item, Item> WEEPING_WELL_ENCASEMENT = register("weeping_well_encasement", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_ENCASEMENT.get(), p));
    public static final DeferredHolder<Item, Item> WEEPING_WELL_ENCASEMENT_MIRRORED = register("weeping_well_encasement_mirrored", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_ENCASEMENT_MIRRORED.get(), p));
    public static final DeferredHolder<Item, Item> WEEPING_WELL_ENCASEMENT_CORNER = register("weeping_well_encasement_corner", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_ENCASEMENT_CORNER.get(), p));
    public static final DeferredHolder<Item, Item> WEEPING_WELL_CENTRAL_ENCASEMENT = register("weeping_well_central_encasement", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_CENTRAL_ENCASEMENT.get(), p));
    public static final DeferredHolder<Item, Item> WEEPING_WELL_CENTRAL_ENCASEMENT_SUPPORT = register("weeping_well_central_encasement_support", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_CENTRAL_ENCASEMENT_SUPPORT.get(), p));
    public static final DeferredHolder<Item, Item> WEEPING_WELL_CENTRAL_PILLAR = register("weeping_well_central_pillar", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_CENTRAL_PILLAR.get(), p));
    public static final DeferredHolder<Item, Item> WEEPING_WELL_SIDE_PILLAR = register("weeping_well_side_pillar", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_SIDE_PILLAR.get(), p));
    //endregion

    @EventBusSubscriber(modid = MalumMod.MALUM, bus = EventBusSubscriber.Bus.MOD)
    public static class Common {
        @SubscribeEvent
        public static void registerCompost(FMLCommonSetupEvent event) {
            registerCompostable(RUNEWOOD_LEAVES, 0.3f);
            registerCompostable(HANGING_RUNEWOOD_LEAVES, 0.2f);
            registerCompostable(SOULWOOD_LEAVES, 0.3f);
            registerCompostable(HANGING_SOULWOOD_LEAVES, 0.2f);
            registerCompostable(RUNEWOOD_SAPLING, 0.3f);
            registerCompostable(SOULWOOD_GROWTH, 0.3f);
        }

        public static void registerCompostable(DeferredHolder<Item, Item> item, float chance) {
            ComposterBlock.COMPOSTABLES.put(item.get(), chance);
        }
    }


    @EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent
        public static void registerExtras(FMLClientSetupEvent event) {
            CuriosRendererRegistry.register(ItemRegistry.TOKEN_OF_GRATITUDE.get(), TokenOfGratitudeRenderer::new);
            CuriosRendererRegistry.register(ItemRegistry.TOPHAT.get(), TopHatCurioRenderer::new);

            HiddenTagRegistry.registerHiddenTags();
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void addItemProperties(FMLClientSetupEvent event) {
            Set<LodestoneArmorItem> armors = ItemRegistry.ITEMS.getEntries().stream().filter(r -> r.get() instanceof LodestoneArmorItem).map(r -> (LodestoneArmorItem) r.get()).collect(Collectors.toSet());
            ItemPropertyFunction armorPropertyFunction = (stack, level, holder, holderID) -> {
                if (!stack.has(DataComponentRegistry.ITEM_SKIN)) {
                    return -1;
                }
                var skin = stack.get(DataComponentRegistry.ITEM_SKIN.get());
                ArmorSkin armorSkin = ArmorSkinRegistry.SKINS.get(skin);
                if (armorSkin == null) {
                    return -1;
                }
                return armorSkin.index;
            };
            for (LodestoneArmorItem armor : armors) {
                ItemProperties.register(armor, MalumMod.malumPath("item_skin"), armorPropertyFunction);
            }

            ItemProperties.register(
                    SOULWOVEN_POUCH.get(),
                    MalumMod.malumPath("filled"),
                    (stack, level, holder, holderID) -> SoulwovenPouchItem.getFullnessDisplay(stack));
            ItemProperties.register(
                    SOULWOVEN_BANNER.get(),
                    MalumMod.malumPath("pattern"),
                    (stack, level, holder, holderID) -> SoulwovenBannerBlockItem.getBannerPattern(stack));
            ItemProperties.register(
                    RITUAL_SHARD.get(),
                    MalumMod.malumPath("tier"),
                    (stack, level, holder, holderID) -> RitualShardItem.getStateDisplay(stack));
            ItemProperties.register(
                    CATALYST_LOBBER.get(),
                    MalumMod.malumPath("state"),
                    (stack, level, holder, holderID) -> CatalystLobberItem.getStateDisplay(stack));
        }

        @SubscribeEvent
        public static void setItemColors(RegisterColorHandlersEvent.Item event) {
            ItemColors itemColors = event.getItemColors();
            HashSet<DeferredHolder<Item, ? extends Item>> items = new HashSet<>(ITEMS.getEntries());

            event.register((stack, tintIndex) -> ColorHelper.getColor(((IGradientedLeavesBlock) ((BlockItem) stack.getItem()).getBlock()).getMaxColor()),
                    RUNEWOOD_LEAVES.get(), HANGING_RUNEWOOD_LEAVES.get(), AZURE_RUNEWOOD_LEAVES.get(), HANGING_AZURE_RUNEWOOD_LEAVES.get());
            event.register((stack, tintIndex) -> ColorHelper.getColor(((IGradientedLeavesBlock) ((BlockItem) stack.getItem()).getBlock()).getMinColor()),
                    SOULWOOD_LEAVES.get(), HANGING_SOULWOOD_LEAVES.get());

            DataHelper.takeAll(items, i -> i.get() instanceof EtherTorchItem || i.get() instanceof EtherBrazierItem).forEach(i -> event.register((s, c) -> {
                AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                switch (c) {
                    case 2 -> {
                        return etherItem.getSecondColor(s);
                    }
                    case 1 -> {
                        return etherItem.getFirstColor(s);
                    }
                    default -> {
                        return -1;
                    }
                }
            }, i.get()));
            DataHelper.takeAll(items, i -> i.get() instanceof EtherItem).forEach(i -> event.register((s, c) -> {
                AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                return c == 0 ? etherItem.getFirstColor(s) : etherItem.getSecondColor(s);
            }, i.get()));

            DataHelper.takeAll(items, i -> i.get() instanceof SpiritShardItem).forEach(item ->
                    event.register((s, c) -> ColorHelper.getColor(((SpiritShardItem) item.get()).type.getItemColor()), item.get()));

            event.register((s, c) -> {
                var data = s.get(DataComponentRegistry.RITUAL_DATA);
                if (data == null) {
                    return -1;
                }
                return ColorHelper.getColor(data.ritualType().spirit.getItemColor());
            }, RITUAL_SHARD.get());
        }
    }
}
