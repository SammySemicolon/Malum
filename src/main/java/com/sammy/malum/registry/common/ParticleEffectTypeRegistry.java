package com.sammy.malum.registry.common;

import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.altar.*;
import com.sammy.malum.visual_effects.networked.attack.slam.SlamAttackParticleEffect;
import com.sammy.malum.visual_effects.networked.attack.slash.AscensionRadialSlashParticleEffect;
import com.sammy.malum.visual_effects.networked.blight.*;
import com.sammy.malum.visual_effects.networked.crucible.*;
import com.sammy.malum.visual_effects.networked.geas.*;
import com.sammy.malum.visual_effects.networked.gluttony.*;
import com.sammy.malum.visual_effects.networked.nitrate.*;
import com.sammy.malum.visual_effects.networked.pylon.*;
import com.sammy.malum.visual_effects.networked.ritual.*;
import com.sammy.malum.visual_effects.networked.attack.slash.*;
import com.sammy.malum.visual_effects.networked.spirit_diode.SpiritDiodeCloseParticleEffect;
import com.sammy.malum.visual_effects.networked.spirit_diode.SpiritDiodeOpenParticleEffect;
import com.sammy.malum.visual_effects.networked.spirit_mote.*;
import com.sammy.malum.visual_effects.networked.staff.*;
import com.sammy.malum.visual_effects.networked.totem.*;
import com.sammy.malum.visual_effects.networked.weeping_well.*;

import java.util.*;

public class ParticleEffectTypeRegistry {

    public static final Map<String, ParticleEffectType> EFFECT_TYPES = new LinkedHashMap<>();

    //Blight vfx
    public static final ParticleEffectType BLIGHTING_MIST = new BlightingMistParticleEffect("blighting_mist");

    //Spirit Altar
    public static final ParticleEffectType SPIRIT_ALTAR_CRAFTS = new SpiritAltarCraftParticleEffect("spirit_altar_crafts");
    public static final ParticleEffectType SPIRIT_ALTAR_EATS_ITEM = new SpiritAltarEatItemParticleEffect("spirit_altar_eats_item");

    //Crucible
    public static final ParticleEffectType SPIRIT_CRUCIBLE_CRAFTS = new SpiritCrucibleCraftParticleEffect("spirit_crucible_crafts");

    //Repair Pylon
    public static final ParticleEffectType REPAIR_PYLON_PREPARES = new PylonPrepareRepairParticleEffect("repair_pylon_prepare");
    public static final ParticleEffectType REPAIR_PYLON_REPAIRS = new PylonRepairParticleEffect("repair_pylon_repairs");

    //Totem Effects
    public static final ParticleEffectType TOTEM_POLE_ACTIVATED = new TotemPoleActivatedParticleEffect("totem_pole_activated");
    public static final ParticleEffectType RITE_EFFECT_TRIGGERED = new GenericRiteEffectTriggerParticleEffect("generic_rite_effect");

    //Ritual Plinth
    public static final ParticleEffectType RITUAL_PLINTH_EATS_ITEM = new RitualPlinthAbsorbItemParticleEffect("ritual_plinth_eats_item");
    public static final ParticleEffectType RITUAL_PLINTH_EATS_SPIRIT = new RitualPlinthAbsorbSpiritParticleEffect("ritual_plinth_eats_spirit");
    public static final ParticleEffectType RITUAL_PLINTH_BEGINS_CHARGING = new RitualPlinthBeginChargingParticleEffect("ritual_plinth_begins_charging");
    public static final ParticleEffectType RITUAL_PLINTH_CHANGES_TIER = new RitualPlinthChangeTierParticleEffect("ritual_plinth_changes_tier");
    public static final ParticleEffectType RITUAL_PLINTH_FAILURE = new RitualPlinthFailureParticleEffect("ritual_plinth_failure");

    //Spirit Diode
    public static final ParticleEffectType SPIRIT_DIODE_OPEN = new SpiritDiodeOpenParticleEffect("spirit_diode_open");
    public static final ParticleEffectType SPIRIT_DIODE_CLOSE = new SpiritDiodeCloseParticleEffect("spirit_diode_close");

    //Slash Effects
    public static final ParticleEffectType SCYTHE_SLASH = new SlashAttackParticleEffect("scythe_slash");
    public static final ParticleEffectType SCYTHE_ASCENSION_SPIN = new AscensionRadialSlashParticleEffect("scythe_ascension_spin");
    public static final ParticleEffectType SCYTHE_ASCENSION_UPPERCUT = new AscensionUppercutParticleEffect("scythe_ascension_uppercut");

    public static final ParticleEffectType TYRVING_SLASH = new TyrvingSlashParticleEffect("tyrving_slash");
    public static final ParticleEffectType SUNDERING_ANCHOR_SLASH = new SunderingAnchorSlashParticleEffect("sundering_anchor_slash");

    public static final ParticleEffectType WEIGHT_OF_WORLDS_CRIT = new WeightOfWorldsCritParticleEffect("weight_of_worlds_crit");
    public static final ParticleEffectType EDGE_OF_DELIVERANCE_CRIT = new EdgeOfDeliveranceCritParticleEffect("weight_of_worlds_crit");

    public static final ParticleEffectType HIDDEN_BLADE_COUNTER_FLURRY = new HiddenBladeCounterParticleEffect("hidden_blade_counter_flurry");

    //Slam Effects
    public static final ParticleEffectType STAFF_SLAM = new SlamAttackParticleEffect("staff_slam");

    //Wand
    public static final ParticleEffectType HEX_BOLT_IMPACT = new HexBoltImpactParticleEffect("hex_bolt_impact");
    public static final ParticleEffectType DRAINING_BOLT_IMPACT = new DrainingBoltImpactParticleEffect("draining_bolt_impact");
    public static final ParticleEffectType ENTROPIC_BOLT_IMPACT = new EntropicBoltImpactParticleEffect("entropic_bolt_impact");

    //Geas
    public static final ParticleEffectType SOULWASHING_IMPACT = new SoulwashingGeasImpactParticleEffect("soulwashing_impact");

    //Gluttony
    public static final ParticleEffectType THROWN_GLUTTONY_IMPACT = new ThrownGluttonyParticleEffect("splash_of_gluttony");
    public static final ParticleEffectType GLUTTONY_ABSORB = new AbsorbGluttonyParticleEffect("gluttony_absorbed");

    //Nitrate
    public static final ParticleEffectType ETHERIC_NITRATE_IMPACT = new EthericNitrateImpactParticleEffect("etheric_nitrate_impact");

    //Void vfx
    public static final ParticleEffectType WEEPING_WELL_REACTS = new WeepingWellReactionParticleEffect("weeping_well_reacts");
    public static final ParticleEffectType WEEPING_WELL_EMITS_RADIANCE = new WeepingWellRadianceParticleEffect("weeping_well_emits_radiance");

    //Misc vfx
    public static final ParticleEffectType SPIRIT_MOTE_SPARKLES = new SpiritMotePlaceParticleEffect("spirit_mote_sparkles");
    public static final ParticleEffectType SOULWOVEN_BANNER_ACTIVATED = new TotemPoleActivatedParticleEffect("soulwoven_banner_activated");
    public static final ParticleEffectType SAP_COLLECTED = new SapCollectionParticleEffect("sap_collected");

}
