package nespisnikersni.dirty.items;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Dried_roots_sword extends SwordItem {
    public Dried_roots_sword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this,3600);
        StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.STRENGTH, 1800, 0);
        user.addStatusEffect(effect);
        Vec3d pos = user.getPos().add(user.getHandPosOffset(this));
        for (int i = 0; i < 50; i++) {
            double offsetX = (world.random.nextDouble() - 0.5) * 0.5;
            double offsetY = (world.random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (world.random.nextDouble() - 0.5) * 0.5;

            double speedX = (world.random.nextDouble() - 0.5) * 0.1;
            double speedY = (world.random.nextDouble() - 0.5) * 0.1;
            double speedZ = (world.random.nextDouble() - 0.5) * 0.1;

            world.addParticle(
                    ParticleTypes.FLAME,
                    pos.x + offsetX,
                    pos.y + offsetY+1,
                    pos.z + offsetZ,
                    speedX,
                    speedY,
                    speedZ
            );
        }
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
