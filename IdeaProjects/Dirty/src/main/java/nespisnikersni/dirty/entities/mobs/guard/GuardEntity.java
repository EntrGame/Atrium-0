package nespisnikersni.dirty.entities.mobs.guard;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.EnumSet;
import java.util.List;

public class GuardEntity extends VindicatorEntity{
    private int ticksExisted = 0;
    private PlayerEntity player;

    public GuardEntity(EntityType<? extends VindicatorEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
        this.goalSelector.add(1, new AroundTargetGoal(this, this.player));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 0.7, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.5));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(6,new FollowPlayerGoal(this,this.player,0.9,5,15,true));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, true, this::canTarget));
    }
    public PlayerEntity getPlayer(){
        return this.player;
    }

    public static DefaultAttributeContainer.Builder createGuardAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5f);
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return this.player!=null && this.getTarget()!=null && !this.getTarget().getName().equals(this.player.getName())&& !(this.getTarget() instanceof GuardEntity);
    }

    @Override
    protected void mobTick() {
        if (this.getTarget() == null || !this.getTarget().isAlive()) {
            double radius = 20.0;
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            Box box = new Box(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
            List<Entity> entities = this.getWorld().getOtherEntities(this, box);
            for (Entity entity : entities) {
                    if (entity instanceof LivingEntity && !(entity instanceof AnimalEntity) && this.getTarget()!=null && !this.getTarget().getName().equals(this.player.getName())) {
                        this.setTarget((LivingEntity) entity);
                        break;
                    }
            }
        }
        ticksExisted++;

        if (ticksExisted >= 3*20*60) {
            this.remove(RemovalReason.DISCARDED);
        }
        super.mobTick();
    }

    static class AroundTargetGoal extends ActiveTargetGoal<LivingEntity> {
        PlayerEntity player;

        public AroundTargetGoal(MobEntity mob, PlayerEntity player) {
            super(mob, LivingEntity.class, true, true);
            this.player = player;
        }

        @Override
        public void start() {}

        @Override
        public boolean canStart() {
            if (this.mob.getTarget() != null) {
                return !(this.mob.getTarget() instanceof PlayerEntity);
            }
            return true;
        }

        @Override
        public void stop() {
        }
        @Override
        public void tick() {
        }

    }

    public class FollowPlayerGoal extends Goal {
        public static final int TELEPORT_DISTANCE = 12;
        private static final int HORIZONTAL_RANGE = 2;
        private static final int HORIZONTAL_VARIATION = 3;
        private static final int VERTICAL_VARIATION = 1;
        private final MobEntity mob;
        private final LivingEntity target;
        private final WorldView world;
        private final double speed;
        private final EntityNavigation navigation;
        private int updateCountdownTicks;
        private final float maxDistance;
        private final float minDistance;
        private float oldWaterPathfindingPenalty;
        private final boolean leavesAllowed;
        public FollowPlayerGoal(MobEntity mob, LivingEntity target, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
            this.mob = mob;
            this.target = target;
            this.world = mob.getWorld();
            this.speed = speed;
            this.navigation = mob.getNavigation();
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.leavesAllowed = leavesAllowed;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (this.target == null) {
                return false;
            } else if (this.cannotFollow()) {
                return false;
            } else if (this.mob.squaredDistanceTo(this.target) < (double)(this.minDistance * this.minDistance)) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public boolean shouldContinue() {
            if (this.navigation.isIdle()) {
                return false;
            } else if (this.cannotFollow()) {
                return false;
            } else {
                return !(this.mob.squaredDistanceTo(this.target) <= (double)(this.maxDistance * this.maxDistance));
            }
        }

        private boolean cannotFollow() {
            return this.mob.hasVehicle() || this.mob.isLeashed();
        }

        @Override
        public void start() {
            this.updateCountdownTicks = 0;
            this.oldWaterPathfindingPenalty = this.mob.getPathfindingPenalty(PathNodeType.WATER);
            this.mob.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        }

        @Override
        public void stop() {
            this.navigation.stop();
            this.mob.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
        }

        @Override
        public void tick() {
            this.mob.getLookControl().lookAt(this.target, 10.0F, (float)this.mob.getMaxLookPitchChange());
            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = this.getTickCount(10);
                if (this.mob.squaredDistanceTo(this.target) >= 144.0) {
                    this.tryTeleport();
                } else {
                    this.navigation.startMovingTo(this.target, this.speed);
                }
            }
        }

        private void tryTeleport() {
            BlockPos blockPos = this.target.getBlockPos();

            for (int i = 0; i < 10; ++i) {
                int j = this.getRandomInt(-3, 3);
                int k = this.getRandomInt(-1, 1);
                int l = this.getRandomInt(-3, 3);
                boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
                if (bl) {
                    return;
                }
            }
        }

        private boolean tryTeleportTo(int x, int y, int z) {
            if (Math.abs((double) x - this.target.getX()) < 2.0 && Math.abs((double) z - this.target.getZ()) < 2.0) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
                return false;
            } else {
                this.mob.refreshPositionAndAngles((double) x + 0.5, (double) y, (double) z + 0.5, this.mob.getYaw(), this.mob.getPitch());
                this.navigation.stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos pos) {
            PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world, pos.mutableCopy());
            if (pathNodeType != PathNodeType.WALKABLE) {
                return false;
            } else {
                BlockState blockState = this.world.getBlockState(pos.down());
                if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
                    return false;
                } else {
                    BlockPos blockPos = pos.subtract(this.mob.getBlockPos());
                    return this.world.isSpaceEmpty(this.mob, this.mob.getBoundingBox().offset(blockPos));
                }
            }
        }

        private int getRandomInt(int min, int max) {
            return this.mob.getRandom().nextInt(max - min + 1) + min;
        }
    }
}

