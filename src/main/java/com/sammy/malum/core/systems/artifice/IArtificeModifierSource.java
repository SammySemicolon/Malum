package com.sammy.malum.core.systems.artifice;

import java.util.Optional;

public interface IArtificeModifierSource {
    ArtificeModifierSourceInstance createFocusingModifierInstance();

    Optional<ArtificeModifierSourceInstance> getFocusingModifierInstance();

    default ArtificeModifierSourceInstance getActiveFocusingModifierInstance() {
        return getFocusingModifierInstance()
                .filter(ArtificeModifierSourceInstance::isBound)
                .orElseGet(this::createFocusingModifierInstance);
    }
}
