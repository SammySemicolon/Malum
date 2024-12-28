package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.NBTHelper;
import team.lodestar.lodestone.helpers.block.BlockEntityHelper;

import java.util.*;

public record ArtificeInfluenceData(Set<ArtificeModifierSource> modifiers) {

    public static ArtificeInfluenceData createData(int lookupRange, Level level, BlockPos pos) {
        var nearbyInfluencers = BlockEntityHelper.getBlockEntities(ArtificeModifierSource.CrucibleInfluencer.class, level, pos, lookupRange);
        return createData(nearbyInfluencers);
    }

    public static ArtificeInfluenceData loadData(Level level, ListTag list) {
        Collection<ArtificeModifierSource.CrucibleInfluencer> nearbyInfluencers = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            var pos = NBTHelper.readBlockPos(list, i);
            if (level.getBlockEntity(pos) instanceof ArtificeModifierSource.CrucibleInfluencer influencer) {
                nearbyInfluencers.add(influencer);
            } else {
                return null;
            }

        }
        return createData(nearbyInfluencers);
    }

    public static ArtificeInfluenceData reconstructData(Level level, ArtificeAttributeData data) {
        Collection<ArtificeModifierSource.CrucibleInfluencer> nearbyInfluencers = new HashSet<>();
        List<BlockPos> knownPositions = data.modifierPositions;
        for (BlockPos pos : knownPositions) {
            if (level.getBlockEntity(pos) instanceof ArtificeModifierSource.CrucibleInfluencer influencer) {
                nearbyInfluencers.add(influencer);
            } else {
                return null;
            }

        }
        return createData(nearbyInfluencers);
    }

    public static ArtificeInfluenceData createData(Collection<ArtificeModifierSource.CrucibleInfluencer> nearbyInfluencers) {
        Set<ArtificeModifierSource> validModifiers = new HashSet<>();
        Map<ResourceLocation, Integer> counter = new HashMap<>();
        for (ArtificeModifierSource.CrucibleInfluencer influencer : nearbyInfluencers) {
            ArtificeModifierSource modifier = influencer.getActiveFocusingModifierInstance();
            if (modifier.isBound()) {
                continue;
            }
            if (modifier.canModifyFocusing()) {
                int count = counter.merge(modifier.type, 1, Integer::sum);
                if (count <= modifier.maxAmount) {
                    validModifiers.add(modifier);
                }
            }
        }
        return new ArtificeInfluenceData(validModifiers);
    }
}