package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.data_components.ArtificeAugmentData;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;

import static com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeAttributeType.*;

public class ArtificeAttributeData {

    public static Codec<ArtificeAttributeData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            ArtificeAttributeValue.CODEC.listOf().fieldOf("values").forGetter(v -> v.attributes),
            BlockPos.CODEC.listOf().fieldOf("modifierPositions").forGetter(v -> v.modifierPositions),
            ArtificeAttributeType.CODEC.optionalFieldOf("tunedAttribute").forGetter(v -> Optional.ofNullable(v.tunedAttribute)),
            Codec.FLOAT.fieldOf("chainProcessingBonus").forGetter(v -> v.chainProcessingBonus)
    ).apply(obj, ((a, b, h, c) -> new ArtificeAttributeData(a, b, h.orElse(null), c))));


    public final ArtificeAttributeValue focusingSpeed = new ArtificeAttributeValue(FOCUSING_SPEED);
    public final ArtificeAttributeValue instability = new ArtificeAttributeValue(INSTABILITY);
    public final ArtificeAttributeValue fuelUsageRate = new ArtificeAttributeValue(FUEL_USAGE_RATE);

    public final ArtificeAttributeValue fortuneChance = new ArtificeAttributeValue(FORTUNE_CHANCE);
    public final ArtificeAttributeValue chainFocusingChance = new ArtificeAttributeValue(CHAIN_FOCUSING_CHANCE);
    public final ArtificeAttributeValue damageAbsorptionChance = new ArtificeAttributeValue(DAMAGE_ABSORPTION_CHANCE);
    public final ArtificeAttributeValue restorationChance = new ArtificeAttributeValue(RESTORATION_CHANCE);
    public final ArtificeAttributeValue weakestBoost = new ArtificeAttributeValue(WEAKEST_BOOST);
    public final ArtificeAttributeValue tuningPotency = new ArtificeAttributeValue(TUNING_POTENCY);

    public final List<ArtificeAttributeValue> attributes = List.of(
            focusingSpeed, instability, fuelUsageRate,
            fortuneChance, chainFocusingChance, damageAbsorptionChance, restorationChance, weakestBoost, tuningPotency
    );
    public final List<BlockPos> modifierPositions = new ArrayList<>();
    @Nullable
    private ArtificeInfluenceData influenceData;

    public ArtificeAttributeType tunedAttribute;
    public float chainProcessingBonus;

    public ArtificeAttributeData(@Nullable ArtificeInfluenceData influenceData) {
        this.influenceData = influenceData;
        if (influenceData != null) {
            for (ArtificeModifierSource modifier : influenceData.modifiers()) {
                modifier.modifyFocusing(this::applyModifier);
                modifier.applyAugments(this::applyAugment);
            }
        }
        var attributesForTuning = getExistingAttributesForTuning();
        if (tunedAttribute != null) {
            for (ArtificeAttributeValue attribute : attributes) {
                attribute.tune(tunedAttribute.equals(attribute.type) ? AppliedTuningType.POSITIVE : AppliedTuningType.NEGATIVE);
            }
        }
        var weakestValue = figureOutWeakestValue(attributesForTuning);
        if (weakestValue != null) {
            weakestValue.addTuningMultiplier(weakestValue.getValue(this));
        }
    }

    public ArtificeAttributeData(List<ArtificeAttributeValue> attributes, List<BlockPos> modifierPositions, ArtificeAttributeType tunedAttribute, float chainProcessingBonus) {
        for (int i = 0; i < this.attributes.size(); i++) {
            this.attributes.get(i).copyFrom(attributes.get(i));
        }
        this.modifierPositions.addAll(modifierPositions);
        this.tunedAttribute = tunedAttribute;
        this.chainProcessingBonus = chainProcessingBonus;
    }

    public ArtificeAttributeData() {

    }

    public void applyAugment(ItemStack augment) {
        if (!augment.has(DataComponentRegistry.ARTIFICE_AUGMENT))
        {
            throw new IllegalArgumentException();
        }
        ArtificeAugmentData augmentData = augment.get(DataComponentRegistry.ARTIFICE_AUGMENT);
        for (ArtificeModifier modifier : augmentData.modifiers()) {
            applyModifier(modifier);
        }
    }
    public void applyModifier(ArtificeModifier modifier) {
        ArtificeAttributeValue value = getAttributeValue(modifier.attribute());
        value.addValue(modifier.value());
    }

    public Optional<ArtificeInfluenceData> getInfluenceData(Level level) {
        if (influenceData == null) {
            influenceData = ArtificeInfluenceData.reconstructData(level, this);
        }
        return Optional.ofNullable(influenceData);
    }

    public void selectNextAttributeForTuning() {
        if (tunedAttribute == null) {
            tunedAttribute = CRUCIBLE_ATTRIBUTES.getFirst();
            return;
        }
        var attributes = getExistingAttributesForTuning();
        int index = attributes.indexOf(tunedAttribute) + 1;
        if (index >= attributes.size()) {
            tunedAttribute = null;
            return;
        }
        tunedAttribute = attributes.get(index);
    }

    public ArtificeAttributeValue getAttributeValue(ArtificeAttributeType attributeType) {
        return attributeType.getAttributeValue(this);
    }

    public List<ArtificeAttributeType> getExistingAttributesForTuning() {
        return getExistingAttributes(this).stream().filter(d -> d.canBeTuned).toList();
    }

    public ArtificeAttributeValue figureOutWeakestValue(List<ArtificeAttributeType> tunedValues) {
        if (tunedValues.size() == 1) {
            return tunedValues.getFirst().getAttributeValue(this);
        }
        return tunedValues.stream().map(t -> t.getAttributeValue(this)).min((t1, t2) -> {
            float difference1 = t1.type.tuningBehavior.getRelativeValue(this, t1);
            float difference2 = t2.type.tuningBehavior.getRelativeValue(this, t2);
            return Float.compare(difference1, difference2);
        }).orElse(null);
    }
}