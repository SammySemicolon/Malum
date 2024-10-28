package com.sammy.malum.visual_effects.networked.altar;

import com.sammy.malum.common.block.curiosities.spirit_altar.SpiritAltarBlockEntity;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.visual_effects.SpiritAltarParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.function.Supplier;

public class SpiritAltarEatItemParticleEffect extends ParticleEffectType {

    public SpiritAltarEatItemParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(BlockPos holderPos, ItemStack stack) {
        NBTEffectData effectData = new NBTEffectData(stack);
        BlockHelper.saveBlockPos(effectData.compoundTag, holderPos);
        return effectData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof SpiritAltarBlockEntity spiritAltar)) {
                return;
            }
            if (!(level.getBlockEntity(BlockHelper.loadBlockPos(nbtData.compoundTag)) instanceof IMalumSpecialItemAccessPoint holder)) {
                return;
            }
            SpiritAltarParticleEffects.eatItemParticles(spiritAltar, holder, colorData, nbtData.getStack());
        };
    }
}