package com.sammy.malum.common.block.curiosities.mana_mote;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.client.particle.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;

public class ManaMoteBlockClientExtension implements IClientBlockExtensions {

    @Override
    public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
        return true;
    }

    @Override
    public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
        if (state.getBlock() instanceof ManaMoteBlock) {
            SpiritMoteParticleEffects.destroy(level, pos, state, MalumSpiritType.getSpiritType(state.getValue(ManaMoteBlock.SPIRIT_TYPE)));
        }
        return true;
    }
}
