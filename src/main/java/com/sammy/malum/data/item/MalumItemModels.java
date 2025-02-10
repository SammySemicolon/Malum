package com.sammy.malum.data.item;

import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.DataMapRegistry;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.itemsmith.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

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
        Set<Supplier<? extends Item>> items = new HashSet<>(ITEMS.getEntries());

        items.removeIf(i -> i.get() instanceof BlockItem);
        items.remove(GEAS);

        ItemModelSmithData data = new ItemModelSmithData(this, items::remove);
        MalumItemModelSmithTypes.PARENTED_ITEM.apply(ResourceLocation.parse("item/air")).act(data, SOUL_OF_A_SCYTHE).applyModifier(result -> {
            var separateTransforms = result.addSeparateTransformData();
            var guiModel = ItemModelSmithTypes.GENERATED_ITEM.addModelNameAffix("_gui").act(data, result::getItem);
            separateTransforms.perspective(ItemDisplayContext.GUI, guiModel.parentedToThis(existingFileHelper));
            separateTransforms.perspective(ItemDisplayContext.FIXED, guiModel.parentedToThis(existingFileHelper));
            separateTransforms.base(getBuilder("item/air"));
        });
        setTexturePath("cosmetic/weaves/pride/");
        MalumItemModelSmithTypes.GENERATED_ITEM.act(data,
                ACE_PRIDEWEAVE, AGENDER_PRIDEWEAVE, ARO_PRIDEWEAVE, AROACE_PRIDEWEAVE, BI_PRIDEWEAVE,
                DEMIBOY_PRIDEWEAVE, DEMIGIRL_PRIDEWEAVE, ENBY_PRIDEWEAVE, GAY_PRIDEWEAVE, GENDERFLUID_PRIDEWEAVE,
                GENDERQUEER_PRIDEWEAVE, INTERSEX_PRIDEWEAVE, LESBIAN_PRIDEWEAVE, PAN_PRIDEWEAVE, PLURAL_PRIDEWEAVE,
                POLY_PRIDEWEAVE, PRIDE_PRIDEWEAVE, TRANS_PRIDEWEAVE
        );
        setTexturePath("cosmetic/weaves/");
        MalumItemModelSmithTypes.GENERATED_ITEM.act(data,
                ANCIENT_WEAVE, CORNERED_WEAVE, MECHANICAL_WEAVE_V1, MECHANICAL_WEAVE_V2
        );

        setTexturePath("runes/");
        MalumItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractRuneCurioItem).collect(Collectors.toList()));

        setTexturePath("impetus/");
        MalumItemModelSmithTypes.IMPETUS_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ImpetusItem ||
                i.get() instanceof CrackedImpetusItem).collect(Collectors.toList())); //TODO: make this cleaner :3
        MalumItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof NodeItem).collect(Collectors.toList()));

        setTexturePath("");
        MalumItemModelSmithTypes.UMBRAL_SPIRIT_ITEM.act(data, UMBRAL_SPIRIT);
        MalumItemModelSmithTypes.SPIRIT_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SpiritShardItem).collect(Collectors.toList()));
        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof DiggerItem).collect(Collectors.toList()));
        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SwordItem).collect(Collectors.toList()));
        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, MNEMONIC_HEX_STAFF, EROSION_SCEPTER);
        MalumItemModelSmithTypes.HANDHELD_OVERLAY_ITEM.act(data, UNWINDING_CHAOS, SUNDERING_ANCHOR).forEach(result -> result.addModelLayerData().emissive(15, 15, 1));
        MalumItemModelSmithTypes.LARGE_HANDHELD_ITEM.act(data, CRUDE_SCYTHE, SOUL_STAINED_STEEL_SCYTHE, EDGE_OF_DELIVERANCE, WEIGHT_OF_WORLDS);
        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, SOUL_STAINED_STEEL_KNIFE, TUNING_FORK, LAMPLIGHTERS_TONGS, ARTIFICERS_CLAW, TOTEMIC_STAFF);
        MalumItemModelSmithTypes.CATALYST_LOBBER.act(data, CATALYST_LOBBER);
        MalumItemModelSmithTypes.SOULWOVEN_POUCH.act(data, SOULWOVEN_POUCH);

        MalumItemModelSmithTypes.SKIN_APPLICABLE_ARMOR_ITEM.act(data,
                SOUL_HUNTER_CLOAK, SOUL_HUNTER_ROBE, SOUL_HUNTER_LEGGINGS, SOUL_HUNTER_BOOTS,
                SOUL_STAINED_STEEL_HELMET, SOUL_STAINED_STEEL_CHESTPLATE, SOUL_STAINED_STEEL_LEGGINGS, SOUL_STAINED_STEEL_BOOTS,
                MALIGNANT_STRONGHOLD_HELMET, MALIGNANT_STRONGHOLD_CHESTPLATE, MALIGNANT_STRONGHOLD_LEGGINGS, MALIGNANT_STRONGHOLD_BOOTS);

        MalumItemModelSmithTypes.RITUAL_SHARD_ITEM.act(data, RITUAL_SHARD);

        MalumItemModelSmithTypes.GENERATED_ITEM.act(data, items);
    }

    @Override
    public String getName() {
        return "Malum Item Models";
    }
}
