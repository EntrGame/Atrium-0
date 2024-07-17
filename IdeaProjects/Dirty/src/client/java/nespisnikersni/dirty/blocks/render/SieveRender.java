package nespisnikersni.dirty.blocks.render;

import nespisnikersni.dirty.blocks.entities.sieve.SieveEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class SieveRender implements BlockEntityRenderer<SieveEntity> {
    public SieveRender(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(SieveEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(entity.getRenderStack().getItem()== Items.AIR){
            return;
        }
        BlockRenderManager renderManager = MinecraftClient.getInstance().getBlockRenderManager();
        BlockState blockState = ((BlockItem) entity.getRenderStack().getItem()).getBlock().getDefaultState();
        int sizeY = 10-entity.getProgress();
        matrices.push();
        matrices.translate(0.1, 0.7, 0.1);
        matrices.scale(0.8f, (float) sizeY/10*0.8f, 0.8f);
//        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
        renderManager.renderBlock(blockState, entity.getPos().add(0, 2, 0), entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), false, Random.create());
        matrices.pop();
    }
}
