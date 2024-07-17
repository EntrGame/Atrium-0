package nespisnikersni.dirty.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
	@Inject(method = "tick", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;craftRecipe(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/recipe/Recipe;Lnet/minecraft/util/collection/DefaultedList;I)Z",shift = At.Shift.AFTER))
	private static void onTick(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity,CallbackInfo info) {
		if(blockEntity.getStack(2).toString().contains("dried_roots")){
			world.createExplosion(null,pos.getX(),pos.getY(),pos.getZ(),5, World.ExplosionSourceType.TNT);
		}
	}
}