package nespisnikersni.dirty.entities;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.entities.mobs.guard.GuardEntity;
import nespisnikersni.dirty.entities.mobs.mud.MudEntity;
import nespisnikersni.dirty.entities.projectiles.Explosive_dirt_entity;
import nespisnikersni.dirty.entities.projectiles.Piece_of_dirt_entity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<Piece_of_dirt_entity> PIECE_OF_DIRT_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dirty.MOD_ID,"piece_of_dirt_entity"), FabricEntityTypeBuilder.<Piece_of_dirt_entity>create(SpawnGroup.MISC, Piece_of_dirt_entity::new)
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f))
                    .build());
    public static final EntityType<Explosive_dirt_entity> EXPLOSIVE_DIRT_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dirty.MOD_ID,"explosive_dirt_entity"), FabricEntityTypeBuilder.<Explosive_dirt_entity>create(SpawnGroup.MISC, Explosive_dirt_entity::new)
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f))
                    .build());
    public static final EntityType<MudEntity> MUD_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dirty.MOD_ID,"mud_dog"), FabricEntityTypeBuilder.<MudEntity>create(SpawnGroup.MISC, MudEntity::new)
                    .dimensions(EntityDimensions.fixed(1,1))
                    .build());
    public static final EntityType<GuardEntity> GUARD_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dirty.MOD_ID, "guard"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, GuardEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
                    .build()
    );
}
