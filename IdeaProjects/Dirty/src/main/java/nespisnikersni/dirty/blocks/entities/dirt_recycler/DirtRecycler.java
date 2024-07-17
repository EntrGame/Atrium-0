package nespisnikersni.dirty.blocks.entities.dirt_recycler;

import nespisnikersni.dirty.blocks.entities.DirtyBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DirtRecycler extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,16,16);
    public DirtRecycler(Settings settings) {
        super(settings.nonOpaque());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DirtRecyclerEntity(pos,state);
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
        return checkType(type, DirtyBlockEntities.DIRT_RECYCLER_ENTITY, ((world1, pos, state1, blockEntity) -> blockEntity.tick(world1,pos,state1)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            NamedScreenHandlerFactory screenHandlerFactory = ((DirtRecyclerEntity) world.getBlockEntity(pos));
            if(screenHandlerFactory!=null){
                player.openHandledScreen(screenHandlerFactory);
            }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DirtRecyclerEntity) {
                ItemScatterer.spawn(world, pos, (Inventory)((DirtRecyclerEntity)blockEntity));
                world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
