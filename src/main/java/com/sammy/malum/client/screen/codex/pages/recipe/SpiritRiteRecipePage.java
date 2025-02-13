package com.sammy.malum.client.screen.codex.pages.recipe;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.helpers.*;

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
        final Minecraft minecraft = Minecraft.getInstance();
        var rand = minecraft.level.random;
        PoseStack poseStack = guiGraphics.pose();
        if (!isRepeat) {
            if (ScreenParticleHandler.canSpawnParticles) {
                RITE_PARTICLES.tick();
            }
            ScreenParticleHandler.renderParticles(RITE_PARTICLES);
        }

        int riteStartX = left + 63;
        int riteStartY = top + 118;
        for (int i = 0; i < spirits.size(); i++) {
            final int y = riteStartY - 20 * i;
            MalumSpiritType spiritType = spirits.get(i);
            ResourceLocation spiritTexture = spiritType.getTotemGlowTexture();
            ItemStack stack = spirits.get(i).getSpiritShard().getDefaultInstance();
            renderRiteIcon(spiritTexture, spiritType, poseStack, isCorrupted(), 0.25f, riteStartX, y);
            if (screen.isHovering(mouseX, mouseY, riteStartX, y, 16, 16)) {
                guiGraphics.renderComponentTooltip(minecraft.font, Screen.getTooltipFromItem(minecraft, stack), mouseX, mouseY);
            }
            if (ScreenParticleHandler.canSpawnParticles && minecraft.level.getGameTime() % 6L == 0) {
                final int x = riteStartX + 8;
                float xOffset = 25;
                float yMotion = RandomHelper.randomBetween(rand, -0.05f, -0.3f);
                int lifetime = RandomHelper.randomBetween(rand, 60, 120);
                ScreenParticleBuilder.create(ScreenParticleRegistry.LIGHT_SPEC_SMALL, RITE_PARTICLES)
                        .setTransparencyData(GenericParticleData.create(0.04f, 0.4f, 0f).setEasing(Easing.CUBIC_OUT, Easing.SINE_IN_OUT).build())
                        .setSpinData(SpinParticleData.createRandomDirection(rand, RandomHelper.randomBetween(rand, 0.1f, 0.2f), 0).randomSpinOffset(rand).setEasing(Easing.SINE_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(rand, 0.8f, 2.4f), 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setColorData(spiritType.createColorData().setCoefficient(0.25f).build())
                        .setLifetime(lifetime)
                        .setMotion(0, yMotion)
                        .spawn(x - xOffset, y+8+4*i)
                        .spawn(x + xOffset, y+8+4*i);
            }
        }
    }
    public boolean isCorrupted() {
        return bookEntry.identifier.contains("corrupt");
    }
}