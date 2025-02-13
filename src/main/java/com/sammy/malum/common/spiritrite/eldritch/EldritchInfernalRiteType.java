package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.*;
import com.sammy.malum.common.spiritrite.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.network.*;

import java.util.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchInfernalRiteType extends TotemicRiteType {
    public EldritchInfernalRiteType() {
        super("greater_infernal_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getBlocksAhead(totemBase).forEach(p -> {
                    BlockState state = level.getBlockState(p);
                    Optional<SmeltingRecipe> optional = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(state.getBlock().asItem(), 1)), level);
                    if (optional.isPresent()) {
                        SmeltingRecipe recipe = optional.get();
                        ItemStack output = recipe.getResultItem(level.registryAccess());
                        if (output.getItem() instanceof BlockItem) {
                            Block block = ((BlockItem) output.getItem()).getBlock();
                            BlockState newState = block.defaultBlockState();
                            level.setBlockAndUpdate(p, newState);
                            level.levelEvent(2001, p, Block.getId(newState));
                            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(p)), new BlockSparkleParticlePacket(INFERNAL_SPIRIT.getPrimaryColor(), p));
                        }
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyBlocks(totemBase, AbstractFurnaceBlock.class).map(level::getBlockEntity).filter(e -> e instanceof AbstractFurnaceBlockEntity).map(e -> (AbstractFurnaceBlockEntity) e).forEach(f -> {
                    if (f.isLit()) {
                        BlockPos blockPos = f.getBlockPos();
                        //    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level().getChunkAt(blockPos)), new InfernalAccelerationRiteEffectPacket(INFERNAL_SPIRIT.getPrimaryColor(), blockPos));
                        f.cookingProgress = Math.min(f.cookingProgress + 20, f.cookingTotalTime - 1);
                    }
                });
            }
        };
    }
}