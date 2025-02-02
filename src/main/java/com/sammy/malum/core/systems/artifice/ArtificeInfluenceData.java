package com.sammy.malum.core.systems.artifice;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.lodestar.lodestone.helpers.NBTHelper;
import team.lodestar.lodestone.helpers.block.BlockEntityHelper;

import java.util.*;

public record ArtificeInfluenceData(Set<ArtificeModifierSourceInstance> modifiers) {

    public static ArtificeInfluenceData createFreshData(int lookupRange, Level level, BlockPos pos, ArtificeAttributeData attributes) {
        var nearbyInfluencers = BlockEntityHelper.getBlockEntities(IArtificeModifierSource.class, level, pos, lookupRange, ArtificeInfluenceData::isValidInfluencer);
         return createData(nearbyInfluencers, attributes);
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
        return createData(nearbyInfluencers, data);
    }

    public static ArtificeInfluenceData createData(Collection<IArtificeModifierSource> nearbyInfluencers, ArtificeAttributeData attributes) {
        Set<ArtificeModifierSourceInstance> validModifiers = new HashSet<>();
        Map<ResourceLocation, Integer> counter = new HashMap<>();
        for (IArtificeModifierSource influencer : nearbyInfluencers) {
            ArtificeModifierSourceInstance modifier = influencer.getActiveFocusingModifierInstance();
            if (modifier.canModifyFocusing(attributes)) {
                int count = counter.merge(modifier.type, 1, Integer::sum);
                if (count <= modifier.maxAmount) {
                    validModifiers.add(modifier);
                }
            }
        }
        return new ArtificeInfluenceData(validModifiers);
    }

    public static boolean isValidInfluencer(IArtificeModifierSource influencer) {
        Optional<ArtificeModifierSourceInstance> focusingModifierInstance = influencer.getFocusingModifierInstance();
        Optional<ArtificeModifierSourceInstance> artificeModifierSourceInstance = focusingModifierInstance.filter(ArtificeModifierSourceInstance::isBound);
        Optional<IArtificeAcceptor> iArtificeAcceptor = artificeModifierSourceInstance.map(p -> p.target);
        if (iArtificeAcceptor.isEmpty()) {
            return true;
        }
        var target = iArtificeAcceptor.get();
        return !((BlockEntity)target).isRemoved();
    }
}