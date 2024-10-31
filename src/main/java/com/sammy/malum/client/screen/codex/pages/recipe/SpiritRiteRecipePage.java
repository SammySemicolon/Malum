package com.sammy.malum.client.screen.codex.pages.recipe;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.helpers.*;

import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.util.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritRiteRecipePage extends BookPage {

    private static final ScreenParticleHolder RITE_PARTICLES = new ScreenParticleHolder();

    private final TotemicRiteType riteType;

    public SpiritRiteRecipePage(TotemicRiteType riteType) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_rite_recipe_page.png"));
        this.riteType = riteType;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        final List<MalumSpiritType> spirits = riteType.spirits;
        var rand = Minecraft.getInstance().level.random;
        PoseStack poseStack = guiGraphics.pose();
        if (!isRepeat) {
            if (ScreenParticleHandler.canSpawnParticles) {
                RITE_PARTICLES.tick();
            }
            ScreenParticleHandler.renderParticles(RITE_PARTICLES);
        }

        int riteStartX = left + 63;
        int riteStartY = top + 127;
        for (int i = 0; i < spirits.size(); i++) {
            final int y = riteStartY - 20 * i;
            MalumSpiritType spiritType = spirits.get(i);
            ResourceLocation spiritTexture = spiritType.getTotemGlowTexture();
            ItemStack stack = spirits.get(i).getSpiritShard().getDefaultInstance();
            renderRiteIcon(spiritTexture, spiritType, poseStack, isCorrupted(), 0.25f, riteStartX, y);
            if (screen.isHovering(mouseX, mouseY, riteStartX, y, 16, 16)) {
                guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, Screen.getTooltipFromItem(Minecraft.getInstance(), stack), mouseX, mouseY);
            }
            if (ScreenParticleHandler.canSpawnParticles) {
                final int x = riteStartX + 8;
                float xOffset = 25;
                float yMotion = RandomHelper.randomBetween(rand, 0.2f, 0.4f) * (rand.nextBoolean() ? -1 : 1);
                int lifetime = RandomHelper.randomBetween(rand, 40, 80);
                float scale = RandomHelper.randomBetween(rand, 0.2f, 0.6f);
                float spin = RandomHelper.randomBetween(rand, 0.2f, 0.4f);
                ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, RITE_PARTICLES)
                        .setTransparencyData(GenericParticleData.create(0.04f, 0.4f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.create(spin).build())
                        .setScaleData(GenericParticleData.create(scale, 0).build())
                        .setColorData(spiritType.createColorData().setCoefficient(0.25f).build())
                        .setLifetime(lifetime)
                        .setMotion(0, yMotion)
                        .spawn(x - xOffset, y)
                        .spawn(x + xOffset, y);
            }
        }
    }
    public boolean isCorrupted() {
        return bookEntry.identifier.contains("corrupt");
    }
}