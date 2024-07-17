package nespisnikersni.dirty.blocks.entities.processing_table;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.blocks.entities.DirtyBlockEntities;
import nespisnikersni.dirty.items.DirtyItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ProcessingTable extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,16,16);
    public ProcessingTable(Settings settings) {
        super(settings.nonOpaque());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ProcessingTableEntity(pos,state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type,DirtyBlockEntities.PROCESSING_TABLE_ENTITY,((world1, pos, state1, blockEntity) -> blockEntity.tick(world1,pos,state1)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ProcessingTableEntity blockEntity = (ProcessingTableEntity) world.getBlockEntity(pos);
        if(player.getMainHandStack().getItem()==DirtyItems.DIAMOND_HAMMER) {
            blockEntity.increaseProgress();
        }else{
            blockEntity.addItem(player.getMainHandStack().getItem());
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ProcessingTableEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory)((ProcessingTableEntity)blockEntity));
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}
