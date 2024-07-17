package nespisnikersni.dirty;

import nespisnikersni.dirty.blocks.DirtyBlocks;
import nespisnikersni.dirty.blocks.entities.DirtyBlockEntities;
import nespisnikersni.dirty.blocks.render.ProcessingTableRender;
import nespisnikersni.dirty.entities.ModEntities;
import nespisnikersni.dirty.entity.guard.GuardModel;
import nespisnikersni.dirty.entity.guard.GuardRender;
import nespisnikersni.dirty.entity.mud.Layers;
import nespisnikersni.dirty.entity.mud.MudModel;
import nespisnikersni.dirty.entity.mud.MudRender;
import nespisnikersni.dirty.screen.DirtRecyclerScreen;
import nespisnikersni.dirty.screenhandlers.DirtyScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class DirtyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.PIECE_OF_DIRT_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.EXPLOSIVE_DIRT_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.GUARD_ENTITY_TYPE, GuardRender::new);
        EntityRendererRegistry.register(ModEntities.MUD_ENTITY_TYPE, MudRender::new);
        EntityModelLayerRegistry.registerModelLayer(Layers.MUD, MudModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(Layers.GUARD, GuardModel::getTexturedModelData);
        DirtyScreenHandlers.register();
        HandledScreens.register(DirtyScreenHandlers.DIRT_RECYCLER_SCREEN_HANDLER, DirtRecyclerScreen::new);
        BlockEntityRendererFactories.register(DirtyBlockEntities.PROCESSING_TABLE_ENTITY, ProcessingTableRender::new);
        BlockRenderLayerMap.INSTANCE.putBlock(DirtyBlocks.PROCESSING_TABLE, RenderLayer.getTranslucent());
    }
}
