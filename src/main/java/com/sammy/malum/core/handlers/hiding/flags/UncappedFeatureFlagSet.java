package com.sammy.malum.core.handlers.hiding.flags;

import net.minecraft.world.flag.FeatureFlagUniverse;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;

public class UncappedFeatureFlagSet {
	private static final UncappedFeatureFlagSet EMPTY = new UncappedFeatureFlagSet(null, new BitSet());
	@Nullable
	private final FeatureFlagUniverse universe;
	private final BitSet mask;

	private UncappedFeatureFlagSet(@Nullable FeatureFlagUniverse pUniverse, BitSet pMask) {
		this.universe = pUniverse;
		this.mask = pMask;
	}

	public static UncappedFeatureFlagSet create(FeatureFlagUniverse pUniverse, Collection<UncappedFeatureFlag> pFlags) {
		if (pFlags.isEmpty()) {
			return EMPTY;
		} else {
			BitSet i = computeMask(pUniverse, -1, pFlags);
			return new UncappedFeatureFlagSet(pUniverse, i);
		}
	}

	public static UncappedFeatureFlagSet of() {
		return EMPTY;
	}

	private static BitSet bitSetOf(int maskBit) {
		if (maskBit < 0)
			return new BitSet();
		BitSet result = new BitSet(maskBit + 1);
		result.set(maskBit, true);
		return result;
	}

	public static UncappedFeatureFlagSet of(UncappedFeatureFlag pFlag) {
		return new UncappedFeatureFlagSet(pFlag.universe, bitSetOf(pFlag.maskBit));
	}

	public static UncappedFeatureFlagSet of(UncappedFeatureFlag pFlag, UncappedFeatureFlag... pOthers) {
		BitSet i = pOthers.length == 0 ? bitSetOf(pFlag.maskBit) : computeMask(pFlag.universe, pFlag.maskBit, Arrays.asList(pOthers));
		return new UncappedFeatureFlagSet(pFlag.universe, i);
	}

	private static BitSet computeMask(FeatureFlagUniverse pUniverse, int pMaskBit, Iterable<UncappedFeatureFlag> pFlags) {
		BitSet result = bitSetOf(pMaskBit);
		for(UncappedFeatureFlag featureflag : pFlags) {
			if (pUniverse != featureflag.universe) {
				throw new IllegalStateException("Mismatched feature universe, expected '" + pUniverse + "', but got '" + featureflag.universe + "'");
			}

			result.set(pMaskBit);
		}

		return result;
	}

	public boolean contains(UncappedFeatureFlag pFlag) {
		if (this.universe != pFlag.universe) {
			return false;
		} else {
			return this.mask.get(pFlag.maskBit);
		}
	}

	public boolean isSubsetOf(UncappedFeatureFlagSet pSet) {
		if (this.universe == null) {
			return true;
		} else if (this.universe != pSet.universe) {
			return false;
		} else {
			BitSet copy = BitSet.valueOf(this.mask.toLongArray());
			copy.andNot(pSet.mask);
			return copy.isEmpty();
		}
	}

	public UncappedFeatureFlagSet join(UncappedFeatureFlagSet pOther) {
		if (this.universe == null) {
			return pOther;
		} else if (pOther.universe == null) {
			return this;
		} else if (this.universe != pOther.universe) {
			throw new IllegalArgumentException("Mismatched set elements: '" + this.universe + "' != '" + pOther.universe + "'");
		} else {
			BitSet copy = BitSet.valueOf(this.mask.toLongArray());
			copy.and(pOther.mask);
			return new UncappedFeatureFlagSet(this.universe, copy);
		}
	}

	public boolean equals(Object pOther) {
		if (this == pOther) {
			return true;
		} else {
			if (pOther instanceof UncappedFeatureFlagSet featureflagset) {
				return this.universe == featureflagset.universe && this.mask == featureflagset.mask;
			}

			return false;
		}
	}

	public int hashCode() {
		return this.mask.hashCode();
	}

	@Nullable
	public FeatureFlagUniverse getUniverse() {
		return universe;
	}
}
