package com.sammy.malum.data.block;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.banner.*;
import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlock;
import com.sammy.malum.common.block.curiosities.weeping_well.PrimordialSoupBlock;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.data.item.MalumItemModelSmithTypes;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.statesmith.BlockStateSmith;

import java.util.function.Function;

import static com.sammy.malum.MalumMod.malumPath;

public class MalumBlockStateSmithTypes {

    public static BlockStateSmith<TotemPoleBlock> TOTEM_POLE = new BlockStateSmith<>(TotemPoleBlock.class, ItemModelSmithTypes.NO_MODEL, (block, provider) -> {
        String name = provider.getBlockName(block);
        String woodName = name.substring(0, 8);
        ResourceLocation parent = malumPath("block/templates/template_totem_pole");
        ResourceLocation side = provider.getBlockTexture(woodName + "_log");
        ResourceLocation top = provider.getBlockTexture(woodName + "_log_top");
        provider.getVariantBuilder(block).forAllStates(s -> {
            String type = s.getValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY);
            MalumSpiritType spiritType = SpiritTypeRegistry.SPIRITS.get(type);
            ResourceLocation front = provider.modLoc("block/totem_poles/" + spiritType.getIdentifier() + "_" + woodName + "_cutout");
            ModelFile pole = provider.models().withExistingParent(name + "_" + spiritType.getIdentifier(), parent)
                    .texture("side", side)
                    .texture("top", top)
                    .texture("front", front);
            return ConfiguredModel.builder().modelFile(pole).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    });

    public static BlockStateSmith<SoulwovenBannerBlock> SOULWOVEN_BANNER = new BlockStateSmith<>(SoulwovenBannerBlock.class, MalumItemModelSmithTypes.SOULWOVEN_BANNER, (block, provider) -> {
        ResourceLocation hanging = malumPath("block/soulwoven_banner");
        ResourceLocation mounted = malumPath("block/soulwoven_banner_directional");
        provider.getVariantBuilder(block).forAllStates(s -> {
            var value = s.getValue(SoulwovenBannerBlock.BANNER_TYPE);
            boolean isVertical = value.direction.getAxis().isVertical();
            Direction direction = isVertical ? (value.equals(SoulwovenBannerBlock.BannerType.HANGING_Z) ? Direction.NORTH : Direction.WEST) : value.direction;
            ResourceLocation model = isVertical ? hanging : mounted;
            return ConfiguredModel.builder().modelFile(provider.models().getExistingFile(model)).rotationY(((int) direction.toYRot()) % 360).build();
        });
    });

    public static BlockStateSmith<RepairPylonComponentBlock> REPAIR_PYLON_COMPONENT = new BlockStateSmith<>(RepairPylonComponentBlock.class, ItemModelSmithTypes.NO_MODEL, (block, provider) -> {
        ModelFile model = provider.models().getExistingFile(malumPath("block/repair_pylon_component_middle"));
        ModelFile topModel = provider.models().getExistingFile(malumPath("block/repair_pylon_component_top"));
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(RepairPylonComponentBlock.TOP) ? topModel : model).build());
    });

    public static BlockStateSmith<PrimordialSoupBlock> PRIMORDIAL_SOUP = new BlockStateSmith<>(PrimordialSoupBlock.class, ItemModelSmithTypes.AFFIXED_BLOCK_MODEL.apply("_top"), (block, provider) -> {
        String name = provider.getBlockName(block);
        ModelFile model = provider.models().withExistingParent(name, ResourceLocation.withDefaultNamespace("block/powder_snow")).texture("texture", malumPath("block/weeping_well/" + name));
        ModelFile topModel = provider.models().getExistingFile(malumPath("block/" + name + "_top"));
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(PrimordialSoupBlock.TOP) ? topModel : model).build());
    });

    public static BlockStateSmith<Block> HANGING_LEAVES = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.AFFIXED_BLOCK_TEXTURE_MODEL.apply("_0"), (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> modelProvider = (i) ->
                provider.models().withExistingParent(name+"_"+i, malumPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", provider.getBlockTexture(name + "_" + i)).texture("particle", provider.getBlockTexture(name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();

        for (int i = 0; i < 3; i++) {
            final ModelFile model = modelProvider.apply(i);
            builder = builder.modelFile(model)
                    .nextModel().modelFile(model).rotationY(90)
                    .nextModel().modelFile(model).rotationY(180)
                    .nextModel().modelFile(model).rotationY(270);
            if (i != 2) {
                builder = builder.nextModel();
            }
        }
        builder.addModel();
    });

    public static BlockStateSmith<ClingingBlightBlock> CLINGING_BLIGHT = new BlockStateSmith<>(ClingingBlightBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation creeping = malumPath("block/templates/template_creeping_blight");
        ResourceLocation creepingWall = malumPath("block/templates/template_creeping_blight_wall");
        ResourceLocation creepingCeiling = malumPath("block/templates/template_creeping_blight_ceiling");
        provider.getVariantBuilder(block).forAllStates(s -> {
            final ClingingBlightBlock.BlightType value = s.getValue(ClingingBlightBlock.BLIGHT_TYPE);
            final String valueName = value.getSerializedName();
            ResourceLocation parent = creepingWall;
            if (value.equals(ClingingBlightBlock.BlightType.HANGING_ROOTS) || value.equals(ClingingBlightBlock.BlightType.GROUNDED_ROOTS) || value.equals(ClingingBlightBlock.BlightType.HANGING_BLIGHT_CONNECTION)) {
                parent = creeping;
            }
            if (value.equals(ClingingBlightBlock.BlightType.HANGING_BLIGHT)) {
                parent = creepingCeiling;
            }
            ResourceLocation texture = provider.getBlockTexture(valueName);
            ResourceLocation smallTexture = provider.getBlockTexture(valueName +"_small");
            ModelBuilder model = provider.models().withExistingParent(name+"_"+ valueName, parent).texture("big", texture).texture("small", smallTexture).texture("particle", texture);
            if (!parent.equals(creeping)) {
                ResourceLocation bracingTexture = provider.getBlockTexture(valueName +"_bracing");
                model.texture("bracing", bracingTexture);
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    });

    public static BlockStateSmith<Block> BLIGHTED_BLOCK = new BlockStateSmith<>(Block.class, (block, provider) -> {
        String name = provider.getBlockName(block);

        ModelFile soil0 = provider.models().cubeAll(name, malumPath("block/" + name + "_0"));
        ModelFile soil1 = provider.models().cubeAll(name + "_1", malumPath("block/" + name + "_1"));

        provider.getVariantBuilder(block).partialState().modelForState()
                .modelFile(soil0)
                .nextModel().modelFile(soil0).rotationY(90)
                .nextModel().modelFile(soil0).rotationY(180)
                .nextModel().modelFile(soil0).rotationY(270)
                .nextModel().modelFile(soil1)
                .nextModel().modelFile(soil1).rotationY(90)
                .nextModel().modelFile(soil1).rotationY(180)
                .nextModel().modelFile(soil1).rotationY(270)
                .addModel();
    });

    public static BlockStateSmith<Block> BLIGHTED_GROWTH = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.NO_MODEL, (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> tumorFunction = (i) -> provider.models().withExistingParent(name + "_" + i, ResourceLocation.withDefaultNamespace("block/cross")).texture("cross", malumPath("block/" + name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();
        for (int i = 0; i < 10; i++) {
            builder = builder.modelFile(tumorFunction.apply(i));
            if (i != 9) {
                builder = builder.nextModel();
            }
        }
        builder.addModel();
    });

    public static BlockStateSmith<Block> CALCIFIED_BLIGHT = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> modelFunction = (s) -> provider.models().withExistingParent(name + "_" + s, ResourceLocation.withDefaultNamespace("block/cross")).texture("cross", malumPath("block/" + name + "_" + s));
        provider.getVariantBuilder(block).forAllStates(s -> {
            int value = s.getValue(CalcifiedBlightBlock.STAGE);
            return ConfiguredModel.builder().modelFile(modelFunction.apply(value)).build();
        });
    });

    public static BlockStateSmith<Block> TALL_CALCIFIED_BLIGHT = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.NO_MODEL, (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<String, ModelFile> modelFunction = (s) -> provider.models().withExistingParent(name + "_" + s, ResourceLocation.withDefaultNamespace("block/cross")).texture("cross", malumPath("block/" + name + "_" + s));
        provider.getVariantBuilder(block).forAllStates(s -> {
            int value = s.getValue(CalcifiedBlightBlock.STAGE);
            String prefix = "";
            if (s.hasProperty(TallCalcifiedBlightBlock.HALF) && s.getValue(TallCalcifiedBlightBlock.HALF).equals(DoubleBlockHalf.UPPER)) {
                prefix = "top_";
            }
            return ConfiguredModel.builder().modelFile(modelFunction.apply(prefix + value)).build();
        });
    });

    public static BlockStateSmith<EtherBrazierBlock> BRAZIER_BLOCK = new BlockStateSmith<>(EtherBrazierBlock.class, MalumItemModelSmithTypes.ETHER_BRAZIER_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String textureName = name.replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = provider.models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", provider.getBlockTexture(textureName)).texture("particle", provider.getBlockTexture(particleName));
        ModelFile brazier_hanging = provider.models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", provider.getBlockTexture(textureName)).texture("particle", provider.getBlockTexture(particleName));

        provider.getVariantBuilder(block)
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    });

    public static BlockStateSmith<EtherBrazierBlock> IRIDESCENT_BRAZIER_BLOCK = new BlockStateSmith<>(EtherBrazierBlock.class, MalumItemModelSmithTypes.IRIDESCENT_ETHER_BRAZIER_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String textureName = name.replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = provider.models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", provider.getBlockTexture(textureName)).texture("particle", provider.getBlockTexture(particleName));
        ModelFile brazier_hanging = provider.models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", provider.getBlockTexture(textureName)).texture("particle", provider.getBlockTexture(particleName));

        provider.getVariantBuilder(block)
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    });
}
