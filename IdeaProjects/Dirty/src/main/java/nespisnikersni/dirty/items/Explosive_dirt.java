package nespisnikersni.dirty.items;

import nespisnikersni.dirty.entities.projectiles.Explosive_dirt_entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Explosive_dirt extends Item {
        public Explosive_dirt(Settings settings) {
            super(settings);
        }
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            user.getItemCooldownManager().set(this,100);
            ItemStack itemStack = user.getStackInHand(hand);
            world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                Explosive_dirt_entity piece_of_dirt_entity = new Explosive_dirt_entity(user, world);
                piece_of_dirt_entity.setItem(itemStack);
                piece_of_dirt_entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(piece_of_dirt_entity);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return TypedActionResult.success(itemStack, world.isClient());
        }

}
