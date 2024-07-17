package nespisnikersni.dirty.blocks.render;

import nespisnikersni.dirty.blocks.entities.processing_table.ProcessingTableEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ProcessingTableRender implements BlockEntityRenderer<ProcessingTableEntity> {
    public ProcessingTableRender(BlockEntityRendererFactory.Context context){

    }
    @Override
    public void render(ProcessingTableEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack itemStack = entity.getRenderStack();
        matrices.push();
        matrices.translate(0.5,0.94,0.5);
        matrices.scale(0.35f,0.35f,0.35f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
        itemRenderer.renderItem(itemStack, ModelTransformationMode.GUI,getLight(entity.getWorld(),entity.getPos()), OverlayTexture.DEFAULT_UV,matrices,vertexConsumers,entity.getWorld(),1);
        matrices.pop();
    }
    private int getLight(World world, BlockPos pos){
        int blight = world.getLightLevel(LightType.BLOCK,pos.add(0,1,0));
        int slight = world.getLightLevel(LightType.SKY,pos.add(0,1,0));
        return LightmapTextureManager.pack(blight,slight);
    }
}
