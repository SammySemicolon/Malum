package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.item.augment.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;

import java.util.*;
import java.util.stream.*;

public class CrucibleAccelerationData {

    public static final CrucibleAccelerationData DEFAULT = new CrucibleAccelerationData();

    public final List<BlockPos> positions = new ArrayList<>();
    public final List<ICrucibleAccelerator> accelerators = new ArrayList<>();

    public final TunedValue focusingSpeed;
    public final TunedValue damageChance;

    public final TunedValue bonusYieldChance;
    public final TunedValue fuelUsageRate;
    public final TunedValue chainFocusingChance;
    public final TunedValue damageAbsorptionChance;
    public final TunedValue restorationChance;

    public float globalAttributeModifier;

    public static CrucibleAccelerationData createData(ICatalyzerAccelerationTarget target, int lookupRange, Level level, BlockPos pos) {
        Collection<ICrucibleAccelerator> nearbyAccelerators = BlockEntityHelper.getBlockEntities(ICrucibleAccelerator.class, level, pos, lookupRange);
        Collection<ICrucibleAccelerator> validAccelerators = new ArrayList<>();
        Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> typeCount = new HashMap<>();

        for (ICrucibleAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canStartAccelerating()) {
                if (accelerator.getTarget() == null || accelerator.getTarget().equals(target) || (accelerator.getTarget() != null && !accelerator.getTarget().isValidAccelerationTarget())) {
                    var acceleratorType = accelerator.getAcceleratorType();
                    int max = acceleratorType.maximumEntries;
                    int amount = typeCount.getOrDefault(acceleratorType, 0);
                    if (amount < max) {
                        accelerator.setTarget(target);
                        validAccelerators.add(accelerator);
                        typeCount.put(acceleratorType, amount + 1);
                    }
                }
            }
        }
        final CrucibleAccelerationData data = new CrucibleAccelerationData(target, typeCount, validAccelerators);
        data.globalAttributeModifier = target.getAccelerationData() != null ? target.getAccelerationData().globalAttributeModifier : 0;
        return data;
    }

    public CrucibleAccelerationData(ICatalyzerAccelerationTarget target, Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> typeCount, Collection<ICrucibleAccelerator> accelerators) {
        final List<AbstractAugmentItem> augments = Stream.concat(accelerators.stream().map(ICrucibleAccelerator::getAugmentType), target.getAugments().stream()).filter(Optional::isPresent).map(Optional::get).toList();
        float acceleratorExtraSpeed = typeCount.entrySet().stream().map((entry) -> entry.getKey().getAcceleration(entry.getValue())).reduce(Float::sum).orElse(0f);
        float acceleratorExtraDamage = typeCount.entrySet().stream().map((entry) -> entry.getKey().getExtraDamageRollChance(entry.getValue())).reduce(Float::sum).orElse(0f);
        float tuningStrength = 1f + augments.stream().map(AbstractAugmentItem::getTuningStrengthIncrease).reduce(Float::sum).orElse(0f);
        var tuning = new CrucibleTuning(target, tuningStrength);
        this.focusingSpeed = tuning.focusingSpeedMultiplier.createTunedValue(augments, AbstractAugmentItem::getSpeedIncrease, acceleratorExtraSpeed);
        this.damageChance = tuning.damageChanceMultiplier.createTunedValue(augments, AbstractAugmentItem::getInstabilityIncrease, acceleratorExtraDamage);

        this.bonusYieldChance = tuning.bonusYieldChanceMultiplier.createTunedValue(augments, AbstractAugmentItem::getFortuneChance);
        this.fuelUsageRate = tuning.fuelUsageRate.createTunedValue(augments, AbstractAugmentItem::getFuelUsageRateIncrease);
        this.chainFocusingChance = tuning.chainFocusingChanceMultiplier.createTunedValue(augments, AbstractAugmentItem::getChainFocusingChance);
        this.damageAbsorptionChance = tuning.damageAbsorptionChanceMultiplier.createTunedValue(augments, AbstractAugmentItem::getShieldingChance);
        this.restorationChance = tuning.restorationChanceMultiplier.createTunedValue(augments, AbstractAugmentItem::getRestorationChance);

        final List<CrucibleTuning.CrucibleAttributeType> validTuningTypes = CrucibleTuning.CrucibleAttributeType.getValidValues(this);
        TunedValue weakestValue = figureOutWeakestValue(target.getAccelerationData(), Stream.of(focusingSpeed, bonusYieldChance, chainFocusingChance, damageAbsorptionChance, restorationChance).filter(t -> validTuningTypes.contains(t.tuning.attributeType)).collect(Collectors.toList()));
        weakestValue.setMultiplier(augments.stream().map(AbstractAugmentItem::getWeakestAttributeMultiplier).reduce(Float::sum).orElse(0f));
        accelerators.forEach(this::addAccelerator);
    }

    public CrucibleAccelerationData() {
        this.focusingSpeed = new DefaultedTunedValue(1);
        this.damageChance = new DefaultedTunedValue(0);
        this.bonusYieldChance = new DefaultedTunedValue(0);
        this.fuelUsageRate = new DefaultedTunedValue(1);
        this.chainFocusingChance = new DefaultedTunedValue(0);
        this.damageAbsorptionChance = new DefaultedTunedValue(0);
        this.restorationChance = new DefaultedTunedValue(0);
    }

    private void addAccelerator(ICrucibleAccelerator crucibleAccelerator) {
        positions.add(((BlockEntity) crucibleAccelerator).getBlockPos());
        accelerators.add(crucibleAccelerator);
    }

    public void save(CompoundTag compound) {
        CompoundTag acceleratorTag = new CompoundTag();
        if (!positions.isEmpty()) {
            acceleratorTag.putInt("amount", positions.size());
            for (int i = 0; i < positions.size(); i++) {
                BlockPos position = positions.get(i);
                acceleratorTag.put("acceleratorPosition_"+i, NBTHelper.saveBlockPos(position));
            }
        }
        acceleratorTag.putFloat("globalAttributeModifier", globalAttributeModifier);
        compound.put("acceleratorData", acceleratorTag);
    }

    public static CrucibleAccelerationData load(Level level, ICatalyzerAccelerationTarget target, CompoundTag compound) {
        if (level == null) {
            return DEFAULT;
        }
        Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> typeCount = new HashMap<>();
        Collection<ICrucibleAccelerator> accelerators = new ArrayList<>();
        if (compound.contains("acceleratorData")) {
            CompoundTag acceleratorTag = compound.getCompound("acceleratorData");
            int amount = acceleratorTag.getInt("amount");
            for (int i = 0; i < amount; i++) {
                BlockPos pos = NBTHelper.readBlockPos(acceleratorTag.getCompound("acceleratorPosition_" + i));
                if (pos != null) {
                    if (level.getBlockEntity(pos) instanceof ICrucibleAccelerator accelerator) {
                        typeCount.compute(accelerator.getAcceleratorType(), (type, count) -> count == null ? 1 : count + 1);
                        accelerators.add(accelerator);
                        accelerator.setTarget(target);
                        BlockStateHelper.updateState(level, pos);
                    }
                }
            }
            final CrucibleAccelerationData data = new CrucibleAccelerationData(target, typeCount, accelerators);
            data.globalAttributeModifier = acceleratorTag.getFloat("globalAttributeModifier");
            return data;
        }
        else {
            return DEFAULT;
        }
    }

    public static class TunedValue {
        public final CrucibleTuning.TuningModifier tuning;
        protected final float value;
        private float multiplier = 0f;

        public TunedValue(CrucibleTuning.TuningModifier tuning, float bonus) {
            this.tuning = tuning;
            this.value = (tuning.baseValue + bonus) * (1 + tuning.getTuningMultiplier());
        }

        protected void setMultiplier(float multiplier) {
            this.multiplier = multiplier;
        }

        public float getValue(CrucibleAccelerationData accelerationData) {
            float modifier = accelerationData.globalAttributeModifier;
            if (tuning.attributeType.equals(CrucibleTuning.CrucibleAttributeType.CHAIN_FOCUSING_CHANCE)) {
                modifier *= -1;
            }
            return value * tuning.getMultiplierScalar(modifier + multiplier);
        }

    }
    public static class DefaultedTunedValue extends TunedValue {
        public DefaultedTunedValue(float baseValue) {
            super(CrucibleTuning.TuningModifier.DEFAULT, baseValue);
        }

        @Override
        public float getValue(CrucibleAccelerationData accelerationData) {
            return value;
        }
    }

    public static TunedValue figureOutWeakestValue(CrucibleAccelerationData data, List<TunedValue> tunedValues) {
        if (tunedValues.size() == 1) {
            return tunedValues.get(0);
        }
        return tunedValues.stream().min((t1, t2) -> {
            float difference1 = t1.tuning.getRelativeValue(data, t1);
            float difference2 = t2.tuning.getRelativeValue(data, t2);
            return Float.compare(difference1, difference2);
        }).orElse(null);
    }
}
