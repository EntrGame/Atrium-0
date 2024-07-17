package nespisnikersni.dirty.mixin.client;

import nespisnikersni.dirty.enchants.DirtyEnchants;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.enchantment.EnchantmentHelper;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
	private int jumpCount = 0;
	private boolean jumpedLastTick = false;
	private long lastJumpTime = 0;
	private static final long DOUBLE_JUMP_THRESHOLD = 400;

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void tickMovement(CallbackInfo info) {
		net.minecraft.client.network.ClientPlayerEntity player = (net.minecraft.client.network.ClientPlayerEntity) (Object) this;
		if (jumpCount == 1 && !jumpedLastTick) {
			if (player.input.jumping) {
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastJumpTime <= DOUBLE_JUMP_THRESHOLD) {
					jumpCount = 0;
					toggleFlight(player);
					lastJumpTime = 0;
				} else {
					lastJumpTime = currentTime;
				}
			}
		}else if (jumpCount == 0&& !jumpedLastTick) {
			if (player.input.jumping) {
				lastJumpTime = System.currentTimeMillis();
			}
			jumpCount++;
		}
		jumpedLastTick = player.input.jumping;
	}

	private void toggleFlight(PlayerEntity player) {
		if(EnchantmentHelper.getLevel(DirtyEnchants.FLY, player.getEquippedStack(EquipmentSlot.FEET)) > 0 && !player.isCreative()) {
			if (player.getAbilities().allowFlying) {
				player.getAbilities().flying = false;
				player.getAbilities().allowFlying = false;
			} else {
				player.getAbilities().flying = true;
				player.getAbilities().allowFlying = true;
			}
			player.sendAbilitiesUpdate();
		}
	}
}