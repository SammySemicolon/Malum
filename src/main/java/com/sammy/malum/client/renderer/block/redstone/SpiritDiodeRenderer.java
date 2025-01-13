package com.sammy.malum.client.renderer.block.redstone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlock;
import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlockEntity;
import com.sammy.malum.core.systems.item.HeldItemTracker;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.LodestoneBufferWrapper;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.cube.CubeVertexData;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import java.awt.*;


public abstract class SpiritDiodeRenderer<T extends SpiritDiodeBlockEntity> implements BlockEntityRenderer<T> {

    private static final MultiBufferSource TEXT = new LodestoneBufferWrapper(LodestoneRenderTypes.ADDITIVE_TEXT, RenderHandler.DELAYED_RENDER.getTarget());

    protected final RenderTypeToken output;
    protected final MutableComponent text;
    protected final MutableComponent outlineText;
    protected static final Color COLOR = new Color(170, 15, 1);

    public static final HeldItemTracker CLAW_TRACKER = new HeldItemTracker(p -> p.is(ItemTagRegistry.IS_REDSTONE_TOOL));

    public SpiritDiodeRenderer(BlockEntityRendererProvider.Context context, ResourceLocation tokenTexture, String langKey) {
        this.output = RenderTypeToken.createToken(tokenTexture);

        text = Component.translatable(langKey).withStyle(ChatFormatting.GOLD);
        outlineText = Component.translatable(langKey).withStyle(ChatFormatting.RED);
    }

    public abstract float getGlowDelta(T blockEntityIn, float delta);
    @Override
    public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntityIn.visualTransitionTime != 0) {
            float timing = (blockEntityIn.getLevel().getGameTime() - blockEntityIn.visualStartTime) / (float) blockEntityIn.visualTransitionTime;
            float delta = Mth.clampedLerp(blockEntityIn.visualTransitionStart, blockEntityIn.visualTransitionEnd, timing);
            float pct = Mth.clamp(getGlowDelta(blockEntityIn, delta), 0, 1);
            if (pct > 0) {
                float alpha = Easing.CUBIC_OUT.clamped(pct, 0, 1) * 0.8f;
                float glowAlpha = Easing.SINE_OUT.clamped(pct, 0, 1) * 0.4f;
                var cubeVertexData = CubeVertexData.makeCubePositions(1.002f);
                var builder = VFXBuilders.createWorld()
                        .setColor(COLOR);
                poseStack.pushPose();
                poseStack.translate(0.5f, 0.5f, 0.5f);
                BlockState state = blockEntityIn.getBlockState();
                for (int i = 0; i < 4; i++) {
                    var direction = Direction.from2DDataValue(i);
                    var token = getTokenForSide(state, direction);
                    var transparent = LodestoneRenderTypes.TRANSPARENT_TEXTURE.apply(token);
                    var additive = LodestoneRenderTypes.ADDITIVE_TEXTURE.apply(token);
                    builder.setAlpha(alpha).setRenderType(transparent).drawCubeSide(poseStack, cubeVertexData, direction);
                    builder.setAlpha(glowAlpha).setRenderType(additive).drawCubeSide(poseStack, cubeVertexData, direction);
                }
                poseStack.popPose();
            }
        }

        if (CLAW_TRACKER.isVisible()) {
            Minecraft minecraft = Minecraft.getInstance();
            var font = minecraft.font;
            float timeDelta = Mth.clamp(((float) minecraft.level.getGameTime() - blockEntityIn.toggleTime) / 20f, 0, 1);
            float heldDelta = Easing.SINE_IN_OUT.clamped(CLAW_TRACKER.getDelta(partialTicks), 0, 1);
            if (!blockEntityIn.getBlockState().getValue(SpiritDiodeBlock.OPEN)) {
                timeDelta = 1 - timeDelta;
            }
            float textDelta = timeDelta * heldDelta;
            float scale = 0.016F - (1 - textDelta) * 0.004f;
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.75f, 0.55f);
            poseStack.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
            poseStack.scale(scale, -scale, -scale);
            MutableComponent text = Component.translatable(this.text.getString(), blockEntityIn.delay).withStyle(ChatFormatting.RED);
            MutableComponent outlineText = Component.translatable(this.outlineText.getString(), blockEntityIn.delay).withStyle(ChatFormatting.DARK_RED);

            float f = (-font.width(text) / 2f);
            float xPos = 0 + f;
            Matrix4f pose = poseStack.last().pose();
            float alpha = 0.38f * textDelta; //We're rendering in additive, so whether we change alpha or the color it doesn't really matter.
            //Minecraft for whatever reason treats alpha values between 0-3 as 255 when rendering text, so we're going to modify color instead of alpha
            int color = ColorHelper.getColor(1, 1, 1, alpha);
            if (alpha > 0.02f) {
                renderText(text, xPos, 0, color, pose);
            }
            alpha = 0.18f * textDelta;
            if (alpha > 0.02f) {
                color = ColorHelper.getColor(1, 1, 1, alpha);
                renderText(text, xPos - 0.5f, 0, color, pose);
                renderText(text, xPos - 0.5f, 0, color, pose);
                renderText(text, xPos, 0.5f, color, pose);
                renderText(text, xPos, -0.5f, color, pose);
            }
            alpha = 0.12f * textDelta;
            if (alpha > 0.02f) {
                color = ColorHelper.getColor(1, 1, 1, alpha);
                renderText(text, xPos - 1, 0, color, pose);
                renderText(outlineText, xPos + 1, 0, color, pose);
                renderText(outlineText, xPos, 1, color, pose);
                renderText(text, xPos, -1, color, pose);
                renderText(outlineText, xPos - 0.5f, -0.5f, color, pose);
                renderText(text, xPos - 0.5f, 0.5f, color, pose);
                renderText(outlineText, xPos + 0.5f, 0.5f, color, pose);
                renderText(text, xPos + 0.5f, -0.5f, color, pose);
            }
            poseStack.popPose();
        }
    }

    public static void renderText(MutableComponent component, float xPos, float yPos, int color, Matrix4f pose) {
        var font = Minecraft.getInstance().font;
        font.drawInBatch(component, xPos, yPos, color, false, pose, TEXT, Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT);
    }

    public RenderTypeToken getTokenForSide(BlockState state, Direction direction) {
        Direction facing = state.getValue(SpiritDiodeBlock.FACING);
        if (direction.equals(facing)) {
            return MalumRenderTypeTokens.DIODE_INPUT;
        }
        if (direction.equals(facing.getOpposite())) {
            return output;
        }
        return MalumRenderTypeTokens.DIODE_LOCKED;
    }
}
