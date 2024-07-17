package nespisnikersni.dirty.entity.guard;

import nespisnikersni.dirty.entities.mobs.guard.GuardEntity;
import nespisnikersni.dirty.entity.animations.GuardAnimations;
import nespisnikersni.dirty.entity.animations.MudAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.10.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GuardModel<T extends GuardEntity> extends SinglePartEntityModel<T> {
	private final ModelPart main;
	private final ModelPart head;
	public GuardModel(ModelPart root) {
		this.main = root.getChild("main");
		this.head = main.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData leg1 = main.addChild("leg1", ModelPartBuilder.create().uv(0, 34).cuboid(-1.0F, 10.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(30, 18).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -11.0F, 1.0F));

		ModelPartData leg2 = main.addChild("leg2", ModelPartBuilder.create().uv(12, 34).cuboid(-1.0F, 10.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(30, 30).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -11.0F, 1.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -25.0F, -2.0F, 10.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData arm2 = main.addChild("arm2", ModelPartBuilder.create().uv(38, 7).cuboid(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -22.0F, 1.0F));

		ModelPartData cube_r1 = arm2.addChild("cube_r1", ModelPartBuilder.create().uv(32, 9).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 0.0F, -8.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData arm1 = main.addChild("arm1", ModelPartBuilder.create().uv(36, 28).cuboid(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -22.0F, 1.0F));

		ModelPartData cube_r2 = arm1.addChild("cube_r2", ModelPartBuilder.create().uv(32, 0).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 0.0F, -8.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 20).cuboid(-4.0F, -7.0F, -3.0F, 8.0F, 7.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -25.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(GuardEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw,headPitch);
		this.animateMovement(GuardAnimations.WALKING,limbSwing,limbSwingAmount,2,2.5f);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return main;
	}

	private void setHeadAngles(float headYaw,float headPitch){
		headPitch = MathHelper.clamp(headPitch,-10.0f,10.0f);
		headYaw = MathHelper.clamp(headYaw,-5.0f,5.0f);
		this.head.yaw = headYaw;
		this.head.pitch = headPitch;
	}
}