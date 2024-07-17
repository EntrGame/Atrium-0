package nespisnikersni.dirty.blocks.entities;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.blocks.DirtyBlocks;
import nespisnikersni.dirty.blocks.entities.dirt_recycler.DirtRecyclerEntity;
import nespisnikersni.dirty.blocks.entities.processing_table.ProcessingTableEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class DirtyBlockEntities {
   public static final BlockEntityType<DirtRecyclerEntity> DIRT_RECYCLER_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Dirty.MOD_ID, "dirt_recycler_entity"),
            FabricBlockEntityTypeBuilder.create(DirtRecyclerEntity::new, DirtyBlocks.DIRT_RECYCLER).build()
    );
   public static final BlockEntityType<ProcessingTableEntity> PROCESSING_TABLE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Dirty.MOD_ID, "processing_table_entity"),
            FabricBlockEntityTypeBuilder.create(ProcessingTableEntity::new,DirtyBlocks.PROCESSING_TABLE).build()
    );
    public static final BlockEntityType<ProcessingTableEntity> SIEVE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Dirty.MOD_ID, "sieve"),
            FabricBlockEntityTypeBuilder.create(ProcessingTableEntity::new,DirtyBlocks.SIEVE).build()
    );
    public static void register(){}
}
