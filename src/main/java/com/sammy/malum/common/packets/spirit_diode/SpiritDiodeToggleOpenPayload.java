package com.sammy.malum.common.packets.spirit_diode;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public class SpiritDiodeToggleOpenPayload extends OneSidedPayloadData {
    private final BlockPos pos;
    private final boolean isOpen;

    public SpiritDiodeToggleOpenPayload(BlockPos pos, boolean isOpen) {
        this.pos = pos;
        this.isOpen = isOpen;
    }

    public SpiritDiodeToggleOpenPayload(FriendlyByteBuf buf) {
        this.pos = BlockPos.STREAM_CODEC.decode(buf);
        this.isOpen = buf.readBoolean();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        Level level = iPayloadContext.player().level();
        if (level.getBlockEntity(pos) instanceof SpiritDiodeBlockEntity spiritDiode) {
            spiritDiode.toggleTime = level.getGameTime();
        }
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        BlockPos.STREAM_CODEC.encode(friendlyByteBuf, pos);
        friendlyByteBuf.writeBoolean(this.isOpen);
    }
}