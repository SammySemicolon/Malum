package com.sammy.malum.data.item;

import com.sammy.malum.*;
import com.sammy.malum.common.item.cosmetic.weaves.*;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.itemsmith.*;
import team.lodestar.lodestone.systems.datagen.providers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class MalumItemModels extends LodestoneItemModelProvider {
    public MalumItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArmorSkinRegistry.registerItemSkins(null);
        Set<Supplier<? extends Item>> items = new HashSet<>(ITEMS.getEntries());

        items.removeIf(i -> i.get() instanceof BlockItem);
        items.removeIf(i -> i.get() instanceof MalumScytheItem);
        items.removeIf(i -> i.get() instanceof WeightOfWorldsItem);
        items.removeIf(i -> i.get() instanceof TemporarilyDisabledItem);

        ItemModelSmithData data = new ItemModelSmithData(this, items::remove);

        setTexturePath("cosmetic/weaves/pride/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof PrideweaveItem).collect(Collectors.toList()));
        setTexturePath("cosmetic/weaves/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractWeaveItem).collect(Collectors.toList()));

        setTexturePath("runes/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractRuneCurioItem).collect(Collectors.toList()));

        setTexturePath("impetus/");
        MalumItemModelSmithTypes.IMPETUS_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ImpetusItem || i.get() instanceof CrackedImpetusItem).collect(Collectors.toList()));
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof NodeItem).collect(Collectors.toList()));

        setTexturePath("");
        MalumItemModelSmithTypes.UMBRAL_SPIRIT_ITEM.act(data, UMBRAL_SPIRIT);
        MalumItemModelSmithTypes.SPIRIT_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SpiritShardItem).collect(Collectors.toList()));

        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof DiggerItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SwordItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ModCombatItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractStaffItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, SOUL_STAINED_STEEL_KNIFE, TUNING_FORK, LAMPLIGHTERS_TONGS, TOTEMIC_STAFF);
        MalumItemModelSmithTypes.CATALYST_LOBBER.act(data, CATALYST_LOBBER);


        MalumItemModelSmithTypes.ARMOR_ITEM.act(data,
                SOUL_HUNTER_CLOAK, SOUL_HUNTER_ROBE, SOUL_HUNTER_LEGGINGS, SOUL_HUNTER_BOOTS,
                SOUL_STAINED_STEEL_HELMET, SOUL_STAINED_STEEL_CHESTPLATE, SOUL_STAINED_STEEL_LEGGINGS, SOUL_STAINED_STEEL_BOOTS);

        MalumItemModelSmithTypes.RITUAL_SHARD_ITEM.act(data, RITUAL_SHARD);

        ItemModelSmithTypes.GENERATED_ITEM.act(data, items);
    }

    @Override
    public String getName() {
        return "Malum Item Models";
    }
}
