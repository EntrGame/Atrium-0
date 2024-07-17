package nespisnikersni.dirty.entity.mud;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.entities.mobs.mud.MudEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MudRender extends MobEntityRenderer<MudEntity,MudModel<MudEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dirty.MOD_ID,"textures/entity/mud.png");
    public MudRender(EntityRendererFactory.Context context) {
        super(context, new MudModel<>(context.getPart(Layers.MUD)),0.7f);
    }

    @Override
    public Identifier getTexture(MudEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(MudEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f,0.5f,0.5f);
        }else{
            matrixStack.scale(1f,1,1f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
