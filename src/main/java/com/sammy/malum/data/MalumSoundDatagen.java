package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.malumPath;


public class MalumSoundDatagen extends SoundDefinitionsProvider {

    public MalumSoundDatagen(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        this.add(SoundRegistry.ARCANA_CODEX_OPEN, s -> definition(s).with(sounds("codex/book_open", 4)));
        this.add(SoundRegistry.ARCANA_CODEX_CLOSE, s -> definition(s).with(sounds("codex/book_close", 4)));
        this.add(SoundRegistry.ARCANA_ENTRY_OPEN, s -> definition(s).with(sounds("codex/book_entry_open", 4)));
        this.add(SoundRegistry.ARCANA_ENTRY_CLOSE, s -> definition(s).with(sounds("codex/book_entry_close", 4)));
        this.add(SoundRegistry.ARCANA_PAGE_FLIP, s -> definition(s).with(sounds("codex/book_page_turn", 4)));
        this.add(SoundRegistry.ARCANA_SWEETENER_NORMAL, s -> definition(s).with(sounds("codex/book_swtnr_normal", 8)));
        this.add(SoundRegistry.ARCANA_SWEETENER_EVIL, s -> definition(s).with(sounds("codex/book_swtnr_evil", 8)));
        this.add(SoundRegistry.ARCANA_TRANSITION_NORMAL, s -> definition(s).with(sounds("codex/book_transition_normal", 2)));
        this.add(SoundRegistry.ARCANA_TRANSITION_EVIL, s -> definition(s).with(sounds("codex/book_transition_evil", 2)));

        this.add(SoundRegistry.PEDESTAL_ITEM_INSERT, s -> definition(s).with(sounds("block_interaction/pedestal_item_insert", 3)));
        this.add(SoundRegistry.PEDESTAL_ITEM_PICKUP, s -> definition(s).with(sounds("block_interaction/pedestal_item_remove", 3)));
        this.add(SoundRegistry.PEDESTAL_SPIRIT_INSERT, s -> definition(s).with(sounds("block_interaction/pedestal_spirit_insert", 3)));
        this.add(SoundRegistry.PEDESTAL_SPIRIT_PICKUP, s -> definition(s).with(sounds("block_interaction/pedestal_spirit_remove", 3)));

        this.add(SoundRegistry.CLOTH_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/cloth/equip_cloth", 3)));
        this.add(SoundRegistry.ORNATE_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/ornate/equip_ornate", 3)));
        this.add(SoundRegistry.GILDED_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/gilded/equip_gilded", 3)));
        this.add(SoundRegistry.ALCHEMICAL_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/alchemical/equip_alchemical", 3)));
        this.add(SoundRegistry.ROTTEN_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/rotten/equip_rotten", 3)));
        this.add(SoundRegistry.METALLIC_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/metallic/equip_metallic", 3)));
        this.add(SoundRegistry.RUNE_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/rune/equip_rune", 3)));
        this.add(SoundRegistry.VOID_TRINKET_EQUIP, s -> definition(s).with(sounds("equip_sounds/void/equip_void", 3)));

        this.add(SoundRegistry.HUNGRY_BELT_FEEDS, s -> definition(s).with(sounds("curiosities/trinkets/starved/nom", 4)));
        this.add(SoundRegistry.VORACIOUS_RING_FEEDS, s -> definition(s).with(sounds("curiosities/trinkets/starved/nom", 4)));
        this.add(SoundRegistry.GRUESOME_RING_FEEDS, s -> definition(s).with(sounds("curiosities/trinkets/starved/nom", 4)));
        this.add(SoundRegistry.FLESH_RING_ABSORBS, s -> definition(s).with(sounds("curiosities/trinkets/cancer_ring/grow", 3)));
        this.add(SoundRegistry.ECHOING_RING_ABSORBS, s -> definition(s).with(sounds("curiosities/trinkets/cancer_ring/grow", 3))); //TODO: this needs a unique sound

        this.add(SoundRegistry.CONCENTRATED_GLUTTONY_DRINK, s -> definition(s).with(sounds("curiosities/concentrated_gluttony/drink", 2)));
        this.add(SoundRegistry.SPIRIT_MOTE_CREATED, s -> definition(s).with(sounds("curiosities/spirit_mote/created", 3)));
        this.add(SoundRegistry.TUNING_FORK_TINKER, s -> definition(s).with(sounds("curiosities/augments/tinker", 6)));
        this.add(SoundRegistry.CRUCIBLE_AUGMENT_APPLY, s -> definition(s).with(sounds("curiosities/augments/augment_insert", 3)));
        this.add(SoundRegistry.CRUCIBLE_AUGMENT_REMOVE, s -> definition(s).with(sounds("curiosities/augments/augment_remove", 3)));
        this.add(SoundRegistry.WARPING_ENGINE_REVERBERATES, s -> definition(s).with(sounds("curiosities/augments/warping_engine_reverberates", 4)));
        this.add(SoundRegistry.SHIELDING_APPARATUS_SHIELDS, s -> definition(s).with(sounds("curiosities/augments/shielding_apparatus_shields", 2)));

        this.add(SoundRegistry.ARCANE_WHISPERS, s -> definition(s).with(sounds("curiosities/spirit/spirit_idle", 4)));
        this.add(SoundRegistry.SPIRIT_PICKUP, s -> definition(s).with(sounds("curiosities/spirit/spirit_pickup", 4)));
        this.add(SoundRegistry.SOUL_SHATTER, s -> definition(s).with(sounds("curiosities/soul_shatter", 3)));

        this.add(SoundRegistry.SOUL_WARD_HIT, s -> definition(s).with(sounds("curiosities/soul_ward/grow", 4)));
        this.add(SoundRegistry.SOUL_WARD_GROW, s -> definition(s).with(sounds("curiosities/soul_ward/dmg", 4)));
        this.add(SoundRegistry.SOUL_WARD_CHARGE, s -> definition(s).with(sounds("curiosities/soul_ward/full", 2)));
        this.add(SoundRegistry.SOUL_WARD_DEPLETE, s -> definition(s).with(sounds("curiosities/soul_ward/break", 2)));

        this.add(SoundRegistry.SCYTHE_SWEEP, s -> definition(s).with(sounds("curiosities/weapons/scythe/sweep", 7)));
        this.add(SoundRegistry.SCYTHE_CUT, s -> definition(s).with(sounds("curiosities/weapons/scythe/sweep", 7)));
        this.add(SoundRegistry.EDGE_OF_DELIVERANCE_SWEEP, s -> definition(s).with(sounds("curiosities/weapons/scythe/sweep", 7)));
        this.add(SoundRegistry.EDGE_OF_DELIVERANCE_CUT, s -> definition(s).with(sounds("curiosities/weapons/scythe/sweep", 7)));

        this.add(SoundRegistry.SCYTHE_ASCENSION, s -> definition(s).with(sounds("curiosities/weapons/scythe/throw", 3)));
        this.add(SoundRegistry.SCYTHE_THROW, s -> definition(s).with(sounds("curiosities/weapons/scythe/throw", 3)));
        this.add(SoundRegistry.SCYTHE_SPINS, s -> definition(s).with(sound("curiosities/weapons/scythe/spin")));
        this.add(SoundRegistry.SCYTHE_CATCH, s -> definition(s).with(sounds("curiosities/weapons/scythe/catch", 2)));

        this.add(SoundRegistry.HIDDEN_BLADE_CHARGED, s -> definition(s).with(sound("curiosities/trinkets/hidden_blade/charge")));
        this.add(SoundRegistry.HIDDEN_BLADE_PRIMED, s -> definition(s).with(sound("curiosities/trinkets/hidden_blade/charge")));
        this.add(SoundRegistry.HIDDEN_BLADE_DISRUPTED, s -> definition(s).with(sound("curiosities/trinkets/hidden_blade/charge")));
        this.add(SoundRegistry.HIDDEN_BLADE_UNLEASHED, s -> definition(s).with(sounds("curiosities/trinkets/hidden_blade/flurry", 2)));

        this.add(SoundRegistry.TYRVING_SLASH, s -> definition(s).with(sounds("curiosities/weapons/tyrving/hit", 5)));
        this.add(SoundRegistry.WEIGHT_OF_WORLDS_CUT, s -> definition(s).with(sounds("curiosities/weapons/scythe/sweep", 7)));

        this.add(SoundRegistry.STAFF_FIRES, s -> definition(s).with(sounds("curiosities/weapons/staff/fire", 2)));
        this.add(SoundRegistry.STAFF_POWERS_UP, s -> definition(s).with(sounds("curiosities/weapons/staff/power_up", 2)));
        this.add(SoundRegistry.STAFF_SIZZLES_OUT, s -> definition(s).with(sounds("curiosities/weapons/staff/sizzle", 2)));
        this.add(SoundRegistry.STAFF_CHARGED, s -> definition(s).with(sounds("curiosities/weapons/staff/charge", 2)));
        this.add(SoundRegistry.STAFF_STRIKES, s -> definition(s).with(sounds("curiosities/weapons/staff/hit", 4)));

        this.add(SoundRegistry.AURIC_FLAME_MOTIF, s -> definition(s).with(sounds("curiosities/motifs/auric_cinders", 3)));
        this.add(SoundRegistry.DRAINING_MOTIF, s -> definition(s).with(sounds("curiosities/motifs/draining_bubbling", 3)));
        this.add(SoundRegistry.MALIGNANT_METAL_MOTIF, s -> definition(s).with(sounds("curiosities/motifs/malignant_crit", 3)));

        this.add(SoundRegistry.ALTAR_LOOP, s -> definition(s).with(sound("altar/altar_loop")));
        this.add(SoundRegistry.ALTAR_CRAFT, s -> definition(s).with(sounds("altar/altar_craft", 2)));
        this.add(SoundRegistry.ALTAR_CONSUME, s -> definition(s).with(sounds("altar/altar_speedup", 2)));
        this.add(SoundRegistry.ALTAR_SPEED_UP, s -> definition(s).with(sounds("altar/altar_consume", 2)));

        this.add(SoundRegistry.CRUCIBLE_LOOP, s -> definition(s).with(sound("crucible/crucible_loop")));
        this.add(SoundRegistry.CRUCIBLE_CRAFT, s -> definition(s).with(sounds("crucible/crucible_craft", 3)));
        this.add(SoundRegistry.IMPETUS_CRACK, s -> definition(s).with(sounds("crucible/impetus_crack", 3)));

        this.add(SoundRegistry.REPAIR_PYLON_LOOP, s -> definition(s).with(sound("repair_pylon/repair_pylon_loop")));
        this.add(SoundRegistry.REPAIR_PYLON_REPAIR_START, s -> definition(s).with(sounds("repair_pylon/repair_pylon_send", 4)));
        this.add(SoundRegistry.REPAIR_PYLON_REPAIR_FINISH, s -> definition(s).with(sounds("repair_pylon/repair_pylon_fix", 4)));

        this.add(SoundRegistry.RUNIC_WORKBENCH_CRAFT, s -> definition(s).with(sounds("runic_workbench/craft", 2)));
        this.add(SoundRegistry.WEAVERS_WORKBENCH_CRAFT, s -> definition(s).with(sounds("runic_workbench/craft", 2)));

        this.add(SoundRegistry.TOTEM_CHARGE, s -> definition(s).with(sounds("totem/totem_charge", 3)));
        this.add(SoundRegistry.TOTEM_ACTIVATED, s -> definition(s).with(sound("totem/totem_activate")));
        this.add(SoundRegistry.TOTEM_CANCELLED, s -> definition(s).with(sound("totem/totem_cancel")));
        this.add(SoundRegistry.TOTEM_ENGRAVE, s -> definition(s).with(sounds("totem/totem_engrave", 3)));
        this.add(SoundRegistry.TOTEM_AERIAL_MAGIC, s -> definition(s).with(sounds("minecraft:mob/phantom/flap", 6)));

        this.add(SoundRegistry.RITUAL_BEGINS, s -> definition(s).with(sound("ritual/ritual_start")));
        this.add(SoundRegistry.RITUAL_ABSORBS_ITEM, s -> definition(s).with(sounds("ritual/ritual_absorb_item", 3)));
        this.add(SoundRegistry.RITUAL_FORMS, s -> definition(s).with(sound("ritual/ritual_stage2")));
        this.add(SoundRegistry.RITUAL_ABSORBS_SPIRIT, s -> definition(s).with(sounds("ritual/ritual_absorb_spirit", 3)));
        this.add(SoundRegistry.RITUAL_EVOLVES, s -> definition(s).with(sounds("ritual/ritual_level_up", 2)));
        this.add(SoundRegistry.RITUAL_COMPLETED, s -> definition(s).with(sound("ritual/ritual_level_up_max")));
        this.add(SoundRegistry.RITUAL_BEGINNING_AMBIENCE, s -> definition(s).with(sound("ritual/ritual_loop1")));
        this.add(SoundRegistry.RITUAL_EVOLUTION_AMBIENCE, s -> definition(s).with(sound("ritual/ritual_loop2")));
        this.add(SoundRegistry.COMPLETED_RITUAL_AMBIENCE, s -> definition(s).with(sound("ritual/ritual_loop3")));

        this.add(SoundRegistry.UNCANNY_VALLEY, s -> definition(s).with(sounds("weeping_well/uncanny_valley", 2)));
        this.add(SoundRegistry.VOID_HEARTBEAT, s -> definition(s).with(sound("weeping_well/void_heartbeat")));
        this.add(SoundRegistry.SONG_OF_THE_VOID, s -> definition(s).with(sounds("weeping_well/song_of_the_void", 3)));
        this.add(SoundRegistry.VOID_REJECTION, s -> definition(s).with(sound("weeping_well/void_rejection")));
        this.add(SoundRegistry.VOID_TRANSMUTATION, s -> definition(s).with(sounds("weeping_well/void_transmutation", 2)));
        this.add(SoundRegistry.VOID_EATS_GUNK, s -> definition(s).with(sounds("curiosities/trinkets/starved/nom", 4)));


        this.add(SoundRegistry.QUARTZ_CLUSTER_BLOCK_BREAK, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.QUARTZ_CLUSTER_BLOCK_STEP, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.QUARTZ_CLUSTER_BLOCK_PLACE, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.QUARTZ_CLUSTER_BLOCK_HIT, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));


        this.add(SoundRegistry.CATALYST_LOBBER_UNLOCKED, s -> definition(s).with(sounds("curiosities/catalyst_lobber/open", 2)));
        this.add(SoundRegistry.CATALYST_LOBBER_LOCKED, s -> definition(s).with(sounds("curiosities/catalyst_lobber/open", 2)));
        this.add(SoundRegistry.CATALYST_LOBBER_PRIMED, s -> definition(s).with(sounds("curiosities/catalyst_lobber/load", 2)));
        this.add(SoundRegistry.CATALYST_LOBBER_FIRED, s -> definition(s).with(sounds("curiosities/catalyst_lobber/fire", 2)));

        this.add(SoundRegistry.THE_DEEP_BECKONS, s -> definition(s).with(sound("suspicious_sound")));
        this.add(SoundRegistry.THE_HEAVENS_SIGN, s -> definition(s).with(sound("heavenly_organs")));


        this.add(SoundRegistry.RUNEWOOD_BREAK, s -> definition(s).with(sounds("blocks/runewood/break", 6)));
        this.add(SoundRegistry.RUNEWOOD_STEP, s -> definition(s).with(sounds("blocks/runewood/hit", 6)));
        this.add(SoundRegistry.RUNEWOOD_PLACE, s -> definition(s).with(sounds("blocks/runewood/break", 6)));
        this.add(SoundRegistry.RUNEWOOD_HIT, s -> definition(s).with(sounds("blocks/runewood/hit", 6)));

        this.add(SoundRegistry.RUNEWOOD_HANGING_SIGN_BREAK, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/break", 4)));
        this.add(SoundRegistry.RUNEWOOD_HANGING_SIGN_STEP, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/step", 4)));
        this.add(SoundRegistry.RUNEWOOD_HANGING_SIGN_PLACE, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/break", 4)));
        this.add(SoundRegistry.RUNEWOOD_HANGING_SIGN_HIT, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/step", 4)));


        this.add(SoundRegistry.RUNEWOOD_BUTTON_CLICK_OFF, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.RUNEWOOD_BUTTON_CLICK_ON, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.RUNEWOOD_PRESSURE_PLATE_CLICK_OFF, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.RUNEWOOD_PRESSURE_PLATE_CLICK_ON, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.RUNEWOOD_FENCE_GATE_CLOSE, s -> definition(s).with(sounds("blocks/runewood/fence_gate/toggle", 3)));
        this.add(SoundRegistry.RUNEWOOD_FENCE_GATE_OPEN, s -> definition(s).with(sounds("blocks/runewood/fence_gate/toggle", 3)));
        this.add(SoundRegistry.RUNEWOOD_DOOR_CLOSE, s -> definition(s).with(sounds("blocks/runewood/door/toggle", 3)));
        this.add(SoundRegistry.RUNEWOOD_DOOR_OPEN, s -> definition(s).with(sounds("blocks/runewood/door/toggle", 3)));
        this.add(SoundRegistry.RUNEWOOD_TRAPDOOR_CLOSE, s -> definition(s).with(sounds("blocks/runewood/trapdoor/toggle", 3)));
        this.add(SoundRegistry.RUNEWOOD_TRAPDOOR_OPEN, s -> definition(s).with(sounds("blocks/runewood/trapdoor/toggle", 3)));

        this.add(SoundRegistry.SOULWOOD_BREAK, s -> definition(s).with(sounds("blocks/runewood/break", 6)));
        this.add(SoundRegistry.SOULWOOD_STEP, s -> definition(s).with(sounds("blocks/runewood/hit", 6)));
        this.add(SoundRegistry.SOULWOOD_PLACE, s -> definition(s).with(sounds("blocks/runewood/break", 6)));
        this.add(SoundRegistry.SOULWOOD_HIT, s -> definition(s).with(sounds("blocks/runewood/hit", 6)));

        this.add(SoundRegistry.SOULWOOD_HANGING_SIGN_BREAK, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/break", 4)));
        this.add(SoundRegistry.SOULWOOD_HANGING_SIGN_STEP, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/step", 4)));
        this.add(SoundRegistry.SOULWOOD_HANGING_SIGN_PLACE, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/break", 4)));
        this.add(SoundRegistry.SOULWOOD_HANGING_SIGN_HIT, s -> definition(s).with(sounds("blocks/runewood/hanging_sign/step", 4)));


        this.add(SoundRegistry.SOULWOOD_BUTTON_CLICK_OFF, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.SOULWOOD_BUTTON_CLICK_ON, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.SOULWOOD_PRESSURE_PLATE_CLICK_OFF, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.SOULWOOD_PRESSURE_PLATE_CLICK_ON, s -> definition(s).with(sound("blocks/runewood/button/runewood_button")));
        this.add(SoundRegistry.SOULWOOD_FENCE_GATE_CLOSE, s -> definition(s).with(sounds("blocks/runewood/fence_gate/toggle", 3)));
        this.add(SoundRegistry.SOULWOOD_FENCE_GATE_OPEN, s -> definition(s).with(sounds("blocks/runewood/fence_gate/toggle", 3)));
        this.add(SoundRegistry.SOULWOOD_DOOR_CLOSE, s -> definition(s).with(sounds("blocks/runewood/door/toggle", 3)));
        this.add(SoundRegistry.SOULWOOD_DOOR_OPEN, s -> definition(s).with(sounds("blocks/runewood/door/toggle", 3)));
        this.add(SoundRegistry.SOULWOOD_TRAPDOOR_CLOSE, s -> definition(s).with(sounds("blocks/runewood/trapdoor/toggle", 3)));
        this.add(SoundRegistry.SOULWOOD_TRAPDOOR_OPEN, s -> definition(s).with(sounds("blocks/runewood/trapdoor/toggle", 3)));

        this.add(SoundRegistry.SPIRIT_DIODE_BREAK, s -> definition(s).with(sounds("blocks/spirit_diode/break", 4)));
        this.add(SoundRegistry.SPIRIT_DIODE_STEP, s -> definition(s).with(sounds("minecraft:block/copper_bulb/step", 6)));
        this.add(SoundRegistry.SPIRIT_DIODE_PLACE, s -> definition(s).with(sounds("blocks/spirit_diode/place", 4)));
        this.add(SoundRegistry.SPIRIT_DIODE_HIT, s -> definition(s).with(sounds("minecraft:block/copper_bulb/step", 6)));
        this.add(SoundRegistry.SPIRIT_DIODE_OPEN, s -> definition(s).with(sounds("blocks/spirit_diode/waveform_open", 4)));
        this.add(SoundRegistry.SPIRIT_DIODE_CLOSE, s -> definition(s).with(sounds("blocks/spirit_diode/waveform_close", 4)));

        this.add(SoundRegistry.WAVECHARGER_CHARGE, s -> definition(s).with(sound("blocks/spirit_diode/tick")));
        this.add(SoundRegistry.WAVEBANKER_STORE, s -> definition(s).with(sound("blocks/spirit_diode/tick")));
        this.add(SoundRegistry.WAVEBREAKER_STORE, s -> definition(s).with(sound("blocks/spirit_diode/tick")));
        this.add(SoundRegistry.WAVEBREAKER_RELEASE, s -> definition(s).with(sound("blocks/spirit_diode/tick")));
        this.add(SoundRegistry.WAVEMAKER_PULSE, s -> definition(s).with(sound("blocks/spirit_diode/tick")));

        this.add(SoundRegistry.ARCANE_ROCK_BREAK, s -> definition(s).with(sounds("blocks/arcane_rock/break", 6)));
        this.add(SoundRegistry.ARCANE_ROCK_STEP, s -> definition(s).with(sounds("blocks/arcane_rock/hit", 6)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.ARCANE_ROCK_PLACE, s -> definition(s).with(sounds("blocks/arcane_rock/break", 6)));
        this.add(SoundRegistry.ARCANE_ROCK_HIT, s -> definition(s).with(sounds("blocks/arcane_rock/hit", 6)).with(sounds("minecraft:block/basalt/step", 6)));

        this.add(SoundRegistry.ARCANE_ROCK_BRICKS_BREAK, s -> definition(s).with(sounds("blocks/arcane_rock/bricks/break", 6)));
        this.add(SoundRegistry.ARCANE_ROCK_BRICKS_STEP, s -> definition(s).with(sounds("blocks/arcane_rock/bricks/hit", 6)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.ARCANE_ROCK_BRICKS_PLACE, s -> definition(s).with(sounds("blocks/arcane_rock/bricks/break", 6)));
        this.add(SoundRegistry.ARCANE_ROCK_BRICKS_HIT, s -> definition(s).with(sounds("blocks/arcane_rock/bricks/hit", 6)).with(sounds("minecraft:block/basalt/step", 6)));

        this.add(SoundRegistry.SOULSTONE_BREAK, s -> definition(s).with(sounds("blocks/soulstone/break", 4)));
        this.add(SoundRegistry.SOULSTONE_STEP, s -> definition(s).with(sounds("blocks/soulstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.SOULSTONE_PLACE, s -> definition(s).with(sounds("blocks/soulstone/break", 4)));
        this.add(SoundRegistry.SOULSTONE_HIT, s -> definition(s).with(sounds("blocks/soulstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));

        this.add(SoundRegistry.DEEPSLATE_SOULSTONE_BREAK, s -> definition(s).with(sounds("blocks/soulstone/deepslate/break", 4)));
        this.add(SoundRegistry.DEEPSLATE_SOULSTONE_STEP, s -> definition(s).with(sounds("blocks/soulstone/deepslate/break", 4)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.DEEPSLATE_SOULSTONE_PLACE, s -> definition(s).with(sounds("blocks/soulstone/deepslate/place", 6)));
        this.add(SoundRegistry.DEEPSLATE_SOULSTONE_HIT, s -> definition(s).with(sounds("blocks/soulstone/deepslate/place", 4)).with(sounds("minecraft:block/basalt/step", 6)));


        this.add(SoundRegistry.BLAZING_QUARTZ_ORE_BREAK, s -> definition(s).with(sounds("blocks/blazing_quartz/break", 4)));
        this.add(SoundRegistry.BLAZING_QUARTZ_ORE_PLACE, s -> definition(s).with(sounds("blocks/blazing_quartz/break", 4)));


        this.add(SoundRegistry.BLAZING_QUARTZ_BLOCK_BREAK, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.BLAZING_QUARTZ_BLOCK_STEP, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.BLAZING_QUARTZ_BLOCK_PLACE, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.BLAZING_QUARTZ_BLOCK_HIT, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));

        this.add(SoundRegistry.BRILLIANCE_BLOCK_BREAK, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.BRILLIANCE_BLOCK_STEP, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.BRILLIANCE_BLOCK_PLACE, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.BRILLIANCE_BLOCK_HIT, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));


        this.add(SoundRegistry.CTHONIC_GOLD_BREAK, s -> definition(s).with(sounds("blocks/cthonic_gold/break", 4)));
        this.add(SoundRegistry.CTHONIC_GOLD_PLACE, s -> definition(s).with(sounds("blocks/cthonic_gold/break", 4)));


        this.add(SoundRegistry.ARCANE_CHARCOAL_BLOCK_BREAK, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.ARCANE_CHARCOAL_BLOCK_STEP, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));
        this.add(SoundRegistry.ARCANE_CHARCOAL_BLOCK_PLACE, s -> definition(s).with(sounds("blocks/gemstone/break", 4)));
        this.add(SoundRegistry.ARCANE_CHARCOAL_BLOCK_HIT, s -> definition(s).with(sounds("blocks/gemstone/hit", 4)).with(sounds("minecraft:block/basalt/step", 6)));

        this.add(SoundRegistry.HALLOWED_GOLD_BREAK, s -> definition(s).with(sounds("blocks/hallowed_gold/break", 4)));
        this.add(SoundRegistry.HALLOWED_GOLD_STEP, s -> definition(s).with(sounds("blocks/hallowed_gold/hit", 4)));
        this.add(SoundRegistry.HALLOWED_GOLD_PLACE, s -> definition(s).with(sounds("blocks/hallowed_gold/break", 4)));
        this.add(SoundRegistry.HALLOWED_GOLD_HIT, s -> definition(s).with(sounds("blocks/hallowed_gold/hit", 4)));

        this.add(SoundRegistry.SOUL_STAINED_STEEL_BREAK, s -> definition(s).with(sounds("blocks/soul_stained_steel/break", 4)));
        this.add(SoundRegistry.SOUL_STAINED_STEEL_STEP, s -> definition(s).with(sounds("blocks/soul_stained_steel/hit", 4)));
        this.add(SoundRegistry.SOUL_STAINED_STEEL_PLACE, s -> definition(s).with(sounds("blocks/soul_stained_steel/break", 4)));
        this.add(SoundRegistry.SOUL_STAINED_STEEL_HIT, s -> definition(s).with(sounds("blocks/soul_stained_steel/hit", 4)));


        this.add(SoundRegistry.MAJOR_BLIGHT_MOTIF, s -> definition(s).with(sounds("blocks/blight/blight_motif", 6)));
        this.add(SoundRegistry.MINOR_BLIGHT_MOTIF, s -> definition(s).with(sounds("blocks/blight/minor_blight_motif", 6)));



        this.add(SoundRegistry.CALCIFIED_BLIGHT_BREAK, s -> definition(s).with(sounds("blocks/calcified_blight/break", 4)));
        this.add(SoundRegistry.CALCIFIED_BLIGHT_STEP, s -> definition(s).with(sounds("blocks/calcified_blight/hit", 4)));
        this.add(SoundRegistry.CALCIFIED_BLIGHT_PLACE, s -> definition(s).with(sounds("blocks/calcified_blight/break", 4)));
        this.add(SoundRegistry.CALCIFIED_BLIGHT_HIT, s -> definition(s).with(sounds("blocks/calcified_blight/hit", 4)));

        this.add(SoundRegistry.ETHER_BREAK, s -> definition(s).with(sounds("blocks/ether/break", 4)));
        this.add(SoundRegistry.ETHER_PLACE, s -> definition(s).with(sounds("blocks/ether/break", 4)));


        this.add(SoundRegistry.RUNEWOOD_LEAVES_BREAK, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/break", 7)));
        this.add(SoundRegistry.RUNEWOOD_LEAVES_STEP, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/step", 5)));
        this.add(SoundRegistry.RUNEWOOD_LEAVES_PLACE, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/break", 7)));
        this.add(SoundRegistry.RUNEWOOD_LEAVES_HIT, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/step", 5)));

        this.add(SoundRegistry.SOULWOOD_LEAVES_BREAK, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/break", 7)));
        this.add(SoundRegistry.SOULWOOD_LEAVES_STEP, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/step", 5)));
        this.add(SoundRegistry.SOULWOOD_LEAVES_PLACE, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/break", 7)));
        this.add(SoundRegistry.SOULWOOD_LEAVES_HIT, s -> definition(s).with(sounds("minecraft:block/azalea_leaves/step", 5)));

        this.add(SoundRegistry.ARCANE_ELEGY, s -> definition(s).with(sound("arcane_elegy")));
        this.add(SoundRegistry.AESTHETICA, s -> definition(s).with(sound("aesthetica")));

    }

    protected SoundDefinition definition(SoundEvent soundEvent) {
        return SoundDefinition.definition().subtitle(malumSubtitle(soundEvent));
    }

    public void add(final Supplier<SoundEvent> soundEvent, final Function<SoundEvent, SoundDefinition> definition) {
        add(soundEvent, definition.apply(soundEvent.get()));
    }

    public static SoundDefinition.Sound sound(String name) {
        return sound(malumPath(name));
    }

    public SoundDefinition.Sound[] sounds(String name, int variants) {
        SoundDefinition.Sound[] sounds = new SoundDefinition.Sound[variants];
        for (int i = 0; i < variants; i++) {
            var resourceLocation = name.contains(":") ? ResourceLocation.parse(name + (i + 1)) : malumPath(name + (i + 1));
            sounds[i] = sound(resourceLocation);
        }
        return sounds;
    }

    public String malumSubtitle(SoundEvent soundEvent) {
        return malumSubtitle(soundEvent.getLocation());
    }

    public String malumSubtitle(ResourceLocation id) {
        return MalumMod.MALUM + ".subtitle." + id.getPath();
    }
}
