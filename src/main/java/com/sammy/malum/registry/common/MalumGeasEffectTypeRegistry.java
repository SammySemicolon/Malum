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


    //TODO: Hello Wiresegal https://cdn.discordapp.com/emojis/1102382147941191681.gif?size=48&name=wave%7E1&quality=lossless
    // Pretty please rename all the stuff accordingly to whatever names you choose
    // Tooltip Descriptions are in MalumLang#135 :3

    //+Spirit Spoils +Scythe Damage at night
    //-Attack Damage At day
    public static final DeferredHolder<GeasEffectType, GeasEffectType> BLESSED_MOON = GEAS_TYPES.register("blessed_moon", () -> new GeasEffectType(BlessedMoonGeas::new));
    //+Spirit Spoils +Healing Received at day
    //-Armor at night
    public static final DeferredHolder<GeasEffectType, GeasEffectType> RADIANT_DAWN = GEAS_TYPES.register("radiant_dawn", () -> new GeasEffectType(RadiantDawnGeas::new));
    //+Spirit Spoils +Arcane Resonance
    public static final DeferredHolder<GeasEffectType, GeasEffectType> SOULDRINKERS_ECSTASY = GEAS_TYPES.register("souldrinkers_ecstasy", () -> new GeasEffectType(SouldrinkerGeas::new));

    //+SoulWard Capacity, Integrity
    //-SoulWard Recovery Rate, Magic Proficiency
    public static final DeferredHolder<GeasEffectType, GeasEffectType> MANAWEAVERS_INTEGRITY = GEAS_TYPES.register("manaweavers_integrity", () -> new GeasEffectType(ManaweaverIntegrityGeas::new));
    //+SoulWard Capacity, Recovery Rate
    //-SoulWard Integrity, Magic Proficiency
    public static final DeferredHolder<GeasEffectType, GeasEffectType> MANAWEAVERS_OBSESSION = GEAS_TYPES.register("manaweavers_obsession", () -> new GeasEffectType(ManaweaverObsessionGeas::new));
    //+Soul Ward Everything
    //+Soul Ward Recovers Via Magic Damage Dealt
    //-100% Soul Ward Recovery Rate, Soul Ward can only be gained via exterior means (and the above)
    public static final DeferredHolder<GeasEffectType, GeasEffectType> RUNIC_INFUSION = GEAS_TYPES.register("runic_infusion", () -> new GeasEffectType(RunicInfusionGeas::new));

    //Scythes sometimes strike twice with larger aoe (if narrow edge necklace, they instead strike thrice)
    //Non Scythes deal significantly reduced damage and crumble in your paws
    public static final DeferredHolder<GeasEffectType, GeasEffectType> THANATOPHOBIA = GEAS_TYPES.register("thanatophobia", () -> new GeasEffectType(ThanatophobiaGeas::new));

    //You auto-attack targets that take fall damage
    //Requires that you and the target have attacked each other in recent time
    public static final DeferredHolder<GeasEffectType, GeasEffectType> FALL_DAMAGE_GEAS = GEAS_TYPES.register("fall_damage_geas", () -> new GeasEffectType(FallDamageGeas::new));


    //Gluttony is replaced with [Name Pending]
    //[Name Pending] instead grants a stacking increase to healing received
    //Eating Rotten Foods heals you
    //You cannot heal naturally
    //You cannot eat non-rotten foods
    public static final DeferredHolder<GeasEffectType, GeasEffectType> ROTTEN_DIET = GEAS_TYPES.register("mmm_rotten_flesh_so_hungry_i_could_eat_a_horse", () -> new GeasEffectType(RottenDietGeas::new));
    //Gluttony is replaced with [Name Pending]
    //[Name Pending] stacks to twice as high, providing potentially double the original benefit (200% increase to magic damage, normal is 100%)
    //[Name Pending] also reduces armor, magic resistance, and healing received
    //[Name Pending] is reduced when taking damage
    public static final DeferredHolder<GeasEffectType, GeasEffectType> RAVENOUS_GLUTTONY = GEAS_TYPES.register("ravenous_gluttony", () -> new GeasEffectType(RavenousGluttonyGeas::new));

    //Staff projectiles home in on targets
    //+Charge Duration for staves, this is bad
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OVERKEEN_EYE = GEAS_TYPES.register("overkeen_eye", () -> new GeasEffectType(OverkeenEyeGeas::new));
    //Staves autofire once fully charged
    //-Charge Duration for staves, this is good
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OVEREAGER_FIST = GEAS_TYPES.register("overeager_fist", () -> new GeasEffectType(OvereagerFist::new));

    //Malignant Deliverance leeches health and hunger from the hit target
    //The leaching effect starts to add a healing received penalty after the 3rd trigger, resets after a minute of no leeching
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> HUNGERING_DELIVERANCE = GEAS_TYPES.register("hungering_deliverance", () -> new GeasEffectType(HungeringDeliveranceGeas::new));
    //Malignant Deliverance generates Malignant Conversion
    //Need to think about this one more but the general theming of armor generation will persist
    //This is a void geas
    public static final DeferredHolder<GeasEffectType, GeasEffectType> REINFORCING_DELIVERANCE = GEAS_TYPES.register("reinforcing_deliverance", () -> new GeasEffectType(ReinforcingDeliveranceGeas::new));


    //Requires Several Players
    //All Bound Players can no longer hurt eachother
    //All Bound Players can see each other regardless of invisibility
    //All Bound Players receive Healing Received for each Bound Player
    //Healing is Distributed between all Bound Players within a certain radius
    public static final DeferredHolder<GeasEffectType, GeasEffectType> AMOROUS_CHAINS = GEAS_TYPES.register("amorous_chains", () -> new GeasEffectType(AmorousChainsGeas::new));

    //Requires Several Players
    //All Bound Players receive extra Scythe Proficiency for each Bound Player
    //All Bound Players lose some armor for each Bound Player
    //Damage taken is Distributed between all Bound Players within a certain radius
    public static final DeferredHolder<GeasEffectType, GeasEffectType> DEATH_WORSHIP = GEAS_TYPES.register("death_worship", () -> new GeasEffectType(DeathWorshipGeas::new));



    //Damage you deal is applied to witnesses at a halved amount
    //Damage you take is applied to witnesses
    //Witness Detection range scales with arcane resonance
    //Named Entities and Tamed Pets are excluded
    //Effect stops working completely if the wrathbearer is invisible to the target
    //Reduces Magic Resistance significantly
    //This is a void geas, might use fused consciousness even
    public static final DeferredHolder<GeasEffectType, GeasEffectType> SOULWASHING = GEAS_TYPES.register("soulwashing", () -> new GeasEffectType(SoulwashingGeas::new));
    //Taking damage applies a stasis onto your potion effects
    //Potion effects in stasis have their duration paused with no change to their effect
    //Stasis duration scales with arcane resonance
    //While stasis is active you cannot be hurt by potion effects
    //Reduces Healing Received significantly
    //This is a void geas, might use fused consciousness even
    public static final DeferredHolder<GeasEffectType, GeasEffectType> LIONS_HEART = GEAS_TYPES.register("lions_heart", () -> new GeasEffectType(LionsHeartGeas::new));

}
