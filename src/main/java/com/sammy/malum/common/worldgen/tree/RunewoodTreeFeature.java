package com.sammy.malum.common.worldgen.tree;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.nature.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.worldgen.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.common.worldgen.WorldgenHelper.*;
import static team.lodestar.lodestone.systems.worldgen.LodestoneBlockFiller.*;

public class RunewoodTreeFeature extends Feature<RunewoodTreeConfiguration> {

    public static final LodestoneLayerToken LOGS = new LodestoneLayerToken();
    public static final LodestoneLayerToken LEAVES = new LodestoneLayerToken();
    public static final LodestoneLayerToken HANGING_LEAVES = new LodestoneLayerToken();

    public RunewoodTreeFeature() {
        super(RunewoodTreeConfiguration.CODEC);
    }

    private int getSapBlockCount(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }

    private int getTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 7, 10);
    }

    private int getSideTrunkHeight(RandomSource random) {
        return Mth.nextInt(random, 0, 2);
    }

    private int getDownwardsBranchOffset(RandomSource random) {
        return Mth.nextInt(random, 2, 4);
    }

    private int getBranchLength(RandomSource random) {
        return Mth.nextInt(random, 2, 3);
    }

    private int getBranchHeight(RandomSource random) {
        return Mth.nextInt(random, 3, 5);
    }

    @Override
    public boolean place(FeaturePlaceContext<RunewoodTreeConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var config = context.config();
        if (level.isEmptyBlock(pos.below()) || !config.sapling.defaultBlockState().canSurvive(level, pos)) {
            return false;
        }
        var rand = context.random();
        var log = config.log;
        var logState = log.defaultBlockState();
        var filler = new LodestoneBlockFiller().addLayers(LOGS, LEAVES, HANGING_LEAVES);
        int sapBlockCount = getSapBlockCount(rand);
        int trunkHeight = getTrunkHeight(rand);
        var mutable = new BlockPos.MutableBlockPos().set(pos);

        for (int i = 0; i <= trunkHeight; i++) { //Main Trunk
            if (!canPlace(level, mutable)) {
                return false;
            }
            filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
            mutable.move(Direction.UP);
        }
        for (int i = 0; i < 4; i++) { //Side Trunk Stumps
            Direction direction = Direction.from2DDataValue(i);
            int sideTrunkHeight = getSideTrunkHeight(rand);
            if (sideTrunkHeight == 0) {
                continue;
            }
            mutable.set(pos).move(direction);
            addDownwardsTrunkConnections(logState, level, filler, mutable);
            for (int j = 0; j < sideTrunkHeight; j++) {
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
                mutable.move(Direction.UP);
            }
        }

        for (int i = 0; i < 4; i++) { //Branches
            Direction direction = Direction.from2DDataValue(i);
            int downwardsBranchOffset = getDownwardsBranchOffset(rand);
            int branchLength = getBranchLength(rand);
            int branchHeight = getBranchHeight(rand);

            mutable.set(pos);
            mutable.move(Direction.UP, trunkHeight-downwardsBranchOffset);

            for (int j = 0; j < branchLength; j++) {
                mutable.move(direction);
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(logState.setValue(RotatedPillarBlock.AXIS, direction.getAxis())));
            }
            for (int j = 0; j < branchHeight; j++) {
                if (!canPlace(level, mutable)) {
                    return false;
                }
                filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
                mutable.move(Direction.UP);
            }
            makeLeafBlob(config, filler, mutable.move(Direction.DOWN, branchHeight));
        }
        makeLeafBlob(config, filler, mutable.set(pos).move(Direction.UP, trunkHeight-1));

        ArrayList<BlockPos> sapBlockPositions = new ArrayList<>(filler.getLayer(LOGS).keySet());
        Collections.shuffle(sapBlockPositions);
        for (BlockPos blockPos : sapBlockPositions.subList(0, sapBlockCount)) {
            filler.getLayer(LOGS).replace(blockPos, e -> create(BlockStateHelper.getBlockStateWithExistingProperties(e.getState(), BlockRegistry.EXPOSED_RUNEWOOD_LOG.get().defaultBlockState())).build());
        }

        filler.fill(level);
        updateLeaves(level, filler.getLayer(LOGS).keySet());
        return true;
    }

    public void addDownwardsTrunkConnections(BlockState logState, WorldGenLevel level, LodestoneBlockFiller filler, BlockPos pos) {
        var mutable = pos.mutable();
        while (true) {
            mutable.move(Direction.DOWN);
            if (!canPlace(level, mutable)) {
                break;
            }
            filler.getLayer(LOGS).put(mutable.immutable(), create(logState));
        }
    }

    public void makeLeafBlob(RunewoodTreeConfiguration config, LodestoneBlockFiller filler, BlockPos pos) {
        int[] leafSizes = new int[]{1, 2, 2, 2, 1};
        int[] leafColors = new int[]{0, 1, 2, 3, 4};
        var mutable = pos.mutable();
        var leavesState = config.leaves.defaultBlockState();
        var hangingLeavesState = config.hangingLeaves.defaultBlockState();
        for (int i = 0; i < 5; i++) {
            mutable.move(Direction.UP);
            BlockStateEntry leavesEntry = create(leavesState.setValue(MalumLeavesBlock.COLOR, leafColors[i])).build();
            makeLeafSlice(filler.getLayer(LEAVES), mutable, leafSizes[i], leavesEntry);
        }
        mutable.set(pos).move(Direction.DOWN);
        for (int i = 0; i < 2; i++) {
            mutable.move(Direction.UP);
            BlockStateEntry hangingLeavesEntry = create(hangingLeavesState.setValue(MalumHangingLeavesBlock.COLOR, leafColors[i]))
                    .setDiscardPredicate((l, p, s) -> !filler.getLayer(LEAVES).containsKey(p.above()))
                    .build();
            makeLeafSlice(filler.getLayer(HANGING_LEAVES), mutable, leafSizes[i], hangingLeavesEntry);
        }
    }

    public void makeLeafSlice(LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int leavesSize, BlockStateEntry entry) {
        makeLeafSlice(layer, pos, leavesSize, ()->entry);
    }
    public void makeLeafSlice(LodestoneBlockFiller.LodestoneBlockFillerLayer layer, BlockPos pos, int leavesSize, Supplier<BlockStateEntry> entry) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                    continue;
                }
                layer.put(pos.offset(x, 0, z), entry.get());
            }
        }
    }

    public static boolean canPlace(WorldGenLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof MalumSaplingBlock || state.getBlock() instanceof BlightedGrowthBlock || level.isEmptyBlock(pos) || state.canBeReplaced();
    }
}