package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.lodestar.lodestone.helpers.NBTHelper;
import team.lodestar.lodestone.helpers.block.BlockEntityHelper;

import java.util.*;

public record ArtificeInfluenceData(Set<ArtificeModifierInstance> modifiers) {

    public static ArtificeInfluenceData createFreshData(int lookupRange, Level level, BlockPos pos) {
        var nearbyInfluencers = BlockEntityHelper.getBlockEntities(IArtificeModifierSource.class, level, pos, lookupRange, ArtificeInfluenceData::isValidInfluencer);
        return createData(nearbyInfluencers);
    }

    public static ArtificeInfluenceData loadData(Level level, ListTag list) {
        Collection<IArtificeModifierSource> nearbyInfluencers = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            var pos = NBTHelper.readBlockPos(list, i);
            if (level.getBlockEntity(pos) instanceof IArtificeModifierSource influencer) {
                nearbyInfluencers.add(influencer);
            } else {
                return null;
            }

        }
        return createData(nearbyInfluencers);
    }

    public static ArtificeInfluenceData reconstructData(Level level, ArtificeAttributeData data) {
        Collection<IArtificeModifierSource> nearbyInfluencers = new HashSet<>();
        List<BlockPos> knownPositions = data.modifierPositions;
        for (BlockPos pos : knownPositions) {
            if (level.getBlockEntity(pos) instanceof IArtificeModifierSource influencer) {
                nearbyInfluencers.add(influencer);
            } else {
                return null;
            }
        }
        return createData(nearbyInfluencers);
    }

    public static ArtificeInfluenceData createData(Collection<IArtificeModifierSource> nearbyInfluencers) {
        Set<ArtificeModifierInstance> validModifiers = new HashSet<>();
        Map<ResourceLocation, Integer> counter = new HashMap<>();
        for (IArtificeModifierSource influencer : nearbyInfluencers) {
            ArtificeModifierInstance modifier = influencer.getActiveFocusingModifierInstance();
            if (modifier.canModifyFocusing()) {
                int count = counter.merge(modifier.type, 1, Integer::sum);
                if (count <= modifier.maxAmount) {
                    validModifiers.add(modifier);
                }
            }
        }
        return new ArtificeInfluenceData(validModifiers);
    }

    public static boolean isValidInfluencer(IArtificeModifierSource influencer) {
        Optional<IArtificeAcceptor> optional = influencer.getFocusingModifierInstance().filter(ArtificeModifierInstance::isBound).map(p -> p.target);
        if (optional.isEmpty()) {
            return true;
        }
        var target = optional.get();
        return ((BlockEntity)target).isRemoved();
    }
}