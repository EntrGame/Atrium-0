package nespisnikersni.dirty.entity.guard;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.entities.mobs.guard.GuardEntity;
import nespisnikersni.dirty.entity.mud.Layers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GuardRender extends MobEntityRenderer<GuardEntity, GuardModel<GuardEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dirty.MOD_ID,"textures/entity/guard.png");
    public GuardRender(EntityRendererFactory.Context context) {
        super(context, new GuardModel<>(context.getPart(Layers.GUARD)),0.7f);
    }

    @Override
    public Identifier getTexture(GuardEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GuardEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
