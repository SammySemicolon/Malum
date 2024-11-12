package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import team.lodestar.lodestone.systems.config.LodestoneConfig;

import static com.sammy.malum.MalumMod.MALUM;

public class CommonConfig extends LodestoneConfig {

    public static ConfigValueHolder<Boolean> AWARD_CODEX_ON_KILL = new ConfigValueHolder<>(MALUM, "common/codex", (builder ->
            builder.comment("If set to true, the first undead enemy a player slays will drop the encyclopedia arcana.")
                    .define("enableCodexDrop", true)));

    public static ConfigValueHolder<Boolean> NO_FANCY_SPIRITS = new ConfigValueHolder<>(MALUM, "common/spirit", (builder ->
            builder.comment("If set to true, any spirits dropped will simply take the form of an item.")
                    .define("noFancySpirits", false)));

    public static ConfigValueHolder<Boolean> SOULLESS_SPAWNERS = new ConfigValueHolder<>(MALUM, "common/spirit/spawner", (builder ->
            builder.comment("If set to true, mob spawners will create soulless mobs instead.")
                    .define("lameSpawners", false)));

    public static ConfigValueHolder<Boolean> USE_DEFAULT_SPIRIT_VALUES = new ConfigValueHolder<>(MALUM, "common/spirit/defaults", (builder ->
            builder.comment("Whether entities without spirit jsons will use the default spirit data for their category.")
                    .define("defaultSpiritValues", true)));

    public static ConfigValueHolder<Double> SOUL_WARD_PHYSICAL = new ConfigValueHolder<>(MALUM, "common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Multiplier for physical damage taken while soul ward is active.")
                    .defineInRange("soulWardPhysical", 0.7f, 0, 1)));
    public static ConfigValueHolder<Double> SOUL_WARD_MAGIC = new ConfigValueHolder<>(MALUM, "common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Multiplier for magic damage taken while soul ward is active.")
                    .defineInRange("soulWardMagic", 0.1f, 0, 1)));
    public static ConfigValueHolder<Integer> SOUL_WARD_RATE = new ConfigValueHolder<>(MALUM, "common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Base time in ticks it takes for one point of soul ward to recover.")
                    .define("soulWardRate", 100)));

    public static ConfigValueHolder<Boolean> IRONS_SPELLBOOKS_SPIRIT_DAMAGE = new ConfigValueHolder<>(MALUM, "common/compat/irons_spellbooks", (builder ->
            builder.comment("Should Iron's Spellbooks' magic damage count as Soul Damage?")
                    .define("ironsSpellbooksPlayerSpiritDrops", true)));

    public static ConfigValueHolder<Boolean> IRONS_SPELLBOOKS_NON_PLAYER_SPIRIT_DAMAGE = new ConfigValueHolder<>(MALUM, "common/compat/irons_spellbooks", (builder ->
            builder.comment("Should Iron's Spellbooks' magic damage when dealt by non-players count as Soul Damage")
                    .define("ironsSpellbooksNonPlayerSpiritDrops", true)));



    public CommonConfig(ForgeConfigSpec.Builder builder) {
        super(MALUM, "common", builder);
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
