package nespisnikersni.dirty.events;

import nespisnikersni.dirty.enchants.DirtyEnchants;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DirtyEvents implements PlayerBlockBreakEvents.After{
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (EnchantmentHelper.getLevel(DirtyEnchants.BOER, player.getMainHandStack()) > 0 && !player.isSneaking()) {
            int radius = 1;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos currentPos = pos.add(x, y, z);
                        BlockState blockState = world.getBlockState(currentPos);
                        if (blockState.getHardness(world, currentPos) >= 0 && player.canPlaceOn(currentPos, Direction.UP, player.getMainHandStack())) {
                            if (!player.isCreative()) {
                                player.getMainHandStack().damage(1, player, (p) -> {
                                    p.sendToolBreakStatus(player.getActiveHand());
                                });
                            }
                                world.breakBlock(currentPos, false, player);
                                if(EnchantmentHelper.getLevel(DirtyEnchants.AUTO_SMELT, player.getMainHandStack()) > 0) {
                                    LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld) world);
                                    builder.add(LootContextParameters.TOOL, player.getMainHandStack());
                                    builder.add(LootContextParameters.BLOCK_STATE, blockState);
                                    builder.add(LootContextParameters.ORIGIN, new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
                                    List<ItemStack> drops = blockState.getDroppedStacks(builder);
                                    drops = getAutoSmeltedDrops(world,drops);
                                    for (ItemStack drop : drops) {
                                        System.out.println(drop.getName().getString());
                                        Block.dropStack(world, currentPos, drop);
                                    }
                                }else{
                                    LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld) world);
                                    builder.add(LootContextParameters.TOOL, player.getMainHandStack());
                                    builder.add(LootContextParameters.BLOCK_STATE, blockState);
                                    builder.add(LootContextParameters.ORIGIN, new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
                                    List<ItemStack> drops = blockState.getDroppedStacks(builder);
                                    for (ItemStack drop : drops) {
                                        Block.dropStack(world, currentPos, drop);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    public static List<ItemStack> getAutoSmeltedDrops(World world, List<ItemStack> originalDrops) {
        List<ItemStack> smeltedDrops = new ArrayList<>();
        for (ItemStack itemStack : originalDrops) {
            Optional<SmeltingRecipe> recipe = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(itemStack), world);
            DynamicRegistryManager registryManager = world.getRegistryManager();
            if (recipe.isPresent()) {
                ItemStack smelted = recipe.get().getOutput(registryManager).copy();
                smelted.setCount(itemStack.getCount());
                smeltedDrops.add(smelted);
            } else {
                smeltedDrops.add(itemStack);
            }
        }
        return smeltedDrops;
    }

}


