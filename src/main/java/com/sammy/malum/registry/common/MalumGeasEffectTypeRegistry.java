package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.geas.*;
import com.sammy.malum.core.systems.geas.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.neoforged.neoforge.registries.*;

public class MalumGeasEffectTypeRegistry {

    public static ResourceKey<Registry<GeasEffectType>> GEAS_TYPES_KEY = ResourceKey.createRegistryKey(MalumMod.malumPath("geas_types"));
    public static final DeferredRegister<GeasEffectType> GEAS_TYPES = DeferredRegister.create(GEAS_TYPES_KEY, MalumMod.MALUM);
    public static final Registry<GeasEffectType> GEAS_TYPES_REGISTRY = GEAS_TYPES.makeRegistry(builder -> builder.sync(true));


    // Oath, Bond, Pact, Promise, Creed, Coda, Manifesto, Ideal

    //TODO: Hello Wiresegal https://cdn.discordapp.com/emojis/1102382147941191681.gif?size=48&name=wave%7E1&quality=lossless
    // Pretty please rename all the stuff accordingly to whatever names you choose
    // Tooltip Descriptions are in MalumLang#135 :3

    // Pact of the Nightchild
    //+Spirit Spoils +Scythe Damage at night
    //-Attack Damage At day
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_NIGHTCHILD = GEAS_TYPES.register("pact_of_the_nightchild", () -> new GeasEffectType(BlessedMoonGeas::new));
    // Pact of the Dayblessed
    //+Spirit Spoils +Healing Received at day
    //-Armor at night
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_DAYBLESSED = GEAS_TYPES.register("pact_of_the_dayblessed", () -> new GeasEffectType(RadiantDawnGeas::new));
    // Pact of the Shattering Addict
    //+Spirit Spoils +Arcane Resonance
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_SHATTERING_ADDICT = GEAS_TYPES.register("pact_of_the_shattering_addict", () -> new GeasEffectType(SouldrinkerGeas::new));

    // Pact of the Fortress
    //+SoulWard Capacity, Integrity
    //-SoulWard Recovery Rate, Magic Proficiency
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_FORTRESS = GEAS_TYPES.register("pact_of_the_fortress", () -> new GeasEffectType(ManaweaverIntegrityGeas::new));
    // Pact of the Shield
    //+SoulWard Capacity, Recovery Rate
    //-SoulWard Integrity, Magic Proficiency
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_SHIELD = GEAS_TYPES.register("pact_of_the_shield", () -> new GeasEffectType(ManaweaverObsessionGeas::new));
    // Pact of Reciprocation
    //+Soul Ward Everything
    //+Soul Ward Recovers Via Magic Damage Dealt
    //-100% Soul Ward Recovery Rate, Soul Ward can only be gained via exterior means (and the above)
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_RECIPROCATION = GEAS_TYPES.register("pact_of_reciprocation", () -> new GeasEffectType(RunicInfusionGeas::new));

    // Pact of the Reaper
    //Scythes sometimes strike twice with larger aoe (if narrow edge necklace, they instead strike thrice)
    //Non Scythes deal significantly reduced damage and crumble in your paws
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_REAPER = GEAS_TYPES.register("pact_of_the_reaper", () -> new GeasEffectType(ThanatophobiaGeas::new));

    // Pact of the Skybreaker
    //You auto-attack targets that take fall damage
    //Requires that you and the target have attacked each other in recent time
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_SKYBREAKER = GEAS_TYPES.register("pact_of_the_skybreaker", () -> new GeasEffectType(FallDamageGeas::new));


    // Pact of the Profane Ascetic
    //Gluttony is replaced with Trial of Faith
    //Trial of Faith instead grants a stacking increase to healing received
    //Eating Rotten Foods heals you
    //You cannot heal naturally
    //You cannot eat non-rotten foods
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_PROFANE_ASCETIC = GEAS_TYPES.register("pact_of_the_profane_ascetic", () -> new GeasEffectType(RottenDietGeas::new));
    // Pact of the Profane Glutton
    //Gluttony is replaced with Desperate Need
    //Desperate Need stacks to twice as high, providing potentially double the original benefit (200% increase to magic damage, normal is 100%)
    //Desperate Need also reduces armor, magic resistance, and healing received
    //Desperate Need is reduced when taking damage
    public static final DeferredHolder<GeasEffectType, GeasEffectType> PACT_OF_THE_PROFANE_GLUTTON = GEAS_TYPES.register("pact_of_the_profane_glutton", () -> new GeasEffectType(RavenousGluttonyGeas::new));

    // Oath of the Overkeen Eye
    //Staff projectiles home in on targets
    //+Charge Duration for staves, this is bad
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OATH_OF_THE_OVERKEEN_EYE = GEAS_TYPES.register("oath_of_the_overkeen_eye", () -> new GeasEffectType(OverkeenEyeGeas::new));
    // Oath of the Overeager Fist
    //Staves autofire once fully charged
    //-Charge Duration for staves, this is good
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OATH_OF_THE_OVEREAGER_FIST = GEAS_TYPES.register("oath_of_the_overeager_fist", () -> new GeasEffectType(OvereagerFist::new));

    // Oath of the Undiscerned Maw
    //Malignant Deliverance leeches health and hunger from the hit target
    //The leaching effect starts to add a healing received penalty after the 3rd trigger, resets after a minute of no leeching
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OATH_OF_THE_UNDISCERNED_MAW = GEAS_TYPES.register("oath_of_the_undiscerned_maw", () -> new GeasEffectType(HungeringDeliveranceGeas::new));
    // Oath of Unsighted Resistance
    //Malignant Deliverance generates Malignant Conversion
    //Need to think about this one more but the general theming of armor generation will persist
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OATH_OF_UNSIGHTED_RESISTANCE = GEAS_TYPES.register("oath_of_unsighted_resistance", () -> new GeasEffectType(ReinforcingDeliveranceGeas::new));


    // Bond of Beloved Chains
    //Requires Several Players
    //All Bound Players can no longer hurt eachother
    //All Bound Players can see each other regardless of invisibility
    //All Bound Players receive Healing Received for each Bound Player
    //Healing is Distributed between all Bound Players within a certain radius
    public static final DeferredHolder<GeasEffectType, GeasEffectType> BOND_OF_BELOVED_CHAINS = GEAS_TYPES.register("bond_of_beloved_chains", () -> new GeasEffectType(AmorousChainsGeas::new));

    // Bond of Death's Seekers
    //Requires Several Players
    //All Bound Players receive extra Scythe Proficiency for each Bound Player
    //All Bound Players lose some armor for each Bound Player
    //Damage taken is Distributed between all Bound Players within a certain radius
    public static final DeferredHolder<GeasEffectType, GeasEffectType> BOND_OF_DEATHS_SEEKERS = GEAS_TYPES.register("bond_of_deaths_seekers", () -> new GeasEffectType(DeathWorshipGeas::new));


    // Oath of the Inverted Heart
    //Damage you deal is applied to witnesses at a halved amount
    //Damage you take is applied to witnesses
    //Witness Detection range scales with arcane resonance
    //Named Entities and Tamed Pets are excluded
    //Effect stops working completely if the wrathbearer is invisible to the target
    //Reduces Magic Resistance significantly
    //This is a void geas, might use fused consciousness even
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OATH_OF_THE_INVERTED_HEART = GEAS_TYPES.register("oath_of_the_inverted_heart", () -> new GeasEffectType(SoulwashingGeas::new));
    // Oath of the Gleeful Target
    //Taking damage applies a stasis onto your potion effects
    //Potion effects in stasis have their duration paused with no change to their effect
    //Stasis duration scales with arcane resonance
    //While stasis is active you cannot be hurt by potion effects
    //Reduces Healing Received significantly
    //This is a void geas, might use fused consciousness even
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OATH_OF_THE_GLEEFUL_TARGET = GEAS_TYPES.register("oath_of_the_gleeful_target", () -> new GeasEffectType(LionsHeartGeas::new));

}
