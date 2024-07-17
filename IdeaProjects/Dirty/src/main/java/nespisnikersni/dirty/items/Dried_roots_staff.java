package nespisnikersni.dirty.items;

import nespisnikersni.dirty.entities.ModEntities;
import nespisnikersni.dirty.entities.mobs.guard.GuardEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.math.Box;

import java.util.List;

public class Dried_roots_staff extends SwordItem {
    public Dried_roots_staff(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
    private int mode;
    private Text modeName;

    public void switchMode(ItemStack stack) {
        System.out.println(mode);
        mode = (mode + 1) % 5;
        stack.getOrCreateNbt().putInt("mode",mode);
        modeName=Text.translatable("dirty.mode"+String.valueOf(mode));
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        mode=stack.getOrCreateNbt().getInt("mode");
            if (user.isSneaking()) {
                if (!world.isClient) {
                    switchMode(user.getStackInHand(hand));
                    user.sendMessage(Text.translatable("dirty.mode").append(modeName).formatted(Formatting.YELLOW),true);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
            } else {
            switch (mode) {
                case 0 -> {
                    double radius = 5.0;
                    double x = user.getX();
                    double y = user.getY();
                    double z = user.getZ();
                    Box box = new Box(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
                    List<Entity> entities = world.getOtherEntities(user, box);

                    for (Entity entity : entities) {
                        entity.damage(user.getDamageSources().cactus(), 10);
                        entity.setOnFireFor(10);

                        Vec3d knockbackDirection = entity.getPos().subtract(user.getPos()).normalize().multiply(2.0);
                        entity.addVelocity(knockbackDirection.x, knockbackDirection.y, knockbackDirection.z);

                        for (int i = 0; i < 20; i++) {
                            double offsetX = (Math.random() - 0.5) * 2.0;
                            double offsetY = (Math.random() - 0.5) * 2.0;
                            double offsetZ = (Math.random() - 0.5) * 2.0;
                            world.addParticle(ParticleTypes.FLAME, entity.getX() + offsetX, entity.getY() + offsetY, entity.getZ() + offsetZ, 0, 0, 0);
                        }
                    }

                    for (int i = 0; i < 100; i++) {
                        double offsetX = (Math.random() - 0.5) * 2.0 * radius;
                        double offsetY = (Math.random() - 0.5) * 2.0 * radius;
                        double offsetZ = (Math.random() - 0.5) * 2.0 * radius;
                        world.addParticle(ParticleTypes.FLAME, x + offsetX, y + offsetY+1, z + offsetZ, -offsetX / 5.0, -offsetY / 5.0, -offsetZ / 5.0);
                    }
                    user.getItemCooldownManager().set(this,80);
                    if(!user.isCreative()){
                        stack.damage(1,user,(p) -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        });
                    }
                    return TypedActionResult.success(stack);
                }
                case 1 -> {
                        Vec3d velocity = user.getRotationVec(1.0F);
                        FireballEntity fireball = new FireballEntity(world, user, velocity.x, velocity.y, velocity.z, 3);
                        world.spawnEntity(fireball);
                        user.getItemCooldownManager().set(this,20);
                    if(!user.isCreative()){
                        stack.damage(1,user,(p) -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        });
                    }
                        return TypedActionResult.success(stack);
                }
                case 2 -> {
                    double maxDistance = 100.0;
                    HitResult hitResult = user.raycast(maxDistance, 1.0F, false);
                    if (hitResult.getType() == HitResult.Type.ENTITY){
                        EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                        Entity targetEntity = entityHitResult.getEntity();

                        if (targetEntity != null) {
                            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                            if (lightning != null) {
                                lightning.refreshPositionAfterTeleport(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ());
                                world.spawnEntity(lightning);
                            }
                        }
                    } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                        BlockPos targetBlockPos = blockHitResult.getBlockPos();
                        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                        if (lightning != null) {
                            lightning.refreshPositionAfterTeleport(targetBlockPos.getX(), targetBlockPos.getY(), targetBlockPos.getZ());
                            world.spawnEntity(lightning);
                        }
                    }
                    if(!user.isCreative()){
                        stack.damage(1,user,(p) -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        });
                    }
                    user.getItemCooldownManager().set(this, 10);
                    return TypedActionResult.success(stack);
                }

                case 3 -> {
                    GuardEntity guard = new GuardEntity(ModEntities.GUARD_ENTITY_TYPE, world);
                    guard.setPlayer(user);
                    guard.refreshPositionAndAngles(user.getX() + 1, user.getY(), user.getZ(), 0, 0);
                    GuardEntity guard1 = new GuardEntity(ModEntities.GUARD_ENTITY_TYPE, world);
                    guard1.setPlayer(user);
                    guard1.refreshPositionAndAngles(user.getX() + 1, user.getY(), user.getZ() + 1, 0, 0);
                    GuardEntity guard2 = new GuardEntity(ModEntities.GUARD_ENTITY_TYPE, world);
                    guard2.setPlayer(user);
                    guard2.refreshPositionAndAngles(user.getX() - 1, user.getY(), user.getZ() - 1, 0, 0);
                    Vec3d pos = user.getPos().add(user.getHandPosOffset(this));
                    for (int i = 0; i < 50; i++) {
                        double offsetX = (world.random.nextDouble() - 0.5) * 0.5;
                        double offsetY = (world.random.nextDouble() - 0.5) * 0.5;
                        double offsetZ = (world.random.nextDouble() - 0.5) * 0.5;
                        double speedX = (world.random.nextDouble() - 0.5) * 0.1;
                        double speedY = (world.random.nextDouble() - 0.5) * 0.1;
                        double speedZ = (world.random.nextDouble() - 0.5) * 0.1;
                        world.addParticle(
                                ParticleTypes.PORTAL,
                                pos.x + offsetX,
                                pos.y + offsetY+1,
                                pos.z + offsetZ,
                                speedX,
                                speedY,
                                speedZ
                        );
                    }
                    if(!user.isCreative()){
                        stack.damage(1,user,(p) -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        });
                    }
                    if (world.spawnEntity(guard) && world.spawnEntity(guard1) && world.spawnEntity(guard2)) {
                        user.getItemCooldownManager().set(this, 1200);
                        return TypedActionResult.success(stack);
                    } else {
                        return TypedActionResult.fail(stack);
                    }
                    }
                case 4 -> {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,400,1));
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,400,0));
                    if(!user.isCreative()){
                        stack.damage(1,user,(p) -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        });
                    }
                    user.getItemCooldownManager().set(this,800);
                    return TypedActionResult.success(stack);
                }
                }
            }
        return super.use(world, user, hand);
    }




}
