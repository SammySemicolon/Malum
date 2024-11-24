package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

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
    }

    protected SoundDefinition definition(SoundEvent soundEvent) {
        return SoundDefinition.definition().subtitle(malumSubtitle(soundEvent));
    }

    public void add(final Supplier<SoundEvent> soundEvent, final Function<SoundEvent, SoundDefinition> definition) {
        add(soundEvent, definition.apply(soundEvent.get()));
    }

    public SoundDefinition.Sound[] sounds(String name, int variants) {
        SoundDefinition.Sound[] sounds = new SoundDefinition.Sound[variants];
        for (int i = 0; i < variants; i++) {
            sounds[i] = sound(malumPath(name + i));
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
