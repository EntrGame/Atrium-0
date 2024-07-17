package nespisnikersni.dirty.entities.mobs.mud;

import nespisnikersni.dirty.entities.ModEntities;
import nespisnikersni.dirty.items.DirtyItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class MudEntity extends AnimalEntity implements Shearable {
    private int timer = 0;
    public final AnimationState animationState = new AnimationState();
    public MudEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMudAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,15)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.3f);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(new ItemConvertible[]{DirtyItems.PIECE_OF_DIRT}), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.MUD_ENTITY_TYPE.create(world);
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    @Override
    protected void mobTick() {
        timer++;
        super.mobTick();
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
    }

    public boolean isShearable() {
        return this.isAlive() && timer >= 200 && !this.isBaby();
    }
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.getWorld().isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(2, player, (playerx) -> {
                    playerx.sendToolBreakStatus(hand);
                });
                ItemConvertible[] dropItems = new ItemConvertible[]{
                        Items.GRASS_BLOCK,
                        Items.MUD,
                        Items.MOSS_BLOCK,
                        Items.AIR
                };
                int randomIndex = this.random.nextInt(dropItems.length);
                ItemConvertible selectedItem = dropItems[randomIndex];
                ItemEntity itemEntity = this.dropItem(selectedItem, 1);
                if (itemEntity != null) {
                    Vec3d velocity = itemEntity.getVelocity();
                    itemEntity.setVelocity(velocity.add(
                            (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                            this.random.nextFloat() * 0.05F,
                            (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                    ));
                }
                timer=0;
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

}
