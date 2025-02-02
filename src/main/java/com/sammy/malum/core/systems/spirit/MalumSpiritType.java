package com.sammy.malum.core.systems.spirit;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.mana_mote.*;
import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import io.netty.buffer.*;
import net.minecraft.*;
import net.minecraft.network.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.awt.*;
import java.util.function.*;

public class MalumSpiritType {

    public static final Codec<MalumSpiritType> CODEC = Codec.STRING.comapFlatMap(s -> {
        final MalumSpiritType spiritType = SpiritTypeRegistry.SPIRITS.get(s);
        if (spiritType == null) {
            throw new JsonParseException("No Such Spirit Type: " + s);
        }
        return DataResult.success(spiritType);
    }, MalumSpiritType::getIdentifier);

    public static StreamCodec<ByteBuf, MalumSpiritType> STREAM_CODEC = ByteBufCodecs.fromCodec(MalumSpiritType.CODEC);

    public static SpiritTypeBuilder create(String identifier, SpiritVisualMotif visualMotif, Supplier<SpiritShardItem> spiritShard) {
        return new SpiritTypeBuilder(identifier, visualMotif, spiritShard);
    }

    protected final String identifier;
    protected final Supplier<SpiritShardItem> spiritShard;

    private final SpiritVisualMotif visualMotif;

    private final Color itemColor;

    protected Rarity itemRarity;
    protected Component spiritItemDescription;

    public MalumSpiritType(String identifier, SpiritVisualMotif visualMotif, Supplier<SpiritShardItem> spiritShard, Color itemColor) {
        this.identifier = identifier;
        this.spiritShard = spiritShard;
        this.visualMotif = visualMotif;
        this.itemColor = itemColor;
    }

    public static MalumSpiritType getSpiritType(String spirit) {
        return SpiritTypeRegistry.SPIRITS.getOrDefault(spirit, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    public String getIdentifier() {
        return identifier;
    }

    public SpiritShardItem getSpiritShard() {
        return spiritShard.get();
    }

    public boolean test(ItemStack stack) {
        return stack.getItem().equals(getSpiritShard());
    }

    public float getAlphaMultiplier() {
        return visualMotif.getAlphaMultiplier();
    }

    public Color getPrimaryColor() {
        return visualMotif.getPrimaryColor();
    }

    public Color getSecondaryColor() {
        return visualMotif.getSecondaryColor();
    }

    public float getColorCoefficient() {
        return visualMotif.getColorCoefficient();
    }

    public ColorParticleDataBuilder createColorData() {
        return createColorData(1f);
    }

    public ColorParticleDataBuilder createColorData(float coefficientMultiplier) {
        return visualMotif.createColorData(coefficientMultiplier);
    }

    public Color getItemColor() {
        return itemColor;
    }

    public TextColor getTextColor(boolean isTooltip) {
        Color color = isTooltip ? ColorHelper.darker(getPrimaryColor(), 1, 0.75f) : ColorHelper.brighter(getPrimaryColor(), 1, 0.85f);
        return TextColor.fromRgb(color.getRGB());
    }

    public Rarity getItemRarity() {
        if (itemRarity == null) {
            TextColor textColor = getTextColor(false);
            itemRarity = Rarity.UNCOMMON;
//            itemRarity = Rarity.create("malum$" + identifier, (style) -> style.withColor(textColor));
        }
        return itemRarity;
    }

    public Component getSpiritShardFlavourTextComponent() {
        if (spiritItemDescription == null) {
            spiritItemDescription = Component.translatable(getSpiritFlavourText()).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(getTextColor(true)));
        }
        return spiritItemDescription;
    }

    public String getSpiritFlavourText() {
        return "malum.spirit.flavour." + identifier;
    }

    public Component getSpiritJarCounterComponent(int count) {
        return Component.literal(" " + count + " ").append(Component.translatable(getSpiritDescription())).withStyle(Style.EMPTY.withColor(getPrimaryColor().getRGB()));
    }

    public String getSpiritDescription() {
        return "malum.spirit.description." + identifier;
    }

    public ResourceLocation getTotemGlowTexture() {
        return MalumMod.malumPath("textures/vfx/totem_poles/" + identifier + "_glow.png");
    }

    public BlockState getTotemPoleBlockState(boolean isCorrupt, BlockHitResult hit) {
        Block base = isCorrupt ? BlockRegistry.SOULWOOD_TOTEM_POLE.get() : BlockRegistry.RUNEWOOD_TOTEM_POLE.get();
        return base.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()).setValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY, identifier);
    }

    public BlockState getSpiritMoteBlockState() {
        return BlockRegistry.SPIRIT_MOTE.get().defaultBlockState().setValue(ManaMoteBlock.SPIRIT_TYPE, identifier);
    }
}
