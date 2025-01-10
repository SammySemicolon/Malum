package com.sammy.malum.core.systems.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.data_components.ArtificeAugmentData;
import com.sammy.malum.common.item.augment.ImpurityStabilizer;
import com.sammy.malum.common.item.augment.core.CausticCatalystItem;
import com.sammy.malum.common.item.augment.core.ResonanceTuner;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

import javax.annotation.Nullable;
import java.util.*;

import static com.sammy.malum.core.systems.artifice.ArtificeAttributeType.*;

public class ArtificeAttributeData {

    public static Codec<ArtificeAttributeData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            ArtificeAttributeValue.CODEC.listOf().fieldOf("values").forGetter(v -> v.attributes),
            BlockPos.CODEC.listOf().fieldOf("modifierPositions").forGetter(v -> v.modifierPositions),
            ArtificeAttributeType.CODEC.optionalFieldOf("tunedAttribute").forGetter(v -> Optional.ofNullable(v.tunedAttribute)),
            Codec.BOOL.fieldOf("demandsFuel").forGetter(v -> v.demandsFuel),
            Codec.FLOAT.fieldOf("chainProcessingBonus").forGetter(v -> v.chainProcessingBonus)
    ).apply(obj, ((a, b, h, d, c) -> new ArtificeAttributeData(a, b, h.orElse(null), d, c))));

    public final ArtificeAttributeValue focusingSpeed = new ArtificeAttributeValue(FOCUSING_SPEED);
    public final ArtificeAttributeValue instability = new ArtificeAttributeValue(INSTABILITY);
    public final ArtificeAttributeValue fuelUsageRate = new ArtificeAttributeValue(FUEL_USAGE_RATE);
    public final ArtificeAttributeValue fortuneChance = new ArtificeAttributeValue(FORTUNE_CHANCE);

    public final ArtificeAttributeValue chainFocusingChance = new ArtificeAttributeValue(CHAIN_FOCUSING_CHANCE);
    public final ArtificeAttributeValue damageAbsorptionChance = new ArtificeAttributeValue(DAMAGE_ABSORPTION_CHANCE);
    public final ArtificeAttributeValue restorationChance = new ArtificeAttributeValue(RESTORATION_CHANCE);
    public final ArtificeAttributeValue weaknessTuning = new ArtificeAttributeValue(WEAKNESS_TUNING);

    public final ArtificeAttributeValue tuningPotency = new ArtificeAttributeValue(TUNING_POTENCY);
    public final ArtificeAttributeValue tuningStrain = new ArtificeAttributeValue(TUNING_STRAIN);

    public final ArtificeAttributeValue causticSynergy = new ArtificeAttributeValue(CAUSTIC_SYNERGY);
    public final ArtificeAttributeValue resonanceTuning = new ArtificeAttributeValue(RESONANCE_TUNING);
    public final ArtificeAttributeValue misfortuneReversal = new ArtificeAttributeValue(MISFORTUNE_REVERSAL);

    public final List<ArtificeAttributeValue> attributes = List.of(
            focusingSpeed, instability, fuelUsageRate, fortuneChance,
            chainFocusingChance, damageAbsorptionChance, restorationChance, weaknessTuning,
            tuningPotency, tuningStrain
    );
    public final List<BlockPos> modifierPositions = new ArrayList<>();
    @Nullable
    private ArtificeInfluenceData influenceData;

    public ArtificeAttributeType tunedAttribute;
    public boolean demandsFuel;
    public float chainProcessingBonus;

    public ArtificeAttributeData(IArtificeAcceptor target, @Nullable ArtificeInfluenceData influenceData) {
        if (target.getAttributes() != null) {
            tunedAttribute = target.getAttributes().tunedAttribute;
        }
        this.influenceData = influenceData;
        if (influenceData != null) {
            for (ArtificeModifierSourceInstance modifier : influenceData.modifiers()) {
                modifier.modifyFocusing(this::applyModifier);
                modifier.applyAugments(this::applyAugment);
                this.modifierPositions.add(modifier.sourcePosition);
                if (modifier.consumesFuel()) {
                    demandsFuel = true;
                }
            }
        }
        target.applyAugments(this::applyAugment);
        applyTuning();
    }

    public ArtificeAttributeData(List<ArtificeAttributeValue> attributes, List<BlockPos> modifierPositions, ArtificeAttributeType tunedAttribute,
                                 boolean demandsFuel, float chainProcessingBonus) {
        for (int i = 0; i < this.attributes.size(); i++) {
            this.attributes.get(i).copyFrom(attributes.get(i));
        }
        this.modifierPositions.addAll(modifierPositions);
        this.tunedAttribute = tunedAttribute;
        this.demandsFuel = demandsFuel;
        this.chainProcessingBonus = chainProcessingBonus;
    }

    public ArtificeAttributeData() {

    }

    public void applyTuning() {
        for (ArtificeAttributeValue attribute : attributes) {
            attribute.removeModifier(TuningModifier.TUNING_FORK);
        }
        CausticCatalystItem.scalePotency(this);
        var attributesForTuning = getExistingAttributesForTuning();
        if (tunedAttribute != null) {
            float potency = tuningPotency.getValue(this);
            float strain = tuningStrain.getValue(this);
            for (ArtificeAttributeType attribute : attributesForTuning) {
                ArtificeAttributeValue value = attribute.getAttributeValue(this);
                boolean isBoosted = tunedAttribute.equals(attribute);
                var tuningType = isBoosted ? AppliedTuningType.POSITIVE : AppliedTuningType.NEGATIVE;
                float bonus = tuningType.getMultiplier(attribute) * (isBoosted ? potency : strain);
                value.applyModifier(new TuningModifier(TuningModifier.TUNING_FORK, bonus));
            }
        }
        ImpurityStabilizer.applyWeaknessTuning(this, attributesForTuning);
        ResonanceTuner.exchangeSpeed(this);
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
        getAttributeValue(modifier.attribute()).applyModifier(modifier);
    }

    public Optional<ArtificeInfluenceData> getInfluenceData(Level level) {
        if (influenceData == null) {
            influenceData = ArtificeInfluenceData.reconstructData(level, this);
        }
        return Optional.ofNullable(influenceData);
    }

    public void applyTuningForkBuff(ServerLevel level, BlockPos pos) {
        selectNextAttributeForTuning();
        applyTuning();
        float volume = RandomHelper.randomBetween(level.getRandom(), 1.25f, 1.75f);
        float pitch = RandomHelper.randomBetween(level.getRandom(), 0.75f, 1.25f);
        level.playSound(null, pos, SoundRegistry.TUNING_FORK_TINKER.get(), SoundSource.BLOCKS, volume, pitch);
        BlockStateHelper.updateAndNotifyState(level, pos);
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

    public ArtificeAttributeValue figureOutWeakestAttribute(List<ArtificeAttributeType> tunedValues) {
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