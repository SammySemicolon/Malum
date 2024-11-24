package com.sammy.malum.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.registry.common.worldgen.StructureRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.DEFAULT_DIMENSION_PADDING;
import static net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.DEFAULT_LIQUID_SETTINGS;

public class WeepingWellStructure extends Structure {
    public static final MapCodec<WeepingWellStructure> CODEC = RecordCodecBuilder.<WeepingWellStructure>mapCodec(instance ->
            instance.group(WeepingWellStructure.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.INT.fieldOf("size").forGetter(provider -> provider.size),
                    Codec.INT.fieldOf("min_y").forGetter(provider -> provider.min),
                    Codec.INT.fieldOf("max_y").forGetter(provider -> provider.max),
                    Codec.INT.fieldOf("offset_in_ground").forGetter(provider -> provider.offsetInGround),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, WeepingWellStructure::new));



    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final int min;
    private final int max;
    private final int offsetInGround;
    private final int maxDistanceFromCenter;

    public WeepingWellStructure(Structure.StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, int min, int max, int offsetInGround, int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.min = min;
        this.max = max;
        this.offsetInGround = offsetInGround;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext pContext) {
        BlockPos blockPos = new BlockPos(pContext.chunkPos().getMinBlockX(), 0, pContext.chunkPos().getMinBlockZ());
        BlockPos validPos = new BlockPos(blockPos.getX(), getValidY(pContext.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), pContext.heightAccessor(), pContext.randomState())), blockPos.getZ());
        if (validPos.getY() != min - 1 && isSufficientlyFlat(pContext, validPos, 8)) {
            return JigsawPlacement.addPieces(
                    pContext,
                    this.startPool,
                    this.startJigsawName,
                    this.size,
                    validPos.below(-offsetInGround),
                    false,
                    Optional.empty(),
                    this.maxDistanceFromCenter,
                    PoolAliasLookup.EMPTY,
                    DEFAULT_DIMENSION_PADDING,
                    DEFAULT_LIQUID_SETTINGS
            );
            /*
             @NotNull  PoolAliasLookup aliasLookup,
            DimensionPadding dimensionPadding,
            LiquidSettings liquidSetting
             */
        }
        return Optional.empty();
    }

    public boolean isSufficientlyFlat(GenerationContext context, BlockPos origin, int check) {
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -check; x < check; x++) {
            for (int z = -check; z < check; z++) {
                blockPosList.add(origin.offset(x, 0, z));
            }
        }
        int count = 0;
        for (BlockPos pos : blockPosList) {
            NoiseColumn blockView = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
            if (blockView.getBlock(pos.getY()).isAir() && !blockView.getBlock(pos.below().getY()).isAir()) {
                count++;
            }
        }
        return count >= check * check * 2;
    }

    public int getValidY(NoiseColumn sample) {
        int maxLength = 0;
        int currentLength = 0;
        int maxIndex = min - 1;
        for (int i = min; i < max; i += size) {
            if (sample.getBlock(i).isAir()) {
                // check if there are at least size more true values
                int j = i + 1;
                while (j < max && sample.getBlock(j).isAir()) {
                    j++;
                }
                int sequenceLength = j - i;
                if (sequenceLength >= size) {
                    currentLength += sequenceLength;
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        maxIndex = i;
                    }
                    i = j - 1; // skip the sequence we just found
                }
            } else {
                currentLength = 0;
            }
        }
        return maxIndex;
    }

    @Override
    public StructureType<?> type() {
        return StructureRegistry.WEEPING_WELL.get();
    }
}
