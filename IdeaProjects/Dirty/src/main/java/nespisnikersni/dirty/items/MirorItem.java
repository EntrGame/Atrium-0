package nespisnikersni.dirty.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MirorItem extends Item {
    public MirorItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient && world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            MinecraftServer server = serverWorld.getServer();
            ServerPlayerEntity clone = new ServerPlayerEntity(server, serverWorld, user.getGameProfile());

            if (clone != null) {
                clone.copyFrom((ServerPlayerEntity) user, false);
                Vec3d userPos = user.getPos();
                clone.refreshPositionAndAngles(userPos.x + 1, userPos.y, userPos.z, user.getYaw(), user.getPitch());
                serverWorld.spawnEntity(clone);
            }
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 0;
    }
}
