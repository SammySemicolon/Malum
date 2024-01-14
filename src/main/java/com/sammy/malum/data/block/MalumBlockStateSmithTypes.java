package com.sammy.malum.data.block;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlock;
import com.sammy.malum.common.block.curiosities.weeping_well.PrimordialSoupBlock;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.data.item.MalumItemModelSmithTypes;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
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
            ResourceLocation front = provider.modLoc("block/totem_poles/" + spiritType.identifier + "_" + woodName + "_cutout");
            ModelFile pole = provider.models().withExistingParent(name + "_" + spiritType.identifier, parent)
                    .texture("side", side)
                    .texture("top", top)
                    .texture("front", front);
            return ConfiguredModel.builder().modelFile(pole).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    });

    public static BlockStateSmith<PrimordialSoupBlock> PRIMORDIAL_SOUP = new BlockStateSmith<>(PrimordialSoupBlock.class, ItemModelSmithTypes.NO_MODEL, (block, provider) -> {
        String name = provider.getBlockName(block);
        ModelFile model = provider.models().withExistingParent(name, new ResourceLocation("block/powder_snow")).texture("texture", malumPath("block/weeping_well/" + name));
        ModelFile topModel = provider.models().getExistingFile(malumPath("block/" + name + "_top"));
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(PrimordialSoupBlock.TOP) ? topModel : model).build());
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
        Function<Integer, ModelFile> tumorFunction = (i) -> provider.models().withExistingParent(name + "_" + i, new ResourceLocation("block/cross")).texture("cross", malumPath("block/" + name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState()
                .modelFile(tumorFunction.apply(0));
        for (int i = 1; i <= 9; i++) {
            builder = builder.nextModel().modelFile(tumorFunction.apply(i));
        }
        builder.addModel();
    });

    public static BlockStateSmith<EtherBrazierBlock> BRAZIER_BLOCK = new BlockStateSmith<>(EtherBrazierBlock.class, MalumItemModelSmithTypes.ETHER_BRAZIER_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String textureName = name.replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = provider.models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));
        ModelFile brazier_hanging = provider.models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));

        provider.getVariantBuilder(block)
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    });

    public static BlockStateSmith<EtherBrazierBlock> IRIDESCENT_BRAZIER_BLOCK = new BlockStateSmith<>(EtherBrazierBlock.class, MalumItemModelSmithTypes.IRIDESCENT_ETHER_BRAZIER_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String textureName = name.replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = provider.models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));
        ModelFile brazier_hanging = provider.models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));

        provider.getVariantBuilder(block)
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    });
}
