package com.sammy.malum.client;



import com.sammy.malum.MalumMod;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MalumModelLoaderPlugin implements ModelLoadingPlugin {
    private static final ModelResourceLocation CREATIVE_BASE_MODEL = createModel("creative_scythe");
    private static final ResourceLocation CREATIVE_GUI_MODEL = MalumMod.malumPath("item/creative_scythe_gui");

    private static final ModelResourceLocation CRUDE_BASE_MODEL = createModel("crude_scythe");
    private static final ResourceLocation CRUDE_GUI_MODEL = MalumMod.malumPath("item/crude_scythe_gui");

    private static final ModelResourceLocation SOUL_BASE_MODEL = createModel("soul_stained_steel_scythe");
    private static final ResourceLocation SOUL_GUI_MODEL = MalumMod.malumPath("item/soul_stained_steel_scythe_gui");

    private static final ModelResourceLocation WORLD_BASE_MODEL = createModel("weight_of_worlds");
    private static final ResourceLocation WORLD_GUI_MODEL = MalumMod.malumPath("item/weight_of_worlds_gui");

    private static final ModelResourceLocation EDGE_BASE_MODEL = createModel("edge_of_deliverance");
    private static final ResourceLocation EDGE_GUI_MODEL = MalumMod.malumPath("item/edge_of_deliverance_gui");

    public MalumModelLoaderPlugin() {
    }

    @Override
    public void onInitializeModelLoader(Context pluginContext) {

        pluginContext.addModels(CREATIVE_GUI_MODEL);
        pluginContext.addModels(CRUDE_GUI_MODEL);
        pluginContext.addModels(SOUL_GUI_MODEL);
        pluginContext.addModels(WORLD_GUI_MODEL);
        pluginContext.addModels(EDGE_GUI_MODEL);

        pluginContext.modifyModelAfterBake().register((original, context) -> {
            if (CREATIVE_BASE_MODEL.equals(context.topLevelId())) {
                BakedModel guiModel = context.baker().bake(CREATIVE_GUI_MODEL, context.settings());
                return new MalumBakedModel(original, guiModel);
            }
            if (CRUDE_BASE_MODEL.equals(context.topLevelId())) {
                BakedModel guiModel = context.baker().bake(CRUDE_GUI_MODEL, context.settings());
                return new MalumBakedModel(original, guiModel);
            }
            if (SOUL_BASE_MODEL.equals(context.topLevelId())) {
                BakedModel guiModel = context.baker().bake(SOUL_GUI_MODEL, context.settings());
                return new MalumBakedModel(original, guiModel);
            }
            if (WORLD_BASE_MODEL.equals(context.topLevelId())) {
                BakedModel guiModel = context.baker().bake(WORLD_GUI_MODEL, context.settings());
                return new MalumBakedModel(original, guiModel);
            }
            if (EDGE_BASE_MODEL.equals(context.topLevelId())) {
                BakedModel guiModel = context.baker().bake(EDGE_GUI_MODEL, context.settings());
                return new MalumBakedModel(original, guiModel);
            }
            return original;
        });
    }

    private static class MalumBakedModel extends ForwardingBakedModel {
        private static final Set<ItemDisplayContext> ITEM_GUI_CONTEXTS = EnumSet.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED);

        private final BakedModel guiModel;

        public MalumBakedModel(BakedModel heldModel, BakedModel guiModel) {
            this.wrapped = heldModel;
            this.guiModel = guiModel;
        }

        @Override
        public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
            if (ITEM_GUI_CONTEXTS.contains(context.itemTransformationMode())) {
                guiModel.emitItemQuads(stack, randomSupplier, context);
                return;
            }
            super.emitItemQuads(stack, randomSupplier, context);
        }

        @Override
        public boolean isVanillaAdapter() {
            return false;
        }
    }

    private static ModelResourceLocation createModel(String baseName) {
        return new ModelResourceLocation(MalumMod.malumPath(baseName), "inventory");
    }
}
