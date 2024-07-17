package nespisnikersni.dirty.mixin;

import nespisnikersni.dirty.items.BurntIronSheet;
import nespisnikersni.dirty.items.DirtyItems;
import nespisnikersni.dirty.items.Dried_roots_sword;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "tick",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        ItemEntity entity = (ItemEntity) (Object) this;
        World world = entity.getWorld();
        ItemStack stack = entity.getStack();

        if (!world.isClient && stack.getItem() == DirtyItems.BURNT_IRON_SHEET && entity.isTouchingWater()) {
            entity.setStack(new ItemStack(DirtyItems.IRON_SHEET));
        }
    }
}
