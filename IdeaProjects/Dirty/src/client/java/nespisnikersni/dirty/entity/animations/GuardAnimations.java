package nespisnikersni.dirty.entity.animations;// Save this class in your mod and generate all required imports

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

/**
 * Made with Blockbench 4.10.2
 * Exported for Minecraft version 1.19 or later with Yarn mappings
 * @author Author
 */
public class GuardAnimations {
	public static final Animation WALKING = Animation.Builder.create(1.5F).looping()
		.addBoneAnimation("leg1", new Transformation(Transformation.Targets.ROTATE,
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("leg2", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("arm1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(57.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(115.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(57.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("arm2", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(122.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(57.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(122.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final Animation ATTACK = Animation.Builder.create(1.0F)
		.addBoneAnimation("arm1", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-25.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("arm2", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();
}