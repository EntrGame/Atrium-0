package nespisnikersni.dirty.blocks.entities.sieve;

import nespisnikersni.dirty.blocks.entities.DirtyBlockEntities;
import nespisnikersni.dirty.blocks.entities.processing_table.ProcessingTableEntity;
import nespisnikersni.dirty.items.DirtyItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Items;
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

public class Sieve extends BlockWithEntity implements BlockEntityProvider{
    public Sieve(Settings settings) {
        super(settings.nonOpaque());
    }
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,11,16);

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SieveEntity(pos,state);
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
        return checkType(type, DirtyBlockEntities.SIEVE_ENTITY,((world1, pos, state1, blockEntity) -> blockEntity.tick(world1,pos,state1)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        SieveEntity entity = (SieveEntity) world.getBlockEntity(pos);
        if (player.getMainHandStack().getItem() == Items.AIR) {
            entity.increaseProgress();
            return ActionResult.SUCCESS;
        } else {
            entity.addItem(player);
            if (player.getMainHandStack().getItem() == entity.getStack(0).getItem()) {
                entity.increaseProgress();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SieveEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory)((SieveEntity)blockEntity));
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
