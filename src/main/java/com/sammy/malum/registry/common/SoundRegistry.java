package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.sound.BlightedSoundType;
import com.sammy.malum.common.sound.QuartzClusterSoundType;
import com.sammy.malum.common.sound.QuartzSoundType;
import com.sammy.malum.common.sound.RareEarthSoundType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.MalumMod.MALUM;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MALUM);

    public static final RegistryObject<SoundEvent> ARCANA_CODEX_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_codex_opened")));
    public static final RegistryObject<SoundEvent> ARCANA_CODEX_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_codex_closed")));
    public static final RegistryObject<SoundEvent> ARCANA_ENTRY_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_entry_opened")));
    public static final RegistryObject<SoundEvent> ARCANA_ENTRY_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_entry_closed")));
    public static final RegistryObject<SoundEvent> ARCANA_PAGE_FLIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_page_flipped")));
    public static final RegistryObject<SoundEvent> ARCANA_SWEETENER_NORMAL = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_sweetener_normal")));
    public static final RegistryObject<SoundEvent> ARCANA_SWEETENER_EVIL = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_sweetener_evil")));
    public static final RegistryObject<SoundEvent> ARCANA_TRANSITION_NORMAL = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_transition_normal")));
    public static final RegistryObject<SoundEvent> ARCANA_TRANSITION_EVIL = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcana_transition_evil")));

    public static final RegistryObject<SoundEvent> PEDESTAL_ITEM_INSERT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("pedestal_item_inserted")));
    public static final RegistryObject<SoundEvent> PEDESTAL_ITEM_PICKUP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("pedestal_item_picked_up")));
    public static final RegistryObject<SoundEvent> PEDESTAL_SPIRIT_INSERT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("pedestal_spirit_inserted")));
    public static final RegistryObject<SoundEvent> PEDESTAL_SPIRIT_PICKUP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("pedestal_spirit_picked_up")));

    public static final RegistryObject<SoundEvent> CLOTH_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("cloth_trinket_equipped")));
    public static final RegistryObject<SoundEvent> ORNATE_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ornate_trinket_equipped")));
    public static final RegistryObject<SoundEvent> GILDED_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("gilded_trinket_equipped")));
    public static final RegistryObject<SoundEvent> ALCHEMICAL_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("alchemical_trinket_equipped")));
    public static final RegistryObject<SoundEvent> ROTTEN_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("rotten_trinket_equipped")));
    public static final RegistryObject<SoundEvent> METALLIC_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("metallic_trinket_equipped")));
    public static final RegistryObject<SoundEvent> RUNE_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("rune_trinket_equipped")));
    public static final RegistryObject<SoundEvent> VOID_TRINKET_EQUIP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("void_trinket_equipped")));

    public static final RegistryObject<SoundEvent> TUNING_FORK_TINKERS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("tuning_fork_tinkers")));
    public static final RegistryObject<SoundEvent> APPLY_AUGMENT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("augment_applied")));
    public static final RegistryObject<SoundEvent> REMOVE_AUGMENT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("augment_removed")));
    public static final RegistryObject<SoundEvent> WARPING_ENGINE_REVERBERATES = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("warping_engine_reverberates")));
    public static final RegistryObject<SoundEvent> SHIELDING_APPARATUS_SHIELDS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("shielding_apparatus_shields")));

    public static final RegistryObject<SoundEvent> ARCANE_WHISPERS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_whispers")));
    public static final RegistryObject<SoundEvent> SPIRIT_PICKUP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_picked_up")));
    public static final RegistryObject<SoundEvent> SOUL_SHATTER = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("a_soul_shatters")));

    public static final RegistryObject<SoundEvent> SCYTHE_SWEEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("scythe_sweeps")));
    public static final RegistryObject<SoundEvent> SCYTHE_CUT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("scythe_cuts")));
    public static final RegistryObject<SoundEvent> TYRVING_SLASH = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("tyrving_slashes_twice")));

    public static final RegistryObject<SoundEvent> WEIGHT_OF_WORLDS_SLASH = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("weight_of_worlds_slashes")));

    public static final RegistryObject<SoundEvent> STAFF_FIRES = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("staff_fires")));
    public static final RegistryObject<SoundEvent> STAFF_POWERS_UP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("staff_powers_up")));
    public static final RegistryObject<SoundEvent> STAFF_SIZZLES = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("staff_sizzles")));
    public static final RegistryObject<SoundEvent> STAFF_CHARGED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("staff_charged")));
    public static final RegistryObject<SoundEvent> STAFF_STRIKES = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("staff_strikes")));

    public static final RegistryObject<SoundEvent> AURIC_FLAME_MOTIF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("auric_flame_motif")));
    public static final RegistryObject<SoundEvent> DRAINING_MOTIF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("draining_motif")));

    public static final RegistryObject<SoundEvent> ALTAR_CRAFT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_altar_completes_infusion")));
    public static final RegistryObject<SoundEvent> ALTAR_LOOP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_altar_infuses")));
    public static final RegistryObject<SoundEvent> ALTAR_CONSUME = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_altar_absorbs_item")));
    public static final RegistryObject<SoundEvent> ALTAR_SPEED_UP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_altar_speeds_up")));

    public static final RegistryObject<SoundEvent> RUNIC_WORKBENCH_CRAFT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runic_workbench_crafts")));

    public static final RegistryObject<SoundEvent> RITUAL_BEGINS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_begins")));
    public static final RegistryObject<SoundEvent> RITUAL_ABSORBS_ITEM = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_absorbs_item")));
    public static final RegistryObject<SoundEvent> RITUAL_FORMS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_forms")));
    public static final RegistryObject<SoundEvent> RITUAL_ABSORBS_SPIRIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_absorbs_spirit")));
    public static final RegistryObject<SoundEvent> RITUAL_EVOLVES = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_evolves")));
    public static final RegistryObject<SoundEvent> RITUAL_COMPLETED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_completed")));
    public static final RegistryObject<SoundEvent> RITUAL_BEGINNING_AMBIENCE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_beginning_ambience")));
    public static final RegistryObject<SoundEvent> RITUAL_EVOLUTION_AMBIENCE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ritual_evolution_ambience")));
    public static final RegistryObject<SoundEvent> COMPLETED_RITUAL_AMBIENCE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("completed_ritual_ambience")));

    public static final RegistryObject<SoundEvent> TOTEM_CHARGE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("totem_charges")));
    public static final RegistryObject<SoundEvent> TOTEM_ACTIVATED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_rite_activated")));
    public static final RegistryObject<SoundEvent> TOTEM_CANCELLED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_rite_cancelled")));
    public static final RegistryObject<SoundEvent> TOTEM_ENGRAVE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_engraved")));

    public static final RegistryObject<SoundEvent> CRUCIBLE_CRAFT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_crucible_completes_focusing")));
    public static final RegistryObject<SoundEvent> CRUCIBLE_LOOP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_crucible_focuses")));
    public static final RegistryObject<SoundEvent> IMPETUS_CRACK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("impetus_takes_damage")));

    public static final RegistryObject<SoundEvent> ALTERATION_PLINTH_ALTERS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("alteration_plinth_alters")));

    public static final RegistryObject<SoundEvent> UNCANNY_VALLEY = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("the_unknown_weeps")));
    public static final RegistryObject<SoundEvent> VOID_HEARTBEAT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("the_void_heart_beats")));
    public static final RegistryObject<SoundEvent> SONG_OF_THE_VOID = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("song_of_the_void")));
    public static final RegistryObject<SoundEvent> VOID_REJECTION = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("rejected_by_the_unknown")));
    public static final RegistryObject<SoundEvent> VOID_TRANSMUTATION = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("void_transmutation")));
    public static final RegistryObject<SoundEvent> VOID_EATS_GUNK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("void_eats_gunk")));

    public static final RegistryObject<SoundEvent> AERIAL_FALL = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("aerial_magic_swooshes")));

    public static final RegistryObject<SoundEvent> CONCENTRATED_GLUTTONY_DRINK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("concentrated_gluttony_drink")));

    public static final RegistryObject<SoundEvent> HIDDEN_BLADE_UNLEASHED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("hidden_blade_unleashed")));
    public static final RegistryObject<SoundEvent> MALIGNANT_METAL_RESONATES = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("malignant_metal_resonates")));
    public static final RegistryObject<SoundEvent> HUNGRY_BELT_FEEDS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("hungry_belt_feeds")));
    public static final RegistryObject<SoundEvent> VORACIOUS_RING_FEEDS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("voracious_ring_feeds")));
    public static final RegistryObject<SoundEvent> GRUESOME_RING_FEEDS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("gruesome_ring_feeds")));
    public static final RegistryObject<SoundEvent> FLESH_RING_ABSORBS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("flesh_ring_absorbs")));
    public static final RegistryObject<SoundEvent> SPIRIT_MOTE_CREATED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("spirit_mote_created")));

    public static final RegistryObject<SoundEvent> CATALYST_LOBBER_UNLOCKED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("catalyst_lobber_unlocked")));
    public static final RegistryObject<SoundEvent> CATALYST_LOBBER_LOCKED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("catalyst_lobber_locked")));
    public static final RegistryObject<SoundEvent> CATALYST_LOBBER_PRIMED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("catalyst_lobber_primed")));
    public static final RegistryObject<SoundEvent> CATALYST_LOBBER_FIRED = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("catalyst_lobber_fired")));

    public static final RegistryObject<SoundEvent> SOUL_WARD_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_ward_damaged")));
    public static final RegistryObject<SoundEvent> SOUL_WARD_GROW = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_ward_grows")));
    public static final RegistryObject<SoundEvent> SOUL_WARD_CHARGE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_ward_charged")));
    public static final RegistryObject<SoundEvent> SOUL_WARD_DEPLETE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_ward_depleted")));

    public static final RegistryObject<SoundEvent> SOULSTONE_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulstone_break")));
    public static final RegistryObject<SoundEvent> SOULSTONE_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulstone_place")));
    public static final RegistryObject<SoundEvent> SOULSTONE_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulstone_step")));
    public static final RegistryObject<SoundEvent> SOULSTONE_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulstone_hit")));

    public static final RegistryObject<SoundEvent> DEEPSLATE_SOULSTONE_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("deepslate_soulstone_break")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_SOULSTONE_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("deepslate_soulstone_place")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_SOULSTONE_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("deepslate_soulstone_step")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_SOULSTONE_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("deepslate_soulstone_hit")));

    public static final RegistryObject<SoundEvent> BLAZING_QUARTZ_ORE_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blazing_quartz_ore_break")));
    public static final RegistryObject<SoundEvent> BLAZING_QUARTZ_ORE_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blazing_quartz_ore_place")));

    public static final RegistryObject<SoundEvent> BLAZING_QUARTZ_BLOCK_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blazing_quartz_block_break")));
    public static final RegistryObject<SoundEvent> BLAZING_QUARTZ_BLOCK_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blazing_quartz_block_place")));
    public static final RegistryObject<SoundEvent> BLAZING_QUARTZ_BLOCK_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blazing_quartz_block_step")));
    public static final RegistryObject<SoundEvent> BLAZING_QUARTZ_BLOCK_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blazing_quartz_block_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_CHARCOAL_BLOCK_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_charcoal_block_break")));
    public static final RegistryObject<SoundEvent> ARCANE_CHARCOAL_BLOCK_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_charcoal_block_place")));
    public static final RegistryObject<SoundEvent> ARCANE_CHARCOAL_BLOCK_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_charcoal_block_step")));
    public static final RegistryObject<SoundEvent> ARCANE_CHARCOAL_BLOCK_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_charcoal_block_hit")));

    public static final RegistryObject<SoundEvent> BRILLIANCE_BLOCK_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("brilliance_block_break")));
    public static final RegistryObject<SoundEvent> BRILLIANCE_BLOCK_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("brilliance_block_place")));
    public static final RegistryObject<SoundEvent> BRILLIANCE_BLOCK_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("brilliance_block_step")));
    public static final RegistryObject<SoundEvent> BRILLIANCE_BLOCK_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("brilliance_block_hit")));

    public static final RegistryObject<SoundEvent> QUARTZ_CLUSTER_BLOCK_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("quartz_cluster_block_break")));
    public static final RegistryObject<SoundEvent> QUARTZ_CLUSTER_BLOCK_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("quartz_cluster_block_place")));
    public static final RegistryObject<SoundEvent> QUARTZ_CLUSTER_BLOCK_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("quartz_cluster_block_step")));
    public static final RegistryObject<SoundEvent> QUARTZ_CLUSTER_BLOCK_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("quartz_cluster_block_hit")));

    public static final RegistryObject<SoundEvent> CTHONIC_GOLD_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("cthonic_gold_break")));
    public static final RegistryObject<SoundEvent> CTHONIC_GOLD_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("cthonic_gold_place")));

    public static final RegistryObject<SoundEvent> RUNEWOOD_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_break")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_place")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_step")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_hit")));

    public static final RegistryObject<SoundEvent> RUNEWOOD_HANGING_SIGN_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_hanging_sign_break")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_HANGING_SIGN_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_hanging_sign_place")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_HANGING_SIGN_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_hanging_sign_step")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_HANGING_SIGN_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_hanging_sign_hit")));

    public static final RegistryObject<SoundEvent> RUNEWOOD_BUTTON_CLICK_OFF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_button_click_off")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_BUTTON_CLICK_ON = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_button_click_on")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_PRESSURE_PLATE_CLICK_OFF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_PRESSURE_PLATE_CLICK_ON = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_pressure_plate_click_on")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_FENCE_GATE_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_fence_gate_close")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_FENCE_GATE_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_fence_gate_open")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_DOOR_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_door_close")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_DOOR_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_door_open")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_TRAPDOOR_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_trapdoor_close")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_TRAPDOOR_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_trapdoor_open")));
    
    public static final RegistryObject<SoundEvent> SOULWOOD_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_break")));
    public static final RegistryObject<SoundEvent> SOULWOOD_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_place")));
    public static final RegistryObject<SoundEvent> SOULWOOD_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_step")));
    public static final RegistryObject<SoundEvent> SOULWOOD_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_hit")));

    public static final RegistryObject<SoundEvent> SOULWOOD_HANGING_SIGN_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_hanging_sign_break")));
    public static final RegistryObject<SoundEvent> SOULWOOD_HANGING_SIGN_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_hanging_sign_place")));
    public static final RegistryObject<SoundEvent> SOULWOOD_HANGING_SIGN_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_hanging_sign_step")));
    public static final RegistryObject<SoundEvent> SOULWOOD_HANGING_SIGN_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_hanging_sign_hit")));

    public static final RegistryObject<SoundEvent> SOULWOOD_BUTTON_CLICK_OFF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_button_click_off")));
    public static final RegistryObject<SoundEvent> SOULWOOD_BUTTON_CLICK_ON = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_button_click_on")));
    public static final RegistryObject<SoundEvent> SOULWOOD_PRESSURE_PLATE_CLICK_OFF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> SOULWOOD_PRESSURE_PLATE_CLICK_ON = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_pressure_plate_click_on")));
    public static final RegistryObject<SoundEvent> SOULWOOD_FENCE_GATE_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_fence_gate_close")));
    public static final RegistryObject<SoundEvent> SOULWOOD_FENCE_GATE_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_fence_gate_open")));
    public static final RegistryObject<SoundEvent> SOULWOOD_DOOR_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_door_close")));
    public static final RegistryObject<SoundEvent> SOULWOOD_DOOR_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_door_open")));
    public static final RegistryObject<SoundEvent> SOULWOOD_TRAPDOOR_CLOSE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_trapdoor_close")));
    public static final RegistryObject<SoundEvent> SOULWOOD_TRAPDOOR_OPEN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_trapdoor_open")));

    public static final RegistryObject<SoundEvent> ARCANE_ROCK_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_break")));
    public static final RegistryObject<SoundEvent> ARCANE_ROCK_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_place")));
    public static final RegistryObject<SoundEvent> ARCANE_ROCK_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_step")));
    public static final RegistryObject<SoundEvent> ARCANE_ROCK_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_ROCK_BRICKS_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_bricks_break")));
    public static final RegistryObject<SoundEvent> ARCANE_ROCK_BRICKS_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_bricks_place")));
    public static final RegistryObject<SoundEvent> ARCANE_ROCK_BRICKS_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_bricks_step")));
    public static final RegistryObject<SoundEvent> ARCANE_ROCK_BRICKS_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_rock_bricks_hit")));

    public static final RegistryObject<SoundEvent> HALLOWED_GOLD_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("hallowed_gold_break")));
    public static final RegistryObject<SoundEvent> HALLOWED_GOLD_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("hallowed_gold_hit")));
    public static final RegistryObject<SoundEvent> HALLOWED_GOLD_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("hallowed_gold_place")));
    public static final RegistryObject<SoundEvent> HALLOWED_GOLD_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("hallowed_gold_step")));

    public static final RegistryObject<SoundEvent> SOUL_STAINED_STEEL_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_stained_steel_break")));
    public static final RegistryObject<SoundEvent> SOUL_STAINED_STEEL_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_stained_steel_hit")));
    public static final RegistryObject<SoundEvent> SOUL_STAINED_STEEL_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_stained_steel_place")));
    public static final RegistryObject<SoundEvent> SOUL_STAINED_STEEL_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soul_stained_steel_step")));

    public static final RegistryObject<SoundEvent> RUNEWOOD_LEAVES_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_leaves_break")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_LEAVES_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_leaves_hit")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_LEAVES_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_leaves_place")));
    public static final RegistryObject<SoundEvent> RUNEWOOD_LEAVES_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("runewood_leaves_step")));

    public static final RegistryObject<SoundEvent> SOULWOOD_LEAVES_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_leaves_break")));
    public static final RegistryObject<SoundEvent> SOULWOOD_LEAVES_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_leaves_hit")));
    public static final RegistryObject<SoundEvent> SOULWOOD_LEAVES_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_leaves_place")));
    public static final RegistryObject<SoundEvent> SOULWOOD_LEAVES_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("soulwood_leaves_step")));

    public static final RegistryObject<SoundEvent> CALCIFIED_BLIGHT_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("calcified_blight_break")));
    public static final RegistryObject<SoundEvent> CALCIFIED_BLIGHT_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("calcified_blight_place")));
    public static final RegistryObject<SoundEvent> CALCIFIED_BLIGHT_STEP = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("calcified_blight_step")));
    public static final RegistryObject<SoundEvent> CALCIFIED_BLIGHT_HIT = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("calcified_blight_hit")));

    public static final RegistryObject<SoundEvent> MAJOR_BLIGHT_MOTIF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blight_reacts")));
    public static final RegistryObject<SoundEvent> MINOR_BLIGHT_MOTIF = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("blight_reacts_faintly")));

    public static final RegistryObject<SoundEvent> ETHER_PLACE = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ether_place")));
    public static final RegistryObject<SoundEvent> ETHER_BREAK = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("ether_break")));

    public static final RegistryObject<SoundEvent> THE_DEEP_BECKONS = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("the_deep_beckons")));
    public static final RegistryObject<SoundEvent> THE_HEAVENS_SIGN = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("the_heavens_sing")));

    public static final RegistryObject<SoundEvent> ARCANE_ELEGY = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("arcane_elegy")));
    public static final RegistryObject<SoundEvent> AESTHETICA = register(SoundEvent.createVariableRangeEvent(MalumMod.malumPath("aesthetica")));


    public static final SoundType SOULSTONE = new ForgeSoundType(1.0F, 1.0F, SOULSTONE_BREAK, SOULSTONE_STEP, SOULSTONE_PLACE, SOULSTONE_HIT, () -> SoundEvents.STONE_FALL);
    public static final SoundType DEEPSLATE_SOULSTONE = new ForgeSoundType(1.0F, 1.0F, DEEPSLATE_SOULSTONE_BREAK, DEEPSLATE_SOULSTONE_STEP, DEEPSLATE_SOULSTONE_PLACE, DEEPSLATE_SOULSTONE_HIT, () -> SoundEvents.DEEPSLATE_FALL);
    public static final SoundType BLAZING_QUARTZ_ORE = new ForgeSoundType(1.0F, 1.0F, BLAZING_QUARTZ_ORE_BREAK, () -> SoundEvents.NETHER_ORE_STEP, BLAZING_QUARTZ_ORE_PLACE, () -> SoundEvents.NETHER_ORE_HIT, () -> SoundEvents.NETHER_GOLD_ORE_FALL);
    public static final SoundType NATURAL_QUARTZ = new QuartzSoundType(1.0F, 0.9f, () -> SoundEvents.STONE_BREAK, () -> SoundEvents.STONE_STEP, () -> SoundEvents.STONE_PLACE, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);
    public static final SoundType DEEPSLATE_QUARTZ = new QuartzSoundType(1.0F, 0.9f, () -> SoundEvents.DEEPSLATE_BREAK, () -> SoundEvents.DEEPSLATE_STEP, () -> SoundEvents.DEEPSLATE_PLACE, () -> SoundEvents.DEEPSLATE_HIT, () -> SoundEvents.DEEPSLATE_FALL);
    public static final SoundType CTHONIC_GOLD = new RareEarthSoundType(1.0F, 1.15f, SOULSTONE_BREAK, SOULSTONE_STEP, SOULSTONE_PLACE, DEEPSLATE_SOULSTONE_HIT, () -> SoundEvents.DEEPSLATE_FALL);
    public static final SoundType MALIGNANT_LEAD = new RareEarthSoundType(1.0F, 0.7f, () -> SoundEvents.DEEPSLATE_BREAK, () -> SoundEvents.DEEPSLATE_STEP, () -> SoundEvents.DEEPSLATE_PLACE, () -> SoundEvents.DEEPSLATE_HIT, () -> SoundEvents.NETHER_ORE_FALL);

    public static final SoundType BRILLIANCE_BLOCK = new ForgeSoundType(1.0F, 1.4f, BRILLIANCE_BLOCK_BREAK, BRILLIANCE_BLOCK_STEP, BRILLIANCE_BLOCK_PLACE, BRILLIANCE_BLOCK_HIT, () -> SoundEvents.NETHER_GOLD_ORE_FALL);
    public static final SoundType BLAZING_QUARTZ_BLOCK = new ForgeSoundType(1.0F, 1.25f, BLAZING_QUARTZ_BLOCK_BREAK, BLAZING_QUARTZ_BLOCK_STEP, BLAZING_QUARTZ_BLOCK_PLACE, BLAZING_QUARTZ_BLOCK_HIT, () -> SoundEvents.NETHER_GOLD_ORE_FALL);
    public static final SoundType BLAZING_QUARTZ_CLUSTER = new QuartzClusterSoundType(0.3F, 1.5f, BLAZING_QUARTZ_BLOCK_BREAK, BLAZING_QUARTZ_BLOCK_STEP, BLAZING_QUARTZ_BLOCK_PLACE, BLAZING_QUARTZ_BLOCK_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL);
    public static final SoundType ARCANE_CHARCOAL_BLOCK = new ForgeSoundType(1.0F, 0.9f, ARCANE_CHARCOAL_BLOCK_BREAK, ARCANE_CHARCOAL_BLOCK_STEP, ARCANE_CHARCOAL_BLOCK_PLACE, ARCANE_CHARCOAL_BLOCK_HIT, () -> SoundEvents.NETHER_GOLD_ORE_FALL);
    public static final SoundType QUARTZ_CLUSTER = new QuartzClusterSoundType(0.3F, 1.5f, QUARTZ_CLUSTER_BLOCK_BREAK, QUARTZ_CLUSTER_BLOCK_STEP, QUARTZ_CLUSTER_BLOCK_PLACE, QUARTZ_CLUSTER_BLOCK_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL);
    public static final SoundType HALLOWED_GOLD = new ForgeSoundType(1.0F, 1.0F, HALLOWED_GOLD_BREAK, HALLOWED_GOLD_STEP, HALLOWED_GOLD_PLACE, HALLOWED_GOLD_HIT, () -> SoundEvents.METAL_FALL);
    public static final SoundType SOUL_STAINED_STEEL = new ForgeSoundType(1.0F, 1.0F, SOUL_STAINED_STEEL_BREAK, SOUL_STAINED_STEEL_STEP, SOUL_STAINED_STEEL_PLACE, SOUL_STAINED_STEEL_HIT, () -> SoundEvents.METAL_FALL);
    public static final SoundType MALIGNANT_PEWTER = new RareEarthSoundType(1.0F, 0.7f, SOUL_STAINED_STEEL_BREAK, SOUL_STAINED_STEEL_STEP, SOUL_STAINED_STEEL_PLACE, SOUL_STAINED_STEEL_HIT, () -> SoundEvents.METAL_FALL);

    public static final SoundType TAINTED_ROCK = new ForgeSoundType(1.0F, 1.1F, ARCANE_ROCK_BREAK, ARCANE_ROCK_STEP, ARCANE_ROCK_PLACE, ARCANE_ROCK_HIT, () -> SoundEvents.BASALT_FALL);
    public static final SoundType TAINTED_ROCK_BRICKS = new ForgeSoundType(1.0F, 1.1F, ARCANE_ROCK_BRICKS_BREAK, ARCANE_ROCK_BRICKS_STEP, ARCANE_ROCK_BRICKS_PLACE, ARCANE_ROCK_BRICKS_HIT, () -> SoundEvents.BASALT_FALL);
    public static final SoundType TWISTED_ROCK = new ForgeSoundType(1.0F, 0.85F, ARCANE_ROCK_BREAK, ARCANE_ROCK_STEP, ARCANE_ROCK_PLACE, ARCANE_ROCK_HIT, () -> SoundEvents.BASALT_FALL);
    public static final SoundType TWISTED_ROCK_BRICKS = new ForgeSoundType(1.0F, 0.85F, ARCANE_ROCK_BRICKS_BREAK, ARCANE_ROCK_BRICKS_STEP, ARCANE_ROCK_BRICKS_PLACE, ARCANE_ROCK_BRICKS_HIT, () -> SoundEvents.BASALT_FALL);

    public static final SoundType RUNEWOOD = new ForgeSoundType(1.0F, 1.3F, RUNEWOOD_BREAK, RUNEWOOD_STEP, RUNEWOOD_PLACE, RUNEWOOD_HIT, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final SoundType RUNEWOOD_HANGING_SIGN = new ForgeSoundType(1.0F, 1.3F, RUNEWOOD_HANGING_SIGN_BREAK, RUNEWOOD_HANGING_SIGN_STEP, RUNEWOOD_HANGING_SIGN_PLACE, RUNEWOOD_HANGING_SIGN_HIT, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final SoundType RUNEWOOD_LEAVES = new ForgeSoundType(1.0F, 1.1F, RUNEWOOD_LEAVES_BREAK, RUNEWOOD_LEAVES_STEP, RUNEWOOD_LEAVES_PLACE, RUNEWOOD_LEAVES_HIT, () -> SoundEvents.AZALEA_LEAVES_FALL);

    public static final SoundType SOULWOOD = new BlightedSoundType(1.0F, 1.1f, SOULWOOD_BREAK, SOULWOOD_STEP, SOULWOOD_PLACE, SOULWOOD_HIT, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final SoundType SOULWOOD_HANGING_SIGN = new ForgeSoundType(1.0F, 1.1f, SOULWOOD_HANGING_SIGN_BREAK, SOULWOOD_HANGING_SIGN_STEP, SOULWOOD_HANGING_SIGN_PLACE, SOULWOOD_HANGING_SIGN_HIT, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final SoundType SOULWOOD_LEAVES = new BlightedSoundType(1.0F, 0.9F, SOULWOOD_LEAVES_BREAK, SOULWOOD_LEAVES_STEP, SOULWOOD_LEAVES_PLACE, SOULWOOD_LEAVES_HIT, () -> SoundEvents.AZALEA_LEAVES_FALL);


    public static final SoundType WEEPING_WELL_BRICKS = new ForgeSoundType(1.0F, 0.6f, ARCANE_ROCK_BRICKS_BREAK, ARCANE_ROCK_BRICKS_STEP, ARCANE_ROCK_BRICKS_PLACE, ARCANE_ROCK_BRICKS_HIT, () -> SoundEvents.BASALT_FALL);

    public static final SoundType BLIGHTED_FOLIAGE = new BlightedSoundType(1.0F, 1.0F, () -> SoundEvents.NETHER_WART_BREAK, () -> SoundEvents.STONE_STEP, () -> SoundEvents.NETHER_WART_PLANTED, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);
    public static final SoundType BLIGHTED_EARTH = new BlightedSoundType(1.0F, 1.0F, () -> SoundEvents.NYLIUM_BREAK, () -> SoundEvents.NYLIUM_STEP, () -> SoundEvents.NYLIUM_PLACE, () -> SoundEvents.NYLIUM_HIT, () -> SoundEvents.NYLIUM_FALL);
    public static final SoundType CURSED_SAP = new BlightedSoundType(1.0F, 0.9F, () -> SoundEvents.SLIME_BLOCK_BREAK, () -> SoundEvents.SLIME_BLOCK_STEP, () -> SoundEvents.SLIME_BLOCK_PLACE, () -> SoundEvents.SLIME_BLOCK_HIT, () -> SoundEvents.SLIME_BLOCK_FALL);

    public static final SoundType CALCIFIED_BLIGHT = new ForgeSoundType(1.0F, 1.25F, CALCIFIED_BLIGHT_BREAK, CALCIFIED_BLIGHT_STEP, CALCIFIED_BLIGHT_PLACE, CALCIFIED_BLIGHT_HIT, () -> SoundEvents.BONE_BLOCK_FALL);

    public static final SoundType ETHER = new ForgeSoundType(1.0F, 1.0F, ETHER_BREAK, () -> SoundEvents.WOOL_STEP, ETHER_PLACE, () -> SoundEvents.ANCIENT_DEBRIS_HIT, () -> SoundEvents.WOOL_FALL);

    public static RegistryObject<SoundEvent> register(SoundEvent soundEvent) {
        return SOUNDS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }
}