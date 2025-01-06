package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import java.util.Optional;

public interface IArtificeModifierSource {
    ArtificeModifierInstance createFocusingModifierInstance();

    Optional<ArtificeModifierInstance> getFocusingModifierInstance();

    default ArtificeModifierInstance getActiveFocusingModifierInstance() {
        return getFocusingModifierInstance()
                .filter(ArtificeModifierInstance::isBound)
                .orElseGet(this::createFocusingModifierInstance);
    }
}
