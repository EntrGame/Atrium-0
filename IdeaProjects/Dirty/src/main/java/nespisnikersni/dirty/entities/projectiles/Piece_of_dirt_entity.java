package nespisnikersni.dirty.entities.projectiles;

import nespisnikersni.dirty.entities.ModEntities;
import nespisnikersni.dirty.items.DirtyItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;


public class Piece_of_dirt_entity extends ThrownItemEntity {

    public Piece_of_dirt_entity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public Piece_of_dirt_entity(LivingEntity livingEntity, World world) {
        super(ModEntities.PIECE_OF_DIRT_ENTITY_TYPE, livingEntity,world);
    }



    @Override
    protected Item getDefaultItem() {
        return DirtyItems.PIECE_OF_DIRT;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 4f);
        this.kill();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.kill();
    }
}
