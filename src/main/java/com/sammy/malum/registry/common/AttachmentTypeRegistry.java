package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.common.capabilities.soul_data.*;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import java.util.function.*;

public class AttachmentTypeRegistry {

    public static final AttachmentType<LivingSoulData> LIVING_SOUL_INFO =
            AttachmentRegistry.<LivingSoulData>builder()
                    .persistent(LivingSoulData.CODEC)
                    .initializer(LivingSoulData::new)
                    .buildAndRegister(MalumMod.malumPath("living_soul_info"));


    public static final AttachmentType<ProjectileSoulData> PROJECTILE_SOUL_INFO =
            AttachmentRegistry.<ProjectileSoulData>builder()
                    .persistent(ProjectileSoulData.CODEC)
                    .initializer(ProjectileSoulData::new)
                    .buildAndRegister(MalumMod.malumPath("projectile_soul_info"));

    public static final AttachmentType<CachedSpiritDropsData> CACHED_SPIRIT_DROPS =
            AttachmentRegistry.<CachedSpiritDropsData>builder()
                    .persistent(CachedSpiritDropsData.CODEC)
                    .initializer(CachedSpiritDropsData::new)
                    .buildAndRegister(MalumMod.malumPath("cached_spirit_drops"));

    public static final AttachmentType<ProgressionData> PROGRESSION_DATA =
            AttachmentRegistry.<ProgressionData>builder()
                    .persistent(ProgressionData.CODEC)
                    .initializer(ProgressionData::new)
                    .buildAndRegister(MalumMod.malumPath("progression_data"));

    public static final AttachmentType<CurioData> CURIO_DATA =
            AttachmentRegistry.<CurioData>builder()
                    .persistent(CurioData.CODEC)
                    .initializer(CurioData::new)
                    .buildAndRegister(MalumMod.malumPath("curio_data"));

    public static final AttachmentType<SoulWardData> SOUL_WARD =
            AttachmentRegistry.<SoulWardData>builder()
                    .persistent(SoulWardData.CODEC)
                    .initializer(SoulWardData::new)
                    .buildAndRegister(MalumMod.malumPath("soul_ward"));

    public static final AttachmentType<StaffAbilityData> RESERVE_STAFF_CHARGES =
            AttachmentRegistry.<StaffAbilityData>builder()
                    .persistent(StaffAbilityData.CODEC)
                    .initializer(StaffAbilityData::new)
                    .buildAndRegister(MalumMod.malumPath("reserve_staff_charges"));

    public static final AttachmentType<VoidInfluenceData> VOID_INFLUENCE =
            AttachmentRegistry.<VoidInfluenceData>builder()
                    .persistent(VoidInfluenceData.CODEC)
                    .initializer(VoidInfluenceData::new)
                    .buildAndRegister(MalumMod.malumPath("void_influence"));


    public static final AttachmentType<MalignantInfluenceData> MALIGNANT_INFLUENCE =
            AttachmentRegistry.<MalignantInfluenceData>builder()
                    .persistent(MalignantInfluenceData.CODEC)
                    .initializer(MalignantInfluenceData::new)
                    .buildAndRegister(MalumMod.malumPath("malignant_influence"));


    public static void register(){

    }

}
