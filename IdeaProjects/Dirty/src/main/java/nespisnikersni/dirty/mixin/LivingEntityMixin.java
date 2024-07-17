package nespisnikersni.dirty.mixin;

import nespisnikersni.dirty.enchants.DirtyEnchants;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void cancelFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity instanceof PlayerEntity) {
            PlayerEntity player=(PlayerEntity) entity;
            if(EnchantmentHelper.getLevel(DirtyEnchants.FLY, player.getEquippedStack(EquipmentSlot.FEET)) > 0 && !player.isCreative()) {
                cir.setReturnValue(false);
            }
        }
    }
}
