package nespisnikersni.dirty.entities.projectiles;

import nespisnikersni.dirty.entities.ModEntities;
import nespisnikersni.dirty.items.DirtyItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;


public class Explosive_dirt_entity extends ThrownItemEntity{
    public Explosive_dirt_entity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public Explosive_dirt_entity(LivingEntity livingEntity, World world) {
        super(ModEntities.EXPLOSIVE_DIRT_ENTITY_TYPE, livingEntity,world);
    }

    @Override
    public HitResult raycast(double maxDistance, float tickDelta, boolean includeFluids) {
        return super.raycast(maxDistance, tickDelta, includeFluids);
    }

    @Override
    protected Item getDefaultItem() {
        return DirtyItems.EXPLOSIVE_DIRT;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        World world = this.getWorld();
        world.createExplosion(this,this.getX(),this.getY(),this.getZ(),3, World.ExplosionSourceType.TNT);
        this.kill();
    }
}
