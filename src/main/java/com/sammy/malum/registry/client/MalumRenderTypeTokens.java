package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import static com.sammy.malum.MalumMod.*;
import static team.lodestar.lodestone.LodestoneLib.*;

public class MalumRenderTypeTokens {

    public static final RenderTypeToken CONCENTRATED_TRAIL = RenderTypeToken.createToken(malumPath("textures/vfx/concentrated_trail.png"));
    public static final RenderTypeToken TWINKLE = RenderTypeToken.createToken(lodestonePath("textures/particle/twinkle.png"));
    public static final RenderTypeToken STAR = RenderTypeToken.createToken(malumPath("textures/particle/star.png"));
    public static final RenderTypeToken AREA_COVERAGE = RenderTypeToken.createToken(malumPath("textures/vfx/area_coverage.png"));

    public static final RenderTypeToken MOTE_OF_MANA = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/spirit_mote.png"));

    public static final RenderTypeToken INCOMPLETE_RITUAL = RenderTypeToken.createToken(malumPath("textures/vfx/ritual/incomplete_ritual.png"));
    public static final RenderTypeToken RITUAL_SILHOUETTE = RenderTypeToken.createToken(malumPath("textures/vfx/ritual/silhouette.png"));

    
    public static final RenderTypeToken VOID_VIGNETTE = RenderTypeToken.createToken(malumPath("textures/block/weeping_well/primordial_soup_vignette.png"));
    public static final RenderTypeToken VOID_NOISE = RenderTypeToken.createToken(malumPath("textures/vfx/void_noise.png"));

    public static final RenderTypeToken DEBUG_GIZMO = RenderTypeToken.createToken(malumPath("textures/particle/funky_star.png"));

}
