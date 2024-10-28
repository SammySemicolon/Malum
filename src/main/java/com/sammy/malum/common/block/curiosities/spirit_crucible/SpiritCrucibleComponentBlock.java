package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.multiblock.MultiBlockComponentEntity;
import team.lodestar.lodestone.systems.multiblock.MultiblockComponentBlock;

import java.util.Optional;

public class SpiritCrucibleComponentBlock extends MultiblockComponentBlock {
    public static final VoxelShape SHAPE = makeShape();
    public static final VoxelShape RENDER_SHAPE = makeRenderShape();

    public SpiritCrucibleComponentBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return ItemRegistry.SPIRIT_CRUCIBLE.get().getDefaultInstance();
    }

    @Override
    public VoxelShape getInteractionShape(BlockState p_60547_, BlockGetter p_60548_, BlockPos p_60549_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return RENDER_SHAPE;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof MultiBlockComponentEntity component) {
            if (Capabilities.ItemHandler.BLOCK.getCapability(pLevel, pPos, pState, component, null) instanceof IItemHandler inventory) {
                return ItemHandlerHelper.calcRedstoneFromInventory(inventory);
            }
        }
        return 0;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.25, 0.75, 0.6875, 0.375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.25, 0, 0.6875, 0.375, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.25, 0.3125, 1, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.25, 0.3125, 0.25, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0, 1, 0, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0.6875, 1, 0, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.6875, 0.3125, 0, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.3125, 0, 0.3125), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeRenderShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, -0.125, 0.75, 0.6875, 0.375, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, -0.125, -0.125, 0.6875, 0.375, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, -0.125, 0.3125, 1.125, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, -0.125, 0.3125, 0.25, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0, 1, 0, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0.6875, 1, 0, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.6875, 0.3125, 0, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.3125, 0, 0.3125), BooleanOp.OR);

        return shape;
    }
}
