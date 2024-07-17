package nespisnikersni.dirty.entity.mud;

import nespisnikersni.dirty.entities.mobs.mud.MudEntity;
import nespisnikersni.dirty.entity.animations.MudAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class MudModel<T extends MudEntity> extends SinglePartEntityModel<T> {
	private final ModelPart muddog;
	private final ModelPart head;
	public MudModel(ModelPart root) {
		this.muddog = root.getChild("muddog");
		this.head = muddog.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData muddog = modelPartData.addChild("muddog", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData foot4 = muddog.addChild("foot4", ModelPartBuilder.create(), ModelTransform.pivot(-7.0F, -6.0F, 0.0F));

		ModelPartData cube_r1 = foot4.addChild("cube_r1", ModelPartBuilder.create().uv(0, 26).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, 6.0F, -6.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData foot3 = muddog.addChild("foot3", ModelPartBuilder.create(), ModelTransform.pivot(-7.0F, -6.0F, 2.0F));

		ModelPartData cube_r2 = foot3.addChild("cube_r2", ModelPartBuilder.create().uv(0, 26).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, 1.0F, 4.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData foot2 = muddog.addChild("foot2", ModelPartBuilder.create(), ModelTransform.pivot(6.0F, -6.0F, -2.0F));

		ModelPartData cube_r3 = foot2.addChild("cube_r3", ModelPartBuilder.create().uv(0, 26).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 6.0F, -4.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData foot1 = muddog.addChild("foot1", ModelPartBuilder.create(), ModelTransform.pivot(6.0F, -5.0F, 0.0F));

		ModelPartData cube_r4 = foot1.addChild("cube_r4", ModelPartBuilder.create().uv(0, 26).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, 0.0F, 6.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData tail = muddog.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(-8.0F, -10.0F, 0.0F));

		ModelPartData cube_r5 = tail.addChild("cube_r5", ModelPartBuilder.create().uv(16, 14).cuboid(-7.0F, -2.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 0.0F, 8.0F, -1.5708F, 1.2217F, 1.5708F));

		ModelPartData body = muddog.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r6 = body.addChild("cube_r6", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -6.0F, -1.0F, 16.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -5.0F, 8.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData head = muddog.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.0F, -8.0F));

		ModelPartData cube_r7 = head.addChild("cube_r7", ModelPartBuilder.create().uv(0, 14).cuboid(1.0F, -6.0F, -1.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 3.0F, 1.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData ear2 = head.addChild("ear2", ModelPartBuilder.create(), ModelTransform.of(11.0F, -2.0F, 3.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData cube_r8 = ear2.addChild("cube_r8", ModelPartBuilder.create().uv(24, 0).cuboid(-17.0F, 0.0F, 0.0F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, 10.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData ear1 = head.addChild("ear1", ModelPartBuilder.create(), ModelTransform.pivot(10.0F, -2.0F, 9.0F));

		ModelPartData cube_r9 = ear1.addChild("cube_r9", ModelPartBuilder.create().uv(24, 0).cuboid(-2.0F, 0.0F, -1.0F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-14.0F, 0.0F, -11.0F, 0.0F, -1.5708F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(MudEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		//this.setHeadAngles(netHeadYaw,headPitch);
		this.animateMovement(MudAnimations.WALKING,limbSwing,limbSwingAmount,2,2.5f);
		this.updateAnimation(entity.animationState, MudAnimations.IDLING,ageInTicks,1f);
	}
	private void setHeadAngles(float headYaw,float headPitch){
		headPitch = MathHelper.clamp(headPitch,-30.0f,30.0f);
		headYaw = MathHelper.clamp(headYaw,-25.0f,45.0f);
		this.head.yaw = headYaw;
		this.head.pitch = headPitch;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		muddog.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return muddog;
	}
}